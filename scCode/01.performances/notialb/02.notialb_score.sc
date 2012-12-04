/*
This class is notialb score
Aris Bezas Corfu -> Igoumeninja 121129

OSCFunc.trace(true); // Turn posting on
OSCFunc.trace(false); // Turn posting off

NetAddr("192.168.1.6", 5711 0).sendMsg('eisagogi'); // about 3min

//============
// Eisaqgogi |
//============
NetAddr("localhost", 57120).sendMsg('eisagogi'); // about 3min
NetAddr("localhost", 57120).sendMsg('eisagogiFeedBack'); // about 3min

46 meter

//=================
// afterEisaqgogi |
//=================
NetAddr("localhost", 57120).sendMsg('afterEisagogi');
meter duration: 2.6 sec
pappous sound at : 1,3
vaggelis at: 1,2,3,4,7,11(drone)

//==========
// meros1o |
//==========

Notialb.load;
Notialb.meros1o;
{
3000.do{|i|
OF.particle(
"add",
rrand(0,~width).asInteger,
rrand(0,~height).asInteger,
0.5, 0.5);
0.01.wait;
}
}.fork;

{Notialb.meros1o;0.01.wait;NetAddr("localhost", 57120).sendMsg('meros1o')}.fork

meter duration: 2.6 sec
pappous sound at : 1,3
vaggelis at: 1-11

//==========
// meros2o |
//==========
NetAddr("localhost", 57120).sendMsg('meros2o')
meter duration: 2.6 sec
pappous sound at : 1,3
vaggelis at: 1-11


OF.effect("mirror", 0);
OF.effect("tree");

*/

