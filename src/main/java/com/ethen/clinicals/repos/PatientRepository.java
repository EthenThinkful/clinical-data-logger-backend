package com.ethen.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ethen.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
