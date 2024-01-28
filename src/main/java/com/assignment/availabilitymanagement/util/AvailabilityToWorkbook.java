package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for converting Availability entities to Workbook.
 * Author: Sanskar Sethiya
 */
@Component
public class AvailabilityToWorkbook {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilityToWorkbook.class);

  /**
   * Convert a list of Availability entities to a Workbook.
   *
   * @param availabilities List of Availability entities
   * @return Workbook containing Availability data
   * @throws RuntimeException if there is an error while creating the Workbook
   */
  public static Workbook getAvailabilityWorkbook(List<Availability> availabilities) {
    try {
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
    } catch (Exception e) {
      logger.error("Error while creating Availability workbook", e);
      throw new RuntimeException("Error while creating Availability workbook", e);
    }
  }

  /**
   * Convert an integer array to a comma-separated string.
   *
   * @param intArray Integer array
   * @return Comma-separated string
   */
  public static String intArrayToString(int[] intArray) {
    return String.join(",", Arrays.stream(intArray).mapToObj(String::valueOf).toArray(String[]::new));
  }
}
