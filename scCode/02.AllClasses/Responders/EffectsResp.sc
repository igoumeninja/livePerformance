
EffectsResponders {
	*initClass {
		StartUp add: {
			"-EffectsResponders".postln;			
			this.iPadRespondersEffects;
		}
	}
	*iPadRespondersEffects	{
		
		//Typography Effect
		
		~typoEffectTask = Task({
			inf.do({|i|
				OF.writeString("bigCenter", "~`!@#$%^&*()_+}{][|".at(rrand(0,10).asInteger).asString,~width/2 - 100,~height/2,255,255,255,255);				
					0.04.wait;	
			});
		});
		
		~typoEffectResp.remove;
		~typoEffectResp = OSCresponderNode(nil, '/typoEffect', { |t,r,msg| 
			if( msg[1] == 1,{
				~typoEffect.play;
			},{
				~typoEffect.stop;
			});
		}).add;

		~noiseEffectResp.remove;
		~noiseEffectResp = OSCresponderNode(nil, '/noiseEffect', { |t,r,msg| 
			OF.effect("noiseEffect", msg[1]);
			OF.background(0,0,0);
		}).add;
		
		
		~feedbackResp.remove;
		~feedbackResp = OSCresponderNode(nil, '/feedback', { |t,r,msg| 
			OF.feedback("activate", msg[1]);
		}).add;
		
		~xFeedbackSpec = ControlSpec(-5, 5, \lin);
		~yFeedbackSpec = ControlSpec(5, -5, \lin);
		~feedbackXYResp.remove;
		~feedbackXYResp = OSCresponderNode(nil, '/feedbackXY', { | time, resp, msg| 
			OF.feedback("speedXY", ~xFeedbackSpec.map(msg[1]), ~yFeedbackSpec.map(msg[2]));
		} ).add; 
		
		
		~destructEffect.remove;
		~destructEffect = OSCresponderNode(nil, '/destructEffect', { |t,r,msg|
			OF.effect("destruct", "activate", msg[1]);
		}).add;
		
		~destructEffect0.remove;
		~destructEffect0 = OSCresponderNode(nil, '/destructEffect0', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("destruct", "case", 0);
			});
		}).add;
		
		~destructEffect1.remove;
		~destructEffect1 = OSCresponderNode(nil, '/destructEffect1', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("destruct", "case", 1);
			});
		}).add;
		
		~destructEffect2.remove;
		~destructEffect2 = OSCresponderNode(nil, '/destructEffect2', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("destruct", "case", 2);
			});
		}).add;
		
		~destructEffect3.remove;
		~destructEffect3 = OSCresponderNode(nil, '/destructEffect3', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("destruct", "case", 3);
			});
		}).add;
		
		~destructEffect4.remove;
		~destructEffect4 = OSCresponderNode(nil, '/destructEffect4', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("destruct", "case", 4);
			});
		}).add;
		
		~mirrorEffect.remove;
		~mirrorEffect = OSCresponderNode(nil, '/mirrorEffect', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", 1);
			},{
				OF.effect("mirror", 0);
			});
		}).add;
		
		~mirrorEffectCase0Resp.remove;
		~mirrorEffectCase0Resp = OSCresponderNode(nil, '/mirrorEffect0', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", "case", 0); 
			});
		}).add;
		
		~mirrorEffectCase1Resp.remove;
		~mirrorEffectCase1Resp = OSCresponderNode(nil, '/mirrorEffect1', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", "case", 1); 
			});
		}).add;
		
		~mirrorEffectCase2Resp.remove;
		~mirrorEffectCase2Resp = OSCresponderNode(nil, '/mirrorEffect2', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", "case", 2); 
			});
		}).add;
		
		~mirrorEffectCase3Resp.remove;
		~mirrorEffectCase3Resp = OSCresponderNode(nil, '/mirrorEffect3', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", "case", 3); 
			});
		}).add;
		
		~mirrorEffectCase4Resp.remove;
		~mirrorEffectCase4Resp = OSCresponderNode(nil, '/mirrorEffect4', { |t,r,msg|
			if( msg[1] == 1,{
				OF.effect("mirror", "case", 4); 
			});
		}).add;
		
		~treeEffectResp.remove;~treeEffectResp = OSCresponderNode(nil, '/treeEffect', { | time, resp, msg|
			if(msg[1] == 1,{
				OF.effect("tree");
			})
		}).add; 
		
	}
}
