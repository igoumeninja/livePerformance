//:WireFace Sound Enviroment

//:-------------------------
//:Synth Definition
// Stagones by Batuhan
SynthDef( "stagones",
			{ | freq = 40, tempo = 1.5, out = 0, pan =0, amp
				=1, 
			mulDelay = 2, roundStart = 2e-3, roundEnd = 4e-3|
	 			var source;
				source = AllpassC.ar( SinOsc.ar(freq).tanh,  //AllpassC.ar(in, ...)
							0.4, // maxdelaytime,
							TExpRand.ar(2e-4, 0.4, 
								Impulse.ar(tempo)).round([roundStart, roundEnd]), //delaytime,
							mulDelay,
							0.1); // mul,
				Out.ar( out, Pan2.ar(source, pan, amp));
				
}).send(s);
	
// foubuf v.002
SynthDef(\foubuf_002, {| out = 0, bufnum = 0, rate = 1, trigger = 1, loop = 1, pan = 0, level = 1, freqShift = 0 |
	Out.ar(out,
		Pan2.ar(
			FreqShift.ar(
				PlayBuf.ar(1, 
						bufnum, 
						rate, 
						trigger, 
						0, 
						loop
				),
				freqShift),
			pan,
			level)
		)
}).send(s);

// Crotale
SynthDef("PMCrotale", {
	arg midi = 60, tone = 3, art = 1, amp = 0.8, pan = 0; 
	var env, out, mod, freq;

	freq = midi.midicps;
	env = Env.perc(0, art);
	mod = 5 + (1/IRand(2, 6));

	out = PMOsc.ar(freq, mod*freq, 
		pmindex: EnvGen.kr(env, timeScale: art, levelScale: tone), 
		mul: EnvGen.kr(env, timeScale: art, levelScale: 0.3));

	out = Pan2.ar(out, pan);

	out = out * EnvGen.kr(env, timeScale: 1.3*art, 
		levelScale: Rand(0.1, 0.5), doneAction:2);  
	
	Out.ar(0, out); //Out.ar(bus, out);
}).send(s);

// Padovani
SynthDef('padovani', {	|sawFreq = 1, trigDur = 0.05, trigFreq = 10, roomSize = 2, sizeGrain = 1, signalOutput = 10, level = 1, pan = 0|
	var in, singal;
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
	Out.ar(0, Pan2.ar(singal, pan, level));
}).send(s);
  
//:Play the Synths
x = Synth(\stagones).play;
y = Synth(\foubuf_002).play;
z = Synth(\padovani).play;

//:------------------------

//:Testing Synths
//:Play Batuhan
x = Synth(\stagones).play;
//:Control Batuhan
~tempoButSpec = ControlSpec(0.001, 10, \lin);
OSCresponderNode(n, '\tempo', { |t,r,msg| 
	x.set(\tempo, ~tempoButSpec.map(msg[1])*0.1);
	~tempoButSpec.map(msg[1]).postln;
}).add;
//:Test Batuhan
NetAddr("127.0.0.1", 57120).sendMsg("\tempo", 0.1);

//:Play foubuf
y = Synth(\foubuf_002).play
//:Control foubuf
y.set(\bufnum, 5)
y.set(\loop, 1)
y.set(\level, 0.5)
y.set(\rate, 0.3)


//:Buffer OSC Responders
p = NetAddr("127.0.0.1", 57120);  // send to SuperCollider Server (just for debugging)
OSCresponder(nil, "ice", { x.set(\trigger, 1, \bufnum, 0);}).add; //Create a Responder
p.sendMsg(\ice); //test message

//:Play Crotale2Synth("PMCrotale", ["midi", rrand(48, 72).round(1), "tone", rrand(1, 6)]);


//:Play Padovani
z = Synth(\padovani).play;
//:Test padovani
NetAddr("127.0.0.1", 57120).sendMsg("/glBeginCase", 1);
