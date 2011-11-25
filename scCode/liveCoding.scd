//:--
~ataka.stop;
~ataka = {
	~dur = Pseq((0.1..10).mirror*0.1, inf).asStream;
	~dur = Pseq([1/2,1/2,1/2,1]/8, inf).asStream;
	~dur = Pseq([1/2,1,1/2,1/2]/2, inf).asStream;	
	~dur = Pseq([1/4,1,3/2,1/2]/2, inf).asStream;		
	inf.do { |i|
		OF.writeString("bigCenter", "hbuc".at(i%4).asString,(~width/2-150).asInteger,(~height/2).asInteger,255,255,255,255); // BIG 
		~dur.next.wait;
	}
}.fork;
//:FFT LETTER DESTRUCT
thisProcess.stop;
OF.effect("mirror", 1); 	
OF.effect("mirror", "case", 1); 
~myBufferForDestruct = Buffer.alloc(s, 512);
~fftDestructTrigger.free;
~fftDestrucrtTrigger = { | threshold = 1.0 |
	var sig, chain, onsets, pips;
	sig = In.ar(8);	
	chain = FFT(~myBufferForDestruct, sig);
	onsets = Onsets.kr(chain, threshold, \rcomplex);
	SendReply.kr(onsets, \destrucrt);
}.play;

~destructTriggerResp.remove;
~count = 0;
~destructTriggerResp = OSCresponderNode(n, 'destrucrt', { |time, resp, msg| 
	~count=~count+1;
	OF.writeString("bigCenter", "hbuc".at(~count%4).asString,(~width/2-150).asInteger,(~height/2).asInteger,255,255,255,255); // BIG 
	OF.effect("destruct","phrase1");
}).add; 
//:-

//:FFT LETTER DESTRUCT

thisProcess.stop;
OF.effect("mirror", 1); 	
OF.effect("mirror", "case", 0); 
~myBufferForDestruct = Buffer.alloc(s, 512);
~fftDestructTrigger.free;
~fftDestrucrtTrigger = { | threshold = 1.0 |
	var sig, chain, onsets, pips;
	sig = In.ar(8);	
	chain = FFT(~myBufferForDestruct, sig);
	onsets = Onsets.kr(chain, threshold, \rcomplex);
	SendReply.kr(onsets, \destrucrt);
}.play;

~destructTriggerResp.remove;
~count = 0;
~destructTriggerResp = OSCresponderNode(n, 'destrucrt', { |time, resp, msg| 
	~count=~count+1;
	OF.writeString("bigCenter", "hbuc".at(~count%4).asString,(~width/2-150).asInteger,(~height/2).asInteger,255,255,255,255); // BIG 
}).add; 
//:-

OF.effect("mirror", 1); 	
~fftDestructTrigger.free;
~destructTriggerResp.remove;