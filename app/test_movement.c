#include <stdio.h>
#include <stdlib.h>
#include "movement.h"

int main( int argc, char **argv ){
	int **tab = malloc(5 * sizeof(int*));
	for(int i = 0; i < 5; i++){
		tab[i] = malloc(5 * sizeof(int));
	
	}

	for(int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			int k = rand();
			tab[i][j] = k % 2;
		}
	}

	printf("Przed:\n");

	for (int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			printf("%d,  ", tab[i][j]);
		}
		printf("\n");
	}
	printf("\n");

	m_move(tab, 5, 5);
	
	printf("Po \"Moor\":\n");

	for (int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			printf("%d,  ", tab[i][j]);
		}
		printf("\n");
	}
	printf("\n");
	
	
	m_move(tab, 5, 5);


	printf("Nastepnie po \"Neumann\":\n");
	
	for (int i = 0; i < 5; i++){
		for(int j = 0; j < 5; j++){
			printf("%d,  ", tab[i][j]);
		}
		printf("\n");
	}
	printf("\n");

	for(int i = 0; i < 5; i++){
		free(tab[i]);
	}
	free(tab);
	
	return 0;
}
