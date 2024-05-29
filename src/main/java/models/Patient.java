/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Acer
 */
public class Patient extends Person {
    private String medicalHistory;
    private String healthStatus;

    // Constructor
    public Patient() {
    }

    public Patient(int id, String name, String contactInformation, String address, String medicalHistory, String healthStatus) {
        super(id, name, contactInformation, address);
        this.medicalHistory = medicalHistory;
        this.healthStatus = healthStatus;
    }
    
    public Patient(Patient patient) {
        super(patient.getId(),patient.getName(),patient.getContactInformation(),patient.getAddress());
        this.healthStatus = patient.getHealthStatus();
        this.medicalHistory = patient.getMedicalHistory();
    }

    // Getters and setters specific to Patient
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
