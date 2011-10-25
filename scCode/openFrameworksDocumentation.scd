//:Joda iPod
//:------------------
//:OSC messages dictionary



BACKGROUND
----------
OF.background(0,0,0);		//OF.rgb("background",R,G,B,A);
OF.rgb("background",0,0,0,0);	//OF.rgb("background",R,G,B,A);

SKETCH
------
OF.interactWithSound("activate",1); 			//	0:DEACTIVATE, 1: ACTIVATE
OF.interactWithSound("numSoundSketches", 7);		//	RANGE: 1 -> 999
OF.interactWithSound("glBeginType",0);			//	0:POINTS, Ê Ê 1: LINES
OF.interactWithSound("maxSoundElasticity",0.1);		//	RANGE: 0 -> 20
OF.rgb("sound",255,255,55,20);				// 	OF.rgb("sound",r,g,b,a)

PARTICLE
--------
OF.rgb("background",0,0,0,10);	//OF.rgb("background",R,G,B,A);
OF.particle("activate", 1);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("forceRadius", 100);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);			//	RANGE: 1 -> 25
OF.particle("iPodPush", 1);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)


OF.rgb("particleConnections",255,255,255,255);		//	RGBA
OF.rgb("particlesColor",255,255,255,255);			//	RGBA

TYPOGRAPHY
----------
OF.writeString("string",100,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);Ê

FEEDBACK
--------

OF.feedback("activate", 1);
OF.feedback("speexXY", 1, 2);


//:wfae

OF.rgb("background",0,0,0,10);	//OF.rgb("background",R,G,B,A);
OF.particle("activate", 1);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("forceRadius", 100);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);			//	RANGE: 1 -> 25
OF.rgb("particleConnections",255,255,255,255);		//	RGBA
OF.rgb("particlesColor",255,255,255,255);			//	RGBA