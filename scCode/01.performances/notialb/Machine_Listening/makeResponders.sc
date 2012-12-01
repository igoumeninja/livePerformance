/* 
Aris Bezas Mon, 12 12 2012, 20:36
A class based on SendAmpFreq for machine listening.

r = MakeResponders.new
r.all
r.removeResponders;

*/

MakeResponders {
	classvar default;
	var <server;					//	the scserver that runs the listening process
	var <synthListenAmpFreq;		//	the listening Amp Freq process
	var <synthListenOnsets;			//	the listening Onsets process
	var <synthPlay;				//	the produce process
	var <addr;					//	the address (p5, of ...) for sending the data for drawing
	var <chan = 0;				//	the channel that we detect
	var <responders;				//	responders 
	
	*default {
		if (default.isNil) { default = this.new };  // create default
		^default;
	}
	*new { | server, addr, chan = 0 |
		^super.new.init(server, addr, chan);		
	}	
	init { | argServer, argAddr, argChan = 0 |
		"-MakeResponders".postln;
		server = argServer ?? { Server.default };  //define server
		addr =  argAddr ?? { NetAddr("127.0.0.1", 12345); }; //localhost, oF port
		chan = argChan;
		this.makeResponders;	// call makeResponders
	}
	makeResponders {
		responders = [
			this.makeAudioResp	, this.makeOnsetResp	];
	}
	
	makeAudioResp {
		^OSCresponder(server.addr, '/tr',{ arg time,responder,msg;
			switch(msg[2], 	
				1, { 
					OF.sketch3d('zCoordSketch3d', msg[3]);
					OF.mlab('amp', msg[3]);					
					SC_Client.mlab('amp', msg[3]);
					}, 
				2, { 
					OF.mlab('freq', msg[3]);
					SC_Client.mlab('freq', msg[3]);
					},
				3, { OF.mlab('specCentroid', msg[3]);
					SC_Client.mlab('specCentroid', msg[3]);
					},
				4, { OF.mlab('specFlatness', msg[3]);
					SC_Client.mlab('specFlatness', msg[3]);
					},
				5, { OF.mlab('loudness', msg[3]);
					SC_Client.mlab('loudness', msg[3]);
					}
			);
		});
	}
	
	makeOnsetResp {		
		^OSCresponder(server.addr, '/onset',{ arg time,responder,msg;
			SC_Client.mlab('onset');
//			OF.mlab('onset');
//			OF.writeString("bigCenter", "~sad",~width/2,~height/2,255,255,255,255); // BIG 
//			{
//				1.do{|i|
//					OF.particle("forceRadius", 300);	
//					0.05.wait;
//					OF.particle("forceRadius", 10);	
//				}
//			}.fork;
		});
	}
	
	all {
		responders do: _.add;			//.add all the responders
	}

	removeResponders {
		responders do: _.remove;
	}
}

