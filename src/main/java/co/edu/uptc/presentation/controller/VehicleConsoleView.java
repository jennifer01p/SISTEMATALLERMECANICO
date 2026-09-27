package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.VehicleService;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.util.MessageProvider;

public class VehicleConsoleView {

    private final VehicleService vehicleService;
    private final Scanner sc;

    public VehicleConsoleView(VehicleService vehicleService, Scanner sc) {
        this.vehicleService = vehicleService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("vehicle.menu.title") + "\n"
                + MessageProvider.get("vehicle.menu.1") + "\n"
                + MessageProvider.get("vehicle.menu.2") + "\n"
                + MessageProvider.get("vehicle.menu.3") + "\n"
                + MessageProvider.get("vehicle.menu.4") + "\n"
                + MessageProvider.get("vehicle.menu.5") + "\n"
                + MessageProvider.get("vehicle.menu.6");

        while (!volver) {
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registerVehicle();
                    break;
                case 2:
                    findByLicensePlateVehicle();
                    break;
                case 3:
                    findAll();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    public void registerVehicle() {
        try {
            System.out.println(MessageProvider.get("vehicle.prompt.plate"));
            String licensePlate = sc.nextLine();
            System.out.println(MessageProvider.get("vehicle.prompt.brand"));
            String brand = sc.nextLine();
            System.out.println(MessageProvider.get("vehicle.prompt.model"));
            String model = sc.nextLine();
            System.out.println(MessageProvider.get("vehicle.prompt.year"));
            String year = sc.nextLine();
            System.out.println(MessageProvider.get("vehicle.prompt.mileage"));
            double mileage = sc.nextDouble();
            sc.nextLine();

            Vehicle vehicle = new Vehicle(licensePlate, brand, model, year, mileage);
            boolean saved = vehicleService.register(vehicle);
            System.out.println(saved
                    ? MessageProvider.get("vehicle.msg.registered")
                    : MessageProvider.get("vehicle.msg.duplicate"));
        } catch (IllegalArgumentException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findByLicensePlateVehicle() {
        try {
            System.out.println(MessageProvider.get("vehicle.prompt.plate.search"));
            String licensePlate = sc.nextLine();
            Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
            if (vehicle != null) {
                System.out.println(MessageProvider.get("vehicle.msg.found") + " " + vehicle);
            } else {
                System.out.println(MessageProvider.get("vehicle.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findAll() {
        List<Vehicle> vehicles = vehicleService.findAll();
        if (vehicles.isEmpty()) {
            System.out.println(MessageProvider.get("vehicle.msg.emptylist"));
        }
        System.out.println(vehicles.toString());
    }

    public void update() {
        try {
            System.out.println(MessageProvider.get("vehicle.prompt.plate.update"));
            String licensePlate = sc.nextLine();
            Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
            if (vehicle != null) {
                System.out.println(MessageProvider.get("vehicle.prompt.newmileage"));
                double mileage = sc.nextDouble();
                sc.nextLine();
                vehicle.setMileage(mileage);
                vehicleService.update(vehicle);
                System.out.println(MessageProvider.get("vehicle.msg.updated") + " " + vehicle);
            } else {
                System.out.println(MessageProvider.get("vehicle.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void delete() {
        try {
            System.out.println(MessageProvider.get("vehicle.prompt.plate.delete"));
            String licensePlate = sc.nextLine();
            boolean deleted = vehicleService.delete(licensePlate);
            System.out.println(deleted
                    ? MessageProvider.get("vehicle.msg.deleted")
                    : MessageProvider.get("vehicle.msg.notdeleted"));
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

}