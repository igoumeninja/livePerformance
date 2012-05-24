
GeneralResponders {
	*initClass {
		StartUp add: {			
			"-GeneralResponders".postln;
			this.iPadRespondersGlobal;
		}
	}
	*iPadRespondersGlobal {
		
		// My Global Sound Setup
		~mySendAmpFreq = SendAmpFreq.new; 
		~mySendOnsets = SendOnsets.new;

		~rebootServerResp.remove;
		~rebootServerResp = OSCresponderNode(~ofNetwork, '/rebootServer', { |t,r,msg|
			Server.default.reboot;
		}).add;
		

		~thisProcessStopResp.remove;
		~thisProcessStopResp = OSCresponderNode(~ofNetwork, '/thisProcessStop', { |t,r,msg|
			thisProcess.stop;
		}).add;
		
		~blackBackgroundResp.remove;
		~blackBackgroundResp = OSCresponderNode(~ofNetwork, '/blackBackground', { |t,r,msg| 
			OF.background(0,0,0);				
		}).add;
		
		~blackBackgroundAlpha.remove;
		~blackBackgroundAlphaResp = OSCresponderNode(~ofNetwork, '/blackBackgroundAlpha', { |t,r,msg| 
			if( msg[1] == 1,{
				OF.background(0,0,0,20);
			},{
				OF.background(0,0,0,0);
			});
		}).add;

		~alphaBackgroundSpec = ControlSpec(0, 125, \lin);
		~alphaBackgroundResp.remove;
		~alphaBackgroundResp = OSCresponderNode(~ofNetwork, '/alphaBackground', { | time, resp, msg| 
			OF.background(~alphaBackgroundSpec.map(msg[1]).asInteger);
		} ).add; 		

		~whiteOnsetResp.remove;
		~whiteOnsetResp = OSCresponderNode(~ofNetwork, '/whiteBackOnset', { | time, resp, msg| 
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
