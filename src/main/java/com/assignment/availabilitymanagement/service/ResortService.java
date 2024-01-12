package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Resort;

import java.util.List;

public interface ResortService {

  List<Resort> getAllResorts();

  Resort getResortById(Long id);

  Resort saveResort(Resort resort);

  String deleteResortByID(Long id);
}
