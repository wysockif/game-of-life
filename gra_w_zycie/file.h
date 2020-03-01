#include <stdio.h>
#include <stdlib.h>

#ifndef FILE_H
#define FILE_H


FILE * open(char *name);

char **read_data(char *name);

int get_height(char **tab);

int get_width(char **tab);

char *get_type(char **tab);

#endif
