#include "ofMain.h"
#include "liveApp.h"
#include "ofAppGlutWindow.h"

int main( ){

	ofAppGlutWindow window;
	
	// play at 1280, 1024
	//ofSetupOpenGL(&window, 1440, 900, OF_WINDOW);
	ofSetupOpenGL(&window, 1440, 900, OF_FULLSCREEN);	
	ofSetWindowPosition(1440,0);
	
	ofRunApp( new liveApp());

}
