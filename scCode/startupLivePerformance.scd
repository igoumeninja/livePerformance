
"startup is working".postln;
//~width = 1280;
//~height = 1024;

~width = 1024;
~height = 768;

// Take a string from file
~myString = File.use("/Users/ari/Projects/artistic/performance/hbuc/data/text/text", "r", { |f| f.readAllString});

// Sound Interaction
Task({
		if (not(s.serverRunning)) { s.boot };
		s.doWhenBooted {
			SendAmpFreq.start;
		};					
}).play;
	
	
"open /Users/ari/Projects/artistic/performance/hbuc/scCode/of-sc-glossary.scd".unixCmd;
"open /Users/ari/Projects/artistic/performance/hbuc/scCode/hbuc-responders.scd".unixCmd;
	


~thisProcessStopResp.remove;
~thisProcessStopResp = OSCresponderNode(n, '/thisProcessStop', { |t,r,msg|
	thisProcess.stop;
}).add;


~blackBackgroundResp.remove;~blackBackgroundResp = OSCresponderNode(n, '/blackBackground', { |t,r,msg| 
	OF.background(0,0,0);				
}).add;

~blackBackgroundAlpha.remove;~blackBackgroundAlphaResp = OSCresponderNode(n, '/blackBackgroundAlpha', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.background(0,0,0,20);
	},{
		OF.background(0,0,0,0);
	});
}).add;

~alphaBackgroundSpec = ControlSpec(0, 125, \lin);
~alphaBackgroundResp.remove;~alphaBackgroundResp = OSCresponderNode(n, '/alphaBackground', { | time, resp, msg| 
	OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
} ).add; 

// =================
// TYPOGRAPHY

~typoNormalResp.remove;
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

~typoInverseResp.remove;
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

~myBufferForCutMotion = Buffer.alloc(s, 512);

~fftImageTriggerResp.remove;
~fftImageTriggerResp = OSCresponderNode(n, '/imageTriger', { |t,r,msg| 
	if( msg[1] == 1,{
		~fftImageTrigger = { | threshold = 1.0 |
			var sig, chain, onsets, pips;
			sig = In.ar(8);	
			chain = FFT(~myBufferForCutMotion, sig);
			onsets = Onsets.kr(chain, threshold, \rcomplex);
			SendReply.kr(onsets, \trigImage);
		}.play;

	},{
		~fftImageTrigger.free;
	});
}).add;

~trigImageResp.remove;
~trigImageResp = OSCresponderNode(n, 'trigImage', { |time, resp, msg| 
	OF.img(rrand(0,60).asInteger, (~xPos[rrand(0,pow(~columns,2))]/4).asInteger, (~yPos[rrand(0,pow(~columns,2))]/4).asInteger, ~fotoSize.asInteger, ~fotoSize.asInteger);
	OF.img(rrand(0,60).asInteger, (~xPos[rrand(0,pow(~columns,2))]/4).asInteger, (~yPos[rrand(0,pow(~columns,2))]/4).asInteger, ~fotoSize.asInteger, ~fotoSize.asInteger);
}).add; 

~columns = 10;
~xPos = Array.new(pow(~columns,2));
~yPos = Array.new(pow(~columns,2));	
~columns.do{|j|
	~columns.do{|i|
		~xPos.add((~width*(i)/~columns).round);
		~yPos.add((~width*(i)/~columns).round);
	};
};
~fotoSize = ~width/~columns;
~collageRandom = Task({
	var 	delayTime = 0.04;
	loop	{
		OF.img(rrand(0,60).asInteger, ~xPos[rrand(0,pow(~columns,2))].asInteger, ~yPos[rrand(0,pow(~columns,2))].asInteger, ~fotoSize.asInteger, ~fotoSize.asInteger);
		delayTime.wait;
	}
});
~collageRandomResp.remove;
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
			OF.img(rrand(0,60).asInteger, countX.asInteger, countY.asInteger, fotoWidth.asInteger, fotoHeight.asInteger);
			countX = countX + fotoWidth;
			if (countX>~width, {countX = 0;countY = countY + fotoHeight});
			if (countY>~height, {countY = 0;});
			delayTime.wait;
		}
	}
});

