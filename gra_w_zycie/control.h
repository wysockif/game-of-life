#ifndef CONTROL_H
#define CONTROL_H

void run(int **board, int w, int h, char *type, int argc, char **argv);
void help();
void free_names( char **name, int n, int is_png );
void free_board( int **board, int h);

#endif
