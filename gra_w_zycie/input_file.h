#include <stdio.h>
#include <stdlib.h>

#ifndef FILE_H
#define FILE_H


FILE * open(char *name);

char **read_data(FILE *in);

int get_height(char **tab);

int get_width(char **tab);

char *get_type(char **tab);

int is_rand(char **tab);

char **get_init_gen(char **tab);

void free_args(char **tab);

#endif
