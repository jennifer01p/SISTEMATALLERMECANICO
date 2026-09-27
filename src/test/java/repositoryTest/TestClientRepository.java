package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.Client;
import co.edu.uptc.domain.repository.IClientRepository;

public class TestClientRepository implements IClientRepository {

    private List<Client> clients = new ArrayList<>();

    @Override
    public boolean save(Client client) {
        clients.add(client);
        return true;
    }

    @Override
    public Client findById(int id) {
        for (Client c : clients) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client update(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == client.getId()) {
                clients.set(i, client);
                return client;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return clients.removeIf(c -> c.getId() == id);
    }

}