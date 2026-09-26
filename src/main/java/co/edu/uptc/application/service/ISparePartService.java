package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.SparePart;

public interface ISparePartService {

    SparePart registerNewSparePart(String code, String name, double unitPrice, int initialStock);

    SparePart restockSparePart(String code, int additionalQuantity);

    boolean hasEnoughStock(String code, int quantity);

    boolean discountStock(String code, int quantity);

    SparePart findByCode(String code);

    List<SparePart> findAll();

}
