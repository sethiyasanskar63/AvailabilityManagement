package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class WorkBookToAvailability {

  private static final Logger logger = LoggerFactory.getLogger(WorkBookToAvailability.class);

  @Autowired
  private AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  /**
   * Converts an Excel workbook to a list of Availability objects.
   *
   * @param workbook The Excel workbook containing Availability data.
   * @return List of Availability objects.
   * @throws RuntimeException if there's an error while processing the workbook.
   */
  public List<Availability> excelToAvailability(Workbook workbook) {
    List<Availability> availabilities = new ArrayList<>();

    try {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();

      if (rowIterator.hasNext()) {
        rowIterator.next(); // Skip header row
      }

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        Availability availability = createAvailabilityFromRow(row);
        availabilities.add(availability);
      }
    } catch (Exception e) {
      logger.error("Error while processing Excel sheet to Availability", e);
      throw new RuntimeException("Error while processing Excel sheet to Availability", e);
    }

    return availabilities;
  }

  /**
   * Creates an Availability object from a row in the Excel sheet.
   *
   * @param row The Excel row containing Availability data.
   * @return The created Availability object.
   * @throws RuntimeException if there's an error while creating the Availability object.
   */
  private Availability createAvailabilityFromRow(Row row) {
    try {
      Availability availability = new Availability();

      availability.setAvailabilityId((long) row.getCell(0).getNumericCellValue());
      availability.setAccommodationType(accommodationTypeServiceImpl.getAccommodationTypes((long) row.getCell(1).getNumericCellValue(), null, null).get(0));
      availability.setMinNight((int) row.getCell(2).getNumericCellValue());
      availability.setStayFromDate(LocalDate.from(row.getCell(3).getLocalDateTimeCellValue()));
      availability.setStayToDate(LocalDate.from(row.getCell(4).getLocalDateTimeCellValue()));
      availability.setArrivalDays(StringToBitMask(row.getCell(5).getStringCellValue()));
      availability.setDepartureDays(StringToBitMask(row.getCell(6).getStringCellValue()));

      return availability;
    } catch (Exception e) {
      logger.error("Error while creating Availability from Excel row", e);
      throw new RuntimeException("Error while creating Availability from Excel row", e);
    }
  }

  /**
   * Converts a comma-separated string of days to a bitmask.
   *
   * @param daysString The comma-separated string of days.
   * @return Bitmask representing the selected days.
   */
  private Integer StringToBitMask(String daysString) {
    try {
      int[] days = Arrays.stream(daysString.split(","))
          .map(String::trim)
          .mapToInt(Integer::parseInt)
          .toArray();
      return DaysOfWeek.setDays(days);
    } catch (Exception e) {
      logger.error("Error while converting days string to bitmask", e);
      throw new RuntimeException("Error while converting days string to bitmask", e);
    }
  }
}