~collageNormalResp.remove;
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
		OF.img(rrand(0,60).asInteger,(~width/5-(imageSize/2)).asInteger, (~height/2-(imageSize/2)).asInteger, imageSize.asInteger, imageSize.asInteger);		waitTime.wait;
		OF.img(rrand(0,60).asInteger,(2*~width/5-(imageSize/2)).asInteger, (~height/2-(imageSize/2)).asInteger, imageSize.asInteger, imageSize.asInteger);		waitTime.wait;
		OF.img(rrand(0,60).asInteger,(3*~width/5-(imageSize/2)).asInteger, (~height/2-(imageSize/2)).asInteger, imageSize.asInteger, imageSize.asInteger);		waitTime.wait;
		OF.img(rrand(0,60).asInteger,(4*~width/5-(imageSize/2)).asInteger, (~height/2-(imageSize/2)).asInteger, imageSize.asInteger, imageSize.asInteger);		waitTime.wait;
	}
});
~fourImagesResp.remove;
~fourImagesResp = OSCresponderNode(n, '/fourImages', { |t,r,msg| 
	if( msg[1] == 1,{
		~fourImages.play;
	},{
		~fourImages.stop;
	});
}).add;

~rotate2d = Task({
	inf.do	{ |i|
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, (3*~width/4).asInteger, (~height/2).asInteger, 0, 0, 0, i.asInteger);
		0.04.wait;
		OF.img(rrand(0,60).asInteger,0, 0, 200, 200, (~width/4).asInteger, (~height/2).asInteger, 0, 0, 0, i.asInteger);		0.04.wait;
	}
});
~rotate2dResp.remove;
~rotate2dResp = OSCresponderNode(n, '/rotate2d', { |t,r,msg| 
	if( msg[1] == 1,{
		~rotate2d.play;
	},{
		~rotate2d.stop;
	});
}).add;


~upLeftImageResp.remove;
~upLeftImageResp = OSCresponderNode(n, '/upLeftImage', { |t,r,msg| 
	//OF.img(rrand(0,60).asInteger, 0, 0, ~width/8, ~height/8);
	OF.img(rrand(0,60).asInteger, 0, 0, 100,100);
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

~setupSoundResp.remove;
~setupSoundResp = OSCresponderNode(n, '/setupSound', { |t,r,msg| 
	if( msg[1] == 1,{
		"start posting".postln;
		~soundSynth = Synth("SendAmpPitch");
	},{
		"Stop posting and free synth from SendAmpFreq".postln;
		~soundSynth.free;
	});
}).add;

~viewSoundResp.remove;
~viewSoundResp = OSCresponderNode(n, '/viewSound', { |t,r,msg| 
	OF.interactWithSound("activate", msg[1].asInteger);
}).add;

~glBeginTypeResp.remove;
~glBeginTypeResp = OSCresponderNode(n, '/glBeginType', { | time, resp, msg| 
	OF.interactWithSound("glBeginType", msg[1].asInteger);
} ).add; 

~numSoundSketchesSpec = ControlSpec(1, 500, \lin);
~numSoundSketchesResp.remove;
~numSoundSketchesResp = OSCresponderNode(n, '/numSoundSketches', { | time, resp, msg| 
	OF.interactWithSound("numSoundSketches", ~numSoundSketchesSpec.map(msg[1]).asInteger);
} ).add; 

~maxFreqInSpec = ControlSpec(20, 3000, \lin);
~maxFreqInResp.remove;
~maxFreqInResp = OSCresponderNode(n, '/maxFreqIn', { | time, resp, msg| 
	OF.interactWithSound("maxFreqIn", ~maxFreqInSpec.map(msg[1]));
} ).add; 

~maxAmpInSpec = ControlSpec(0.001, 1, \lin);
~maxAmpInResp.remove;
~maxAmpInResp = OSCresponderNode(n, '/maxAmpIn', { | time, resp, msg| 
	OF.interactWithSound("maxAmpIn", ~maxAmpInSpec.map(msg[1]));
}).add; 

~elasticitySpec = ControlSpec(0.001, 2, \lin);
~elasticityResp.remove;
~elasticityResp = OSCresponderNode(n, '/elasticity', { | time, resp, msg| 
	OF.interactWithSound("maxSoundElasticity", ~elasticitySpec.map(msg[1]));
} ).add; 

~btw1 = Task({
	inf.do({
		OF.rgb("sound",255,255,255,55);				
		0.04.wait;	
		OF.rgb("sound",0,0,0,55);
		0.04.wait;	
	});
});

~btw1Resp.remove;
~btw1Resp = OSCresponderNode(n, '/btw1', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.background(0, 0, 0, 0);
		OF.interactWithSound("numSoundSketches", 800);
		OF.interactWithSound("glBeginType", 1);	
		~btw1.play;
	},{
		~btw1.stop;
		OF.background(0, 0, 0, 20);
		OF.interactWithSound("numSoundSketches", 800);
		OF.interactWithSound("glBeginType", 0);
		OF.rgb("sound",255,255,255,55);	
		
	});
}).add;

