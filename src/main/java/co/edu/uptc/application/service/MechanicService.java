package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.repository.IMechanicRepository;

public class MechanicService implements IMechanicService {

    private final IMechanicRepository mechanicRepository;

    public MechanicService(IMechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public boolean register(Mechanic mechanic) {
        validate(mechanic);
        return mechanicRepository.save(mechanic);
    }

    @Override 
    public Mechanic findById(int id) {
        return mechanicRepository.findById(id);
    }

    @Override
    public List<Mechanic> findAll() {
        return mechanicRepository.findAll();
    }

    @Override 
    public Mechanic update(Mechanic mechanic) {
        validate(mechanic);
        return mechanicRepository.update(mechanic);
    }

    @Override 
    public boolean delete(int id) {
        return mechanicRepository.delete(id);
    }

    private void validate(Mechanic mechanic) {
    if (mechanic.getSpecialty() == null) {
        throw new IllegalArgumentException("Debe seleccionar una especialidad valida.");
    }
    if (mechanic.getHourlyRate() <= 0) {
        throw new IllegalArgumentException("La tarifa por hora debe ser mayor a 0.");
    }
}


}