Notialb_Score {

	*initClass {
		StartUp add: {
			//this.sendTheTags;
			this.defineRespTags;
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
	*defineRespTags {
		~a1 = RespTags.do('/tags', 1, nil);
		~a2 = RespTags.do('/tags', 2, nil);
		~a3 = RespTags.do('/tags', 3, nil);
		~a4 = RespTags.do('/tags', 4, nil);

		~b1 =  RespTags.do('/tags1', 1, nil);   ~b2 = RespTags.do('/tags1', 2, nil);  ~b3 = RespTags.do('/tags1', 3, nil);
		~b4 =  RespTags.do('/tags1', 4, nil);   ~b5 = RespTags.do('/tags1', 5, nil);  ~b6 = RespTags.do('/tags1', 6, nil);
		~b7 =  RespTags.do('/tags1', 7, nil);   ~b8 = RespTags.do('/tags1', 8, nil);  ~b9 = RespTags.do('/tags1', 9, nil);
		~b10 = RespTags.do('/tags1', 10, nil); ~b11 = RespTags.do('/tags1', 11, nil);

		~c1 =  RespTags.do('/tags2', 1, nil);   ~c2 = RespTags.do('/tags2', 2, nil);  ~c3 = RespTags.do('/tags2', 3, nil);
		~c4 =  RespTags.do('/tags2', 4, nil);   ~c5 = RespTags.do('/tags2', 5, nil);  ~c6 = RespTags.do('/tags2', 6, nil);
		~c7 =  RespTags.do('/tags2', 7, nil);   ~c8 = RespTags.do('/tags2', 8, nil);  ~c9 = RespTags.do('/tags2', 9, nil);
		~c10 = RespTags.do('/tags2', 10, nil); ~c11 = RespTags.do('/tags2', 11, nil);

		~d1  = RespTags.do('/tags3', 1, nil);   ~d2 = RespTags.do('/tags3', 2, nil);  ~d3 = RespTags.do('/tags3', 3, nil);
		~d4  = RespTags.do('/tags3', 4, nil);   ~d5 = RespTags.do('/tags3', 5, nil);  ~d6 = RespTags.do('/tags3', 6, nil);
		~d7  = RespTags.do('/tags3', 7, nil);   ~d8 = RespTags.do('/tags3', 8, nil);  ~d9 = RespTags.do('/tags3', 9, nil);
		~d10 = RespTags.do('/tags3', 10, nil); ~d11 = RespTags.do('/tags3', 11, nil);
	}

	//========================================================================//
	//========================================================================//

	*eisagogi{
		~eisagogiResp.remove;
		~eisagogiResp = OSCresponderNode(
			nil,
			'eisagogi',
			{ |t,r,msg|
				OF.background(0,0,0,255);
				OF.background(0,0,0,255);
				OF.background(0,0,0,0);
				OF.particle("dotColor",255,255,255,55);		//	RGBA
				OF.particle("conColor",255,255,255,25);		//	RGBA
				OF.particle("activate", 1);
				//:Add particles
				{
					3000.do{|i|
						OF.particle(
							"add",
							rrand(0,~width).asInteger,
							rrand(0,~height).asInteger,
							0.5, 0.5);
						0.01.wait;
					}
				}.fork;

				~meterCount = 1;
				~a1.action = {
					"meterCount=".post; ~meterCount.postln;
					"a1".postln;
					~meterCount = ~meterCount + 1;
				};
				~a2.action = {
					"a2".postln;
					if (~meterCount == 28,{
						OF.particle("bounce", 100, 0, 300, ~height);
						OF.background(0,0,0,25);
					});
					if ((~meterCount > 29) && (~meterCount.even) && (~meterCount < 42) && (~meterCount != 35),{
						OF.particle("bounce", 0, ~height/2, ~width, (~height/2+200));
					});
					if ((~meterCount > 29) && (~meterCount.odd && (~meterCount < 42)),{
						OF.particle("bounce", 0, ~height/2, ~width, ~height+~meterCount*10);
					});

					if (~meterCount == 32,{
						OF.feedback("activate", 0);
						{
							OF.particle("bounce", ~screen1, 0, ~screen1+30, ~height);
							1.wait;
							OF.particle("bounce", ~screen2, 0, ~screen2+30, ~height);
							1.wait;
							OF.particle("bounce", ~screen3, 0, ~screen3+30, ~height);
							1.wait;
						}.fork;
					});
					if (~meterCount == 43,{
						OF.particle("bounce", ~screen1, 0, ~screen2,~height);
					});
					if (~meterCount == 44,{
						OF.particle("bounce", ~screen2, 0, ~screen3,~height);
					});
					if (~meterCount == 45,{
						OF.particle("bounce", ~screen3, 0, ~width,~height);
					});
				};
				~a3.action = {
					"a3".postln;
				};
				~a4.action = {
					"a4".postln;
					if (~meterCount == 20,
						{
							OF.feedback("activate", 1);
							OF.feedback("speedXY", 0,-0.5);
						}
					);
				};
		}).add;
	}

	//========================================================================//
	//========================================================================//

	*afterEisagogi {
		~afterEisagogiResp.remove;
		~afterEisagogiResp = OSCresponderNode(
			nil,
			'afterEisagogi',
			{ |t,r,msg|

				OF.particle("activate", 0);
				OF.effect("destruct","activate",0);
				OF.feedback("activate", 0);
				~meterCount = 0;

				//Actions
				~b1.action = {
					//if(~meterCount > 25, {~meterCount=0;});
					~meterCount = ~meterCount + 1; "\n~meterCount=".post; ~meterCount.postln;
					"b1".postln;
					{
						if (~meterCount < 15, {
							OF.rect(~screen31,0,~screenWidth,~height, 255,255,255,255);
						});

						2.do{
							OF.rect(0,1000.rand,~width,2,255,255,255,255);
						};
						(~meterCount*1).do{|i|
							OF.writeString(
								"110",
								"|",
								(~screen1+~screenWidth/2-55),250+(30*i),
								255,255,255,255
							);
							0.05.wait;
							//0.08.wait;
						};
					}.fork
				};
				~b2.action = {
					"b2".postln;
					{
						if (~meterCount < 15, {OF.rect(~screen2,0,~screenWidth,~height, 255,255,255,255);});
						(~meterCount*1).do{|i|
							OF.writeString(
								"110",
								"/",
								(~screen2+(~screenWidth/2)-55),250+(30*i),
								255,255,255,255
							);
							0.05.wait;
						}
					}.fork
				};
				~b3.action = {
					"b3".postln;
					{
						if (~meterCount < 15, {OF.rect(~screen3,0,~screenWidth,~height, 255,255,255,255);});
						2.do{
							OF.rect(1000.rand,0,2,~height,255,255,255,255);
						};
						(~meterCount*1).do{|i|
							OF.writeString(
								"bigCenter",
								"-",
								(~screen3+(~screenWidth/2)-55),250+(30*i),
								255,255,255,255
							);
							0.05.wait;
						};
					}.fork
				};
				~b4.action = {
					"b4".postln;
					if (~meterCount < 15, {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
					OF.background(0,0,0,255);
					OF.background(0,0,0,20);
				};
				~b5.action = {
					"b5".postln;
				};
				~b6.action = {
					"b6".postln;
				};
				~b7.action = {
					"b7".postln;
					2.do{
						OF.rect(0,1000.rand,~width,2,255,255,255,255);
					};
					if(~meterCount%3==0,
						{
							OF.writeString(
								"110",
								"this.asValue.postln;",
								20,~height,
								255,255,255,255
							)
						}
					)
				};
				~b8.action = {
					"b8".postln;
				};
				~b9.action = {
					"b9".postln;
				};
				~b10.action = {"b10".postln;};
				~b11.action = {
					"b11".postln;
					if (~meterCount % 5 == 0,
						{
							{
								(~width/20).do{
									|i|
									OF.writeString(
										"110",
										"notialb".choose.asString,
										0,500,
										255,255,255,255
									);
									0.05.wait;
								};
							}.fork
						}
					);
					if (~meterCount % 6 == 0,
						{
							{
								(~width/20).do{
									|i|
									OF.writeString(
										"110",
										"notialb".choose.asString,
										~screen2,500,
										255,255,255,255
									);
									0.05.wait;
								};
							}.fork
						}
					);
					if (~meterCount % 7 == 0,
						{
							{
								(~width/20).do{
									|i|
									OF.writeString(
										"110",
										"notialb".choose.asString,
										~screen3,500,
										255,255,255,255
									);
									0.05.wait;
								};
							}.fork
						}
					);

					if (~meterCount > 18,
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",1);
						}
					);
					if ((~meterCount % 3 == 0) && (~meterCount > 24),
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",2);
						}
					);
					OF.background(0,0,0,255);
					OF.background(0,0,0,25);
				};

		}).add;

	}
	//========================================================================//
	//========================================================================//
	//   Notialb.load;
	//                Notialb.meros1o;

	*meros1o {
		~meros1oResp.remove;
		~meros1oResp = OSCresponderNode(
			nil,
			'meros1o',
			{ |t,r,msg|
				OF.particle("activate", 0);
				OF.effect("destruct","activate",0);
				OF.feedback("activate", 0);
				~meterCount = 0;
				//~mySendAmpFreq.start;
				//OF.interactWithSound("glBeginType",0);
				OF.interactWithSound("maxFreqIn", 600);
				OF.interactWithSound("maxAmpIn", 0.7);
				OF.interactWithSound("maxSoundElasticity",0.7);
				OF.interactWithSound("numSoundSketches", 300);
				OF.background(0,0,0,20);
				//OF.background(255,255,255,15);

				OF.particle("activate", 1);
				//OF.particle("bounce", 0, (~height/2 -10), ~width, (~height/2 + 100));
				OF.particle("bounce", 0, 0, ~width, ~height);
				OF.particle("pusher","set", 0, ~width/2, ~height/2);
				OF.particle("pusher","set", 1, 0, 0);
				OF.particle("pusher","set", 2, ~width, 0);
				OF.particle("pusher","set", 3, ~width, ~height);
				OF.particle("pusher","set", 4, 0, ~height);
				//OF.particle("pusher","remove", 0)
				OF.particle("forceRadius", 400);
				OF.particle("particleNeighborhood", 25);
				OF.particle("dotColor",255,255,255,155);
				OF.particle("conColor",255,255,255,10);

				~c1.action = { "c1".postln; "\n~meterCount=".post; ~meterCount.postln;
					OF.interactWithSound("activate",0);
					if (~meterCount < 25, {{5.do{OF.rect(~screen3+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if (~meterCount == 3, {
						OF.particle("bounce", 0, (~height/2 -10), ~width, (~height/2 + 100));
						OF.particle("pusher","remove", 0);
						OF.particle("pusher","remove", 1);
						OF.particle("pusher","remove", 2);
						OF.particle("pusher","remove", 3);
						OF.particle("pusher","remove", 4);
						OF.particle("particleNeighborhood", 10);});
					if (~meterCount == 14,{
						OF.particle("particleNeighborhood", 20);
						OF.particle("forceRadius", 500);
						OF.particle("dotColor",255,255,255,55);
						OF.particle("conColor",255,255,255,10);
					});
					if (~meterCount == 16,{
						OF.particle("forceRadius", 100);
						OF.particle("dotColor",255,255,255,55);
						OF.particle("conColor",255,255,255,10);
					});
					if (~meterCount == 17,{
						OF.particle("particleNeighborhood", 15);
					});
					if (~meterCount == 18,{
						OF.particle("particleNeighborhood", 5);
					});

					if ((~meterCount > 4) && (~meterCount < 23), {OF.particle("bounce", ~screen1, 0, ~screen2,~height);});
					if (~meterCount > 25, {
						OF.particle("bounce", 0, ((~height/2)-100), ~width,((~height/2)+100));
						OF.particle("particleNeighborhood", 15);
						OF.particle("pusher","set", 0, ~screen1+(~screenWidth/2), ~height/2);
						OF.particle("pusher","set", 1, ~screen2+(~screenWidth/2), ~height/2);
						OF.particle("pusher","set", 2, ~screen3+(~screenWidth/2), ~height/2);
					});
					~meterCount = ~meterCount + 1;

				};
				~c2.action = {"c2".postln;
					if (~meterCount < 25, {{5.do{OF.rect(~screen2+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
				};
				~c3.action  = {"c3".postln;
					if (~meterCount < 25, {{5.do{OF.rect(~screen3+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if ((~meterCount > 4) && (~meterCount < 25), {OF.particle("bounce", ~screen3, 0, ~width,~height);});
				};
				~c4.action  = {"c4".postln;
					if (~meterCount < 25, {{5.do{OF.rect(~screen2+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if (~meterCount > 4, {OF.particle("bounce", ~screen2, 0, ~screen3,~height);});
				};
				~c5.action  = {"c5".postln;
					if (~meterCount < 25, {{5.do{OF.rect(~screen1+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if ((~meterCount > 4) && (~meterCount < 14), {OF.particle("bounce", ~screen1, 0, ~screen2,~height);});
					if ((~meterCount > 11) && (~meterCount < 20), {OF.particle("bounce", ~screen3, 0, ~width,~height);});
					if (~meterCount > 17, {OF.particle("bounce", ~screen2, 0, ~screen3,~height);});
				};
				~c6.action  = {"c6".postln;
					OF.interactWithSound("activate",0);
					if (~meterCount < 15, {OF.rect(~screen3,0,~screenWidth,~height, 255,255,255,255);});
				};
				~c7.action  = {"c7".postln;
					if (~meterCount < 15, {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
				};
				~c8.action  = {"c8".postln;
					if (~meterCount < 15, {OF.rect(~screen2,0,~screenWidth,~height, 255,255,255,255);});
				};
				~c9.action  = {"c9".postln;
					if (~meterCount < 15, {OF.rect(~screen3,0,~screenWidth,~height, 255,255,255,255);});
				};
				~c10.action  = {"c10".postln;
					if (~meterCount < 15, {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
				};
				~c11.action  = {"c11".postln;
					if (~meterCount < 15, {OF.rect(~screen2,0,~screenWidth,~height, 255,255,255,255);});
				};
		}).add;


	}

	//========================================================================//
	//========================================================================//

	*meros2o {
		~meros2oResp.remove;
		~meros2oResp = OSCresponderNode(
			nil,
			'meros2o',
			{ |t,r,msg|
				//Actions
				~d1.action = {
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
				~d2.action = {
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
				~d3.action = {
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
				~d4.action = {
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
