package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
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
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilitySpecification.class);
  @Autowired
  private AvailabilityServiceImpl availabilityServiceImpl;

  @GetMapping("/getAvailability")
  public List<AvailabilityDTO> getAvailability(
      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate arrivalDate,

      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate departureDate,

      @RequestParam(name = "availabilityId", required = false) Long availabilityId,

      @RequestParam(name = "accommodationId", required = false) Long accommodationId,

      @RequestParam(name = "accommodationTypeId", required = false) Long accommodationTypeId
  ) {
    return availabilityServiceImpl.getAvailability(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate)
        .stream().map(AvailabilityDTO::new).collect(Collectors.toList());
  }

  @PostMapping("/addAvailability")
  public AvailabilityDTO addAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @PutMapping("/updateAvailability")
  public AvailabilityDTO updateAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @DeleteMapping("/deleteAvailabilityById")
  public String deleteAvailabilityById(@RequestParam(name = "availabilityId") Long availabilityId) {
    return availabilityServiceImpl.deleteAvailabilityById(availabilityId);
  }

  @GetMapping(path = "/downloadAvailability")
  public ResponseEntity<ByteArrayResource> downloadAvailability() {
    Workbook workbook = availabilityServiceImpl.getAvailabilityWorkBook(availabilityServiceImpl.getAvailability(null, null, null, null, null));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      logger.error("Error while processing workbook", e);
    }

    byte[] excelBytes = outputStream.toByteArray();
    ByteArrayResource resource = new ByteArrayResource(excelBytes);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=availability.xlsx")
        .contentLength(resource.contentLength())
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(resource);
  }

  @PostMapping("/uploadAvailability")
  public String uploadAvailability(@RequestParam("file") MultipartFile file) throws IOException {
    Workbook workbook = new XSSFWorkbook();

    try (InputStream inputStream = file.getInputStream()) {
      workbook = new XSSFWorkbook(inputStream);
    }
    return availabilityServiceImpl.saveAllAvailabilityFromWorkbook(workbook);
  }
}
