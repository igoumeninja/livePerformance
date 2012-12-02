


NotialbPatterns
{

*load{

//:Patterns - Parallages
//:===================================================================


//:Eisagogi_Pattern
//:========================

Pdef(\import, Pbind(
		\instrument, \buf,
		\dur, Pseq([1], 1),
		\ipod, Pseq([0], 1)
));

Pdef(\import).quant = 0;
//Pdef(\import).fadeTime = 5;


//:After_Eisagogi_Patterns
//:========================

Pdef(\afterEisagogi, Ppar([
		

Pbind(
		\instrument, \Noise_Eisagogi,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\ampN, Pseq([0.5, 0.1, 0.2, 0.2, Pseq([0], 6), 0]*40, inf),
		\nMul, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([0.001], 6), 1e40], inf),
		//\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
		\nAdd, Pseq([1e40, 1e40, 1e40, Prand([1e4, 1e40], 1), Pseq([1e4], 6), 1e40], inf),
		//Noise Envelope
		\at, Pseq([0.08, 0.08, 0.08, 0.08, Pseq([0.08], 6), 0.08], inf), 
		\rel, Pseq([0.02, 0.02, 0.02, 0.02, Pseq([0.02], 6), 0.02], inf),
		\shape, Pseq([-8, -8, -8, -18, Pseq([8], 6), 8], inf)
),
	
	
Pbind(
		\instrument, \Drone_Glitch_Eisagogi,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf), 
		\ampD, Pseq([0, 0, 0, 0, Pseq([0, 0, 0.6 ,0, 0, 0], 1), 0.6], inf), 
		\ampG, Pseq([0, 0, 0, 0, Pseq([4], 6), 0]*0, inf), 
		\harm, Pseq([5, 0.5, 0.5, 0.5, Pseq([0.5, 0.5, 20, 0.5, 0.5, 0.5], 1), 0.5], inf), 
		\freq, Pseq([~fD2, 0, 0, 0, Pseq([4, 4, [~fD7, ~f_7], 4, 4, 4], 1), ~fD2], inf),
		//Drone Envelope
		\at, Pseq([0.008, 0.08, 0.08, 0.08, Pseq([0.8, 0.8, 1/3, 0.8, 0.8, 0.8], 1), 0.05], inf), 
		\rel, Pseq([1.5, 0.2, 0.2, 0.2, Pseq([0.2, 0.2, 1/6, 0.2, 0.2, 0.2], 1), 1.5], inf),
		\shape, Pseq([8, 8, 8, 8, Pseq([8, 8, 8, 8, 8, 8], 1), 8], inf)
		
);
	
	], inf)
);

Pdef(\afterEisagogi).quant = 0;

//:Meros_1_Patterns
//:========================

//Auto tha kleinei me to Drone tou kai telos tha menei mono to glitch
//Dokimi Pio poli freq sto LFPUlse --- me freq:2 GAMATO!! kai 0 ---------> Auto na mpainei anamesa dio fores mexri na ginei o kiklos (Tha vgenei to noise kai freq:2)
//1e4 anti 1e40 sto 3o tag kai dokimi kai se alla tags episis gamata 

Pdef(\meros_1, Ppar([
		
Pbind(
		\instrument, \Bota_Meros_1_1,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\harm, Pseq([1], inf),
		\ampB, Pseq([3, 3, 3, 3, Pseq([3], 6), 3]/4, inf),
		\freq, Pseq([~f_2], inf),
		//Bota Envelope
		\at, Pseq([0.01, 0.01, 0.01, 0.01, Pseq([0.01], 6), 0.01], inf),
		\rel, Pseq([0.02, 0.02, 3, 3, Pseq([3], 6), 3], inf),
		\shape, Pseq([-4], inf)
		
),

Pbind(
		\instrument, \Noise_Meros_1,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf),
		\ampN, Pseq([0.1, 0.1, 0.1, 0.1, Pseq([0.1], 6), 0.1], inf),
		\nMul, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([0.001], 6), 1e40], inf),
		\nAdd, Pseq([1e40, 1e40, 1e40, 1e40, Pseq([1e4], 6), 1e40], inf),
		
		//Noise Envelope
		\at, Pseq([0.08, 0.08, 0.08, 0.08, 0.08, 0.08], inf), 
		\rel, Pseq([0.2, 0.2, 0.2, 0.2, 0.2, 0.2], inf),
		//\shape, Pseq([-200, -200, -200, -200, -200, -200], inf)
		\shape, Pseq([-200], inf)
),

	
Pbind(
		\instrument, \Drone_Glitch_Meros_1,
		//All
		\dur, Pseq([1/3, 1/2, 1/3, 1/3, Pseq([1/6], 6), 1/6], inf), //?
		\ampD, Pseq([1.5, 0, 0, 0, Pseq([0], 6), 1]/2, inf), 
		\ampG, Pseq([0], inf), 
		//\harm, Pseq([0.5], inf), 
		\harm, Pseq([0.5, 0.5, 0.5, 0.5, Pseq([0.5], 6), 20], inf), 
		\freq, Pseq([~fD2, ~fD2, ~fD2, ~fD2, Pseq([~fD2], 6), [~fD7, ~f_8]], inf),
		//Drone Envelope
		\at, Pseq([0.5, 0.08, 0.08, 0.08, Pseq([0.08], 6), 1/3], inf), 
		\rel, Pseq([1, 0.2, 0.2, 0.2, Pseq([0.2], 6), 1/12], inf),
		\shape, Pseq([4], inf)
		
)
	

	
	], inf)
);

Pdef(\meros_1).quant = 0;





	}

}