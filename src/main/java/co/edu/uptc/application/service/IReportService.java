package co.edu.uptc.application.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.application.dto.ProductivityEntry;
import co.edu.uptc.application.dto.SparePartUsage;

public interface IReportService {

    double incomeBetween(LocalDate from, LocalDate to);

    List<SparePartUsage> topSpareParts(int topN);

    List<ProductivityEntry> productivityPerMechanic();

    void exportToJson(Object report, String filePath) throws IOException;

    void exportIncomeToCsv(LocalDate from, LocalDate to, String filePath) throws IOException;

    void exportTopSparePartsToCsv(int topN, String filePath) throws IOException;

    void exportProductivityToCsv(String filePath) throws IOException;

}