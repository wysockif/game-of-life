#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "control.h"
#include "board.h"
#include "movement.h"
#include "input_file.h"
#include "output_file.h"
#include "png_drawing.h"
#include "help.h"


void run(int **board, int w, int h, char *type, int argc, char **argv){
	int sbs = 0;
	int outfile = 0;
	int n = 1;
	int png = 0;

	//obsługa flag
	for(int i = 2; i < argc; i++){
		if( strcmp(argv[i] , "-SBS" ) == 0 ){
			sbs = 1;
		} else if( strcmp(argv[i], "-N" ) == 0){
			if (i == argc - 1){
				offer_help();
				printf("Nie podano argumentu -N\n");
				exit(-5);
			}
			else{ 
				if( atoi(argv[i+1]) == 0 && (*argv[i+1] != 0 || *argv[i+1] != '0' )){
					offer_help();
					printf("Błędny argument po -N\n");
					exit(-5);
				}

				n =  atoi(argv[i+1]);
				if(n < 0){
					printf("Błędny argument po -N\n");
					offer_help();
					exit(-5);
				}
				png = 1;
				i++;
			
			}
		} else if (strcmp(argv[i] , "-S" ) == 0 ) { 
			if( i == argc - 1){
				printf("Nie podano pliku do którego zapisac\n");
				offer_help();
				exit(-12);
			} else {
				outfile = i;
				i++;		
			}

		} else if (strcmp(argv[i] , "-H" ) == 0 ) { 
			help();


		} else {
			printf("Nie rozpoznano komendy %s\n!", argv[i]);
			offer_help();
			exit(-5);
		}



	}


	printf("Obsługiwane sąsiedztwo: %s\n", type);	

		char **name = malloc( (n + 1)* sizeof(char*));
	if( png != 0 ){
	       	for( int i = 0; i < n + 1; i++){
			name[i] = malloc( 15 * sizeof(char));
			sprintf(name[i], "rys%d.png", i);
		}
		
		generate_drawing(board, w, h, name[0]);
	}	



	if( sbs == 0 ){
		printf("Na poczatku:\n");	
		draw_board(board, w, h);	
		for( int i = 0; i < n; i++ ){
			if( strcmp(type, "MOOR") == 0){
				m_move(board, w, h);
				if (png != 0 )
					generate_drawing(board, w, h, name[i+1]);

			}
			else if(strcmp(type, "NEUMANN") == 0){
				n_move(board, w, h);
				if (png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			}
		}
		printf("Na końcu:\n");
		draw_board(board, w, h);
	} else if (sbs == 1){
		printf("Na poczatku:\n");
		draw_board(board, w, h);

		
	
		for( int i = 0; i < n; i++){
			if( strcmp(type, "MOOR") == 0){
				m_move(board, w, h);
				if (png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			} else if(strcmp(type, "NEUMANN") == 0) {
				n_move(board, w, h);
				if (png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			}
			printf("Po %d iteracji:\n", i+1);
			
			draw_board(board, w, h);	
			
		}


	}

	if( outfile != 0 ){
 		save( argv[outfile+1], w, h, type, board );

	}


	if( png != 0 ){
	       	for( int i = 0; i < n + 1; i++){
			free(name[i]);
		}
		free(name);
	}



	for( int i = 0; i < h; i++ )
		free(board[i]);
	free(board);
}


