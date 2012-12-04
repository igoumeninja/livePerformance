SketchResponders {

	*initClass {
		StartUp add: {
			"-SketchResponders".postln;
			this.sketch3dResponders;
			this.sketchResponders;
			this.sketchTasksResponders;
		}
	}

	*sketch3dResponders {
		~activatesketch3dResp.remove;
		~activatesketch3dResp = OSCresponderNode(nil, '/activatesketch3d', { |t,r,msg|
			if( msg[1] == 1,{
				OF.sketch3d("activate", 1);
			},{
				OF.sketch3d("activate", 0);
			});
		}).add;
		~glTypeSketch3dResp.remove;
		~glTypeSketch3dResp = OSCresponderNode(nil, '/glTypeSketch3d', { |t,r,msg|
			if( msg[1] == 1,{
				OF.sketch3d("glBeginType",1);					},{
				OF.sketch3d("glBeginType",0);
			});
		}).add;
		~numSketch3dSpec = ControlSpec(1, 500, \lin);
		~numSketch3dResp.remove;
		~numSketch3Resp = OSCresponderNode(nil, '/numSketch3d', { | time, resp, msg|
			OF.sketch3d("numSketch3d", ~numSketch3dSpec.map(msg[1]).asInteger);
		} ).add;

		~elasticitySketch3dSpec = ControlSpec(0.001, 2, \lin);
		~elasticitySketch3dResp.remove;
		~elasticitySketch3dResp = OSCresponderNode(nil, '/elasticitySketch3d', { | time, resp, msg|
			OF.sketch3d("maxSketch3dElasticity", ~elasticitySketch3dSpec.map(msg[1]));
		} ).add;

		~rotYratioSketch3dSpec = ControlSpec(0.001, 2, \lin);
		~rotYratioSketch3dResp.remove;
		~rotYratioSketch3dResp = OSCresponderNode(nil, '/rotYratioSketch3d', { | time, resp, msg|
			OF.sketch3d("rotYratio", ~rotYratioSketch3dSpec.map(msg[1]));
		} ).add;

		~rotXratioSketch3dSpec = ControlSpec(0.001, 2, \lin);
		~rotXratioSketch3dResp.remove;
		~rotXratioSketch3dResp = OSCresponderNode(nil, '/rotXratioSketch3d', { | time, resp, msg|
			OF.sketch3d("rotXratio", ~rotXratioSketch3dSpec.map(msg[1]));
		} ).add;

	}
	*sketchResponders {

		~setupSoundResp.remove;
		~setupSoundResp = OSCresponderNode(nil, '/setupSound', { |t,r,msg|
			if( msg[1] == 1,{
				"_________Start SendAmpFreq__________".postln;
				~mySendAmpFreq.start;
			},{
				"_________Stop SendAmpFreq__________".postln;
				~mySendAmpFreq.stop;
			});
		}).add;

		~viewSoundResp.remove;
		~vewSoundResp = OSCresponderNode(nil, '/viewSound', { |t,r,msg|
			if( msg[1] == 1,{
				OF.interactWithSound("activate",1); 				},{
				OF.interactWithSound("activate",0);
			});
		}).add;

		~glBeginTypeResp.remove;
		~glBeginTypeResp = OSCresponderNode(nil, '/glBeginType', { | time, resp, msg|
			OF.interactWithSound("glBeginType", msg[1].asInteger);
		} ).add;

		~numSoundSketchesSpec = ControlSpec(1, 500, \lin);
		~numSoundSketchesResp.remove;
		~numSoundSketchesResp = OSCresponderNode(nil, '/numSoundSketches', { | time, resp, msg|
			OF.interactWithSound("numSoundSketches", ~numSoundSketchesSpec.map(msg[1]).asInteger);
		} ).add;

		~maxFreqInSpec = ControlSpec(20, 3000, \lin);
		~maxFreqInResp.remove;
		~maxFreqInResp = OSCresponderNode(nil, '/maxFreqIn', { | time, resp, msg|
			OF.interactWithSound("maxFreqIn", ~maxFreqInSpec.map(msg[1]));
		} ).add;

		~maxAmpInSpec = ControlSpec(0.001, 1, \lin);
		~maxAmpInResp.remove;
		~maxAmpInResp = OSCresponderNode(nil, '/maxAmpIn', { | time, resp, msg|
			OF.interactWithSound("maxAmpIn", ~maxAmpInSpec.map(msg[1]));
		}).add;

		~elasticitySpec = ControlSpec(0.001, 2, \lin);
		~elasticityResp.remove;
		~elasticityResp = OSCresponderNode(nil, '/elasticity', { | time, resp, msg|
			OF.interactWithSound("maxSoundElasticity", ~elasticitySpec.map(msg[1]));
		} ).add;

		~aSoundSpec = ControlSpec(0, 255, \lin);
		~aSoundResp.remove;
		~aSoundResp = OSCresponderNode(nil, '/aSound', { | time, resp, msg|
			OF.rgb("sound",255,255,255,~aSoundSpec.map(msg[1]).asInteger);
		} ).add;

		~padSketchResp.remove;
		~padSketchResp = OSCresponderNode(nil, '/padSketch', { |t,r,msg|
			OF.interactWithSketch("activate", msg[1].asInteger);
		}).add;

		~xPadSpec = ControlSpec(0, ~width, \lin);
		~yPadSpec = ControlSpec(~height, 0, \lin);
		~padSketchXYResp.remove;
		~padSketchXYResp = OSCresponderNode(nil, '/padSketchXY', { | time, resp, msg| OF.interactWithSketch("padSketchXY", ~xPadSpec.map(msg[2]).asInteger, ~yPadSpec.map(msg[1]).asInteger);
		}).add;

		~aPadSpec = ControlSpec(0, 255, \lin);
		~aPadResp.remove;
		~aPadResp = OSCresponderNode(nil, '/aPad', { | time, resp, msg|
			OF.rgb("sketch",255,255,255,~aPadSpec.map(msg[1]).asInteger);
		} ).add;

		~padSketchTypeResp.remove;
		~padSketchTypeResp = OSCresponderNode(nil, '/padSketchType', { | time, resp, msg|
			OF.interactWithSketch("mouseLines",msg[1].asInteger);
		} ).add;

	}
	*sketchTasksResponders	{

		~btw1 = Task({
			inf.do({
				OF.rgb("sound",255,255,255,55);
				0.04.wait;
				OF.rgb("sound",0,0,0,55);
				0.04.wait;
			});
		});

		~btw1Resp.remove;
		~btw1Resp = OSCresponderNode(nil, '/btw1', { |t,r,msg|
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
		~btw2Resp = OSCresponderNode(nil, '/btw2', { |t,r,msg|
			if( msg[1] == 1,{
				OF.background(0, 0, 0, 0);
				OF.rgb("a8",0);
				OF.interactWithSound("numSoundSketches", 1000);
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
		~btw3Resp = OSCresponderNode(nil, '/btw3', { |t,r,msg|
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
		~elastTaskResp = OSCresponderNode(nil, '/elastTask', { |t,r,msg|
			if( msg[1] == 1,{
				~elastTask.play;
			},{
				~elastTask.stop;
			});
		}).add;

		~blackWhiteSoundResp.remove;
		~blackWhiteSoundResp = OSCresponderNode(nil, '/blackWhiteSound', { |t,r,msg|
			if( msg[1] == 1,{
				OF.rgb("sound",255,255,255,120);
			},{
				OF.rgb("sound",0,0,0,120);
			});
		}).add;
	}
}
