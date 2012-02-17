/*
 
 Preapering AudioVisual Performance at Lyon-Grenoble
 
 Aris Bezas 
 Igoumeninja 28-10-2011
 
*/
 
#include "liveApp.h"

void liveApp::setup()	{
	{

		ofBackground(0,0,0);

		int windowMode = ofGetWindowMode();  
		if(windowMode == OF_FULLSCREEN){  
			this->windowWidth = ofGetScreenWidth();  
			this->windowHeight = ofGetScreenHeight();  
		}  
		else if(windowMode == OF_WINDOW){  
			this->windowWidth = ofGetScreenWidth();  
			this->windowHeight = ofGetScreenHeight();  
		} 
		
		//ofSetCircleResolution(200);
		texScreen.allocate(ofGetWidth(), ofGetHeight(),GL_RGB);// GL_RGBA); 
		ofSetBackgroundAuto(false);
		ofEnableSmoothing();
		ofEnableAlphaBlending(); 
		//glutSetCursor(GLUT_CURSOR_CYCLE);  // change cursor icon (http://pyopengl.sourceforge.net/documentation/manual/glutSetCursor.3GLUT.html)
		cout << "recieving OSC at port: 12345 " << PORTlisten << "\n";
		receiver.setup( PORTlisten );
		current_msg_string = 0;
				
		ofSetWindowTitle("hbut: High But Under Control");
		ofSetFrameRate(60); // if vertical sync is off, we can go a bit fast... this caps the framerate at 60fps.
		ofSetVerticalSync(false);
		
		
	}	// Setup
	{
	memAlloc = true;
	float cellX = 0, cellY = 0;
	for (int i = 0; i < MAX_MATRIX; i++)	{
		for (int j = 0; j < MAX_MATRIX; j++)	{			
			gridX[i][j] = cellX; 
			gridY[i][j] = cellY;
			cellX = cellX + 20;
		}
		cellX = 0;
		cellY = cellY + 20;
	}
	float cellX3d = 0, cellY3d = 0, cellZ3d = 0;
	for (int i = 0; i < MAX_MATRIX; i++)	{
		for (int j = 0; j < MAX_MATRIX; j++)	{			
			for (int k = 0; k < MAX_MATRIX; k++)	{					
				gridX3d[i][j][k] = cellX3d; 
				gridY3d[i][j][k] = cellY3d;
				gridZ3d[i][j][k] = cellZ3d;				
				cellX3d = cellX3d + 40;
			}
			cellX3d = 0;
			cellY3d = cellY3d + 40;
		}
		//cellX3d = 0;
		cellY3d = 0;		
		cellZ3d = cellZ3d + 40;
	}
	}	// memAlloc
	{
		//video
		videoX=videoY=0;
		
		//destruct
		destruct = 0;
		destructCase = 1;
		
		//camera
		camera=false;
		
		//superformula
		superformula = false;
		
		//sketch
		aSound = 5;
		rSketch = gSketch = bSketch = aSketch = 255;
		 		
		// Tree
		dotSize = 15;
		angleOffsetA = (1.5*3.14)/180; // Convert 1.5 degrees to radians
		angleOffsetB = (50*3.14)/180;  // Convert 50 degrees to radians
		
		//background
		aBack = 0;
		view_fillBackground = 1;
		
		//sound
		viewSoundChanels = 1;
		
		drawWithMouse = 0;
		numMouseSketches = 99;
		minMouseElasticity = 0.0;
		maxMouseElasticity = 0.99;
		minMouseDamping = 0.0;
		maxMouseDamping = 0.99;
		
		numSoundSketches = 99;
		minSoundElasticity = 0.0;
		maxSoundElasticity = 0.99;
		minSoundDamping = 0.0;
		maxSoundDamping = 0.99;
		
		mouseLines = 1;
		soundLines = 0;
		
		ampInLow =0.0;
		ampInHigh = 0.15;
		freqInLow = 20;
		freqInHigh = 4000;
		
		feedbackView = 0;
		feedbackSpeedX = 0;
		feedbackSpeedY = 0;
		timeLine = 0;
		viewRotate = 0;				
			
		}	// Initial Values
	{
		// Imagenes
		// GROUP 1
		string imageDir = "/Users/ari/Media/images/paintings/lyon/";
		
		for (int i = 0; i < 61; i++)	{
			string number;
			std::string s;
			std::stringstream out;
			out << i;
			s = out.str();
			imageDir += s;
			imageDir += ".jpg";
			cout << imageDir << endl;
			image[i].loadImage(imageDir);
			imageDir = "/Users/ari/Media/images/paintings/lyon/";
		}

		// GROUP 2
		imageDir = "/Users/ari/Media/images/maps/google/grenoble/";
		
		for (int i = 0; i < 10; i++)	{
			string number;
			std::string s;
			std::stringstream out;
			out << i;
			s = out.str();
			imageDir += s;
			imageDir += ".png";
			cout << imageDir << endl;
			image[100+i].loadImage(imageDir);
			imageDir = "/Users/ari/Media/images/maps/google/grenoble/";
		}
		
		// Video
		myVideo = new ofVideoPlayer();
		playVideo = 0;
		rVideo = gVideo = bVideo = aVideo = 255;
		
		//===================================
		
		// Fonts
		myFont11.loadFont("/Users/ari/Media/fonts/favorites/Batang.ttf", 11, true, true, true);
		myFont350.loadFont("/Users/ari/Media/fonts/favorites/Batang.ttf", 350, true, true, true);		
	}	// data (images, fonts, video ...)
 	{
		for (int i = 0; i < MAX_SKETCHES; i++){
			sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));	//to 1o stoixeio einai to id 0:
			sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping));	//id:1 => mouse init(int sketchID, float elast, float aposv)
		}
		
		sketchPhrase = false;

	}	// sKeTch
	{
		playSpectro = 0;
		rSound = gSound = bSound = 255; aSound = 25;
		textureRed = textureGreen = textureBlue = textureAlpha = 255;
		reverseEllipse = ofGetWidth();	reverseTexture = -1;
		mirrorMode = 9;
		spectroRed = spectroGreen = spectroBlue = 1;	
		
		
		soundEffectNoto = false;
	}	// Sound Interaction
	{
        // this number describes how many bins are used
        // on my machine, 2 is the ideal number (2^2 = 4x4 pixel bins)
        // if this number is too high, binning is not effective
        // because the screen is not subdivided enough. if
        // it's too low, the bins take up so much memory as to
        // become inefficient.
        int binPower = 4;

        particleSystem.setup(ofGetWidth(), ofGetHeight(), binPower);
		/*
        kParticles = 2;  // change that to 5 for MacBook Pro
        float padding = 0;
        float maxVelocity = .5;
        for(int i = 0; i < kParticles * 1024; i++) {
                float x = ofRandom(padding, ofGetWidth() - padding);
                float y = ofRandom(padding, ofGetHeight() - padding);
                float xv = ofRandom(-maxVelocity, maxVelocity);
                float yv = ofRandom(-maxVelocity, maxVelocity);
                Particle particle(x, y, xv, yv);
                particleSystem.add(particle);
        }
		 */
		rConColor=gConColor=bConColor=rDotColor=gDotColor=bDotColor=255;
        ofBackground(0, 0, 0);

        timeStep = 1;
        lineOpacity = 0;
        pointOpacity = 255;
        isMousePressed = false;
        slowMotion = false;
        particleNeighborhood = 15;
        particleRepulsion = 1;
        centerAttraction = .01;
		forceRadius = 100;
		forceScale = 10;
		bounceXstart = 0; 
		bounceYstart = 0;
		bounceXend = ofGetWidth(); 
		bounceYend = ofGetHeight();
		
		viewParticles = false;
		
		
		}	// Particles
	{
//		w = ofGetWidth();
//		h = ofGetHeight();
		w = 1280;
		h = 1024;
		
		mirrowEffect4 = false;
		texMirrow.allocate(w,h, GL_RGB);

		noiseEffect = false;
		texGray.allocate(w,h,GL_LUMINANCE);
		grayPixels			= new unsigned char [w*h];
		// gray pixels, set them randomly
		for (int i = 0; i < w*h; i++){
			grayPixels[i] = (unsigned char)(ofRandomuf() * 255);
			
		}
		texGray.loadData(grayPixels, w,h, GL_LUMINANCE); 
	}	// Texture effects
		
}
void liveApp::update()	{ 
	for ( int i=0; i<NUM_MSG_STRINGS; i++ )	{
		if ( timers[i] < ofGetElapsedTimef() )
			msg_strings[i] = "";
	}	
	while( receiver.hasWaitingMessages() )
	{
	ofxOscMessage m;
	receiver.getNextMessage( &m ); 
	if ( m.getAddress() == "playSpectro")			{
		if (m.getArgAsString(0) == "activate") {
			playSpectro = m.getArgAsInt32(1);
			cout << playSpectro << endl;
		} else if (m.getArgAsString(0) == "mirrorMode") {
			mirrorMode = m.getArgAsInt32(1);
		}
	}
	if ( m.getAddress() == "rotate" )				{
		ofBeginShape();		
		ofRotateX(m.getArgAsInt32(0));
		ofRotateY(m.getArgAsInt32(1));
		ofRotateZ(m.getArgAsInt32(2));										
		ofEndShape();					
	}	//	rotate
	if ( m.getAddress() == "superformula" )			{
		if (m.getArgAsString(0) == "activate") {
			if (m.getArgAsInt32(1) == 1) {
				superformula = true;
			}	else {
				superformula = false;
			}
		}
		if (m.getArgAsString(0) == "mi") {
			myform.mi = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "n1") {
			myform.n1 = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "n2") {
			myform.n2 = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "n3") { 
			myform.n3 = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "epi") {
			myform.epi = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "step") {
			myform.step = m.getArgAsFloat(1);
		}
		if (m.getArgAsString(0) == "num") {
			myform.num = m.getArgAsInt32(1);
		}		
		if (m.getArgAsString(0) == "alpha") {
			myform.alpha = m.getArgAsFloat(1);
			cout << m.getArgAsString(0);
		}
		if (m.getArgAsString(0) == "bita") {
			myform.bita = m.getArgAsFloat(1);
		}
	}	//	superformula		
	if ( m.getAddress() == "img" )					{
		
		//cout << m.getNumArgs() << endl;
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE			
		switch (m.getNumArgs())	{
			case 1:
				ofSetHexColor(0xFFFFFF);				
				image[m.getArgAsInt32(0)].draw(0,0);
				break;
			case 3:
				ofSetHexColor(0xFFFFFF);				
				image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), m.getArgAsInt32(2));
				break;
				
			case 4:
				if (image[m.getArgAsInt32(0)].width/image[m.getArgAsInt32(0)].height > 1.25)	{
					
					//image[id].draw(x,y,width,height);	
					
				}	else	{
					
				}
				
				break;
			case 5:
				//ofFill();
				ofSetHexColor(0xFFFFFF);				
				image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), m.getArgAsInt32(2),m.getArgAsInt32(3),m.getArgAsInt32(4));
				break;
			case 8:
				ofNoFill();
				ofSetHexColor(0xFFFFFF);		
				ofBeginShape();		
				ofRotateX(m.getArgAsInt32(5));
				ofRotateY(m.getArgAsInt32(6));
				ofRotateZ(m.getArgAsInt32(7));										
				image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), m.getArgAsInt32(2),m.getArgAsInt32(3),m.getArgAsInt32(4));
				ofEndShape();
				break;
			case 11:
				//cout << m.getNumArgs() << endl;
				ofNoFill();
				ofSetHexColor(0xFFFFFF);		
				ofBeginShape();		
				ofTranslate(m.getArgAsInt32(5),m.getArgAsInt32(6),m.getArgAsInt32(7));
				ofRotateX(m.getArgAsInt32(8));
				ofRotateY(m.getArgAsInt32(9));
				ofRotateZ(m.getArgAsInt32(10));										
				image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), m.getArgAsInt32(2),m.getArgAsInt32(3),m.getArgAsInt32(4));					
				ofEndShape();
				break;
			case 14:
				//cout << m.getNumArgs() << endl;
				ofNoFill();
				ofSetHexColor(0xFFFFFF);		
				ofBeginShape();		
				ofTranslate(m.getArgAsInt32(5),m.getArgAsInt32(6),m.getArgAsInt32(7));
				ofScale(m.getArgAsInt32(8),m.getArgAsInt32(9),m.getArgAsInt32(10));										
				ofRotateX(m.getArgAsInt32(11));
				ofRotateY(m.getArgAsInt32(12));
				ofRotateZ(m.getArgAsInt32(13));										
				image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), m.getArgAsInt32(2),m.getArgAsInt32(3),m.getArgAsInt32(4));
				ofEndShape();
				break;
				
		}
	}	//  images	
	if ( m.getAddress() == "video")					{
		if (m.getArgAsString(0) == "playVideo")		{
			switch (m.getNumArgs()) {
				case 2:
					playVideo = m.getArgAsInt32(1);
					break;
				case 4:
					playVideo = m.getArgAsInt32(1);
					videoW = m.getArgAsInt32(2);
					videoH = m.getArgAsInt32(3);					
					break;
				default:
					break;
			}


		}
		else if (m.getArgAsString(0) == "setSpeed") myVideo->setSpeed(m.getArgAsFloat(1));
		else if (m.getArgAsString(0) == "rVideo") rVideo = m.getArgAsInt32(1);			
		else if (m.getArgAsString(0) == "gVideo") gVideo = m.getArgAsInt32(1);			
		else if (m.getArgAsString(0) == "bVideo") bVideo = m.getArgAsInt32(1);			
		else if (m.getArgAsString(0) == "aVideo") aVideo = m.getArgAsInt32(1);						
		else if (m.getArgAsString(0) == "colorVideo") {
			rVideo = m.getArgAsInt32(1);
			gVideo = m.getArgAsInt32(2);
			bVideo = m.getArgAsInt32(3);
			aVideo = m.getArgAsInt32(4);
		}
		else if (m.getArgAsString(0) == "deleteVideo")		{
			myVideo->stop();
			myVideo->close();
			delete myVideo;
			myVideo = 0;
			//myVideo.closeMovie();//();myVideo.loadMovie("/Users/ari/Media/videos/maps/smallVideo.mov");
		}
		else if (m.getArgAsString(0) == "reloadVideo")		{
			myVideo = new ofVideoPlayer();
			myVideo->loadMovie("/Users/ari/Media/videos/maps/grenoble.mov");
			videoW=myVideo->width;
			videoH=myVideo->height;

			//myVideo->setSpeed(4);
			myVideo->play();
			//myVideo.loadMovie("/Users/ari/Media/videos/maps/grenobleY.mov");
			
		}
	}	//	video
	if ( m.getAddress() == "particle" )				{
		if (m.getArgAsString( 0 ) == "activate")				{
				if (m.getArgAsInt32(1) == 1)	viewParticles = true;	
				else if (m.getArgAsInt32(1) == 0)	viewParticles = false;	
		}
		else if (m.getArgAsString( 0 ) == "lineOpacity")			lineOpacity = m.getArgAsInt32( 1 );	
		else if (m.getArgAsString( 0 ) == "particleNeighborhood")	particleNeighborhood = m.getArgAsInt32( 1 );
		else if (m.getArgAsString( 0 ) == "forceRadius")			forceRadius = m.getArgAsInt32( 1 );		
		else if (m.getArgAsString( 0 ) == "forceScale")				forceScale = m.getArgAsInt32( 1 );		
		else if (m.getArgAsString( 0 ) == "iPadPush")				{
			if (m.getArgAsInt32(1) == 1)	iPadPush = true;	
			else if (m.getArgAsInt32(1) == 0)	iPadPush = false;	
		}
		else if (m.getArgAsString( 0 ) == "pushParticles")			{
			if (m.getArgAsInt32(1) == 1)	pushParticles = true;	
			else if (m.getArgAsInt32(1) == 0)	pushParticles = false;	
		}
		else if (m.getArgAsString( 0 ) == "push")					{
			pushX = m.getArgAsFloat( 1 );
			pushY = m.getArgAsFloat( 2 );
		}
		else if	(m.getArgAsString( 0 ) == "add")					{
			Particle particle(m.getArgAsInt32(1), m.getArgAsInt32(2), m.getArgAsFloat(3), m.getArgAsFloat(4));
			particleSystem.add(particle);
		}
		else if	(m.getArgAsString( 0 ) == "dotColor")				{
			switch (m.getNumArgs())	{
				case 2:
					aDotColor = m.getArgAsInt32( 1 );
					cout << aDotColor;
					break;				
				case 4:
					rDotColor = m.getArgAsInt32( 1 );
					gDotColor = m.getArgAsInt32( 2 );
					bDotColor = m.getArgAsInt32( 3 );
					break;
				case 5:
					rDotColor = m.getArgAsInt32( 1 );
					gDotColor = m.getArgAsInt32( 2 );
					bDotColor = m.getArgAsInt32( 3 );
					aDotColor = m.getArgAsInt32( 4 );
				break;
			}
		}
		else if	(m.getArgAsString( 0 ) == "conColor")				{
			switch (m.getNumArgs())	{
				case 2:
					aConColor = m.getArgAsInt32( 1 );
					//cout << aConColor;
					break;				
				case 4:
					rConColor = m.getArgAsInt32( 1 );
					gConColor = m.getArgAsInt32( 2 );
					bConColor = m.getArgAsInt32( 3 );
					break;
				case 5:
					rConColor = m.getArgAsInt32( 1 );
					gConColor = m.getArgAsInt32( 2 );
					bConColor = m.getArgAsInt32( 3 );
					aConColor = m.getArgAsInt32( 4 );
					break;
			}
		}			
		else if	(m.getArgAsString( 0 ) == "bounce")					{
			bounceXstart = m.getArgAsInt32(1);
			bounceYstart = m.getArgAsInt32(2); 
			bounceXend = m.getArgAsInt32(3);
			bounceYend = m.getArgAsInt32(4);
		}			
	}	//  particles
	if ( m.getAddress() == "feedback" )				{
		if (m.getArgAsString( 0 ) == "activate")	{
			feedbackView = m.getArgAsInt32( 1 );
		} else if (m.getArgAsString( 0 ) == "speedXY")		{
			feedbackSpeedY = m.getArgAsFloat( 1 );
			feedbackSpeedX = m.getArgAsFloat( 2 );
			//cout << feedbackSpeedY << endl;
		}
	}	//	feedback		
	if ( m.getAddress() == "background" )			{
		switch (m.getNumArgs())	{
			case 1:
				aBack = m.getArgAsInt32( 0 );
				//cout << aBack;
				break;				
			case 3:
				rBack = m.getArgAsInt32( 0 );
				gBack = m.getArgAsInt32( 1 );
				bBack = m.getArgAsInt32( 2 );
				ofBackground(rBack,gBack,bBack);
				break;				
			case 4:
				rBack = m.getArgAsInt32( 0 );
				gBack = m.getArgAsInt32( 1 );
				bBack = m.getArgAsInt32( 2 );
				aBack = m.getArgAsInt32( 3 );	
				break;
		}
		//ofBackground(rBack, gBack, bBack);
	}	//	background						
	if ( m.getAddress() == "rect" )					{
		switch (m.getNumArgs())	{
			case 4:
				ofFill();
				ofSetColor(0, 0, 0);
				ofRect(m.getArgAsInt32(0), m.getArgAsInt32(1), m.getArgAsInt32(2), m.getArgAsInt32(3));
				break;
		}
	}	//	rect
	if ( m.getAddress() == "writeString" )			{
		if (m.getArgAsString(0) == "bigCenter") {
			//OF.writeString("bigCenter", "~sadfh",~width/2,~height/2,255,0,0,255); 
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
			ofFill();
			ofSetColor(m.getArgAsInt32( 4 ),m.getArgAsInt32( 5 ),m.getArgAsInt32( 6 ),m.getArgAsInt32( 7 ));
			ofPushMatrix();
			ofTranslate(m.getArgAsInt32( 2 ), m.getArgAsInt32(3), 0);
			myFont350.drawString(m.getArgAsString( 1 ), 0, 0);		
			ofPopMatrix();
			
		} else	{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
			ofFill();
			ofSetColor(m.getArgAsInt32( 3 ),m.getArgAsInt32( 4 ),m.getArgAsInt32( 5 ),m.getArgAsInt32( 6 ));
			ofPushMatrix();
			ofTranslate(m.getArgAsInt32( 1 ), m.getArgAsInt32(2), 0);
			myFont11.drawString(m.getArgAsString( 0 ), 0, 0);		
			ofPopMatrix();
		}		
		
	}	//	Typography
	if ( m.getAddress() == "effect" )				{
		if ( m.getArgAsString(0) == "tree" )		{
			glTranslatef(ofGetWidth()/2,ofGetHeight(),0);	
			seed1(dotSize, (270*3.1415926)/180, 0, 0);
		}	else if ( m.getArgAsString(0) == "noiseEffect" ) {
				if (m.getArgAsInt32(1) == 1) {
					noiseEffect = true;
				} else {
					noiseEffect = false;
				}
		}	else if ( m.getArgAsString(0) == "destruct" ) {
			if (m.getArgAsString(1)	== "activate") {
				if (m.getArgAsInt32(2) == 1) {
					destruct = true;
				} else {
					destruct = false;
				}
			} else if (m.getArgAsString(1) == "case") {
				destructCase = m.getArgAsInt32(2);
			} else if (m.getArgAsString(1) == "phrase1") {
				//cout << "ok" << endl;
				for (int i = 0; i<10; i++) {
					ofSetColor(255,255,255,255);
					texScreen.loadScreenData(0,0,ofGetWidth(),ofGetHeight());
					texScreen.draw(int(ofRandom(0,1400)),int(ofRandom(0,1400)),500,500);
				}
			}
		}	else if ( m.getArgAsString(0) == "mirror" ) {
			switch (m.getNumArgs()) {
				case 2:
					if (m.getArgAsInt32(1) == 1) {
						mirrorEffect = true;
					} else {
						mirrorEffect = false;
					}					
					break;
				case 3:
					mirrorEffectCase = m.getArgAsInt32(2);
					break;

				default:
					break;
			}
		}	else {
			cout << "Write a new effect";
		}

	}	//	effects
	if ( m.getAddress() == "rgb" )					{
		if ( m.getArgAsString( 0 ) == "sketch")	{ rSketch = m.getArgAsInt32( 1 );	gSketch = m.getArgAsInt32( 2 );	bSketch = m.getArgAsInt32( 3 );	aSketch = m.getArgAsInt32( 4 );	}
		
		else if ( m.getArgAsString( 0 ) == "sound")	{ rSound = m.getArgAsInt32( 1 );	gSound = m.getArgAsInt32( 2 );	bSound = m.getArgAsInt32( 3 );	aSound = m.getArgAsInt32( 4 );	}

	}	//	rgb directamente		
	if ( m.getAddress() == "interactWithSound" )	{
		if ( m.getArgAsString(0) == "activate" )	{
			if (m.getArgAsInt32(1) == 1)	viewSoundChanels = true;	
			else if (m.getArgAsInt32(1) == 0)	viewSoundChanels = false;	
		}
		else if ( m.getArgAsString(0) == "deactivate" )	viewSoundChanels = false;					
		else if ( m.getArgAsString(0) == "glBeginType" )	soundLines = m.getArgAsInt32(1);
		else if ( m.getArgAsString(0) == "numSoundSketches" )	numSoundSketches = m.getArgAsInt32(1);				
		else if ( m.getArgAsString(0) == "minSoundElasticity" )	{
			minSoundElasticity = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000+numSoundSketches; i++ ) {
				sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));
			}
		}				
		else if ( m.getArgAsString(0) == "maxSoundElasticity" )	{
			maxSoundElasticity = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000+numSoundSketches; i++ ) {
				sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));
			}
		}				
		else if ( m.getArgAsString(0) == "minSoundDamping" )	{
			minSoundDamping = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000+numSoundSketches; i++ ) {
				sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));
			}
		}				
		else if ( m.getArgAsString(0) == "maxSoundDamping" )	{
			maxSoundDamping = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000+numSoundSketches; i++ ) {
				sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));
			}
		}				
		else if ( m.getArgAsString(0) == "maxAmpIn" )	ampInHigh =  m.getArgAsFloat(1);	
		else if ( m.getArgAsString(0) == "maxFreqIn" )	freqInHigh =  m.getArgAsFloat(1);					
	}	//	sound interaction
	if ( m.getAddress() == "interactWithSketch" )	{
		if ( m.getArgAsString(0) == "activate" )	{
			if (m.getArgAsInt32(1) == 1)	drawWithMouse = true;	
			else if (m.getArgAsInt32(1) == 0)	drawWithMouse = false;	
		}
		else if ( m.getArgAsString(0) == "padSketchXY" )	{	padX = m.getArgAsInt32(1);	padY = m.getArgAsInt32(2);}
		else if ( m.getArgAsString(0) == "mouseLines" )		mouseLines = m.getArgAsInt32(1);
		else if ( m.getArgAsString(0) == "numMouseSketches" )	numMouseSketches = m.getArgAsFloat(1);			
		else if ( m.getArgAsString(0) == "minMouseElasticity" )	{
			minMouseElasticity = m.getArgAsFloat( 1 );			
			for( int i=1000; i<1000 + numMouseSketches; i++ ) {
				sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping)); //id:1 => mouse init(int sketchID, float elast, float aposv)
			}		
		}		
		else if ( m.getArgAsString(0) == "maxMouseElasticity" )	{
			maxMouseElasticity = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000 + numMouseSketches; i++ ) {
				sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping)); //id:1 => mouse init(int sketchID, float elast, float aposv)
			}		
		}		
		else if ( m.getArgAsString(0) == "minMouseDamping" )	{
			minMouseDamping = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000 + numMouseSketches; i++ ) {
				sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping)); //id:1 => mouse init(int sketchID, float elast, float aposv)
			}		
		}		
		else if ( m.getArgAsString(0) == "maxMouseDamping" )	{
			maxMouseDamping = m.getArgAsFloat( 1 );
			for( int i=1000; i<1000 + numMouseSketches; i++ ) {
				sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping)); //id:1 => mouse init(int sketchID, float elast, float aposv)
			}		
		}
	}	//  Mouse Interaction		
	if	(viewSoundChanels)							{			
		if ( m.getAddress() == "mlab" )	{					// Machine Listening
			if		(m.getArgAsString(0) == "amp" )			{	ampChan0 = m.getArgAsFloat( 1 );		} 
			else if	(m.getArgAsString(0) == "freq" )		{	freqChan0 = m.getArgAsFloat( 1 );} 
			else if	(m.getArgAsString(0) == "loudness" )	{	
				loudness = m.getArgAsFloat( 1 );
				glColor4f(1,1,1,1);
				//ofEllipse(ofGetWidth()/2, ofMap(loudness, 0, 30, 512, 0),4,2);	

				//printf(" %f \n", m.getArgAsFloat( 1 ));		
			
			} 
			else if	(m.getArgAsString(0) == "onset" )		{	printf(" onset !!! \n");		} 
			else if	(m.getArgAsString(0) == "specCentroid" ){	
				specCentroid = m.getArgAsFloat( 1 );
				glColor3f(1,1,1);
				ofEllipse(ofGetWidth()/2, ofMap(specCentroid, 400, 6000, 512, 0),2,2);
				//printf(" %f \n", m.getArgAsFloat( 1 ));		
			} 
			else if	(m.getArgAsString(0) == "specFlatness" ){	
				printf(" %f \n", m.getArgAsFloat( 1 ));						
			} 
			else if	(m.getArgAsString(0) == "fftData" )		{
				switch ( mirrorMode )
				{
					case 0:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],spectroGreen*data[i],spectroBlue*data[i]);
							ofEllipse(reverseEllipse,512-i,2,2);
							//glColor3f(0,0,0);
							//ofEllipse(reverseEllipse,512+i,2,2);				
						}
						texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);
						texScreen.draw(reverseTexture,0,ofGetWidth(), ofGetHeight());
						
						break;
					case 1:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],spectroGreen*data[i],spectroBlue*data[i]);
							ofEllipse(reverseEllipse,512-i,2,2);
							ofEllipse(reverseEllipse,512+i,2,2);				
						}
						texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);
						texScreen.draw(reverseTexture,0,ofGetWidth(), ofGetHeight());
						break;
					case 2:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],spectroGreen*data[i],spectroBlue*data[i]);
							ofEllipse(0,512-i,2,2);
							ofEllipse(0,512+i,2,2);				
						}
						texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);
						texScreen.draw(reverseTexture,0,ofGetWidth(), ofGetHeight());
						break;
						
					case 3:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],spectroGreen*data[i],spectroBlue*data[i]);
							ofEllipse(ofGetWidth()/2,512-i,2,2);
							ofEllipse(ofGetWidth()/2,512+i,2,2);						
						}
						texScreen.loadScreenData(0,0,ofGetWidth()/2, ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(-1,0,ofGetWidth()/2, ofGetHeight());					
						texScreen.loadScreenData(ofGetWidth()/2, 0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(ofGetWidth()/2 +1,0,ofGetWidth(), ofGetHeight());					
						break;
					case 4:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],spectroGreen*data[i],spectroBlue*data[i]);
							
							ofEllipse(ofGetWidth()/4,256-i/2,2,2);
							ofEllipse(ofGetWidth()/4,256+i/2,2,2);						
							
							ofEllipse(ofGetWidth()/4,776-i/2,2,2);
							ofEllipse(ofGetWidth()/4,776+i/2,2,2);						
							
							ofEllipse(3*ofGetWidth()/4,256-i/2,2,2);
							ofEllipse(3*ofGetWidth()/4,256+i/2,2,2);						
							
							ofEllipse(3*ofGetWidth()/4,776-i/2,2,2);
							ofEllipse(3*ofGetWidth()/4,776+i/2,2,2);						
							
						}
						ofSetColor(255,255,255,255);
						texScreen.loadScreenData(0,0,ofGetWidth()/4, ofGetHeight());
						texScreen.draw(-1,0);					
						texScreen.loadScreenData(ofGetWidth()/4, 0,ofGetWidth()/4, ofGetHeight());
						texScreen.draw(ofGetWidth()/4 + 1,0);					
						
						texScreen.loadScreenData(ofGetWidth()/4, 0,ofGetWidth()/4, ofGetHeight());
						texScreen.draw(3*ofGetWidth()/4 + 1,0);					
						//
						texScreen.loadScreenData(ofGetWidth()/2, 0, ofGetWidth()/4, ofGetHeight());
						texScreen.draw(ofGetWidth()/2 - 1,0);
						
						break;
					case 5:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],0,0);
							ofEllipse(reverseEllipse,512-i,2,2);
							glColor4f(spectroRed*data[i],spectroGreen*data[i],0,data[i]);
							ofEllipse(reverseEllipse,512-i,2,2);
							
						}
						texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);
						texScreen.draw(reverseTexture,0,ofGetWidth(), ofGetHeight());
						
						break;
						// fire colors
					case 6:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],0,0);
							ofEllipse(reverseEllipse,512-i,2,2);
							ofEllipse(reverseEllipse,512+i,2,2);
							glColor4f(spectroRed*data[i],spectroGreen*data[i],0,data[i]);
							ofEllipse(reverseEllipse,512-i,2,2);
							ofEllipse(reverseEllipse,512+i,2,2);						
							
						}
						texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);
						texScreen.draw(reverseTexture,0,ofGetWidth(), ofGetHeight());
						
						break;
						// fire from middle
					case 7:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],0,0);
							ofEllipse(ofGetWidth()/2,512-i,2,2);
							ofEllipse(ofGetWidth()/2,512+i,2,2);
							glColor4f(spectroRed*data[i],spectroGreen*data[i],0,data[i]);
							ofEllipse(ofGetWidth()/2,512-i,2,2);
							ofEllipse(ofGetWidth()/2,512+i,2,2);						
							
						}
						texScreen.loadScreenData(0,0,ofGetWidth()/2, ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(-1,0,ofGetWidth()/2, ofGetHeight());					
						texScreen.loadScreenData(ofGetWidth()/2, 0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(ofGetWidth()/2 +1,0,ofGetWidth(), ofGetHeight());					
						break;
						
					case 8:
						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(m.getArgAsFloat( i ), 0, 0);
							ofLine(0, 512*m.getArgAsFloat( i ), ofGetWidth(), 512*m.getArgAsFloat( i ));
						}
						break;				
						// 768 HEIGHT
					case 9:


						for (int i=1; i<513; i++)	{
							data[i] = m.getArgAsFloat( i );
							glColor3f(spectroRed*data[i],0,0);
							ofEllipse(ofGetWidth()/2,512-i,2,2);
							ofLine(0, 512+i, ofGetWidth(), 512+i);					
							glColor4f(spectroRed*data[i],spectroGreen*data[i],0,data[i]);
							ofEllipse(ofGetWidth()/2,512-i,2,2);						
						}
						glColor4f(0, 0, 0, 0.1);
						ofRect(0, 512, ofGetWidth(), ofGetHeight());
						texScreen.loadScreenData(0,0,ofGetWidth()/2, ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(-1,0,ofGetWidth()/2, ofGetHeight());					
						texScreen.loadScreenData(ofGetWidth()/2, 0,ofGetWidth(), ofGetHeight());
						ofSetColor(textureRed,textureGreen,textureBlue,textureAlpha);					
						texScreen.draw(ofGetWidth()/2 +1,0,ofGetWidth(), ofGetHeight());					
						
						ofLine(ofGetWidth()/2 - 1, ofMap(ploudness, 0, 30, 512, 0), ofGetWidth()/2, ofMap(loudness, 0, 30, 512, 0));
						ploudness = loudness;
						break;
						
					default:
						cout << "default";
				}
			}
			else if	(m.getArgAsString(0) == "fftColor" )	{
				spectroRed = m.getArgAsInt32(1);
				spectroGreen = m.getArgAsInt32(2);
				spectroBlue = m.getArgAsInt32(3);
			}
		}
	}	//	Sound Interaction amp, freq, loudness, onset, specCentroid, specFlatness, fftData 
	}
}
	
