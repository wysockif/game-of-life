#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "board.h"

/* w - width /szerokość
 * h - height /wysokość
 * board[h][w]
*/

int **create_board(int w, int h, int r, char **strings){
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
	if( r == 1 )
		populate_rand(board, w, h);
	else if( r == 0 )
		populate_norm(board, w, h, strings);
	return board;

}

void populate_norm(int **board, int w, int h, char **strings){
	for( int i = 0; i < h; i++ ){
		for( int j = 0; j < w; j++ ){
	
			if( strings[i][j] == '\0' ){
				printf("Za krótka długość wprowadzonych danych!\n");
				exit(-11);
			}
	
			if( strings[i][j] != '0' && strings[i][j] != '1' ){
				printf("Wprowadzono generacja powinna skladac sie tylko z 0 i 1!\n");
				exit(-9);

			}
		
			board[i][j] =  strings[i][j] - '0';
		}
	}

	for( int i = 0; i < h; i++) 
		free(strings[i]);
	free(strings);

}
void populate_rand(int **board, int w, int h){
	srand( time( NULL ) );
	int r;
	for( int i = 0; i < h; i++){
		for( int j = 0; j < w; j++){
			r = rand() % 2;
			board[i][j] = r;		

		}

	}
//	draw_board(board, w, h);
}

void draw_board(int **board, int w, int h){
	for (int i = 0; i < h; i++){
		for(int j = 0; j < w; j++){
			printf("%d  ", board[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

