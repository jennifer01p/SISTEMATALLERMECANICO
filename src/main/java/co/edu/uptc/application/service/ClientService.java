package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.Client;
import co.edu.uptc.domain.repository.IClientRepository;

public class ClientService implements IClientService {

    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean register(Client client) {
        validate(client);
        return clientRepository.save(client);
    }

    @Override 
    public Client findById(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override 
    public Client update(Client client) {
        validate(client);
        return clientRepository.update(client);
    }

    @Override 
    public boolean delete(int id) {
        return clientRepository.delete(id);
    }

    private void validate(Client client) {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio.");
        }
        if (client.getPhone() == null || !client.getPhone().matches("\\d{7,10}")) {
            throw new IllegalArgumentException("El telefono debe tener entre 7 y 10 digitos numericos.");
        }
    }

}