~btw2 = Task({
	inf.do({
		OF.rgb("sound",255,255,255,255);				
		0.04.wait;	
		OF.rgb("sound",0,0,0,255);
		0.04.wait;	
	});
});

~btw2Resp.remove;
~btw2Resp = OSCresponderNode(n, '/btw2', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.background(0, 0, 0, 0);
		OF.rgb("a8",0);
		OF.interactWithSound("numSoundSketches", 800);
		OF.interactWithSound("glBeginType", 1);				~btw2.play;
	},{
		OF.background(0, 0, 0, 20);
		OF.interactWithSound("numSoundSketches", 800);
		OF.interactWithSound("glBeginType", 0);	
		OF.rgb("sound",255,255,255,55);			
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

~btw3Resp.remove;
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

~elastTaskResp.remove;
~elastTaskResp = OSCresponderNode(n, '/elastTask', { |t,r,msg| 
	if( msg[1] == 1,{
		~elastTask.play;
	},{
		~elastTask.stop;
	});
}).add;

~blackWhiteSoundResp.remove;
~blackWhiteSoundResp = OSCresponderNode(n, '/blackWhiteSound', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.rgb("sound",255,255,255,120);
	},{
		OF.rgb("sound",0,0,0,120);				
	});
}).add;

~aSoundSpec = ControlSpec(0, 255, \lin);
~aSoundResp.remove;
~aSoundResp = OSCresponderNode(n, '/aSound', { | time, resp, msg| 
	OF.rgb("sound",255,255,255,~aSoundSpec.map(msg[1]).asInteger);
} ).add; 

// ==================
// SKETCH

~padSketchResp.remove;
~padSketchResp = OSCresponderNode(n, '/padSketch', { |t,r,msg| 
	OF.interactWithSketch("activate", msg[1].asInteger);
}).add;

~xPadSpec = ControlSpec(0, ~width, \lin);
~yPadSpec = ControlSpec(~height, 0, \lin);
~padSketchXYResp.remove;
~padSketchXYResp = OSCresponderNode(n, '/padSketchXY', { | time, resp, msg| 
	OF.interactWithSketch("padSketchXY", ~xPadSpec.map(msg[2]).asInteger, ~yPadSpec.map(msg[1]).asInteger);
} ).add; 

~aPadSpec = ControlSpec(0, 255, \lin);
~aPadResp.remove;
~aPadResp = OSCresponderNode(n, '/aPad', { | time, resp, msg| 
	OF.rgb("sketch",255,255,255,~aPadSpec.map(msg[1]).asInteger);
} ).add; 

