package com.saurabh_project.minimalist_expense_tracker.service;

import java.io.IOException;

public interface IExpenseReportService {
    byte[] generateExpenseExcel(String dateRange, String startDate, String endDate,Long userId) throws IOException;
}

