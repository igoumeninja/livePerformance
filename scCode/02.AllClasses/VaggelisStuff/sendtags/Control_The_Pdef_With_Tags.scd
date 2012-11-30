
//:
Pdef(\x, Pbind(
		\dur, ~tagSync,
		\freq, Pseq([148, 50, 30, 148]*4, inf),
		\amp, 1
));
//:
Pdef(\x).play;
Pdef(\x).stop;



~tagSync = PatternProxy(Pseq([0.2], inf));
~tagSync.source = Pseq([0.1], inf).asStream;
//
//~dur1 = PatternProxy(Pseq([0.01], 1));
//
//~dur1.source = Pseq([value*0.01], 1);