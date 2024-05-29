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

@Path("/appointment") // Base URI path for this resource
public class AppointmentResource {

    // logger for appointment class
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentResource.class);

    // Instances of DAO classes to interact with the database
    private AppointmentDAO appointment_dao = new AppointmentDAO();

    // Method to retrieve all appointments
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAppointments() {
        try {
            LOG.info("Retrieving all appointments");
            List<Appointment> appointments = appointment_dao.getAllAppointments();
            return Response.ok().entity(appointments).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all appointments: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving appointments").build();
        }
    }

    // Method to retrieve an Appointment by ID
    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("appointmentId") int appointmentId) {
        try {
            LOG.info("Retrieving appointment by ID: " + appointmentId);
            Appointment appointment = appointment_dao.getAppointmentById(appointmentId);
            return Response.ok().entity(appointment).build();
        } catch (Exception e) {
            LOG.error("Error retrieving appointment by ID: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving appointment by ID").build();
        }
    }

    // Method to add a new appointment
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAppointment(Appointment appointment) {
        try {
            LOG.info("Creating new appointment");
            appointment_dao.createAppointment(appointment);
            return Response.status(Response.Status.CREATED).entity("New appointment created").build();
        } catch (Exception e) {
            LOG.error("Error creating new appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating appointment").build();
        }
    }

    // Method to update an existing appointment
    @PUT
    @Path("/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") int appointmentId, Appointment updatedAppointment) {
        try {
            LOG.info("Updating appointment with ID: " + appointmentId);
            Appointment existingAppointment = appointment_dao.getAppointmentById(appointmentId);
            if (existingAppointment != null) {
                updatedAppointment.setId(appointmentId);
                appointment_dao.updateAppointment(updatedAppointment);
                return Response.ok().entity("Appointment " + appointmentId + " updated").build();
            } else {
                LOG.warn("Appointment with ID " + appointmentId + " not found");
                return Response.status(Response.Status.NOT_FOUND).entity("Appointment not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating appointment").build();
        }
    }

    // Method to delete an appointment by ID
    @DELETE
    @Path("/{appointmentId}")
    public Response deleteAppointment(@PathParam("appointmentId") int appointmentId) {
        try {
            LOG.info("Deleting appointment with ID: " + appointmentId);
            appointment_dao.deleteAppointment(appointmentId);
            return Response.ok().entity("Appointment " + appointmentId + " deleted").build();
        } catch (Exception e) {
            LOG.error("Error deleting appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting appointment").build();
        }
    }

    // Method to delete appointments by patient ID
    @DELETE
    @Path("/patient/{patientId}")
    public Response deleteAppointmentbyPatientId(@PathParam("patientId") int patientId) {
        try {
            LOG.info("Deleting appointments for patient with ID: " + patientId);
            appointment_dao.deleteAppointmentByPatientId(patientId);
            return Response.ok().entity("Appointments for patient with ID " + patientId + " deleted").build();
        } catch (Exception e) {
            LOG.error("Error deleting appointments for patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting appointments for patient").build();
        }
    }
}
