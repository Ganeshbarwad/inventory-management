package com.gt.inventory_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagementApplication {

    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {

        typeLine(">>> INITIALIZING SYSTEM KERNEL...", 25);
        typeLine(">>> LOADING SECURITY MODULES...", 25);
        typeLine(">>> VERIFYING DATABASE LINK...", 25);
        Thread.sleep(400);
        System.out.println();

        printHackerBanner();

        SpringApplication.run(InventoryManagementApplication.class, args);
    }

    private static void typeLine(String text, int delay) throws InterruptedException {
        System.out.print(GREEN);
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(delay);
        }
        System.out.println(RESET);
    }

    private static void printHackerBanner() {

        System.out.println(GREEN + """
				██╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗██╗   ██╗
				██║████╗  ██║██║   ██║██╔════╝████╗  ██║██║   ██║
				██║██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║██║   ██║
				██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║██║   ██║
				██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║╚██████╔╝
				╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝ ╚═════╝
				""");

        System.out.println("""
				[ SYSTEM IDENT: INVENTORY MANAGEMENT ]
				[ AUTHOR      : GANESH ]
				[ PROTOCOL    : ACCESS LEVEL ROOT ]
				""");

        System.out.println(">>> SYSTEM ONLINE..." + RESET);
    }
}
