package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.MechanicService;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.enums.MechanicSpecialty;

public class MechanicConsoleView {
    private final MechanicService mechanicService;
    private final Scanner sc;

    public MechanicConsoleView(MechanicService mechanicService,Scanner sc){
        this.mechanicService = mechanicService;
        this.sc = sc;
    }


    public void showMenu(){
        boolean volver = false;
        String menu = """
            1.Agregar Mecanico
            2.Busar Mecanico
            3.Listar Mecanicos
            4.Editar Mecanico
            5.Eliminar Mecanico
            6.Volver
                """;

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
                   System.out.println("opcion Invalida");
            }
            
        }
    }


    public MechanicSpecialty editSpecialty(){
        String menu = """
                1.ENGINE
                2.ELECTRICAL
                3.SUSPENSION
                4.BRAKES
                """;

        System.out.println(menu);
        int opcion = sc.nextInt();
        sc.nextLine();
        MechanicSpecialty specialty = null;
        switch (opcion) {
            case 1 :
                specialty = MechanicSpecialty.ENGINE;
                break;

            case 2 :
                specialty = MechanicSpecialty.ELECTRICAL;
                break;
            case 3 :
                specialty = MechanicSpecialty.SUSPENSION;
                break;
            case 4 :
                specialty = MechanicSpecialty.BRAKES;
                break;
            default:
                System.out.println("Opcion Invalida");
        }

        return specialty;
        

    }

    public void registerMechanic(){
        try {
            System.out.println("Ingrese el nombre de el mecanico");
            String name = sc.nextLine();
            System.out.println("Ingrese el numero de telefono");
            String phone = sc.nextLine();
            MechanicSpecialty mechanicSpecialty = editSpecialty();
            System.out.println("Ingrese la tarifa por hora");
            double hourlyRate = sc.nextDouble();
            sc.nextLine(); 
            Mechanic mechanic = new Mechanic(0,name,phone,mechanicSpecialty,hourlyRate);
            mechanicService.register(mechanic);
            System.out.println("Mecanico Registrado");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

    }

    public void findByIdMechanic(){
        try {
            System.out.println("Ingrese el id del mecanico a buscar");
            int id = sc.nextInt();
            Mechanic mechanic = mechanicService.findById(id);
            if(mechanic != null){
            System.out.println("Mecanico enocontrado" + mechanic);
            }else{
            System.out.println("Mecanico no encontrado");
            }
            
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        
    }

    public void findAll(){
        List<Mechanic> mechanics = mechanicService.findAll();
            if (mechanics.isEmpty()) {
                System.out.println("Lista de clientes vacia");
            }
            System.out.println(mechanics.toString());
            return;
    }

    public void update(){
        try {
            System.out.println("Ingrese el id del mecanico a actualizar");
            int id = sc.nextInt();
            sc.nextLine(); 
            Mechanic mechanic =  mechanicService.findById(id);
            if (mechanic != null){
                System.out.println("Ingrese el nuevo telefono");
                String phone = sc.nextLine();
                mechanic.setPhone(phone);
                MechanicSpecialty mechanicSpecialty = editSpecialty();
                mechanic.setSpecialty(mechanicSpecialty);
                System.out.println("Ingrese la nueva tarifa por hora");
                double hourlyRate = sc.nextDouble();
                mechanic.setHourlyRate(hourlyRate);
                mechanicService.update(mechanic);
                System.out.println("Mecanico actualizado" + mechanic);
            }else{
            System.out.println("Mecanico no encontrado");
            }
        }catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void delete(){
        try {
            System.out.println("Ingrese el id del mecanico a eliminar");
            int id = sc.nextInt();
            Mechanic mechanic = mechanicService.findById(id);
            boolean deleted = mechanicService.delete(id);
            System.out.println(deleted ? "Mecanico eliminado." : "No existe un mecanico con ese id.");
         
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }







}
