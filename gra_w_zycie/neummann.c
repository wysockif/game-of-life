#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "movement.h"
#include "board.h"

/* w - width /szerokość
 * h - height /wysokość
 * board[h][w]
*/

void n_move(int **board, int w, int h){
	int before[h+2][w+2];

	memset(before, 0, (h+2)*(w+2) * sizeof(int));

	//srodek
	for( int i = 0; i < h; i++){
		for (int j = 0; j < w; j++){
			before[i+1][j+1] = board[i][j];

		}
	}

	//krawedzie
	for (int i = 0; i < h; i++){
		before[i+1][0] = board[i][w-1];
		before[i+1][w+1] = board[i][0];
	}

	for( int j = 0; j < w; j++ ){
		before[0][j+1] = board[h-1][j];
		before[h+1][j+1] = board[0][j];
	}

	//rogi
	before[0][0] = board[h-1][w-1];
	before[0][w+1] = board[h-1][0];
	before[h+1][0] = board[0][w-1];
	before[h+1][w+1] = board[0][0];

	/*
	// rysowanie duzej planszy	
	for (int i = 0; i < h+2; i++){
		for(int j = 0; j < w+2; j++){
			printf("%d,  ", before[i][j]);
		}
		printf("\n");
	}
	printf("\n");
	*/

	for( int i = 1; i < h + 1 ; i++){
		for( int j = 1; j < w + 1 ; j++){
			int n = 0;
			if ( before[i-1][j] == 1 )
				n++;
			if( before[i][j-1] == 1 )
				n++;
			if ( before[i][j+1] == 1 )
				n++;
			if ( before[i+1][j] == 1 )
				n++;


			if (n == 3 && before[i][j] == 0 ){
				board[i-1][j-1] = 1;
			}	
			if ((n != 2 &&  n != 3) && before[i][j] == 1 ){
				board[i-1][j-1] = 0;
			}
	
		}
	}

//	draw_board(board, w, h);

}
