package co.edu.uptc.domain.repository;

import java.util.List;

import co.edu.uptc.domain.model.Mechanic;

public interface IMechanicRepository {

    boolean save (Mechanic  mechanic);
    Mechanic findById(int id);
    List<Mechanic> findAll();
    Mechanic update(Mechanic mechanic);
    boolean delete(int id);


}
