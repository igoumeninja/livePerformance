#include "ofMain.h"
#include "liveApp.h"
#include "ofAppGlutWindow.h"

int main( ){

	ofAppGlutWindow window;
	
	// play at 1280, 1024
	ofSetupOpenGL(&window, 1280, 1054, OF_WINDOW);
	ofSetWindowPosition(1440,0);
	
	ofRunApp( new liveApp());

}
