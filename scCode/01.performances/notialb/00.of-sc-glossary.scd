/*
livePerformance
OSC messages dictionary

Aris Bezas Igoumeninja <-> Corfu
05 - 04 - 2012

MakeResponders
*/


BACKGROUND
----------
OF.background(0,0,0,10);		//OF.rgb("background",R,G,B,A);
OF.background(255,255,255,10);
OF.background(255,10);
OF.background(0);		//OF.rgb("background",R,G,B,A);
OF.rgb("background",0,0,0,255);	//OF.rgb("background",R,G,B,A);
OF.rect(100,100,200,200)	//x,y,width,height
OF.rect(100,100,200,200,255,255,255,255);	//x,y,width,height

OF.background(setBackgroundAutoBool, 0);

SIN EQUATIONS
-------------
OF.background(0,0,0,0);
OF.sinEq(0,600.rand,10.03213); // OF.sinEq(true,xPos,par1);
OF.sinEq(0,200,100); // OF.sinEq(true,xPos,par1);
OF.sinEq("remove",1)
OF.sinEq("remove",0)
25.do{|i|OF.sinEq("remove",24-i)}

FEEDBACK
--------
OF.feedback("activate", 0);
OF.feedback("speedXY", 0,5.5);

EFFECTS
=======
OF.effect("destruct","activate",1)
OF.effect("destruct","case",2)	// 2: the best 0,1,2,3
OF.effect("destruct","phrase1")	// 2: the best 0,1,2,3

DESTRUCT CASES
--------------
0:
OF.effect("noiseEffect", 0); 	// with z0re is clearing the background
OF.effect("mirror", 1);
OF.effect("mirror", "case", 3);
OF.effect("tree");

MACHINE LISTENING
-----------------
~mlabResponders.removeResponders;

AMP - FREQ
----------
~mySendAmpFreq.start;
OF.interactWithSound("activate",1);
OF.rgb("sound",0,255,255,25);				// 	OF.rgb("sound",r,g,b,a)
~mySendAmpFreq.impulseRate(24); // 24 frames/ sec
~mySendAmpFreq.stop;

ONSETS
-------
~mySendOnsets.start;
~mySendOnsets.stop;
~mySendOnsets.threshold(1.5);  // beetwen [0,1]
~boolSketchColor = 0;
~onsetSketchColorResp.remove;
~onsetSketchColorResp = OSCresponderNode(nil, 'onset', { |t,r,msg|
	OF.rect(0,0,~width,~height,255,255,255,255);
}).add;

~onsetSketchColorResp = OSCresponderNode(nil, 'onset', { |t,r,msg|
	{}.fork
	OF.rect(0,0,~width,~height,255,255,255,255);
}).add;


SPECTROGRAPH
------------
~mySendSpectrogramData.connectToPoller;
~mySendSpectrogramData.stopSending;

DEMO
----
//:Curves
{
{SinOsc.ar(2000*SinOsc.kr(XLine.kr(0.001,0.5,20, doneAction:2)), mul:0.5)}.play;
1.wait;
{SinOsc.ar(4000*SinOsc.kr(XLine.kr(0.001,0.5,20, doneAction:2)), mul:0.5)}.play;
1.wait;
{SinOsc.ar(8000*SinOsc.kr(XLine.kr(0.001,0.5,20, doneAction:2)), mul:0.5)}.play;
10.wait;
{SinOsc.ar(8000*SinOsc.kr(XLine.kr(0.5,0.001,20, doneAction:2)), mul:0.5)}.play;
1.wait;
{SinOsc.ar(4000*SinOsc.kr(XLine.kr(0.5,0.001,20, doneAction:2)), mul:0.5)}.play;
1.wait;
{SinOsc.ar(2000*SinOsc.kr(XLine.kr(0.5,0.001,20, doneAction:2)), mul:0.5)}.play;
}.fork
//:-

OF.playSpectro("mirrorMode", 3);

OF.playSpectro("rotCircSpect", 0.12);

OF.background(0,0,0,0);

OF.mlab("fftColor", 1,1,1); // the range of the color is 0 < fftColor < 1

