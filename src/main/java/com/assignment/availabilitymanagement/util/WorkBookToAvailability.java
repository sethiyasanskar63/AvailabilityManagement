package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class to convert an Excel workbook into a list of AvailabilityDTO objects.
 */
@Component
public class WorkBookToAvailability {

  private static final Logger logger = LoggerFactory.getLogger(WorkBookToAvailability.class);

  /**
   * Converts an Excel workbook into a list of AvailabilityDTO objects.
   *
   * @param workbook The Excel workbook to convert.
   * @return A list of AvailabilityDTO objects parsed from the workbook.
   * @throws RuntimeException If there's an error processing the workbook.
   */
  public List<AvailabilityDTO> excelToAvailabilityDTO(Workbook workbook) {
    List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();

    try {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();
      if (rowIterator.hasNext()) {
        rowIterator.next(); // Skipping header row
      }

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        try {
          AvailabilityDTO availabilityDTO = createAvailabilityDTOFromRow(row);
          availabilityDTOs.add(availabilityDTO);
        } catch (Exception e) {
          logger.error("Error processing row, skipping. Error: {}", e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.error("Error while processing Excel sheet to AvailabilityDTO", e);
      throw new RuntimeException("Error while processing Excel sheet to AvailabilityDTO: " + e.getMessage(), e);
    }

    return availabilityDTOs;
  }

  /**
   * Creates an AvailabilityDTO object from a row in the Excel workbook.
   *
   * @param row The row in the Excel workbook.
   * @return An AvailabilityDTO object created from the row data.
   * @throws Exception If there's an error creating the AvailabilityDTO.
   */
  private AvailabilityDTO createAvailabilityDTOFromRow(Row row) throws Exception {
    AvailabilityDTO availabilityDTO = new AvailabilityDTO();

    try {
      availabilityDTO.setAccommodationTypeId((long) row.getCell(0).getNumericCellValue());
      availabilityDTO.setMinNight((int) row.getCell(1).getNumericCellValue());
      availabilityDTO.setStayFromDate(LocalDate.parse(row.getCell(2).getStringCellValue()));
      availabilityDTO.setStayToDate(LocalDate.parse(row.getCell(3).getStringCellValue()));
      availabilityDTO.setArrivalDays(convertStringToDayArray(row.getCell(4).getStringCellValue()));
      availabilityDTO.setDepartureDays(convertStringToDayArray(row.getCell(5).getStringCellValue()));
    } catch (Exception e) {
      logger.error("Error while creating AvailabilityDTO from Excel row: {}", e.getMessage());
      throw e;
    }
    return availabilityDTO;
  }

  /**
   * Converts a comma-separated string of day numbers to an integer array.
   *
   * @param daysString The comma-separated string of day numbers.
   * @return An integer array containing the day numbers.
   */
  private int[] convertStringToDayArray(String daysString) {
    return Arrays.stream(daysString.split(","))
        .map(String::trim)
        .mapToInt(Integer::parseInt)
        .toArray();
  }
}