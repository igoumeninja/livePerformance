SketchResponders {
	
	*initClass {
		StartUp add: {			
			"-SketchResponders".postln;
			~width = 1280;
			~height = 1024;
			this.iPadRespondersSketch;
			this.iPadRespondersSketchTasks;
		}
	}
	
	*iPadRespondersSketch {

		~setupSoundResp.remove;
		~setupSoundResp = OSCresponderNode(~ofNetwork, '/setupSound', { |t,r,msg| 
			if( msg[1] == 1,{
				"_________Start SendAmpFreq__________".postln;
				~mySendAmpFreq.start; 	
			},{
				"_________Stop SendAmpFreq__________".postln;
				~mySendAmpFreq.stop; 	
			});
		}).add;
		
		~glBeginTypeResp.remove;
		~glBeginTypeResp = OSCresponderNode(~ofNetwork, '/glBeginType', { | time, resp, msg| 
			OF.interactWithSound("glBeginType", msg[1].asInteger);
		} ).add; 
		
		~numSoundSketchesSpec = ControlSpec(1, 500, \lin);
		~numSoundSketchesResp.remove;
		~numSoundSketchesResp = OSCresponderNode(~ofNetwork, '/numSoundSketches', { | time, resp, msg| 
			OF.interactWithSound("numSoundSketches", ~numSoundSketchesSpec.map(msg[1]).asInteger);
		} ).add; 
		
		~maxFreqInSpec = ControlSpec(20, 3000, \lin);
		~maxFreqInResp.remove;
		~maxFreqInResp = OSCresponderNode(~ofNetwork, '/maxFreqIn', { | time, resp, msg| 
			OF.interactWithSound("maxFreqIn", ~maxFreqInSpec.map(msg[1]));
		} ).add; 
		
		~maxAmpInSpec = ControlSpec(0.001, 1, \lin);
		~maxAmpInResp.remove;
		~maxAmpInResp = OSCresponderNode(~ofNetwork, '/maxAmpIn', { | time, resp, msg| 
			OF.interactWithSound("maxAmpIn", ~maxAmpInSpec.map(msg[1]));
		}).add; 
		
		~elasticitySpec = ControlSpec(0.001, 2, \lin);
		~elasticityResp.remove;
		~elasticityResp = OSCresponderNode(~ofNetwork, '/elasticity', { | time, resp, msg| 
			OF.interactWithSound("maxSoundElasticity", ~elasticitySpec.map(msg[1]));
		} ).add; 

		~aSoundSpec = ControlSpec(0, 255, \lin);
		~aSoundResp.remove;
		~aSoundResp = OSCresponderNode(~ofNetwork, '/aSound', { | time, resp, msg| 
			OF.rgb("sound",255,255,255,~aSoundSpec.map(msg[1]).asInteger);
		} ).add; 	
		
		~padSketchResp.remove;
		~padSketchResp = OSCresponderNode(~ofNetwork, '/padSketch', { |t,r,msg| 
			OF.interactWithSketch("activate", msg[1].asInteger);
		}).add;

		~xPadSpec = ControlSpec(0, ~width, \lin);
		~yPadSpec = ControlSpec(~height, 0, \lin);
		~padSketchXYResp.remove;
		~padSketchXYResp = OSCresponderNode(~ofNetwork, '/padSketchXY', { | time, resp, msg| OF.interactWithSketch("padSketchXY", ~xPadSpec.map(msg[2]).asInteger, ~yPadSpec.map(msg[1]).asInteger);
		}).add; 

		~aPadSpec = ControlSpec(0, 255, \lin);
		~aPadResp.remove;
		~aPadResp = OSCresponderNode(~ofNetwork, '/aPad', { | time, resp, msg| 
			OF.rgb("sketch",255,255,255,~aPadSpec.map(msg[1]).asInteger);
		} ).add; 
		
		~padSketchTypeResp.remove;
		~padSketchTypeResp = OSCresponderNode(~ofNetwork, '/padSketchType', { | time, resp, msg| 
			OF.interactWithSketch("mouseLines",msg[1].asInteger);
		} ).add; 

	}
	*iPadRespondersSketchTasks	{
		
		~btw1 = Task({
			inf.do({
				OF.rgb("sound",255,255,255,55);				
				0.04.wait;	
				OF.rgb("sound",0,0,0,55);
				0.04.wait;	
			});
		});
		
		~btw1Resp.remove;
		~btw1Resp = OSCresponderNode(~ofNetwork, '/btw1', { |t,r,msg| 
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
		~btw2Resp = OSCresponderNode(~ofNetwork, '/btw2', { |t,r,msg| 
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
		~btw3Resp = OSCresponderNode(~ofNetwork, '/btw3', { |t,r,msg| 
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
		~elastTaskResp = OSCresponderNode(~ofNetwork, '/elastTask', { |t,r,msg| 
			if( msg[1] == 1,{
				~elastTask.play;
			},{
				~elastTask.stop;
			});
		}).add;
		
		~blackWhiteSoundResp.remove;
		~blackWhiteSoundResp = OSCresponderNode(~ofNetwork, '/blackWhiteSound', { |t,r,msg| 
			if( msg[1] == 1,{
				OF.rgb("sound",255,255,255,120);
			},{
				OF.rgb("sound",0,0,0,120);				
			});
		}).add;
	}
}