~inputSpectroSynth.set(\level, 2);

~mySendSpectrogramData.rate(1)

Mirror Effect Modes:
0 : White from Right
1 : White from Right - Mirror
3 : White from Center - two directions - Mirror
4 : White from Two Centers - Mirror
5 : Fire Colors from Right
6 : Fire Colors Mirrow from Right
7 : Fire Colors Mirrow from Middle
8 : Circular
9 : Alva Noto Insiration
10: Circular sunset

OTHER MACHINE LISTENING
-----------------------

c = SendSpecCentroid.new;
c.start;
c.stop;
c.impulseRate(0.5);
d = SendSpecFlatness.new; 	d.start; 	d.stop; 	d.impulseRate(24);
e = SendLoudness.new;
e.start;
e.stop;
e.impulseRate(24);



SKETCH
------
-mouse
OF.interactWithSketch("activate", 1);
OF.interactWithSketch("padSketchXY", 2200,200);
OF.interactWithSketch("mouseLines",1);
OF.interactWithSketch("maxSoundElasticity",0.1)
OF.rgb("sketch",255,255,255,20);
OF.rgb("sketch",0,0,0,250);
-sound2d
OF.interactWithSound("activate",1); 				//	0:DEACTIVATE, 1: ACTIVATE
OF.interactWithSound("numSoundSketches", 80);		//	RANGE: 1 -> 999
OF.interactWithSound("glBeginType",1);			//	0:POINTS, 1: LINES
OF.interactWithSound("numSoundSketches", 200);		//  200: LINES or POINTS
OF.interactWithSound("maxFreqIn", 2000);			// 2000 Hz
OF.interactWithSound("maxAmpIn", 1);				// 1: amplitude
OF.interactWithSound("maxSoundElasticity",5.1);	//  RANGE: 0 -> 20
OF.rgb("sound",255,255,255,25);				// 	OF.rgb("sound",r,g,b,a)
-sound3d
OF.sketch3d("activate", 0);
OF.sketch3d("glBeginType",1);
OF.sketch3d("numSketch3d",500);
OF.sketch3d("rotYratio",0.3);
OF.sketch3d("rotZratio",0.1);
OF.sketch3d("maxSketch3dElasticity",0.9);
OF.rgb("sketch3d",255,255,255,20);


PARTICLE
--------
OF.rgb("background",0,0,0,0);				//OF.rgb("background",R,G,B,A);
OF.particle("activate", 0);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("add", xPos, yPos, xSpeed, ySpeed);
OF.particle("add", 10,10, 0.5, 0.5);
//:Add more
{
	2000.do{|i|
		OF.particle("add", rrand(0,~width).asInteger,rrand(0,~height).asInteger,0.5, 0.5);
		i.postln;
		0.05.wait;
	}
}.fork
//:-
OF.particle("pusher","set", 10, 800,200) // ACTIVATE SEND AMP FREQ PUSHER
OF.particle("pusher","set", 0, 200,800)
OF.particle("pusher","set", 1, 600,200)
OF.particle("pusher","set", 2, 800,200)
OF.particle("pusher","remove", 0)
OF.particle("pusher","remove", 10)
OF.particle("pusher","remove", 2)

OF.particle("forceRadius", 30);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);//15 default			//	RANGE: 1 -> 25
OF.particle("iPodPush", 0);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor", 15);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor",255,255,255,255);		//	RGBA
OF.particle("dotColor",255,255,0);		//	RGB
OF.particle("conColor",255,255,255,25);		//	RGBA
OF.particle("conColor",255,0,0);				//	RGB

OF.particle("bounce", 0, 0, ~width, ~height);

OF.particle("pushParticles", 0);					//	0:DON'T PUSH, 1: PUSH
OF.particle("push", 100,400);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

//:-
40.do{|i|
OF.particle("pusher","set", i, i*200,~height/2);
}

//:-
(10..20).do{|i|
	i.postln;
OF.particle("pusher","set", i, i*200,0);
}

//:-

