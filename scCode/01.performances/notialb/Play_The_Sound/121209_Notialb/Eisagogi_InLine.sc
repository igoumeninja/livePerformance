Eisagogi_InLine{

	*load{	var a, b;


		//:Eisagogi_SynthDefs
		//:========================

		//b = Buffer.read(Server.default, "/Users/vagelis/Desktop/Notia_Alvania_Track/_Pdefs/Import/Import.wav");
		b = Buffer.read(Server.default, "/Users/ari/Library/Application\ Support/SuperCollider/Extensions/notialb/Play_The_Sound/Import.wav");


		//:
		SynthDef(\buf, {|startp = 1, ipod = 0, pos = 0|
			var source, env, sec, bufnum = b;

			sec = BufDur.ir(b);
			pos = sec * ipod * 44100;
			source = PlayBuf.ar(1, b, BufRateScale.kr(b), trigger: 0, startPos: pos, loop:0, doneAction:2);

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



		//:Eisagogi_Pattern
		//:========================

		Pdef(\import, Pbind(
			\instrument, \buf,
			\dur, Pseq([1], 1),
			\ipod, Pseq([0], 1)
		));

		Pdef(\import).quant = 0;


		//:Extra_Sounds_Responder
		//:========================

		// ~cc_Import = CCResponder({
		//
		// 	|src,chan,num,value|
		//
		// 	//start-stop
		// 	if (num == 23){
		//
		// 		if (value == 127) { a = Synth(\chaos1); ("Chaos1_Start").postln; };
		// 		if (value == 0) { a.free; ("Chaos1_Stop").postln; };
		//
		// 	};
		//
		// 	//set the amp
		// 	if (num == 2){ var amp_im1, amp1;
		//
		// 		amp1 = BiMap(0, 127, outMin: 0.0, outMax: 0.1);
		// 		amp_im1 = amp1.value(value);
		// 		//	("Chaos_1_Amp = ").post;(amp_im1).postln;
		//
		// 		a.set(\amp, amp_im1);
		//
		// 	};
		//
		// 	//set the freq(0.1 - 100)
		// 	if (num == 14){ var freq_im1, freq1;
		//
		// 		freq1 = BiMap(0, 127, outMin: 0.1, outMax: 100);
		// 		freq_im1 = freq1.value(value);
		// 		//("Chaos_1_Freq = ").post;(freq_im1).postln;
		//
		// 		a.set(\freq, freq_im1);
		//
		// 	};
		//
		// 	//set hilo(1, 1000)
		// 	if (num == 33){
		//
		// 		if (value == 127) { a.set(\hilo, 1000); /*("HiLo_1000").postln;*/};
		// 		if (value == 0) { a.set(\hilo, 1); /*("HiLo_1").postln;*/};
		//
		// 	};
		//
		// 	},
		// 	nil,
		// 	0,
		// 	[2, 23, 14, 33],
		// 	nil
		//
		// );

	}

}

//CCR.kill;

