/* 
Aris Bezas Mon, 12 12 2012, 20:36
SpecCentroid for machine listening.

//Class implementation
c = SendSpecCentroid.new
c.dump
c.start
c.impulseRate(1)
c.stop;
*/

SendSpecCentroid {
	classvar default;
	var <server;					// the scserver that runs the listening process
	var <synthListenCentroid;			// the listening Onsets process
	var <synthPlay;					// the produce process
	var <addr;				// the address (p5, of ...) for sending the data for drawing
	var <chan = 0;		// the channel that we detect
	var <responders;		//	responders 

	*default {
		if (default.isNil) { default = this.new };  // create default
		^default;
	}
	
	*new { | server, addr, chan = 0|
		^super.new.init(server, addr, chan);		
	}	
	init { | argServer, argAddr, argChan = 0|
		server = argServer ?? { Server.default };  //define server
		addr =  argAddr ?? { NetAddr("127.0.0.1", 12345); }; //localhost, oF port
		chan = argChan;
	}

	start	{ 
		if (not(server.serverRunning)) { server.boot };
		server.doWhenBooted {			
			 synthListenCentroid = SynthDef(\onsetSynth, { |thres = 1, impulseRate = 24|
				var sig, chain, onsets, pips,buf, centroid, trig;
				trig = Impulse.kr(impulseRate);
				buf = Buffer.alloc(server, 512);
				sig = In.ar(8);
				chain = FFT(buf, sig);
				centroid = SpecCentroid.kr(chain);
				SendTrig.kr(trig, 3, centroid); 
			}).play(server);
		};		
			
	}
	stop	{
		synthListenCentroid.free;	
	}
	impulseRate	{ |impulseRateArg|
		synthListenCentroid.set(\impulseRate, impulseRateArg);
	}
}

