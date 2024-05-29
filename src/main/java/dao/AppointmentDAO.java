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

public class AppointmentDAO {

    //private static List<Appointment> appointments = new ArrayList<>();
    private static Map<Integer, Appointment> appointments = new TreeMap<>();
    
    private DoctorDAO doctor_dao = new DoctorDAO(); 
    private PatientDAO patient_dao = new PatientDAO(); 


    public void createAppointment(Appointment appointment) throws DataNotFoundException{
        if (patient_dao.getPMap() == null) {
            if ( appointment == null && doctor_dao.getDMap() == null) {
                throw new DataNotFoundException("Appointments and Patients and doctors cannot be null");
            }
        }
        int nextId = getNextPrescriptionId();
        int patientId = appointment.getPatient().getId();
        int doctorId = appointment.getDoctor().getId();
        appointment.setDoctor(getDoctor(doctorId)); 
        appointment.setPatient(getPatient(patientId));
        appointment.setId(nextId);
        appointments.put(nextId, appointment);
    }

    public Appointment getAppointmentById(int id) throws DataNotFoundException {
        Appointment appointment = appointments.get(id);
        if (appointment == null) {
            throw new DataNotFoundException("Appointment with ID " + id + " not found");
        }
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        // Return a copy to avoid modification of original list
        return new ArrayList<>(appointments.values());
    }

    public List<Appointment> getAppointmentsByPatientId(int id) throws DataNotFoundException {
        List<Appointment> appointmentsByPatient = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatient().getId() == id) {
                appointmentsByPatient.add(appointment);
            }
        }
        if (appointmentsByPatient.isEmpty()) {
            throw new DataNotFoundException("No appointments found for patient with ID " + id);
        }
        return appointmentsByPatient;
    }

    public void updateAppointment(Appointment updatedAppointment) throws DataNotFoundException {
        int id = updatedAppointment.getId();
        if (!appointments.containsKey(id)) {
            throw new DataNotFoundException("Appointment with ID " + id + " not found for update");
        }
        int patientId = updatedAppointment.getPatient().getId();
        int doctorId = updatedAppointment.getDoctor().getId();
        updatedAppointment.setDoctor(getDoctor(doctorId)); 
        updatedAppointment.setPatient(getPatient(patientId));
        appointments.put(id, updatedAppointment);
    }

    public void deleteAppointment(int id) {
        if (appointments.remove(id) == null) {
            throw new DataNotFoundException("Appointment with ID " + id + " not found for deletion");
        }
    }

    public void deleteAppointmentByPatientId(int pid) throws DataNotFoundException {
        boolean deleted = false;
        Iterator<Map.Entry<Integer, Appointment>> iterator = appointments.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Appointment> entry = iterator.next();
            if (entry.getValue().getPatient().getId() == pid) {
                iterator.remove();
                deleted = true;
            }
        }
        if (!deleted) {
            throw new DataNotFoundException("No appointments found for patient with ID " + pid + " to delete");
        }
    }
    
    // Generate a new unique ID for a student
    private int getNextPrescriptionId() {
      int maxUserId = 0;
      for (Integer key : appointments.keySet()) {
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
    // get doctor by id
    private Doctor getDoctor(int id){
        Doctor doctor = doctor_dao.getDoctorById(id);
        return doctor;
    }
}
