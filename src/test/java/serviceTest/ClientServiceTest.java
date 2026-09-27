package serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.service.ClientService;
import co.edu.uptc.domain.model.Client;
import co.edu.uptc.domain.repository.IClientRepository;
import repositoryTest.TestClientRepository;

class ClientServiceTest {

    private IClientRepository repository;
    private ClientService service;

    @BeforeEach
    void setUp() {
        repository = new TestClientRepository();
        service = new ClientService(repository);
    }

    @Test
    void registerValidClientReturnsTrue() {
        Client client = new Client(0, "Juan Perez", "3101234567");

        boolean result = service.register(client);

        assertTrue(result);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void registerClientWithEmptyNameThrowsException() {
        Client client = new Client(0, "", "3101234567");

        assertThrows(IllegalArgumentException.class, () -> service.register(client));
    }

    @Test
    void registerClientWithInvalidPhoneThrowsException() {
        Client client = new Client(0, "Juan Perez", "abc");

        assertThrows(IllegalArgumentException.class, () -> service.register(client));
    }

    @Test
    void findByIdExistingClientReturnsClient() {
        Client client = new Client(1, "Ana Torres", "3111234567");
        repository.save(client);

        Client found = service.findById(1);

        assertEquals("Ana Torres", found.getName());
    }

    @Test
    void findByIdNonExistingClientReturnsNull() {
        Client found = service.findById(99);

        assertNull(found);
    }

    @Test
    void updateExistingClientChangesPhone() {
        Client client = new Client(1, "Luis Rojas", "3121234567");
        repository.save(client);

        client.setPhone("3009998888");
        service.update(client);

        Client updated = service.findById(1);
        assertEquals("3009998888", updated.getPhone());
    }

    @Test
    void deleteExistingClientReturnsTrue() {
        Client client = new Client(1, "Maria Lopez", "3131234567");
        repository.save(client);

        boolean deleted = service.delete(1);

        assertTrue(deleted);
        assertNull(service.findById(1));
    }

    @Test
    void deleteNonExistingClientReturnsFalse() {
        boolean deleted = service.delete(99);

        assertFalse(deleted);
    }

}