package co.edu.uptc.config;

import java.util.Scanner;

import co.edu.uptc.application.service.BillingService;
import co.edu.uptc.application.service.ClientService;
import co.edu.uptc.application.service.IReportService;
import co.edu.uptc.application.service.IServiceOrderService;
import co.edu.uptc.application.service.ISparePartService;
import co.edu.uptc.application.service.MechanicService;
import co.edu.uptc.application.service.ReportService;
import co.edu.uptc.application.service.ServiceOrderService;
import co.edu.uptc.application.service.SparePartService;
import co.edu.uptc.application.service.VehicleService;
import co.edu.uptc.domain.repository.IClientRepository;
import co.edu.uptc.domain.repository.IMechanicRepository;
import co.edu.uptc.domain.repository.IServiceOrderRepository;
import co.edu.uptc.domain.repository.ISparePartRepository;
import co.edu.uptc.domain.repository.IVehicleRepository;
import co.edu.uptc.infraestructure.persistence.JsonClientRepository;
import co.edu.uptc.infraestructure.persistence.JsonMechanicRepository;
import co.edu.uptc.infraestructure.persistence.JsonServiceOrderRepository;
import co.edu.uptc.infraestructure.persistence.JsonSparePartRepository;
import co.edu.uptc.infraestructure.persistence.JsonVehicleRepository;
import co.edu.uptc.presentation.controller.BillingConsoleView;
import co.edu.uptc.presentation.controller.BillingReportsConsoleView;
import co.edu.uptc.presentation.controller.ClientConsoleView;
import co.edu.uptc.presentation.controller.MechanicConsoleView;
import co.edu.uptc.presentation.controller.ReportConsoleView;
import co.edu.uptc.presentation.controller.ServiceOrderConsoleView;
import co.edu.uptc.presentation.controller.SparePartConsoleView;
import co.edu.uptc.presentation.controller.VehicleConsoleView;
import co.edu.uptc.util.MessageProvider;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        IClientRepository clientRepository = new JsonClientRepository();
        IVehicleRepository vehicleRepository = new JsonVehicleRepository();
        IMechanicRepository mechanicRepository = new JsonMechanicRepository();
        ISparePartRepository sparePartRepository = new JsonSparePartRepository();
        IServiceOrderRepository serviceOrderRepository = new JsonServiceOrderRepository();

        ClientService clientService = new ClientService(clientRepository);
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        MechanicService mechanicService = new MechanicService(mechanicRepository);
        ISparePartService sparePartService = new SparePartService(sparePartRepository);
        IServiceOrderService serviceOrderService = new ServiceOrderService(
                serviceOrderRepository, sparePartService, vehicleRepository, mechanicRepository);

        BillingService billingService = new BillingService(serviceOrderRepository);
        IReportService reportService = new ReportService(serviceOrderRepository, sparePartRepository);

        ClientConsoleView clientView = new ClientConsoleView(clientService, sc);
        VehicleConsoleView vehicleView = new VehicleConsoleView(vehicleService, sc);
        MechanicConsoleView mechanicView = new MechanicConsoleView(mechanicService, sc);
        SparePartConsoleView sparePartView = new SparePartConsoleView(sparePartService, sc);
        ServiceOrderConsoleView serviceOrderView = new ServiceOrderConsoleView(serviceOrderService, sc);
        BillingConsoleView billingView = new BillingConsoleView(billingService, sc);
        ReportConsoleView reportView = new ReportConsoleView(reportService, sc);
        BillingReportsConsoleView billingReportsView = new BillingReportsConsoleView(billingView, reportView, sc);

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
                    serviceOrderView.showMenu();
                    break;
                case 5:
                    sparePartView.showMenu();
                    break;
                case 6:
                    billingReportsView.showMenu();
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
        String menu = MessageProvider.get("language.title");
        System.out.println(menu);
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                MessageProvider.setLanguage("es");
                System.out.println(MessageProvider.get("language.changed.es"));
                break;
            case 2:
                MessageProvider.setLanguage("en");
                System.out.println(MessageProvider.get("language.changed.en"));
                break;
            default:
                System.out.println(MessageProvider.get("common.invalid.option"));
        }
    }
}