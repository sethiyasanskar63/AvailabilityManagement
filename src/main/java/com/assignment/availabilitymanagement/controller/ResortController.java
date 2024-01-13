package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resort")
public class ResortController {

  @Autowired
  ResortServiceImpl resortServiceImpl;

  @GetMapping("/getAllResorts")
  public List<ResortDTO> getAllResorts() {
    return resortServiceImpl.getAllResorts().stream().map(ResortDTO::new).collect(Collectors.toList());
  }

  @GetMapping("/getResortById/{id}")
  public ResortDTO getResortById(@PathVariable("id") Long id) {
    return new ResortDTO(resortServiceImpl.getResortById(id));
  }

  @PostMapping("/addResort")
  public ResortDTO addResort(@RequestBody Resort resort) {
    return new ResortDTO(resortServiceImpl.saveResort(resort));
  }

  @PutMapping("/updateResort")
  public ResortDTO updateResort(@RequestBody Resort resort) {
    return new ResortDTO(resortServiceImpl.saveResort(resort));
  }

  @DeleteMapping("/deleteResortById/{id}")
  public String deleteResortById(@PathVariable("id") Long id) {
    return resortServiceImpl.deleteResortByID(id);
  }
}
