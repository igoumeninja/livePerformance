

Notialb{


	*load {

		Eisagogi_InLine.load;
		AfterEisagogi_InLine.load;
		Meros1_InLine.load;
		Meros2_InLine.load;
		NotialbScores.load;
		Central_Sequencer.load;
	}

	*playAll {

		NilTheCounters.load;
		~eisagogi.loop;

	}

	*playAfterEisagogi {

		NilTheCounters.load;
		~aftereisagogi.loop;

	}

	*playMeros1 {

		NilTheCounters.load;
		~meros1.loop;
	}

	*playMeros2 {

		NilTheCounters.load;
		~meros2.loop;

	}


}



/*
Notialb.load;
Notialb.playMeros1;
Notialb.playAfterEisagogi;
Notialb.playAll;
*/

