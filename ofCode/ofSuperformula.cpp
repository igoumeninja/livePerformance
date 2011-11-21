/*
 *  Superformula.cpp
 *  superformula
 *
 *  Created by fou fou on 1/2/10.
 *  Copyright 2010 __MyCompanyName__. All rights reserved.
 *
 */

#include "liveApp.h"
#include "ofSuperformula.h"

ofSuperformula::ofSuperformula()
{
	mi = 4;n1=1.2;n2=1.1;n3=0.8;	
	alpha = bita = 1;
	epi = 200;
	redF = greenF = blueF = alphaF =255;
	num = 30;
	step = 60;
}

void	ofSuperformula::draw(float xf, float yf) {	
	
	ofNoFill();
	ofSetColor(redF, greenF, blueF, alphaF);
	//ofSetPolyMode(OF_POLY_WINDING_ABS_GEQ_TWO);//OF_POLY_WINDING_NONZERO);
	ofBeginShape();
	for(int i=1; i < num; i++) {
		r = epi *	( ( ( powf( labs( cos( (mi*th)/4 ) /alpha ), n2 ) ) + ( powf( labs( sin( mi*th/4) /bita), n3) ) ),(-1/n1));  
		th = th + step;
		x = xf + r*cos(th);
		y = yf + r*sin(th);
		ofCurveVertex(x,y);
		//ofVertex(x,y);		
	}
	//ofEndShape();
	ofEndShape(true);	

	
//	ofBeginShape();
//	for(int i=1; i < 10; i++) {
//		ofCurveVertex(ofRandom(200),ofRandom(200));
//	}
//	ofEndShape();
	if	(th > 10*360)	{
		th = 0;
	}
	//cout << num;
	//ofRect(0, 0, xf, yf);
}
