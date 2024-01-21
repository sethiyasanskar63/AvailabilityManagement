package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
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
import java.util.Iterator;
import java.util.List;

@Component
public class WorkBookToAvailability {

  private static final Logger logger = LoggerFactory.getLogger(WorkBookToAvailability.class);

  @Autowired
  AccommodationServiceImpl accommodationServiceImpl;
  @Autowired
  AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  public List<Availability> excelToAvailability(Workbook workbook) {
    List<Availability> availabilities = new ArrayList<>();

    try {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();

      if (rowIterator.hasNext()) {
        rowIterator.next();
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

  private Availability createAvailabilityFromRow(Row row) {
    try {
      Availability availability = new Availability();

      availability.setAvailabilityId((long) row.getCell(0).getNumericCellValue());
      availability.setAccommodation(row.getCell(1).getNumericCellValue() == 0 ? null : accommodationServiceImpl.getAccommodations((long) row.getCell(1).getNumericCellValue(), null, null).get(0));
      availability.setAccommodationType(accommodationTypeServiceImpl.getAccommodationTypes((long) row.getCell(2).getNumericCellValue(), null, null).get(0));
      availability.setMinNight((int) row.getCell(3).getNumericCellValue());
      availability.setStayFromDate(LocalDate.from(row.getCell(4).getLocalDateTimeCellValue()));
      availability.setStayToDate(LocalDate.from(row.getCell(5).getLocalDateTimeCellValue()));
      availability.setArrivalDays(row.getCell(6).getStringCellValue());
      availability.setDepartureDays(row.getCell(7).getStringCellValue());

      return availability;
    } catch (Exception e) {
      logger.error("Error while creating Availability from Excel row", e);
      throw new RuntimeException("Error while creating Availability from Excel row", e);
    }
  }
}
