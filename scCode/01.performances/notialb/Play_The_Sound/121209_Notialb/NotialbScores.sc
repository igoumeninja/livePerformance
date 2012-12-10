NotialbScores
{
	*load{

		//MasterEQ(2);

		//:SendTags TODO: all the netaddres to write one time
		//:================================================================

		//NetAddresses
		~alex     = NetAddr("169.254.145.99", 57121);
		~vaggelis = NetAddr("192.168.1.6", 57120);
		//~aris     = NetAddr("192.168.1.7", 57120);
		~aris     = NetAddr("127.0.0.1", 57120);

		~eisagogi = SendTags.new;
		~eisagogi.dests = [~vaggelis, ~aris, ~alex];
		~eisagogi.title = '/tags';
		~eisagogi.tags = [1, 2, 3, 4];
		~eisagogi.step = [1];
		~eisagogi.times = inf;
		//~eisagogi.loop;


		~aftereisagogi = SendTags.new;
		~aftereisagogi.dests = [~vaggelis, ~aris, ~alex];
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


		~meros2 = SendTags.new;
		~meros2.dests = [~vaggelis, ~aris, ~alex];
		~meros2.title = '/tags3';
		~meros2.tags = [1, 2, 3, 4];
		~meros2.step = [1, 1/2, 3/2, 1];
		~meros2.times = inf;
		//~meros2.loop;


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

		~c1 = RespTags.do('/tags2', 1, nil);
		~c2 = RespTags.do('/tags2', 2, nil);
		~c3 = RespTags.do('/tags2', 3, nil);
		~c4 = RespTags.do('/tags2', 4, nil);
		~c5 = RespTags.do('/tags2', 5, nil);
		~c6 = RespTags.do('/tags2', 6, nil);
		~c7 = RespTags.do('/tags2', 7, nil);
		~c8 = RespTags.do('/tags2', 8, nil);
		~c9 = RespTags.do('/tags2', 9, nil);
		~c10 = RespTags.do('/tags2', 10, nil);
		~c11 = RespTags.do('/tags2', 11, nil);

		~d1 = RespTags.do('/tags3', 1, nil);
		~d2 = RespTags.do('/tags3', 2, nil);
		~d3 = RespTags.do('/tags3', 3, nil);
		~d4 = RespTags.do('/tags3', 4, nil);

		//:Actions of Responders
		//:================================================================

		NilTheCounters.load;

		//:Eisagogi_Actions
		//:========================

		~a1.action = {

			("Metro: ").post; ~counter1.postln;

			if (~counter1 == 1) { ("==============EISAGOGI").postln;

				~aris.sendMsg("eisagogi");
				Pdef(\import).play;

			};

			if (~counter1 == 21) {~aris.sendMsg("eisagogiFeedBack");};

			~counter1 = ~counter1 + 1;
		};

		~a2.action = {};

		~a3.action = {};

		~a4.action = {


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

			~counter4 = ~counter4 + 1;

		};

		//:After_Eisagogi_Actions
		//:========================

		~b1.action = {

			if (~counter1 == 1) { ("==============AFTER_EISAGOGI").postln;

				~aris.sendMsg("afterEisagogi");
				Pdef(\afterEisagogi).play;



			};

			~counter1 = ~counter1 + 1;


		};


		~b2.action = {};

		~b3.action = {};

		~b4.action = {};

		~b5.action = {};

		~b6.action = {};

		~b7.action = {};

		~b8.action = {};

		~b9.action = {};

		~b10.action = {};

		~b11.action = {

			if (~counter11 == 67) {


				Pdef(\afterEisagogi).stop;
				~aftereisagogi.stop;
				~meros1.loop;

				NilTheCounters.load;


			};


			~counter11 = ~counter11 + 1;

		};


		//:Meros_1_Actions //~meros1.loop;
		//:========================

		~c1.action = {


			if (~counter1 == 1) { ("==============MEROS_1").postln;


				Pdef(\meros_1o).play;
				~aris.sendMsg("meros1o");

			};

			~counter1 = ~counter1 + 1;

		};


		~c2.action = {};

		~c3.action = {};

		~c4.action = {};

		~c5.action = {};

		~c6.action = {};

		~c7.action = {};

		~c8.action = {};

		~c9.action = {};

		~c10.action = {};

		~c11.action = {

			if (~counter11 == 132) {


				//Pdef(\meros_1o).stop;
				NilTheCounters.load;

				~meros1.stop;
				~meros2.loop;

			};

			~counter11 = ~counter11 + 1;

		};



		//:Meros_2_Actions //~meros2.loop;
		//:========================

		~d1.action = {

			if (~counter1 == 1) { ("==============MEROS_2").postln;

				Pdef(\meros_1o).stop;
				Pdef(\meros_2o).play;
				~aris.sendMsg("meros2o");

			};

			if (~counter1 == 33) {


				Pdef(\mozart).play;


			};


			~counter1 = ~counter1 + 1;

		};


		~d2.action = {};

		~d3.action = {};

		~d4.action = {};



	}


}









