
GeneralResponders {
	*initClass {
		StartUp add: {			
			"-GeneralResponders".postln;
			this.iPadRespondersGlobal;
		}
	}
	*iPadRespondersGlobal {
		

		~rebootServerResp.remove;
		~rebootServerResp = OSCresponderNode(nil, '/rebootServer', { |t,r,msg|
			Server.default.reboot;
		}).add;
		

		~thisProcessStopResp.remove;
		~thisProcessStopResp = OSCresponderNode(nil, '/thisProcessStop', { |t,r,msg|
			thisProcess.stop;
		}).add;
		
		~blackBackgroundResp.remove;
		~blackBackgroundResp = OSCresponderNode(nil, '/blackBackground', { |t,r,msg| 
			OF.background(0,0,0);				
		}).add;
		
		~blackBackgroundAlpha.remove;
		~blackBackgroundAlphaResp = OSCresponderNode(nil, '/blackBackgroundAlpha', { |t,r,msg| 
			if( msg[1] == 1,{
				OF.background(0,0,0,20);
			},{
				OF.background(0,0,0,0);
			});
		}).add;

		~alphaBackgroundSpec = ControlSpec(0, 125, \lin);
		~alphaBackgroundResp.remove;
		~alphaBackgroundResp = OSCresponderNode(nil, '/alphaBackground', { | time, resp, msg| 
			OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
		} ).add; 		

		~whiteOnsetResp.remove;
		~whiteOnsetResp = OSCresponderNode(nil, '/whiteBackOnset', { | time, resp, msg| 
			if( msg[1] == 1,{
				~onsetSketchColorResp = OSCresponderNode(nil, 'onset', { |t,r,msg|
					OF.rect(0,0,~width,~height,255,255,255,255);
				}).add;
			},{
				~onsetSketchColorResp.remove;
			});
			
			OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
		} ).add; 		
		
	}
}
