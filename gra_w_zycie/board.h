#ifndef BOARD_H
#define BOARD_H

int **create_board( int w, int h, int r, char **strings );
void populate_rand( int **board, int w, int h );
void draw_board( int **board, int w, int h );
void populate_norm( int **board, int w, int h, char **strings );

#endif
