
Dests { classvar <>mineAddr, <>pappousAddr, <>axilleasAddr;

	*initClass {
	
		StartUp.add{
				
			mineAddr = NetAddr.localAddr;
			pappousAddr = NetAddr("169.254.47.198", 57120);
			axilleasAddr = NetAddr("169.254.45.129", 57120);
						//mitsosAddr = NetAddr("10.1.60.1", 57121);
		
		
		}
	
	}
	
	
	
}

SendTags {
	
	var <>num=0, <>tags, <>step=0.25, <>title, <>steppattern, <>tagPat, task, verbose = true;
	var  <>dests, <>tag, <>tagg, <>stp, <>stp2, <>x, <>times = inf;
	var tag2;
	
	immediate {
		verbose = true;
	}
	atEnd {
		verbose = false;
	}
	
	loop { 

		tagPat = PatternProxy(Pseq([nil], inf));
		steppattern = PatternProxy(Pseq([nil], inf));//def 0.2 --nil
		~tagSync = PatternProxy(Pseq([nil], inf));//def 0.2 --nil
		tagg = tagPat.asStream;
		stp = steppattern.asStream;
		//stp2 = ~tagSync.asStream;
		
		task = Task({ 
			times.do{
				
				tagPat.source = Pseq(tags, inf);
				steppattern.source = Pseq(step, inf);
				~tagSync.source = Pseq(step, inf).asStream;
				
				
				verbose.switch(
					true, {				
						num = tags.numChannels;
						num.do{ 
							x = tagg.next;
							dests do: _.sendMsg(title.asString, x);
							stp.next.wait;
							//1/2 * (stp2.next + stp.next).wait; 
							//tag = tag+1;
						}
					},   
					false, {
						steppattern.source = Pseq([step], inf);
						~tagSync.source = Pseq([step], inf).asStream;
						num.do{ 					
							
							x = tagg.next;
							stp = steppattern.asStream;
							stp = ~tagSync.asStream;
							dests do: _.sendMsg(title.asString, x);
							stp.next.wait;
							//1/2 * (stp2.next + stp.next).wait; 
							//tag = tag+1;
						}
					}
				)
			}
		});
		task.start;
	}
	pause {
		task.pause;
	}
	resume {
		task.resume;
	}
	reset {
		task.reset;
	}
	stop {
		task.stop;		
	}
	
}


RespTags { var <>title, <>tag, <>action, <>responder;
	// <> Gia na dilonontai apo exo san sendTags.scd me .title ktl
		var addResp;
	
	*do { |title, tag, action, responder|
		
		^super.newCopyArgs(title, tag, action, responder).addResp;
	
	}
	

	addResp { 
		
		responder = OSCresponderNode(nil, title.asString, {arg time, resp, msg; /*msg[1].postln;*/
			msg[1].switch( tag, 
				action
			 );
		
		}).add;
	
	}
	

	removeResp {
		responder.remove;
	}
	

}



RespClockTags { var <>title, <>tags, <>actions, <>numTags1, <>numTags2, num = 0;
	
	addResp { 
		numTags1 = tags.size;
		fork{			
			numTags1.do{ 
				OSCresponderNode(nil, title.asString, {arg time, resp, msg; /*msg[1].postln;*/
					msg[1].switch( tags[num], 
					actions[num]
						 );
					}).add;
				num.postln;
				num = num + 1;
				//numTags = numTags;
			}
		};
		fork{
			inf.do{
				var count;
				numTags2 = tags.size;
				if((numTags2 != numTags1) && (numTags2 > numTags1), 
					{ count = numTags2 - numTags1;
						count.do{
							OSCresponderNode(nil, title.asString, {arg time, resp, msg;
								msg[1].switch( tags[num], 
								actions[num]
									 );
								}).add;
							num.postln;
							num = num + 1;
							  };
					},
					{  }
					
				);
				0.001.wait;
			}
		};
				
		
	}

}
