//============================
//   Eisagogi (Impovization)
//============================


//=============================
//  After Eisagogi (keimeno)
//=============================
(
~afterEisagogiResp.remove;
~afterEisagogiResp = OSCresponderNode(
	nil,
	'afterEisagogi',
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
		//Actions
		~a1.action = {
			{
				OF.feedback("activate", 1);
				OF.feedback("speedXY", 0,0);

				OF.writeString(
					"bigCenter",
					"~",
					100,300,
					255,255,255,255
				);
				0.1.wait;
				OF.feedback("speedXY", 0,0.5);
				OF.writeString(
					"bigCenter",
					"~",
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
					"@",
					200,100,
					255,255,255,255
				);
				0.1.wait;
				OF.feedback("speedXY", 0,0);
				OF.writeString(
					"bigCenter",
					"@",
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
					"#",
					200,100,
					255,255,255,255
				);
				0.1.wait;
				OF.feedback("speedXY", 0,0);
				OF.writeString(
					"bigCenter",
					"#",
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
					"$",
				200,100,
					255,255,255,255
				);
				0.1.wait;
				OF.feedback("speedXY", 0,0);
				OF.writeString(
					"bigCenter",
					"$",
					200, 200,
					255,255,255,255
				);

			}.fork

		};
		~a5.action = {
			{
				OF.feedback("speedXY", 0, 1.5);
				OF.writeString(
					"bigCenter",
					"%",
					200,100,
					255,255,255,255
				);
				0.1.wait;
				OF.feedback("speedXY", 0,0);
				OF.writeString(
					"bigCenter",
					"%",
					200, 200,
					255,255,255,255
				);

			}.fork
		};
		~a6.action = {};
		~a7.action = {};
		~a8.action = {};
		~a9.action = {};
		~a10.action = {};
		~a11.action = {};

}).add;
)

//============================
//   Meros 1o
//============================
(
~meros1oResp.remove;
~meros1oResp = OSCresponderNode(
	nil,
	'meros1o',
	{ |t,r,msg|
		//Actions
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
)


//============================
//   Meros 2o
//============================
(
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


		~a1 = RespTags.do('/tags', 2, nil);
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
)









//This my setup after 121128

OSCFunc.trace(true); // Turn posting on
OSCFunc.trace(false); // Turn posting on


//First run the SendTags, then the Pdef, then the RespTags, then the actions
//Because SendTags contains the ~tagSync informations

//WARNING: Pdef(\x).quant = 0;

//: Ta tags kai ta step allazoun live

//:
~ena = SendTags.new;
~ena.dests = [Dests.mineAddr,NetAddr("169.254.63.100", 57120)];
~ena.title = '/tags';
~ena.tags = [1, 2, 3, 4];
~ena.step = [1, 1/2, 3/2, 1];
//~ena.step = [1];
~ena.times = inf;
~ena.loop;
//:

~ena.stop;
~ena.resume;
Pdef(\x).quant = 0;

//: oi metavlites tou RespTags dilonontai se seira kai ta action apo exo
(
~a1 = RespTags.do('/tags', 1, nil);
~a2 = RespTags.do('/tags', 2, nil);
~a3 = RespTags.do('/tags', 3, nil);
~a4 = RespTags.do('/tags', 4, nil);
)

//:
(
~a1.action = {
	{
		OF.rect(100,100,200,200,255,255,255,255);
		0.1.wait;
		OF.rect(100,300,200,200,255,255,255,255);
		0.1.wait;
		OF.rect(100,300,200,200,255,255,255,255);
	}.fork;

};
~a2.action = {OF.rect(300,100,200,200,255,255,255,255)};
~a3.action = {OF.rect(500,100,200,200,255,255,255,255)};
~a4.action = {OF.rect(700,100,200,200,255,255,255,255)};

)
(
~a1.action = {
	{
		OF.rect(100,100,200,200);
		0.1.wait;
		OF.rect(100,300,200,200);
		0.1.wait;
		OF.rect(100,300,200,200);
	}.fork;

};
)
//:

~a1.removeResp;
~a2.removeResp;
~a3.removeResp;
~a4.removeResp;

//:



