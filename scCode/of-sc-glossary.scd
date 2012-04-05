/*
livePerformance
OSC messages dictionary

Aris Bezas Igoumeninja <-> Corfu 
05 - 04 - 2012
*/


BACKGROUND
----------
OF.background(0,0,0,0);		//OF.rgb("background",R,G,B,A);
OF.background(0);		//OF.rgb("background",R,G,B,A);
OF.rgb("background",0,0,0,5);	//OF.rgb("background",R,G,B,A);
OF.rect(100,100,200,200)	//x,y,width,height

MACHINE LISTENING
-----------------
r = MakeResponders.new;		
r.all;		
r.removeResponders;

a = SendAmpFreq.new; 	
a.start; 	
OF.rgb("sound",255,255,255,20);				// 	OF.rgb("sound",r,g,b,a)
a.stop;  
a.impulseRate(0.5);
c = SendSpecCentroid.new; 	
c.start; 	
c.stop; 	
c.impulseRate(0.5);
d = SendSpecFlatness.new; 	d.start; 	d.stop; 	d.impulseRate(24);
e = SendLoudness.new;	
e.start;	
e.stop;	
e.impulseRate(24);

b = SendOnsets.new;	b.start;	b.stop	b.threshold(1);

SPECTROGRAPH
------------
OF.playSpectro("mirrorMode", 9);
OF.mlab("fftColor", 1,1,1); // the range of the color is 0 < fftColor < 1
f = SendSpectrogramData.new; 
SynthDef(\input, { | level = 1| Out.ar(0,In.ar(8)*level)}).send(s);
s.mute;
i = Synth(\input).play
i.set(\level, 2);
i.free

s.volume_(25)
x={Out.ar(0,In.ar(8)*1.9)}.play;
x.free
f.stopSending;
f.connectToPoller


Mirror Effect Modes:
0 : White from Right
1 : White from Right - Mirror
3 : White from Center - two directions - Mirror
4 : White from Two Centers - Mirror
5 : Fire Colors from Right
6 : Fire Colors Mirrow from Right
7 : Fire Colors Mirrow from Middle
9 : Alva Noto Insiration


SKETCH
------
OF.interactWithSketch("activate", 0);
OF.interactWithSketch("padSketchXY", 200,200);
OF.interactWithSketch("mouseLines",0);
OF.rgb("sketch",255,255,255,20);

OF.interactWithSound("activate",0); 			//	0:DEACTIVATE, 1: ACTIVATE
OF.interactWithSound("numSoundSketches", 7);		//	RANGE: 1 -> 999
OF.interactWithSound("glBeginType",0);			//	0:POINTS, Ê Ê 1: LINES
OF.interactWithSound("numSoundSketches", 200);		//  200: LINES or POINTS
OF.interactWithSound("maxFreqIn", 2000);			// 2000 Hz
OF.interactWithSound("maxAmpIn", 1);				// 1: amplitude
OF.interactWithSound("maxSoundElasticity",0.1);	//  RANGE: 0 -> 20
OF.rgb("sound",255,255,255,20);				// 	OF.rgb("sound",r,g,b,a)

PARTICLE
--------
OF.rgb("background",0,0,0,10);				//OF.rgb("background",R,G,B,A);
OF.particle("activate", 1);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("add", xPos, yPos, xSpeed, ySpeed);		
OF.particle("add", 10,10, 0.5, 0.5);		
//:Add more
{
	1000.do{|i|
		OF.particle("add", rrand(0,~width).asInteger,rrand(0,~height).asInteger,0.5, 0.5);		
		i.postln;
		0.05.wait;
	}
}.fork
//:-

OF.particle("forceRadius", 10);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 15);			//	RANGE: 1 -> 25
OF.particle("iPodPush", 0);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor", 15);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor",255,255,255,25);		//	RGBA
OF.particle("dotColor",255,255,0);		//	RGB
OF.particle("conColor",255,255,255,25);		//	RGBA
OF.particle("conColor",255,0,0);				//	RGB

OF.particle("bounce", 100, 100, 800, 1500);			

OF.particle("pushParticles", 0);					//	0:DON'T PUSH, 1: PUSH 
OF.particle("push", 100,400);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)		

TYPOGRAPHY
----------
OF.writeString("string",500,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);Ê
OF.writeString("bigCenter", "~sadfh",~width/2,~height/2,255,255,255,255); // BIG 

FEEDBACK
--------
OF.feedback("activate", 0);
OF.feedback("speedXY", 0.5,0);

EFFECTS
=======
OF.effect("destruct","activate",1)
OF.effect("destruct","case",1)	// 2: the best 0,1,2,3
OF.effect("destruct","phrase1")	// 2: the best 0,1,2,3

OF.effect("noiseEffect", 0); 	// with z0re is clearing the background
OF.effect("mirror", 0); 	
OF.effect("mirror", "case", 1); 
OF.effect("tree");

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
		OF.img(rrand(0,60).asInteger,0, 0, 400, 400, 400, 200, 0, 0, 0, i);
		i.postln;
		0.05.wait;
	}
}.fork
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
~ataka.stop;
~ataka = {

	inf.do {
		~amp = Pseq((1.0..1.0), 2).asStream;
		~dur = Pseq((1.0..1.0), 2).asStream;
		~tone = Scale.huzam.degreeToRatio(1, 1);
		~kontraBass.brt_(~tone).playBuf(0.001, 0.5, 0.3,mul:~amp.next, loop:0);
		~dur.next.wait;
	}
}.fork;


