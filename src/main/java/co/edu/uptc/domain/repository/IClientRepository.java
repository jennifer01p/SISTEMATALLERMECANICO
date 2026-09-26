package co.edu.uptc.domain.repository;
import java.util.List;

import co.edu.uptc.domain.model.Client;

public interface IClientRepository {

    boolean save (Client  client);
    Client findById(int id);
    List<Client> findAll();
    Client update(Client client);
    boolean delete(int id);

}
