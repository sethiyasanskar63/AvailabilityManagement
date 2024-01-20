package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelToAvailability {

  public List<Availability> getExcelToAvailability(Workbook workbook) {

    List<Availability> availabilities = new ArrayList<>();

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

    return availabilities;
  }

  private Availability createAvailabilityFromRow(Row row) {

    AccommodationServiceImpl accommodationServiceImpl = new AccommodationServiceImpl();
    AccommodationTypeServiceImpl accommodationTypeServiceImpl = new AccommodationTypeServiceImpl();
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
  }
}
