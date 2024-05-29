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
public class DoctorDAO extends PersonDAO {

    private static Map<Integer, Doctor> doctors = new TreeMap<>();

    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new DataNotFoundException("Doctor cannot be null");
        }
        doctor.setId(getNextDoctorId());
        super.addPerson(doctor);
        doctors.put(doctor.getId(), doctor);
    }

    public Doctor getDoctorById(int id) {
        if (doctors.containsKey(id)) {
            return doctors.get(id);
        }
        throw new DataNotFoundException("Doctor with ID " + id + " not found");        
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public void updateDoctor(Doctor updatedDoctor) {
        int id = updatedDoctor.getId();
        if (doctors.containsKey(id)) {
            super.updatePerson(updatedDoctor);
            doctors.put(id, updatedDoctor);
        } else {
            throw new DataNotFoundException("Doctor with ID " + id + " not found for update");
        }
    }

    public void deleteDoctor(int id) {
        if (doctors.containsKey(id)) {
            super.deletePerson(id);
            doctors.remove(id);
        } else {
            throw new DataNotFoundException("Doctor with ID " + id + " not found for deletion");
        }
    }

    // Generate a new unique ID for a doctor
    private int getNextDoctorId() {
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
    public Map<Integer, Doctor>  getDMap() {
        return doctors;
    }
}
