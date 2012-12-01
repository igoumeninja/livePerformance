Posc
Preceive
Psend
~step = PatternProxy(Pseq([0.33525], inf));

~step.source = Pseq([0.2], inf).asStream;

Pdef(\synthtags,
			Posc(
				\msg,  Pseq([\1, \2,  \3, \4, \5, \6, \7, \8], inf),
				\dur, Pfunc({ ~step.asStream.next; }, { \reset.postln;}),
				\dest, [NetAddr.localAddr]
			);
		);
				
Pdef(\synthtags).play(quant:0);

(
Preceive(
	'1' -> {
		'ena'.postln;
	}	
).play; 
)

~r1.play;

(
fork{
	inf.do{
			8.do{ arg tag=1;
				NetAddr.localAddr.sendMsg("/tags", tag+1);
				0.25.wait;
				tag = tag+1;
			};
		};
};
)

(
~resp = OSCresponderNode(NetAddr.localAddr, "/tags", {arg time, resp, msg; msg[1].postln;
	msg[1].switch( 1, {
				{
				Saw.ar(~g2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), 					doneAction: 2)}.play;

	} );}).add;
)
~resp.remove;
a = SendTags.new;
a.loop(10, 1)
a.num = 10
a.step = 0.1
Synth
Preceive
a.stop;
"5".isKindOf(String)

NeedSugar

Destinations.dakisAddr


SendTags.dests = [Dests.dakisAddr]

SendTags.dests

///check this out.......
//SendTags.dests = [Dests.dakisAddr]
a = SendTags.new
a.dests = [Dests.dakisAddr]
a.title = '/tags';
a.loop
a.step = 0.1
a.pause
a.resume
a.reset
a.stop
a.num = 16
a.immediate;
a.atEnd;
~step = PatternProxy(Pseq([0.2], inf));
~step.source = Pseq([0.1, 0.2, 0.01, 0.3], inf).asStream;

fork{
30.do{
a.step = ~step.asStream.next;
a.step.wait;
}
}

~step.source = Pseq([0.1, 0.2, 0.01, 0.3], inf).asStream;

//////RespTags testing.....

r=RespTags.new;
r.title = '/tags'
r.tag=7;
r.action = {'efta'.postln;}
r.addResp

RespTags.addresp

/perctags

fork{
	a.loop
	b.loop
	..
	..
}