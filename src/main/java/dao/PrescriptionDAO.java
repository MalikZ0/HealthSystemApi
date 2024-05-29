/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DataNotFoundException;
import models.*;

/**
 *
 * @author Acer
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PrescriptionDAO {

  //private static List<Prescription> prescriptions = new ArrayList<>();    
  private static Map<Integer, Prescription> prescriptions = new TreeMap<>();

  private PatientDAO patient_dao = new PatientDAO(); 
  
  public void createPrescription(Prescription prescription) {
    if (prescription == null && patient_dao.getPMap() == null) {
            throw new DataNotFoundException("Prescriptions and Patients cannot be null");
    }
    int nextId = getNextPrescriptionId();
    int patientId = prescription.getPatient().getId();
    Patient patient = getPatient(patientId);
    prescription.setPatient(patient);
    prescription.setId(nextId);
    prescriptions.put(nextId, prescription);
  }

  public Prescription getPrescriptionById(int id) throws DataNotFoundException {
    Prescription prescription = prescriptions.get(id);
    if (prescription == null) {
        throw new DataNotFoundException("Prescription with ID " + id + " not found");
    }
    return prescription;
  }

  public List<Prescription> getAllPrescriptions() {
    // Return a copy to avoid modification of original list
    return new ArrayList<>(prescriptions.values()); 
  }

  public List<Prescription> getPrescriptionsByPatientId(int id) throws DataNotFoundException {
    List<Prescription> prescriptionsByPatient = new ArrayList<>();
    for (Prescription prescription : prescriptions.values()) {
        if (prescription.getPatient().getId() == id) {
            prescriptionsByPatient.add(prescription);
        }
    }
    if (prescriptionsByPatient.isEmpty()) {
        throw new DataNotFoundException("No prescriptions found for patient with ID " + id);
    }
    return prescriptionsByPatient;
  }

  public void updatePrescription(Prescription updatedPrescription) throws DataNotFoundException {
    int id = updatedPrescription.getId();
        if (!prescriptions.containsKey(id)) {
            throw new DataNotFoundException("Prescription with ID " + id + " not found for update");
        }
        int patientId = updatedPrescription.getPatient().getId();
        Patient patient = getPatient(patientId);
        updatedPrescription.setPatient(patient);
        prescriptions.put(id, updatedPrescription);
  }

  public void deletePrescription(int id) throws DataNotFoundException {
    if (prescriptions.remove(id) == null) {
        throw new DataNotFoundException("Prescription with ID " + id + " not found for deletion");
    }
  }

  public void deletePrescriptionsByPatientId(int pId) {
    boolean deleted = false;
    Iterator<Map.Entry<Integer, Prescription>> iterator = prescriptions.entrySet().iterator();
    while (iterator.hasNext()) {
        Map.Entry<Integer, Prescription> entry = iterator.next();
        if (entry.getValue().getPatient().getId() == pId) {
            iterator.remove();
            deleted = true;
        }
    }
    if (!deleted) {
        throw new DataNotFoundException("No prescriptions found for patient with ID " + pId + " for deletion");
    }
  }
  
  // Generate a new unique ID for a student
    private int getNextPrescriptionId() {
      int maxUserId = 0;
      for (Integer key : prescriptions.keySet()) {
          if (key > maxUserId) {
              maxUserId = key;
          }
      }
      return maxUserId + 1;
    }
    
    // get patient by id
    private Patient getPatient(int id){
        Patient patient = patient_dao.getPatientById(id);
        return patient;
    }
}
