a.out: main.c board.c input_file.c output_file.c moor.c neumann.c control.c  board.h movement.h control.h input_file.h output_file.h png_drawing.c png_drawing.h help.c help.h
	cc -pedantic -Wall -ggdb main.c board.c input_file.c moor.c help.c neumann.c control.c output_file.c png_drawing.c -lpng
test_movement: test_movement.c moor.c neumann.c movement.h
	cc -pedantic -Wall -ggdb test_movement.c moor.c neumann.c -o test_movement
	./test_movement
test_input_file: test_input_file.c input_file.c input_file.h
	cc -pedantic -Wall -ggdb test_input_file.c input_file.c help.c -o test_input_file
	./test_input_file dane_do_testu.txt
test_board: test_board.c board.c board.h
	cc -pedantic -Wall -ggdb test_board.c board.c help.c -o test_board 
	./test_board
test_control: test_control.c control.c input_file.c board.c moor.c neumann.c control.h input_file.h movement.h
	cc -Wall -pedantic -ggdb test_control.c control.c input_file.c png_drawing.c output_file.c board.c moor.c neumann.c help.c -o test_control -lpng
	./test_control
test_output_file: test_output_file.c output_file.c output_file.h
	cc -Wall -pedantic test_output_file.c output_file.c -o test_output_file
	./test_output_file
test_png_drawing: png_drawing.c png_drawing.h test_png_drawing.c
	cc -Wall -pedantic png_drawing.c test_png_drawing.c -lpng -o test_png_drawing
	./test_png_drawing
