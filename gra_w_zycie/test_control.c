#include <stdio.h>
#include <stdlib.h>
#include "control.h"

int main( int argc, char **argv ){

	int a = 5, b = 5;
	
	char t[10] = "MOOR";


	
	int **board = malloc(5 * sizeof(int*));
	for(int i = 0; i < 5; i++){
		board[i] = malloc(5 * sizeof(int));
				
	}

	for(int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			int k = rand();
			board[i][j] = k % 2;
		}
	}

	run( board, a, b, t, argc, argv);

	return 0;
}
