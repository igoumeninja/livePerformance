//sendtags
(
~ena = SendTags.new;
~ena.tags = [1, 2, 3, 4, 5, 6, 7, 8];
~ena.dests = [Dests.dakisAddr];
~ena.step = 0.7773125;
~ena.title = '/tags';
~ena.loop;
)



~enar = RespTags.new;
~enar.title = '/tags';
~enar.tag = 1;
~enar.action = {
	'enwwa'.postln;
	};
~enar.addResp;


~dio = RespTags.new;
~dio.title = '/tags';
~dio.tag = 3;
~dio.action = { 'dio'.postln;};
~dio.addResp;


~tria = RespTags.do('/tags', 3, nil);
~tria.addResp;

~tria.action = {'trai'.postln;}


~dio = RespTags.do('/tags', 4, {'ZZZ'.postln;});
~dio.addResp;

~dio.action = {'30'.postln;}


//////////////////////New

(
~ena = SendTags.new;
~ena.tags = [1, 2];
~ena.dests = [Dests.dakisAddr];
~ena.step = [1];
~ena.title = '/tags';
~ena.loop;
)

~ena.stop;

(
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


~a1.action = {'ena'.postln;};
~a2.action = {'dio'.postln;};
~a3.action = {'tria'.postln;};
~a4.action = {'tesera'.postln;};
~a5.action = {'pente'.postln;};
~a6.action = {'exi'.postln;};
~a7.action = {'efta'.postln;};
~a8.action = {'okto'.postln;};

~a9.action = {'enia'.postln;};
~a10.action = {'deka'.postln;};
)

~a1.tag = 1;
~a2.tag = 2;

~a1.removeResp;
~a2.removeResp;

