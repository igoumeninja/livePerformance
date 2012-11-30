/*

This class is used iPad responders

Aris Bezas Astakos -> Sami 120429

## SOME EXTRA PHRASES ##
//:-
{
OF.background(0,0,0,40);
inf.do({|i|
	OF.writeString("bigCenter", "Technoetic Telos".at(i%16).asString,~width/2 - 200,~height/2,255,255,255,255);				
	~delayTechnoetic.wait;
	~kontraBass.brt_((i%14)+2).playBuf(0.001, 0.5, 0.3,mul:~amp.next, loop:0);		
});
}.fork
//:-
~delayTechnoetic = 0.05;
{
inf.do({|i|
	OF.writeString("bigCenter", "~`!@#$%^&*()_+}{][|".at(rrand(0,10).asInteger).asString,~width/2 - 200,~height/2,255,255,255,255);				
		0.08.wait;	
});
}.fork

*/

UnderDevelopmentResponders {
	*initClass {
		StartUp add: {			
		}
	}

}



/*	

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

~viewSoundResp.remove;
~viewSoundResp = OSCresponderNode(n, '/viewSound', { |t,r,msg| 
	OF.interactWithSound("activate", msg[1].asInteger);
}).add;





// ==================
// PARTICLES


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




*/		
