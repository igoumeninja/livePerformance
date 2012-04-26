/*
This class is used as a startup file

Aris Bezas Igoumeninja -> Corfu 120405

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
		};					
	}).play;

	
	
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/of-sc-glossary.scd".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/StartupLivePerformance.sc".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/".unixCmd;
"open /Users/ari/Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/iPadResponders.sc".unixCmd;



	
		"".postln;	
		"################################".postln;
		"#######START THE LIVE!!!########".postln;
		"################################".postln;	
		"".postln;		
	}
		
}
