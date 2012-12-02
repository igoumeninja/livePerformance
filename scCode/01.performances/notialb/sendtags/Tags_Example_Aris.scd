
//First run the SendTags, then the Pdef, then the RespTags, then the actions 
//Because SendTags contains the ~tagSync informations

//WARNING: Pdef(\x).quant = 0;

//: Ta tags kai ta step allazoun live

//:
~ena = SendTags.new;
~ena.dests = [Dests.mineAddr, v = NetAddr("192.168.1.67", 57120), NetAddr("169.254.145.99", 57120)];
~ena.title = '/tags';
~ena.tags = [1, 2, 3, 4, 5];
//~ena.step = [1, 1/2, 1/4, 1, 1.5]/2;
~ena.step = [1/3, 1/2, 1/3, 1/3, 1/6];
//~ena.step = [1];
~ena.times = inf;
~ena.loop;
//:

~ena.stop;
~ena.resume;
Pdef(\x).quant = 0;

//: oi metavlites tou RespTags dilonontai se seira kai ta action apo exo

~a1 = RespTags.do('/tags', 1, nil);
~a2 = RespTags.do('/tags', 2, nil);
~a3 = RespTags.do('/tags', 3, nil);
~a4 = RespTags.do('/tags', 4, nil);
~a5 = RespTags.do('/tags', 5, nil);

~counter1 = 0;
~counter2 = 0;
//:

~a1.action = {'ena'.postln; /*Pdef(\x).play;*/ ~counter1 = ~counter1 + 1;
		//~counter.postln;
		if (~counter1 == 1) {v.sendMsg("afterEisagogi"); ("afterEisagogi").postln;};
		if (~counter1 == 5) {v.sendMsg("meros1o"); ("meros1o").postln;};		
				};

~a2.action = {'dio'.postln; ~counter2 = ~counter2 + 1;
	
		if (~counter2 == 10) {v.sendMsg("meros2o"); ("meros2o").postln; };
		
				};

~a3.action = {'tria'.postln;};

~a4.action = {'tessera'.postln;};

~a5.action = {'pente'.postln; /*v.sendMsg("meros1o");*/};


//:

~a1.removeResp;
~a2.removeResp;
~a3.removeResp;
~a4.removeResp;

//:
Pdef(\x).stop; 
