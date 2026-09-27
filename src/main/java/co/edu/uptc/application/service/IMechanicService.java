package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.Mechanic;

public interface IMechanicService {
    boolean register(Mechanic mechanic);
    Mechanic findById(int id);
    List<Mechanic> findAll();
    Mechanic update (Mechanic mechanic);
    boolean delete (int id);
    
}
