/*
This class is notialb score
Aris Bezas Corfu -> Igoumeninja 121129

OSCFunc.trace(true); // Turn posting on
OSCFunc.trace(false); // Turn posting off

NetAddr("localhost", 57120).sendMsg('eisagogi');

NetAddr("localhost", 57120).sendMsg('afterEisagogi')
NetAddr("localhost", 57120).sendMsg('meros1o')

OF.effect("mirror", 0);
OF.effect("tree");

*/

Notialb_Score {

	*initClass {
		StartUp add: {
			//this.sendTheTags;
			this.eisagogi;
			this.afterEisagogi;
			this.meros1o;
			this.meros2o;
			//NetAddr("localhost", 57120).sendMsg('eisagogi');
			//NetAddr("localhost", 57120).sendMsg('afterEisagogi');
			"\n* Notialb_Score_is_waiting\n".postln;
		}
	}
	*sendTheTags {
		~ena = SendTags.new;
		~ena.dests = [Dests.mineAddr];
		~ena.title = '/tags';
		~ena.tags = (1..11);
		~ena.step = [1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6]/1;
		~ena.loop;
	}
	*eisagogi{
		~eisagogiResp.remove;
		~eisagogiResp = OSCresponderNode(
			nil,
			'eisagogi',
			{ |t,r,msg|

				//RespTags
				~a1 = RespTags.do('/tags', 1, nil);
				~a2 = RespTags.do('/tags', 2, nil);
				~a3 = RespTags.do('/tags', 3, nil);
				~a4 = RespTags.do('/tags', 4, nil);
				~a5 = RespTags.do('/tags', 5, nil);
				~a6 = RespTags.do('/tags', 6, nil);
				~a7 = RespTags.do('/tags', 7, nil);
				~a8 = RespTags.do('/tags', 8, nil);
				~a9 = RespTags.do('/tags', 9, nil);
				~a10 = RespTags.do('/tags', 10, nil);
				~a11 = RespTags.do('/tags', 11, nil);
				~a1.removeResp;
				~a2.removeResp;
				~a3.removeResp;
				~a4.removeResp;
				~a5.removeResp;
				~a6.removeResp;
				~a7.removeResp;
				~a8.removeResp;
				~a9.removeResp;
				~a10.removeResp;
				~a11.removeResp;

				OF.background(0,0,0,255);
				OF.background(0,0,0,0);
				OF.particle("dotColor",255,255,255,55);		//	RGBA
				OF.particle("conColor",255,255,255,25);		//	RGBA
				OF.particle("activate", 1);
				//:Add particles
				{
					2000.do{|i|
						OF.particle("add", rrand(0,~width).asInteger,rrand(0,~height).asInteger,0.5, 0.5);
						i.postln;
						0.05.wait;
					}
				}.fork
		}).add;
	}
	*afterEisagogi {
		~afterEisagogiResp.remove;
		~afterEisagogiResp = OSCresponderNode(
			nil,
			'afterEisagogi',
			{ |t,r,msg|

				OF.particle("activate", 0);
				//RespTags
				~a1 = RespTags.do('/tags', 1, nil);
				~a2 = RespTags.do('/tags', 2, nil);
				~a3 = RespTags.do('/tags', 3, nil);
				~a4 = RespTags.do('/tags', 4, nil);
				~a5 = RespTags.do('/tags', 5, nil);
				~a6 = RespTags.do('/tags', 6, nil);
				~a7 = RespTags.do('/tags', 7, nil);
				~a8 = RespTags.do('/tags', 8, nil);
				~a9 = RespTags.do('/tags', 9, nil);
				~a10 = RespTags.do('/tags', 10, nil);
				~a11 = RespTags.do('/tags', 11, nil);
				~a1.removeResp;
				~a2.removeResp;
				~a3.removeResp;
				~a4.removeResp;
				~a5.removeResp;
				~a6.removeResp;
				~a7.removeResp;
				~a8.removeResp;
				~a9.removeResp;
				~a10.removeResp;
				~a11.removeResp;
				~a1 = RespTags.do('/tags', 1, nil);
				~a2 = RespTags.do('/tags', 2, nil);
				~a3 = RespTags.do('/tags', 3, nil);
				~a4 = RespTags.do('/tags', 4, nil);
				~a5 = RespTags.do('/tags', 5, nil);
				~a6 = RespTags.do('/tags', 6, nil);
				~a7 = RespTags.do('/tags', 7, nil);
				~a8 = RespTags.do('/tags', 8, nil);
				~a9 = RespTags.do('/tags', 9, nil);
				~a10 = RespTags.do('/tags', 10, nil);
				~a11 = RespTags.do('/tags', 11, nil);


				~meterCount = 0;

				//Actions
				~a1.action = {
					if(~meterCount > 25, {~meterCount=0;});
					~meterCount = ~meterCount + 1; "\n~meterCount=".post; ~meterCount.postln;
					"a1".postln;
					{
						(~meterCount*1).do{|i|
							OF.writeString(
								"110",
								"|",
								100,250+(30*i),
									255,255,255,255
							);
							(~ena.step[0]/10).wait;
							//0.08.wait;
						}
					}.fork
				};
				~a2.action = {
					"a2".postln;
					{
						(~meterCount*1).do{|i|
							OF.writeString(
								"110",
								"/",
								400,250+(30*i),
								255,255,255,255
							);
							(~ena.step[1]/10).wait;
						}
					}.fork
				};
				~a3.action = {
					"a3".postln;
					{
						(~meterCount*1).do{|i|
							OF.writeString(
								"bigCenter",
								"-",
								700,250+(30*i),
								255,255,255,255
							);
							(~ena.step[2]/10).wait;
						}
					}.fork

				};
				~a4.action = {
					"a4".postln;
					OF.background(0,0,0,255);
					OF.background(0,0,0,20);
					{
						(~meterCount*1).do{|i|
							OF.writeString(
								"bigCenter",
								"_",
								1000,250+(30*i),
								255,255,255,255
							);
							(~ena.step[2]/10).wait;
						}
					}.fork



				};
				~a5.action = {
					"a5".postln;				{
						2.do{
							OF.rect(1000.rand,0,2,~height,255,255,255,255);
						}
					}.fork;

				};
				~a6.action = {
					"a6".postln;
					if(~meterCount > 1,
						{
							if(~meterCount%3==0,
								{
									OF.writeString(
										"110",
										"this.asValue;",
										20,~height,
										255,255,255,255
									)
								}
							)
						}
					)
				};
				~a7.action = {
					"a7".postln;
					if (~meterCount == 20,
						{
							{
								(~width/20).do{
									|i|
									OF.writeString(
										"110",
										"notialb".choose.asString,
										0+(i*20),500,
										255,255,255,255
									);
									(~ena.step[2]/5).wait;
								};
							}.fork
						}
					)
				};
				~a8.action = {
					"a8".postln;
					if (~meterCount % 10 == 0,
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",2);
						},
						{
							OF.effect("destruct","activate",0);
						}

					);

				};
				~a9.action = {
					"a9".postln;
					if ((~meterCount % 4 == 0) && (~meterCount > 18),
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",1);
						},
						{
							OF.effect("destruct","activate",0);
						}

					);

				};
				~a10.action = {"a10".postln;};
				~a11.action = {
					"a11".postln;
					{
						2.do{
							OF.rect(0,1000.rand,~width,2,255,255,255,255);
						}
					}.fork;
					OF.background(0,0,0,255);
					OF.background(0,0,0,25);
				};

		}).add;

	}

	*meros1o {
		~meros1oResp.remove;
		~meros1oResp = OSCresponderNode(
			nil,
			'meros1o',
			{ |t,r,msg|
				//Actions
				~a1.removeResp;
				~a2.removeResp;
				~a3.removeResp;
				~a4.removeResp;
				~a5.removeResp;
				~a6.removeResp;
				~a7.removeResp;
				~a8.removeResp;
				~a9.removeResp;
				~a10.removeResp;
				~a11.removeResp;

				~a1 = RespTags.do('/tags', 1, nil);
				~a2 = RespTags.do('/tags', 2, nil);
				~a3 = RespTags.do('/tags', 3, nil);
				~a4 = RespTags.do('/tags', 4, nil);
				~a5 = RespTags.do('/tags', 5, nil);
				~a6 = RespTags.do('/tags', 6, nil);
				~a7 = RespTags.do('/tags', 7, nil);
				~a8 = RespTags.do('/tags', 8, nil);
				~a9 = RespTags.do('/tags', 9, nil);
				~a10 = RespTags.do('/tags', 10, nil);
				~a11 = RespTags.do('/tags', 11, nil);

				~a1.action = {
					{
						OF.feedback("activate", 1);
						OF.feedback("speedXY", 0,0);

						OF.writeString(
							"bigCenter",
							"nla",
							100,300,
							255,255,255,255
						);
						0.1.wait;
						OF.feedback("speedXY", 0,0.5);
						OF.writeString(
							"bigCenter",
							"nla",
							100, 400,
							255,255,255,255
						);

					}.fork
				};
				~a2.action = {
					{
						OF.writeString(
							"bigCenter",
							"sdjfh",
							200,100,
							255,255,255,255
						);
						0.1.wait;
						OF.writeString(
							"bigCenter",
							"zsd",
							200, 200,
							255,255,255,255
						);

					}.fork

				};
				~a3.action = {};
				~a4.action = {};
				~a5.action = {};
				~a6.action = {};
				~a7.action = {};
				~a8.action = {};
				~a9.action = {};
				~a10.action = {};
				~a11.action = {};

		}).add;


	}

	*meros2o {
		~meros2oResp.remove;
		~meros2oResp = OSCresponderNode(
			nil,
			'meros2o',
			{ |t,r,msg|
				//RespTags
				~a1.removeResp;
				~a2.removeResp;
				~a3.removeResp;
				~a4.removeResp;
				~a5.removeResp;
				~a6.removeResp;
				~a7.removeResp;
				~a8.removeResp;
				~a9.removeResp;
				~a10.removeResp;
				~a11.removeResp;


				~a1 = RespTags.do('/tags', 1, nil);
				~a2 = RespTags.do('/tags', 2, nil);
				~a3 = RespTags.do('/tags', 3, nil);
				~a4 = RespTags.do('/tags', 4, nil);
				//Actions
				~a1.action = {
					{
						OF.feedback("activate", 1);
						OF.feedback("speedXY", 0,0);

						OF.writeString(
							"bigCenter",
							"TSOUK",
							100,300,
							255,255,255,255
						);
						0.1.wait;
						OF.feedback("speedXY", 0,0.5);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							100, 400,
							255,255,255,255
						);

					}.fork
				};
				~a2.action = {
					{
						OF.feedback("speedXY", 0.5,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200,100,
							255,255,255,255
						);
						0.1.wait;
						OF.feedback("speedXY", 0,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200, 200,
							255,255,255,255
						);

					}.fork

				};
				~a3.action = {
					{
						OF.feedback("speedXY", 1.5,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200,100,
							255,255,255,255
						);
						0.1.wait;
						OF.feedback("speedXY", 0,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200, 200,
							255,255,255,255
						);

					}.fork

				};
				~a4.action = {
					{
						OF.feedback("speedXY", -1.5,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200,100,
							255,255,255,255
						);
						0.1.wait;
						OF.feedback("speedXY", 0,0);
						OF.writeString(
							"bigCenter",
							"TSOUK",
							200, 200,
							255,255,255,255
						);

					}.fork

				};

		}).add;


	}
}
	