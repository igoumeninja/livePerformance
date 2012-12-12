/*
This class is notialb score
Aris Bezas Corfu -> Igoumeninja 121129

OSCFunc.trace(true); // Turn posting on
OSCFunc.trace(false); // Turn posting off

//============
// Eisaqgogi |
//============
Notialb_Score.sendTheTags
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
		~aResp1 = RespTags.do('/tags', 1, nil);
		~aResp2 = RespTags.do('/tags', 2, nil);
		~aResp3 = RespTags.do('/tags', 3, nil);
		~aResp4 = RespTags.do('/tags', 4, nil);

		~bResp1 =  RespTags.do('/tags1', 1, nil);   ~bResp2 = RespTags.do('/tags1', 2, nil);  ~bResp3 = RespTags.do('/tags1', 3, nil);
		~bResp4 =  RespTags.do('/tags1', 4, nil);   ~bResp5 = RespTags.do('/tags1', 5, nil);  ~bResp6 = RespTags.do('/tags1', 6, nil);
		~bResp7 =  RespTags.do('/tags1', 7, nil);   ~bResp8 = RespTags.do('/tags1', 8, nil);  ~bResp9 = RespTags.do('/tags1', 9, nil);
		~bResp10 = RespTags.do('/tags1', 10, nil); ~bResp11 = RespTags.do('/tags1', 11, nil);

		~cResp1 =  RespTags.do('/tags2', 1, nil);   ~cResp2 = RespTags.do('/tags2', 2, nil);  ~cResp3 = RespTags.do('/tags2', 3, nil);
		~cResp4 =  RespTags.do('/tags2', 4, nil);   ~cResp5 = RespTags.do('/tags2', 5, nil);  ~cResp6 = RespTags.do('/tags2', 6, nil);
		~cResp7 =  RespTags.do('/tags2', 7, nil);   ~cResp8 = RespTags.do('/tags2', 8, nil);  ~cResp9 = RespTags.do('/tags2', 9, nil);
		~cResp10 = RespTags.do('/tags2', 10, nil); ~cResp11 = RespTags.do('/tags2', 11, nil);

		~dResp1  = RespTags.do('/tags3', 1, nil);   ~dResp2 = RespTags.do('/tags3', 2, nil);  ~dResp3 = RespTags.do('/tags3', 3, nil);
		~dResp4  = RespTags.do('/tags3', 4, nil);   ~dResp5 = RespTags.do('/tags3', 5, nil);  ~dResp6 = RespTags.do('/tags3', 6, nil);
		~dResp7  = RespTags.do('/tags3', 7, nil);   ~dResp8 = RespTags.do('/tags3', 8, nil);  ~dResp9 = RespTags.do('/tags3', 9, nil);
		~dResp10 = RespTags.do('/tags3', 10, nil); ~dResp11 = RespTags.do('/tags3', 11, nil);
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
				OF.sinEq("remove",0);
				//:Add particles
				{
					3000.do{|i|
						OF.particle(
							"add",
							rrand(0,~width).asInteger,
							rrand(0,~height).asInteger,
							0.5, 0.5);
						0.09.wait;
					}
				}.fork;

				~meterCount = 1;
				~aResp1.action = {
					"meterCount=".post; ~meterCount.postln;
					"a1".postln;
					~meterCount = ~meterCount + 1;
				};
				~aResp2.action = {
					"a2".postln;
					if (~meterCount == 28,{
						OF.particle("bounce", 100, 0, 300, ~height);
						OF.background(0,0,0,25);
					});
					if ((~meterCount > 29) && (~meterCount.even) && (~meterCount < 42) && (~meterCount != 35),{
						OF.particle("bounce", 0, ~height/2, ~width, (~height/2+200));
					});
					if ((~meterCount > 29) && (~meterCount.odd && (~meterCount < 42)) && (~meterCount != 35),{
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
				~aResp3.action = {
					"a3".postln;
				};
				~aResp4.action = {
					"a4".postln;
					if (~meterCount == 20,
						{
							OF.feedback("activate", 1);
							OF.feedback("speedXY", 0,-0.5);
							OF.particle("dotColor",255,255,255,65);		//	RGBA
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
				OF.sinEq("remove",0);
				~meterCount = 0;

				//Actions
				~bResp1.action = {
					//if(~meterCount > 25, {~meterCount=0;});
					~meterCount = ~meterCount + 1; "\n~meterCount=".post; ~meterCount.postln;
					"b1".postln;
					{
						if (~meterCount > 9, {
							{
								OF.effect("noiseEffect", 1);
								0.04.wait;
								OF.effect("noiseEffect", 0);
							}.fork;
						});

						if (~meterCount > 40, {
							OF.effect("mirror", 1);
						});
						if (~meterCount > 63, {
							OF.effect("mirror", 0);
						});
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
				~bResp2.action = {
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
				~bResp3.action = {
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
				~bResp4.action = {
					"b4".postln;
					if (~meterCount < 15, {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
					OF.background(0,0,0,255);
					OF.background(0,0,0,20);
				};
				~bResp5.action = {
					"b5".postln;
				};
				~bResp6.action = {
					"b6".postln;
				};
				~bResp7.action = {
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
				~bResp8.action = {
					"b8".postln;
				};
				~bResp9.action = {
					"b9".postln;
				};
				~bResp10.action = {"b10".postln;};
				~bResp11.action = {
					"b11".postln;
					if ((~meterCount > 15) && (~meterCount < 64),{
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
					});

					if ((~meterCount > 34) && (~meterCount < 62),
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",1);
						}
					);
					if ((~meterCount % 3 == 0) && (~meterCount > 24) && (~meterCount < 62),
						{
							OF.effect("destruct","activate",1);
							OF.effect("destruct","case",2);
						}
					);
					if ((~meterCount > 64),
						{
							OF.effect("destruct","activate",0);
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
				OF.effect("mirror", 0);
				OF.particle("activate", 0);
				OF.effect("destruct","activate",0);
				OF.feedback("activate", 0);
				OF.interactWithSound("activate",0);
				25.do{|i|OF.sinEq("remove",24-i)};
				~meterCount = 0;

				OF.interactWithSound("glBeginType",1);
				//OF.interactWithSound("maxFreqIn", 600);
				//OF.interactWithSound("maxAmpIn", 0.7);
				OF.interactWithSound("maxSoundElasticity",0.7);
				OF.interactWithSound("numSoundSketches", 300);
				OF.background(0,0,0,20);
				//OF.background(255,255,255,15);
				{
					OF.particle("activate", 1);
					2.wait;
					OF.particle("activate", 0);
				}.fork;
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
				~cResp1.action = { "\n~meterCount=".post; ~meterCount.postln;"c1".postln;
					if ((~meterCount > 92) && (~meterCount < 117), {
						{
							{
								OF.effect("noiseEffect", 1);
								0.04.wait;
								OF.effect("noiseEffect", 0);
							}.fork;

						}.fork;
					});
					if ((~meterCount > 47) && (~meterCount < 58) , {
						{
							OF.rect([~screen1,~screen2,~screen3].choose,0,~screenWidth,~height, 255,255,255,255);
							0.1.wait;
							OF.rect([~screen1,~screen2,~screen3].choose,0,~screenWidth,~height, 255,255,255,255);
							0.1.wait;
							OF.rect([~screen1,~screen2,~screen3].choose,0,~screenWidth,~height, 255,255,255,255);
							0.1.wait;
							OF.rect([~screen1,~screen2,~screen3].choose,0,~screenWidth,~height, 255,255,255,255);
							0.1.wait;
						}.fork;
						}
					);
					if ((~meterCount > 58) && (~meterCount < 65) , {
						{
							OF.writeString("bigCenter","-",~screen1+(~screenWidth/2)-100,~height.rand,255,255,255,255);
							((1/3)+(1/2)).wait;
							OF.writeString("bigCenter","-",~screen2+(~screenWidth/2)-100,~height.rand,255,255,255,255);
							((2/3)+(1/6)).wait;
							OF.writeString("bigCenter","-",~screen3+(~screenWidth/2)-100,~height.rand,255,255,255,255);
							(2/6).wait;
							OF.writeString("bigCenter","-",~screen2+(~screenWidth/2)-100,~height.rand,255,255,255,255);
							(3/6).wait;
							OF.writeString("bigCenter","-",~screen1+(~screenWidth/2)-100,~height.rand,255,255,255,255);
							(1/6).wait;

						}.fork;
					});
					if ((~meterCount > 26) && (~meterCount < 122) , {
						OF.particle("bounce", ~screen2, rrand(0,~height/2), ~screen3, rrand(~height/2,~height));
					});
					if ((~meterCount < 25) && (~meterCount > 1),{
							{5.do{OF.rect(~screen3+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;
					});
					if (~meterCount == 0, {
						OF.particle("activate", 0);
					});
					if (~meterCount == 1, {
						OF.particle("activate", 1);
						OF.particle("bounce", 0, (~height/2 -10), ~width, (~height/2 + 100));
						OF.particle("pusher","remove", 0);
						OF.particle("pusher","remove", 1);
						OF.particle("pusher","remove", 2);
						OF.particle("pusher","remove", 3);
						OF.particle("pusher","remove", 4);
						OF.particle("particleNeighborhood", 10);
					});
					if (~meterCount == 14,{
						OF.particle("particleNeighborhood", 15);
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
						OF.particle("particleNeighborhood", 15);
					});
					if ((~meterCount > 1) && (~meterCount < 21), {
						OF.particle("bounce", ~screen1, 0, ~screen2,~height);
					});
					if ((~meterCount > 25) && (~meterCount < 29) , {
						OF.particle("forceRadius", 500);
						OF.particle("bounce", 0, ((~height/2)-100), ~width,((~height/2)+100));
						OF.particle("particleNeighborhood", 15);
						OF.particle("pusher","remove", 0);
						OF.particle("pusher","remove", 1);
						OF.particle("pusher","remove", 2);
					});
					if ((~meterCount > 29) && (~meterCount < 32) , {
						OF.particle("forceRadius", 500);
						OF.particle("particleNeighborhood", 15);
					});
					if ((~meterCount == 66), {
						~mySendAmpFreq.start;
					});
					if (
						((~meterCount > 84) && (~meterCount < 88)) ||
						((~meterCount > 91) && (~meterCount < 96)),
						{
						{
							OF.background(0,0,0,255);
							OF.background(0,0,0,0);
							OF.rgb("sound",255,255,255,200);
							0.04.wait;
							OF.rgb("sound",0,0,0,20);
							0.04.wait;
							OF.rgb("sound",255,255,255,200);
							0.04.wait;
							OF.rgb("sound",0,0,0,20);
							0.04.wait;
							OF.rgb("sound",255,255,255,20);
							OF.background(0,0,0,25);
						}.fork;
					});
					if (
						((~meterCount > 100) && (~meterCount < 115)),
						{
							{
								OF.background(0,0,0,255);
								OF.background(0,0,0,0);
								OF.rgb("sound",255,255,255,200);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
								0.04.wait;
								OF.rgb("sound",255,255,255,200);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
								0.04.wait;
								OF.rgb("sound",255,255,255,20);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
							}.fork;
					});
					if (
						((~meterCount > 114) && (~meterCount < 123)),
						{
							{
								OF.rgb("sound",255,255,255,200);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
								0.04.wait;
								OF.rgb("sound",255,255,255,200);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
								0.04.wait;
								OF.rgb("sound",255,255,255,20);
								0.04.wait;
								OF.rgb("sound",0,0,0,20);
							}.fork;
					});
					if ((~meterCount == 56), {20.do{|i|OF.sinEq("remove",19-i);}});
					if ((~meterCount == 66), {
						OF.particle("activate", 0);
						OF.interactWithSound("activate",1);
						OF.interactWithSound("numSoundSketches", 180);
						OF.interactWithSound("maxSoundElasticity",5.1);
					});
					~meterCount = ~meterCount + 1;

				};
				~cResp2.action = {"c2".postln;
					if ((~meterCount > 2) && (~meterCount < 25),
						{{5.do{OF.rect(~screen2+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
				};
				~cResp3.action  = {"c3".postln;
					if ((~meterCount > 2) && (~meterCount < 25),
						{{5.do{OF.rect(~screen3+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if ((~meterCount > 2) && (~meterCount < 21), {OF.particle("bounce", ~screen3, 0, ~width,~height);});
				};
				~cResp4.action  = {"c4".postln;
					if ((~meterCount > 2) && (~meterCount < 25),
						{{5.do{OF.rect(~screen2+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if ((~meterCount > 1) && (~meterCount != 21) && (~meterCount != 22), {
						OF.particle("bounce", ~screen2, 0, ~screen3,~height);
					});
				};
				~cResp5.action  = {"c5".postln;
					if ((~meterCount > 2) && (~meterCount < 25),
						{{5.do{OF.rect(~screen1+~screenWidth.rand-30,0,1,~height,255,255,255,255);}}.fork;});
					if ((~meterCount > 1) && (~meterCount < 14), {OF.particle("bounce", ~screen1, 0, ~screen2,~height);});
					if ((~meterCount > 11) && (~meterCount < 20), {OF.particle("bounce", ~screen3, 0, ~width,~height);});
					if ((~meterCount > 17) && (~meterCount < 29) , {OF.particle("bounce", ~screen2, 0, ~screen3,~height);});
					if (~meterCount > 29, {/*OF.particle("bounce", 0,0, ~screen2, ~height/2);*/});
					};
				~cResp6.action  = {"c6".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen3,0,~screenWidth,~height, 255,255,255,255);});
					};
				~cResp7.action  = {"c7".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
					if ((~meterCount > 4) && (~meterCount < 21), {
						{
							OF.effect("mirror", 1);
							~mySendAmpFreq.start;
							OF.interactWithSound("activate",1);
							OF.effect("mirror","case", 5);
							0.2.wait;
							OF.effect("mirror","case", 3);
							0.2.wait;
							OF.effect("mirror","case", 2);
							0.2.wait;
							OF.effect("mirror", 0);
							OF.interactWithSound("activate",0);
							OF.sinEq("remove",1);OF.sinEq("remove",0);
							~mySendAmpFreq.stop;
						}.fork;
					});
					if ((~meterCount == 25), {
						~mySendAmpFreq.start;
					});
					if ((~meterCount > 26) && (~meterCount < 27), {
						{
							OF.sinEq(0,~screen2+(~screenWidth/2)-5,10);OF.sinEq(1,~screen2+(~screenWidth/2)+5,10);
							OF.interactWithSound("activate",1);
						}.fork;
					});
					if ((~meterCount == 27), {
						{
							OF.sinEq("remove",1);OF.sinEq("remove",0);
						}.fork;
					});
				};
				~cResp8.action  = {"c8".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen2,0,~screenWidth,~height, 255,255,255,255);});
				};
				~cResp9.action  = {"c9".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen3,0,~screenWidth,~height, 255,255,255,255);});
				};
				~cResp10.action  = {"c10".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen1,0,~screenWidth,~height, 255,255,255,255);});
				};
				~cResp11.action  = {"c11".postln;
					if ((~meterCount > 0) && (~meterCount < 15), {OF.rect(~screen2,0,~screenWidth,~height, 255,255,255,255);});
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
				OF.effect("mirror", 0);
				OF.particle("activate", 0);
				OF.effect("destruct","activate",0);
				OF.feedback("activate", 1);
				OF.background(0,0,0,0);
				OF.feedback("speedXY", 0,0.5);
				OF.rgb("sound",255,255,255,15);
				OF.interactWithSound("maxFreqIn", 1000);
				OF.interactWithSound("glBeginType",1);

				OF.sketch3d("glBeginType",1);
				OF.sketch3d("numSketch3d",250);
				OF.sketch3d("rotYratio",0.3);
				OF.sketch3d("rotZratio",0.1);
				OF.sketch3d("maxSketch3dElasticity",0.9);
				OF.rgb("sketch3d",255,255,255,20);
				OF.sketch3d("activate", 0);
				~meterCount = 0;
				//Actions
				~dResp1.action = { "\n~meterCount=".post; ~meterCount.postln;"d1".postln;
					if (~meterCount == 3, {OF.background(0,0,0,5);});
					if (~meterCount == 30, {~meterCount = ~meterCount+1});

					if ((~meterCount > 8) && (~meterCount%2 == 0) && (~meterCount < 61),
						{
							OF.sketch3d("numSketch3d",250);
							OF.sketch3d("activate", 1);
						},
						{
							OF.sketch3d("activate", 0);
							OF.sketch3d("numSketch3d",50);
					});
					~meterCount = ~meterCount+1;
				};
				~dResp2.action = {"d2".postln;
				};
				~dResp3.action = {"d3".postln;
				};
				~dResp4.action = {"d4".postln;
					if (~meterCount == 63,
						{
							OF.feedback("activate", 0);
							OF.sketch3d("activate", 0);
							OF.background(0,0,0,15);
							OF.interactWithSound("activate",0);
							{
								9.wait;
								OF.background(0,0,0,0);
								OF.writeString("110","notialb",
									~screen3,~height,
									255,255,255,255);
								OF.feedback("activate", 1);
								OF.feedback("speedXY", -0.5,0);
							}.fork;
						}
					);
				};
		}).add;
	}
}
