/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.health_system_api.resources;

import dao.*;
import java.util.List;
import models.*;
import exceptions.*;

/**
 *
 * @author Acer
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/prescription") // Base URI path for this resource
public class PrescriptionResource {

    // logger for prescription class
    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionResource.class);
    
    // Instances of DAO classes to interact with the database
    private PrescriptionDAO prescription_dao = new PrescriptionDAO();

    // Method to retrieve all prescriptions
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPrescriptions() {
        try {
            LOG.info("Retrieving all prescriptions");
            List<Prescription> prescriptions = prescription_dao.getAllPrescriptions();
            return Response.ok(prescriptions).build();
        } catch (Exception e) {
            LOG.error("Error retrieving prescriptions: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving prescriptions").build();
        }
    }
    
    // Method to retrieve a Prescription by ID
    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescription(@PathParam("prescriptionId") int prescriptionId) {
        try {
            LOG.info("Retrieving prescription by ID: " + prescriptionId);
            Prescription prescription = prescription_dao.getPrescriptionById(prescriptionId);
            return Response.ok(prescription).build();
        } catch (DataNotFoundException e) {
            LOG.warn("Prescription with ID " + prescriptionId + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Prescription not found").build();
        } catch (Exception e) {
            LOG.error("Error retrieving prescription: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving prescription").build();
        }
    }

    // Method to add a new prescription
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPrescription(Prescription prescription) {
        try {
            LOG.info("Creating a new prescription");
            prescription_dao.createPrescription(prescription);
            return Response.status(Response.Status.CREATED).entity("Prescription created successfully").build();
        } catch (Exception e) {
            LOG.error("Error creating prescription: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating prescription").build();
        }
    }
    
    // Method to update an existing prescription
    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription (@PathParam("prescriptionId") int prescriptionId, Prescription updatedPrescription) {
        try {
            Prescription existingPrescription = prescription_dao.getPrescriptionById(prescriptionId);
            if (existingPrescription != null) {
                LOG.info("Updating prescription with ID: " + prescriptionId);
                updatedPrescription.setId(prescriptionId);
                prescription_dao.updatePrescription(updatedPrescription);
                return Response.ok().entity("Prescription updated successfully").build();
            } else {
                LOG.warn("Prescription with ID " + prescriptionId + " not found");
                return Response.status(Response.Status.NOT_FOUND).entity("Prescription not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating prescription: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating prescription").build();
        }
    }

    // Method to delete a prescription by ID
    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@PathParam("prescriptionId") int prescriptionId) {
        try {
            LOG.info("Deleting prescription with ID: " + prescriptionId);
            prescription_dao.deletePrescription(prescriptionId);
            return Response.ok().entity("Prescription deleted successfully").build();
        } catch (DataNotFoundException e) {
            LOG.warn("Prescription with ID " + prescriptionId + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Prescription not found").build();
        } catch (Exception e) {
            LOG.error("Error deleting prescription: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting prescription").build();
        }
    }
    
    // Method to delete prescriptions by patient ID
    @DELETE
    @Path("/patient/{patientId}")
    public Response deletePrescriptionbyPatientId(@PathParam("patientId") int patientId) {
        try {
            LOG.info("Deleting prescriptions with patient ID: " + patientId);
            prescription_dao.deletePrescriptionsByPatientId(patientId);
            return Response.ok().entity("Prescriptions for patient ID " + patientId + " deleted successfully").build();
        } catch (DataNotFoundException e) {
            LOG.warn("No prescriptions found for patient with ID: " + patientId);
            return Response.status(Response.Status.NOT_FOUND).entity("No prescriptions found for patient").build();
        } catch (Exception e) {
            LOG.error("Error deleting prescriptions: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting prescriptions").build();
        }
    }
}
