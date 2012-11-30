//A practical buffer player 
//Omer Chatziserif 
//Thu, 24 May 2011, 08:30
//Corfu

/*



'exponential'
'sine'
'welch'
'step'


Env.new([0.001, 1.0, 1.7, 0.001], [1.1, 2, 1],'sine').test.plot;

*/

Kaffer : Buffer {
	
	var <>bout = 0, <>batt = 0.1, <>bsus = 2.0, <>brls = 2.5, <>bmul = 1.0, <>bloop = false;
	var <>btrig = 0, <>bstart = 0, <>bend = 1, <>breset = 0, <>bpan = 0;
	var <>brt = 1.0, <>brt1 = 1.0, <>brt2 = 1.0, <>brt3 = 1.0, <>brt1Dur = 1.0, <>brt2Dur = 0.5, <>btrDur = 0.2, <>bvib = 1;
	var <>broom = 125, <>brev = 1, <>bdamp = 0.5;
	var <>bpv1a = 0.1, <>bpv1b = 0.1;
	var <>bpv2a = 0.1, <>bpv2b = 0.5;
	var <>bpv3a = 0.1, <>bpv3b = 0.1;
	var <>bpv4a = 0.1, <>bpv4b = 0.1;
	var <>bpv5a = 0.00001, <>bpv5b = 0.001; 
	
	*initClass {
		StartUp add: {
			"-Kaffer Class by Omer".postln;
			this.loadKafs;

		}
	}

	// PV1 FFT with PV_BrickWall
	playPV1 { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^SynthDef("playPV1", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT({LocalBuf(2048, 2)}.dup(2), player);
			chain = PV_BrickWall(chain, 
				SinOsc.kr(
					brt2 * XLine.kr(1, 15 * [1, 1.6], bsus), 
					Rand(0, pi)
				);
			); 
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}


	// PV1R FFT with PV_BrickWall reverse
	playPV1R { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^SynthDef("playPV1", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = BufRd.ar(
						numChannels,
						bufnum, 
						Phasor.ar(
							btrig, 
							BufRateScale.kr(bufnum) * brt *(-1), 
							BufFrames.kr(bufnum) * bstart, 
							BufFrames.kr(bufnum) * bend, 
							BufFrames.kr(bufnum) * breset
						),
						loop: bloop.binaryValue
					);
			chain = FFT({LocalBuf(2048, 2)}.dup(2), player);
			chain = PV_BrickWall(chain, Line.kr(0, -1, bsus));
 
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}



	// PV2 FFT with PV_BinScramble
	playPV2 { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pv2a, pv2b, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		bpv2a = pv2a ? bpv2a;
		bpv2b = pv2b ? bpv2b;
		
		^SynthDef("playPV2", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT(LocalBuf(2048), player);
			chain = PV_BinScramble(chain, bpv2a , 0.1, bpv2b );
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}


	// PV3 FFT with PV_BinShift
	playPV3 { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pv3a, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		bpv3a = pv3a ? bpv3a;
		
		
		^SynthDef("playPV3", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT(LocalBuf(2048), player);
			chain = PV_BinShift(
				chain, 
				bpv3a,
				SinOsc.kr(
					brt2 , 
					Rand(0, pi)
				);
			);
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}


	// PV4 FFT with PV_MagShift
	playPV4 { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pv4a, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		bpv4a = pv4a ? bpv4a;
		
		^SynthDef("playPV4", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT(LocalBuf(2048), player);
			chain = PV_MagShift(chain,  XLine.kr(0.25 * [1, 1.6], 4, bsus), bpv4a ); 
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}


	// PV4B FFT with PV_MagShift
	playPV4B { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pv4a, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		bpv4a = pv4a ? bpv4a;
		
		^SynthDef("playPV4", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT(LocalBuf(2048), player);
			chain = PV_MagShift(chain,  XLine.kr(0.25 * [1, 1.6], 4, bsus), bpv4a ); 
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}



	// PV5 FFT with PV_ConformalMap
	playPV5 { arg  att, sus, rls, mul, trig, rate, rate2, start, loop, pv5a, pv5b, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		brt2 = rate2 ? brt2;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		bpv5a = pv5a ? bpv5a;
		bpv5b = pv5b ? bpv5b;
		
		^SynthDef("playPV5", { 
			var player, chain, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			chain = FFT(LocalBuf(2048), player);
			chain = PV_ConformalMap(chain, bpv5a, bpv5b); 
			// --- pvc1 -1.0 -> 1.0 ---- pvc1 -1.0 -> 1.0
			
			Out.ar(bout, IFFT(chain) * bmul *env);
		}).play(Server.default);
	}


////// -------- TODO : add dseq for scale -------
	//with PlayBuf SynthDef
	playSD { arg  att, sus, rls, mul, trig, rate, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^SynthDef("playSD", { 
			var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 'linear', loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player * bmul *env);
		}).play(Server.default);
	}

	//with PlayBuf
	transBuf { arg  att, sus, rls, mul, trig, rt, rt1, rt2, rt3, rt1Dur, rt2Dur, trDur, vib, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rt ? brt;
		brt1 = rt1 ? brt1;
		brt2 = rt2 ? brt2;
		brt3 = rt3 ? brt3;
		btrDur = trDur ? btrDur;
		brt1Dur = rt1Dur ? brt1Dur;
		brt2Dur = rt2Dur ? brt2Dur;
		bvib = vib ? bvib;
		bstart = start ? bstart;

		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		
		
		^{ var player, panlayer, env, env2;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], -4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			
			env2 =  EnvGen.ar(
				Env.new([brt1, brt1, brt2, brt2], [brt1Dur, btrDur, brt2Dur+brls], -4, loop, releaseNode: nil), 
				1, 
				doneAction: 0
			);
			
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						SinOsc.kr(bvib*MouseX.kr(0.01, 4)).range(0.99, 1.01)*BufRateScale.kr(bufnum)*1 *env2,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);

			Out.ar(bout, player*2 *bmul *env);
		}.play(Server.default);
	}

	//with PlayBuf
	playBuf { arg  att, sus, rls, mul, trig, rate, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^{ var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player * bmul *env);
		}.play(Server.default);
	}

	//with BufRD to be able to reverse a buffer
	playBufR { arg  att, sus, rls, mul, trig, rate, start, end, reset, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		bend = end ? bend;
		breset = reset ? breset;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		
		^{ var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], -5, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = BufRd.ar(
						numChannels,
						bufnum, 
						Phasor.ar(
							btrig, 
							BufRateScale.kr(bufnum) * brt *(-1), 
							BufFrames.kr(bufnum) * bstart, 
							BufFrames.kr(bufnum) * bend, 
							BufFrames.kr(bufnum) * breset
						),
						loop: bloop.binaryValue
					);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player * bmul *env);
		}.play(Server.default);
	}

	play { arg loop = false;
		^{ var player;
			player = PlayBuf.ar(
				numChannels,
				bufnum,
				BufRateScale.kr(bufnum),
				loop: loop.binaryValue
			);
			loop.not.if(FreeSelfWhenDone.kr(player));
			player * bmul;
		}.play(Server.default);
	}

	//with PlayBuf Gverb
	playGverb { arg  att, sus, rls, mul, trig, rate, start, loop, room, rev, damp, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		broom = room ? broom;
		brev = rev ? brev;
		bdamp = damp ? bdamp;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^{ var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], -4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			player = GVerb.ar(
				player,
				broom, 
				brev, 
				bdamp, 
				0.45, 
				15, 
				-3.dbamp,
				-11.dbamp, 
				-9.dbamp,
				broom, bmul);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player.sum/2 *env);
		}.play(Server.default);
	}


	//with GverbR
	playGverbR { arg  att, sus, rls, mul, trig, rate, start, end, reset, loop, room, rev, damp, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		bend = end ? bend;
		breset = reset ? breset;
		broom = room ? broom;
		brev = rev ? brev;
		bdamp = damp ? bdamp;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^{ var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], -4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = BufRd.ar(
						numChannels,
						bufnum, 
						Phasor.ar(
							btrig, 
							BufRateScale.kr(bufnum) * brt *(-1), 
							BufFrames.kr(bufnum) * bstart, 
							BufFrames.kr(bufnum) * bend, 
							BufFrames.kr(bufnum) * breset
						),
						loop: bloop.binaryValue
					);
			player = GVerb.ar(
				player,
				broom, 
				brev, 
				bdamp, 
				0.45, 
				15, 
				-3.dbamp,
				-11.dbamp, 
				-9.dbamp,
				broom, bmul);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player.sum/2 *env);
		}.play(Server.default);
	}


	//with transGverb
	transGverb { arg  att, sus, rls, mul, trig, rt, rt1, rt2, rt3, rt1Dur, rt2Dur, trDur, vib, start, loop, room, rev, damp, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rt ? brt;
		brt1 = rt1 ? brt1;
		brt2 = rt2 ? brt2;
		brt3 = rt3 ? brt3;
		brt1Dur = rt1Dur ? brt1Dur;
		brt2Dur = rt2Dur ? brt2Dur;
		btrDur = trDur ? btrDur;
		bvib = vib ? bvib;
		bstart = start ? bstart;
		broom = room ? broom;
		brev = rev ? brev;
		bdamp = damp ? bdamp;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		
		
		^{ var player, panlayer, env, env2;
			
			env =  EnvGen.ar(
				Env.new([0, 0.5, 0.8,  0], [batt, bsus, brls], -4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			
			env2 =  EnvGen.ar(
				Env.new([brt1, brt1, brt2, brt2], [brt1Dur, btrDur, brt2Dur+brls], -5, loop, releaseNode: nil), 
				1, 
				doneAction: 0
			);
			
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						SinOsc.kr(bvib*MouseX.kr(0.01, 4)).range(0.99, 1.01)*BufRateScale.kr(bufnum)*1 *env2,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			
			
			
			player = GVerb.ar(
				player,
				broom, 
				brev, 
				bdamp, 
				0.45, 
				15, 
				-3.dbamp,
				-11.dbamp, 
				-9.dbamp,
				broom, bmul);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player.sum *env);
		}.play(Server.default);
	}

	playLP { arg  att, sus, rls, mul, trig, rate, start, loop, pan, out;

		batt = att ? batt;
		bsus = sus ? bsus;
		brls = rls ? brls;
		bmul = mul ? bmul;
		btrig = trig ? btrig;
		brt = rate ? brt;
		bstart = start ? bstart;
		bpan = pan ? bpan;
		bout = out ? bout;
		bloop = loop ? bloop;
		
		^{ var player, panlayer, env;
			
			env =  EnvGen.ar(
				Env.new([0, 1, 0.8,  0], [batt, bsus, brls], 4, loop, releaseNode: nil), 
				1, 
				doneAction: 2
			);
			player = PlayBuf.ar(
						numChannels,
						bufnum, 
						BufRateScale.kr(bufnum) * brt,
						btrig,
						BufFrames.kr(bufnum) * bstart,
						loop: bloop.binaryValue
					);
			//player = Pan2.ar(player, bpan);
			Out.ar(bout, player * bmul *env);
		}.play(Server.default);
	}


	*loadKafs {

		Server.default.doWhenBooted({

		~kontraBass = Kaffer.read(Server.default, "/Users/ari/Media/sounds/kaffers/kontraBass.aiff");		~kaf01 = Kaffer.read(Server.default, "/Users/ari/Media/sounds/sketch/meTsirigma.aiff");
		~kaf02 = Kaffer.read(Server.default, "/Users/ari/Media/sounds/sketch/clarinet2.aiff");
		~kaf03 = Kaffer.read(Server.default, "/Users/ari/Media/sounds/sketch/chord01.aiff");
		~kaf04 = Kaffer.read(Server.default, "/Users/ari/Media/sounds/sketch/breakmetal.aiff");
		});
		
	}



}








/*


-------------  TO DO   ----------------

	(
	{
	var in, chain, n = 4;
		in = WhiteNoise.ar(0.1.dup(n));
		chain = FFT({LocalBuf(2048, 1)}.dup(n), in);
		chain = PV_BrickWall(chain, LFNoise2.kr(2.dup(n))); 
		Splay.ar(IFFT(chain)) // inverse FFT
	}.play;
	)

--------


-----------

//// Multiple Magnitude plots
(
x = { var in, chain, chainB, chainC;
	in = WhiteNoise.ar;
	chain = FFT(b, in);
	PV_Copy(chain, LocalBuf(2048)); // initial spectrum
	chain = PV_RectComb(chain, 20, 0, 0.2);
	PV_Copy(chain, LocalBuf(2048)); // after comb
	2.do({chain = PV_MagSquared(chain)}); 
	PV_Copy(chain, LocalBuf(2048)); // after magsquared
	0.00001 * Pan2.ar(IFFT(chain));
}.play(s);
)


--------------------------------------

*/


