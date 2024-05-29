/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



/**
 *
 * @author Acer
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import exceptions.DataNotFoundException;
import java.util.Iterator;
import models.*;

public class BillingDAO {

    // private static List<Billing> billings = new ArrayList<>();    
    private static Map<Integer, Billing> billings = new TreeMap<>();
    
    private PatientDAO patient_dao = new PatientDAO(); 

    public void createBilling(Billing billing) throws DataNotFoundException {
        if (billing == null && patient_dao.getPMap() == null) {
            throw new DataNotFoundException("Billings and Patients cannot be null");
        }
        int nextId = getNextBillingId();
        int patientId = billing.getPatient().getId();
        Patient patient = getPatient(patientId);
        billing.setPatient(patient);
        billing.setId(nextId);
        billings.put(nextId, billing);
    }

    public Billing getBillingById(int id) throws DataNotFoundException {
        Billing billing = billings.get(id);
        if (billing == null) {
            throw new DataNotFoundException("Billing with ID " + id + " not found");
        }
        return billing;
    }

    public List<Billing> getAllBillings() {
        // Return a copy to avoid modification of original list
        return new ArrayList<>(billings.values()); 
    }

    public List<Billing> getBillingsbyPatientId(int id) throws DataNotFoundException {
        List<Billing> billingsByPatient = new ArrayList<>();
        for (Billing billing : billings.values()) {
            if (billing.getPatient().getId() == id) {
                billingsByPatient.add(billing);
            }
        }
        if (billingsByPatient.isEmpty()) {
            throw new DataNotFoundException("No billings found for patient with ID " + id);
        }
        return billingsByPatient;
    }

    public void updateBilling(Billing updatedBilling) throws DataNotFoundException {
        int id = updatedBilling.getId();
        if (!billings.containsKey(id)) {
            throw new DataNotFoundException("Billing with ID " + id + " not found for update");
        }
        int patientId = updatedBilling.getPatient().getId();
        Patient patient = getPatient(patientId);
        updatedBilling.setPatient(patient);
        billings.put(id, updatedBilling);
    }

    public void deleteBilling(int id) throws DataNotFoundException {
        if (billings.remove(id) == null) {
            throw new DataNotFoundException("Billing with ID " + id + " not found for deletion");
        }
    }

    public void deleteBillingbyPatientId(int pid) throws DataNotFoundException {
        boolean deleted = false;
        Iterator<Map.Entry<Integer, Billing>> iterator = billings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Billing> entry = iterator.next();
            if (entry.getValue().getPatient().getId() == pid) {
                iterator.remove();
                deleted = true;
            }
        }
        if (!deleted) {
            throw new DataNotFoundException("No billings found for patient with ID " + pid + " for deletion");
        }
    }
    
    // Generate a new unique ID for a student
    private int getNextBillingId() {
      int maxUserId = 0;
      for (Integer key : billings.keySet()) {
          if (key > maxUserId) {
              maxUserId = key;
          }
      }
      return maxUserId + 1;
    }
    
    // generate invoice
    private String getInvoice(int id){
        for (Billing billing : billings.values()) {
            if (billing.getId() == id) {
                String invoice = billing.toString();
                return invoice;
            }
        }
        return "null";
    }
    
    // get patient by id
    private Patient getPatient(int id){
        Patient patient = patient_dao.getPatientById(id);
        return patient;
    }      
}