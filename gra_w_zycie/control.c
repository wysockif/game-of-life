#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "control.h"
#include "board.h"
#include "movement.h"
#include "file.h"

void run(int **board, int w, int h, char *type, int argc, char **argv){
	int sbs = 0;
	int n = 5;

	printf("Obsługiwane sąsiedztwo: %s\n", type);	

	//obsługa flag
	for(int i = 2; i < argc; i++){
		if( strcmp(argv[i] , "-SBS" ) == 0 ){
			sbs = 1;
		} else if( strcmp(argv[i], "-N" ) == 0){
			if (i == argc - 1){
				printf("Nie podano argumentu -N\n");
				exit(-5);
			}
			else{ 
				if( atoi(argv[i+1]) == 0 && (*argv[i+1] != 0 || *argv[i+1] != '0' )){
					printf("Błędny argument po -N\n");
					exit(-5);
				}

				n =  atoi(argv[i+1]);
				if(n < 0){
					printf("Błędny argument po -N\n");
					exit(-5);
				}
				
				i++;
			
			}
		} else {
			printf("Nie rozpoznano komendy %s\n!", argv[i]);
			exit(-5);
		}



	}

	if( sbs == 0 ){
		printf("Na poczatku:\n");	
		draw_board(board, w, h);	
		for( int i = 0; i < n; i++ ){
			if( strcmp(type, "MOOR") == 0)
				m_move(board, w, h);
			else if(strcmp(type, "NEUMMAN") == 0)
				n_move(board, w, h);
		}
		printf("Na końcu:\n");
		draw_board(board, w, h);
	} else if (sbs == 1){
		printf("Na poczatku:\n");
		draw_board(board, w, h);
		for( int i = 0; i < n; i++){
			if( strcmp(type, "MOOR") == 0)
				m_move(board, w, h);
			else if(strcmp(type, "NEUMMAN") == 0)
				n_move(board, w, h);
			printf("Po %d iteracji:\n", i+1);
			
			draw_board(board, w, h);	
			
		}


	}
}
