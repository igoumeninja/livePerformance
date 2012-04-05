/* 

Aris Bezas Mon, 12 12 2012, 20:36
Send for machine listening.

b = SendOnsets.new
b.start
b.threshold(0)

b.stop
*/

SendOnsets {
	classvar default;
	var <server;					// the scserver that runs the listening process
	var <synthListenOnsets;			// the listening Onsets process
	var <synthPlay;					// the produce process
	var <addr;				// the address (p5, of ...) for sending the data for drawing
	var <chan = 0;		// the channel that we detect

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
			 synthListenOnsets = SynthDef(\onsetSynth, { |thres = 1|
				var sig, chain, onsets, pips,buf;
				buf = Buffer.alloc(server, 512);
				sig = In.ar(8);
				chain = FFT(buf, sig);
				thres.postln;
				onsets = Onsets.kr(chain, thres, \rcomplex);
				SendReply.kr(onsets, \onset);		
			}).play(server);
		};		
			
	}
	stop	{
		synthListenOnsets.free;	
	}
	threshold		{ |threshold|
		synthListenOnsets.set(\thres, threshold);	
	}
}

