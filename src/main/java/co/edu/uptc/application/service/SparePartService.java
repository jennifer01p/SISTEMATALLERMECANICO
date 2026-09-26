package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.exception.DuplicateCodeException;
import co.edu.uptc.domain.exception.InsufficientStockException;
import co.edu.uptc.domain.exception.SparePartNotFoundException;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.repository.ISparePartRepository;

public class SparePartService implements ISparePartService{

    public final ISparePartRepository repository;

    public SparePartService(ISparePartRepository repository) {
        this.repository = repository;
    }

    @Override
    public SparePart registerNewSparePart(String code, String name, double unitPrice, int initialStock) {
        
        if(repository.findByCode(code) != null){
            throw new DuplicateCodeException("El repuesto ya existe");
        }

        SparePart newPart = new SparePart(code, name, unitPrice, initialStock);
        repository.save(newPart);
        return newPart;
    }

    @Override
    public SparePart restockSparePart(String code, int additionalQuantity) {
        SparePart part = repository.findByCode(code);

        if(part == null){
            throw new SparePartNotFoundException("No existen repuestos con ese codigo");
        }

        int currentStock = part.getStock();
        int newStock = currentStock + additionalQuantity;
        part.setStock(newStock);
        repository.update(part);
        return part;
    }

    @Override
    public boolean hasEnoughStock(String code, int quantity) {
        SparePart part = repository.findByCode(code);
        if(part == null){
            throw new SparePartNotFoundException("No existen repuestos con ese codigo");
        }
        
        return part.getStock() >= quantity;
    }

    @Override
    public boolean discountStock(String code, int quantity) {

        SparePart part = repository.findByCode(code);
        
        if(!hasEnoughStock(code, quantity)){
            throw new InsufficientStockException("No hay suficiente stock");
        }

        int currentStock = part.getStock();
        int newStock = currentStock - quantity;
        part.setStock(newStock);
        repository.update(part);
        return true;

    }

    @Override
    public SparePart findByCode(String code) {
        SparePart part = repository.findByCode(code);
        if(part == null){
            throw new SparePartNotFoundException("No existen repuestos con ese código");
        }
        return part;
    }

    @Override
    public List<SparePart> findAll() {
        return repository.findAll();
    }

}
