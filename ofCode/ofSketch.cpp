/*
 *  Sketch.cpp
 *  sketch091221
 *
 *  Created by Aris Bezas on 091223
 *  Copyright 2009 igoumeninja. All rights reserved.
 *
 */
 
#include "liveApp.h"
#include "ofSketch.h"
//----------------------------------------------------------------------------------------------------//

// Initialize the Sketch Lines Characteristics
void ofSketch::init(int sketchID, float elast, float aposv) {
	
	sender.setup( HOST, PORT );	
	if	(sketchID == 0)	{
		for (int i=0; i<stoixeia; i++){
			elastikotita[i] = (elast)*(.07*(i+1));// 0.05  kai 0.005
			aposbesi[i] = aposv-(0.02 *i);
		}
	}
	if	(sketchID == 1)	{
		for (int i=0; i<stoixeiaMouse; i++){
			elastikotitaMouse[i] = (elast)*(.05*(i+1));// 0.05  kai 0.005
			aposbesiMouse[i] = aposv-(0.02 *i);
		}
	}
}
void ofSketch::drawMouse(int xL, int yL, int zL, int redL, int greenL, int blueL, int alphaL, float lines) {
	ofNoFill();
	ofSetColor(redL, greenL, blueL, alphaL);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_LINE_SMOOTH);    
	if	(lines)	{
		glBegin(GL_LINE_LOOP); //GL_LINE_LOOP,GL_POINTS, GL_LINE_STRIP  ( http://pyopengl.sourceforge.net/documentation/manual/glBegin.3G.xml )
	}	else	{
		glBegin(GL_POINTS); //GL_LINE_LOOP
	}

	for (int i=0; i<stoixeiaMouse; i++){
	if	(alphaL != 0)	{
		if (i==0){
			deltaXMouse[i] = (xL - xiMouse[i]);
			deltaYMouse[i] = (yL - yiMouse[i]);
		}
		else {
			deltaXMouse[i] = (xiMouse[i-1]-xiMouse[i]);
			deltaYMouse[i] = (yiMouse[i-1]-yiMouse[i]);
		}		
		deltaXMouse[i] *= elastikotitaMouse[i];    // create elastikotita effect
		deltaYMouse[i] *= elastikotitaMouse[i];
		epitaxinsiXMouse[i] += deltaXMouse[i];
		epitaxinsiYMouse[i] += deltaYMouse[i];
		xiMouse[i] += epitaxinsiXMouse[i]; // move it
		yiMouse[i] += epitaxinsiYMouse[i];
		} else {
			xiMouse[i] = xL;
			yiMouse[i] = yL;
		}		
		my3d.x = xiMouse[i];
		my3d.y = yiMouse[i];
		my3d.z = zL;		//my3d.z = ofRandom(100,500); maybe it 'll be interasting to put a midi controller for this to prevent chaotic-sand results
		glVertex3f(my3d.x, my3d.y, my3d.z);	
		epitaxinsiXMouse[i] *= aposbesiMouse[i];    // slow down elastikotita
		epitaxinsiYMouse[i] *= aposbesiMouse[i];
	}
	glEnd();	
}
void ofSketch::drawSound(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL, float slines) {
	ofFill();
	ofSetColor(redL, greenL, blueL, alphaL);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_LINE_SMOOTH);   
	if	(slines)	{
		glBegin(GL_LINE_LOOP); //GL_LINE_LOOP,GL_POINTS, GL_LINE_STRIP  ( http://pyopengl.sourceforge.net/documentation/manual/glBegin.3G.xml )
	}	else	{
		glBegin(GL_POINTS); //GL_LINE_LOOP
	}
	//printf(" %i \n", slines);			

	for (int i=0; i<stoixeia; i++){
		if (i==0){
			deltaX[i] = (xL - xi[i]);
			deltaY[i] = (yL - yi[i]);
		}
		else {
			deltaX[i] = (xi[i-1]-xi[i]);
			deltaY[i] = (yi[i-1]-yi[i]);
		}		
		deltaX[i] *= elastikotita[i];    // create elastikotita effect
		deltaY[i] *= elastikotita[i];
		epitaxinsiX[i] += deltaX[i];
		epitaxinsiY[i] += deltaY[i];
		xi[i] += epitaxinsiX[i];// move it
		yi[i] += epitaxinsiY[i];
		my3d.x = xi[i];
		my3d.y = yi[i];
		my3d.z = zL;
		glVertex3f(my3d.x, my3d.y, my3d.z);	
		epitaxinsiX[i] *= aposbesi[i];    // slow down elastikotita
		epitaxinsiY[i] *= aposbesi[i];
	}
	glEnd();	
}
void ofSketch::update(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL) {
	ofFill();
	ofSetColor(redL, greenL, blueL, alphaL);
	glEdgeFlag(GL_TRUE);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_LINE_SMOOTH);       
	glBegin(GL_POINTS);	//GL_POINTS,GL_LINE_LOOP, GL_LINE_STRIP  (http://pyopengl.sourceforge.net/documentation/manual/glBegin.3G.xml)

	for (int i=0; i<stoixeia; i++){
		if (i==0){
			deltaX[i] = (xL - xi[i]);
			deltaY[i] = (yL - yi[i]);
		}
		else {
			deltaX[i] = (xi[i-1]-xi[i]);
			deltaY[i] = (yi[i-1]-yi[i]);
		}		
		deltaX[i] *= elastikotita[i];    // create elastikotita effect
		deltaY[i] *= elastikotita[i];
		epitaxinsiX[i] += deltaX[i];
		epitaxinsiY[i] += deltaY[i];
		xi[i] += epitaxinsiX[i];// move it
		yi[i] += epitaxinsiY[i];
		my3d.x = xi[i];
		my3d.y = yi[i];
		my3d.z = zL;
		glVertex3f(my3d.x, my3d.y, my3d.z);		
		epitaxinsiX[i] *= aposbesi[i];    // slow down elastikotita
		epitaxinsiY[i] *= aposbesi[i];
	}
	glEnd();	
}
void ofSketch::updateClassic(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL) {	
	ofFill();
	ofSetColor(redL, greenL, blueL, alphaL);
	ofBeginShape();
	for (int i=0; i<stoixeia; i++){
		if (i==0){
			deltaX[i] = (xL - xi[i]);
			deltaY[i] = (yL - yi[i]);
		}
		else {
			deltaX[i] = (xi[i-1]-xi[i]);
			deltaY[i] = (yi[i-1]-yi[i]);
		}		
		deltaX[i] *= elastikotita[i];    // create elastikotita effect
		deltaY[i] *= elastikotita[i];
		epitaxinsiX[i] += deltaX[i];
		epitaxinsiY[i] += deltaY[i];
		xi[i] += epitaxinsiX[i];// move it
		yi[i] += epitaxinsiY[i];
		ofCurveVertex(xi[i], yi[i]);
		epitaxinsiX[i] *= aposbesi[i];    // slow down elastikotita
		epitaxinsiY[i] *= aposbesi[i];
	}
	ofEndShape();
	
}
void ofSketch::stoping(float xL, float yL) {
	ofxOscMessage m;
	m.setAddress( "/ampXar" );
	m.addFloatArg( 0);
	sender.sendMessage( m );			
	for (int i=0; i<stoixeia; i++){
		xi[i]  = xL;
		yi[i]  = yL;
		deltaX[i] =0;
		deltaY[i] =0;
		epitaxinsiX[i] = 0;
		epitaxinsiY[i]  =0;
	}
}
void ofSketch::sound(float xL, float yL) {
	for (int i=0; i<stoixeia; i++){
		if (i==0){
			deltaX[i] = (xL - xi[i]);
			deltaY[i] = (yL - yi[i]);
			ofxOscMessage m;
			m.setAddress( "/ampXar" );
			delta = ofMap(ABS(deltaX[0])+ABS(deltaY[0]), 0, 540, 0, 0.7  );			
			m.addFloatArg( delta);
			sender.sendMessage( m );			
		}
		else {
			deltaX[i] = (xi[i-1]-xi[i]);
			deltaY[i] = (yi[i-1]-yi[i]);
		}
		
		deltaX[i] *= elastikotita[i];    // create elastikotita effect
		deltaY[i] *= elastikotita[i];
		epitaxinsiX[i] += deltaX[i];
		epitaxinsiY[i] += deltaY[i];
		xi[i] += epitaxinsiX[i];// move it
		yi[i] += epitaxinsiY[i];
		epitaxinsiX[i] *= aposbesi[i];    // slow down elastikotita
		epitaxinsiY[i] *= aposbesi[i];
	}
}














































