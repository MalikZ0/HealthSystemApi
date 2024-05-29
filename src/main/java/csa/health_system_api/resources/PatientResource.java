/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.health_system_api.resources;

import dao.*;
import java.util.List;
import models.*;

/**
 *
 * @author Acer
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/patient") // Base URI path for this resource
public class PatientResource {

    // logger for patient class
    private static final Logger LOG = LoggerFactory.getLogger(PatientResource.class);
    
    // Instances of DAO classes to interact with the database
    private PatientDAO patient_dao = new PatientDAO();

    // Method to retrieve all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients() {
        try {
            LOG.info("Retrieving all patients");
            List<Patient> patients = patient_dao.getAllPatients();
            return Response.ok(patients).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all patients: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving all patients").build();
        }
    }
    
    // Method to retrieve a Patient by ID
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatient(@PathParam("patientId") int patientId) {
        try {
            LOG.info("Retrieving patient by ID: " + patientId);
            Patient patient = patient_dao.getPatientById(patientId);
            return Response.ok(patient).build();
        } catch (Exception e) {
            LOG.error("Error retrieving patient by ID: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving patient").build();
        }
    }

    // Method to add a new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(Patient patient) {
        try {
            LOG.info("Creating new patient");
            patient_dao.addPatient(patient);
            return Response.status(Response.Status.CREATED).entity("New patient created").build();
        } catch (Exception e) {
            LOG.error("Error creating new patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating new patient").build();
        }
    }
    
    // Method to update an existing patient
    @PUT
    @Path("/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient (@PathParam("patientId") int patientId, Patient updatedPatient) {
        try {
            LOG.info("Updating patient with ID: " + patientId);
            Patient existingPatient = patient_dao.getPatientById(patientId);
            if (existingPatient != null) {
                updatedPatient.setId(patientId);
                patient_dao.updatePatient(updatedPatient);
                return Response.ok().entity("Patient updated").build();
            } else {
                LOG.warn("Patient with ID " + patientId + " not found");
                return Response.status(Response.Status.NOT_FOUND).entity("Patient not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating patient").build();
        }
    }

    // Method to delete a patient by ID
    @DELETE
    @Path("/{patientId}")
    public Response deletePatient(@PathParam("patientId") int patientId) {
        try {
            LOG.info("Deleting patient with ID: " + patientId);
            patient_dao.deletePatient(patientId);
            return Response.ok().entity("Patient deleted").build();
        } catch (Exception e) {
            LOG.error("Error deleting patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting patient").build();
        }
    }
}
