#include <stdio.h>
#include <stdlib.h>
#include "png_drawing.h"

int main(){
	
	int w = 10;
	int h = 10;
	int n = 5;


		char **name = malloc( (n + 1)* sizeof(char*));
	       	for( int i = 0; i < n + 1; i++){
			name[i] = malloc( 15 * sizeof(char));
			sprintf(name[i], "rys%d.png", i);
		}



	int **board = malloc( h * sizeof( int * ));
	for( int i = 0; i < h; i++ ){
		board[i] = malloc( w * sizeof( int ));
	}

	
	for(int k = 0; k < n; k++){
		for( int i = 0; i < h; i++ ){
			for( int j = 0; j < w; j++){
				board[i][j] = rand() % 2;
			}
		}
	
		for( int i = 0; i < h; i++ ){
			for( int j = 0; j < w; j++){
				printf("%d ", board[i][j] );
			}
			printf("\n");
		}
		
		generate_drawing( board, w, h, name[k + 1] );	
	
		printf("Wygenerowano plik %s\n", name[k + 1] );
		printf("\n");
	}

	for( int i = 0; i < h; i++ ){
		free( board[i] );
	}
	free( board );


}
