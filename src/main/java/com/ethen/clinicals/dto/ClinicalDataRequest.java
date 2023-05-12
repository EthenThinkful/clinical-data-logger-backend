package com.ethen.clinicals.dto;

public class ClinicalDataRequest {
	private String componentName;
	private String ComponentValue;
	private int patientId;

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentValue() {
		return ComponentValue;
	}

	public void setComponentValue(String componentValue) {
		ComponentValue = componentValue;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
}
