/*
 *  Superformula.h
 *  superformula
 *
 *  Created by fou fou on 1/2/10.
 *  Copyright 2010 __MyCompanyName__. All rights reserved.
 *
 */
#ifndef _OF_SUPERFORMULA // if this class hasn't been defined, the program can define it
#define _OF_SUPERFORMULA // by using this if statement you prevent the class to be called more 

// than once which would confuse the compiler

#include "ofMain.h"


class ofSuperformula {
public:	
	void	draw(float xf, float yf);
	
	float	x, y, xf, yf, r, th, step, epi;
	float	mi, n1, n2, n3, alpha, bita, sformType;	
	int		redF, greenF, blueF, alphaF;
	int		num;
	
	ofSuperformula();	// constructor - used to initialize an object, if no properties are passed
						// the program sets them to the default value
	
	
	
};

#endif 