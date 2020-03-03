#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ELEMENTS 5

FILE *open(char *name){
	FILE * in = fopen(name, "r");
	if( in == NULL ){
		printf("Wystąpił błąd podczas otwierania pliku %s!\n", name);
		exit(-1);
	}
}

char **read_data(char *name){
	FILE *file = open( name );
	char **tab = malloc( ELEMENTS * sizeof( char* ));

	for( int i = 0; i < ELEMENTS; i++){
		tab[i] = malloc( 50 * sizeof( char ) );
	}

	
	for( int i = 0; i < ELEMENTS; i++){
		if( fscanf( file, "%s", tab[i] ) != 1 ){
			printf("Za malo argumentow w pliku wejściowym!\n");
			exit (-1);
		}
	}

	char temp[50];
	if( fscanf( file, "%s", temp) == 1 ){
		printf("Nieprawidlowy format danych w pliku!\n");
		exit(-3);

	}	

	if( strcmp( tab[1] , "X") != 0 ){
		printf("Nieprawidlowy format danych w pliku!\n");
		exit(-4);
	}
	return tab;
}

int get_width(char **tab){
	if( atof (tab[0] ) == 0 && tab[0][0] != '0' ){
		printf("Nieprawidlowy format danych w pliku!\n");
		exit(-4);
	}
	return atof(tab[0]);
}

int get_height(char **tab){
	if( atof (tab[2] ) == 0 && tab[2][0] != '0' ){
		printf("Nieprawidlowy format danych w pliku!\n");
		exit(-5);
	}
	return atof(tab[2]);
}

char *get_type(char **tab){
	if( strcmp( tab[3], "MOOR") != 0 && strcmp( tab[3], "NEUMMANN" ) != 0 ){
		printf("Wprowadzono nieodpowiedni typ sąsiedztwa: %s\n", tab[3] );
		exit(-4);

	}
	return tab[3];
}

