package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.repository.IMechanicRepository;

public class TestMechanicRepository implements IMechanicRepository{

     private List<Mechanic> mechanics = new ArrayList<>();

    @Override
    public boolean save(Mechanic mechanic) {
        mechanics.add(mechanic);
        return true;
    }

    @Override
    public Mechanic findById(int id) {
        for (Mechanic m : mechanics) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Mechanic> findAll() {
        return mechanics;
    }

    @Override
    public Mechanic update(Mechanic mechanic) {
        for (int i = 0; i < mechanics.size(); i++) {
            if (mechanics.get(i).getId() == mechanic.getId()) {
                mechanics.set(i, mechanic);
                return mechanic;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return mechanics.removeIf(m -> m.getId() == id);
    }




}
