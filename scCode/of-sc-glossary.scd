//:Joda Performance
	//:------------------
//:OSC messages dictionary


BACKGROUND
----------
OF.background(0,0,0,20);		//OF.rgb("background",R,G,B,A);
OF.background(0);		//OF.rgb("background",R,G,B,A);
OF.rgb("background",0,0,0,5);	//OF.rgb("background",R,G,B,A);

SKETCH
------
OF.interactWithSketch("activate", 1);
OF.interactWithSketch("padSketchXY", 200,200);
OF.interactWithSketch("mouseLines",0);
OF.rgb("sketch",255,255,255,20);

OF.interactWithSound("activate",1); 			//	0:DEACTIVATE, 1: ACTIVATE
OF.interactWithSound("numSoundSketches", 7);		//	RANGE: 1 -> 999
OF.interactWithSound("glBeginType",0);			//	0:POINTS, Ê Ê 1: LINES
OF.interactWithSound("numSoundSketches", 200);		//  200: LINES or POINTS
OF.interactWithSound("maxFreqIn", 2000);			// 2000 Hz
OF.interactWithSound("maxAmpIn", 1);				// 1: amplitude
OF.interactWithSound("maxSoundElasticity",0.1);	//  RANGE: 0 -> 20
OF.rgb("sound",255,255,55,20);				// 	OF.rgb("sound",r,g,b,a)

PARTICLE
--------
OF.rgb("background",0,0,0,10);				//OF.rgb("background",R,G,B,A);
OF.particle("activate", 0);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("add", xPos, yPos, xSpeed, ySpeed);		
OF.particle("add", 10,10, 0.5, 0.5);		
OF.particle("forceRadius", 100);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);			//	RANGE: 1 -> 25
OF.particle("iPodPush", 0);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor", 15);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)

OF.particle("dotColor",255,255,255,225);		//	RGBA
OF.particle("dotColor",255,255,0);		//	RGB
OF.particle("conColor",255,0,255,225);		//	RGBA
OF.particle("conColor",255,0,0);				//	RGB

OF.particle("bounce", 100, 100, 800, 1500);					

TYPOGRAPHY
----------
OF.writeString("string",100,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);Ê

FEEDBACK
--------
OF.feedback("activate", 0);
OF.feedback("speedXY", 0.5,0);

EFFECTS
=======
OF.effect("destruct","activate",1)
OF.effect("destruct","case",3)	//0,1,2,3

OF.effect("noiseEffect", 0); // with z0re is clearing the background
OF.background(0,0,0);		//OF.rgb("background",R,G,B,A);
OF.effect("noiseEffect", "true");
OF.effect("mirrowEffect", "fasle");	// 2 d copy mirrow
OF.effect("mirrowEffect2", 1);	// 4-D copy mirrow
OF.effect("tree");

WFAE
----
OF.rgb("background",0,0,0,10);					//   OF.rgb("background",R,G,B,A);
OF.particle("activate", 1);						//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("forceRadius", 100);					//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);			//	RANGE: 1 -> 25
OF.rgb("particleConnectionsColor",255,255,255,255);	//	RGBA
OF.rgb("particlesColor",255,255,255,255);			//	RGBA

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

VIDEO
-----
OF.video("playVideo",1)
OF.video("setSpeed",1)
OF.video("deleteVideo")
OF.video("reloadVideo")
OF.video("rVideo", 255)
OF.video("gVideo", 115)
OF.video("bVideo", 5)
OF.video("aVideo", 255)
OF.video("colorVideo", 255,255,255,255)

SPECTROGRAPH
------------
SendSpectrogramData.new;
SendSpectrogramData.new;
~ter.stopPoller
{Out.ar(0, Pan2.ar(In.ar(8), 0, 0.5))}.play  //!!!!FEEDBACK!!!!!!

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
