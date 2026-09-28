package co.edu.uptc.util;

import java.util.Scanner;

public class InputUtil {

    private InputUtil() {
    }

    public static int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(MessageProvider.get("common.invalid.number"));
            }
        }
    }

    public static double readDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println(MessageProvider.get("common.invalid.number"));
            }
        }
    }
}