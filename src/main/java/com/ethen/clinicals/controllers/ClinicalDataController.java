package com.ethen.clinicals.controllers;

import java.util.ArrayList;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.ethen.clinicals.dto.ClinicalDataRequest;
import com.ethen.clinicals.model.ClinicalData;
import com.ethen.clinicals.model.Patient;
import com.ethen.clinicals.repos.ClinicalDataRepository;
import com.ethen.clinicals.repos.PatientRepository;
import com.ethen.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	private ClinicalDataRepository clinicalDataRepository;
	private PatientRepository patientRepository;

	ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepository = patientRepository;
	}

	@PostMapping("/clinicals")
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalDataRepository.save(clinicalData);
	}

	@GetMapping("/clinicals/{patientId}/{componentName}")
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		if (componentName.equals("bmi")) {
			componentName = "hw";
		}
		List<ClinicalData> clinicalData = clinicalDataRepository.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId, componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData eachEntry : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		
		return clinicalData;

	}

}
	