(-1..1000).do{|i|
		i.postln;
OF.particle("pusher","remove", i);
}
//:-
~mySendOnsets.start;
~mySendOnsets.threshold(2);  // beetwen [0,1]
//:-
~onsetPushParticlesResp.remove;
~onsetPushParticlesResp = OSCresponderNode(nil, 'onset', { |t,r,msg|
	{
		2.do	{
			OF.particle("forceRadius", 200);
			0.1.wait;
			OF.particle("forceRadius", 10);
			1.wait
		}
	}.fork;
}).add;

//:-


TYPOGRAPHY
----------
OF.writeString("34","sdg",500,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);
OF.writeString("string",500,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);
OF.writeString("bigCenter", "~sadfh",~width/2,~height/2,255,255,255,255); // BIG
OF.writeString("bigCenter", "hi",~width/2,~height/2,255,255,255,255); // BIG


CUTMOTION
---------
OF.img(imageID, xPos, yPos, width, height);
OF.img(imageID, xPos, yPos, width, height, rotX,rotY,rotZ);
OF.img(imageID, xPos, yPos, width, height, translateX, translateY, translateZ, rotX,rotY,rotZ);
OF.img(image
	ID,
	xPos, yPos,
	width, height,
	translateX, translateY, translateZ,
	scaleX, scaleY, scaleZ,
	rotX,rotY,rotZ);
//:
{
	inf.do{|i|
		OF.img(rrand(0,60).asInteger,0, 0, ~width, ~height);
		i.postln;
		0.02.wait;
	}
}.fork
//:
~delay = 0.05;
{
	inf.do{|i|
		OF.img(i%60,0, 0, 1936/2,1288/2);
		~kontraBass.brt_(rrand(1,3).asInteger).playBuf(0.001, 0.5, 0.3, loop:0);
		//i.postln;
		~delay.wait;
	}
}.fork
//OF.img(0, 0, 0,100,100);
//:-


//:-

VIDEO
-----
OF.video("playVideo",1)
OF.video("reloadVideo")
OF.video("playVideo",1,~width/8,~height/8)	//width,height
OF.video("deleteVideo")
OF.video("setSpeed",1)
OF.video("rVideo", 255)
OF.video("gVideo", 115)
OF.video( )
OF.video("aVideo", 255)
OF.video("colorVideo", 255,255,255,255)


SUPERFORMULA
------------
m=int(random(3,40));
n1=random(.5);
n2=random(6.);
n3=random(6.);
epi=random(100,360);
step=random(.05,10);

OF.superformula("activate",0)
OF.superformula("num",20)
OF.superformula("epi",1600)
OF.superformula("mi",19)
OF.superformula("n1",9)
OF.superformula("n2",14)
OF.superformula("n3",18)
OF.superformula("step",36)
OF.superformula("alpha",0)

SOUND
-----
Kaffer
~ataka.stop;
~kontraBass.brt_(3).playBuf(0.001, 0.5, 0.3,mul:~amp.next, loop:0);
~ataka = {

	inf.do {
		~amp = Pseq((1.0..1.0), 2).asStream;
		~dur = Pseq((1.0..1.0), 2).asStream;
		~tone = Scale.huzam.degreeToRatio(1, 1);
		~kontraBass.brt_(~tone).playBuf(0.001, 0.5, 0.3,mul:~amp.next, loop:0);
		~dur.next.wait;
	}
}.fork;

PATTERNS
--------
Posc - Psend - Preceive - Pfuncn
//:-
OF.background(255,255,255,10);
~dur = Pseq([0.73, 0.4, 1, 0.2], inf).asStream;
~atakaVisual = {
	inf.do {|i|
		OF.rect(i%4*300, 100, 200, 200);
		OF.writeString("ok",i%4*300, 100, 255,255,255,255); // BIG 		~dur.next.wait;
	}
}.fork;

~pushOnsetResp.remove;
~pushOnsetResp = OSCresponderNode(nil, '/pushParticlesOnset', { | time, resp, msg|
	if( msg[1] == 1,{
		OF.particle("pusher","set", 10, 800,200);
	},{
		~onsetPushParticlesResp.remove;
	});

	OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
} ).add;

