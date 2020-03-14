#include <stdio.h>
#include <stdlib.h>
#include "output_file.h"

int main(){
	int h = 5;
	int w = 5;
	char filename[] = "zapisz.txt";

	int **board = malloc( h * sizeof( int* ));

	for( int i = 0; i < h; i++){
		board[i] = malloc( w * sizeof ( int ));
		for( int j =0; j < w; j++){
			board[i][j] = rand() % 2;
		}
	}


	for( int i = 0; i < h; i++){
		for( int j =0; j < w; j++){
			printf("%d ", board[i][j] );
		}
		printf("\n");
	}


	save( filename, w, h, "MOOR", board );
	printf("Zapisano w pliku \"zapisz.txt\"\n");

	for( int i = 0; i < h; i++)
		free(board[i]);
	free(board);

}








