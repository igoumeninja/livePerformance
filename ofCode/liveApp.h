#ifndef _LIVE_APP
#define _LIVE_APP
#define OF_ADDON_USING_OFXVECTORMATH

// addons
#include "ofMain.h"
#include "ofxVectorMath.h"
#include "ofxOsc.h"
#include "ofxThread.h"
#include "ParticleSystem.h"

#include <vector>

// my classes
#include "ofSketch.h"

#define MAX_SKETCHES		2048
#define MAX_CIRCLES			1024
#define MAX_MATRIX			64
#define PORTlisten			12345
#define NUM_MSG_STRINGS		20

class liveApp : public ofBaseApp{
	
public:
	
	void	setup();
	void	update();
	void	draw();
	
	void	keyPressed  (int key);
	void	keyReleased	(int key);
	void	mouseMoved	(int x, int y );
	void	mouseDragged(int x, int y, int button);
	void	mousePressed(int x, int y, int button);
	void	mouseReleased(int x, int y, int button);
	void	resized		(int w, int h);		
	
	void	beatStrPrint(string beatStr);						
	void	fillBackground();
	void	circleStudies();
	void	playWithComplex();	
	void	feedbackStudies();	
	void	maskStudies();			
	void	seed1(float dotSize, float angle, float x, float y);
	void	seed2(float dotSize, float angle, float x, float y);	
	
	//========================
	//   VIDEO
	int						playVideo, rVideo, gVideo, bVideo, aVideo;
	ofVideoPlayer*			myVideo;
	
	//	addons objects
	ofxVec2f				p;
	ofxOscSender			osc_sender;
	ofxThread				thread;
	ParticleSystem			particleSystem;
		
	// my objects
	ofSketch				sketch[MAX_SKETCHES];
	
	//==================
	// texture effect
	bool noiseEffect, mirrowEffect1, mirrowEffect2;
	ofTexture		texGray;
	ofTexture 		texMirrow;
	
	int 			w, h;
	
	unsigned char 	* colorPixels;
	unsigned char 	* grayPixels;
	unsigned char 	* colorAlphaPixels;
	
	
	//================
	
	
	// spectro
	//ofTexture		texScreen;
	float spectroRed,spectroGreen,spectroBlue,spectroAlpha;
	float textureRed, textureGreen, textureBlue, textureAlpha, reverseEllipse, reverseTexture ;
	int mirrorMode;
	
	
	ofTrueTypeFont		font;
		
	float	data[1024];
	float		countX;
	
	// Particles
	int rConColor,gConColor,bConColor,aConColor,rDotColor,gDotColor,bDotColor,aDotColor;
	int bounceXstart, bounceYstart, bounceXend, bounceYend;
	bool isMousePressed, slowMotion, viewParticles, iPadPush, pushParticles;
	float timeStep, pushX, pushY;
	int lineOpacity, pointOpacity;
	float particleNeighborhood, particleRepulsion;
	float centerAttraction;
	int kParticles, forceScale, forceRadius;
	
	// Read from file x,y,z coordinates
	ofxVec3f		myObj;
	int csvLines, distrurbance, objType;
	float multiX,multiY,multiZ;
	bool  viewOBJ;
	float xPos[4760];
	float yPos[4760];
	float zPos[4760];
	float viewControlY,viewControlX;
	int obj_a, obj_r, obj_g, obj_b;
	
	
	string	lsystemString;
	int		lsystemGeneration;
		

	ofImage			image[200];
		
	//****  control view ******
	bool	viewSketchAutomato, memAlloc;

	
	//***** memAlloc
	float gridX[MAX_MATRIX][MAX_MATRIX], gridY[MAX_MATRIX][MAX_MATRIX];
	float gridX3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX], gridY3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX], gridZ3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX];
	ofxVec3f		matrix3d;			
	
	string renderString;
	
	//##########	Beat  #################
	bool typoEffect;
	ofTrueTypeFont myFont11, myFont350;
	string str;
	int strPosX, strPosY;
		
	//	Feedback
	ofTexture		texScreen, texScreen2, pixelTexture;
	float 			feedbackSpeedX,	feedbackSpeedY;
	int				feedbackView, timeLine, viewRotate, view_fillBackground;
	
	//  Tree
	float dotSize, angleOffsetA, angleOffsetB;
		
	//from GUI
	float			numMouseSketches, numSoundSketches, mouseLines, soundLines, viewSoundChanels;
	float			minSoundElasticity, maxSoundElasticity, minSoundDamping, maxSoundDamping;
	float			minMouseElasticity, maxMouseElasticity, minMouseDamping, maxMouseDamping;
	float			xSolo, ySolo;
	
	//		for ImageCapture		
	int				snapCounter;
	char 			snapString[255];
	ofImage 		img;
	bool 			bSnapshot;		
	float 			phase;
	
	//	SKETCH - SOUND - NO SOUND
	
	int				rSound, gSound, bSound, aSound;
	float			padX, padY;
	float			Yamp0, Yamp1, Yamp2, Yamp3, Yamp4, Yamp5, Yamp6, Yamp7;
	float			Xfreq0, Xfreq1, Xfreq2, Xfreq3, Xfreq4, Xfreq5, Xfreq6,Xfreq7;
	bool			bSmooth;
	float			ampIn,ampOut, freqIn, freqOut;
	float			ampChan0, ampChan1, ampChan2, ampChan3;		
	float			freqChan0, freqChan1, freqChan2, freqChan3;				
	float			ampInLow,ampOutLow, ampInHigh, ampOutHigh;
	float			freqInLow, freqOutLow,  freqInHigh, freqOutHigh;
	
	bool			sketchPhrase, drawNow, drawWithMouse, rotate, viewCamera, viewBlob, viewDiff, soundEffectNoto;
	int				midi1, midi2, midi3, midi4, midi5, midi6, midi7, midi8;
	int				midi65, midi66, midi67, midi68, midi69;
	int				midi70, midi71, midi72, midi73, midi74, midi75, midi76, midi77, midi78, midi79;
	int				midi80, midi81, midi82, midi83, midi84, midi85, midi86, midi87, midi88, midi89;
	int				midi90, midi91, midi92, midi93, midi94, midi95, midi96, midi97, midi98, midi99;
	int				midi100, midi101, midi102, midi103, midi104;
	int				r1, r2, r3, r4, r5, r6, r7, r8, r9;
	int				g1, g2, g3, g4, g5, g6, g7, g8, g9;
	int				b1, b2, b3, b4, b5, b6, b7, b8, b9;				
	int				a1, a2, a3, a4, a5, a6, a7, a8, a9;		
	
	//		background
	float			fillRate;
	int				aBack, rBack, bBack, gBack; 
	
private:
	int windowWidth, windowHeight; 
	ofxOscReceiver	receiver;
	int				current_msg_string;
	string			msg_strings[NUM_MSG_STRINGS];
	float			timers[NUM_MSG_STRINGS];
	
};

//extern liveApp *myApp;

#endif
