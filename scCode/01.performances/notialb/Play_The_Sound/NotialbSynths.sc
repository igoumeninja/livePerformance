

NotialbSynths{

	*load{	var b;

		//:SynthDefs
		//:===================================================================

		//:Eisagogi_SynthDefs
		//:========================

		b = Buffer.read(Server.default, "/Users/ari/Library/Application\ Support/SuperCollider/Extensions/notialb/Play_The_Sound/Import.wav");

		//:
		SynthDef(\buf, {|startp = 1, ipod = 0, pos = 0|
			var source, env, sec, bufnum = b;
			sec = BufDur.ir(b);
			pos = sec * ipod * 44100;
			source = PlayBuf.ar(1, b, BufRateScale.kr(b), trigger: 0, startPos: pos, loop:0, doneAction:2);
			//env = EnvGen.ar(Env.perc(1, sec, 1, -4), doneAction:2);
			//SendReply.kr(Impulse.kr(10), \pare, pos);
			Out.ar([0,1], source)
		}).add;

		//:The_Extra_Sounds_(Live set!!!)

		SynthDef(\chaos1, {
			| amp = 0.01, freq = 1, hilo = 1 |

			Out.ar([0,1],
				HenonN.ar(
					SampleRate.ir/freq,
					SinOsc.kr(0.1, 0.2, 1.2),
					PinkNoise.kr(hilo),
					WhiteNoise.kr(0.2),
					SinOsc.kr(10)

			) * amp  )
		}).send(Server.default);


		//:After_Eisagogi_SynthDefs
		//:========================

		SynthDef(\Noise_Eisagogi, {
			|
			//Noise
			freq = 5, ampN = 0.5, ttur = 1e40, nMul = 1e40, nAdd = 1e40,
			//Noise Envelope
			at = 1, rel = 2 , shape = -4

			|

			var sourceN, envN, out = 0;

			//Drone
			sourceN =

			LinCongN.ar(
				LFNoise2.kr(0.5, nMul, nAdd), //(1e40, 1e40) - fisima . 1e4 - ligo pio skliro .  1 mono glitch
				Dust.kr(2, 0.5, 10.4),
				SinOsc.kr(1, 0.1, 10.1),
				SinOsc.kr(2)
			) * LFPulse.kr(1000).range(0, 1);


			//Envelopes
			envN = EnvGen.ar(Env.perc(at, rel, 1, shape),/*, levelscale: 1, timescale: 1,*/ doneAction:2)!2;

			Out.ar(out, sourceN * envN * ampN )

		}).add;

		//

		SynthDef(\Drone_Glitch_Eisagogi, {
			|
			//Drone
			freq = 43, ampD = 0.5, fback = 0, ampsin = 0.4,
			maxdel = 0.4, decTime = 0.2, delt = 0.2, num = 0.4, harm = 1,

			//Glitch
			pfreq = 0.2, range1 = 6, range2 = 0.2, ampG = 1

			//Drone Envelope
			at = 1, rel = 2, shape = -4
			|


			var sourceD, out = 0, envD;

			sourceD =

			//Drone
			AllpassC.ar(								/*WARNING kr - ar*/
				Mix.fill(2, { |i| i = i * harm; SinOscFB.ar([freq/*,freq+num*/]*i/**LFNoise1.ar(1).range	(1, 2.1)*/, fback, ampsin).tanh}),

				maxdel, delt, decTime, ampD)
			+


			//Glitch
			Pan2.ar(LFPulse.ar(LFPulse.kr(pfreq).range(range1, range2),
				LFNoise0.kr(10).range(0, pi), 0.0001, 0.4),
				PinkNoise.kr(10).range(-1, 1), ampG);


			envD = EnvGen.ar(Env.perc( at, rel, 1, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, sourceD * envD)

		}).add;


		//:Meros_1_SynthDefs
		//:========================

		//LFPulse freq : 1

		SynthDef(\Bota_Meros_1_1, {
			|
			//Bota
			harm = 1, freq = 90, ampB = 0.1,

			//Bota Envelope
			at = 0.01, rel = 0.2 , shape = -200
			|

			var bota, envB, out = 0;

			//Bota
			bota =

			//SinOsc.ar(90)*LFPulse.ar(1, 0, 0.01).range(0, 1)!2
			//
			Mix.fill(2, {|i| i = i * harm; SinOsc.ar(freq) * LFPulse.ar(1, 0, 0.01).range(0, 1)!2}); //Dokimi Pio poli freq sto LFPUlse 2 GAMATO!! kai 0



			//Envelopes
			envB = EnvGen.ar(Env.perc( at, rel, ampB, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, bota * envB)

		}).add;

		////LFPulse freq : 2

		SynthDef(\Bota_Meros_1_2, {
			|
			//Bota
			harm = 1, freq = 90, ampB = 0.1,

			//Bota Envelope
			at = 0.01, rel = 0.2 , shape = -200
			|

			var bota, envB, out = 0;

			//Bota
			bota =

			//SinOsc.ar(90)*LFPulse.ar(1, 0, 0.01).range(0, 1)!2
			//
			Mix.fill(2, {|i| i = i * harm; SinOsc.ar(freq) * LFPulse.ar(2, 0, 0.01).range(0, 1)!2}); //Dokimi Pio poli freq sto LFPUlse 2 GAMATO!! kai 0



			//Envelopes
			envB = EnvGen.ar(Env.perc( at, rel, ampB, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, bota * envB)

		}).add;

		//

		SynthDef(\Noise_Meros_1, {
			|

			//Noise
			freq = 5, ampN = 0.5, ttur = 1e40, nMul = 1e40, nAdd = 1e40,

			//Noise Envelope
			at = 1, rel = 2 , shape = -4

			|

			var sourceN, envN, out = 0;

			//Drone
			sourceN =

			LinCongN.ar(
				LFNoise2.kr(0.5, nMul, nAdd), //(1e40, 1e40) - fisima . 1e4 - ligo pio skliro .  1 mono glitch
				Dust.kr(2, 0.5, 10.4),
				SinOsc.kr(1, 0.1, 10.1),
				SinOsc.kr(2)
			);

			//Envelopes
			envN = EnvGen.ar(Env.perc(at, rel, 1, shape),/*, levelscale: 1, timescale: 1,*/ doneAction:2)!2;

			Out.ar(out, sourceN * envN * ampN)

		}).add;

		//

		SynthDef(\Drone_Glitch_Meros_1, {
			|
			//Drone
			freq = 43, ampD = 0.5, fback = 0, ampsin = 0.4,
			maxdel = 0.4, decTime = 0.2, delt = 0.2, num = 0.4, harm = 1,

			//Glitch
			pfreq = 0.2, range1 = 6, range2 = 0.2, ampG = 1

			//Drone Envelope
			at = 1, rel = 2, shape = -4
			|


			var sourceD, out = 0, envD;

			sourceD =

			//Drone
			AllpassC.ar(								/*WARNING kr - ar*/
				Mix.fill(2, { |i| i = i * harm; SinOscFB.ar([freq/*,freq+num*/]*i/**LFNoise1.ar(1).range	(1, 2.1)*/, fback, ampsin).tanh}),

				maxdel, delt, decTime, ampD)
			+


			//Glitch
			Pan2.ar(LFPulse.ar(LFPulse.kr(pfreq).range(range1, range2),
				LFNoise0.kr(10).range(0, pi), 0.0001, 0.4),
				PinkNoise.kr(10).range(-1, 1), ampG);


			envD = EnvGen.ar(Env.perc( at, rel, 1, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, sourceD * envD)

		}).add;


	}

}


