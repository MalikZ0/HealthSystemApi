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

@Path("/billing") // Base URI path for this resource
public class BillingResource {

    // logger for billing class
    private static final Logger LOG = LoggerFactory.getLogger(BillingResource.class);
    
    // Instances of DAO classes to interact with the database
    private BillingDAO billing_dao = new BillingDAO();

    // Method to retrieve all billing
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillings() {
        try {
            LOG.info("Retrieving all billings");
            List<Billing> billings = billing_dao.getAllBillings();
            return Response.ok(billings).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all billings: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving billings").build();
        }
    }
    
    // Method to retrieve a Billing by ID
    @GET
    @Path("/{billingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBilling(@PathParam("billingId") int billingId) {
        try {
            LOG.info("Retrieving billing by ID: " + billingId);
            Billing billing = billing_dao.getBillingById(billingId);
            return Response.ok(billing).build();
        } catch (Exception e) {
            LOG.error("Error retrieving billing by ID: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving billing").build();
        }
    }
    
    // Method to add a new billing
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBilling(Billing billing) {
        try {
            LOG.info("Creating new billing");
            billing_dao.createBilling(billing);
            return Response.status(Response.Status.CREATED).entity("Billing created successfully").build();
        } catch (Exception e) {
            LOG.error("Error creating new billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating billing").build();
        }
    }
    
    // Method to update an existing billing
    @PUT
    @Path("/{billingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling (@PathParam("billingId") int billingId, Billing updatedBilling) {
        try {
            LOG.info("Updating billing with ID: " + billingId);
            Billing existingBilling = billing_dao.getBillingById(billingId);
            if (existingBilling != null) {
                updatedBilling.setId(billingId);
                billing_dao.updateBilling(updatedBilling);
                return Response.ok("Billing updated successfully").build();
            } else {
                LOG.warn("Billing " + billingId + " not found");
                return Response.status(Response.Status.NOT_FOUND).entity("Billing not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating billing").build();
        }
    }

    // Method to delete a billing by ID
    @DELETE
    @Path("/{billingId}")
    public Response deleteBilling(@PathParam("billingId") int billingId) {
        try {
            LOG.info("Deleting billing with ID: " + billingId);
            billing_dao.deleteBilling(billingId);
            return Response.ok("Billing deleted successfully").build();
        } catch (Exception e) {
            LOG.error("Error deleting billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting billing").build();
        }
    }
    
    // Method to delete a billing by patient ID
    @DELETE
    @Path("/patient/{patientId}")
    public Response deleteBillingbyPatientId(@PathParam("patientId") int patientId) {
        try {
            LOG.info("Deleting billings for patient with ID: " + patientId);
            billing_dao.deleteBillingbyPatientId(patientId);
            return Response.ok("Billings for patient deleted successfully").build();
        } catch (Exception e) {
            LOG.error("Error deleting billings for patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting billings for patient").build();
        }
    }
}
