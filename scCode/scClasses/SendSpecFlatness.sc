/* 
Aris Bezas Mon, 15 12 2012, 20:36
SpecFlatness for machine listening.

d = SendSpecFlatness.new
d.start
d.impulseRate(1)
d.stop
*/

SendSpecFlatness {
	classvar default;
	var <server;					// the scserver that runs the listening process
	var <synthListenSpecFlatness;			// the listening Onsets process
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
			 synthListenSpecFlatness = SynthDef(\specFlatness, { |thres = 1, impulseRate = 24|
				var sig, chain, onsets, pips,buf, flat, flatdb, flatdbsquish, trig;
				trig = Impulse.kr(impulseRate);
				buf = Buffer.alloc(server, 512);
				sig = In.ar(8);
				chain = FFT(buf, sig);
				flat = SpecFlatness.kr(chain);
				flatdb = 10 * flat.log; // Convert to decibels
				flatdbsquish = LinLin.kr(flatdb, -45, -1.6, 0, 1).max(-10); // Rescale db E {0,1}
				SendTrig.kr(trig, 4, flatdbsquish); 
			}).play(server);
		};		
			
	}
	stop	{
		synthListenSpecFlatness.free;	
	}
	impulseRate	{ |impulseRateArg|
		synthListenSpecFlatness.set(\impulseRate, impulseRateArg);
	}
}

