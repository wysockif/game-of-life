a.out: main.c board.c file.c moor.c neummann.c board.h movement.h
	cc -ggdb main.c board.c file.c moor.c neummann.c
test_movement.out: test_movement.c moor.c neummann.c movement.h
	cc -ggdb test_movement.c moor.c neummann.c -o test_movement.out
	./test_movement
test_file.out: test_file.c file.c file.h
	cc -ggdb test_file.c file.c -o test_file.out
	./test_file dane.txt
test_board.out: test_board.c board.c board.h
	cc -ggdb test_board.c board.c -o test_board.out
	./test_board

