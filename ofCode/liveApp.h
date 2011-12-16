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
#include "ofSuperformula.h"

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
	
	void	seed1(float dotSize, float angle, float x, float y);
	void	seed2(float dotSize, float angle, float x, float y);	
	
	//========================
	//	VIDEO
	int		playVideo, rVideo, gVideo, bVideo, aVideo;
	int		videoX,videoY,videoW,videoH;		
	ofVideoPlayer*			myVideo;	
	
	//========================
	//	SOUND
	int playSpectro,mirrorMode, count;
	float spectroRed,spectroGreen,spectroBlue,spectroAlpha;
	float textureRed, textureGreen, textureBlue, textureAlpha, reverseEllipse, reverseTexture ;
	float	data[1024];
	float amp, freq, loudness, onset, specCentroid, specFlatness; 
	
	//========================
	//  OBJECTS
	ofxVec2f				p;
	ofxOscSender			osc_sender;
	ofxThread				thread;
	ParticleSystem			particleSystem;
	ofSketch				sketch[MAX_SKETCHES];
	
	//==================
	//	EFFECTS
	ofTexture		texScreen2, pixelTexture;
	float 			feedbackSpeedX,	feedbackSpeedY;
	int				feedbackView, timeLine, viewRotate, view_fillBackground, mirrorEffectCase;	
	bool			mirrorEffect, noiseEffect, mirrowEffect1, mirrowEffect2, mirrowEffect3,mirrowEffect4, memAlloc;
	ofTexture		texGray;
	ofTexture 		texMirrow;	
	int 			w, h;	
	unsigned char 	* colorPixels;
	unsigned char 	* grayPixels;
	unsigned char 	* colorAlphaPixels;
	float			gridX[MAX_MATRIX][MAX_MATRIX], gridY[MAX_MATRIX][MAX_MATRIX];
	float			gridX3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX], gridY3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX], gridZ3d[MAX_MATRIX][MAX_MATRIX][MAX_MATRIX];
	ofxVec3f		matrix3d;
	float			dotSize, angleOffsetA, angleOffsetB;
	int				aBack, rBack, bBack, gBack; 
	
	//================
	//	TYPOGRAPHY
	ofTrueTypeFont myFont11, myFont350;
		
	//================	
	//	PARTICLES
	int rConColor,gConColor,bConColor,aConColor,rDotColor,gDotColor,bDotColor,aDotColor;
	int bounceXstart, bounceYstart, bounceXend, bounceYend;
	bool isMousePressed, slowMotion, viewParticles, iPadPush, pushParticles;
	float timeStep, pushX, pushY;
	int lineOpacity, pointOpacity;
	float particleNeighborhood, particleRepulsion;
	float centerAttraction;
	int kParticles, forceScale, forceRadius;
		
	//================
	//	IMAGES
	ofImage			image[200];
		
	//================
	//	SKETCH
	bool	viewSketchAutomato;
	float	numMouseSketches, numSoundSketches, mouseLines, soundLines, viewSoundChanels;
	float	minSoundElasticity, maxSoundElasticity, minSoundDamping, maxSoundDamping;
	float	minMouseElasticity, maxMouseElasticity, minMouseDamping, maxMouseDamping;
	float	xSolo, ySolo;
	int		rSound, gSound, bSound, aSound;
	int		rSketch, gSketch, bSketch, aSketch;
	int		padX, padY;
	float	Yamp0, Yamp1, Yamp2, Yamp3, Yamp4, Yamp5, Yamp6, Yamp7;
	float	Xfreq0, Xfreq1, Xfreq2, Xfreq3, Xfreq4, Xfreq5, Xfreq6,Xfreq7;
	bool	bSmooth;
	float	ampIn,ampOut, freqIn, freqOut;
	float	ampChan0, ampChan1, ampChan2, ampChan3;		
	float	freqChan0, freqChan1, freqChan2, freqChan3;				
	float	ampInLow,ampOutLow, ampInHigh, ampOutHigh;
	float	freqInLow, freqOutLow,  freqInHigh, freqOutHigh;
	bool	sketchPhrase, drawNow, drawWithMouse, rotate, viewCamera, viewBlob, viewDiff, soundEffectNoto;
	
	//=================
	//	SUPERFORMULA
	ofSuperformula myform;
	bool	superformula;
	
	
	//=================
	//	CAMERA
	bool camera;
	//ofEasyCam cam; // add mouse controls for camera movement
	
	//=================
	//	DESTRUCT
	ofTexture texScreen;
	bool destruct;
	int destructCase;
	
	
	
private:
	int windowWidth, windowHeight; 
	ofxOscReceiver	receiver;
	int				current_msg_string;
	string			msg_strings[NUM_MSG_STRINGS];
	float			timers[NUM_MSG_STRINGS];
	
};

#endif
