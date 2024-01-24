package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import com.assignment.availabilitymanagement.util.AvailabilityToWorkbook;
import com.assignment.availabilitymanagement.util.PossibleDates;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilitySpecification.class);

  @Autowired
  private AvailabilityServiceImpl availabilityServiceImpl;

  @GetMapping("/getAvailability")
  public ResponseEntity<List<AvailabilityDTO>> getAvailability(
      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
      @RequestParam(name = "availabilityId", required = false) Long availabilityId,
      @RequestParam(name = "accommodationId", required = false) Long accommodationId,
      @RequestParam(name = "accommodationTypeId", required = false) Long accommodationTypeId) {

    try {
      List<AvailabilityDTO> availabilities = availabilityServiceImpl.getAvailability(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate)
          .stream().map(AvailabilityDTO::new).collect(Collectors.toList());

      logger.info("Successfully retrieved availability");
      return ResponseEntity.ok(availabilities);
    } catch (Exception e) {
      logger.error("Error while processing getAvailability request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/addAvailability")
  public ResponseEntity<AvailabilityDTO> addAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
    try {
      availabilityServiceImpl.saveAvailabilityFromDTO(availabilityDTO);
      logger.info("Successfully added availability with ID: {}", availabilityDTO.getAvailabilityId());
      return ResponseEntity.ok(availabilityDTO);
    } catch (Exception e) {
      logger.error("Error while processing addAvailability request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("/updateAvailability")
  public ResponseEntity<AvailabilityDTO> updateAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
    try {
      availabilityServiceImpl.saveAvailabilityFromDTO(availabilityDTO);
      logger.info("Successfully updated availability with ID: {}", availabilityDTO.getAvailabilityId());
      return ResponseEntity.ok(availabilityDTO);
    } catch (Exception e) {
      logger.error("Error while processing updateAvailability request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/deleteAvailabilityById")
  public ResponseEntity<String> deleteAvailabilityById(@RequestParam(name = "availabilityId") Long availabilityId) {
    try {
      String result = availabilityServiceImpl.deleteAvailabilityById(availabilityId);
      logger.info("Successfully deleted availability with ID: {}", availabilityId);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing deleteAvailabilityById request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping(path = "/downloadAvailability")
  public ResponseEntity<ByteArrayResource> downloadAvailability() {
    try {
      Workbook workbook = AvailabilityToWorkbook.getAvailabilityWorkbook(availabilityServiceImpl.getAvailability(null, null, null, null, null));

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      workbook.close();

      byte[] excelBytes = outputStream.toByteArray();
      ByteArrayResource resource = new ByteArrayResource(excelBytes);

      logger.info("Successfully downloaded availability workbook");
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=availability.xlsx")
          .contentLength(resource.contentLength())
          .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
          .body(resource);
    } catch (Exception e) {
      logger.error("Error while processing downloadAvailability request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/uploadAvailability")
  public ResponseEntity<String> uploadAvailability(@RequestParam("file") MultipartFile file) {
    try {
      Workbook workbook = new XSSFWorkbook();

      try (InputStream inputStream = file.getInputStream()) {
        workbook = new XSSFWorkbook(inputStream);
      }

      String result = availabilityServiceImpl.saveAllAvailability(workbook);
      logger.info("Successfully uploaded availability workbook");
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing uploadAvailability request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/getPossibleDatesByAccommodationTypeId")
  public ResponseEntity<List<Map<String, Object>>> getPossibleDatesByAccommodationTypeId(
      @RequestParam(name = "accommodationTypeId") Long accommodationTypeId,
      @RequestParam(name = "year") Integer year) {

    try {
      List<Availability> availabilities = availabilityServiceImpl.getAvailability(null, null, accommodationTypeId, null, null);
      List<Map<String, Object>> possibleDates = PossibleDates.getPossibleDatesByAccommodationTypeId(year, availabilities);
      logger.info("Successfully retrieved possible dates by accommodationId");
      return ResponseEntity.ok(possibleDates);
    } catch (Exception e) {
      logger.error("Error while processing getPossibleDatesByAccommodationId request", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
