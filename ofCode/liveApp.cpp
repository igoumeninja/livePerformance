/*
 
 Preapering AudioVisual Performance at Lyon
 
 Aris Bezas 
 Igoumeninja 28-10-2011
 
*/
 
#include "liveApp.h"

void liveApp::setup()	{
	{

		int windowMode = ofGetWindowMode();  
		if(windowMode == OF_FULLSCREEN){  
			this->windowWidth = ofGetScreenWidth();  
			this->windowHeight = ofGetScreenHeight();  
		}  
		else if(windowMode == OF_WINDOW){  
			this->windowWidth = ofGetWidth();  
			this->windowHeight = ofGetHeight();  
		} 
		
		ofSetCircleResolution(200);
		texScreen.allocate(ofGetWidth(), ofGetHeight(),GL_RGBA);// GL_RGBA); 
		ofSetBackgroundAuto(false);
		//ofEnableSmoothing();
		//ofEnableAlphaBlending(); 
		//glutSetCursor(GLUT_CURSOR_CYCLE);  // change cursor icon (http://pyopengl.sourceforge.net/documentation/manual/glutSetCursor.3GLUT.html)
		cout << "recieving OSC at port: 12345 " << PORTlisten << "\n";
		receiver.setup( PORTlisten );
		current_msg_string = 0;
				
		ofSetWindowTitle("Joda: AudioVisual Performance at Lyon");
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
		 		
		// Tree
		dotSize = 15;
		angleOffsetA = (1.5*3.14)/180; // Convert 1.5 degrees to radians
		angleOffsetB = (50*3.14)/180;  // Convert 50 degrees to radians
		
		//	RGB - MIDI
		obj_a = 10;
		obj_r = obj_g = obj_b = 255;
		a1 = 255;
		a2 = 0;
		a3 = 18;
		a4 = 8;
		a5 = 70;
		a6 = 40;
		a7 = 140;
		a8 = 0; //for Blending:  GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA  a8=18 (oriaka)
		r1 = g1 = b1 = 255;
		r2 = g2 = b2 = 0;
		r3 = g3 = b3 = 255;
		r4 = g4 = b4 = 255;
		r5 = g5 = b5 = 0;
		r6 = g6 = b6 = 0;
		r7 = g7 = b7 = 255;
		r8 = g8 = b8 = 0;				
		r9 = g9 = b9 =  a9 = 255;						
		viewSoundChanels = 1;
		midi85 = midi93 = 127;
		
		drawWithMouse = 1;
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
		
		ampInLow =0;
		ampInHigh = 0.15;
		freqInLow = 120;
		freqInHigh = 4000;
		
		snapCounter = 3;
		feedbackView = 0;
		feedbackSpeedX = 0;
		feedbackSpeedY = 0;
		timeLine = 0;
		viewRotate = 0;
				
		view_fillBackground = 1;
	
		beatsView = 1;
		
		strPosX = strPosY = 200;
		}	// Initial Values
	{
		// Imagenes
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
		
		//image[0].loadImage("/Users/ari/Media/images/bibliOdyssey/Australian-Places/Cape-Otway-Ranges.jpg");
		// Video
			//sketchDust100328.loadMovie("videos/sketchDust100328.avi");
		// Fonts
		myFont11.loadFont("/Users/ari/Media/fonts/favorites/Batang.ttf", 11, true, true, true);
		myFont350.loadFont("/Users/ari/Media/fonts/favorites/Batang.ttf", 350, true, true, true);		
	}	// data (images, fonts, video ...)
	{
		for (int i = 0; i < MAX_SKETCHES; i++){
			sketch[i].init(0, ofRandom(minSoundElasticity, maxSoundElasticity), ofRandom(minSoundDamping, maxSoundDamping));	//to 1o stoixeio einai to id 0:
			sketch[i].init(1, ofRandom(minMouseElasticity, maxMouseElasticity), ofRandom(minMouseDamping, maxMouseDamping));	//id:1 => mouse init(int sketchID, float elast, float aposv)
		}

	}	// sKeTch
	{
        // this number describes how many bins are used
        // on my machine, 2 is the ideal number (2^2 = 4x4 pixel bins)
        // if this number is too high, binning is not effective
        // because the screen is not subdivided enough. if
        // it's too low, the bins take up so much memory as to
        // become inefficient.
        int binPower = 4;

        particleSystem.setup(ofGetWidth(), ofGetHeight(), binPower);

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
		
		viewParticles = false;
		
		
		}	// Particles
		
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
		if ( m.getAddress() == "img" )	{
			
			//cout << m.getNumArgs() << endl;
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE			
			switch (m.getNumArgs())	{
				case 1:
					ofFill();
					ofSetHexColor(0xFFFFFF);				
					//image[m.getArgAsInt32(0)].draw(m.getArgAsInt32(1), 0, 0, ofGetWidth(), ofGetHeight());
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
					cout << m.getNumArgs() << endl;
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
		}		
		if ( m.getAddress() == "obj" )					{
			if (m.getArgAsString( 0 ) == "activate")	viewOBJ = m.getArgAsInt32( 1 );		
			else if (m.getArgAsString( 0 ) == "type")	objType = m.getArgAsInt32( 1 );		
			else if (m.getArgAsString( 0 ) == "distrurbance")	distrurbance = m.getArgAsFloat( 1 );
			else if (m.getArgAsString( 0 ) == "viewControl")	{
				viewControlY = m.getArgAsFloat( 1 );
				viewControlX = m.getArgAsFloat( 2 );				
			}
			else if (m.getArgAsString( 0 ) == "transparence")	obj_a = m.getArgAsInt32( 1 );					
		}	//  .obj	
		if ( m.getAddress() == "particle" )				{
			if (m.getArgAsString( 0 ) == "type")	objType = m.getArgAsInt32( 1 );		
			else if (m.getArgAsString( 0 ) == "activate")	{
					if (m.getArgAsInt32(1) == 1)	viewParticles = true;	
					else if (m.getArgAsInt32(1) == 0)	viewParticles = false;	
			}
			else if (m.getArgAsString( 0 ) == "lineOpacity")	lineOpacity = m.getArgAsInt32( 1 );	
			else if (m.getArgAsString( 0 ) == "particleNeighborhood")	particleNeighborhood = m.getArgAsInt32( 1 );
			else if (m.getArgAsString( 0 ) == "forceRadius")	forceRadius = m.getArgAsInt32( 1 );		
			else if (m.getArgAsString( 0 ) == "forceScale")	forceScale = m.getArgAsInt32( 1 );		
			else if (m.getArgAsString( 0 ) == "iPodPush")		{
				if (m.getArgAsInt32(1) == 1)	iPodPush = true;	
				else if (m.getArgAsInt32(1) == 0)	iPodPush = false;	
			}
			else if (m.getArgAsString( 0 ) == "pushParticles")		{
				if (m.getArgAsInt32(1) == 1)	pushParticles = true;	
				else if (m.getArgAsInt32(1) == 0)	pushParticles = false;	
			}
			else if (m.getArgAsString( 0 ) == "push")		{
				pushX = m.getArgAsFloat( 1 );
				pushY = m.getArgAsFloat( 2 );
			}
			else if	(m.getArgAsString( 0 ) == "addParticles")	{
				kParticles = m.getArgAsInt32( 1 );	
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
			}
			else if	(m.getArgAsString( 0 ) == "eraseParticles")	{
				particleSystem.erase();
			}
		}	//  particles
		if ( m.getAddress() == "feedback" )				{
			if (m.getArgAsString( 0 ) == "activate")	{
				feedbackView = m.getArgAsInt32( 1 );
			} else if (m.getArgAsString( 0 ) == "speedXY")		{
				feedbackSpeedY = m.getArgAsFloat( 1 );
				feedbackSpeedX = m.getArgAsFloat( 2 );
				cout << feedbackSpeedY << endl;
			}
		}	//	feedback		
		if ( m.getAddress() == "background" )			{
			int rBack = m.getArgAsInt32(0);
			int gBack = m.getArgAsInt32(1);
			int bBack = m.getArgAsInt32(2);								
			ofBackground(rBack, gBack, bBack);
		}	//	background						
		if ( m.getAddress() == "writeString" )			{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
			ofFill();
			ofSetColor(m.getArgAsInt32( 3 ),m.getArgAsInt32( 4 ),m.getArgAsInt32( 5 ),m.getArgAsInt32( 6 ));
			ofPushMatrix();
			ofTranslate(m.getArgAsFloat( 1 ), m.getArgAsFloat( 2 ), 0);
			myFont11.drawString(m.getArgAsString( 0 ), 0, 0);		
			ofPopMatrix();
		
		}	//	Write a string
		if ( m.getAddress() == "renderString" )			{
			if (m.getArgAsString( 0 ) == "string")	{
				renderString = m.getArgAsString( 1 );
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
				ofFill();
				float redRandom = ofRandom(0.0,1.0);
				if (redRandom <	0.10) ofSetColor(255,0,0,255);	
				else ofSetColor(255,255,255,255);	// even
				float strPosX = ofRandom(0, ofGetWidth());
				float strPosY = ofRandom(0, ofGetHeight());
				ofPushMatrix();
				ofTranslate(strPosX, strPosY, 0);
				myFont11.drawString(renderString, 0, 0);		
				ofPopMatrix();
			}
		}	//	Write a string
		if ( m.getAddress() == "tree" )	{
			glTranslatef(ofGetWidth()/2,ofGetHeight(),0);	
			seed1(dotSize, (270*3.1415926)/180, 0, 0);
		}			
		if ( m.getAddress() == "rgb" )					{
				 if ( m.getArgAsString( 0 ) == "background")	{ r8 = m.getArgAsInt32( 1 );	g8 = m.getArgAsInt32( 2 );	b8 = m.getArgAsInt32( 3 );	a8 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "sketch")	{ r7 = m.getArgAsInt32( 1 );	g7 = m.getArgAsInt32( 2 );	b7 = m.getArgAsInt32( 3 );	a7 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "sketchBW")	{ r7 = m.getArgAsInt32( 1 );	g7 = m.getArgAsInt32( 2 );	b7 = m.getArgAsInt32( 3 );	}			
			else if ( m.getArgAsString( 0 ) == "sound")	{ r6 = m.getArgAsInt32( 1 );	g6 = m.getArgAsInt32( 2 );	b6 = m.getArgAsInt32( 3 );	a6 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "5")	{ r5 = m.getArgAsInt32( 1 );	g5 = m.getArgAsInt32( 2 );	b5 = m.getArgAsInt32( 3 );	a5 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "4")	{ r4 = m.getArgAsInt32( 1 );	g4 = m.getArgAsInt32( 2 );	b4 = m.getArgAsInt32( 3 );	a4 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "object")	{ r3 = m.getArgAsInt32( 1 );	g3 = m.getArgAsInt32( 2 );	b3 = m.getArgAsInt32( 3 );	a3 = m.getArgAsInt32( 4 );	}								
			else if ( m.getArgAsString( 0 ) == "particles")	{ r2 = m.getArgAsInt32( 1 );	g2 = m.getArgAsInt32( 2 );	b2 = m.getArgAsInt32( 3 );	}
			else if ( m.getArgAsString( 0 ) == "particlesColor")	{ r2 = m.getArgAsInt32( 1 );	g2 = m.getArgAsInt32( 2 );	b2 = m.getArgAsInt32( 3 );	a2 = m.getArgAsInt32( 4 );}			
			else if ( m.getArgAsString( 0 ) == "particleConnections")	{ r1 = m.getArgAsInt32( 1 );	g1 = m.getArgAsInt32( 2 );	b1 = m.getArgAsInt32( 3 );	} 
			else if ( m.getArgAsString( 0 ) == "particleConnectionsColor")	{ r1 = m.getArgAsInt32( 1 );	g1 = m.getArgAsInt32( 2 );	b1 = m.getArgAsInt32( 3 ); a1 = m.getArgAsInt32( 4 );	} 			
			else if ( m.getArgAsString( 0 ) == "iperatou")	{ r9 = m.getArgAsInt32( 1 );	g9 = m.getArgAsInt32( 2 );	b9 = m.getArgAsInt32( 3 );	a9 = m.getArgAsInt32( 4 );	}
			else if ( m.getArgAsString( 0 ) == "r1" )			r1 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g1" )			g1 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b1" )			b1 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a1" )			a1 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r2" )			r2 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g2" )			g2 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b2" )			b2 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a2" )			a2 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r3" )			r3 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g3" )			g3 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b3" )			b3 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a3" )			a3 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r4" )			r4 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g4" )			g4 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b4" )			b4 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a4" )			a4 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r5" )			r5 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g5" )			g5 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b5" )			b5 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a5" )			a5 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r6" )			r6 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g6" )			g6 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b6" )			b6 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a6" )			a6 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r7" )			r7 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g7" )			g7 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b7" )			b7 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a7" )			a7 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "r8" )			r8 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "g8" )			g8 = m.getArgAsInt32(1);	
			else if ( m.getArgAsString( 0 ) == "b8" )			b8 = m.getArgAsInt32(1);
			else if ( m.getArgAsString( 0 ) == "a8" )			a8 = m.getArgAsInt32(1);
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
		}		
		if ( m.getAddress() == "interactWithSketch" )	{
			if ( m.getArgAsString(0) == "activate" )	{
				if (m.getArgAsInt32(1) == 1)	drawWithMouse = true;	
				else if (m.getArgAsInt32(1) == 0)	drawWithMouse = false;	
			}
			else if ( m.getArgAsString(0) == "padXY" )	{	padX = m.getArgAsFloat(1);	padY = m.getArgAsFloat(2);	}
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
		}	//	Mouse Interaction		
		if	(viewSoundChanels)	{			
				if ( m.getAddress() == "/ampChan0" )	{
					ampChan0 = m.getArgAsFloat( 0 );
					//printf(" %f \n", ampChan0);			
				}
				if ( m.getAddress() == "/ampChan1" )	{
					ampChan1 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/ampChan2" )	{
					ampChan2 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/ampChan4" )	{
					ampChan3 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/ampChan5" )	{
					ampChan3 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/ampChan6" )	{
					ampChan3 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/ampChan7" )	{
					ampChan3 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan0" )	{
					freqChan0 = m.getArgAsFloat( 0 );
					//printf(" %f \n", ampChan0);			
				}
				if ( m.getAddress() == "/freqChan1" )	{
					freqChan1 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan2" )	{
					freqChan2 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan3" )	{
					freqChan3 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan4" )	{
					freqChan0 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan5" )	{
					freqChan1 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan6" )	{
					freqChan2 = m.getArgAsFloat( 0 );
				}
				if ( m.getAddress() == "/freqChan7" )	{
					freqChan3 = m.getArgAsFloat( 0 );
				}							
		}	//	Sound Interaction //sound lines characteristics (channel I)
	}	
}
void liveApp::draw()	{
	if	(viewParticles)	{
        particleSystem.setTimeStep(timeStep);
        ofEnableAlphaBlending();
		ofSetColor(r1, g1, b1, a1);	
        particleSystem.setupForces();
        glBegin(GL_LINES);
        for(int i = 0; i < particleSystem.size(); i++) {
                Particle& cur = particleSystem[i];
                particleSystem.addRepulsionForce(cur, particleNeighborhood, particleRepulsion);
                cur.bounceOffWalls(0, 0, ofGetWidth(), ofGetHeight());				
                cur.addDampingForce();
        }
        glEnd();
        //particleSystem.addAttractionForce(ofGetWidth() / 2, ofGetHeight() / 2, ofGetWidth(), centerAttraction);
		if	(viewSoundChanels)	{
			Yamp0 = ofMap(ampChan0, ampInLow, ampInHigh, 0, ofGetHeight());
			Xfreq0 = ofMap(freqChan0, freqInLow, freqInHigh, 0, ofGetWidth());
			particleSystem.addRepulsionForce(Xfreq0, Yamp0, forceRadius, forceScale);											
		}
        if(iPodPush)	{
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

        ofSetColor(r2, g2, b2, a2);
        particleSystem.draw();
	}	// Particles
	if	(viewSketchAutomato)	{
		for( int i=0; i<100; i++ ) {
			sketch[i].drawMouse(xSolo, ySolo, 0, a7, g7, b7, a7, 0);	
		}		
	}
	if	(view_fillBackground)	{
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
		ofFill();	
		ofSetColor(r8, g8, b8, a8);
		ofRect(0,0,ofGetWidth(),ofGetHeight());			
	}
	if	(viewSoundChanels)	{
		Yamp0 = ofMap(ampChan0, ampInLow, ampInHigh, 0, ofGetHeight());
		Xfreq0 = ofMap(freqChan0, freqInLow, freqInHigh, 0, ofGetWidth());
		for( int i=1000; i<1000+numSoundSketches; i++ ) {
			sketch[i].drawSound(Xfreq0, Yamp0, 0, r6, g6, b6, a6, soundLines);	
		}
	}  	//  viewSoundChanels 
	if	(drawWithMouse)	{
		for( int i=1000; i<1000 + numMouseSketches; i++ ) {
				sketch[i].drawMouse(padX, padY, 0, r7, g7, b7, a7/3, mouseLines);	
			}
//		for( int i=2000; i<2000 + numMouseSketches; i++ ) {
//			sketch[i].drawMouse(padX+400, padY, 0, r7, g7, b7, a7/3, mouseLines);	
//		}	
	}
	if	(feedbackView)	{
		texScreen.loadScreenData(0,0,ofGetWidth(), ofGetHeight());
		//texScreen.loadScreenData(0,0,ofGetScreenWidth(), ofGetScreenHeight());							
		//texScreen.loadScreenData(0,0,1280,1024);							
		glPushMatrix();
		ofSetHexColor(0xffffff);
		glTranslatef(feedbackSpeedX,feedbackSpeedY,0);
		//		glTranslatef(2,2,0);		
		texScreen.draw(0,0,ofGetWidth(), ofGetHeight());
		//texScreen.draw(0,0,ofGetScreenWidth(), ofGetScreenHeight());
		//texScreen.draw(0,0,1280,1024);		
		glPopMatrix();
		//cout << "test" << endl;
	}
}
void liveApp::syncStudies ()	{
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // GL_SRC_ALPHA_SATURATE,GL_ONE     GL_SRC_ALPHA, GL_ONE
		ofFill();
		strPosX = ofRandom(0, ofGetWidth());
		strPosY = ofRandom(0, ofGetHeight());
		ofSetColor(r1,g1,b1,a1);	// even
		ofPushMatrix();
		ofTranslate(strPosX, strPosY, 0);
		myFont11.drawString(beatStr,0, 0);		
		ofPopMatrix();
}
void liveApp::seed1(float dotSize, float angle, float x, float y)	{
  
  if (dotSize > 1.0) {
    
    // Create a random numbers between 0 and 1
    float r = ofRandom(0, 1.0);  
    
    // 02% chance this will happen
    if (r > 0.04) { 
		ofFill();	
		//ofSetColor(coldColor[int(ofRandom(0,11))]);	
		ofSetColor(255,255,255,125);			
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
	if ( key == 'm')	{	ofHideCursor();	}
	if ( key == 'M')	{	ofShowCursor();	}
	if ( key == 'x')	{	bSnapshot = true;	}
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
		//ofBackground(0,0,0);
	    glPushMatrix();
        //draw in middle of the screen
        glTranslatef(ofGetWidth()/2,ofGetHeight()/2,0);
		//glScalef(0.5, 0.5, 1);
		gluLookAt(2*mouseX, 2*mouseY, 2*mouseX, // eyeX, eyeY, eyeZ
         0.0, 0.0, 0.0, // centerX, centerY, centerZ
         0.0, 1.0, 0.0);		
		
        //tumble according to mouse
        glRotatef(-2*mouseY,1,0,0);
        glRotatef(2*mouseX,0,1,0);
//        glTranslatef(-ofGetWidth()/2,-ofGetHeight()/2,0);

//		glEdgeFlag(GL_TRUE);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glEnable(GL_LINE_SMOOTH);       
		ofSetColor(r4,g4,b4,a4);
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
    if ( key == 'k')		{	
		//viewOBJ = !viewOBJ;
		//delete particle;
	}
    if( key == 'x'){	
    }	
    if( key == 'z' ){
	}	
    if(key == 'c'){
    }	
    if(key == 'n'){
    }
    if(key == 'N'){
    }
	if(key == 'v' or key == 'V'){
		int previousWindowX, previousWindowY;  
		
		if(ofGetWindowMode() == 0){  
			ofSetFullscreen(true);
		}else{  
			ofSetFullscreen(false);
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

