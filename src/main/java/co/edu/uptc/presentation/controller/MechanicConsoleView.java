package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.MechanicService;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.enums.MechanicSpecialty;
import co.edu.uptc.util.MessageProvider;

public class MechanicConsoleView {
    private final MechanicService mechanicService;
    private final Scanner sc;

    public MechanicConsoleView(MechanicService mechanicService, Scanner sc) {
        this.mechanicService = mechanicService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean volver = false;
        String menu = MessageProvider.get("mechanic.menu.title") + "\n"
                + MessageProvider.get("mechanic.menu.1") + "\n"
                + MessageProvider.get("mechanic.menu.2") + "\n"
                + MessageProvider.get("mechanic.menu.3") + "\n"
                + MessageProvider.get("mechanic.menu.4") + "\n"
                + MessageProvider.get("mechanic.menu.5") + "\n"
                + MessageProvider.get("mechanic.menu.6");

        while (!volver) {
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registerMechanic();
                    break;
                case 2:
                    findByIdMechanic();
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

    public MechanicSpecialty editSpecialty() {
        System.out.println(MessageProvider.get("mechanic.specialty.title"));
        int opcion = sc.nextInt();
        sc.nextLine();
        MechanicSpecialty specialty;
        switch (opcion) {
            case 1:
                specialty = MechanicSpecialty.ENGINE;
                break;
            case 2:
                specialty = MechanicSpecialty.ELECTRICAL;
                break;
            case 3:
                specialty = MechanicSpecialty.SUSPENSION;
                break;
            case 4:
                specialty = MechanicSpecialty.BRAKES;
                break;
            default:
                throw new IllegalArgumentException(MessageProvider.get("mechanic.msg.invalidspecialty"));
        }
        return specialty;
    }

    public void registerMechanic() {
        try {
            System.out.println(MessageProvider.get("mechanic.prompt.name"));
            String name = sc.nextLine();
            System.out.println(MessageProvider.get("mechanic.prompt.phone"));
            String phone = sc.nextLine();
            MechanicSpecialty mechanicSpecialty = editSpecialty();
            System.out.println(MessageProvider.get("mechanic.prompt.rate"));
            double hourlyRate = sc.nextDouble();
            sc.nextLine();
            Mechanic mechanic = new Mechanic(0, name, phone, mechanicSpecialty, hourlyRate);
            mechanicService.register(mechanic);
            System.out.println(MessageProvider.get("mechanic.msg.registered"));
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findByIdMechanic() {
        try {
            System.out.println(MessageProvider.get("mechanic.prompt.id.search"));
            int id = sc.nextInt();
            Mechanic mechanic = mechanicService.findById(id);
            if (mechanic != null) {
                System.out.println(MessageProvider.get("mechanic.msg.found") + " " + mechanic);
            } else {
                System.out.println(MessageProvider.get("mechanic.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findAll() {
        List<Mechanic> mechanics = mechanicService.findAll();
        if (mechanics.isEmpty()) {
            System.out.println(MessageProvider.get("mechanic.msg.emptylist"));
        }
        System.out.println(mechanics.toString());
    }

    public void update() {
        try {
            System.out.println(MessageProvider.get("mechanic.prompt.id.update"));
            int id = sc.nextInt();
            sc.nextLine();
            Mechanic mechanic = mechanicService.findById(id);
            if (mechanic != null) {
                System.out.println(MessageProvider.get("mechanic.prompt.newphone"));
                String phone = sc.nextLine();
                mechanic.setPhone(phone);
                MechanicSpecialty mechanicSpecialty = editSpecialty();
                mechanic.setSpecialty(mechanicSpecialty);
                System.out.println(MessageProvider.get("mechanic.prompt.newrate"));
                double hourlyRate = sc.nextDouble();
                sc.nextLine();
                mechanic.setHourlyRate(hourlyRate);
                mechanicService.update(mechanic);
                System.out.println(MessageProvider.get("mechanic.msg.updated") + " " + mechanic);
            } else {
                System.out.println(MessageProvider.get("mechanic.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void delete() {
        try {
            System.out.println(MessageProvider.get("mechanic.prompt.id.delete"));
            int id = sc.nextInt();
            boolean deleted = mechanicService.delete(id);
            System.out.println(deleted
                    ? MessageProvider.get("mechanic.msg.deleted")
                    : MessageProvider.get("mechanic.msg.notdeleted"));
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

}