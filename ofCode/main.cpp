#include "ofMain.h"
#include "liveApp.h"
#include "ofAppGlutWindow.h"

int main( ){

	ofAppGlutWindow window;
	
	// play at 1280, 1024
	//ofSetupOpenGL(&window, 1440, 900, OF_WINDOW);
	//ofSetupOpenGL(&window, 1440, 900, OF_FULLSCREEN);	
	
	//ofSetupOpenGL(&window, 1280, 1024, OF_FULLSCREEN);			
	//ofSetupOpenGL(&window, 1024, 768, OF_FULLSCREEN);			
	ofSetupOpenGL(&window, 1280, 1024, OF_WINDOW);				
	//ofSetWindowPosition(1440,0);
	
	ofRunApp( new liveApp());

}
