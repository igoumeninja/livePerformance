//:RESPONDERS - SETUP

// ==================
// GENERAL
~blackBackgroundResp = OSCresponderNode(n, '/blackBackground', { |t,r,msg| 
	OF.background(0,0,0);				
}).add;

~blackBackgroundAlphaResp = OSCresponderNode(n, '/blackBackgroundAlpha', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.background(0,0,0,20);
	},{
		OF.background(0,0,0,0);
	});
}).add;

~alphaBackgroundSpec = ControlSpec(0, 125, \lin);
~alphaBackgroundResp = OSCresponderNode(n, '/alphaBackground', { | time, resp, msg| 
	OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
} ).add; 

~feedbackResp = OSCresponderNode(n, '/feedback', { |t,r,msg| 
	OF.feedback("activate", msg[1]);
}).add;

~xFeedbackSpec = ControlSpec(-5, 5, \lin);
~yFeedbackSpec = ControlSpec(5, -5, \lin);
~feedbackXYResp = OSCresponderNode(n, '/feedbackXY', { | time, resp, msg| 
	//"test".postln;
	OF.feedback("speedXY", ~xFeedbackSpec.map(msg[1]), ~yFeedbackSpec.map(msg[2]));
} ).add; 

~mirrowEffect1Resp = OSCresponderNode(n, '/mirrowEffect1', { |t,r,msg|
	OF.effect("mirrowEffect1", msg[1]); 
}).add;

~mirrowEffect2Resp = OSCresponderNode(n, '/mirrowEffect2', { |t,r,msg|
	OF.effect("mirrowEffect2", msg[1]); 
}).add;

~thisProcessStopResp = OSCresponderNode(n, '/thisProcessStop', { |t,r,msg|
	thisProcess.stop;
}).add;

// =================
// TYPOGRAPHY

~typoNormalResp = OSCresponderNode(n, '/typoNormal', { |t,r,msg|
	Task({
		var xPos, yPos = 20, count = 0;
		11500.do	{|i|
			xPos = 8*count;
			OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
			0.02.wait;
			if (xPos > ~width, {xPos = 0; count = 0; yPos = yPos +15} );
			count = count + 1;			
		}
	}).play;
}).add;

~typoInverseResp = OSCresponderNode(n, '/typoInverse', { |t,r,msg|
	~typoInverse = Task({
		var xPos, yPos = ~height, count = 0;
		11500.do	{|i|
			xPos = (~width - (8*count));
			OF.writeString(~myString.at(i).asString,xPos,yPos,255,255,255,225);
			0.03.wait;
			if (xPos < 0, {xPos = ~width; count = 0; yPos = yPos -15} );
			count = count + 1;	
		}
	});	
	~typoInverse.play;
}).add;

// ==================
// CUT MOTION
s.boot.doWhenBooted {
	b = Buffer.alloc(s, 512);
};

~fftTrigerResp = OSCresponderNode(n, '/fftTriger', { |t,r,msg| 
	if( msg[1] == 1,{
		~fftTriger = { | threshold = 0.9, out = 100 |
			var sig, chain, onsets, pips;
			sig = In.ar(8);	
			chain = FFT(b, sig);
			onsets = Onsets.kr(chain, threshold, \rcomplex);
			SendReply.kr(onsets, \onset);
		}.play;

	},{
		~fftTriger.free;
	});
}).add;

~onsetResp = OSCresponder.new(nil, "onset", { 
	arg time, resp, msg; 
	OF.img(rrand(0,60).asInteger,0,0,~width, ~height);
	OF.img(rrand(0,60).asInteger,0,0,~width, ~height);
}).add; 

~columns = 10;
~collageRandom = Task({
	var 	delayTime = 0.04, xPos, yPos, fotoWidth = ~width/~columns, fotoHeight = ~width/~columns;
	xPos = Array.new(pow(~columns,2));
	yPos = Array.new(pow(~columns,2));	
	~columns.do{|j|
		~columns.do{|i|
			xPos.add((~width*(i)/~columns).round);
			yPos.add((~width*(i)/~columns).round);
		};
	};
	//xPos = xPos.scramble.postln;
	//yPos = yPos.scramble;	
	loop	{
		pow(~columns,2).do	{ |i|
			OF.img(rrand(0,60).asInteger, xPos[rrand(0,pow(~columns,2))], yPos[rrand(0,pow(~columns,2))], fotoWidth, fotoHeight);
			delayTime.wait;
		}
	}
});
~collageRandomResp = OSCresponderNode(n, '/collageRandom', { |t,r,msg| 
	if( msg[1] == 1,{
		~collageRandom.play;
	},{
		~collageRandom.stop;
	});
}).add;

~collageNormal = Task({
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

~collageNormalResp = OSCresponderNode(n, '/collageNormal', { |t,r,msg| 
	if( msg[1] == 1,{
		~collageNormal.play;
 	},{
		~collageNormal.stop;
	});
}).add;

~fourImages = Task({
	var imageSize = 200, waitTime = 0.02;
	inf.do	{ |i|
		OF.img(rrand(0,60).round,~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		waitTime.wait;
		OF.img(rrand(0,60).round,2*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		waitTime.wait;
		OF.img(rrand(0,60).round,3*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		waitTime.wait;
		OF.img(rrand(0,60).round,4*~width/5-(imageSize/2), ~height/2-(imageSize/2), imageSize, imageSize);		waitTime.wait;
	}
});
~fourImagesResp = OSCresponderNode(n, '/fourImages', { |t,r,msg| 
	if( msg[1] == 1,{
		~fourImages.play;
	},{
		~fourImages.stop;
	});
}).add;

~rotate2d = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, 3*~width/4, ~height/2, 0, 0, 0, i);
		0.04.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, ~width/4, ~height/2, 0, 0, 0, i);		0.04.wait;
	}
});
~rotate2dResp = OSCresponderNode(n, '/rotate2d', { |t,r,msg| 
	if( msg[1] == 1,{
		~rotate2d.play;
	},{
		~rotate2d.stop;
	});
}).add;


// ==================
// SOUND INTERACTION
~setupSound = Task({
	if (not(s.serverRunning)) { s.boot };
	s.doWhenBooted {
		SendAmpFreq.start;
		0.1.wait;
		~soundSynth = Synth("SendAmpPitch");	
	};					
});

