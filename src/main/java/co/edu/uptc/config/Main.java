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

        boolean exist = false ;

        String menu = """
                1.Gestion de Clientes
                2.Gestion de Mecanicos
                3.Gestion de Vehiculo
                4.
                5.
                6.
                7.

                """;


        while(!exist){
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


             case 3 :

                break;
             default:
                System.out.println("Opcion Invalida");
        } 

        }

      
    }
}