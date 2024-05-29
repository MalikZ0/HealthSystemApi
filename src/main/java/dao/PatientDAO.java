/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import models.*;

/**
 *
 * @author Acer
 */
public class PatientDAO extends PersonDAO {

  private static Map<Integer, Patient> patients = new TreeMap<>();
  
  public void addPatient(Patient patient){
    if (patient == null) {
        throw new DataNotFoundException("Patient cannot be null");
    }
    patient.setId(getNextPatientId());
    super.addPerson(patient);
    patients.put(patient.getId(), patient);
  }

  public Patient getPatientById(int id) throws DataNotFoundException {
    Patient patient = patients.get(id);
    if (patient == null) {
        throw new DataNotFoundException("Patient with ID " + id + " not found");
    }
    return patient;
  }

  public List<Patient> getAllPatients() {
    // Return a copy to avoid modification of original list
    return new ArrayList<>(patients.values());
  }

  public void updatePatient(Patient updatedPatient) throws DataNotFoundException {
    if (!patients.containsKey(updatedPatient.getId())) {
        throw new DataNotFoundException("Patient with ID " + updatedPatient.getId() + " not found for update");
    }
    super.updatePerson(updatedPatient);
    patients.put(updatedPatient.getId(), updatedPatient);
  }

  public void deletePatient(int id) {
    if (!patients.containsKey(id)) {
        throw new DataNotFoundException("Patient with ID " + id + " not found for deletion");
    }
    super.deletePerson(id);
    patients.remove(id);
  }
  
  // Generate a new unique ID for a Patient
  private int getNextPatientId() {
    int maxUserId = 0;
    Map<Integer, Person> persons = getMap(); 
    for (Integer key : persons.keySet()) {
        if (key > maxUserId) {
            maxUserId = key;
        }
    }
    return maxUserId + 1;
  }
  
  // Getter method to access the private map
    public Map<Integer, Patient>  getPMap() {
        return patients;
    }
}
