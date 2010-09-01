//
//  WarAcademy.h
//  WarAcademyProject
//
//  Created by Miguel Angel  Lopez Montellano on 31/08/10.
//  Copyright (c) 2010 BitsOnFire Spanish iPhone Game Desing. All rights reserved.
//

#import "CCSprite.h"

#define STATE_VIVO 1
#define STATE_MUERTO -1

#define TYPE_SOLDADO 1
#define TYPE_ARQUERO 2
#define TYPE_MAGO 4
#define TYPE_CAPITAN 8
#define TYPE_LANCERO 16
#define 

//Posicion discreta en el mapa del puzzle
struct point_struct{
    
    short i; // posicion x discreta
    short j; // posicion y discreta
}

typedef point_struct wapoint;


//estructura de datos de cada ficha/mini en el tablero
struct mini_struct{
    CCSprite * sprite;
    wapoint position;
    int state;
    int type;   
}


typedef mini_struct mini;