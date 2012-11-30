/*
This class is used as a startup file

Aris Bezas Igoumeninja -> Corfu 120405
~delay = 1;
*/

StartupLivePerformance {

	*initClass {
		StartUp add: {
			"-StartupLivePerformance".postln;
			this.startupLivePerformance;
		}
	}

	*startupLivePerformance {
		//Error.debug = true;
		~ofNetwork = NetAddr("127.0.0.1", 12345);
		~scNetwork = NetAddr("127.0.0.1", 57120);

		~width = 1280;
		~height = 1024;

		~delayTechnoetic = 0.8;

		// Take a string from file
		~myString = File.use("/Users/ari/Projects/artistic/AB_Performances/livePerformance/data/text/text", "r", { |f|f.readAllString});

		// Sound Interaction
		Task({
			if (not(Server.default.serverRunning)) { Server.default.boot };
			Server.default.doWhenBooted {
				~mlabResponders = MakeResponders.new;
				~mlabResponders.all;
				OF.mlab("fftColor", 1,1,1); // the range of the color is 0 < fftColor < 1
				~mySendSpectrogramData = SendSpectrogramData.new;
				SynthDef(\input, { | level = 1| Out.ar(0,In.ar(8)*level)}).send(Server.default);
				~mySendAmpFreq = SendAmpFreq.new;
				~mySendOnsets = SendOnsets.new;
				0.04.wait;
				//Server.default.mute;
				//~inputSpectroSynth = Synth(\input).play(Server.default);

			};
		}).play;

		// "open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/of-sc-glossary.scd".unixCmd;
		// "open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/StartUp/StartupLivePerformance.sc".unixCmd;
		// "open /Users/ari/Projects/artistic/AB_Performances/livePerformance/".unixCmd;
		// "open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/Controller/iPadResponders.sc".unixCmd;

		"".postln;
		"##################################".postln;
		"######## START THE LIVE!!!########".postln;
		"##################################".postln;
		"".postln;
	}

}
