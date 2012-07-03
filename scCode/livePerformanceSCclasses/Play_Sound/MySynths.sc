//: Create a class for auto send 
MySynths {
	*sendToServer {
	//:Use microphone
	Udef("pure_clarinet",	
		{
			var in;
			in = In.ar(8);
			Out.ar(0, in);
		}		
	);
	//:Play Buffer v.1
	Udef(\foubuf, {| out = 0, bufnum = 0, rate = 1, trigger = 1, loop = 1, pos = 0, level = 1, windowSize = 0.5, pitchRatio = 1, in , cutoffFreq = 0|
		in = Pan2.ar(PitchShift.ar(                  
	          		PlayBuf.ar(1, bufnum, rate, trigger, 0, loop),
	                  	windowSize,
	                  	pitchRatio
	                 ),
	          	pos,
	             	level
	        	);
	      Out.ar(out, in);
	});
	//:Batuhan Synth
	Udef("batuhan",	{ | freq = 40, tempo = 1.5, out = 0, pan =0, amp=1, mulDelay = 2, roundStart = 2e-3, roundEnd = 4e-3|
		var source;	
		source = AllpassC.ar( 
					SinOsc.ar(freq).tanh,  //AllpassC.ar(in, ...)
					0.4, // maxdelaytime,
					TExpRand.ar(2e-4, 0.4, 
						Impulse.ar(tempo)).round([roundStart, roundEnd]), //delaytime,
						mulDelay,
					0.1); // mul,
		Out.ar( out, Pan2.ar(source, 0, amp));
	});
	//:Padovani Synth
	Udef("padovani", {	|sawFreq = 1, trigDur = 0.05, trigFreq = 10, roomSize = 2, sizeGrain = 1, signalOutput = 10|
	var in, singal,p,y,z;
	p= Trig.ar(Saw.ar(sawFreq),trigDur);
	y= SinOsc.ar(p*trigFreq);
	z= SinOsc.ar(p);
	in = GrainIn.ar(
			2, 			//channelNum
			y, 			//trigger
			y/sizeGrain,	//duration-the size of the grain
			z,			//input to granulate
			p*z			//pan
			-1			//buf num 
		);
	singal = GVerb.ar(in, roomSize )/signalOutput;
	Out.ar(0, singal);
	});
	//:PV_BinShift
	Udef("pv_binShift", {  | out=0, bufnum=0, stretch = 1, shift = 0, level = 1, rate = 1 |
		var in, chain;
		in = PlayBuf.ar(1, bufnum, rate, loop: 1);
		chain = FFT(LocalBuf(2048), in);
		chain = PV_BinShift(chain, 1, stretch ); 
		Out.ar(out, level * IFFT(chain).dup);
	});
	
	//:PV_MagSquared
	Udef(\pv_magSquared, {  |out=0, bufnum=0, soundBufnum=2|
		var in, chain;
		in = PlayBuf.ar(1, soundBufnum, BufRateScale.kr(soundBufnum), loop: 1);
		chain = FFT(LocalBuf(2048), in);
		chain = PV_MagSquared(chain); 
		Out.ar(out, 0.003 * IFFT(chain).dup); 
	});
	
	//:PV_RectComb synth with SinOsc Buffer as in
	Udef(\pv_rectcombSinBuf, {
		|out =0, in, bufnum = 0, rate = 1, trigger = 1, loop = 1, chain, pos = 0, level = 1, numTeeth = 1, sinPhaseFreq = 0, sinPhaseMul = 0, sinWidthMul = 0, sinWidthFreq = 0, sinPhasePhase = 0, sinWidthPhase = 0, sinPhaseAdd = 0.5, sinWidthAdd = 0.5 | 
		in = PlayBuf.ar(1, bufnum, rate, trigger, 0, loop);
		in = WhiteNoise.ar(0.2);
		chain = FFT(LocalBuf(2048), in);
		chain = PV_RectComb(chain, 
							numTeeth, 
							SinOsc.kr(sinPhaseFreq, sinPhasePhase, sinPhaseMul, 0.5), 
							SinOsc.kr(sinWidthFreq, sinWidthPhase, sinWidthMul, 0.5)
		);
	     Out.ar(out,Pan2.ar( IFFT(chain).dup, pos,level));	 
	
	});

	
	//:PV_RectComb synth with SinOsc
	Udef(\pv_rectcombSin, {
		|out =0, in, chain, pos = 0, level = 1, numTeeth = 1, sinPhaseFreq = 0, sinPhaseMul = 0.4, sinWidthMul = -0.5, sinWidthFreq = 0.24, sinPhasePhase = 0, sinWidthPhase = 0, sinPhaseAdd = 0.5, sinWidthAdd = 0.5 | 
		in = WhiteNoise.ar(0.2);
		chain = FFT(LocalBuf(2048), in);
		chain = PV_RectComb(chain, 
							numTeeth, 
							SinOsc.kr(sinPhaseFreq, sinPhasePhase, sinPhaseMul, 0.5), 
							SinOsc.kr(sinWidthFreq, sinWidthPhase, sinWidthMul, 0.5)
		);
	     Out.ar(out,Pan2.ar( IFFT(chain).dup, pos,level));	 
	
	});
	 
	//:PV_RectComb synth with LFTri
	Udef(\pv_rectcombLFTri, {
		|out =0, in, chain, pos = 0, level = 1, numTeeth = 8, lftPhaseFreq = 0.097, lftPhaseMul = 0.4, lftWidthMul = -0.5, lftWidthFreq = 0.24, lftPhasePhase = 0, lftWidthPhase = 0 | 
		in = WhiteNoise.ar(0.2);
		chain = FFT(LocalBuf(2048), in);
		chain = PV_RectComb(chain, 
							numTeeth, 
							LFTri.kr(lftPhaseFreq, lftPhasePhase, lftPhaseMul, 0.5), 
							LFTri.kr(lftWidthFreq, lftWidthPhase, lftWidthMul, 0.5)
		);
	     Out.ar(out,Pan2.ar( IFFT(chain).dup, pos,level));	 
	
	});
	 
	}
}