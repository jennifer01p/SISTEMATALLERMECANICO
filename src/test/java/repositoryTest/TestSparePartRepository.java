package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.repository.ISparePartRepository;

public class TestSparePartRepository implements ISparePartRepository {

    private List<SparePart> parts = new ArrayList<>();

    @Override
    public void save(SparePart part) {
        parts.add(part);
    }

    @Override
    public SparePart findByCode(String code) {
        for (SparePart p : parts) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<SparePart> findAll() {
        return new ArrayList<>(parts);
    }

    @Override
    public SparePart update(SparePart part) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getCode().equals(part.getCode())) {
                parts.set(i, part);
                return part;
            }
        }
        return null;
    }

    @Override
    public boolean delete(String code) {
        return parts.removeIf(p -> p.getCode().equals(code));
    }

}
