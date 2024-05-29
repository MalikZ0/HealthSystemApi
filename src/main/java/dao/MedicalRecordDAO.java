/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Acer
 */
import exceptions.DataNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import models.*;

public class MedicalRecordDAO {
    private static Map<Integer, MedicalRecord> medicalRecords = new TreeMap<>();
    
    private PatientDAO patient_dao = new PatientDAO(); 

    public void createMedicalRecord(MedicalRecord medicalRecord) throws DataNotFoundException {
        if (medicalRecord == null && patient_dao.getPMap() == null ) {
            throw new DataNotFoundException("MedicalRecords and Patients cannot be null");
        }
        int nextId = getNextMRId();
        int patientId = medicalRecord.getPatient().getId();
        Patient patient = getPatient(patientId);
        medicalRecord.setPatient(patient);
        medicalRecord.setId(nextId);
        medicalRecords.put(nextId, medicalRecord);
    }

    public MedicalRecord getMedicalRecordById(int id) throws DataNotFoundException {
        MedicalRecord medicalRecord = medicalRecords.get(id);
        if (medicalRecord == null) {
            throw new DataNotFoundException("MedicalRecord with ID " + id + " not found");
        }
        return medicalRecord;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        // Return a copy to avoid modification of original list
        return new ArrayList<>(medicalRecords.values());
    }

    public List<MedicalRecord> getMedicalRecordsbyPatientId(int id) throws DataNotFoundException {
        List<MedicalRecord> medicalRecordsbyPatient = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecords.values()) {
            if (medicalRecord.getPatient().getId() == id) {
                medicalRecordsbyPatient.add(medicalRecord);
            }
        }
        if (medicalRecordsbyPatient.isEmpty()) {
            throw new DataNotFoundException("No MedicalRecords found for patient with ID " + id);
        }
        return medicalRecordsbyPatient;
    }
    

    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) throws DataNotFoundException {
        int id = updatedMedicalRecord.getId();
        if (!medicalRecords.containsKey(id)) {
            throw new DataNotFoundException("MedicalRecord with ID " + id + " not found for update");
        }
        int patientId = updatedMedicalRecord.getPatient().getId();
        Patient patient = getPatient(patientId);
        updatedMedicalRecord.setPatient(patient);
        medicalRecords.put(id, updatedMedicalRecord);
    }

    public void deleteMedicalRecord(int id) throws DataNotFoundException {
        if (medicalRecords.remove(id) == null) {
            throw new DataNotFoundException("MedicalRecord with ID " + id + " not found for deletion");
        }
    }

    public void deleteMedicalRecordbyPatientId(int pid) throws DataNotFoundException {
        boolean deleted = false;
        Iterator<Map.Entry<Integer, MedicalRecord>> iterator = medicalRecords.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, MedicalRecord> entry = iterator.next();
            if (entry.getValue().getPatient().getId() == pid) {
                iterator.remove();
                deleted = true;
            }
        }
        if (!deleted) {
            throw new DataNotFoundException("No MedicalRecords found for patient with ID " + pid + " for deletion");
        }
    }
    
    // Generate a new unique ID for a student
    private int getNextMRId() {
      int maxUserId = 0;
      for (Integer key : medicalRecords.keySet()) {
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
