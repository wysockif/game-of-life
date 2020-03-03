#include <stdio.h>
#include <stdlib.h>
#include "board.h"

int main( int argc, char **argv ){
	int a = 5, b = 5;
	int **board = create_board( a, b);
	draw_board(board, a, b);

	return 0;
}