void liveApp::draw()	{
	if (camera)										{
		ofPushMatrix();
		ofTranslate(ofGetWidth() / 2, ofGetHeight() / 2, 0);
		ofRotateX(mouseY);
		ofRotateY(mouseX);
	}	//	camera
	if (view_fillBackground)						{
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
		ofFill();	
		ofSetColor(rBack, gBack, bBack, aBack);
		ofRect(0,0,ofGetWidth(),ofGetHeight());			
	}	//	background	
	if (playVideo)									{
		myVideo->idleMovie();
		ofSetColor(rVideo,gVideo,bVideo,aVideo);		
		//myVideo->draw(0,0,ofGetWidth()/4,ofGetWidth()/4);
		myVideo->draw(videoX,videoY,videoW,videoH);
	}	//  Play Video
	if (viewParticles)								{
        particleSystem.setTimeStep(timeStep);
		ofSetColor(rConColor, gConColor, bConColor, aConColor);	
        particleSystem.setupForces();
        glBegin(GL_LINES);
        for(int i = 0; i < particleSystem.size(); i++) {
                Particle& cur = particleSystem[i];
                particleSystem.addRepulsionForce(cur, particleNeighborhood, particleRepulsion);
                //cur.bounceOffWalls(0, 0, ofGetWidth(), ofGetHeight());				
				cur.bounceOffWalls(bounceXstart, bounceYstart, bounceXend, bounceYend);				
                cur.addDampingForce();
        }
        glEnd();
        //particleSystem.addAttractionForce(ofGetWidth() / 2, ofGetHeight() / 2, ofGetWidth(), centerAttraction);
		if	(viewSoundChanels)	{
			Yamp0 = ofMap(ampChan0, ampInLow, ampInHigh, 0, ofGetHeight());
			Xfreq0 = ofMap(freqChan0, freqInLow, freqInHigh, 0, ofGetWidth());
			particleSystem.addRepulsionForce(Xfreq0, Yamp0, forceRadius, forceScale);											
		}
        if(iPadPush)	{
			particleSystem.addRepulsionForce( ofGetWidth() / 2, ofGetHeight() / 2, forceRadius, forceScale);
		}		
        if(pushParticles)	{
//			for (int j=0;j < pushes)	{
//				particleSystem.addRepulsionForce(pushX[i], pushY[i], forceRadius, forceScale);
//			}
			particleSystem.addRepulsionForce(pushX, pushY, forceRadius, forceScale);
		}		
        if(isMousePressed)	{
			particleSystem.addRepulsionForce( mouseX, mouseY, forceRadius, forceScale);
		}
		particleSystem.update();

        ofSetColor(rDotColor, gDotColor, bDotColor, aDotColor);
        particleSystem.draw();
	}	//  Particles
	if (sketchPhrase)								{
		
		for( int i=0; i<100; i++ ) {
			
			sketch[i].drawMouse(i, 100+ i, 0, 255,255,255,255, 0);	
		}		
	}	//	Sketch
	if (drawWithMouse)								{
		//cout << padY;
		for( int i=0; i<numMouseSketches; i++ ) {
			sketch[i].drawMouse(padX, padY, 0, rSketch, gSketch, bSketch, aSketch/3, mouseLines);	
			
		}
		//		for( int i=2000; i<2000 + numMouseSketches; i++ ) {
		//			sketch[i].drawMouse(padX+400, padY, 0, r7, g7, b7, a7/3, mouseLines);	
		//		}	
	}	//	sketch with mouse	
	if (viewSoundChanels)							{
		Yamp0 = ofMap(ampChan0, ampInLow, ampInHigh, 0, ofGetHeight());
		Xfreq0 = ofMap(freqChan0, freqInLow, freqInHigh, 0, ofGetWidth());
		for( int i=1000; i<1000+numSoundSketches; i++ ) {
			sketch[i].drawSound(Xfreq0, Yamp0, 0, rSound, gSound, bSound, aSound, soundLines);	
		}
	}  	//  viewSoundChanels 
	if (soundEffectNoto)							{
		Yamp0 = ofMap(ampChan0, ampInLow, ampInHigh, 0, ofGetHeight());
		Xfreq0 = ofMap(freqChan0, freqInLow, freqInHigh, 0, ofGetWidth());
		for( int i=1000; i<1000+numSoundSketches; i++ ) {
			sketch[i].drawSound(Xfreq0, Yamp0, 0, rSound, gSound, bSound, aSound, soundLines);	
		}
	}  	//  viewSoundChanels 
	if (feedbackView)								{
		texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
		glPushMatrix();
		ofSetHexColor(0xffffff);
		glTranslatef(feedbackSpeedX,feedbackSpeedY,0);
		texScreen.draw(0,0,ofGetWidth(), ofGetHeight());
		glPopMatrix();
	}	//	feedback
	if (superformula)								{
		myform.draw(ofGetWidth()/2,ofGetHeight()/2);
	}	//	superformula
	if (camera)										{
		ofPopMatrix();
	}	//	camera
	if (destruct)									{
		switch (destructCase) {
			case 0:
				for (int i = 0; i < 1; i++) {
					ofSetColor(255,255,255,255);
					texScreen.loadScreenData(0,0,ofGetWidth(),ofGetHeight());
					texScreen.draw(int(ofRandom(0,1400)),int(ofRandom(0,1400)),100,100);
				} 				
				break;
			case 1:
				texScreen.loadScreenData(0,0,ofGetWidth(),ofGetHeight());
				ofPushMatrix();
				ofTranslate(0, ofGetHeight()/2, 0);				
				ofRotateX(ofGetFrameNum());				
				ofSetColor(255,255,255,255);
				texScreen.draw(0,0,ofGetWidth(),ofGetHeight());
				ofPopMatrix();
				break;
			case 2:
				for (int i = 0; i < 1; i++) {
					ofSetColor(255,255,255,255);
					texScreen.loadScreenData(0,0,ofGetWidth(),ofGetHeight());
					texScreen.draw(int(ofRandom(0,1400)),int(ofRandom(0,1400)),500,500);
				} 				
				break;
			case 3:		// rotate feedback
				for (int i = 0; i < 1; i++) {
					glPushMatrix();
					ofSetHexColor(0xffffff);
					glRotatef(5, 0, 1, 0);
					texScreen.draw(0,0,ofGetWidth(), ofGetHeight());
					glPopMatrix();
					
				} 				
				break;
				
			default:
				break;
		}
	}	//	destruct
	if (noiseEffect)								{
		ofBackground(255,255,255);
		
		for (int i = 0; i < w; i = i+ 1){
			for (int j = 0; j < h; j=j+1){
				grayPixels[j*w+i] = (unsigned char)(ofRandomuf() * 255);
			}
		}
		texGray.loadData(grayPixels, w, h, GL_LUMINANCE); 
		ofSetHexColor(0xffffff);
		texGray.draw(0, 0, w, h);
	}	//  Noise Effect
	if (mirrorEffect)								{
		switch (mirrorEffectCase) {
			case 0:
				texMirrow.loadScreenData(0, 0,	w/2, h);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/2,h);
				glPopMatrix();
				break;
			case 1:
				texMirrow.loadScreenData(0, 0,	w/2, h/2);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,h,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				break;
			case 2:
				texMirrow.loadScreenData(0, 0,	w/4, h/4);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/2,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,h/2,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				texMirrow.loadScreenData(0, 0,	w/2, h/2);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,h,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				break;
			case 3:
				texMirrow.loadScreenData(0, 0,	w/8, h/8);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/4,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/8,h/8);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/4,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/8,h/8);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/4,h/4,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/8,h/8);
				glPopMatrix();
				
				texMirrow.loadScreenData(0, 0,	w/4, h/4);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/2,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,h/2,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				texMirrow.loadScreenData(0, 0,	w/2, h/2);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,h,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				break;
			case 4:
				texMirrow.loadScreenData(0, 0,	w/16, h/16);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/8,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/8,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/8,h/8,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				texMirrow.loadScreenData(0, 0,	w/16, h/16);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/8,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/8,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/8,h/8,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/16,h/16);
				glPopMatrix();
				
				texMirrow.loadScreenData(0, 0,	w/4, h/4);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h/2,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w/2,h/2,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/4,h/4);
				glPopMatrix();
				
				texMirrow.loadScreenData(0, 0,	w/2, h/2);
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,0,0);
				glRotatef(180, 0, 1.0f, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(0,h,0);
				glRotatef(180, 1.0f, 0, 0);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				
				glPushMatrix();
				ofSetHexColor(0xffffff);
				glTranslatef(w,h,0);
				glRotatef(180, 0,0,1.0f);
				texMirrow.draw(0,0,w/2,h/2);
				glPopMatrix();
				break;
				
			default:
				break;
		}
	}	//	Mirrow Effect			
}
void liveApp::seed1(float dotSize, float angle, float x, float y)	{
  
  if (dotSize > 1.0) {
    
    // Create a random numbers between 0 and 1
    float r = ofRandom(0, 1.0);  
    
    // 02% chance this will happen
    if (r > 0.04) { 
		ofFill();	
		//ofSetColor(coldColor[int(ofRandom(0,11))]);	
		ofSetColor(255,255,255,225);			
		ofEllipse(x, y, dotSize, dotSize);
		float newx = x + cos(angle) * dotSize;
		float newy = y + sin(angle) * dotSize;
		seed1(dotSize * 0.99, angle - angleOffsetA, newx, newy);   
    }
    // 98% chance this will happen
    else {  
		ofEllipse(x, y, dotSize, dotSize);
		float newx = x + cos(angle);
		float newy = y + sin(angle);
		seed2(dotSize * 0.99, angle + angleOffsetA, newx, newy);
		seed1(dotSize * 0.60, angle + angleOffsetB, newx, newy);
		seed2(dotSize * 0.50, angle - angleOffsetB, newx, newy);
    } 
  }
}
void liveApp::seed2(float dotSize, float angle, float x, float y)	{
  
  if (dotSize > 1.0) {
    
    // Create a random numbers between 0 and 1
    float r = ofRandom(0, 1.0);
    
    // 5% chance this will happen
    if (r > 0.05) {
      ofEllipse(x, y, dotSize, dotSize);
      float newx = x + cos(angle) * dotSize;
      float newy = y + sin(angle) * dotSize;
      seed2(dotSize * 0.99, angle + angleOffsetA, newx, newy);
    } 
    // 95% chance this will happen
    else {
      ofEllipse(x, y, dotSize, dotSize);
      float newx = x + cos(angle);
      float newy = y + sin(angle);
      seed1(dotSize * 0.99, angle + angleOffsetA, newx, newy);  
      seed2(dotSize * 0.60, angle + angleOffsetB, newx, newy);
      seed1(dotSize * 0.50, angle - angleOffsetB, newx, newy);
    }
  }
}
void liveApp::keyPressed  (int key)	{
	if ( key == 'm')	ofHideCursor();
	if ( key == 'M')	ofShowCursor();
	if ( key == 'g')	{
		for (int t = 0; t < 1; t++)	{
			int x1i = int(ofRandom(0,63));
			int x1j = int(ofRandom(0,63));			
			int x2i = int(ofRandom(0,63));			
			int x2j = int(ofRandom(0,63));			
			int y1i = int(ofRandom(0,63));			
			int y1j = int(ofRandom(0,63));			
			int y2i = int(ofRandom(0,63));																		
			int y2j = int(ofRandom(0,63));						
			//SetColor(citrusColor[int(ofRandom(0,11))]);									
			ofSetColor(255,255,255,255);												
			myFont11.drawString("0",gridX[x1i][x1j], gridY[y1i][y1j]);							
			myFont11.drawString("1",gridX[x2i][x2j], gridY[y2i][y2j]);	
			ofSetColor(255,255,255,12);
			//ofCurve(0,0, gridX[x1i][x1j], gridY[y1i][y1j], gridX[x2i][x2j], gridY[y2i][y2j], 1000,1000);						
			ofLine(gridX[x1i][x1j], gridY[y1i][y1j], gridX[x2i][x2j], gridY[y2i][y2j]);									
		}
	}	
	if ( key == 'G')	{
	    glPushMatrix();
        glTranslatef(ofGetWidth()/2,ofGetHeight()/2,0);
		gluLookAt(2*mouseX, 2*mouseY, 2*mouseX, // eyeX, eyeY, eyeZ
         0.0, 0.0, 0.0, // centerX, centerY, centerZ
         0.0, 1.0, 0.0);				
        glRotatef(-2*mouseY,1,0,0);
        glRotatef(2*mouseX,0,1,0);
		ofSetColor(255,255,255,255);
		glBegin(GL_POINTS);	//GL_POINTS,GL_LINE_LOOP, GL_LINE_STRIP  (http://pyopengl.sourceforge.net/documentation/manual/glBegin.3G.xml)
		for (int i = 0; i < MAX_MATRIX; i++)	{
			for (int j = 0; j < MAX_MATRIX; j++)	{			
				for (int k = 0; k < MAX_MATRIX; k++)	{	
					//ofPoint()
					matrix3d.x = gridX3d[i][j][k];
					matrix3d.y = gridY3d[i][j][k];
					matrix3d.z = gridZ3d[i][j][k];
					glVertex3i(matrix3d.x, matrix3d.y, matrix3d.z);		
				}
			}
		}
		glEnd();	
		glPopMatrix();
	}	
	if(key == 'f' or key == 'F'){
		int previousWindowX, previousWindowY;  
		
		if(ofGetWindowMode() == 0){  

			ofSetFullscreen(true);
			ofBackground(0, 0, 0);
		}else{  
			ofSetFullscreen(false);
			ofBackground(0, 0, 0);
		}  
	}		
	if(key == 'b' or key == 'B'){
		ofBackground(0, 0, 0);
	}	
	if(key == 't' or key == 'T'){
		//ofBackground(0,0,0);
        glTranslatef(ofGetWidth()/2,ofGetHeight(),0);	
		seed1(dotSize, (270*3.1415926)/180, 0, 0);
	}	
	if(key == 'p') {
			slowMotion = !slowMotion;
			if(slowMotion)
					timeStep = .05;
			else
					timeStep = 1;
	}
}
void liveApp::keyReleased(int key)	{
	
}
void liveApp::mouseMoved(int x, int y )	{
}
void liveApp::mouseDragged(int x, int y, int button)	{
		
}
void liveApp::mousePressed	(int x, int y, int button)	{
	isMousePressed = true;
	drawNow = true;	
}
void liveApp::mouseReleased	(int x, int y, int button)	{
	isMousePressed = false;
	drawNow = false;
}
void liveApp::resized(int w, int h)	{

}