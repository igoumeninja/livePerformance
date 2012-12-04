NotialbScores

{

	*load{

		//MasterEQ(2);


		//:SendTags TODO: all the netaddres to write one time
		//:================================================================

		//NetAddresses


		~alex     = NetAddr("192.168.1.5", 57120);
		~vaggelis = NetAddr("192.168.1.6", 57120);
		~aris     = NetAddr("localhost", 57120);
		~eisagogi = SendTags.new;
		~eisagogi.dests = [~vaggelis, ~aris, ~alex];
		~eisagogi.title = '/tags';
		~eisagogi.tags = [1, 2, 3, 4];
		~eisagogi.step = [1];
		~eisagogi.times = inf;
		//~eisagogi.loop;


		~aftereisagogi = SendTags.new;
		~aftereisagogi.dests = [Dests.mineAddr, ~aris, ~alex];
		~aftereisagogi.title = '/tags1';
		~aftereisagogi.tags = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
		~aftereisagogi.step = [1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6];
		~aftereisagogi.times = inf;
		//~aftereisagogi.loop;

		~meros1 = SendTags.new;
		~meros1.dests = [~vaggelis, ~aris, ~alex];
		~meros1.title = '/tags2';
		~meros1.tags = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
		~meros1.step = [1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6];
		~meros1.times = inf;
		//~meros1.loop;

		//:RespTags
		//:================================================================

		~a1 = RespTags.do('/tags', 1, nil);
		~a2 = RespTags.do('/tags', 2, nil);
		~a3 = RespTags.do('/tags', 3, nil);
		~a4 = RespTags.do('/tags', 4, nil);


		~b1 = RespTags.do('/tags1', 1, nil);
		~b2 = RespTags.do('/tags1', 2, nil);
		~b3 = RespTags.do('/tags1', 3, nil);
		~b4 = RespTags.do('/tags1', 4, nil);
		~b5 = RespTags.do('/tags1', 5, nil);
		~b6 = RespTags.do('/tags1', 6, nil);
		~b7 = RespTags.do('/tags1', 7, nil);
		~b8 = RespTags.do('/tags1', 8, nil);
		~b9 = RespTags.do('/tags1', 9, nil);
		~b10 = RespTags.do('/tags1', 10, nil);
		~b11 = RespTags.do('/tags1', 11, nil);

		~r1 = RespTags.do('/tags2', 1, nil);
		// ~c2 = RespTags.do('/tags2', 2, nil);
		// ~c3 = RespTags.do('/tags2', 3, nil);
		// ~c4 = RespTags.do('/tags2', 4, nil);
		// ~c5 = RespTags.do('/tags2', 5, nil);
		// ~c6 = RespTags.do('/tags2', 6, nil);
		// ~c7 = RespTags.do('/tags2', 7, nil);
		// ~c8 = RespTags.do('/tags2', 8, nil);
		// ~c9 = RespTags.do('/tags2', 9, nil);
		// ~c10 = RespTags.do('/tags2', 10, nil);
		// ~c11 = RespTags.do('/tags2', 11, nil);



		//:Actions of Responders
		//:================================================================

		NilTheCounters.load;

		//:Eisagogi_Actions
		//:========================

		~a1.action = {
			("Metro: ").post; ~counter1.postln;
			'a1'.postln;

			if (~counter1 == 0) { ("-EISAGOGI-").postln;
				//MasterEQ.start;
				Pdef(\import).play;
				~aris.sendMsg("eisagogi");
			};
			if (~counter1 == 21) {
				~aris.sendMsg("eisagogiFeedBack");
			};
			~counter1 = ~counter1 + 1;
		};

		~a2.action = {'2'.postln;};

		~a3.action = {'3'.postln;};

		~a4.action = {'4'.postln; ~counter4 = ~counter4 + 1;


			if (~counter4 == 45) {

				NilTheCounters.load;
				~eisagogi.stop;
				~aftereisagogi.loop;
				//MasterEQ.stop;

				~a1.removeResp;
				~a2.removeResp;
				~a3.removeResp;
				~a4.removeResp;


			};



		};

		//:After_Eisagogi_Actions
		//:========================

		~b1.action = {'1'.postln; ~counter1 = ~counter1 + 1;
			("Metro: ").post; ~counter1.postln;

			if (~counter1 == 1) { ("==============AFTER_EISAGOGI").postln;

				~aris.sendMsg("afterEisagogi");
				Pdef(\afterEisagogi).play;



			};

			//OF.background(255, 255, 255, 255);

		};


		~b2.action = {'2'.postln;};
		~b3.action = {'3'.postln;

			//OF.background(0, 0, 0, 0);

		};
		~b4.action = {'4'.postln;};
		~b5.action = {'5'.postln;};
		~b6.action = {'6'.postln;};
		~b7.action = {'7'.postln;};
		~b8.action = {'8'.postln;};
		~b9.action = {'9'.postln;};
		~b10.action = {'10'.postln;};

		~b11.action = {'11'.postln; ~counter11 = ~counter11 + 1;

			if (~counter11 == 25) {

				Pdef(\afterEisagogi).stop;
				~aftereisagogi.stop;
				~meros1.loop;
				NilTheCounters.load;

			};


		};


		//:Meros_1_Actions //~meros1.loop;
		//:========================

		~r1.action = {'1'.postln; ~counter1 = ~counter1 + 1;
			("Metro: ").post; ~counter1.postln;


			if (~counter1 == 1) { ("==============MEROS_1").postln;

				//MasterEQ.start;
				Pdef(\meros_1).play;
				~aris.sendMsg("meros1o");

			};

		};


		// ~c2.action = {'2'.postln;};
		// ~c3.action = {'3'.postln;};
		// ~c4.action = {'4'.postln;};
		// ~c5.action = {'5'.postln;};
		// ~c6.action = {'6'.postln;};
		// ~c7.action = {'7'.postln;};
		// ~c8.action = {'8'.postln;};
		// ~c9.action = {'9'.postln;};
		// ~c10.action = {'10'.postln;};
		// ~c11.action = {'11'.postln;};





	}
}









