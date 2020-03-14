#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "input_file.h"

#define ELEMENTS 5

FILE *open(char *name){
	FILE * in = fopen(name, "r");
	if( in == NULL ){
		printf("Wystąpił błąd podczas otwierania pliku %s!\n", name);
		exit(-1);
	}
	return in;
}

char **read_data(FILE *file){
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
	
	if( strcmp( tab[1] , "X") != 0 && strcmp( tab[1], "x") != 0){
		printf("Nieprawidlowy format danych w pliku!\n");
		exit(-4);
	}
	
	if( is_rand(tab) == 0 ){
		int w = get_width(tab);
		int h = get_height(tab);
		
		
		tab = realloc(tab, (ELEMENTS + h ) * sizeof (char*));
	

		for( int i = ELEMENTS; i < ELEMENTS + h; i++){
			tab[i] = malloc( (w+20) * sizeof( char ) );
			memset( tab[i] , 0 , (w+20) * sizeof(char));
		}

		for( int i = ELEMENTS; i < ELEMENTS + h; i++ ){
			if( fscanf( file, "%s", tab[i] ) != 1 ){
				printf("Za malo argumentow w pliku wej\n");
				exit(-3);
			}	
			if( tab[i][w] != '\0'  ){
				printf("Nieprawidlowy format wprowadzonych danych w plk wejściowym!\n");
				exit(-10);
			}

		}

	}

	if( fscanf( file, "%s", temp) == 1 ){
		printf("Nieprawidlowy format danych w pliku: Za duzo danych\n");
		exit(-3);

	}	
	fclose(file);
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

int is_rand(char **tab){
	if( strcmp( tab[4], "RAND") == 0 )
		return 1;
	else if( strcmp( tab[4], "NORMAL" ) == 0 )
		return 0;
	else {
		printf("Nierozpoznany typ generowania: %s\n", tab[4]);
		exit( -1);
	}
}

char **get_init_gen(char **tab){
	int h = get_height(tab);
	int w = get_width(tab);
	char **t = malloc( h * sizeof (char*));
	for(int i = 0; i < h; i++){
		t[i] = malloc( sizeof( (w+20) * sizeof(char)));
		strcpy(t[i], tab[ELEMENTS+i]);
	}

	return t;

}

void free_args(char **tab){
	int r = is_rand(tab);
	int h = get_height(tab);
	if( r == 0 ){
		for(int i = 0; i < h + ELEMENTS; i++)
			free(tab[i]);
		
	} else if( r == 1 ){
		for( int i = 0; i < ELEMENTS; i++)
			free(tab[i]);

	}
	
	free(tab);
}
