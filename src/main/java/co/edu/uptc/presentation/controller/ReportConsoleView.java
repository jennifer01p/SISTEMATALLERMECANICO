package co.edu.uptc.presentation.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.dto.ProductivityEntry;
import co.edu.uptc.application.dto.SparePartUsage;
import co.edu.uptc.application.service.IReportService;
import co.edu.uptc.util.InputUtil;
import co.edu.uptc.util.MessageProvider;

public class ReportConsoleView {

    private final IReportService reportService;
    private final Scanner sc;

    public ReportConsoleView(IReportService reportService, Scanner sc) {
        this.reportService = reportService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("report.menu.title") + "\n"
                + MessageProvider.get("report.menu.1") + "\n"
                + MessageProvider.get("report.menu.2") + "\n"
                + MessageProvider.get("report.menu.3") + "\n"
                + MessageProvider.get("report.menu.4") + "\n"
                + MessageProvider.get("report.menu.5") + "\n"
                + MessageProvider.get("report.menu.6") + "\n"
                + MessageProvider.get("report.menu.7");

        while (!volver) {
            System.out.println(menu);
            int opcion = InputUtil.readInt(sc);

            switch (opcion) {
                case 1:
                    incomeBetween();
                    break;
                case 2:
                    topSpareParts();
                    break;
                case 3:
                    productivityPerMechanic();
                    break;
                case 4:
                    exportIncomeCsv();
                    break;
                case 5:
                    exportTopSparePartsCsv();
                    break;
                case 6:
                    exportProductivityCsv();
                    break;
                case 7:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    private void incomeBetween() {
        try {
            System.out.println(MessageProvider.get("report.prompt.from"));
            String from = sc.nextLine();
            System.out.println(MessageProvider.get("report.prompt.to"));
            String to = sc.nextLine();

            double income = reportService.incomeBetween(LocalDate.parse(from), LocalDate.parse(to));
            System.out.println(MessageProvider.get("report.msg.income") + " " + income);
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void topSpareParts() {
        try {
            System.out.println(MessageProvider.get("report.prompt.topn"));
            int topN = InputUtil.readInt(sc);
            List<SparePartUsage> list = reportService.topSpareParts(topN);
            System.out.println(list);
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void productivityPerMechanic() {
        try {
            List<ProductivityEntry> list = reportService.productivityPerMechanic();
            System.out.println(list);
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void exportIncomeCsv() {
        try {
            System.out.println(MessageProvider.get("report.prompt.from"));
            String from = sc.nextLine();
            System.out.println(MessageProvider.get("report.prompt.to"));
            String to = sc.nextLine();
            System.out.println(MessageProvider.get("report.prompt.exportpath"));
            String path = sc.nextLine();

            reportService.exportIncomeToCsv(LocalDate.parse(from), LocalDate.parse(to), path);
            System.out.println(MessageProvider.get("report.msg.exported") + " " + path);
        } catch (IOException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void exportTopSparePartsCsv() {
        try {
            System.out.println(MessageProvider.get("report.prompt.topn"));
            int topN = InputUtil.readInt(sc);
            System.out.println(MessageProvider.get("report.prompt.exportpath"));
            String path = sc.nextLine();

            reportService.exportTopSparePartsToCsv(topN, path);
            System.out.println(MessageProvider.get("report.msg.exported") + " " + path);
        } catch (IOException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

    private void exportProductivityCsv() {
        try {
            System.out.println(MessageProvider.get("report.prompt.exportpath"));
            String path = sc.nextLine();

            reportService.exportProductivityToCsv(path);
            System.out.println(MessageProvider.get("report.msg.exported") + " " + path);
        } catch (IOException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + ": " + e.getMessage());
        }
    }

}
