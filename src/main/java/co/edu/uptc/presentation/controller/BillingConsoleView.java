package co.edu.uptc.presentation.controller;

import java.io.IOException;
import java.util.Scanner;

import co.edu.uptc.application.dto.Invoice;
import co.edu.uptc.application.service.IBillingService;
import co.edu.uptc.util.InputUtil;
import co.edu.uptc.util.JsonExporter;
import co.edu.uptc.util.MessageProvider;

public class BillingConsoleView {

    private final IBillingService billingService;
    private final Scanner sc;
    private final JsonExporter jsonExporter;

    public BillingConsoleView(IBillingService billingService, Scanner sc) {
        this.billingService = billingService;
        this.sc = sc;
        this.jsonExporter = new JsonExporter();
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("billing.menu.title") + "\n"
                + MessageProvider.get("billing.menu.1") + "\n"
                + MessageProvider.get("billing.menu.2") + "\n"
                + MessageProvider.get("billing.menu.3");

        while (!volver) {
            System.out.println(menu);
            int opcion = InputUtil.readInt(sc);

            switch (opcion) {
                case 1:
                    generateAndPrintInvoice();
                    break;
                case 2:
                    generateAndExportInvoice();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    private void generateAndPrintInvoice() {
        try {
            System.out.println(MessageProvider.get("billing.prompt.orderid"));
            int orderId = InputUtil.readInt(sc);
            System.out.println(MessageProvider.get("billing.prompt.completedservices"));
            int completed = InputUtil.readInt(sc);

            Invoice invoice = billingService.generateInvoice(orderId, completed);
            System.out.println(MessageProvider.get("billing.msg.generated"));
            System.out.println(invoice);

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void generateAndExportInvoice() {
        try {
            System.out.println(MessageProvider.get("billing.prompt.orderid"));
            int orderId = InputUtil.readInt(sc);
            System.out.println(MessageProvider.get("billing.prompt.completedservices"));
            int completed = InputUtil.readInt(sc);
            System.out.println(MessageProvider.get("billing.prompt.exportpath"));
            String path = sc.nextLine();

            Invoice invoice = billingService.generateInvoice(orderId, completed);
            jsonExporter.write(invoice, path);
            System.out.println(MessageProvider.get("billing.msg.exported") + " " + path);

        } catch (IOException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

}
