package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resort")
public class ResortController {

  @Autowired
  ResortServiceImpl resortServiceImpl;

  @GetMapping("/getResorts")
  public List<ResortDTO> getResorts(@RequestParam(name = "resortId", required = false) Long resortId) {
    if (resortId ==null){
      return resortServiceImpl.getAllResorts()
          .stream()
          .map(ResortDTO::new)
          .collect(Collectors.toList());
    }
    else{
     return resortServiceImpl.getResortById(resortId) != null ? List.of() : Collections.emptyList();
    }
  }

  @PostMapping("/addResort")
  public ResortDTO addResort(@RequestBody Resort resort) {
    return new ResortDTO(resortServiceImpl.saveResort(resort));
  }

  @PutMapping("/updateResort")
  public ResortDTO updateResort(@RequestBody Resort resort) {
    return new ResortDTO(resortServiceImpl.saveResort(resort));
  }

  @DeleteMapping("/deleteResortById")
  public String deleteResortById(@RequestParam(name = "resortId", required = false) Long resortId) {
    return resortServiceImpl.deleteResortByID(resortId);
  }
}
