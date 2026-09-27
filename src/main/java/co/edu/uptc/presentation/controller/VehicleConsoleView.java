package co.edu.uptc.presentation.controller;

import java.util.Scanner;

import co.edu.uptc.application.service.VehicleService;

public class VehicleConsoleView {

    private final VehicleService vehicleService;
    private final Scanner sc;

    public VehicleConsoleView (VehicleService vehicleService, Scanner sc){
        this.vehicleService = vehicleService;
        this.sc = sc;
    }

    public void showMenu(){
        boolean exist = false ;
        String menu = """
                
                """;



        while (!exist) { 
            System.out.println(menu);
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 :
                    
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
            
        }
    }








}
