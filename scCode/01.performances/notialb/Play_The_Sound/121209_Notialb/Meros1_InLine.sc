
Meros1_InLine {

	* load {


		//:SynthDefs_Meros_1o
		//:===================================================================
		//TODO Na kopso tags apo tin Bota
		//freq : 1

		SynthDef(\Bota1_Meros_1, {
			|
			//Bota
			harm = 1, freq = 90, ampB = 0.1,

			//Bota Envelope
			at = 0.01, rel = 0.2 , shape = -200
			|

			var bota, envB, out = 0;

			//Bota
			bota =


			Mix.fill(2, {|i| i = i * harm; SinOsc.ar(freq) * LFPulse.ar(1, 0, 0.01).range(0, 1)!2});


			//Envelopes
			envB = EnvGen.ar(Env.perc( at, rel, ampB, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, bota * envB)

		}).add;

		//freq:2

		SynthDef(\Bota2_Meros_1, {
			|
			//Bota
			harm = 1, freq = 90, ampB = 0.1,

			//Bota Envelope
			at = 0.01, rel = 0.2 , shape = -200
			|

			var bota, envB, out = 0;

			//Bota
			bota =


			Mix.fill(2, {|i| i = i * harm; SinOsc.ar(freq) * LFPulse.ar(2, 0, 0.01).range(0, 1)!2});


			//Envelopes
			envB = EnvGen.ar(Env.perc( at, rel, ampB, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, bota * envB)

		}).add;

		//Noises

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

		SynthDef(\Noise3_Meros_1, {
			|

			//Noise
			freq = 5, ampN = 0.5, ttur = 1e40, nMul = 1e40, nAdd = 1e40,

			//Noise Envelope
			at = 1, rel = 2 , shape = -4

			|

			var sourceN, envN, out = 0;

			//Drone

			sourceN =

			BBandPass.ar(

				HenonN.ar(
					SampleRate.ir/freq,
					SinOsc.kr(0.1, 0.2, 1.2),
					PinkNoise.kr(1, 0.15, 0.15),//def hilo 1
					WhiteNoise.kr(1),
					SinOsc.kr(10))/* * LFPulse.ar(10).range(1, 1.4)*/,

				1500, 1, 5);

			//Envelopes
			envN = EnvGen.ar(Env.perc(at, rel, 1, shape),/*, levelscale: 1, timescale: 1,*/ doneAction:2)!2;

			Out.ar(out, sourceN * envN * ampN *0.1)

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
				Mix.fill(2, { |i| i = i * harm;
					SinOscFB.ar([freq/*,freq+num*/]*i/**LFNoise1.ar(1).range			(1, 2.1)*/, fback, ampsin).tanh}),
				maxdel, delt, decTime, ampD)
			+


			//Glitch
			Pan2.ar(LFPulse.ar(LFPulse.kr(pfreq).range(range1, range2),
				LFNoise0.kr(10).range(0, pi), 0.0001, 0.4),
				PinkNoise.kr(10).range(-1, 1), ampG);


			envD = EnvGen.ar(Env.perc( at, rel, 1, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out, sourceD * envD)

		}).add;





		//Maxis

		SynthDef(\maxi1_Meros_1, {
			|
			//Maxi

			//Drone Envelope
			at = 1, dec = 2, ss = 1, rel = 0.1,
			att = 1, dect = 1, relt = 1,
			fback = 0,
			ampM = 1
			|

			var sourceM, out = 0, envM;

			sourceM = SinOscFB.ar([

				~f_4 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_4, [att, dect, relt], 1, 3, 0), 				doneAction:2),

				~f_5 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_5, [att/1, dect, relt/1], 1, 3, 0), 				doneAction:2)

			], fback, 0.1);


			Out.ar(out/*Pan*/, sourceM * ampM)

		}).add;

		//

		SynthDef(\maxi2_Meros_1, {
			|
			//Maxi

			//Drone Envelope
			at = 1, dec = 2, ss = 1, rel = 0.1,
			att = 1, dect = 1, relt = 1,
			fback = 0,
			ampM = 1
			|

			var sourceM, out = 0, envM;

			sourceM = SinOscFB.ar([

				~f_4 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_4, [att, dect, relt], 1, 3, 0), 				doneAction:2),

				~f_5 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_5, [att/2, dect, relt/2], 1, 3, 0), 				doneAction:2)

			], fback, 0.1);


			//envM = EnvGen.ar(Env.perc( at, rel, 1, shape), doneAction:2)!2; //rel 1 nice

			Out.ar(out/*Pan*/, sourceM * ampM)

		}).add;

		//

		SynthDef(\maxi3_Meros_1, {
			|
			//Maxi

			//Drone Envelope

			at = 1, dec = 2, ss = 1, rel = 0.1,

			att = 1, dect = 1, relt = 1,

			fback = 0,

			ampM = 1
			|

			var sourceM, out = 0, envM;

			sourceM = Saw.ar([

				~f_2 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_1, [att, dect, relt]/4, 1, 3, 0), 				doneAction:2),

				~f_1 * EnvGen.kr(Env([at, dec, ss, rel] * ~f_2, [att/1, dect, relt/1]/3, 1, 3, 0), 				doneAction:2)

			], fback, 0.1);


			Out.ar(out/*Pan*/, sourceM * ampM)

		}).add;

		//

		SynthDef(\maxi4_Meros_1, {
			|
			//Maxi

			//Drone Envelope

			at = 1, dec = 2, ss = 1, rel = 0.1,

			att = 1, dect = 1, relt = 1,

			fback = 0,

			ampM = 1
			|

			var sourceM, out = 0, envM;

			sourceM =

			//BBandPass.ar(

			Dust.ar([

				~e_2 * EnvGen.kr(Env([at, dec, ss, rel] * ~e_1, [att, dect, relt]/4, 1, 3, 0), 				doneAction:2),

				~e_1 * EnvGen.kr(Env([at, dec, ss, rel] * ~e_2, [att/1, dect, relt/1]/3, 1, 3, 0), 				doneAction:2)

			], fback, 0.1) * LFPulse.kr(20).range(0.2, 1);

			//	5000, 1, 1.6);

			Out.ar(out/*Pan*/, sourceM * ampM)

		}).add;

		//:
		//VARIATIONS_Meros_1o (Krataei ? Metra)
		//===================================================================
		//Notes: needs Meros_1 EQ
		//========================================

		//:	(1) - TEST
		//===========================================================================
		Pdef(\no_InFast_OnlyNoise_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([0], 6), 0]/1.5, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, 20, 20, 20, 20, 20, 20, -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		Pdef(\no_InFast_OnlyNoise_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([0, 0, 0, 0, Pseq([3, 3, 3, 3, 3, 3], 1), 3]/1.5, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_2, ~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0, 0, 0, 0, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		Pdef(\no_InFast_OnlyNoise_1).quant = 0;
		Pdef(\no_InFast_OnlyNoise_2).quant = 0;

		//:	(1) - Classic_1_2
		//===========================================================================

		Pdef(\classic_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/1.4, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.13, 0.13, 0.13, 0.13, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//

		Pdef(\classic_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 0, 3, Pseq([3, 3, 0, 3, 3, 3], 1), 0]/1.3, inf), //Evgala merikes fores tin Bota
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1, 0.1, 0.2, 0.2, 0.2, 0.1], 1), 0.1]*1.2, inf), //Megalosa 		tin entsi sto Noise
				\nMul, Pseq([0.001, 0.001, 0.001, 0.001, Pseq([1e40], 6), 1e40], inf),
				\nAdd, Pseq([1e4, 1e4, 1e4, 1e4, Pseq([1e40], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.2]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//:Centrel_Sequencer_Classic_1_2
		//=============================

		Pdef(\classic_1_2,
			Pseq([
				Pdef(\classic_1), Pdef(\classic_2)
			], 1)
		);

		Pdef(\classic_1_2).quant = 0;
		//Pdef(\classic_1_2).play;



		//Notes : In Variations 2 - (aeroma) Sta grigora No-Bota
		//=========================	1-2_Meros_1.2


		//:	(2) - No-In-Fast
		//===========================================================================


		Pdef(\no_InFast_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([0], 6), 3]/1.5, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, 20, 20, 20, 20, 20, 20, -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);


		Pdef(\no_InFast_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([0], 6), 3]/1.5, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, 20, 20, 20, 20, 20, 20, -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0, 0.2, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e4, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e4, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//:Centrel_Sequencer - no_Bota_InFast_1_2
		//=============================

		Pdef(\no_InFast_1_2,
			Pseq([
				Pdef(\no_InFast_1), Pdef(\no_InFast_2)
			], 1)
		);
		//:
		Pdef(\no_InFast_1_2).quant = 0;
		//Pdef(\no_InFast_1_2).play;



		//Notes : In Variations 2 - (aeroma) Sta grigora No-Bota
		//=========================	1-2_Meros_1.2

		//:	(3) - High-Freq-InFast
		//===========================================================================

		Pdef(\high_Freq_InFast_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([0, 3, 0, 3, 0, 3]*6, 1), 3]/2, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, Pseq([20], 6), -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);


		Pdef(\high_Freq_InFast_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 0, Pseq([3, 0, 0, 3, 3, 3]*6, 1), 3]/2, inf), //Sta grigora ampB [3]
				\freq, Pseq([~f_2, ~f_2, ~f_2, ~f_2, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_8, ~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
				\shape, Pseq([-4, -4, -4, -4, 20, 20, 20, 20, 20, 20, -4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.2, 0, Pseq([0], 6), 0]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e4, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e4, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//:Centrel_Sequencer - high_Freq_InFast_1_2
		//=============================

		Pdef(\high_Freq_InFast_1_2,
			Pseq([
				Pdef(\high_Freq_InFast_1), Pdef(\high_Freq_InFast_2)
			], 1)
		);
		//:
		Pdef(\high_Freq_InFast_1_2).quant = 0;
		//Pdef(\high_Freq_InFast_1_2).play;




		//:	(4) - Aeromata se Tags tou noise
		//===========================================================================

		//1

		Pdef(\classic2_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 3, 3, 3, 3], 1), 3]/1.5, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.2, 0.1, Pseq([0.1, 0.1, 0.1, 0.1, 0.1, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20 ], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0.7, Pseq([0], 6), 0]/2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 0.01,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),


			], 1)
		);

		//2

		Pdef(\classic2_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 0, 0, 0, 3], 1), 3]/1.5, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0, 0, 0.1, 0.1, 0.1, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),


			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/1.3, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 0.1,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),

			Pbind(
				\instrument, \maxi3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([0, 0, 0, 0, Pseq([0, 1, 0, 0, 1, 0], 1), 0.1]/5, inf), //def xoris *1.4
				\freq, 0.1,
				\fback, Pseq([2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2]),
				//Noise Envelope
				\att, Pseq([0.01], inf),
				\relt, Pseq([0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			)

			], 1)
		);


		//Pdef(\classic2_2).play;

		// - Sto 1 ---Evala - 1

		//3

		Pdef(\classic2_3, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 3, 3, 3, 0], 1), 3]/1.4, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.2, 0.1, 0.2, 0.1, Pseq([0, 0, 0.1, 0.1, 0, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([0.001, 0.001, 0.001, 0.001, Pseq([1e40], 6), 1e40], inf),
				\nAdd, Pseq([1e4, 1e4, 1e4, 1e4, Pseq([1e40], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/3, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 200,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),

			Pbind(
				\instrument, \maxi4_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([0, 1, 0, 0, Pseq([1, 0, 0, 0, 0, 1], 1), 0.1]/2, inf), //def xoris *1.4
				\freq, 0.1,
				\fback, Pseq([2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2]),
				//Noise Envelope
				\att, Pseq([0.001], inf),
				\relt, Pseq([0.02], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([10], inf)
			)

			], 1)
		);



		//4

		Pdef(\classic2_4, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 0, 3, Pseq([3, 0, 3, 3, 3, 0], 1), 0]/1.2, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.2, Pseq([0.1, 0, 0.1, 0, 0.1, 0], 1), 1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e4, 1e40, 1e4, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e4, 1e40, 1e40, 1e4, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]*2], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/1.5, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 0.1,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),


			], 1)
		);

		//5

		Pdef(\classic2_5, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 3, 3, 3, 3], 1), 3]/1.5, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.2, 0.1, Pseq([0.1, 0.1, 0.1, 0.1, 0.1, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20 ], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.6, 0, 0, 0.7, Pseq([0], 6), 0]/3, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 0.01,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),


			], 1)
		);

		//6

		Pdef(\classic2_6, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 0, 0, 0, 3], 1), 3]/1.5, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0, 0, 0.1, 0.1, 0.1, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),


			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 0.1,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),

			Pbind(
				\instrument, \maxi3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([0, 0, 0, 0, Pseq([0, 1, 0, 0, 1, 0], 1), 0.1]/5, inf), //def xoris *1.4
				\freq, 0.1,
				\fback, Pseq([2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2]),
				//Noise Envelope
				\att, Pseq([0.01], inf),
				\relt, Pseq([0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			)

			], 1)
		);


		//7

		Pdef(\classic2_7, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 3, 3, 3, 3, 0], 1), 3]/1.4, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.2, 0.1, 0.2, 0.1, Pseq([0, 0, 0.1, 0.1, 0, 0.1], 1), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([0.001, 0.001, 0.001, 0.001, Pseq([1e40], 6), 1e40], inf),
				\nAdd, Pseq([1e4, 1e4, 1e4, 1e4, Pseq([1e40], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/3, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 200,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),

			Pbind(
				\instrument, \maxi4_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 1, 1, 1, Pseq([0, 0, 0, 0, 0, 0], 1), 0.1]/3, inf), //def xoris *1.4
				\freq, 0.1,
				\fback, Pseq([2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2]),
				//Noise Envelope
				\att, Pseq([0.001], inf),
				\relt, Pseq([0.02], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([10], inf)
			)

			], 1)
		);



		//8

		Pdef(\classic2_8, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 0, 3, Pseq([3, 0, 3, 3, 3, 0], 1), 3]/1.2, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.2, Pseq([0.1, 0, 0.1, 0, 0.1, 0], 1), 1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e4, 1e40, 1e4, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e4, 1e40, 1e40, 1e4, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.25]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]*2], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \Noise3_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.7, 0, 0, 0, Pseq([0], 6), 0]/3, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				\freq, 5,
				//Noise Envelope
				\at, Pseq([2.5], inf),
				\rel, Pseq([1], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-1], inf)
			),


			], 1)
		);


		//:Centrel_Sequencer - classic2_1_2_3_4
		//=============================

		Pdef(\classic2_1_2_3_4,
			Pseq([

				Pdef(\classic2_1), Pdef(\classic2_2), Pdef(\classic2_3), Pdef(\classic2_4),
				Pdef(\classic2_5), Pdef(\classic2_6), Pdef(\classic2_7), Pdef(\classic2_8)

			], 1)
		);

		//:

		Pdef(\classic2_1_2_3_4).quant = 0;
		Pdef(\classic2_1).quant = 0;
		Pdef(\classic2_2).quant = 0;
		Pdef(\classic2_3).quant = 0;
		Pdef(\classic2_4).quant = 0;
		Pdef(\classic2_5).quant = 0;
		Pdef(\classic2_6).quant = 0;
		Pdef(\classic2_7).quant = 0;
		Pdef(\classic2_8).quant = 0;
		//Pdef(\classic2_1_2_3_4).play;



		//:	(5) - No - Noise    - (2 Fores - 1 kiklo)
		//===========================================================================

		Pdef(\no_Noise_1, Ppar([

			Pbind(
				\instrument, \Bota2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([0.1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 0, 3, 3, 3, 0], 1), 3], inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01], inf),
				\rel, Pseq([0.01/*, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02*/], inf),
				\shape, Pseq([-4], inf)

			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0]/1.5, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		Pdef(\no_Noise_2, Ppar([

			Pbind(
				\instrument, \Bota2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([0.1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3, 0, 0, 3, 3, 3], 1), 3], inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.004], 6), 0.004], inf),
				\rel, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.004], 6), 0.004], inf),
				\shape, Pseq([-4], inf)

			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0]/1.5, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		Pdef(\no_Noise_3, Ppar([

			Pbind(
				\instrument, \Bota2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([0.1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([0, 3, 3, 3, 3, 3], 1), 3], inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.0042], 6), 0.004], inf),
				\rel, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.0042], 6), 0.004], inf),
				\shape, Pseq([-4], inf)

			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.24]/1.5, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/2], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//:Centrel_Sequencer - no_Noise
		//=============================

		Pdef(\no_Noise,
			Pseq([

				Pdef(\no_Noise_1), Pdef(\no_Noise_2)

			], 1)
		);
		//:
		Pdef(\no_Noise).quant = 0;
		Pdef(\no_Noise_3).quant = 0;
		//Pdef(\no_Noise).play;


		//:	(6) - High - Env
		//===========================================================================

		Pdef(\high_Env, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/1.6, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1], inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 0.001, 1e40, Pseq([0.001], 6), 1], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 0.3], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.8, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200, -200, -200, -200, Pseq([-200], 6), -200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			)



			], 1)
		);

		//:Centrel_Sequencer - no_Noise
		//=============================

		/*Pdef(\high_Env,
		Pseq([

		Pdef(\high_Env)

		], 1)
		);	*/
		//:
		Pdef(\high_Env).quant = 0;
		//Pdef(\high_Env).play;


		//:	(7) - SinFB - Maxi
		//===========================================================================


		// - /1
		Pdef(\sinFB_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1], inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 1.5, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 1.6, 0.01], 1), 0.01]/10, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 5, 0.01], 1), 0.01]/10, inf),
				\relt, Pseq([0.1], inf),

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		// - /1

		Pdef(\sinFB_2, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1]*1.6, inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 1.5, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 1.6, 0.01], 1), 0.01]/1, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 5, 0.01], 1), 0.01]/10, inf),
				\relt, Pseq([0.1], inf),

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		// - /2

		Pdef(\sinFB_3, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1], inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 1.5, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 8.6, Pseq([0.01, 0.01, 0.01, 0.01, 1.6, 0.01], 1), 0.01]/1, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 2, Pseq([0.02, 0.01, 0.02, 0.01, 5, 0.01], 1), 0.01]/10, inf),
				\relt, Pseq([0.1], inf),

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 0.01, 0.01], 1), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02, 0.02, 0.02, 0.02, 0.02, 0.02], 1), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		// - /1

		Pdef(\sinFB_4, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3], inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1], inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 2, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 1.6, 0.01], 1), 0.01]/10, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 6, 0.01], 1), 0.01]/1, inf),
				\relt, Pseq([0.1], inf),

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		Pdef(\sinFB_5, Ppar([


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([0, 1, 0, 0, Pseq([0, 1, 0, 0, 1, 0], 1), 1], inf), //\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1], inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 2, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 0.01, 0.01], 1), 0.01]/10, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 0.02, 0.01], 1), 0.01]/1, inf),
				\relt, Pseq([0.2], inf),  //def 0.1

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		Pdef(\sinFB_6, Ppar([


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi2_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 1, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1], inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 2, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 0.01, 0.01], 1), 0.01]/10, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 0.02, 0.01], 1), 0.01]/1, inf),
				\relt, Pseq([0.2], inf),  //def 0.1

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);

		//Without Bota

		Pdef(\sinFB_2_1, Ppar([

			Pbind(
				\instrument, \Bota1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\harm, Pseq([1], inf),
				\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2*0, inf), //def /4
				\freq, Pseq([~f_2], inf),
				//Bota Envelope
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
				\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
				\shape, Pseq([-4], inf)

			),

			Pbind(
				\instrument, \Noise_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]/1.2*0, inf), //def xoris *1.4
				\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
				\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
				//Noise Envelope
				\at, Pseq([0.05, 0.05, 0.05, 0.05, 0.05, 0.05], inf),
				\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
				//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
				\shape, Pseq([-200], inf)
			),


			Pbind(
				\instrument, \Drone_Glitch_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
				\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0]*1.3, inf),  //def /2
				\ampG, Pseq([0], inf),
				//\harm, Pseq([0.5], inf),
				\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
				\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
				//Drone Envelope
				\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
				\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
				\shape, Pseq([4], inf)

			),

			Pbind(
				\instrument, \maxi1_Meros_1,
				//All
				\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
				\ampM, Pseq([1, 0, 0, 1, Pseq([0, 1, 1, 1, 1, 0], 1), 1]/1.2, inf),
				\fback, Pseq([1.3, 0, 2, 0, Pseq([1, 1, 1, 1, 2, 1], 1), 0], inf),

				//Maxi Envelope Times
				\att, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01, 0.01, 0.01, 0.01, 1.6, 0.01], 1), 0.01]/1, inf),
				\dect, Pseq([0.02, 0.02, 0.01, 0.02, Pseq([0.02, 0.01, 0.02, 0.01, 5, 0.01], 1), 0.01]/10, inf),
				\relt, Pseq([0.1], inf),

				//Maxi Envelope Shape
				\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01]/100, inf),
				\dec, Pseq([0.02, 0.02, 0.01, 0.01, Pseq([0.02], 6), 0.01]/100, inf),
				\ss, Pseq([0.1], inf),

			);


			], 1)
		);


		//:Centrel_Sequencer - no_Noise
		//=============================

		Pdef(\sinFB,

			Pseq([
				//TODO: Na ta valo se seira (einai oraia apo to xosimo na mpei to 3)
				Pdef(\sinFB_1), Pdef(\sinFB_2), Pdef(\sinFB_3), Pdef(\sinFB_4)

			], 1)
		);
		//:
		Pdef(\sinFB).quant = 0;
		Pdef(\sinFB_1).quant = 0;
		Pdef(\sinFB_2).quant = 0;
		Pdef(\sinFB_3).quant = 0;
		Pdef(\sinFB_4).quant = 0;
		Pdef(\sinFB_5).quant = 0;
		Pdef(\sinFB_2_1).quant = 0;
		//Pdef(\sinFB).play;



	}




}



