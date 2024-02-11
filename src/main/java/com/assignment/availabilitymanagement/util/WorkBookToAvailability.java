package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
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

  public List<AvailabilityDTO> excelToAvailabilityDTO(Workbook workbook) {
    List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();

    try {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();
      if (rowIterator.hasNext()) {
        rowIterator.next();
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

  private AvailabilityDTO createAvailabilityDTOFromRow(Row row) {
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

  private int[] convertStringToDayArray(String daysString) {
    return Arrays.stream(daysString.split(","))
        .map(String::trim)
        .mapToInt(Integer::parseInt)
        .toArray();
  }
}
