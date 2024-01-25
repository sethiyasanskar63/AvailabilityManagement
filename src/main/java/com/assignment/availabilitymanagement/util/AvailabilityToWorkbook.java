package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.entity.DaysOfWeek;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Component
public class AvailabilityToWorkbook {

  public static Workbook getAvailabilityWorkbook(List<Availability> availabilities) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Availability");
    Row headerRow = sheet.createRow(0);

    String[] headers = {"Availability ID", "Accommodation Type ID", "Minimum Nights", "Stay From Date", "Stay To Date", "Arrival Days", "Departure Days"};

    for (int i = 0; i < headers.length; i++) {
      headerRow.createCell(i).setCellValue(headers[i]);
    }

    int rowNum = 1;

    for (Availability availability : availabilities) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(availability.getAvailabilityId());
      row.createCell(1).setCellValue(availability.getAccommodationType().getAccommodationTypeId());
      row.createCell(2).setCellValue(availability.getMinNight());
      row.createCell(3).setCellValue(availability.getStayFromDate());
      row.createCell(4).setCellValue(availability.getStayToDate());
      row.createCell(5).setCellValue(intArrayToString(DaysOfWeek.getSelectedDays(availability.getArrivalDays())));
      row.createCell(6).setCellValue(intArrayToString(DaysOfWeek.getSelectedDays(availability.getDepartureDays())));
    }
    return workbook;
  }

  public static String intArrayToString(int[] intArray){
    return String.join(",", Arrays.stream(intArray).mapToObj(String::valueOf).toArray(String[]::new));
  }
}
