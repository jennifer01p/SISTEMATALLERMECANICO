package co.edu.uptc.presentation.controller;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.application.service.ClientService;
import co.edu.uptc.domain.model.Client;

public class ClientConsoleView {

    private final ClientService clientService;
    private final Scanner sc;
 
    public ClientConsoleView(ClientService clientService, Scanner sc) {
        this.clientService = clientService;
        this.sc = sc;
    }

    public void showMenu (){
        boolean exist = false ;
        String menu = """
            -- MENU CLIENTES --
            1.Agregar Cliente
            2.Buscar Cliente
            3.Listar Clientes
            4.Actualizar Cliente
            5.Eliminar Cliente 
            6.Volver
            """;

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
                case 4 :
                    update();
                    break;
                case 5 :
                    delete();
                    break;
                case 6 : 
                     exist = true;
                    break ;
                default:
                    System.out.println("Opcion Invalida");
            }
            
        }
    }



    public void registerClient (){
        try {
            System.out.println("Ingrese el nombre");
            String name = sc.nextLine();
            System.out.println("Telefono");
            String phone = sc.nextLine();

            Client cliente = new Client(0, name, phone);
            clientService.register(cliente);
            System.out.println("Cliente registrado");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void findByIdClient(){
        try {
            System.out.println("Ingrese el id del cliente a buscar");
            int id = sc.nextInt();
            Client client = clientService.findById(id);
            if(client != null){
            System.out.println("Cliente enocontrado" + client);
            }else{
            System.out.println("Cliente no encontrado");
            }
            
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        
    }

    public void findAll(){
        List<Client> clients = clientService.findAll();
            if (clients.isEmpty()) {
                System.out.println("Lista de clientes vacia");
            }
            System.out.println(clients.toString());
            return;
    }

    public void update(){
        try {
            System.out.println("Ingrese el id del cliente a actualizar");
            int id = sc.nextInt();
            sc.nextLine(); 
            Client client = clientService.findById(id);
            if (client != null){
                System.out.println("Ingrese el nuevo telefono");
                String phone = sc.nextLine();
                client.setPhone(phone);
                clientService.update(client);
                System.out.println("Cliente actualizado" + client);
            }else{
            System.out.println("Cliente no encontrado");
            }
        }catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void delete(){
        try {
            System.out.println("Ingrese el id del cliente a eliminar");
            int id = sc.nextInt();
            Client client = clientService.findById(id);
            boolean deleted = clientService.delete(id);
            System.out.println(deleted ? "Cliente eliminado." : "No existe un cliente con ese id.");
         
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }



}
