SpectroResponders {
	*initClass {
		StartUp add: {			
			"-SpectroResponders".postln;
			this.iPadRespondersSpectro;
		}
	}
	*iPadRespondersSpectro	{
		
		~activateSpectroResp.remove;
		~activateSpectroResp = OSCresponderNode(~ofNetwork, '/activateSpectro', { |t,r,msg| 
			if( msg[1] == 1,{
				~mySendSpectrogramData.stopSending;
				~mySendSpectrogramDataSynth.free;
				~mySendSpectrogramDataSynth = Synth(\input).play;
				~mySendSpectrogramDataSynth.set(\level, 2);
				~mySendSpectrogramData.connectToPoller;
			},{
				~mySendSpectrogramData.stopSending;
				~mySendSpectrogramDataSynth.free;
			});
		}).add;
		
		~soundInLevelSpec = ControlSpec(0, 10, \lin);
		~soundInLevelResp.remove;
		~soundInLevelResp = OSCresponderNode(~ofNetwork, '/soundInLevel', { | time, resp, msg|
			~mySendSpectrogramDataSynth.set(\level, ~soundInLevelSpec.map(msg[1]));
		}).add; 
		
		~spectroMirrorMode0.remove;
		~spectroMirrorMode0 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode0', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 0);
			});
		}).add;

		~spectroMirrorMode1.remove;
		~spectroMirrorMode1 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode1', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 1);
			});
		}).add;

		~spectroMirrorMode2.remove;
		~spectroMirrorMode2 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode2', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 2);
			});
		}).add;

		~spectroMirrorMode3.remove;
		~spectroMirrorMode3 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode3', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 3);
			});
		}).add;

		~spectroMirrorMode4.remove;
		~spectroMirrorMode4 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode4', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 4);
			});
		}).add;

		~spectroMirrorMode5.remove;
		~spectroMirrorMode5 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode5', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 5);
			});
		}).add;

		~spectroMirrorMode6.remove;
		~spectroMirrorMode6 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode6', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 6);
			});
		}).add;

		~spectroMirrorMode7.remove;
		~spectroMirrorMode7 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode7', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 7);
			});
		}).add;

		~spectroMirrorMode8.remove;
		~spectroMirrorMode8 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode8', { |t,r,msg|
			if( msg[1] == 1,{
				OF.playSpectro("mirrorMode", 8);
			});
		}).add;

		~spectroMirrorMode9.remove;
		~spectroMirrorMode9 = OSCresponderNode(~ofNetwork, '/spectroMirrorMode9', { |t,r,msg|
			if( msg[1] == 1,{
				 OF.playSpectro("mirrorMode", 9);
			});
		}).add;
	}
}
