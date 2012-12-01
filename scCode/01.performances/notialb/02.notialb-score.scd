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



