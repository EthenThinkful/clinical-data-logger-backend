package com.ethen.clinicals.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.*;

import org.springframework.web.bind.annotation.*;
import com.ethen.clinicals.model.ClinicalData;
import com.ethen.clinicals.model.Patient;
import com.ethen.clinicals.repos.PatientRepository;
import com.ethen.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

	private PatientRepository repository;
	Map<String, String> filters = new HashMap<>();

	PatientController(PatientRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/patients")
	public List<Patient> getPatients() {
		return repository.findAll();
	}

	@GetMapping("/patients/{id}")
	public Patient getPatient(@PathVariable("id") int id) {
		return repository.findById(id).get();
	}

	@PostMapping("/patients")
	public Patient savePatient(@RequestBody Patient patient) {
		return repository.save(patient);
	}

	@GetMapping("/patients/analyze/{id}")
	public Patient analyze(@PathVariable("id") int id) {
		Patient patient = repository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData eachEntry : duplicateClinicalData) {

			if (filters.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
				continue;
			} else {
				filters.put(eachEntry.getComponentName(), null);
			}

			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}

		filters.clear();
		return patient;

	}

}
