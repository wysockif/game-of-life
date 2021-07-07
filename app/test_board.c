#include <stdio.h>
#include <stdlib.h>
#include "board.h"
#include "help.h"

int main( int argc, char **argv ){
	int a = 5, b = 5;
	char **strings = malloc( 5 * sizeof(char*) );

	int **board = create_board( a, b, 1, strings );
	draw_board(board, a, b);

	free(strings);

	return 0;
}
