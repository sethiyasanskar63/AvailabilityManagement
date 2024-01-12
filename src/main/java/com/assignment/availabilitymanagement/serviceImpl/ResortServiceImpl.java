package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.repository.ResortRepository;
import com.assignment.availabilitymanagement.service.ResortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortServiceImpl implements ResortService {

  @Autowired
  private ResortRepository resortRepository;

  @Override
  public List<Resort> getAllResorts() {
    return resortRepository.findAll();
  }

  @Override
  public Resort getResortById(Long id) {
    return resortRepository.findById(id).orElse(null);
  }

  @Override
  public Resort saveResort(Resort resort) {
    return resortRepository.saveAndFlush(resort);
  }

  @Override
  public String deleteResortByID(Long id) {
    resortRepository.deleteById(id);
    return "Deleted resort ID: " + id;
  }
}
