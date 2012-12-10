
AfterEisagogi_InLine{

	* load {


		//:SynthDefs_After_Eisagogi
		//:===================================================================

		SynthDef(\Bota_AfterImport, {
			|
			//Bota
			harm = 1, freq = 90, ampB = 0.1,

			//Bota Envelope
			at = 0.01, rel = 0.2 , shape = -200
			|

			var bota, envB, out = 0;

			//Bota
			bota =


			//Mix.fill(20, {|i| i = i * harm; SinOsc.ar(freq * i) * LFPulse.ar(1, 0, 0.01).range(0, 1)!2});
			Mix.fill(2, {|i| i = i * harm; SinOsc.ar(freq) * LFPulse.ar(1, 0, 0.01).range(0, 1)!2});



			//Envelopes
			envB = EnvGen.ar(Env.perc( at, rel, ampB, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, bota * envB)

		}).add;
		//
		SynthDef(\Noise_AfterImport, {
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

		SynthDef(\Drone_Glitch_AfterImport, {
			|
			//Drone
			freq = 43, ampD = 0.5, fback = 0, ampsin = 0.4, maxdel = 0.4, decTime = 0.2, delt = 0.2, num = 0.4, harm = 1,

			//Glitch
			pfreq = 0.2, range1 = 6, range2 = 0.2, ampG = 1

			//Drone Envelope
			at = 1, rel = 2, shape = -4
			|


			var sourceD, out = 0, envD;

			sourceD =

			//Drone
			AllpassC.ar(								/*WARNING kr - ar*/				Mix.fill(2, { |i| i = i * harm; SinOscFB.ar([freq]*i /** LFNoise1.ar(100).range(1, 2.1)*/, fback, 					ampsin).tanh}),
				maxdel, delt, decTime, ampD)
			+


			//Glitch
			Pan2.ar(LFPulse.ar(LFPulse.kr(pfreq).range(range1, range2),
				LFNoise0.kr(10).range(0, pi), 0.0001, 0.4),
				PinkNoise.kr(10).range(-1, 1), ampG);


			envD = EnvGen.ar(Env.perc( at, rel, 1, shape), doneAction:2)!2;

			Out.ar(out, sourceD * envD)

		}).add;
		//

		SynthDef(\Noise2_AfterImport, {
			|

			//Drone
			freq = 5, ampN = 0.5,

			//All
			harm = 1,

			//Noise Envelope
			at = 1, rel = 2 , shape = -4

			|

			var sourceN, envN, out = 0;

			//Drone
			sourceN =
			HenonN.ar(
				SampleRate.ir/freq,
				PinkNoise.kr(10, 1, 1.2),
				ClipNoise.kr(100, 1, 0.15),
				SinOsc.kr(0.01, 0.1, 0.1)

			);

			//Envelopes
			envN = EnvGen.ar(Env.perc(at, rel, 1, shape), /*levelscale: 1, timescale: 1,*/ doneAction:2)!2;

			Out.ar(out, sourceN * envN * ampN)

		}).add;
		//:

		//:	PATTERNS_AFTER_EISAGOGI
		//===========================================================================


		//:	(1) - Standart
		//===========================================================================


		Pdef(\standart, Ppar([

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				//\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				//\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)

			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.23, 0, 0, 0], 0.8), 1], inf), //to 0.6 xamiloma se ola
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)


			);

			], 1)
		);

		Pdef(\standart).quant = 0;


		//:	(2) - Clack
		//===========================================================================


		Pdef(\clack, Ppar([

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e4], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e4], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.0 ,0, 0, 0], 1), 0.6], inf), //Xoris Psilo ixo (0.0)
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)


			);

			], 1)
		);


		Pdef(\clack).quant = 0;

		//:	(3) - With_Bota
		//===========================================================================

		Pdef(\with_Bota_1, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 0, 0, 0, 0, 0], 1), 1]/1.3 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.02], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.25 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			);


			], 1)
		);


		Pdef(\with_Bota_2, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 0, 0, 0, 0, 1], 1), 1]/1.3 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.02], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.25 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			);


			], 1)
		);


		//:Centrel_Sequencer_With_Bota_1_2
		//=============================

		Pdef(\with_Bota_1_2,
			Pseq([
				Pdef(\with_Bota_1), Pdef(\with_Bota_2)
			], 1)
		);

		Pdef(\with_Bota_1_2).quant = 0;
		//Pdef(\with_Bota_1_2).play;



		//:	(4) - With_Noise2
		//===========================================================================

		Pdef(\with_Noise2, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 0, 0, 0, 0, 0], 1), 1]/1.4 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.2], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.25 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			),

			Pbind(
				\instrument, \Noise2_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0]*2.3, inf),
				\freq, Pseq([5, 150, 5, 5, 5, 75, 0.1, 5, 5, 5, 150], inf),
				//Noise2 Envelope
				\at, Pseq([ 0.8, 0.1, 0.8, 0.2, 0.2, 0.8, 0.2, 0.8, 0.8, 0.08, 0.8], inf),
				\rel, Pseq([Pseq([0.0002], 10)], inf),
				\shape, Pseq([-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, 3], inf)

			);


			], 1)
		);


		//:Centrel_Sequencer_With_Noise2
		//=============================

		Pdef(\with_Noise2).quant = 0;
		//Pdef(\with_Noise2).play;


		//:	(5) - With_Big_Noise2
		//===========================================================================

		Pdef(\with_Big_Noise2, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 0, 0, 0, 0, 0], 1), 1]/1.4 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.2], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.26 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			),

			Pbind(
				\instrument, \Noise2_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0]/2.3, inf),
				\freq, Pseq([5, 150, 5, 5, 5, 75, 0.1, 5, 5, 5, 150], inf),
				//Noise2 Envelope
				\at, Pseq([ 0.8, 0.1, 0.8, 0.2, 0.8, 0.8, 0.8, 0.8, 0.8, 0.08, 0.8], inf),
				\rel, Pseq([Pseq([0.0002], 10)], inf),
				\shape, Pseq([-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, 3], inf)

			);


			], 1)
		);

		//:Centrel_Sequencer_With_Big_Noise2
		//=============================

		Pdef(\with_Big_Noise2).quant = 0;
		//Pdef(\with_Big_Noise2).play;



		//:	(6) - With_More_Bota
		//===========================================================================

		Pdef(\with_More_Bota_1, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 1, 0, 1, 0, 1], 1), 1]/2 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.02], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.24 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [[~fD7, ~f_7], [~e_7, ~c_7]], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			),

			Pbind(
				\instrument, \Noise2_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0, 0, 0, 0, 1, 0, 1, 0, 1, 10, 0]/1.3, inf),
				\freq, Pseq([5, 150, 5, 5, 5, 75, 0.1, 5, 5, 5, 150], inf),
				//Noise2 Envelope
				\at, Pseq([ 0.8, 0.1, 0.08, 0.2, 0.2, 0.8, 0.2, 0.8, 0.5, 0.08, 0.8], inf),
				\rel, Pseq([Pseq([0.0002], 11)], inf),
				\shape, Pseq([-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, 3], inf)

			);


			], 1)
		);


		Pdef(\with_More_Bota_2, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([0.01], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([1, 1, 1, 1, 0, 1], 1), 1]/2 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.02], inf),
				\shape, Pseq([-20], inf)

			),

			Pbind(
				\instrument, \Noise_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*50, inf),
				\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4/*, 1e40*/], 1), Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4/*, 1e40*/], 1), Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.3 ,0, 0, 0], 1), 0.6], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
				\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [[~fD7, ~f_7], [~e_7, ~c_7]], 4, 4, 4], 1), ~fD2], inf),
				//Drone Envelope
				\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
				\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
				\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

			),

			Pbind(
				\instrument, \Noise2_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0, 0, 0, 0, 1, 0, 1, 0, 0, 10, 0]/1.3, inf),
				\freq, Pseq([5, 150, 5, 5, 5, 75, 0.1, 5, 5, 5, 150], inf),
				//Noise2 Envelope
				\at, Pseq([ 0.8, 0.1, 0.08, 0.2, 0.2, 0.8, 0.2, 0.8, 0.5, 0.08, 0.8], inf),
				\rel, Pseq([Pseq([0.0002], 11)], inf),
				\shape, Pseq([-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, 3], inf)

			);


			], 1)
		);


		//:Centrel_Sequencer_With_More_Bota_1_2
		//=============================

		Pdef(\with_More_Bota_1_2,
			Pseq([
				Pdef(\with_More_Bota_1), Pdef(\with_More_Bota_2)
			], 1)
		);
		//:
		Pdef(\with_More_Bota_1_2).quant = 0;
		//Pdef(\with_More_Bota_1_2).play;



		//:	(7) - High_Alone
		//===========================================================================

		Pdef(\high_Alone, Ppar([

			Pbind(
				\instrument, \Bota_AfterImport,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([1, 0, 0, 0, Pseq([0, 1, 0, 1, 0, 1], 1), 1]/4*0 , inf),
				\freq, Pseq([~fD2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.2], inf),
				\shape, Pseq([4], inf)

			),




			Pbind(
				\instrument, \Drone_Glitch_AfterImport,
				//All
				\dur, Pseq([(1/3) + (1/2) + (1/3) + (1/3) + (1/6 * 6) + (1/6)]*2, 1),
				\ampD, Pseq([0.2], inf), //!!!Drone amp
				\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
				\harm, Pseq([80], inf),
				\freq, Pseq([ [[~fD7, ~f_7], [~e_2, ~c_7]] ], inf),
				//Drone Envelope
				\at, Pseq([(1/3) + (1/2) + (1/3) + (1/3) + (1/6 * 6) + (1/6)]*2, inf),
				\rel, Pseq([(1/3) + (1/2) + (1/3) + (1/3) + (1/6 * 6) + (1/6)]/100, inf),
				\shape, Pseq([-4], inf)

			)



			], 1 )
		);

		//:Centrel_Sequencer_High_Alone
		//=============================

		Pdef(\high_Alone).quant = 0;
		//Pdef(\high_Alone).play;








	}


}

