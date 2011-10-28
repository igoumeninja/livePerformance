#include "ofMain.h"
#include "liveApp.h"
#include "ofAppGlutWindow.h"

//liveApp *myApp;
int main( ){

	//1280, 720  640,480,  1920,1080
    ofAppGlutWindow window;
	//ofSetupOpenGL(&window, 640,480, OF_WINDOW);			// <-------- setup the GL context  1920x1080
	//ofSetupOpenGL(&window, 1000, 500, OF_WINDOW);			// <-------- setup the GL context  1920x1080
	//ofSetupOpenGL(&window, 1280, 1024, OF_WINDOW);	// LG Monitor, hp vp6321
	//ofSetupOpenGL(&window, 1280, 1024, OF_GAME_MODE);	// LG Monitor, hp vp6321	
//	ofSetupOpenGL(&window, 1680, 1050, OF_WINDOW);	// SAMSUNG ANAKTORA
//	ofSetupOpenGL(&window, 1600, 1200, OF_WINDOW);	// SAMSUNG ANAKTORA	
	//ofSetupOpenGL(&window, 3840, 1024, OF_WINDOW);	// Matrox 3 proyectores3840, 1024
	//ofSetupOpenGL(&window, 3840, 1024, OF_FULLSCREEN);	// Matrox 3 proyectores3840, 1024	//ofSetupOpenGL(&window, 300, 300, OF_WINDOW);			// <-------- setup the GL context  1920x1080	//ofSetupOpenGL(&window, 1920, 1080, OF_WINDOW);			// projector
	//ofSetupOpenGL(&window, 2400, 600, OF_WINDOW);	// Matrox 3 proyectores 2400, 600
	//ofSetupOpenGL(&window, 1600, 1200, OF_WINDOW);			// projector
	// this kicks off the running of my app	
	// can be OF_WINDOW or OF_FULLSCREEN
	// pass in width and height too:
	//glutHideWindow();
	ofSetWindowPosition(1440,0);
	//ofSetWindowPosition(-1280,0);
	//ofSetFullscreen(true);
	
//	myApp = new liveApp;
//	ofRunApp(myApp);
	
	ofRunApp( new liveApp());

}