~setupSoundResp = OSCresponderNode(n, '/setupSound', { |t,r,msg| 
	if( msg[1] == 1,{
		"start posting".postln;
		~soundSynth = Synth("SendAmpPitch");
	},{
		"free synth from SendAmpFreq".postln;
		~soundSynth.free;
	});
}).add;

~viewSoundResp = OSCresponderNode(n, '/viewSound', { |t,r,msg| 
	OF.interactWithSound("activate", msg[1].asInteger);
}).add;

~glBeginTypeResp = OSCresponderNode(n, '/glBeginType', { | time, resp, msg| 
	OF.interactWithSound("glBeginType", msg[1].asInteger);
} ).add; 

~numSoundSketchesSpec = ControlSpec(1, 500, \lin);
~numSoundSketchesResp = OSCresponderNode(n, '/numSoundSketches', { | time, resp, msg| 
	OF.interactWithSound("numSoundSketches", ~numSoundSketchesSpec.map(msg[1]).asInteger);
} ).add; 

~maxFreqInSpec = ControlSpec(20, 2000, \lin);
~maxFreqInResp = OSCresponderNode(n, '/maxFreqIn', { | time, resp, msg| 
	OF.interactWithSound("maxFreqIn", ~maxFreqInSpec.map(msg[1]));
} ).add; 

~maxAmpInSpec = ControlSpec(0.001, 0.5, \lin);
~maxAmpInResp = OSCresponderNode(n, '/maxAmpIn', { | time, resp, msg| 
	OF.interactWithSound("maxAmpIn", ~maxAmpInSpec.map(msg[1]));
}).add; 

~elasticitySpec = ControlSpec(0.001, 2, \lin);
~elasticityResp = OSCresponderNode(n, '/elasticity', { | time, resp, msg| 
	OF.interactWithSound("maxSoundElasticity", ~elasticitySpec.map(msg[1]));
} ).add; 

~btw1 = Task({
	OF.background(0, 0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,55);				
		0.04.wait;	
		OF.rgb("sound",0,0,0,55);
		0.04.wait;	
	});
});


~btw1Resp = OSCresponderNode(n, '/btw1', { |t,r,msg| 
	if( msg[1] == 1,{
		~btw1.play;
	},{
		~btw1.stop;
	});
}).add;

~btw2 = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,255);				
		0.04.wait;	
		OF.rgb("sound",0,0,0,255);
		0.04.wait;	
	});
});


~btw2Resp = OSCresponderNode(n, '/btw2', { |t,r,msg| 
	if( msg[1] == 1,{
		~btw2.play;
	},{
		~btw2.stop;
	});
}).add;

~btw3 = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.background(0, 0, 0);
		OF.rgb("sound",255,255,255,255);				
		0.04.wait;	
		OF.background(255,255,255);
		OF.rgb("sound",0,0,0,255);
		0.04.wait;	
	});
});


~btw3Resp = OSCresponderNode(n, '/btw3', { |t,r,msg| 
	if( msg[1] == 1,{
		~btw3.play;
	},{
		~btw3.stop;
	});
}).add;

~elastTask = Task({
	inf.do({
		OF.interactWithSound("maxSoundElasticity",1.0);			0.5.wait;	
		OF.interactWithSound("maxSoundElasticity",0.01);		0.5.wait;			
	});
});
~elastTaskResp = OSCresponderNode(n, '/elastTask', { |t,r,msg| 
	if( msg[1] == 1,{
		~elastTask.play;
	},{
		~elastTask.stop;
	});
}).add;

~blackWhiteSoundResp = OSCresponderNode(n, '/blackWhiteSound', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.rgb("sound",255,255,255,120);
	},{
		OF.rgb("sound",0,0,0,120);				
	});
}).add;

~aSoundSpec = ControlSpec(0, 255, \lin);
~aSoundResp = OSCresponderNode(n, '/aSound', { | time, resp, msg| 
	OF.rgb("sound",255,255,255,~aSoundSpec.map(msg[1]).round);
} ).add; 

~padSketchResp = OSCresponderNode(n, '/padSketch', { |t,r,msg| 
	OF.interactWithSketch("activate", msg[1].asInteger);
}).add;

~xPadSpec = ControlSpec(0, ~width, \lin);
~yPadSpec = ControlSpec(~height, 0, \lin);
~padSketchXYResp = OSCresponderNode(n, '/padSketchXY', { | time, resp, msg| 
	OF.interactWithSketch("padXY", ~xPadSpec.map(msg[2]).asInteger, ~yPadSpec.map(msg[1]).asInteger);
} ).add; 

// ==================
// PARTICLES

~activateParticlesResp = OSCresponderNode(n, '/activateParticles', { |t,r,msg| 
	OF.particle("activate", msg[1].round);
}).add;

~forceRadiusSpec = ControlSpec(0, 500, \lin);
~forceRadiusResp = OSCresponderNode(n, '/forceRadius', { | time, resp, msg|
	OF.particle("forceRadius", ~forceRadiusSpec.map(msg[1]).round);
}).add; 

~particleNeighborhoodSpec = ControlSpec(0, 30, \lin);
~particleNeighborhoodResp = OSCresponderNode(n, '/particleNeighborhood', { | time, resp, msg|
	OF.particle("particleNeighborhood", ~particleNeighborhoodSpec.map(msg[1]).round);
}).add; 

~particleConnectionsAlphaSpec = ControlSpec(0, 25, \lin);
OSCresponderNode(n, '/particleConnectionsAlpha', { | time, resp, msg|
	OF.particle("conColor", ~particleConnectionsAlphaSpec.map(msg[1]).round);
}).add; 

~particlesAlphaSpec = ControlSpec(0, 255, \lin);
~particlesAlphaResp = OSCresponderNode(n, '/particlesAlpha', { | time, resp, msg|
	OF.particle("dotColor", ~particlesAlphaSpec.map(msg[1]).round);
}).add; 

~addParticlesResp = OSCresponderNode(n, '/addParticles', { |t,r,msg| 
	{
		500.do{
			OF.particle("add", ~width/2.asInteger, ~height/2.asInteger, 0.1, 10);
			0.04.wait;
		}	
	}.fork
}).add;

~iPadPushResp = OSCresponderNode(n, '/iPadPush', { |t,r,msg| 
	OF.particle("iPadPush", msg[1]);
}).add;