~padSketchTypeResp.remove;
~padSketchTypeResp = OSCresponderNode(n, '/padSketchType', { | time, resp, msg| 
	OF.interactWithSketch("mouseLines",msg[1].asInteger);
} ).add; 

// ==================
// PARTICLES

~activateParticlesResp.remove;
~activateParticlesResp = OSCresponderNode(n, '/activateParticles', { |t,r,msg| 
	OF.particle("activate", msg[1].round);
}).add;

~forceRadiusSpec = ControlSpec(0, 500, \lin);
~forceRadiusResp.remove;
~forceRadiusResp = OSCresponderNode(n, '/forceRadius', { | time, resp, msg|
	OF.particle("forceRadius", ~forceRadiusSpec.map(msg[1]).asInteger);
}).add; 

~particleNeighborhoodSpec = ControlSpec(0, 30, \lin);
~particleNeighborhoodResp.remove;
~particleNeighborhoodResp = OSCresponderNode(n, '/particleNeighborhood', { | time, resp, msg|
	OF.particle("particleNeighborhood", ~particleNeighborhoodSpec.map(msg[1]).asInteger);
}).add; 

~particleConnectionsAlphaSpec = ControlSpec(0, 25, \lin);
~particleConnectionsAlphaResp.remove;
~particleConnectionsAlphaResp = OSCresponderNode(n, '/particleConnectionsAlpha', { | time, resp, msg|
	OF.particle("conColor", ~particleConnectionsAlphaSpec.map(msg[1]).asInteger);
}).add; 

~particlesAlphaSpec = ControlSpec(0, 255, \lin);
~particlesAlphaResp.remove;
~particlesAlphaResp = OSCresponderNode(n, '/particlesAlpha', { | time, resp, msg|
	OF.particle("dotColor", ~particlesAlphaSpec.map(msg[1]).asInteger);
}).add; 

~particlesRedConResp.remove;
~particlesRedConResp = OSCresponderNode(n, '/redCon', { | time, resp, msg|
	if( msg[1] == 1,{
		OF.particle("conColor", 255,0,0);
	},{
		OF.particle("conColor", 255,255,255);
	});
}).add; 

~particlesYellowDotResp.remove;
~particlesYellowDotResp = OSCresponderNode(n, '/yellowDot', { | time, resp, msg|
	if( msg[1] == 1,{
		OF.particle("dotColor", 255,255,0);
	},{
		OF.particle("dotColor", 255,255,255);
	});
}).add; 

~addParticlesResp.remove;
~addParticlesResp = OSCresponderNode(n, '/addParticles', { |t,r,msg| 
	{
		500.do{
			OF.particle("add", (~width/2).asInteger, (~height/2).asInteger, 0.1, 10.1);
			0.04.wait;
		}	
	}.fork
}).add;

~iPadPushResp.remove;
~iPadPushResp = OSCresponderNode(n, '/iPadPush', { |t,r,msg| 
	OF.particle("iPadPush", msg[1]);
}).add;

~iPadPushXYResp.remove;
~iPadPushXYResp = OSCresponderNode(n, '/iPadPushXY', { |t,r,msg| 
	OF.particle("pushParticles", msg[1]);
}).add;


~xPushPadSpec = ControlSpec(0, ~width, \lin);
~yPushPadSpec = ControlSpec(~height, 0, \lin);
OSCresponderNode(n, '/pushXY', { | time, resp, msg| 
	OF.particle("push", ~xPushPadSpec.map(msg[2]).asInteger, ~yPushPadSpec.map(msg[1]).asInteger);
} ).add; 

~redConTaskResp.remove;
~redConTaskResp = OSCresponderNode(n, '/redConTask', { | time, resp, msg|
	{
	OF.particle("conColor", 255,0,0);
	OF.particle("particleNeighborhood", 0);		
	OF.particle("push", rrand(0,~width).asInteger, rrand(0, ~height).asInteger);				
	OF.particle("pushParticles", 1);				
	0.04.wait;
	OF.particle("conColor", 255,255,255);
	OF.particle("particleNeighborhood", 35);		
	OF.particle("pushParticles", 0);					
	}.fork;
}).add; 

