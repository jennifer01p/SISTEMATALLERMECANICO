package co.edu.uptc.application.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import co.edu.uptc.application.dto.ProductivityEntry;
import co.edu.uptc.application.dto.SparePartUsage;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.repository.IServiceOrderRepository;
import co.edu.uptc.domain.repository.ISparePartRepository;
import co.edu.uptc.enums.OrderStatus;
import co.edu.uptc.util.CsvExporter;
import co.edu.uptc.util.JsonExporter;

public class ReportService implements IReportService {

    private final IServiceOrderRepository orderRepository;
    private final ISparePartRepository sparePartRepository;
    private final JsonExporter jsonExporter;
    private final CsvExporter csvExporter;

    public ReportService(IServiceOrderRepository orderRepository, ISparePartRepository sparePartRepository) {
        this.orderRepository = orderRepository;
        this.sparePartRepository = sparePartRepository;
        this.jsonExporter = new JsonExporter();
        this.csvExporter = new CsvExporter();
    }

    @Override
    public double incomeBetween(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("El rango de fechas no es válido");
        }
        double sum = 0;
        for (ServiceOrder order : orderRepository.findAll()) {
            boolean inRange = order.getEntryDate() != null
                    && !order.getEntryDate().isBefore(from)
                    && !order.getEntryDate().isAfter(to);
            if (inRange && order.getStatus() == OrderStatus.READY_FOR_DELIVERY) {
                sum += order.getTotal();
            }
        }
        return sum;
    }

    @Override
    public List<SparePartUsage> topSpareParts(int topN) {
        Map<String, Integer> counts = new HashMap<>();
        for (ServiceOrder order : orderRepository.findAll()) {
            if (order.getSupplyConsumptions() != null) {
                order.getSupplyConsumptions()
                        .forEach(consumption -> counts.merge(consumption.getCode(), consumption.getQuantity(), Integer::sum));
            }
        }

        List<Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
        sorted.sort(Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Entry.comparingByKey()));

        List<SparePartUsage> result = new ArrayList<>();
        for (Entry<String, Integer> entry : sorted) {
            if (result.size() >= topN) {
                break;
            }
            SparePart part = sparePartRepository.findByCode(entry.getKey());
            String name = part != null ? part.getName() : "(unknown)";
            result.add(new SparePartUsage(entry.getKey(), name, entry.getValue()));
        }
        return result;
    }

    @Override
    public List<ProductivityEntry> productivityPerMechanic() {
        Map<Integer, ProductivityEntry> entries = new HashMap<>();
        for (ServiceOrder order : orderRepository.findAll()) {
            if (order.getMechanic() == null || order.getStatus() != OrderStatus.READY_FOR_DELIVERY) {
                continue;
            }
            int mechanicId = order.getMechanic().getId();
            ProductivityEntry entry = entries.get(mechanicId);
            if (entry == null) {
                entries.put(mechanicId,
                        new ProductivityEntry(mechanicId, order.getMechanic().getName(), order.getWorkHours()));
            } else {
                entry.addHours(order.getWorkHours());
            }
        }
        return new ArrayList<>(entries.values());
    }

    @Override
    public void exportToJson(Object report, String filePath) throws IOException {
        jsonExporter.write(report, filePath);
    }

    @Override
    public void exportIncomeToCsv(LocalDate from, LocalDate to, String filePath) throws IOException {
        double income = incomeBetween(from, to);
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[] { from.toString(), to.toString(), String.valueOf(income) });
        csvExporter.write(new String[] { "from", "to", "income" }, rows, filePath);
    }

    @Override
    public void exportTopSparePartsToCsv(int topN, String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        for (SparePartUsage usage : topSpareParts(topN)) {
            rows.add(new String[] { usage.getCode(), usage.getName(), String.valueOf(usage.getQuantity()) });
        }
        csvExporter.write(new String[] { "code", "name", "quantity" }, rows, filePath);
    }

    @Override
    public void exportProductivityToCsv(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        for (ProductivityEntry entry : productivityPerMechanic()) {
            rows.add(new String[] { String.valueOf(entry.getMechanicId()), entry.getMechanicName(),
                    String.valueOf(entry.getHoursWorked()) });
        }
        csvExporter.write(new String[] { "mechanicId", "mechanicName", "hoursWorked" }, rows, filePath);
    }

}