/*
Re Xitzaz

Create a algorithmic piece that will interact with Re xitzaz scale. 
Steps:
1. Create trigger for 440 to 880 hzq
*/


MakeResponders
a.impulseRate(2);
//:Tempo
~tempo = TempoClock.default;
~timeStart = ~tempo.seconds;	
a = SendAmpFreq.new; 	
a.start; 	
//:Synth
x.free;
x = {SinOsc.ar(460)}.play;

//:Old Responder
~detect2Resp.remove;
~detect2Resp = OSCresponderNode(~scNetwork, 'mlab', { |t,r,msg|
	if( msg[1] == 'freq',{
		if ( (msg[2] > 435) && (msg[2] < 445), {
			~laTrue = true;
			~timeStart = ~tempo.seconds;
			"LA".postln;
		}, 
		{
			~laTrue = false;
			"LA GOES".postln;			
		});
		if ( (msg[2] > 878) && (msg[2] < 882) && ~laTrue = true && (~tempo.seconds - ~timeStart) < 0.5, {
			"DONE".postln;
		});
	});
}).add;
 
 
//:New responder
OSCdef(\detect2Resp2).disable;
OSCdef(\detect2Resp2, { |msg, time, addr, recvPort|
	\opa.postln;
	if( msg[1] == 'freq',{
		if ( (msg[2] > 435) && (msg[2] < 445), {
			~laTrue = true;
			~timeStart = ~tempo.seconds;
			"LA".postln;
		}, 
		{
			~laTrue = false;
				"NO LA".postln;
		});
		if ( (msg[2] > 878) && (msg[2] < 882) && ~laTrue = true && (~tempo.seconds - ~timeStart) < 0.5, {
			"DONE".postln;
		});
	});
	
	
	
	},
	'\mlab', ~scNetwork);
//:-
//:- OSCdef.freeAll