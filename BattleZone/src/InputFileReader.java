import java.io.File;
import java.util.Scanner;

public class InputFileReader {
    public static void odczytPlikuTekstowego(String plik){

        int s, p, h, x, y, z, k, l, m;

        Scanner scanner = new Scanner(plik);
        for (int i = 0; i < 9; i++) {

            String line = scanner.nextLine();
            String[] lineSplit = line.split("=", 3);
            String part1 = lineSplit[0];
            String part2 = lineSplit[1];
            if (part1.equals("S")) {
                try {
                    s = Integer.parseInt(part2);
                    System.out.println(s);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("P")) {
                try {
                    p = Integer.parseInt(part2);
                    System.out.println(p);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("H")) {
                try {
                    h = Integer.parseInt(part2);
                    System.out.println(h);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("X")) {
                try {
                    x = Integer.parseInt(part2);
                    System.out.println(x);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("Y")) {
                try {
                    y = Integer.parseInt(part2);
                    System.out.println(y);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("Z")) {
                try {
                    z = Integer.parseInt(part2);
                    System.out.println(z);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("K")) {
                try {
                    k = Integer.parseInt(part2);
                    System.out.println(k);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("L")) {
                try {
                    l = Integer.parseInt(part2);
                    System.out.println(l);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else if (part1.equals("M")) {
                try {
                    m = Integer.parseInt(part2);
                    System.out.println(m);
                } catch (NumberFormatException e) {
                    System.out.println("Nie podano liczby");
                    System.exit(0);
                }
            } else {
                System.out.println("Podano zla litere!");
                System.exit(0);
            }
        }

    }
}