//~xPushSpec = ControlSpec(0, ~width, \lin);
//~yPushSpec = ControlSpec(~height, 0, \lin);
//OSCresponderNode(n, '/particlesXY', { | time, resp, msg| 
//	OF.interactWithSketch("padXY", ~xPushSpec.map(msg[2]).asInteger, ~yPushSpec.map(msg[1]).asInteger);
//} ).add; 

OSCresponder.new(nil, "onset", { 
	arg time, resp, msg; 
	OF.img(rrand(0,60).asInteger,0,0,~width, ~height);
	OF.img(rrand(0,60).asInteger,0,0,~width, ~height);
}).add; 


// ==================
// VIDEO

OSCresponderNode(n, '/playVideo', { | time, resp, msg|
	OF.video("playVideo", msg[1]);
}).add; 

OSCresponderNode(n, '/deleteVideo', { | time, resp, msg|
	OF.video("deleteVideo");
}).add; 

~rVideoSpec = ControlSpec(0, 255, \lin);
OSCresponderNode(n, '/rVideo', { | time, resp, msg|
	OF.video("rVideo", ~rVideoSpec.map(msg[1]).asInteger);
}).add; 

~gVideoSpec = ControlSpec(0, 255, \lin);
OSCresponderNode(n, '/gVideo', { | time, resp, msg|
	OF.video("gVideo", ~gVideoSpec.map(msg[1]).asInteger);
}).add; 

~bVideoSpec = ControlSpec(0, 255, \lin);
OSCresponderNode(n, '/bVideo', { | time, resp, msg|
	OF.video("bVideo", ~bVideoSpec.map(msg[1]).asInteger);
}).add; 

~aVideoSpec = ControlSpec(0, 255, \lin);
OSCresponderNode(n, '/aVideo', { | time, resp, msg|
	OF.video("aVideo", ~aVideoSpec.map(msg[1]).asInteger);
}).add; 

//:==================

//:WIREFACE RESPONDERS
//:===================

// colorMode
OSCresponderNode(n, '/colorMode', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.colorMode(1);
	},{
		OF.colorMode(0);
	});
}).add;



// Scale
~scaleSpec = ControlSpec(1, 10, \lin);
OSCresponderNode(n, '/scale', { |t,r,msg| 
	OF.scale(~scaleSpec.map(msg[1]));
}).add;

// Randomness
~randomnessSpec = ControlSpec(0,0.1, \lin);
OSCresponderNode(n, '/randomness', { |t,r,msg| 
	OF.randomness(~randomnessSpec.map(msg[1]));
}).add;


// Rotation
~rotYSpec = ControlSpec(360.0, 0.0, \lin);
~rotXSpec = ControlSpec(-180.0, 180.0, \lin);
OSCresponderNode(n, '/rotate', { |t,r,msg| 
	y.set(\freqShift, ~rotXSpec.map(msg[1]));	
	OF.rotY(~rotYSpec.map(msg[2]));
	OF.rotX(~rotXSpec.map(msg[1]));
}).add;

// Rotation angle
~rotAngleSpec = ControlSpec(1,15, \lin);
OSCresponderNode(n, '/rotAngle', { |t,r,msg| 
	~angleStep = ~rotAngleSpec.map(msg[1]);
}).add;

// Rotate circle
OSCresponderNode(n, '/loopRotate', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.background(0);
		OF.aBack(0);
		~pat.play;
		y.set(\bufnum, rrand(0,5).asInteger);		
	},{
		~pat.stop;
		y.set(\bufnum, rrand(0,5).asInteger);		
	});
}).add;

// Speed Rotation
~speedRotSpec = ControlSpec(0.01,2, \lin);
OSCresponderNode(n, '/speedRot', { |t,r,msg| 
	~rotDurValue = ~speedRotSpec.map(msg[1]);
	~rotDur = PatternProxy(Pseq([~rotDurValue],inf));		~pat = Pdef(\rotDurPat, 
		Posc(
			\dur, ~rotDur,
		 	\dest,  [NetAddr.localAddr, NetAddr("localhost", 57120)],
			\msg, Pseq([['rotY', Pseq([1, 2, 3], 10)]], inf)
		);
	);	
}).add;

// Camera
~camDepthSpec = ControlSpec(0,10, \lin);
OSCresponderNode(n, '/camDepth', { |t,r,msg| 
	OF.camDepth(~camDepthSpec.map(msg[1]));
}).add;

~camAngleSpec = ControlSpec(-30,30, \lin);
OSCresponderNode(n, '/camAngle', { |t,r,msg| 
	OF.camAngle(~camAngleSpec.map(msg[1]).asInteger);
}).add;

// Background Colors
~rBackSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/rBack', { |t,r,msg| 
	OF.rBack(~rBackSpec.map(msg[1]));
}).add;
~gBackSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/gBack', { |t,r,msg| 
	OF.gBack(~gBackSpec.map(msg[1]));
}).add;
~bBackSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/bBack', { |t,r,msg| 
	OF.bBack(~bBackSpec.map(msg[1]));
}).add;
~aBackSpec = ControlSpec(0,55, \lin);
OSCresponderNode(n, '/aBack', { |t,r,msg| 
	OF.aBack(~aBackSpec.map(msg[1]));
}).add;

// WireFace Colors
~rFaceSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/rFace', { |t,r,msg| 
	OF.rFace(~rFaceSpec.map(msg[1]));
}).add;
~gFaceSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/gFace', { |t,r,msg| 
	OF.gFace(~gFaceSpec.map(msg[1]));
}).add;
~bFaceSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/bFace', { |t,r,msg| 
	OF.bFace(~bFaceSpec.map(msg[1]));
}).add;
~aFaceSpec = ControlSpec(0,255, \lin);
OSCresponderNode(n, '/aFace', { |t,r,msg| 
	OF.aFace(~aFaceSpec.map(msg[1]));
}).add;

// Step
OF.step(2);	OF.glBeginCase(0);

// glBegin
OSCresponderNode(n, '/glBeginCase', { |t,r,msg| 
	OF.glBeginCase(msg[1].asInt);
}).add;

//:Sound
// Batuhan
~ampButSpec = ControlSpec(0.001, 6, \lin);
OSCresponderNode(n, '/ampBut', { |t,r,msg| 
	x.set(\amp, ~ampButSpec.map(msg[1]));
	~ampButSpec.map(msg[1]).postln;
}).add;