/*
//---------------------------------------------------------------------------TEST
// - Sto 3

Pdef(\classic2_3, Ppar([

Pbind(
\instrument, \Bota1_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\harm, Pseq([1], inf),
\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
\freq, Pseq([~f_2], inf),
//Bota Envelope
\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
\shape, Pseq([-4], inf)

),

Pbind(
\instrument, \Noise_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\ampN, Pseq([0.1, 0.1, 0.2, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
\nAdd, Pseq([1e40, 1e40, 1e4, 1e40, Pseq([1e4], 6), 1e40], inf),
//Noise Envelope
\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
\shape, Pseq([-200], inf)
),


Pbind(
\instrument, \Drone_Glitch_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
\ampG, Pseq([0], inf),
//\harm, Pseq([0.5], inf),
\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
//Drone Envelope
\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
\shape, Pseq([4], inf)

)



], 1)
);


// - Sto 2


Pdef(\classic2_2, Ppar([

Pbind(
\instrument, \Bota1_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\harm, Pseq([1], inf),
\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
\freq, Pseq([~f_2], inf),
//Bota Envelope
\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
\shape, Pseq([-4], inf)

),

Pbind(
\instrument, \Noise_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
//Noise Envelope
\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
\shape, Pseq([-200], inf)
),


Pbind(
\instrument, \Drone_Glitch_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
\ampG, Pseq([0], inf),
//\harm, Pseq([0.5], inf),
\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
//Drone Envelope
\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
\shape, Pseq([4], inf)

)



], 1)
);

// - Sto 1

Pdef(\classic2_1, Ppar([

Pbind(
\instrument, \Bota1_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\harm, Pseq([1], inf),
\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
\freq, Pseq([~f_2], inf),
//Bota Envelope
\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
\shape, Pseq([-4], inf)

),

Pbind(
\instrument, \Noise_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\ampN, Pseq([0.2, 0.1, 0.2, 0.1, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
\nMul, Pseq([1e4, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
\nAdd, Pseq([1e4, 1e4, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
//Noise Envelope
\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
\shape, Pseq([-200], inf)
),


Pbind(
\instrument, \Drone_Glitch_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
\ampG, Pseq([0], inf),
//\harm, Pseq([0.5], inf),
\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
//Drone Envelope
\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
\shape, Pseq([4], inf)

)



], 1)
);


// - Sto 4

Pdef(\classic2_4, Ppar([

Pbind(
\instrument, \Bota1_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\harm, Pseq([1], inf),
\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/2, inf), //def /4
\freq, Pseq([~f_2], inf),
//Bota Envelope
\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
\shape, Pseq([-4], inf)

),

Pbind(
\instrument, \Noise_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1),
\ampN, Pseq([0.1, 0.1, 0.1, 0.2, Pseq([0.1], 6), 0.1]*1.2, inf), //def xoris *1.4
\nMul, Pseq([1e40, 1e4, 1e40, 1e4, Pseq([0.001], 6), 1e40], inf),
\nAdd, Pseq([1e4, 1e40, 1e40, 1e4, Pseq([1e4], 6), 1e40], inf),
//Noise Envelope
\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf),
\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
\shape, Pseq([-200], inf)
),


Pbind(
\instrument, \Drone_Glitch_Meros_1,
//All
\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], 1), //?
\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 0.3]*1.3, inf),  //def /2
\ampG, Pseq([0], inf),
//\harm, Pseq([0.5], inf),
\harm, Pseq([  0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
//Drone Envelope
\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf),
\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
\shape, Pseq([4], inf)

)



], 1)
);

*/