#include <stdio.h>
#include <stdlib.h>

void offer_help(){
	printf("Jeżeli chcesz skorzystać z POMOCY uruchom program z flagą -H\n");
	printf("Przykład:\n");
	printf("./a.out -H\n");
}


void help(){
	puts("POMOC:");
	puts("1. Prawidłowy format danych w pliku wejściowym;");
	puts("  S X W RODZAJ_SĄSIEDZTWA SPOSÓB_ZAPEŁNIENIA_SIATKI\n");
	puts("  (w przypadku: SPOSÓB_ZAPEŁNIENIA_SIATKI = NORMAL)");
	puts("      następnie \"W\" ciągów po \"S\" znaków składających się z 0 albo 1  \n");
	puts("  S - szerokość, X - znak rodzielenie, W - wysokość");
	puts("  RODZAJ_SASIEDZTWA: MOOR albo NEUMANN");
	puts("  SPOSÓB_ZAPEŁNIENIA_SIATKI: RAND albo NORMAL\n");
	puts("  Przykład 1:");
	puts("	10 X 4 MOOR RAND ");
	puts("  Przykład 2:");
	puts("	5 x 3 NEUMANN NORMAL");
	puts("	10101 ");
	puts("	10111 ");
	puts("	01101 \n");


	puts("2. Uruchomienie:");
	puts(" ./a.out nazwa_pliku_z_danymi.txt + (ewentualne) flagi");
	puts(" Obsługiwane flagi:");
	puts("  -SBS - pyta o kolejny krok");
	puts("  -N n - wykonuje n iteracji i generuje N+1 zdjęc png (jedno ze stanem poczatkowym)");
	puts("  -S file.txt - zapisuje ostatnią generacje do pliku file.txt");
	puts("  -H wyświetla pomoc");
	puts("	Przykład 1:");
	puts("  ./a.out dane.txt -S nowedane.txt -N 3\n");

	puts("	Przykład 2:");
	puts("  ./a.out dane.txt -SBS\n");

	exit(10);

}