~tempoButSpec = ControlSpec(0.001, 5, \lin);
OSCresponderNode(n, '\butTempo', { |t,r,msg| 
	msg.postln;
	x.set(\tempo, ~tempoButSpec.map(msg[1]));
	~tempoButSpec.map(msg[1]).postln;
}).add;	


// Buffers
OSCresponderNode(n, '/bufNum/1/1', { |t,r,msg| 
	msg.postln;
	y.set(\bufnum, 0);
}).add;	

OSCresponderNode(n, '/bufNum/1/2', { |t,r,msg| 
	msg.postln;
	y.set(\bufnum, 1);
}).add;	

OSCresponderNode(n, '/bufNum/1/3', { |t,r,msg| 
	msg.postln;
	y.set(\bufnum, 2);
}).add;	

OSCresponderNode(n, '/bufNum/1/4', { |t,r,msg| 
	msg.postln;
	y.set(\bufnum, 3);
}).add;	

OSCresponderNode(n, '/bufNum/1/5', { |t,r,msg| 
	msg.postln;
	y.set(\bufnum, 4);
}).add;	

~bufRateSpec = ControlSpec(-5, 5, \lin);
OSCresponderNode(n, '/bufRate', { |t,r,msg| 
	y.set(\rate, ~bufRateSpec.map(msg[1]));
	~bufRateSpec.map(msg[1]).postln;
}).add;

~ampBufSpec = ControlSpec(0, 5, \lin);
OSCresponderNode(n, '/ampBuf', { |t,r,msg| 
	y.set(\level, ~ampBufSpec.map(msg[1]));
}).add;

~freqShiftSpec = ControlSpec(-2000, 2000, \lin);
OSCresponderNode(n, '/freqShift', { |t,r,msg| 
	y.set(\freqShift, ~freqShiftSpec.map(msg[1]));
}).add;


// Crotale
OSCresponderNode(n, '/crataleNote/1/1', { |t,r,msg| 
	if( msg[1] == 1,{
		Synth("PMCrotale", ["midi", 60, "tone", 3]);
		OF.rFace(255);
		OF.gFace(0);
		OF.bFace(255);
	},{
		Synth("PMCrotale", ["midi", 72, "tone", 1]);
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(255);
	});

}).add;	
OSCresponderNode(n, '/crataleNote/2/1', { |t,r,msg| 
	if( msg[1] == 1,{
		Synth("PMCrotale", ["midi", 62, "tone", 3]);
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(0);
	},{
		Synth("PMCrotale", ["midi", 74, "tone", 3]);	
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(255);
	});

}).add;	
OSCresponderNode(n, '/crataleNote/3/1', { |t,r,msg| 
	if( msg[1] == 1,{
		Synth("PMCrotale", ["midi", 64, "tone", 3]);
		OF.rFace(255);
		OF.gFace(0);
		OF.bFace(0);
	},{
		Synth("PMCrotale", ["midi", 76, "tone", 2]);
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(255);
	});

}).add;	
OSCresponderNode(n, '/crataleNote/4/1', { |t,r,msg| 
	if( msg[1] == 1,{
		Synth("PMCrotale", ["midi", 67, "tone", 3]);
		OF.rFace(0);
		OF.gFace(255);
		OF.bFace(0);
	},{
		Synth("PMCrotale", ["midi", 79, "tone", 3]);
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(255);
	});

}).add;	
OSCresponderNode(n, '/crataleNote/5/1', { |t,r,msg| 
	if( msg[1] == 1,{
		Synth("PMCrotale", ["midi", 71, "tone", 3]);
		OF.rFace(0);
		OF.gFace(0);
		OF.bFace(255);
	},{
		Synth("PMCrotale", ["midi", 83, "tone", 4]);	
		OF.rFace(255);
		OF.gFace(255);
		OF.bFace(255);
	});

}).add;	

// Padovani

~ampPadSpec = ControlSpec(0, 5, \lin);
OSCresponderNode(n, '/ampPad', { |t,r,msg| 
	z.set(\level, ~ampPadSpec.map(msg[1]));
}).add;

~trigDurSpec = ControlSpec(0.001, 10, \lin);
OSCresponderNode(n, '/trigDur', {|t,r,msg| 
	 z.set(\trigDur, ~trigDurSpec.map(msg[1])) ;
	 ~trigDurSpec.map(msg[1]).postln;
	 }).add;
~trigFreqSpec = ControlSpec(0.001, 100, \lin);
OSCresponderNode(n, '/trigFreq', {|t,r,msg| 
	 z.set(\trigFreq, ~trigFreqSpec.map(msg[1]));
	 ~trigFreqSpec.map(msg[1]).postln;
	 }).add;
~sawFreqSpec = ControlSpec(0.001, 100, \lin);
OSCresponderNode(n, '/sawFreq', {|t,r,msg| 
	z.set(\sawFreq, ~sawFreqSpec.map(msg[1])) ;
	 ~sawFreqSpec.map(msg[1]).postln;
	 }).add;
~sizeGrainSpec = ControlSpec(2, 0.001, \lin);
OSCresponderNode(n, '/sizeGrain', {|t,r,msg| 
	 z.set(\sizeGrain, ~sizeGrainSpec.map(msg[1])) ;
	 ~sizeGrainSpec.map(msg[1]).postln;
	 }).add;
~roomSizeSpec = ControlSpec(0.001, 0.32, \lin);
OSCresponderNode(n, '/roomSize', {|t,r,msg| 
	  z.set(\roomSize, ~roomSizeSpec.map(msg[1])) ;
	 ~roomSizeSpec.map(msg[1]).postln;
	 }).add;
~signalOutputSpec = ControlSpec(10, 1, \lin);
OSCresponderNode(n, '/signalOutput', {|t,r,msg| 
	  z.set(\signalOutput, ~signalOutputSpec.map(msg[1])) ;
	 ~signalOutputSpec.map(msg[1]).postln;
	 }).add;


// Recieve from oF CV
OSCresponder(nil, "mute", { | t, r, msg | 
			{	
				y.set(\level, (0));
			}.fork;
	}
).add;
OSCresponder(nil, "intro", { | t, r, msg | 
			{	
				y.set(\level, 1);
			}.fork;
	}
).add;