~bombsTask = Task({
	inf.do({
		OF.particle("push", (~width*0.75).asInteger, (~height/2).asInteger);		0.4.wait;	
		OF.particle("push",(~width*0.25).asInteger, (~height/2).asInteger);		0.4.wait;			
	});
});

~bombsResp.remove;
~bombsResp = OSCresponderNode(n, '/bombs', { | time, resp, msg|
	if(msg[1] == 1,{
		~bombsTask.play;
	},{
		~bombsTask.free;
	});
}).add; 


~myBufferForPushParticles = Buffer.alloc(s, 512);
~fftPushParticlesResp.remove;
~fftPushParticlesResp = OSCresponderNode(n, '/pushParticlesTriger', { |t,r,msg| 
	if( msg[1] == 1,{
		OF.particle("pushParticles",1);
		~fftPushParticlesTriger = { | threshold = 0.1 |
			var sig, chain, onsets, pips;
			sig = In.ar(8);	
			chain = FFT(~myBufferForPushParticles, sig);
			onsets = Onsets.kr(chain, threshold, \rcomplex);
			SendReply.kr(onsets, \pushParticles);
		}.play;

	},{
		~fftPushParticlesTriger.free;
		OF.particle("pushParticles",0);
	});
}).add;

~pushParticlesResp.remove;
~pushParticlesResp = OSCresponderNode(n, 'pushParticles', { |time, resp, msg| 
	OF.particle("push",rrand(0,~width).asInteger, rrand(0,~height).asInteger);
}).add; 


// FFT THRESHOLD
~pushParticlesThresholdSpec = ControlSpec(0.0, 2.0, \lin);
~pushParticlesThresholdResp.remove;
~pushParticlesThresholdResp = OSCresponderNode(n, 'fftThreshold', { |time, resp, msg| 
	~fftPushParticlesTriger.set(\threshold, ~pushParticlesThresholdSpec.map(msg[1]));
	~fftImageTrigger.set(\threshold, ~pushParticlesThresholdSpec.map(msg[1]));
	~fftDestrucrtTrigger.set(\threshold, ~pushParticlesThresholdSpec.map(msg[1]));
}).add; 


// ==================
// VIDEO

~playVideoResp.remove;~playVideoResp = OSCresponderNode(n, '/playVideo', { | time, resp, msg|
	OF.effect("mirrowEffect2", 1);	// 4-D copy mirrow
	OF.video("playVideo", msg[1]);
}).add; 

~playVideo4Resp.remove;~playVideo4Resp = OSCresponderNode(n, '/playVideo4', { | time, resp, msg|
	OF.video("playVideo", msg[1], ~width/4, ~height/4);
	OF.effect("mirrowEffect4", 1);	// 4-D copy mirrow
}).add; 

~deleteVideoResp.remove;~deleteVideoResp = OSCresponderNode(n, '/deleteVideo', { | time, resp, msg|
	if( msg[1] == 1,{
		OF.video("deleteVideo");
	});
}).add; 

~reloadVideoResp.remove;~reloadVideoResp = OSCresponderNode(n, '/reloadVideo', { | time, resp, msg|
	if( msg[1] == 1,{
		OF.video("reloadVideo");
	});
}).add; 

~rVideoSpec = ControlSpec(0, 255, \lin);
~rVideoResp.remove;~rVideoResp = OSCresponderNode(n, '/rVideo', { | time, resp, msg|
	OF.video("rVideo", ~rVideoSpec.map(msg[1]).asInteger);
}).add; 

~gVideoSpec = ControlSpec(0, 255, \lin);
~gVideoResp.remove;~gVideoResp = OSCresponderNode(n, '/gVideo', { | time, resp, msg|
	OF.video("gVideo", ~gVideoSpec.map(msg[1]).asInteger);
}).add; 

