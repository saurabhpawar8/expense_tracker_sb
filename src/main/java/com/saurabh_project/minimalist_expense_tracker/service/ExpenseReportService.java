package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.repository.TransactionRepository;
import com.saurabh_project.minimalist_expense_tracker.utils.DateRange;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseReportService implements IExpenseReportService {
    private final TransactionRepository transactionRepository;


    private static void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Category", "Amount", "Date", "Time", "Notes"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    private static void populateData(List<Transaction> transactions, Sheet sheet) {
        int rowNum = 1;

        for (Transaction transaction : transactions) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(transaction.getCategory().getName());
            row.createCell(1).setCellValue(String.valueOf(transaction.getAmount()));
            row.createCell(2).setCellValue(transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            row.createCell(3).setCellValue(transaction.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            row.createCell(4).setCellValue(transaction.getNotes());

        }
    }


    @Override
    public byte[] generateExpenseExcel(String dateRange, String startDate, String endDate,Long userId) throws IOException {

        LocalDate[] pairs = new DateRange().determineDateRanges(dateRange, startDate, endDate);
        List<Transaction> transactions = transactionRepository.findByDateBetween(pairs[0], pairs[1]);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");

        createHeaderRow(sheet);

        populateData(transactions, sheet);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();


    }
}
