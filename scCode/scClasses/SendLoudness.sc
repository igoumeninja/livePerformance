/* 
Aris Bezas Mon, 16 12 2012, 19:36
Loudness for machine listening.

e = SendLoudness.new
e.start
e.impulseRate(1)
e.stop
*/

SendLoudness {
	classvar default;
	var <server;					// the scserver that runs the listening process
	var <synthListenLoudness;			// the listening Onsets process
	var <synthPlay;					// the produce process
	var <addr;				// the address (p5, of ...) for sending the data for drawing
	var <chan = 0;		// the channel that we detect
	var <responders;		//	responders 

	*default {
		if (default.isNil) { default = this.new };  // create default
		^default;
	}
	*removeResponders { this.default.removeResponders; }
	
	*new { | server, addr, chan = 0|
		^super.new.init(server, addr, chan);		
	}	
	init { | argServer, argAddr, argChan = 0|
		server = argServer ?? { Server.default };  //define server
		addr =  argAddr ?? { NetAddr("127.0.0.1", 12345); }; //localhost, oF port
		chan = argChan;
		this.makeResponders;	// call makeResponders
	}
	makeResponders {
		responders = [
			this.makeLoudnessResp		
		];
	}
	makeLoudnessResp {		
		^OSCresponder(server.addr, '/tr',{ arg time,responder,msg;
			switch(msg[2], 	
				5, {	OF.mlab('loundess', msg[3]); }  
			);
		});
	}
	start	{ 
		responders do: _.add;			
		if (not(server.serverRunning)) { server.boot };
		server.doWhenBooted {			
			 synthListenLoudness = SynthDef(\loudness, { |thres = 1, impulseRate = 24|
				var sig, chain, onsets, pips,buf, loudness, trig;
				trig = Impulse.kr(impulseRate);
				buf = Buffer.alloc(server, 512);
				sig = In.ar(8);
				chain = FFT(buf, sig);
				loudness=Loudness.kr(chain);
				SendTrig.kr(trig, 5, loudness); 
			}).play(server);
		};		
			
	}
	stop	{
		synthListenLoudness.free;	
	}
	impulseRate	{ |impulseRateArg|
		synthListenLoudness.set(\impulseRate, impulseRateArg);
	}
	
	removeResponders {
		responders do: _.remove;
	}
}

