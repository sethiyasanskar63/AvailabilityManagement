package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class AvailabilityToExcel {

  public Workbook getAvailabilityWorkBook(List<Availability> availabilities){
    Workbook workbook =new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Availability");
    Row headerRow = sheet.createRow(0);

    String[] headers = {"Availability ID", "Accommodation ID", "Accommodation Type ID", "Stay From Date", "Stay To Date", "Arrival Days", "Departure Days"};

    for (int i=0; i< headers.length;i++){
      headerRow.createCell(i).setCellValue(headers[i]);
    }

    int rowNum =1;

    for (Availability availability : availabilities){
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(availability.getAvailabilityId());
      row.createCell(1).setCellValue(availability.getAccommodation() == null ? 0 : availability.getAccommodation().getAccommodationId());
      row.createCell(2).setCellValue(availability.getAccommodationType().getAccommodationTypeId());
      row.createCell(3).setCellValue(availability.getStayFromDate());
      row.createCell(4).setCellValue(availability.getStayToDate());
      row.createCell(5).setCellValue(availability.getArrivalDays());
      row.createCell(6).setCellValue(availability.getDepartureDays());
    }
    return workbook;
  }
}
