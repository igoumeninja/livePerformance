
/*
//:Create class for load buffers to Server


MyBuffers.seals;
MyBuffers.swallows;
MyBuffers.conet;

MyBuffers.osmoFull;


//: -----
*/

MyBuffers {
	
	*initClass {
		StartUp add: {
			"-MyBuffers".postln;
			this.simple;
		}
	}

	*simple	{

		BufferResource loadPaths: UserPath("Projects/artistic/AB_Performances/livePerformance/scCode/livePerformanceSCclasses/Play_Sound/buffer-lists/simple-sounds.txt").load;
	}

}

