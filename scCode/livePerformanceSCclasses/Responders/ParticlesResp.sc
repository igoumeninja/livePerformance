ParticlesResponders {
	*initClass {
		StartUp add: {			
			"-ParticlesResponders".postln;
			this.particlesGeneralResponders;
			this.particlesSoundResponders;			
		}
	}
	*particlesSoundResponders	{	
		~pushOnsetResp.remove;
		~pushOnsetResp = OSCresponderNode(nil, '/pushParticlesOnset', { | time, resp, msg| 
			if( msg[1] == 1,{
				OF.particle("pusher","set", 10, 800,200);
				~onsetPushParticlesResp = OSCresponderNode(nil, 'onset', { |t,r,msg|
					"onsetParticle".postln;
					{
						2.do	{
							OF.particle("forceRadius", 300);
							0.5.wait;
							OF.particle("forceRadius", 10);
						}
					}.fork;
				}).add;
			},{
				~onsetPushParticlesResp.remove;
			});
			
			OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
		} ).add; 		
	}
	*particlesGeneralResponders	{
		OF.particle("dotColor",255,255,255,25);	// RGBA
		OF.particle("conColor",255,255,255,25);	// RGBA
		
		~activateParticlesResp.remove;
		~activateParticlesResp = OSCresponderNode(nil, '/activateParticles', { |t,r,msg| 
			OF.particle("activate", msg[1].round);
		}).add;
		
		~forceRadiusSpec = ControlSpec(0, 500, \lin);
		~forceRadiusResp.remove;
		~forceRadiusResp = OSCresponderNode(nil, '/forceRadius', { | time, resp, msg|
			OF.particle("forceRadius", ~forceRadiusSpec.map(msg[1]).asInteger);
		}).add; 
		
		~particleNeighborhoodSpec = ControlSpec(0, 30, \lin);
		~particleNeighborhoodResp.remove;
		~particleNeighborhoodResp = OSCresponderNode(nil, '/particleNeighborhood', { | time, resp, msg|
			OF.particle("particleNeighborhood", ~particleNeighborhoodSpec.map(msg[1]).asInteger);
		}).add; 
		
		~particleConnectionsAlphaSpec = ControlSpec(0, 25, \lin);
		~particleConnectionsAlphaResp.remove;
		~particleConnectionsAlphaResp = OSCresponderNode(nil, '/particleConnectionsAlpha', { | time, resp, msg|
			OF.particle("conColor", ~particleConnectionsAlphaSpec.map(msg[1]).asInteger);
		}).add; 
		
		~particlesAlphaSpec = ControlSpec(0, 255, \lin);
		~particlesAlphaResp.remove;
		~particlesAlphaResp = OSCresponderNode(nil, '/particlesAlpha', { | time, resp, msg|
			OF.particle("dotColor", ~particlesAlphaSpec.map(msg[1]).asInteger);
		}).add; 
		
		~particlesRedConResp.remove;
		~particlesRedConResp = OSCresponderNode(nil, '/redCon', { | time, resp, msg|
			if( msg[1] == 1,{
				OF.particle("conColor", 255,0,0);
			},{
				OF.particle("conColor", 255,255,255);
			});
		}).add; 
		
		~particlesYellowDotResp.remove;
		~particlesYellowDotResp = OSCresponderNode(nil, '/yellowDot', { | time, resp, msg|
			if( msg[1] == 1,{
				OF.particle("dotColor", 255,255,0);
			},{
				OF.particle("dotColor", 255,255,255);
			});
		}).add; 
		
		~addParticlesResp.remove;
		~addParticlesResp = OSCresponderNode(nil, '/addParticles', { |t,r,msg| 
			{
				500.do{
					OF.particle("add", (~width/2).asInteger, (~height/2).asInteger, 0.1, 10.1);
					0.04.wait;
				}	
			}.fork
		}).add;
		
		~iPadPushResp.remove;
		~iPadPushResp = OSCresponderNode(nil, '/iPadPush', { |t,r,msg| 
			if( msg[1] == 1,{
				OF.particle("pusher","set", 10, 200,200);
			},{
				OF.particle("pusher","remove", 10);
			});
		}).add;
		
		~iPadPushXYResp.remove;
		~iPadPushXYResp = OSCresponderNode(nil, '/iPadPushXY', { |t,r,msg| 
			if( msg[1] == 1,{
				OF.particle("pusher","set", 100, 200,200);
			},{
				OF.particle("pusher","remove", 100);
			});
		}).add;
		
		
		~xPushPadSpec = ControlSpec(0, ~width, \lin);
		~yPushPadSpec = ControlSpec(~height, 0, \lin);
		OSCresponderNode(nil, '/pushXY', { | time, resp, msg| 
			OF.particle("pusher","set", 100, ~xPushPadSpec.map(msg[2]).asInteger, ~yPushPadSpec.map(msg[1]).asInteger);
		} ).add; 
		
		~pushParticlesOnSetResp.remove;
		~pushParticlesOnSetResp = OSCresponderNode(nil, '/pushParticlesOnSet', { |t,r,msg| 
			if( msg[1] == 1,{
				~mySendOnsets.start;
			},{
				~mySendOnsets.stop;
			});
		}).add;
	}
}
