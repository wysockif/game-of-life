#include <stdio.h>
#include <string.h>
#include "file.h"

int main( int argc, char **argv ){

	if( argc == 1 ){
		printf("Nie podano nazwy pliku wejsciowego!\n");
		return 2;
	}

	char ** tab = read_data( open(argv[1]) );
	for( int i = 0; i < 5; i++){
		printf("%s   ", tab[i] );
	}	
	
	printf("\n");
	printf("Szerokosc jest rowna %d\n", get_width(tab));
	printf("Wysokosc jest rowna %d\n", get_height(tab));

	return 0;
}
