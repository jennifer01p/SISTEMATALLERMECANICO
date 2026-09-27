package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.ClientService;
import co.edu.uptc.domain.model.Client;
import co.edu.uptc.util.MessageProvider;

public class ClientConsoleView {

    private final ClientService clientService;
    private final Scanner sc;

    public ClientConsoleView(ClientService clientService, Scanner sc) {
        this.clientService = clientService;
        this.sc = sc;
    }

    public void showMenu() {
        boolean exist = false;
        String menu = MessageProvider.get("client.menu.title") + "\n"
                + MessageProvider.get("client.menu.1") + "\n"
                + MessageProvider.get("client.menu.2") + "\n"
                + MessageProvider.get("client.menu.3") + "\n"
                + MessageProvider.get("client.menu.4") + "\n"
                + MessageProvider.get("client.menu.5") + "\n"
                + MessageProvider.get("client.menu.6");

        while (!exist) {
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registerClient();
                    break;
                case 2:
                    findByIdClient();
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
                    exist = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }
        }
    }

    public void registerClient() {
        try {
            System.out.println(MessageProvider.get("client.prompt.name"));
            String name = sc.nextLine();
            System.out.println(MessageProvider.get("client.prompt.phone"));
            String phone = sc.nextLine();

            Client cliente = new Client(0, name, phone);
            clientService.register(cliente);
            System.out.println(MessageProvider.get("client.msg.registered"));

        } catch (IllegalArgumentException e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findByIdClient() {
        try {
            System.out.println(MessageProvider.get("client.prompt.id.search"));
            int id = sc.nextInt();
            Client client = clientService.findById(id);
            if (client != null) {
                System.out.println(MessageProvider.get("client.msg.found") + " " + client);
            } else {
                System.out.println(MessageProvider.get("client.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void findAll() {
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) {
            System.out.println(MessageProvider.get("client.msg.emptylist"));
        }
        System.out.println(clients.toString());
    }

    public void update() {
        try {
            System.out.println(MessageProvider.get("client.prompt.id.update"));
            int id = sc.nextInt();
            sc.nextLine();
            Client client = clientService.findById(id);
            if (client != null) {
                System.out.println(MessageProvider.get("client.prompt.newphone"));
                String phone = sc.nextLine();
                client.setPhone(phone);
                clientService.update(client);
                System.out.println(MessageProvider.get("client.msg.updated") + " " + client);
            } else {
                System.out.println(MessageProvider.get("client.msg.notfound"));
            }
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

    public void delete() {
        try {
            System.out.println(MessageProvider.get("client.prompt.id.delete"));
            int id = sc.nextInt();
            boolean deleted = clientService.delete(id);
            System.out.println(deleted
                    ? MessageProvider.get("client.msg.deleted")
                    : MessageProvider.get("client.msg.notdeleted"));
        } catch (Exception e) {
            System.out.println(MessageProvider.get("common.error.prefix") + e.getMessage());
        }
    }

}