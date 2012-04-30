/*
This class is used as a startup file

Aris Bezas Igoumeninja -> Corfu 120405

Manually Compile the folowing code

OF.playSpectro("mirrorMode", 4);
OF.mlab("fftColor", 1,1,1); // the range of the color is 0 < fftColor < 1
~mySendSpectrogramData = SendSpectrogramData.new; 
SynthDef(\input, { | level = 1| Out.ar(0,In.ar(8)*level)}).send(s);
s.mute;
i = Synth(\input).play
i.set(\level, 2);

*/

StartupLivePerformance {
	
	*initClass {
		StartUp add: {
			
			this.startupLivePerformance;

		}
	}

	*startupLivePerformance {

	~width = 1280;
	~height = 1024;

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
			Server.default.mute;
			
		};
		
							
	}).play;

	
	
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/of-sc-glossary.scd".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/StartupLivePerformance.sc".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/iPadResponders.sc".unixCmd;



	
		"".postln;	
		"##################################".postln;
		"######## START THE LIVE!!!########".postln;
		"##################################".postln;	
		"".postln;		
	}
		
}
