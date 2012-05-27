SpectroResponders {
	*initClass {
		StartUp add: {			
			"-SpectroResponders".postln;
			this.iPadRespondersSpectro;
		}
	}
	*iPadRespondersSpectro	{
		
		~activateSpectroResp.remove;
		~activateSpectroResp = OSCresponderNode(nil, '/activateSpectro', { |t,r,msg| 
			if( msg[1] == 1,{
				~mySendSpectrogramData.connectToPoller;
			},{
				~mySendSpectrogramData.stopSending;
			});
		}).add;
		
		~soundInLevelSpec = ControlSpec(0, 1, \lin);
		~soundInLevelResp.remove;
		~soundInLevelResp = OSCresponderNode(nil, '/soundInLevel', { | time, resp, msg|
			~inputSpectroSynth.set(\level, ~soundInLevelSpec.map(msg[1]));
		}).add; 

		~rotSpectroSpec = ControlSpec(0, 0.48, \lin);
		~rotSpectroResp.remove;
		~rotResp = OSCresponderNode(nil, '/rotSpectro', { | time, resp, msg|
			OF.playSpectro("rotCircSpect", ~rotSpectroSpec.map(msg[1]));
		}).add; 
		
		~spectroMirrorMode0.remove;
		~spectroMirrorMode0 = OSCresponderNode(nil, '/spectroMirrorMode0', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 0);
			});
		}).add;

		~spectroMirrorMode1.remove;
		~spectroMirrorMode1 = OSCresponderNode(nil, '/spectroMirrorMode1', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 1);
			});
		}).add;

		~spectroMirrorMode2.remove;
		~spectroMirrorMode2 = OSCresponderNode(nil, '/spectroMirrorMode2', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 2);
			});
		}).add;

		~spectroMirrorMode3.remove;
		~spectroMirrorMode3 = OSCresponderNode(nil, '/spectroMirrorMode3', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 3);
			});
		}).add;

		~spectroMirrorMode4.remove;
		~spectroMirrorMode4 = OSCresponderNode(nil, '/spectroMirrorMode4', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 4);
			});
		}).add;

		~spectroMirrorMode5.remove;
		~spectroMirrorMode5 = OSCresponderNode(nil, '/spectroMirrorMode5', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 5);
			});
		}).add;

		~spectroMirrorMode6.remove;
		~spectroMirrorMode6 = OSCresponderNode(nil, '/spectroMirrorMode6', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 6);
			});
		}).add;

		~spectroMirrorMode7.remove;
		~spectroMirrorMode7 = OSCresponderNode(nil, '/spectroMirrorMode7', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 7);
			});
		}).add;

		~spectroMirrorMode8.remove;
		~spectroMirrorMode8 = OSCresponderNode(nil, '/spectroMirrorMode8', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 8);
			});
		}).add;

		~spectroMirrorMode9.remove;
		~spectroMirrorMode9 = OSCresponderNode(nil, '/spectroMirrorMode9', { |t,r,msg|
			if( msg[1] == 1,{
				 OF.playSpectro("mirrorMode", 9);
			});
		}).add;
		~spectroMirrorMode10.remove;
		~spectroMirrorMode10 = OSCresponderNode(nil, '/spectroMirrorMode10', { |t,r,msg|
			if( msg[1] == 1,{
				 OF.playSpectro("mirrorMode", 10);
			});
		}).add;

	}
}
