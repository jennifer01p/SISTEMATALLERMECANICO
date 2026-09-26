package co.edu.uptc.application.service;
import java.util.List;

import co.edu.uptc.domain.model.Client;

public interface IClientService {

    boolean register(Client client);
    Client findById(int id);
    List<Client> findAll();
    Client update (Client client);
    boolean delete (int id);



}
