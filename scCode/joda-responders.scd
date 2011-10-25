//:Create OSCresponders
/*
Create OSCResponders for recieving OSC from iPod and 
forward them with a good syntax to openFrameworks.

Aris Bezas Mon, 01 November 2010, 07:41PM
*/


//~addr = NetAddr("127.0.0.1", 12345);
~addr = NetAddr("192.168.1.65", 12345);
Event.addEventType(\osc, { ~dest.sendBundle(~latency, ~msg); });

//page1
OSCresponder.new(nil, "/r8", { arg time, resp, msg; OF.rgb("r8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g8", { arg time, resp, msg; OF.rgb("g8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b8", { arg time, resp, msg; OF.rgb("b8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a8", { arg time, resp, msg; OF.rgb("a8", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r7", { arg time, resp, msg; OF.rgb("r7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g7", { arg time, resp, msg; OF.rgb("g7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b7", { arg time, resp, msg; OF.rgb("b7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a7", { arg time, resp, msg; OF.rgb("a7", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r6", { arg time, resp, msg; OF.rgb("r6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g6", { arg time, resp, msg; OF.rgb("g6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b6", { arg time, resp, msg; OF.rgb("b6", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a6", { arg time, resp, msg; OF.rgb("a6", msg[1].round);} ).add; 
//page2
OSCresponderNode.new(nil, "/setupSound", { | time, resp, msg| ~setupSoundTask.start;}).add; 
OSCresponderNode.new(nil, "/freeSound", { | time, resp, msg| ~soundSynth.free;}).add; 
OSCresponderNode.new(nil, "/viewSoundChanels", { | time, resp, msg| OF.interactWithSound("activate", msg[1]);}).add; 
OSCresponder.new(nil, "/soundLines", { arg time, resp, msg; OF.interactWithSound("glBeginType", msg[1].round);} ).add; 
OSCresponder.new(nil, "/numSoundSketches", { arg time, resp, msg; OF.interactWithSound("numSoundSketches", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxFreqIn", { arg time, resp, msg; OF.interactWithSound("maxFreqIn", msg[1].round);} ).add; 
OSCresponder.new(nil, "/maxAmpIn", { arg time, resp, msg; OF.interactWithSound("maxAmpIn", msg[1]);} ).add; 
OSCresponder.new(nil, "/elasticity", { arg time, resp, msg; OF.interactWithSound("maxSoundElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/soundBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~soundBWTask.stop;OF.rgb("sound",255,255,255,20);OF.rgb("background",0,0,0,0);};
	if (msg[1] == 1)	{~soundBWTask.start};
} ).add; 
OSCresponder.new(nil, "/backBW", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backBWTask.stop;	OF.background(0,0,0);	OF.rgb("sound",255,255,255,25);OF.rgb("background",0,0,0,30);};
	if (msg[1] == 1)	{~backBWTask.start};
} ).add; 
OSCresponder.new(nil, "/elastTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~elastTask.stop};
	if (msg[1] == 1)	{~elastTask.start};
} ).add; 
OSCresponder.new(nil, "/backTask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~backgroundTask.stop;OF.rgb("background",0,0,0,255);OF.rgb("sound",255,255,255,125);	};
	if (msg[1] == 1)	{~backgroundTask.start};
} ).add; 


~setupSoundTask = Task({
					if (not(s.serverRunning)) { s.boot };
					s.doWhenBooted {
						SendAmpFreq.start;
						0.1.wait;
						~soundSynth = Synth("SendAmpPitch");	
					};					
				});
~soundBWTask = Task({
	OF.background(0, 0, 0);
	OF.rgb("a8",0);
	OF.interactWithSound("numSoundSketches", 800);
	OF.interactWithSound("glBeginType", 1);	
	inf.do({
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;	
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("sound",255,255,255,20);					
		0.001.wait;			
	});
});
~backBWTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});
~elastTask = Task({
	inf.do({
//		OF.interactWithSound("maxSoundElasticity",0.1);					
//		0.5.wait;	
		OF.interactWithSound("maxSoundElasticity",1);					
		0.3.wait;	
		OF.interactWithSound("maxSoundElasticity",0.01);					
		0.7.wait;			
	});
});

~backgroundTask = Task({
	inf.do({
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
		OF.rgb("background",255,255,255,255);
		OF.rgb("sound",0,0,0,125);
		0.001.wait;	
		OF.rgb("background",0,0,0,255);
		OF.rgb("sound",255,255,255,125);					
		0.001.wait;	
	});
});


				
//page3

OSCresponderNode.new(nil, "/particles", { | time, resp, msg| OF.particle("activate", msg[1]);}).add; 
OSCresponderNode.new(nil, "/forceRadius", { | time, resp, msg| OF.particle("forceRadius", msg[1]);}).add; 
OSCresponderNode.new(nil, "/particleNeighborhood", { | time, resp, msg| OF.particle("particleNeighborhood", msg[1].round);}).add; 
OSCresponder.new(nil, "/r1", { arg time, resp, msg; OF.rgb("r1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g1", { arg time, resp, msg; OF.rgb("g1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b1", { arg time, resp, msg; OF.rgb("b1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a1", { arg time, resp, msg; OF.rgb("a1", msg[1].round);} ).add; 
OSCresponder.new(nil, "/r2", { arg time, resp, msg; OF.rgb("r2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/g2", { arg time, resp, msg; OF.rgb("g2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/b2", { arg time, resp, msg; OF.rgb("b2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/a2", { arg time, resp, msg; OF.rgb("a2", msg[1].round);} ).add; 
OSCresponder.new(nil, "/conBW", { arg time, resp, msg; OF.rgb("r1", 255*msg[1]);OF.rgb("g1", 255*msg[1]);OF.rgb("b1", 255*msg[1]);} ).add; 
OSCresponder.new(nil, "/particlesBW", { arg time, resp, msg; OF.rgb("r2", 255*msg[1]);OF.rgb("g2", 255*msg[1]);OF.rgb("b2", 255*msg[1]);} ).add; 

OSCresponder.new(nil, "/BWtask", { arg time, resp, msg; 
	if (msg[1] == 0)	{~particlesBWTask.stop;OF.rgb("particleConnections",0,0,0);};
	if (msg[1] == 1)	{~particlesBWTask.start};
} ).add; 
OSCresponder.new(nil, "/iPodPush", { arg time, resp, msg; 
	if (msg[1] == 0)	{OF.particle("iPodPush", msg[1])};
	if (msg[1] == 1)	{OF.particle("iPodPush", msg[1])};
} ).add; 

~particlesBWTask = Task({
	inf.do({
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;	
		OF.rgb("particleConnections",0,0,0);					
		0.001.wait;	
		OF.rgb("particleConnections",255,255,255);					
		0.001.wait;			
	});
});


// ########## FEEDBACK  #############
OSCresponder.new(nil, "/feedback", { arg time, resp, msg; OF.feedback("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/speedXY", { arg time, resp, msg; OF.feedback("speedXY", msg[1], msg[2]);} ).add; 

// ##########  SKETCH  ##############
OSCresponder.new(nil, "/drawWithPad", { arg time, resp, msg; OF.interactWithSketch("activate", msg[1]);} ).add; 
OSCresponder.new(nil, "/mouseLines", { arg time, resp, msg; OF.interactWithSketch("mouseLines", msg[1]);} ).add; 
OSCresponder.new(nil, "/numMouseSketches", { arg time, resp, msg; OF.interactWithSketch("numMouseSketches", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("minMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseElasticity", { arg time, resp, msg; OF.interactWithSketch("maxMouseElasticity", msg[1]);} ).add; 
OSCresponder.new(nil, "/minMouseDamping", { arg time, resp, msg; OF.interactWithSketch("minMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/maxMouseDamping", { arg time, resp, msg; OF.interactWithSketch("maxMouseDamping", msg[1]);} ).add; 
OSCresponder.new(nil, "/padXY", { arg time, resp, msg; OF.interactWithSketch("padXY", msg[2], 1200 - msg[1]);} ).add; 
OSCresponder.new(nil, "/sketchPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPat", { arg time, resp, msg; 
	if (msg[1] == 0)	{Pdef(\sketchBWPat).stop;};
	if (msg[1] == 1)	{Pdef(\sketchBWPat).play;};
} ).add; 
OSCresponder.new(nil, "/sketchBWPatDur", { arg time, resp, msg; ~sketchBWDur.source = Pseq([msg[1]],inf);} ).add; 


~sketchTuple = PatternProxy( 
			Ptuple(["interactWithSketch", "padXY", Pseq(Array.series(1000, 0, 1).scramble, inf), Pseq(Array.series(200, 10, 1000/200).mirror, inf)] , inf) 
		);
~sketchDur = PatternProxy(Pseq(Array.geom(82, 0.003, 1.05).pyramid(2),inf));		

Pdef(\sketchPat,Pbind(
		\type, \osc, 
		\dur, ~sketchDur,
	 	\dest, ~addr,
		\msg, ~sketchTuple
);
);	

~sketchBWTuple = PatternProxy( 
			Ptuple(["rgb", "sketchBW", Pseq([0, 255], inf), Pseq([0, 255], inf), Pseq([0, 255], inf)]) 
		);
~sketchBWDur = PatternProxy(Pseq([0.1,0.1],inf));		

Pdef(\sketchBWPat,Pbind(
		\type, \osc, 
		\dur, ~sketchBWDur,
	 	\dest, ~addr,
		\msg, ~sketchBWTuple
);
);	


