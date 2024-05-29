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

@Path("/doctor") // Base URI path for this resource
public class DoctorResource {

    // Logger for doctor class
    private static final Logger LOG = LoggerFactory.getLogger(DoctorResource.class);
    
    // Instances of DAO classes to interact with the database
    private DoctorDAO doctor_dao = new DoctorDAO();

    // Method to retrieve all doctors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDoctors() {
        try {
            LOG.info("Retrieving all doctors");
            List<Doctor> doctors = doctor_dao.getAllDoctors();
            return Response.ok().entity(doctors).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all doctors: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving doctors").build();
        }
    }
    
    // Method to retrieve a Doctor by ID
    @GET
    @Path("/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctor(@PathParam("doctorId") int doctorId) {
        try {
            LOG.info("Retrieving doctor by ID: {}", doctorId);
            Doctor doctor = doctor_dao.getDoctorById(doctorId);
            if (doctor != null) {
                return Response.ok().entity(doctor).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error retrieving doctor {}: {}", doctorId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving doctor").build();
        }
    }

    // Method to add a new doctor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDoctor(Doctor doctor) {
        try {
            doctor_dao.addDoctor(doctor);
            LOG.info("New Doctor is created");
            return Response.status(Response.Status.CREATED).entity("Doctor created successfully").build();
        } catch (Exception e) {
            LOG.error("Error creating new doctor: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating doctor").build();
        }
    }
    
    // Method to update an existing doctor
    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor (@PathParam("doctorId") int doctorId, Doctor updatedDoctor) {
        try {
            Doctor existingDoctor = doctor_dao.getDoctorById(doctorId);
            if (existingDoctor != null) {
                updatedDoctor.setId(doctorId);
                doctor_dao.updateDoctor(updatedDoctor);
                LOG.info("Doctor {} is updated", doctorId);
                return Response.ok().entity("Doctor updated successfully").build();
            } else {
                LOG.warn("Doctor {} not found", doctorId);
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating doctor {}: {}", doctorId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating doctor").build();
        }
    }

    // Method to delete a doctor by ID
    @DELETE
    @Path("/{doctorId}")
    public Response deleteDoctor(@PathParam("doctorId") int doctorId) {
        try {
            doctor_dao.deleteDoctor(doctorId);
            LOG.info("Doctor {} is deleted", doctorId);
            return Response.ok().entity("Doctor deleted successfully").build();
        } catch (Exception e) {
            LOG.error("Error deleting doctor {}: {}", doctorId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting doctor").build();
        }
    }
}