// Centroid
~centroidXSpec = ControlSpec(-5, 5, \lin);
OSCresponder(nil, "centroid", { | t, r, msg | 
			{	
				y.set(\rate, ~centroidXSpec.map(msg[1]));
				x.set(\tempo, ~tempoButSpec.map(msg[2]));
			}.fork;
	}
).add;

//:Control Padovani
~trigDurSpec = ControlSpec(0.001, 10, \lin);
OSCresponderNode(n, '/trigDur', { |t,r,msg| 
	p.set(\trigDur, ~trigDurSpec.map(msg[1])*0.1);
	~trigDurSpec.map(msg[1]).postln;
}).add;


//:Testing 
NetAddr("127.0.0.1", 57120).sendMsg("\tempo", 0.1);

//:---
z.set(\trigDur, 0.1) ;

//:OLD CONTROLLERS
//:Create OSCresponders
/*
Create OSCResponders for recieving OSC from iPod and 
forward them with a good syntax to openFrameworks.

Aris Bezas Mon, 01 November 2010, 07:41PM
*/


//~addr = NetAddr("127.0.0.1", 12345);
~addr = NetAddr("192.168.1.65", 12345);
Event.addEventType(\osc, { ~dest.sendBundle(~latency, ~msg); });

//page1
OSCresponder.new(nil, "/r8", { arg time, resp, msg; OF.rgb("r8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g8", { arg time, resp, msg; OF.rgb("g8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b8", { arg time, resp, msg; OF.rgb("b8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a8", { arg time, resp, msg; OF.rgb("a8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r7", { arg time, resp, msg; OF.rgb("r7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g7", { arg time, resp, msg; OF.rgb("g7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b7", { arg time, resp, msg; OF.rgb("b7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a7", { arg time, resp, msg; OF.rgb("a7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r6", { arg time, resp, msg; OF.rgb("r6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g6", { arg time, resp, msg; OF.rgb("g6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b6", { arg time, resp, msg; OF.rgb("b6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a6", { arg time, resp, msg; OF.rgb("a6", msg[1].round);} ).add; 
//page2
OSCresponderNode.new(nil, "/setupSound", { | time, resp, msg| ~setupSoundTask.start;}).add; 
OSCresponderNode.new(nil, "/freeSound", { | time, resp, msg| ~soundSynth.free;}).add; 
OSCresponderNode.new(nil, "/viewSoundChanels", { | time, resp, msg| OF.interactWithSound("activate", msg[1]);}).add; 
OSCresponder.new(nil, "/soundLines", { arg time, resp, msg; OF.interactWithSound("glBeginType", msg[1].round);} ).add; 
OSCresponder.new(nil, "/numSoundSketches", { arg time, resp, msg; OF.interactWithSound("numSoundSketches", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxFreqIn", { arg time, resp, msg; OF.interactWithSound("maxFreqIn", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxAmpIn", { arg time, resp, msg; OF.interactWithSound("maxAmpIn", msg[1]);} ).add; 
OSCresponder.new(nil, "/elasticity", { arg time, resp, msg; OF.interactWithSound("maxSoundElasticity", msg[1]);} ).add; 

//+++++++++++++++++++++++++++++++++++++++++++++++
OSCresponder.new(nil, "/soundBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~soundBWTask.stop;OF.rgb("sound",255,255,255,20);OF.rgb("background",0,0,0,0);};
	if (msg[1] == 1)	{~soundBWTask.start};
} ).add; 
OSCresponder.new(nil, "/backBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backBWTask.stop;	OF.background(0,0,0);	OF.rgb("sound",255,255,255,25);OF.rgb("background",0,0,0,30);};
	if (msg[1] == 1)	{~backBWTask.start};
} ).add; 
OSCresponder.new(nil, "/elastTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~elastTask.stop};
	if (msg[1] == 1)	{~elastTask.start};
} ).add; 
OSCresponder.new(nil, "/backTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backgroundTask.stop;OF.rgb("background",0,0,0,255);OF.rgb("sound",255,255,255,125);	};
	if (msg[1] == 1)	{~backgroundTask.start};
} ).add; 


~setupSoundTask = Task({
					if (not(s.serverRunning)) { s.boot };
					s.doWhenBooted {
						SendAmpFreq.start;
						0.1.wait;
						~soundSynth = Synth("SendAmpPitch");	
					};					
				});
~soundBWTask = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;	
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;			
	});
});
~backBWTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});
~elastTask = Task({
	inf.do({
//		OF.interactWithSound("maxSoundElasticity",0.1);					
//		0.5.wait;	
		OF.interactWithSound("maxSoundElasticity",1);					
		0.3.wait;	
		OF.interactWithSound("maxSoundElasticity",0.01);					
		0.7.wait;			
	});
});

~backgroundTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});


				
//page3

OSCresponderNode.new(nil, "/particles", { | time, resp, msg| OF.particle("activate", msg[1]);}).add; 
OSCresponderNode.new(nil, "/forceRadius", { | time, resp, msg| OF.particle("forceRadius", msg[1]);}).add; 
OSCresponderNode.new(nil, "/particleNeighborhood", { | time, resp, msg| OF.particle("particleNeighborhood", msg[1].round);}).add; 
OSCresponder.new(nil, "/r1", { arg time, resp, msg; OF.rgb("r1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g1", { arg time, resp, msg; OF.rgb("g1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b1", { arg time, resp, msg; OF.rgb("b1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a1", { arg time, resp, msg; OF.rgb("a1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r2", { arg time, resp, msg; OF.rgb("r2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g2", { arg time, resp, msg; OF.rgb("g2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b2", { arg time, resp, msg; OF.rgb("b2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a2", { arg time, resp, msg; OF.rgb("a2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/conBW", { arg time, resp, msg; OF.rgb("r1", 255*msg[1]);OF.rgb("g1", 255*msg[1]);OF.rgb("b1", 255*msg[1]);} ).add; 
OSCresponder.new(nil, "/particlesBW", { arg time, resp, msg; OF.rgb("r2", 255*msg[1]);OF.rgb("g2", 255*msg[1]);OF.rgb("b2", 255*msg[1]);} ).add; 

OSCresponder.new(nil, "/BWtask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~particlesBWTask.stop;OF.rgb("particleConnections",0,0,0);};
	if (msg[1] == 1)	{~particlesBWTask.start};
} ).add; 
OSCresponder.new(nil, "/iPodPush", { arg time, resp, msg; 
	if (msg[1] == 0)	{OF.particle("iPodPush", msg[1])};
	if (msg[1] == 1)	{OF.particle("iPodPush", msg[1])};
} ).add; 

~particlesBWTask = Task({
	inf.do({
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;	
		OF.rgb("particleConnections",0,0,0);					
		0.001.wait;	
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;			
	});
});


// ########## FEEDBACK  #############
OSCresponder.new(nil, "/feedback", { arg time, resp, msg; OF.feedback("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/speedXY", { arg time, resp, msg; OF.feedback("speedXY", msg[1], msg[2]);} ).add; 

// ##########  SKETCH  ##############
OSCresponder.new(nil, "/drawWithPad", { arg time, resp, msg; OF.interactWithSketch("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/mouseLines", { arg time, resp, msg; OF.interactWithSketch("mouseLines", msg[1]);} ).add; 
OSCresponder.new(nil, "/numMouseSketches", { arg time, resp, msg; OF.interactWithSketch("numMouseSketches", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("minMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("maxMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseDamping", { arg time, resp, msg; OF.interactWithSketch("minMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseDamping", { arg time, resp, msg; OF.interactWithSketch("maxMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/padXY", { arg time, resp, msg; OF.interactWithSketch("padXY", msg[2], 1200 - msg[1]);} ).add; 
OSCresponder.new(nil, "/sketchPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchBWPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchBWPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPatDur", { arg time, resp, msg; ~sketchBWDur.source = Pseq([msg[1]],inf);} ).add; 


~sketchTuple = PatternProxy( 
			Ptuple(["interactWithSketch", "padXY", Pseq(Array.series(1000, 0, 1).scramble, inf), Pseq(Array.series(200, 10, 1000/200).mirror, inf)] , inf) 
		);
~sketchDur = PatternProxy(Pseq(Array.geom(82, 0.003, 1.05).pyramid(2),inf));		

Pdef(\sketchPat,Pbind(
		\type, \osc, 
		\dur, ~sketchDur,
	 	\dest, ~addr,
		\msg, ~sketchTuple
);
);	

~sketchBWTuple = PatternProxy( 
			Ptuple(["rgb", "sketchBW", Pseq([0, 255], inf), Pseq([0, 255], inf), Pseq([0, 255], inf)]) 
		);
~sketchBWDur = PatternProxy(Pseq([0.1,0.1],inf));		

Pdef(\sketchBWPat,Pbind(
		\type, \osc, 
		\dur, ~sketchBWDur,
	 	\dest, ~addr,
		\msg, ~sketchBWTuple
);
);	


//:Create OSCresponders
/*
Create OSCResponders for recieving OSC from iPod and 
forward them with a good syntax to openFrameworks.

Aris Bezas Mon, 01 November 2010, 07:41PM
*/


//~addr = NetAddr("127.0.0.1", 12345);
~addr = NetAddr("192.168.1.65", 12345);
Event.addEventType(\osc, { ~dest.sendBundle(~latency, ~msg); });

//page1
OSCresponder.new(nil, "/r8", { arg time, resp, msg; OF.rgb("r8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g8", { arg time, resp, msg; OF.rgb("g8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b8", { arg time, resp, msg; OF.rgb("b8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a8", { arg time, resp, msg; OF.rgb("a8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r7", { arg time, resp, msg; OF.rgb("r7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g7", { arg time, resp, msg; OF.rgb("g7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b7", { arg time, resp, msg; OF.rgb("b7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a7", { arg time, resp, msg; OF.rgb("a7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r6", { arg time, resp, msg; OF.rgb("r6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g6", { arg time, resp, msg; OF.rgb("g6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b6", { arg time, resp, msg; OF.rgb("b6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a6", { arg time, resp, msg; OF.rgb("a6", msg[1].round);} ).add; 
//page2
OSCresponderNode.new(nil, "/setupSound", { | time, resp, msg| ~setupSoundTask.start;}).add; 
OSCresponderNode.new(nil, "/freeSound", { | time, resp, msg| ~soundSynth.free;}).add; 
OSCresponderNode.new(nil, "/viewSoundChanels", { | time, resp, msg| OF.interactWithSound("activate", msg[1]);}).add; 
OSCresponder.new(nil, "/soundLines", { arg time, resp, msg; OF.interactWithSound("glBeginType", msg[1].round);} ).add; 
OSCresponder.new(nil, "/numSoundSketches", { arg time, resp, msg; OF.interactWithSound("numSoundSketches", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxFreqIn", { arg time, resp, msg; OF.interactWithSound("maxFreqIn", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxAmpIn", { arg time, resp, msg; OF.interactWithSound("maxAmpIn", msg[1]);} ).add; 
OSCresponder.new(nil, "/elasticity", { arg time, resp, msg; OF.interactWithSound("maxSoundElasticity", msg[1]);} ).add; 

//+++++++++++++++++++++++++++++++++++++++++++++++
OSCresponder.new(nil, "/soundBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~soundBWTask.stop;OF.rgb("sound",255,255,255,20);OF.rgb("background",0,0,0,0);};
	if (msg[1] == 1)	{~soundBWTask.start};
} ).add; 
OSCresponder.new(nil, "/backBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backBWTask.stop;	OF.background(0,0,0);	OF.rgb("sound",255,255,255,25);OF.rgb("background",0,0,0,30);};
	if (msg[1] == 1)	{~backBWTask.start};
} ).add; 
OSCresponder.new(nil, "/elastTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~elastTask.stop};
	if (msg[1] == 1)	{~elastTask.start};
} ).add; 
OSCresponder.new(nil, "/backTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backgroundTask.stop;OF.rgb("background",0,0,0,255);OF.rgb("sound",255,255,255,125);	};
	if (msg[1] == 1)	{~backgroundTask.start};
} ).add; 


~setupSoundTask = Task({
					if (not(s.serverRunning)) { s.boot };
					s.doWhenBooted {
						SendAmpFreq.start;
						0.1.wait;
						~soundSynth = Synth("SendAmpPitch");	
					};					
				});
~soundBWTask = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;	
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;			
	});
});
~backBWTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});
~elastTask = Task({
	inf.do({
//		OF.interactWithSound("maxSoundElasticity",0.1);					
//		0.5.wait;	
		OF.interactWithSound("maxSoundElasticity",1);					
		0.3.wait;	
		OF.interactWithSound("maxSoundElasticity",0.01);					
		0.7.wait;			
	});
});

~backgroundTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});


				
//page3

OSCresponderNode.new(nil, "/particles", { | time, resp, msg| OF.particle("activate", msg[1]);}).add; 
OSCresponderNode.new(nil, "/forceRadius", { | time, resp, msg| OF.particle("forceRadius", msg[1]);}).add; 
OSCresponderNode.new(nil, "/particleNeighborhood", { | time, resp, msg| OF.particle("particleNeighborhood", msg[1].round);}).add; 
OSCresponder.new(nil, "/r1", { arg time, resp, msg; OF.rgb("r1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g1", { arg time, resp, msg; OF.rgb("g1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b1", { arg time, resp, msg; OF.rgb("b1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a1", { arg time, resp, msg; OF.rgb("a1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r2", { arg time, resp, msg; OF.rgb("r2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g2", { arg time, resp, msg; OF.rgb("g2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b2", { arg time, resp, msg; OF.rgb("b2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a2", { arg time, resp, msg; OF.rgb("a2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/conBW", { arg time, resp, msg; OF.rgb("r1", 255*msg[1]);OF.rgb("g1", 255*msg[1]);OF.rgb("b1", 255*msg[1]);} ).add; 
OSCresponder.new(nil, "/particlesBW", { arg time, resp, msg; OF.rgb("r2", 255*msg[1]);OF.rgb("g2", 255*msg[1]);OF.rgb("b2", 255*msg[1]);} ).add; 

OSCresponder.new(nil, "/BWtask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~particlesBWTask.stop;OF.rgb("particleConnections",0,0,0);};
	if (msg[1] == 1)	{~particlesBWTask.start};
} ).add; 
OSCresponder.new(nil, "/iPodPush", { arg time, resp, msg; 
	if (msg[1] == 0)	{OF.particle("iPodPush", msg[1])};
	if (msg[1] == 1)	{OF.particle("iPodPush", msg[1])};
} ).add; 

~particlesBWTask = Task({
	inf.do({
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;	
		OF.rgb("particleConnections",0,0,0);					
		0.001.wait;	
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;			
	});
});


// ########## FEEDBACK  #############
OSCresponder.new(nil, "/feedback", { arg time, resp, msg; OF.feedback("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/speedXY", { arg time, resp, msg; OF.feedback("speedXY", msg[1], msg[2]);} ).add; 

// ##########  SKETCH  ##############
OSCresponder.new(nil, "/drawWithPad", { arg time, resp, msg; OF.interactWithSketch("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/mouseLines", { arg time, resp, msg; OF.interactWithSketch("mouseLines", msg[1]);} ).add; 
OSCresponder.new(nil, "/numMouseSketches", { arg time, resp, msg; OF.interactWithSketch("numMouseSketches", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("minMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("maxMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseDamping", { arg time, resp, msg; OF.interactWithSketch("minMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseDamping", { arg time, resp, msg; OF.interactWithSketch("maxMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/padXY", { arg time, resp, msg; OF.interactWithSketch("padXY", msg[2], 1200 - msg[1]);} ).add; 
OSCresponder.new(nil, "/sketchPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchBWPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchBWPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPatDur", { arg time, resp, msg; ~sketchBWDur.source = Pseq([msg[1]],inf);} ).add; 


~sketchTuple = PatternProxy( 
			Ptuple(["interactWithSketch", "padXY", Pseq(Array.series(1000, 0, 1).scramble, inf), Pseq(Array.series(200, 10, 1000/200).mirror, inf)] , inf) 
		);
~sketchDur = PatternProxy(Pseq(Array.geom(82, 0.003, 1.05).pyramid(2),inf));		

Pdef(\sketchPat,Pbind(
		\type, \osc, 
		\dur, ~sketchDur,
	 	\dest, ~addr,
		\msg, ~sketchTuple
);
);	

~sketchBWTuple = PatternProxy( 
			Ptuple(["rgb", "sketchBW", Pseq([0, 255], inf), Pseq([0, 255], inf), Pseq([0, 255], inf)]) 
		);
~sketchBWDur = PatternProxy(Pseq([0.1,0.1],inf));		

Pdef(\sketchBWPat,Pbind(
		\type, \osc, 
		\dur, ~sketchBWDur,
	 	\dest, ~addr,
		\msg, ~sketchBWTuple
);
);	


//:Create OSCresponders
/*
Create OSCResponders for recieving OSC from iPod and 
forward them with a good syntax to openFrameworks.

Aris Bezas Mon, 01 November 2010, 07:41PM
*/


//~addr = NetAddr("127.0.0.1", 12345);
~addr = NetAddr("192.168.1.65", 12345);
Event.addEventType(\osc, { ~dest.sendBundle(~latency, ~msg); });

//page1
OSCresponder.new(nil, "/r8", { arg time, resp, msg; OF.rgb("r8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g8", { arg time, resp, msg; OF.rgb("g8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b8", { arg time, resp, msg; OF.rgb("b8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a8", { arg time, resp, msg; OF.rgb("a8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r7", { arg time, resp, msg; OF.rgb("r7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g7", { arg time, resp, msg; OF.rgb("g7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b7", { arg time, resp, msg; OF.rgb("b7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a7", { arg time, resp, msg; OF.rgb("a7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r6", { arg time, resp, msg; OF.rgb("r6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g6", { arg time, resp, msg; OF.rgb("g6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b6", { arg time, resp, msg; OF.rgb("b6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a6", { arg time, resp, msg; OF.rgb("a6", msg[1].round);} ).add; 
//page2
OSCresponderNode.new(nil, "/setupSound", { | time, resp, msg| ~setupSoundTask.start;}).add; 
OSCresponderNode.new(nil, "/freeSound", { | time, resp, msg| ~soundSynth.free;}).add; 
OSCresponderNode.new(nil, "/viewSoundChanels", { | time, resp, msg| OF.interactWithSound("activate", msg[1]);}).add; 
OSCresponder.new(nil, "/soundLines", { arg time, resp, msg; OF.interactWithSound("glBeginType", msg[1].round);} ).add; 
OSCresponder.new(nil, "/numSoundSketches", { arg time, resp, msg; OF.interactWithSound("numSoundSketches", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxFreqIn", { arg time, resp, msg; OF.interactWithSound("maxFreqIn", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxAmpIn", { arg time, resp, msg; OF.interactWithSound("maxAmpIn", msg[1]);} ).add; 
OSCresponder.new(nil, "/elasticity", { arg time, resp, msg; OF.interactWithSound("maxSoundElasticity", msg[1]);} ).add; 

//+++++++++++++++++++++++++++++++++++++++++++++++
OSCresponder.new(nil, "/soundBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~soundBWTask.stop;OF.rgb("sound",255,255,255,20);OF.rgb("background",0,0,0,0);};
	if (msg[1] == 1)	{~soundBWTask.start};
} ).add; 
OSCresponder.new(nil, "/backBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backBWTask.stop;	OF.background(0,0,0);	OF.rgb("sound",255,255,255,25);OF.rgb("background",0,0,0,30);};
	if (msg[1] == 1)	{~backBWTask.start};
} ).add; 
OSCresponder.new(nil, "/elastTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~elastTask.stop};
	if (msg[1] == 1)	{~elastTask.start};
} ).add; 
OSCresponder.new(nil, "/backTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backgroundTask.stop;OF.rgb("background",0,0,0,255);OF.rgb("sound",255,255,255,125);	};
	if (msg[1] == 1)	{~backgroundTask.start};
} ).add; 


~setupSoundTask = Task({
					if (not(s.serverRunning)) { s.boot };
					s.doWhenBooted {
						SendAmpFreq.start;
						0.1.wait;
						~soundSynth = Synth("SendAmpPitch");	
					};					
				});
~soundBWTask = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;	
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;			
	});
});
~backBWTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});
~elastTask = Task({
	inf.do({
//		OF.interactWithSound("maxSoundElasticity",0.1);					
//		0.5.wait;	
		OF.interactWithSound("maxSoundElasticity",1);					
		0.3.wait;	
		OF.interactWithSound("maxSoundElasticity",0.01);					
		0.7.wait;			
	});
});

~backgroundTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});


				
//page3

OSCresponderNode.new(nil, "/particles", { | time, resp, msg| OF.particle("activate", msg[1]);}).add; 
OSCresponderNode.new(nil, "/forceRadius", { | time, resp, msg| OF.particle("forceRadius", msg[1]);}).add; 
OSCresponderNode.new(nil, "/particleNeighborhood", { | time, resp, msg| OF.particle("particleNeighborhood", msg[1].round);}).add; 
OSCresponder.new(nil, "/r1", { arg time, resp, msg; OF.rgb("r1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g1", { arg time, resp, msg; OF.rgb("g1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b1", { arg time, resp, msg; OF.rgb("b1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a1", { arg time, resp, msg; OF.rgb("a1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r2", { arg time, resp, msg; OF.rgb("r2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g2", { arg time, resp, msg; OF.rgb("g2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b2", { arg time, resp, msg; OF.rgb("b2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a2", { arg time, resp, msg; OF.rgb("a2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/conBW", { arg time, resp, msg; OF.rgb("r1", 255*msg[1]);OF.rgb("g1", 255*msg[1]);OF.rgb("b1", 255*msg[1]);} ).add; 
OSCresponder.new(nil, "/particlesBW", { arg time, resp, msg; OF.rgb("r2", 255*msg[1]);OF.rgb("g2", 255*msg[1]);OF.rgb("b2", 255*msg[1]);} ).add; 

OSCresponder.new(nil, "/BWtask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~particlesBWTask.stop;OF.rgb("particleConnections",0,0,0);};
	if (msg[1] == 1)	{~particlesBWTask.start};
} ).add; 
OSCresponder.new(nil, "/iPodPush", { arg time, resp, msg; 
	if (msg[1] == 0)	{OF.particle("iPodPush", msg[1])};
	if (msg[1] == 1)	{OF.particle("iPodPush", msg[1])};
} ).add; 

~particlesBWTask = Task({
	inf.do({
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;	
		OF.rgb("particleConnections",0,0,0);					
		0.001.wait;	
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;			
	});
});


// ########## FEEDBACK  #############
OSCresponder.new(nil, "/feedback", { arg time, resp, msg; OF.feedback("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/speedXY", { arg time, resp, msg; OF.feedback("speedXY", msg[1], msg[2]);} ).add; 

// ##########  SKETCH  ##############
OSCresponder.new(nil, "/drawWithPad", { arg time, resp, msg; OF.interactWithSketch("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/mouseLines", { arg time, resp, msg; OF.interactWithSketch("mouseLines", msg[1]);} ).add; 
OSCresponder.new(nil, "/numMouseSketches", { arg time, resp, msg; OF.interactWithSketch("numMouseSketches", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("minMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("maxMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseDamping", { arg time, resp, msg; OF.interactWithSketch("minMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseDamping", { arg time, resp, msg; OF.interactWithSketch("maxMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/padXY", { arg time, resp, msg; OF.interactWithSketch("padXY", msg[2], 1200 - msg[1]);} ).add; 
OSCresponder.new(nil, "/sketchPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchBWPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchBWPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPatDur", { arg time, resp, msg; ~sketchBWDur.source = Pseq([msg[1]],inf);} ).add; 


~sketchTuple = PatternProxy( 
			Ptuple(["interactWithSketch", "padXY", Pseq(Array.series(1000, 0, 1).scramble, inf), Pseq(Array.series(200, 10, 1000/200).mirror, inf)] , inf) 
		);
~sketchDur = PatternProxy(Pseq(Array.geom(82, 0.003, 1.05).pyramid(2),inf));		

Pdef(\sketchPat,Pbind(
		\type, \osc, 
		\dur, ~sketchDur,
	 	\dest, ~addr,
		\msg, ~sketchTuple
);
);	

~sketchBWTuple = PatternProxy( 
			Ptuple(["rgb", "sketchBW", Pseq([0, 255], inf), Pseq([0, 255], inf), Pseq([0, 255], inf)]) 
		);
~sketchBWDur = PatternProxy(Pseq([0.1,0.1],inf));		

Pdef(\sketchBWPat,Pbind(
		\type, \osc, 
		\dur, ~sketchBWDur,
	 	\dest, ~addr,
		\msg, ~sketchBWTuple
);
);	


