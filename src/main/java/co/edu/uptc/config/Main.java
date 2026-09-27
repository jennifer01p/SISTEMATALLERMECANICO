package co.edu.uptc.config;

import java.util.Scanner;

import co.edu.uptc.application.service.ClientService;
import co.edu.uptc.application.service.MechanicService;
import co.edu.uptc.application.service.VehicleService;
import co.edu.uptc.domain.repository.IClientRepository;
import co.edu.uptc.domain.repository.IMechanicRepository;
import co.edu.uptc.domain.repository.IVehicleRepository;
import co.edu.uptc.infraestructure.persistence.JsonClientRepository;
import co.edu.uptc.infraestructure.persistence.JsonMechanicRepository;
import co.edu.uptc.infraestructure.persistence.JsonVehicleRepository;
import co.edu.uptc.presentation.controller.ClientConsoleView;
import co.edu.uptc.presentation.controller.MechanicConsoleView;
import co.edu.uptc.presentation.controller.VehicleConsoleView;
import co.edu.uptc.util.MessageProvider;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        IClientRepository clientRepository = new JsonClientRepository();
        IVehicleRepository vehicleRepository = new JsonVehicleRepository();
        IMechanicRepository mechanicRepository = new JsonMechanicRepository();


        ClientService clientService = new ClientService(clientRepository);
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        MechanicService mechanicService = new MechanicService(mechanicRepository);

        ClientConsoleView clientView = new ClientConsoleView(clientService, sc);
        VehicleConsoleView vehicleView = new VehicleConsoleView(vehicleService, sc);
        MechanicConsoleView mechanicView = new MechanicConsoleView(mechanicService, sc);

        boolean salir = false;

        while (!salir) {
            String menu = MessageProvider.get("mainmenu.1") + "\n"
                    + MessageProvider.get("mainmenu.2") + "\n"
                    + MessageProvider.get("mainmenu.3") + "\n"
                    + MessageProvider.get("mainmenu.4") + "\n"
                    + MessageProvider.get("mainmenu.5") + "\n"
                    + MessageProvider.get("mainmenu.6") + "\n"
                    + MessageProvider.get("mainmenu.7") + "\n"
                    + MessageProvider.get("mainmenu.8");

            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    clientView.showMenu();
                    break;
                case 2:
                    mechanicView.showMenu();
                    break;
                case 3:
                    vehicleView.showMenu();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    changeLanguage(sc);
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println(MessageProvider.get("common.invalid.option"));
            }

        }

    }

    private static void changeLanguage(Scanner scanner) {
        String menu ="""
                1.Espanol
                2.English
                """;
        System.out.println(menu);
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                MessageProvider.setLanguage("es");
                System.out.println("Idioma cambiado a Espanol");
                break;
            case 2:
                MessageProvider.setLanguage("en");
                System.out.println("Language changed to English");
                break;
            default:
                System.out.println("Opcion invalida");
        }
    }
}