package co.edu.uptc.presentation.controller;

import java.util.Scanner;

import co.edu.uptc.util.InputUtil;
import co.edu.uptc.util.MessageProvider;

public class BillingReportsConsoleView {

    private final BillingConsoleView billingView;
    private final ReportConsoleView reportView;
    private final Scanner sc;

    public BillingReportsConsoleView(BillingConsoleView billingView, ReportConsoleView reportView, Scanner sc) {
        this.billingView = billingView;
        this.reportView = reportView;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println(MessageProvider.get("billingreports.menu.title"));
            System.out.println(MessageProvider.get("billingreports.menu.1"));
            System.out.println(MessageProvider.get("billingreports.menu.2"));
            System.out.println(MessageProvider.get("billingreports.menu.3"));

            int opcion = InputUtil.readInt(sc);
            switch (opcion) {
                case 1:
                    billingView.showMenu();
                    break;
                case 2:
                    reportView.showMenu();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

}
