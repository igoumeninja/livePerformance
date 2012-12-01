
//==============================================TREXE TA SYNTHS KAI META TO PATTERN

//:Deafult_Noise_Synth(amp * 0)
(
SynthDef(\Noise, {
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

	Out.ar(0, sourceN * envN * ampN)

}).add;
//:Different_Noise_Synth(amp * 40)
SynthDef(\Noise, {
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
//:
Pdef(\x, Ppar([

/*Pbind(
		\instrument, \Bota,
		//All
		\dur, Pseq([1/3, /*3/5*/1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\harm, Pseq([1], inf),
		\ampB, Pseq([3, 3, 0, 3, Pseq([3], 6), 3]/8*0 , inf),
		\freq, Pseq([~f_2], inf),
		//Bota Envelope
		\at, Pseq([0.01], inf), //def 1 -->try 0.02
		\rel, Pseq([0.2], inf),//def 3 * 2 and the last 0.02 -->try 0.03
		\shape, Pseq([-4], inf)//def -4

),*/

Pbind(
		\instrument, \Noise,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*40, inf),
		\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
		//\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
		\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
		//Noise Envelope
		\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf),
		\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
		\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
),


Pbind(
		\instrument, \Drone_Glitch,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.6 ,0, 0, 0], 1), 0.6], inf),
		\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
		\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf),
		\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
		//Drone Envelope
		\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf),
		\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
		\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)

);
/*
Pbind(
		\instrument, \Drone_Glitch,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf), //?
		\ampD, Pseq([0.6, 0, 0, 0, Pseq([0], 6), 0.6], inf),
		\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf),
		\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf),
		\freq, Pseq([~fD2, 0, 0, 0, Pseq([0], 6), [~fD7, ~f_7]], inf),
		//Drone Envelope
		\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.03], 6), 1/3], inf),
		\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/6], inf),
		\shape, Pseq([8, 8, 8, 8, Pseq([8], 6), 8], inf)

);
*/
	], inf)
);
)
//:

Pdef(\x).play;
Pdef(\x).stop;
