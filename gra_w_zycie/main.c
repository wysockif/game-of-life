#include <stdio.h>
#include <string.h>
#include "input_file.h"
#include "board.h"
#include "movement.h"
#include "control.h"
#include "help.h"

#define ELEMENTS 5

int main( int argc, char **argv ){

	if( argc == 1 ){
		printf("Nie podano nazwy pliku wejsciowego!\n");
		offer_help();
		return 2;
	}

	char ** tab = read_data( open(argv[1]) );
	int a = get_width(tab);
	int b = get_height(tab);
	char t[10];
	strcpy( t, get_type(tab));

	
	char **strings = is_rand(tab) ? NULL : get_init_gen(tab) ;
	
	int **board = create_board( a, b, is_rand(tab), strings);

	free_args(tab);

	run( board, a, b, t, argc, argv );

	return 0;
}
