#include <stdio.h>
#include <string.h>
#include "input_file.h"
#include "output_file.h"
#include "board.h"
#include "movement.h"
#include "control.h"

int main( int argc, char **argv ){

	if( argc == 1 ){
		printf("Nie podano nazwy pliku wejsciowego!\n");
		return 2;
	}

	char ** tab = read_data( open(argv[1] ));
	int a = get_width(tab);
	int b = get_height(tab);
	
	char t[10];
	strcpy( t, get_type(tab) );

	int **board = create_board( a, b);
	run( board, a, b, t, argc, argv);

	return 0;
}
