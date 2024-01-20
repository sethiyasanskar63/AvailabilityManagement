package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;
  @Autowired
  private AccommodationServiceImpl accommodationServiceImpl;
  @Autowired
  private AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  @Override
  public List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                            LocalDate arrivalDate, LocalDate departureDate) {

    AvailabilitySpecification availabilitySpecification = new AvailabilitySpecification(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate);
    return availabilityRepository.findAll(availabilitySpecification);
  }

  @Override
  public Availability saveAvailability(Availability availability) {
    return availabilityRepository.saveAndFlush(availability);
  }

  @Override
  public String saveAllAvailabilityFromWorkbook(Workbook workbook) {
    List<Availability> availabilities = excelToAvailability(workbook);
    availabilityRepository.saveAllAndFlush(availabilities);
    return "Availabilities Imported";
  }

  @Override
  public String deleteAvailabilityById(Long id) {
    availabilityRepository.deleteById(id);
    return "Deleted availability with ID: " + id;
  }

  public Workbook getAvailabilityWorkBook(List<Availability> availabilities) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Availability");
    Row headerRow = sheet.createRow(0);

    String[] headers = {"Availability ID", "Accommodation ID", "Accommodation Type ID", "Minimum Nights", "Stay From Date", "Stay To Date", "Arrival Days", "Departure Days"};

    for (int i = 0; i < headers.length; i++) {
      headerRow.createCell(i).setCellValue(headers[i]);
    }

    int rowNum = 1;

    for (Availability availability : availabilities) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(availability.getAvailabilityId());
      row.createCell(1).setCellValue(availability.getAccommodation() == null ? 0 : availability.getAccommodation().getAccommodationId());
      row.createCell(2).setCellValue(availability.getAccommodationType().getAccommodationTypeId());
      row.createCell(3).setCellValue(availability.getMinNight());
      row.createCell(4).setCellValue(availability.getStayFromDate());
      row.createCell(5).setCellValue(availability.getStayToDate());
      row.createCell(6).setCellValue(availability.getArrivalDays());
      row.createCell(7).setCellValue(availability.getDepartureDays());
    }
    return workbook;
  }

  public List<Availability> excelToAvailability(Workbook workbook) {

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
