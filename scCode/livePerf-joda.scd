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

//:spectro
MySynths.sendToServer;
//:Load Buffers
MyBuffers.seals;
//:SendSpectrogramData
SendSpectrogramData.new;

SendAmpFreq.start
//SendSpectrogramData('fft').free;
//SendSpectrogramData('ofSpectrograph');
//SendSpectrogramData('ofSpectrograph').free;

//:Use SoundIn
'pure_clarinet'.playDef

//:Define Global Variables
OF.background(40,40,40);
OF.rgb("background",0,0,0,10);
NetAddr("127.0.0.1", 12345).sendMsg("rgb","background", 0,0, 0, 30)
//:numSketch
OF.interactWithSound("numSoundSketches", 4);		//	RANGE: 1 -> 999

//:---
~width = 1280;
~height = 1024;

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
				OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
				//i.postln;
				0.01.wait;
				if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
				count = count + 1;
			}
		}.fork;
		rrand(2,14).wait;
	}
}.fork

//:rotateText
~rotateText = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 0, 0, ~width/2, ~height/2, 0, 0,0, i);
		0.02.wait;
	}
});
//:play rotateText
~rotateText.play;
//:stop rotateText
~rotateText.stop;

//:OSC responder from omer
~mertrig = OSCresponder.new(nil, "/mertrig", { 
	arg time, resp, msg; 
	"hi omer".postln;

} ).add; 

//:PARTICLES
/* 
At this phrase we will stop everything else and we will make a transport to particles.
Initially the particles will be connected
*/
{
	OF.rgb("background",0,0,0,20);
	1.wait;
	OF.particle("activate", 0);					
	OF.particle("particleNeighborhood", 25);		
	OF.rgb("particleConnectionsColor",255,255,255,25);	
	OF.rgb("particlesColor",255,255,255,255);		
	4000.do{
		OF.particle("add", ~width/2.asInteger, ~height/2.asInteger, 0.1, 10);
		0.001.wait;}
		
}.fork

//:bounce phrase
~bounce1 = Task({
	inf.do	{ |i|
		OF.particle("bounce", 0, 0, 500,500);
		0.02.wait;
	}
});
//:play bounce
~bounce1.play;
//:stop bounce
~bounce1.stop;

//:=====================
//:    SLIDE  SHOW
//:=====================

//:Collage
~collage = Task({
	var 	delayTime = 0.1, countX = 0, countY = 0, fotoWidth = ~width/2.5, fotoHeight = ~width/2.5;
	loop	{
		60.do	{ |i|
			OF.img(rrand(0,60).asInteger, countX, countY, fotoWidth, fotoHeight);
			countX = countX + fotoWidth;
			if (countX>~width, {countX = 0;countY = countY + fotoHeight});
			if (countY>~height, {countY = 0;});
			delayTime.wait;
		}
	}
});
~collage.play;
//:stop collage
~collage.stop;
//====================================================
//:lyon rotate 2d
~rotate2d = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, 3*~width/4, ~height/2, 0, 0, 0, i);
		0.02.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, ~width/4, ~height/2, 0, 0, 0, i);		0.02.wait;
	}
});
//:play rotate2d
~rotate2d.play;
//:stop rotate2d
~rotate2d.stop;
~position.play;
//:lyon position
~position = Task({
	var imageSize = 200, waitTime = 0.005 ;
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		
		waitTime.wait;
		OF.img(rrand(0,60).asInteger,2*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		
		waitTime.wait;
		OF.img(rrand(0,60).asInteger,3*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		
		waitTime.wait;
		OF.img(rrand(0,60).asInteger,4*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		
		waitTime.wait;
	}
});
~position.play;
//:play position
~position.play;
//:stop position
~position.stop;

//:old rotate with fork
{
	inf.do{|i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, 3*~width/4, ~height/2, 0, 0, 0, i);
		0.02.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, ~width/4, ~height/2, 0, 0, 0, i);		0.02.wait;
	}
}.fork
//:lyon position
{
	inf.do{|i|
		OF.img(rrand(0,60).asInteger,~width/2, 3*~height/4,200,200);		0.02.wait;
		OF.img(rrand(0,60).asInteger,~width/2, ~height/4,200,200);		0.02.wait;
	}
}.fork


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
		if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
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
