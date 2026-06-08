package com.nuist.bookms.util;

import java.util.Scanner;

public class PrintUtil {

    public static Scanner sc = new Scanner(System.in);

    public static void print(String msg, long time) {
        for (char c : msg.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println();
    }

    public static void print(String msg) {
        print(msg, 50);
    }

    public static void print() {
        print("==".repeat(20).concat("\n"), 10);
    }
}
