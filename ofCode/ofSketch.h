/*
 *  Sketch.h
 *  sketch091221
 *
 *  Created by fou fou on 12/23/09.
 *  Copyright 2009 Aris Bezas. All rights reserved.
 *ofSphere
 */

#pragma once

#include "ofxOsc.h"
#include "ofxVectorMath.h"

#define stoixeia			140
#define stoixeiaMouse		140		// power to the people: 400 loop working fine
#define MAX_N_PTS			150

#define HOST				"localhost"
#define PORT				57120

class ofSketch {
public:	
	int				sketchID;
	
	float			xi[stoixeia];
	float			yi[stoixeia];
	float			zi[stoixeia];	
	float			epitaxinsiX[stoixeia];
	float			epitaxinsiY[stoixeia];
	float			epitaxinsiZ[stoixeia];	
	float			elastikotita[stoixeia];
	float			aposbesi[stoixeia];
	float			deltaX[stoixeia];
	float			deltaY[stoixeia];
	float			deltaZ[stoixeia];	

	float			xiMouse[stoixeiaMouse];
	float			yiMouse[stoixeiaMouse];
	float			epitaxinsiXMouse[stoixeiaMouse];
	float			epitaxinsiYMouse[stoixeiaMouse];
	float			elastikotitaMouse[stoixeiaMouse];
	float			aposbesiMouse[stoixeiaMouse];	
	float			deltaXMouse[stoixeiaMouse];
	float			deltaYMouse[stoixeiaMouse];

	float			elast, aposv;
	int				xL, yL, zL;
	float			value, xC, yC;		
	int				redL, greenL, blueL, alphaL;
	float			delta;
	float			xp,yp;
	
	float				on_off, lines, slines;
	
	ofxVec3f		my3d;

    void			init(int sketchID, float elast, float aposv);
	void			drawMouse(int xL, int yL, int zL, int redL, int greenL, int blueL, int alphaL, float lines);
	void			sketch3d(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL, int lines);  
	void			drawSound(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL, float slines);	
    void			update(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL);
	void			updateClassic(float xL, float yL, float zL, int redL, int greenL, int blueL, int alphaL);
	void			sound(float xL, float yL);
	void			stoping(float xL, float yL);

private:
	
	ofxOscSender	sender;
	
};








