~bVideoSpec = ControlSpec(0, 255, \lin);
~bVideoResp.remove;~bVideoResp = OSCresponderNode(n, '/bVideo', { | time, resp, msg|
	OF.video("bVideo", ~bVideoSpec.map(msg[1]).asInteger);
}).add; 

~aVideoSpec = ControlSpec(0, 255, \lin);
~aVideoResp.remove;~aVideoResp = OSCresponderNode(n, '/aVideo', { | time, resp, msg|
	OF.video("aVideo", ~aVideoSpec.map(msg[1]).asInteger);
}).add; 

// :==================
// EFFECTS

~noiseEffectResp.remove;~noiseEffectResp = OSCresponderNode(n, '/noiseEffect', { |t,r,msg| 
	OF.effect("noiseEffect", msg[1]);
	OF.background(0,0,0);
}).add;


~feedbackResp.remove;~feedbackResp = OSCresponderNode(n, '/feedback', { |t,r,msg| 
	OF.feedback("activate", msg[1]);
}).add;

~xFeedbackSpec = ControlSpec(-5, 5, \lin);
~yFeedbackSpec = ControlSpec(5, -5, \lin);
~feedbackXYResp.remove;
~feedbackXYResp = OSCresponderNode(n, '/feedbackXY', { | time, resp, msg| 
	//"test".postln;
	OF.feedback("speedXY", ~xFeedbackSpec.map(msg[1]), ~yFeedbackSpec.map(msg[2]));
} ).add; 


~destructEffect.remove;
~destructEffect = OSCresponderNode(n, '/destructEffect', { |t,r,msg|
	OF.effect("destruct", "activate", msg[1]);
}).add;

~destructEffect0.remove;
~destructEffect0 = OSCresponderNode(n, '/destructEffect0', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("destruct", "case", 0);
	});
}).add;

~destructEffect1.remove;
~destructEffect1 = OSCresponderNode(n, '/destructEffect1', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("destruct", "case", 1);
	});
}).add;

~destructEffect2.remove;
~destructEffect2 = OSCresponderNode(n, '/destructEffect2', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("destruct", "case", 2);
	});
}).add;

~destructEffect3.remove;
~destructEffect3 = OSCresponderNode(n, '/destructEffect3', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("destruct", "case", 3);
	});
}).add;

~destructEffect4.remove;
~destructEffect4 = OSCresponderNode(n, '/destructEffect4', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("destruct", "case", 4);
	});
}).add;

~mirrorEffect.remove;
~mirrorEffect = OSCresponderNode(n, '/mirrorEffect', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", 1);
	},{
		OF.effect("mirror", 0);
	});
}).add;

~mirrorEffectCase0Resp.remove;
~mirrorEffectCase0Resp = OSCresponderNode(n, '/mirrorEffect0', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", "case", 0); 
	});
}).add;

~mirrorEffectCase1Resp.remove;
~mirrorEffectCase1Resp = OSCresponderNode(n, '/mirrorEffect1', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", "case", 1); 
	});
}).add;

~mirrorEffectCase2Resp.remove;
~mirrorEffectCase2Resp = OSCresponderNode(n, '/mirrorEffect2', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", "case", 2); 
	});
}).add;

~mirrorEffectCase3Resp.remove;
~mirrorEffectCase3Resp = OSCresponderNode(n, '/mirrorEffect3', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", "case", 3); 
	});
}).add;

~mirrorEffectCase4Resp.remove;
~mirrorEffectCase4Resp = OSCresponderNode(n, '/mirrorEffect4', { |t,r,msg|
	if( msg[1] == 1,{
		OF.effect("mirror", "case", 4); 
	});
}).add;

~treeEffectResp.remove;~treeEffectResp = OSCresponderNode(n, '/treeEffect', { | time, resp, msg|
	if(msg[1] == 1,{
		OF.effect("tree");
	})
}).add; 


