//:Joda - Live Performance
//:=======================
//:Live Coding
OF.background(40,40,40);
OF.rgb("background",0,0,0,0);
OF.background(0,0,0);
OF.rgb("background",0,0,0,0);	//OF.rgb("background",R,G,B,A);

//:=========================
//:TYPOGRAPHY
//:========================
//:~typo
~typo = Task({
	var xPos, yPos = 20, count = 0;
	11500.do	{|i|
		xPos = 8*count;
		0.01.wait;
		if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
		count = count + 1;			
	}
});
~typo.play;
//:stop typo
~typo.stop;

//:~reverseTypo
~reverseTypo = Task({
	var xPos, yPos = ~height, count = 0;
	11500.do	{|i|
		xPos = (~width - (8*count));
		OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
		0.01.wait;
		if (xPos < 0, {xPos = ~width; count = 0; yPos = yPos -15} );
		count = count + 1;	
		xPos.postln;		
	}
});
~reverseTypo.play;
//:stop typo
~reverseTypo.stop;
//:typo auto loop
OF.background(0,0,0);
OF.rgb("background",0,0,0,0);	//OF.rgb("background",R,G,B,A);
{
	40.do {
		{
			var xPos, yPos = 20, count = 0;
			11500.do	{ |i|
				xPos = 8*count;
				OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
				0.01.wait;
				if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
				count = count + 1;
			}
		}.fork;
		rrand(2,14).wait;
	}
}.fork
//:==========
//:PARTICLES
//:==========

/* 
At this phrase we will stop everything else and we will make a transport to particles.
Initially the particles will be connected
*/
{
	OF.rgb("background",0,0,0,20);
	1.wait;
	OF.particle("activate", 1);					
	OF.particle("particleNeighborhood", 25);		
	OF.rgb("particleConnectionsColor",255,255,255,25);	
	OF.rgb("particlesColor",255,255,255,255);		
	1000.do{
		OF.particle("add", ~width/2.asInteger, ~height/2.asInteger, 0.1, 10);
		0.001.wait;}
		
}.fork

//:bounce phrase UNFINISHED
~bounce1 = Task({
	var step = 10, xStart, yStart, xEnd, yEnd;
	100.do	{ |i|
		OF.particle("bounce", 0 + step +, 0 + , 500,500);
		if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
		0.02.wait;
	}
});
//:play bounce
~bounce1.play;
//:stop bounce
~bounce1.stop;

//:=====================
//:SLIDE  SHOW
//:=====================

//:Collage
~collage = Task({
	var 	delayTime = 0.01, countX = 0, countY = 0, fotoWidth = ~width/15, fotoHeight = ~width/15;
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
//==============
//:rotate 2d
~rotate2d = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, 3*~width/4, ~height/2, 0, 0, 0, i);
		0.02.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, ~width/4, ~height/2, 0, 0, 0, i);		0.02.wait;
	}
});
~rotate2d.play;
//:stop rotate2d
~rotate2d.stop;
//==============
//:position
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
//:=================
//:EFFECTS
//:=================
//:noiseEffect
~noiseEffect = Task({
	OF.effect("noiseEffect", "true");
	loop{
		OF.effect("noiseEffect", "false");
		OF.background(0,0,0);		//OF.rgb("background",R,G,B,A);
			0.5.wait;
		OF.effect("noiseEffect", "true");
		0.5.wait;
	}	
});
~noiseEffect.play;
//:stop noiseEffect
~noiseEffect.stop;

//:mirrowEffect - 2 dimension
OF.effect("mirrowEffect", "fasle");

//:mirrowEffect with map
OF.img(62,0,0,~width,~height)
OF.img(62)


//:Mirrow at 4 
OF.effect("mirrowEffect2", "false");

//:move the map
OF.effect("mirrowEffect2", "true");
~moveMap = Task ({
	
	~width.do{ |i|
		OF.img(62, -1*i, 1*i);	
		0.05.wait;
	}	
});
~moveMap.play

//:=================
//:UNDER DEVELOPMENT
//:=================

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
SendSpectrogramData.stopPoller
SendAmpFreq.start
//SendSpectrogramData('fft').free;
//SendSpectrogramData('ofSpectrograph');
//SendSpectrogramData('ofSpectrograph').free;

//:Use SoundIn
'pure_clarinet'.playDef

//:numSketch
OF.interactWithSound("numSoundSketches", 4);		//	RANGE: 1 -> 999

//:OSC responder from omer
~mertrig = OSCresponder.new(nil, "/mertrig", { 
	arg time, resp, msg; 
	"hi omer".postln;

} ).add; 

//:==============
//:--STARTUP FILE
//:==============
~width = 1280;
~height = 1024;

~myString = File.use("/Users/ari/Projects/artistic/performance/JodaLyon/data/text/text", "r", { |f| f.readAllString.postln })
