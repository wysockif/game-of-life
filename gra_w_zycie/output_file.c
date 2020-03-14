#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "output_file.h"


FILE *open_w(char *name){
	FILE * in = fopen(name, "w");
	if( in == NULL ){
		printf("Wystąpił błąd podczas otwierania pliku %s!\n", name);
		exit(-1);
	}
	return in;
}


void save( char *name, int w, int h, char *type, int **board ){
	FILE *file = open_w(name);
	fprintf(file, "%d X %d %s %s\n", w , h, type, "NORMAL"  );

	for(int i = 0; i < h; i++){
		for(int j = 0; j < w; j++){
			fprintf( file, "%i", board[i][j] );				
		}
		fprintf(file, "\n");

	}

	fclose(file);

}

