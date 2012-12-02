/*
This class is used as a startup file

Aris Bezas Igoumeninja 121201
*/

StartupLivePerformance {

	*initClass {
		StartUp add: {
			"-StartupLivePerformance".postln;
			this.startupLivePerformance;
		}
	}

	*startupLivePerformance {
		~ofNetwork = NetAddr("127.0.0.1", 12345);
		~scNetwork = NetAddr("127.0.0.1", 57120);
		NotesData.load;

		~width = 1280;
		~height = 1024;
		~screen1 = 0;
		~screen2 = ~width/3;
		~screen3 = (~width*2)/3;
		~screenWidth = ~width/3;

		~delayTechnoetic = 0.8;

		// Take a string from file
		~myString = File.use(
			"/Users/ari/Projects/artistic/AB_Performances/livePerformance/data/text/text", "r", { |f|f.readAllString});

		// Sound Interaction
		Task({
			if (not(Server.default.serverRunning)) { Server.default.boot };
			Server.default.doWhenBooted {
				~mlabResponders = MakeResponders.new;
				~mlabResponders.all;
				OF.mlab("fftColor", 1,1,1); // the range of the color is 0 < fftColor < 1
				//~mySendSpectrogramData = SendSpectrogramData.new;
				SynthDef(\input, { | level = 1| Out.ar(0,In.ar(8)*level)}).send(Server.default);
				~mySendAmpFreq = SendAmpFreq.new;
				~mySendOnsets = SendOnsets.new;
				0.04.wait;
				//Server.default.mute;
				//~inputSpectroSynth = Synth(\input).play(Server.default);

			};
		}).play;

		"\nNotialb LiveAudiovisualTecho is ready to go\n".postln;
	}

}
