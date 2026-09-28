package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.IServiceOrderService;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.enums.OrderStatus;
import co.edu.uptc.util.MessageProvider;

public class ServiceOrderConsoleView {

    private final IServiceOrderService serviceOrderService;
    private final Scanner sc;

    public ServiceOrderConsoleView(IServiceOrderService serviceOrderService, Scanner sc) {
        this.serviceOrderService = serviceOrderService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("serviceorder.menu.title") + "\n"
                + MessageProvider.get("serviceorder.menu.1") + "\n"
                + MessageProvider.get("serviceorder.menu.2") + "\n"
                + MessageProvider.get("serviceorder.menu.3") + "\n"
                + MessageProvider.get("serviceorder.menu.4") + "\n"
                + MessageProvider.get("serviceorder.menu.5") + "\n"
                + MessageProvider.get("serviceorder.menu.6") + "\n"
                + MessageProvider.get("serviceorder.menu.7") + "\n"
                + MessageProvider.get("serviceorder.menu.8");

        while (!volver) {
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    addSparePart();
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    registerWorkedHours();
                    break;
                case 5:
                    closeOrder();
                    break;
                case 6:
                    findById();
                    break;
                case 7:
                    findAll();
                    break;
                case 8:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    public OrderStatus selectStatus() {
        System.out.println(MessageProvider.get("serviceorder.status.title"));
        int opcion = sc.nextInt();
        sc.nextLine();
        OrderStatus status;
        switch (opcion) {
            case 1:
                status = OrderStatus.ENTERED;
                break;
            case 2:
                status = OrderStatus.UNDER_DIAGNOSIS;
                break;
            case 3:
                status = OrderStatus.UNDER_REPAIR;
                break;
            case 4:
                status = OrderStatus.READY_FOR_DELIVERY;
                break;
            case 5:
                status = OrderStatus.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException(MessageProvider.get("serviceorder.msg.invalidstatus"));
        }
        return status;
    }

    public void createOrder() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.plate"));
            String plate = sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.mechanicid"));
            int mechanicId = sc.nextInt();
            sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.diagnosis"));
            String diagnosis = sc.nextLine();

            ServiceOrder order = serviceOrderService.createOrder(plate, mechanicId, diagnosis);
            System.out.println(MessageProvider.get("serviceorder.msg.created") + " " + order);

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void addSparePart() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.id"));
            int orderId = sc.nextInt();
            sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.sparepartcode"));
            String code = sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.quantity"));
            int quantity = sc.nextInt();
            sc.nextLine();

            serviceOrderService.addSparePart(orderId, code, quantity);
            System.out.println(MessageProvider.get("serviceorder.msg.sparepartadded"));

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void changeStatus() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.id"));
            int orderId = sc.nextInt();
            sc.nextLine();
            OrderStatus newStatus = selectStatus();

            serviceOrderService.changeStatus(orderId, newStatus);
            System.out.println(MessageProvider.get("serviceorder.msg.statuschanged"));

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void registerWorkedHours() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.id"));
            int orderId = sc.nextInt();
            sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.hours"));
            double hours = sc.nextDouble();
            sc.nextLine();

            serviceOrderService.registerWorkedHours(orderId, hours);
            System.out.println(MessageProvider.get("serviceorder.msg.hoursregistered"));

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void closeOrder() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.id"));
            int orderId = sc.nextInt();
            sc.nextLine();
            System.out.println(MessageProvider.get("serviceorder.prompt.completedservices"));
            int completedServices = sc.nextInt();
            sc.nextLine();

            ServiceOrder order = serviceOrderService.closeOrder(orderId, completedServices);
            System.out.println(MessageProvider.get("serviceorder.msg.closed") + " " + order);

        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findById() {
        try {
            System.out.println(MessageProvider.get("serviceorder.prompt.id"));
            int id = sc.nextInt();
            ServiceOrder order = serviceOrderService.findById(id);
            System.out.println(MessageProvider.get("serviceorder.msg.found") + " " + order);
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findAll() {
        List<ServiceOrder> orders = serviceOrderService.findAll();
        if (orders.isEmpty()) {
            System.out.println(MessageProvider.get("serviceorder.msg.emptylist"));
        }
        System.out.println(orders.toString());
    }

}