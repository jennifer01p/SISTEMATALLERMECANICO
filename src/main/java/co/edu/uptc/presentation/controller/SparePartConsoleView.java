package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.ISparePartService;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.util.InputUtil;
import co.edu.uptc.util.MessageProvider;

public class SparePartConsoleView {

    private final ISparePartService sparePartService;
    private final Scanner sc;

    public SparePartConsoleView(ISparePartService sparePartService, Scanner sc) {
        this.sparePartService = sparePartService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("sparepart.menu.title") + "\n"
                + MessageProvider.get("sparepart.menu.1") + "\n"
                + MessageProvider.get("sparepart.menu.2") + "\n"
                + MessageProvider.get("sparepart.menu.3") + "\n"
                + MessageProvider.get("sparepart.menu.4") + "\n"
                + MessageProvider.get("sparepart.menu.5");

        while (!volver) {
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registerNewSparePart();
                    break;
                case 2:
                    restockSparePart();
                    break;
                case 3:
                    findByCode();
                    break;
                case 4:
                    findAll();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    public void registerNewSparePart() {
        try {
            System.out.println(MessageProvider.get("sparepart.prompt.code"));
            String code = sc.nextLine();
            System.out.println(MessageProvider.get("sparepart.prompt.name"));
            String name = sc.nextLine();
            System.out.println(MessageProvider.get("sparepart.prompt.price"));
            double price = InputUtil.readDouble(sc);
            System.out.println(MessageProvider.get("sparepart.prompt.initialstock"));
            int initialStock = InputUtil.readInt(sc);

            SparePart part = sparePartService.registerNewSparePart(code, name, price, initialStock);
            System.out.println(MessageProvider.get("sparepart.msg.registered") + " " + part);

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void restockSparePart() {
        try {
            System.out.println(MessageProvider.get("sparepart.prompt.code"));
            String code = sc.nextLine();
            System.out.println(MessageProvider.get("sparepart.prompt.quantity"));
            int quantity = InputUtil.readInt(sc);

            SparePart part = sparePartService.restockSparePart(code, quantity);
            System.out.println(MessageProvider.get("sparepart.msg.restocked") + " " + part);

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findByCode() {
        try {
            System.out.println(MessageProvider.get("sparepart.prompt.code"));
            String code = sc.nextLine();
            SparePart part = sparePartService.findByCode(code);
            System.out.println(MessageProvider.get("sparepart.msg.found") + " " + part);
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findAll() {
        List<SparePart> parts = sparePartService.findAll();
        if (parts.isEmpty()) {
            System.out.println(MessageProvider.get("sparepart.msg.emptylist"));
        }
        System.out.println(parts.toString());
    }

}