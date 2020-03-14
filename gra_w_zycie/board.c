#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "board.h"

/* w - width /szerokość
 * h - height /wysokość
 * board[h][w]
*/

int **create_board(int w, int h){
	if( w <= 0 && h <= 0 ){	
		printf("Nieprawidlowy format wprowadzonych danych\n");
		exit(-4);
	}
	int **board = malloc( h * sizeof(int *));
	if( board == NULL ){
		printf("Nastapil blad poczas alokacji pamieci!\n");
		exit(-4);
	}
	for (int i = 0; i < h; i++){			
		board[i] = malloc( w * sizeof( int ));
	
		if( board[i] == NULL ){
			printf("Nastapil blad poczas alokacji pamieci!\n");
			exit(-4);
		}

		memset( board[i] , 0, w * sizeof (int));
	
	}

	populate_board(board, w, h);

	return board;

}


void populate_board(int **board, int w, int h){
	srand( time( NULL ) );
	int r;
	for( int i = 0; i < h; i++){
		for( int j = 0; j < w; j++){
			r = rand() % 2;
			board[i][j] = r;		

		}

	}
}

void draw_board(int **board, int w, int h){
	for (int i = 0; i < h; i++){
		for(int j = 0; j < w; j++){
			printf("%d ", board[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

