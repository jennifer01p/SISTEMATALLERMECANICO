package co.edu.uptc.domain.repository;

import java.util.List;

import co.edu.uptc.domain.model.SparePart;

public interface ISparePartRepository {
    
    void save(SparePart part);

    SparePart findByCode(String code);

    List<SparePart> findAll();

    SparePart update(SparePart part);

    boolean delete(String code);

}
