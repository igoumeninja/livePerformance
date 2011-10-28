//:Joda - Live Performance
//:-----------------------
"open -a /Users/fou/projects/phD/code/Gea/apps/abizof/110517_ofSpectrograph/bin/openFrameworks.app/Contents/MacOS/openFrameworks".unixCmd;

"killall openFrameworks".unixCmd;

//:change application
{
"open -a /Users/fou/projects/phD/code/Gea/apps/abizof/110517_ofSpectrograph/bin/openFrameworks.app/Contents/MacOS/openFrameworks".unixCmd;

2.wait;
"killall openFrameworks".unixCmd;

"open -a /Users/fou/projects/phD/code/Gea/apps/abizof/110728_cutMotion/bin/openFrameworks.app/Contents/MacOS/openFrameworks".unixCmd;

}.fork

//:Define Global Variables
OF.background(40,40,40);
OF.rgb("background",0,0,0,20);

//:numSketch
OF.interactWithSound("numSoundSketches", 4);		//	RANGE: 1 -> 999

//:---
~screenWidth = 1280;
~screenHeight = 1024;

// Take a string from file
~myString = File.use("/Users/ari/Projects/artistic/performance/JodaLyon/data/text/text", "r", { |f| f.readAllString.postln })
//:Do Typo
OF.background(0,0,0);
OF.rgb("background",0,0,0,0);	//OF.rgb("background",R,G,B,A);
{
	40.do {
		{
			var xPos, yPos = 20, count = 0;
			11500.do	{ |i|
				xPos = 8*count;
				OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,125);
				//i.postln;
				0.01.wait;
				if (xPos > ~screenWidth, {xPos = 0; count = 0; yPos = yPos +15} );
				count = count + 1;
			}
		}.fork;
		rrand(2,14).wait;
	}
}.fork

//:Random symbols

OF.writeString(["!","@", "$"].at(1),100,100,255,255,255,125);
{
	40.do {
		{
			var xPos, yPos = 20, count = 0;
			11500.do	{ |i|
				xPos = 8*count;
				OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,125);
				//i.postln;
				0.01.wait;
				if (xPos > ~screenWidth, {xPos = 0; count = 0; yPos = yPos +15} );
				count = count + 1;
			}
		}.fork;
		rrand(2,14).wait;
	}
}.fork

//:OSC responder from omer
OF.background(0,0,0);
OF.particle("pushParticles", 1);				//	0:DON'T PUSH, 1: PUSH


~mertrig = OSCresponder.new(nil, "/mertrig", { 
	arg time, resp, msg; 
	"hi omer".postln;

} ).add; 


//:---------------------
//:Play with Typography (see File)

//:Task-Typography
~typography = Task({
	var xPos, yPos = 100, count = 0;
	inf.do	{ |i|
		xPos = 8*count;
		OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,125);
		i.postln;
		0.01.wait;
		if (xPos > ~screenWidth, {xPos = 0; count = 0; yPos = yPos +15} );
		count = count + 1;
	}
});
//:Play Typography
~typography.play;
//:Release typography
~typography.stop;
//:more Tasks from ~typography
r = ~typography;
r.play
//:SynthDef

SynthDef()
//:-----------------------
//:Preceive
Preceive(
	\start->{ "playing".postln; },
	\a -> { "this was alpha".postln; },
	1 -> { "beat 1".postln; },
	5 -> { "beat 5".postln; },
	\end -> { "end".postln; }
).play;
//:testing
NetAddr.localAddr.sendMsg(\start, 1);
//:Pdef
Pdef(\kickdef, Posc(
	\msg, Pseq([\dum1, \nil, \nil, 	\dum1, \nil, \nil, \nil, \nil, \tek2, \nil], inf),
	\dur, Pseq([~duyekDur], inf)
	)
).play;

//:------------------
//:OSC messages dictionary
//:numSketches
OF.interactWithSound("numSoundSketches", 3);
//:Other
BACKGROUND
----------
OF.background(0,0,0);			//OF.rgb("background",R,G,B,A);
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
OF.particle("activate", 1);					//	0:DEACTIVATE, 1: ACTIVATE
OF.particle("forceRadius", 100);				//	RANGE: 0 -> 700
OF.particle("particleNeighborhood", 25);			//	RANGE: 1 -> 25
OF.particle("iPodPush", 1);					//	0:DON'T PUSH, 1: PUSH (AT THE CENTER)


OF.rgb("particleConnections",255,255,255,255);		//	RGBA
OF.rgb("particlesColor",255,255,255,255);			//	RGBA

TYPOGRAPHY
----------
OF.writeString("string",100,100,255,255,255,255);	//	("takis", X, Y, R, G, B, A);Ê
