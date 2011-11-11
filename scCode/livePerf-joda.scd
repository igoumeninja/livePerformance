//:Joda - Live Performance
//:=======================
//:Live Coding
OF.background(40,40,40);
OF.rgb("background",0,0,0,40);
OF.background(0,0,0);
OF.rgb("background",0,0,0,0);	//OF.rgb("background",R,G,B,A);

//:=========================
//:TYPOGRAPHY
//:========================
//:~typo
~typoNormal = Task({
	var xPos, yPos = 20, count = 0;
	11500.do	{|i|
		xPos = 8*count;
		OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
		0.01.wait;
		if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
		count = count + 1;			
	}
});
~typoNormal.play;
//:stop typo
~typoNormal.stop;

//:~reverseTypo
~typoInverse = Task({
	var xPos, yPos = ~height, count = 0;
	11500.do	{|i|
		xPos = (~width - (8*count));
		OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
		0.01.wait;
		if (xPos < 0, {xPos = ~width; count = 0; yPos = yPos -15} );
		count = count + 1;	
		//xPos.postln;		
	}
});
~typoInverse.play;
//:stop typo
~typoInverse.stop;
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

//:Add 1000 particles
/* 
At this phrase we will stop everything else and we will make a transport to particles.
Initially the particles will be connected
*/
{
	OF.rgb("background",0,0,0,25);
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

//:Collage aside
~collage = Task({
	var 	delayTime = 0.04, countX = 0, countY = 0, fotoWidth = ~width/15, fotoHeight = ~width/15;
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
//:Collage random at the grid UNDER DEVELOPMENT
~collageRandGrid = Task({
	var 	delayTime = 0.01, countX = 0, countY = 0, fotoWidth = ~width/15, fotoHeight = ~width/15;
	loop	{
		(1..59).scramble.do	{ |i|
			OF.img(rrand(0,60).asInteger, countX, countY, fotoWidth, fotoHeight);
			countX = countX + fotoWidth;
			if (countX>~width, {countX = 0;countY = countY + fotoHeight});
			if (countY>~height, {countY = 0;});
			delayTime.wait;
		}
	}
});
~collageRandGrid.play;
//:stop collage
~collageRandGrid.stop;

//==============
//:rotate 2d
~rotate2d = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, 3*~width/4, ~height/2, 0, 0, 0, i);
		0.04.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, ~width/4, ~height/2, 0, 0, 0, i);		0.04.wait;
	}
});
~rotate2d.play;
//:stop rotate2d
~rotate2d.stop;
//==============
//:position
~positions = Task({
	var imageSize = 200, waitTime = 0.02;
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
~positions.play;
//:play position
~positions.play;
//:stop position
~positions.stop;
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
OF.effect("noiseEffect", "false");
~noiseEffect.stop;

//:mirrowEffect - 2 dimension
OF.effect("mirrowEffect", "true");

//:Mirrow effect2 FALSE
OF.effect("mirrowEffect2", "false");
//:move the map
~moveMap.stop;
//~city = [101, 102, 103, 104].at(rrand(0,3).asInteger);

OF.rgb("background",0,0,0,0);
OF.effect("mirrowEffect2", "true");
~moveMap = Task ({	
	var city, direction;
	city = [101, 102, 103, 104].at(rrand(0,3).round);
	direction = [-1,1].at(rrand(0,1).round).postln;
	~width.do{ |i|
		
		//OF.img(103, direction*i + ~width/2, -1*i + ~height/2);
		OF.img(103, direction*i, direction*i);	
		//OF.img(103, -1*i + ~width, -1*i + ~height);	
		//OF.img(103, -1*i + ~width, -1*i + ~height);	
		0.02.wait;
	}	
});
~moveMap.play
//:zoom
~zoom = Task ({	
	loop{
		9.do{ |i|
		
			OF.img(109 -i,0,0,~width, ~height);	
			1.wait;
		}	
	}
});
~zoom.play;
//:ZoomIn ZoomOut phrase
//:-

{
	90.do{ |i|
			OF.img(100+i,0,0,~width,~height);
			0.04.wait;
	};
	90.do{ |i|
			OF.img(190-i,0,0,~width,~height);
			0.04.wait;		
	}
}.fork


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
"startup is working".postln;
~width = 680;
~height = 1024;

// Take a string from file
~myString = File.use("/Users/ari/Projects/artistic/performance/JodaLyon/data/text/text", "r", { |f| f.readAllString})

// Sound Interaction
Task({
		if (not(s.serverRunning)) { s.boot };
		s.doWhenBooted {
			SendAmpFreq.start;
		};					
	}).play;