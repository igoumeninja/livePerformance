NeedSugar.load

NotesData.load;

//sol, sol/2, fa, sib(katw), fa#, fa#/2, sol#, sol#/2

(
fork{
	4.do{
		{Saw.ar(~g_2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~g_1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~f_2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~aD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~fD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~fD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~gD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~gD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
	};	
	4.do{
		fork{
			1.do{
				3.5.wait;
				{Saw.ar(~aD3, 0.3, 0) * EnvGen.ar(Env.perc(releaseTime: 2.25), doneAction: 2)}.play;
				0.5.wait;
				};
		};
		{Saw.ar(~g_2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~g_1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~f_2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~aD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~fD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~fD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~gD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~gD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
	};
	4.do{
		fork{
			1.do{
				3.5.wait;
				{Saw.ar(~aD3, 0.3, 0) * EnvGen.ar(Env.perc(releaseTime: 2.25), doneAction: 2)}.play;
				0.5.wait;
				};
		};
		fork{
			1.do{
				1.5.wait;
				{Saw.ar(~aD4, 0.1, 0) * EnvGen.ar(Env.perc(attackTime: 0.08, releaseTime: 4.25), doneAction: 2)}.play;
				3.5.wait;
				};
		};
		{Saw.ar(~g_2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~g_1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~f_2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~aD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~fD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~fD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~gD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~gD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
	};
	
	3.do{
		fork{
			1.do{
				3.5.wait;
				{Saw.ar(~aD3, 0.3, 0) * EnvGen.ar(Env.perc(releaseTime: 2.25), doneAction: 2)}.play;
				0.5.wait;
				};
		};
		fork{
			1.do{
				1.5.wait;
				{Saw.ar(~d_3, 0.2, 0) * EnvGen.ar(Env.perc(attackTime: 0.06, releaseTime: 2.25), doneAction: 2)}.play;
				3.5.wait;
				};
		};
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~g_2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~g_1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
	};
	1.do{
		fork{
			1.do{
				3.5.wait;
				{Saw.ar(~aD3, 0.3, 0) * EnvGen.ar(Env.perc(releaseTime: 2.25), doneAction: 2)}.play;
				0.5.wait;
				};
		};
		fork{
			1.do{
				1.5.wait;
				{Saw.ar(~d_3, 0.2, 0) * EnvGen.ar(Env.perc(attackTime: 0.06, releaseTime: 2.25), doneAction: 2)}.play;
				3.5.wait;
				};
		};
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(attackTime: 0.01, releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~dD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~dD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
		{Saw.ar(~fD2, 1.5, 0) * EnvGen.ar(Env.perc(releaseTime: 0.25), doneAction: 2)}.play;
		0.25.wait;	
		{Saw.ar(~fD1, 0.8, 0) * EnvGen.ar(Env.perc(releaseTime: 0.5), doneAction: 2)}.play;
		0.75.wait;
	};

};

)


//not ready yet.....
{ AllpassL.ar(Decay.ar(In.ar(0), 0.2, WhiteNoise.ar), 0.2, 0.2, 3) }.play;

