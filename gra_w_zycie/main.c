#include <stdio.h>
#include <string.h>
#include "file.h"
#include "board.h"

int main( int argc, char **argv ){

	if( argc == 1 ){
		printf("Nie podano nazwy pliku wejsciowego!\n");
		return 2;
	}

	char ** tab = read_data( argv[1] );
	for( int i = 0; i < 5; i++){
		printf("%s   ", tab[i] );
	}	
	
	printf("\n");
	
	int a = get_width(tab);
	int b = get_height(tab);
	
	printf("Szerokosc jest rowna %d\n", a);
	printf("Wysokosc jest rowna %d\n", b);



	int **board = create_board( a, b);
	draw_board(board, a, b);

	return 0;
}
