package co.edu.uptc.model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.model.repository.IServiceOrderRepository;
import co.edu.uptc.model.repository.ISparePartRepository;
import co.edu.uptc.util.JsonExporter;

public class ReportService {

    private final IServiceOrderRepository orderRepository;
    private final ISparePartRepository spareRepository;
    private final JsonExporter exporter;

    public ReportService(IServiceOrderRepository orderRepository, ISparePartRepository spareRepository) {
        this.orderRepository = orderRepository;
        this.spareRepository = spareRepository;
        this.exporter = new JsonExporter();
    }

    public double incomeBetween(LocalDate from, LocalDate to) {
        List<ServiceOrder> orders = orderRepository.findAll();
        double sum = 0;
        for (ServiceOrder o : orders) {
            if (o.getEntryDate() != null && !o.getEntryDate().isBefore(from) && !o.getEntryDate().isAfter(to)) {
                if (o.getStatus() != null && o.getStatus().name().equals("READY_FOR_DELIVERY")) {
                    sum += o.getTotal();
                }
            }
        }
        return sum;
    }

    public List<SparePartUsage> topSpareParts(int topN) {
        List<ServiceOrder> orders = orderRepository.findAll();
        Map<String, Integer> counts = new HashMap<>();
        for (ServiceOrder o : orders) {
            if (o.getSupplyConsumptions() != null) {
                o.getSupplyConsumptions().forEach(sc -> counts.merge(sc.getCode(), sc.getQuantity(), Integer::sum));
            }
        }

        List<Entry<String, Integer>> sorted = counts.entrySet().stream()
                .sorted(Comparator.comparing(Entry<String, Integer>::getValue).reversed())
                .limit(topN)
                .collect(Collectors.toList());

        List<SparePartUsage> result = new ArrayList<>();
        for (Entry<String, Integer> e : sorted) {
            SparePart sp = spareRepository.findByCode(e.getKey());
            String name = sp != null ? sp.getName() : "(unknown)";
            result.add(new SparePartUsage(e.getKey(), name, e.getValue()));
        }
        return result;
    }

    public List<ProductivityEntry> productivityPerMechanic() {
        List<ServiceOrder> orders = orderRepository.findAll();
        Map<Integer, ProductivityEntry> map = new HashMap<>();
        for (ServiceOrder o : orders) {
            if (o.getMechanic() == null)
                continue;
            if (o.getStatus() != null && o.getStatus().name().equals("READY_FOR_DELIVERY")) {
                int mechId = o.getMechanic().getId();
                ProductivityEntry entry = map.get(mechId);
                if (entry == null) {
                    entry = new ProductivityEntry(mechId, o.getMechanic().getName(), o.getWorkHours());
                    map.put(mechId, entry);
                } else {
                    entry.addHours(o.getWorkHours());
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    public void exportToJson(Object report, String filePath) throws Exception {
        exporter.write(report, filePath);
    }

}

class SparePartUsage {
    private String code;
    private String name;
    private int quantity;

    public SparePartUsage(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}

class ProductivityEntry {
    private int mechanicId;
    private String mechanicName;
    private double hoursWorked;

    public ProductivityEntry(int mechanicId, String mechanicName, double hoursWorked) {
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
        this.hoursWorked = hoursWorked;
    }

    public void addHours(double h) {
        this.hoursWorked += h;
    }

    public int getMechanicId() {
        return mechanicId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }
}
