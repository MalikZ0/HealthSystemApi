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

@Path("/medicalRecord") // Base URI path for this resource
public class MedicalRecordResource {

    // logger for medicalRecord class
    private static final Logger LOG = LoggerFactory.getLogger(MedicalRecordResource.class);
    
    // Instances of DAO classes to interact with the database
    private MedicalRecordDAO medicalRecord_dao = new MedicalRecordDAO();

    // Method to retrieve all medicalRecord
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicalRecords() {
        try {
            LOG.info("Retrieving all medical records");
            List<MedicalRecord> medicalRecords = medicalRecord_dao.getAllMedicalRecords();
            return Response.ok().entity(medicalRecords).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all medical records: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while retrieving medical records").build();
        }
    }
    
    // Method to retrieve a MedicalRecord by ID
    @GET
    @Path("/{medicalRecordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId) {
        try {
            LOG.info("Retrieving medical record by ID :"+ medicalRecordId);
            MedicalRecord medicalRecord = medicalRecord_dao.getMedicalRecordById(medicalRecordId);
            return Response.ok().entity(medicalRecord).build();
        } catch (Exception e) {
            LOG.error("Error retrieving medical record with ID " + medicalRecordId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while retrieving medical record").build();
        }
    }

    // Method to add a new medicalRecord
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecord_dao.createMedicalRecord(medicalRecord);
            LOG.info("New Medical record is created");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOG.error("Error creating new medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while creating medical record").build();
        }
    }
    
    // Method to update an existing medicalRecord
    @PUT
    @Path("/{medicalRecordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord (@PathParam("medicalRecordId") int medicalRecordId, MedicalRecord updatedMedicalRecord) {
        try {
            MedicalRecord existingMedicalRecord = medicalRecord_dao.getMedicalRecordById(medicalRecordId);
            if (existingMedicalRecord != null) {
                updatedMedicalRecord.setId(medicalRecordId);
                medicalRecord_dao.updateMedicalRecord(updatedMedicalRecord);
                LOG.info("Medical record " + medicalRecordId + " is updated");
                return Response.ok().build();
            } else {
                LOG.warn("Medical record " + medicalRecordId + " is not found");
                return Response.status(Response.Status.NOT_FOUND).entity("Medical record not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating medical record with ID " + medicalRecordId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while updating medical record").build();
        }
    }

    // Method to delete a medicalRecord by ID
    @DELETE
    @Path("/{medicalRecordId}")
    public Response deleteMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId) {
        try {
            medicalRecord_dao.deleteMedicalRecord(medicalRecordId);
            LOG.info("Medical record " + medicalRecordId + " is deleted");
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Error deleting medical record with ID " + medicalRecordId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while deleting medical record").build();
        }
    }
    
    // Method to delete a medicalRecord by patient ID
    @DELETE
    @Path("/patient/{patientId}")
    public Response deleteMedicalRecordbyPatientId(@PathParam("patientId") int patientId) {
        try {
            medicalRecord_dao.deleteMedicalRecordbyPatientId(patientId);
            LOG.info("Medical records for patient by id " + patientId + " is deleted");
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Error deleting medical records for patient with ID " + patientId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while deleting medical records").build();
        }
    }
}
