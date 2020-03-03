a.out: main.c board.c file.c moor.c neummann.c control.c board.h movement.h control.h
	cc -Wall -pedantic -ggdb main.c board.c file.c moor.c neummann.c control.c
test_movement: test_movement.c moor.c neummann.c movement.h
	cc -pedantic -Wall -ggdb test_movement.c moor.c neummann.c -o test_movement
	./test_movement
test_file: test_file.c file.c file.h
	cc -pedantic -Wall -ggdb test_file.c file.c -o test_file
	./test_file dane.txt
test_board: test_board.c board.c board.h
	cc -pedantic -Wall -ggdb test_board.c board.c -o test_board
	./test_board
test_control: test_control.c control.c file.c board.c moor.c neummann.c control.h file.h movement.h
	cc -Wall -pedantic -ggdb test_control.c control.c file.c board.c moor.c neummann.c -o test_control
	./test_control
