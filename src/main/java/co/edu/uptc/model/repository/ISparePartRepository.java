package co.edu.uptc.model.repository;

import java.util.List;

import co.edu.uptc.domain.model.SparePart;

public interface ISparePartRepository {
    
    boolean registerNewSparePart(String code, String name, double unitPrice, int initialStock);

    boolean restockSparePart(String code, int additionalQuantity);

    boolean registerOrRestock(String code, String name, double unitPrice, int quantity);

    boolean hasEnoughStock(String code, int quantity);

    boolean discountStock(String code, int quantity);

    SparePart findByCode(String code);

    List<SparePart> findAll();

}
