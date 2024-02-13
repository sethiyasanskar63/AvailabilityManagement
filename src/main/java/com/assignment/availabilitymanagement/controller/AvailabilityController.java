package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.util.AvailabilityToWorkbook;
import com.assignment.availabilitymanagement.util.PossibleDates;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for managing availabilities within the application.
 * It provides endpoints for CRUD operations on availabilities and exporting/importing availability data.
 */
@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilityController.class);

  @Autowired
  private AvailabilityService availabilityService;
  @Autowired
  private AvailabilityMapper availabilityMapper;

  /**
   * Retrieves a list of availabilities based on optional filters.
   *
   * @param arrivalDate         The start date for availability search.
   * @param departureDate       The end date for availability search.
   * @param availabilityId      Specific availability ID for retrieval.
   * @param accommodationTypeId Accommodation type ID for filtering availabilities.
   * @param pageable            Pageable object for pagination and sorting.
   * @return A page of AvailabilityDTO objects that match the criteria.
   */
  @GetMapping
  public ResponseEntity<Page<AvailabilityDTO>> getAvailability(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
      @RequestParam(required = false) Long availabilityId,
      @RequestParam(required = false) Long accommodationTypeId,
      @PageableDefault(size = 10) Pageable pageable) {

    logger.debug("Fetching availabilities with given criteria.");
    if (availabilityId != null && (arrivalDate != null || departureDate != null || accommodationTypeId != null)) {
      logger.warn("When providing availabilityId, no other parameters should be provided.");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "When availabilityId is provided, no other search criteria should be specified.");
    }

    if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
      logger.debug("Both arrival and departure dates are required to check availability.");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both arrival and departure dates are required to check availability.");
    }

    try {
      Page<AvailabilityDTO> availabilities = availabilityService.getAvailability(availabilityId, accommodationTypeId, arrivalDate, departureDate, pageable);
      if (availabilities.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(availabilities);
    } catch (Exception e) {
      logger.error("Error fetching availabilities: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching availabilities.");
    }
  }


  /**
   * Adds a new availability entry.
   *
   * @param availabilityDTO Data transfer object containing the new availability details.
   * @return The added availability DTO or an error message.
   */
  @PostMapping
  public ResponseEntity<?> addAvailability(@Valid @RequestBody AvailabilityDTO availabilityDTO) {
    try {
      AvailabilityDTO savedAvailability = availabilityService.saveAvailabilityFromDTO(availabilityDTO);
      return ResponseEntity.ok(savedAvailability);
    } catch (Exception e) {
      logger.error("Error adding availability: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding availability.");
    }
  }

  /**
   * Updates an existing availability.
   *
   * @param availabilityDTO Updated availability details.
   * @return The updated availability DTO or an error message.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateAvailability(@PathVariable Long id, @Valid @RequestBody AvailabilityDTO availabilityDTO) {
    try {
      availabilityDTO.setAvailabilityId(id);
      AvailabilityDTO updatedAvailability = availabilityService.updateAvailabilityFromDTO(availabilityDTO);
      return ResponseEntity.ok(updatedAvailability);
    } catch (Exception e) {
      logger.error("Error updating availability: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating availability.");
    }
  }

  /**
   * Deletes an availability by its ID.
   *
   * @param id The ID of the availability to delete.
   * @return Success message or an error response.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAvailabilityById(@PathVariable Long id) {
    logger.debug("Deleting availability with ID: {}", id);
    try {
      availabilityService.deleteAvailabilityById(id);
      return ResponseEntity.ok("Availability deleted successfully.");
    } catch (Exception e) {
      logger.error("Error deleting availability: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found or already deleted.");
    }
  }

  /**
   * Handles the upload of availability data from an Excel file.
   *
   * @param file The uploaded file containing availability data.
   * @return Success message or an error response.
   */
  @PostMapping("/upload")
  public ResponseEntity<?> uploadAvailability(@RequestParam("file") MultipartFile file) {
    logger.debug("Uploading availability data from file.");
    try (InputStream inputStream = file.getInputStream()) {
      Workbook workbook = new XSSFWorkbook(inputStream);
      String message = availabilityService.saveAllAvailability(workbook);
      return ResponseEntity.ok(message);
    } catch (Exception e) {
      logger.error("Error uploading availability: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading availability.");
    }
  }

  /**
   * Downloads the current availability data as an Excel file.
   *
   * @return The Excel file containing availability data.
   */
  @GetMapping("/download")
  public ResponseEntity<?> downloadAvailability() {
    logger.debug("Downloading availability data as Excel file.");
    try {
      Workbook workbook = AvailabilityToWorkbook.getAvailabilityWorkbook(availabilityService.getAvailability(null, null, null, null).stream().map(availabilityMapper::toEntity).collect(Collectors.toList()));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      byte[] bytes = outputStream.toByteArray();

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=availability.xlsx")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(new ByteArrayResource(bytes));
    } catch (Exception e) {
      logger.error("Error downloading availability: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error downloading availability.");
    }
  }

  /**
   * Retrieves possible dates for availability based on accommodation type and year.
   *
   * @param accommodationTypeId The accommodation type ID.
   * @return A list of possible dates or an error message.
   */
  @GetMapping("/getPossibleDates")
  public ResponseEntity<?> getPossibleDatesByAccommodationTypeId(
      @RequestParam Long accommodationTypeId) {
    logger.debug("Fetching possible dates for accommodationTypeId: {}", accommodationTypeId);
    try {
      List<Availability> availabilities = availabilityService.getAvailability(null, accommodationTypeId, null, null).stream().map(availabilityMapper::toEntity).collect(Collectors.toList());
      List<Map<String, Object>> possibleDates = PossibleDates.getPossibleDatesByAccommodationTypeId(availabilities);
      return ResponseEntity.ok(possibleDates);
    } catch (Exception e) {
      logger.error("Error fetching possible dates: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching possible dates.");
    }
  }
}
