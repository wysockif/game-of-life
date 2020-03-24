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


void run( int **board, int w, int h, char *type, int argc, char **argv ){
	int sbs = 0;
	int is_n = 0;
	int is_png = 0;
	int outfile = 0;
	int n = 1;

	//obsługa flag
	for( int i = 2; i < argc; i++ ){
		if( strcmp(argv[i] , "-SBS" ) == 0 ){
			if( is_n != 0 || outfile != 0 ){
				printf( "Podane flagi się wykluczają!\n" );
				offer_help();
				free_board( board, h );
				exit( 304 );
			}

			sbs = 1;
		} else if( strcmp( argv[i], "-N" ) == 0){
			is_n = 1;
			if( sbs == 1 ){
				printf("Podane flagi się wykluczają!\n");
				offer_help();
				free_board( board, h );
				exit( 304 );

			}
			if( i == argc - 1 ){
				printf("Nie podano argumentu -N\n");
				offer_help();
				free_board( board, h);
				exit(301);
			}
			else{ 
				if( atoi(argv[i+1]) == 0 && (*argv[i+1] != 0 || *argv[i+1] != '0' )){
					printf("Błędny argument po -N\n");
					offer_help();
					free_board( board, h);
					exit(305);
				}

				n =  atoi(argv[i+1]);
				if(n < 0){
					printf("Błędny argument po -N\n");
					offer_help();
					free_board( board, h);
					exit(301);
				}

				
		       		if( atof( argv[i+1] ) - n != 0 ){
					printf("Wprowadzony argument po -N nie jest liczbą rzeczywistą!\n");
					offer_help();
					free_board( board, h);
					exit(305);
				} 
				i++;
			
			}
		} else if (strcmp(argv[i] , "-S" ) == 0 ) { 
			if( sbs == 1 ){
				printf("Podane flagi się wykluczają!\n");
				offer_help();
				free_board( board, h);
				exit(301);
			}	
			if( i == argc - 1){
				printf("Nie podano pliku do którego zapisac\n");
				offer_help();
				free_board( board, h);
				exit(301);
			} else {
				outfile = i;
				i++;		
			}

		} else if (strcmp(argv[i] , "-PNG" ) == 0 ) { 
			if( sbs == 1 ){
				printf("Podane flagi się wykluczają!\n");
				offer_help();
				free_board( board, h);
				exit(301);
			}	
			is_png = 1;
			

		} else if(strcmp(argv[i], "-H" ) == 0 ){
			free_board( board, h);
			help();
		
		}else {
			printf("Nie rozpoznano komendy %s!\n", argv[i]);
			printf("Upewnij się, że w razie wystąpienia flag -N lub -S wprowadzono po nich poprawne argumenty\n");
			offer_help();
			free_board( board, h);
			exit(305);
		}



	}

	if( is_png == 1 && is_n != 1 ){
		printf("Flaga -PNG wymaga flagi -N n!");
		offer_help();
		free_board( board, h);
		exit(301);
	}


	if( strcmp( type, "MOOR" ) == 0 )
		printf("Obsługiwane sąsiedztwo: Moore'a\n");	
	else if( strcmp( type, "NEUMANN") == 0 )
		printf("Obsługiwane sąsiedztwo: von Neumanna\n");	

	
	char **name = malloc( (n + 1)* sizeof(char*));
	if( name == NULL ){
		printf("Brak pamięci!\n");
		free_board( board, h );
		free(name);
		exit(201);	
	}
	
	if( is_png != 0 ){
	       	for( int i = 0; i < n + 1; i++ ){
			name[i] = malloc( 15 * sizeof(char));
			sprintf(name[i], "rys%d.png", i);
		
		
		if( name[i] == NULL ){
			printf("Brak pamięci!\n");
			free_board( board, h );
			for( int j = i; j >= 0; j-- )
				free(name[j]);
			exit(201);	
	}
		}
		
		generate_drawing(board, w, h, name[0]);
	}	

	if( is_n == 0 && sbs == 0 )
		printf("Domyślnie ustawiona jest jedna iteracja\n");


	if( sbs == 0 ){
		printf("Na poczatku:\n");	
		draw_board(board, w, h);	
		for( int i = 0; i < n; i++ ){
			if( strcmp(type, "MOOR") == 0 ){
				m_move(board, w, h);
				if (is_png != 0 )
					generate_drawing(board, w, h, name[i+1]);

			}
			else if(strcmp(type, "NEUMANN") == 0 ){
				n_move(board, w, h);
				if (is_png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			}
		
			printf("Po %d iteracji:\n", i+1 );
			draw_board(board, w, h);
		}
	} else if (sbs == 1){
		printf("Na poczatku:\n");
		draw_board(board, w, h);

			
	
		for( int i = 0; i < n; i++){
			char ans[5];
			memset( ans, 0, 5 * sizeof(char) );
			printf("Co chcesz zrobić dalej?\n");
			printf("T - zapisz powyższą generacje do pliku tekstowego (txt)\n");
			printf("P - zapisz powyższą generacje do pliku graficznego (png)\n");
		 	printf("Z - zakończ\n");
			printf("D - dalej\n");
			
			while( 1 ){
			
				scanf( "%s", ans );

				if( ans[1] != '\0' || (ans[0] != 'D' && ans[0] != 'P' && ans[0] != 'T' && ans[0] != 'Z' ) ){
					printf("Nie rozpoznano odpowiedzi!\n");
					continue;
				}
				if( ans[0] == 'D' ){
					n++;
					break;	
				}else if( ans[0] == 'P' ){
					char na[15];
					sprintf( na, "zapis%d.png" , i );
					generate_drawing( board, w, h, na );
					printf("\nWygenerowano plik %s\n", na );
				}else if( ans[0] == 'T' ){	
					char na[15];
					sprintf( na, "zapis%d.txt", i );
					save( na, w, h, type, board );
					printf("\nWygenerowano plik %s\n", na );
				}else if( ans[0] == 'Z' ){
					for( int i = 0; i < h; i++ )
						free(board[i]);
					free(board);
					printf("Zakończono\n");
					exit(1);
				}

				printf("Co chcesz zrobić dalej? (T/P/Z/D)\n");
			}
			
			if( strcmp(type, "MOOR") == 0 ){
				m_move(board, w, h);
				if (is_png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			} else if(strcmp(type, "NEUMANN") == 0 ) {
				n_move(board, w, h);
				if (is_png != 0 )
					generate_drawing(board, w, h, name[i+1]);
			}
			printf("Po %d iteracji:\n", i+1);
			
			draw_board(board, w, h);	
			
		}


	}

	if( outfile != 0 ){

 		save( argv[outfile+1], w, h, type, board );
		printf("Zapisano ostatnią generacje w pliku: %s\n", argv[outfile+1]);
	}


	if( is_png != 0 )
		printf("Wygenerowano %d obrazów png o nazwach rys0.png ... rys%d.png\n", n+1, n);

	free_board( board, h);
	free_names( name, n, is_png );
}



void free_names( char **name, int n, int is_png ){
	if( is_png != 0 )
		for( int i = 0; i < n + 1; i++){
			free(name[i]);
		}

	free(name);
}

void free_board( int **board, int h){
	for( int i = 0; i < h; i++ )
		free(board[i]);
	free(board);
}
