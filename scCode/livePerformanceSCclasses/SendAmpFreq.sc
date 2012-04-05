/* 
Aris Bezas Mon, 12 12 2012, 20:36
A class based on SendAmpFreq for machine listening.

a = SendAmpFreq.new
a.start
a.stop
a.dump
a.impulseRate(24)

*/

SendAmpFreq {
	classvar default;
	var <server;					// the scserver that runs the listening process
	var <synthListenAmpFreq;		// the listening Amp Freq process
	var <synthListenOnsets;			// the listening Onsets process
	var <synthPlay;					// the produce process
	var <addr;				// the address (p5, of ...) for sending the data for drawing
	var <chan = 0;		// the channel that we detect

	*default {
		if (default.isNil) { default = this.new };  // create default
		^default;
	}
	*new { | server, addr, chan = 0 |
		^super.new.init(server, addr, chan);		
	}	
	init { | argServer, argAddr, argChan = 0 |
		server = argServer ?? { Server.default };  //define server
		addr =  argAddr ?? { NetAddr("127.0.0.1", 12345); }; //localhost, oF port
		chan = argChan;
	}

	start {
		if (not(server.serverRunning)) { server.boot };
		server.doWhenBooted {			// doWhenBooted very good method
			 synthListenAmpFreq = SynthDef("SendAmpPitch",{ 
				| chan = 8, ampTrig = 1, freqTrig = 2, impulseRate = 24 |
				var trig, in, amp, freq, hasFreq;
				trig = Impulse.kr(impulseRate);
				in = In.ar(chan);
				amp = Amplitude.kr(in, 0.001, 0.001);
				#freq, hasFreq = Pitch.kr(in, ampThreshold: 0.02, median: 1);
				SendTrig.kr(trig, ampTrig, amp);  
				SendTrig.kr(trig, freqTrig, freq);  
			}).play(server);
		};
	}

	stop {
		synthListenAmpFreq.free;
	}
	
	impulseRate	{ |impulseRateArg|
		synthListenAmpFreq.set(\impulseRate, impulseRateArg);
	}
}

