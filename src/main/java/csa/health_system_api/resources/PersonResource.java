/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.health_system_api.resources;

import dao.PersonDAO;
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

@Path("/person") // Base URI path for this resource
public class PersonResource {
    // logger for person class
    private static final Logger LOG = LoggerFactory.getLogger(PersonResource.class);
    
    // Instances of DAO classes to interact with the database
    private PersonDAO person_dao = new PersonDAO();

    // Method to retrieve all persons
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerson() {
        try {
            LOG.info("Retrieving all persons");
            List<Person> persons = person_dao.getAllPerson();
            return Response.ok(persons).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all persons: "+e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving all persons").build();
        }
    }
    
    // Method to retrieve a Person by ID
    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("personId") int personId) {
        try {
            LOG.info("Retrieving person by ID: "+ personId);
            Person person = person_dao.getPersonById(personId);
            return Response.ok(person).build();
        } catch (Exception e) {
            LOG.error("Error retrieving person by ID : "+ personId + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving person").build();
        }
    }

    // Method to add a new person
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {
        try {
            person_dao.addPerson(person);
            LOG.info("New Person is created");
            return Response.status(Response.Status.CREATED).entity("Person created successfully").build();
        } catch (Exception e) {
            LOG.error("Error creating new person: "+ e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating new person").build();
        }
    }
    
    // Method to update an existing person
    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson (@PathParam("personId") int personId, Person updatedPerson) {
        try {
            Person existingPerson = person_dao.getPersonById(personId);
            if (existingPerson != null) {
                updatedPerson.setId(personId);
                person_dao.updatePerson(updatedPerson);
                LOG.info("Person is updated :"+ personId);
                return Response.ok().entity("Person updated successfully").build();
            } else {
                LOG.warn("Person not found "+ personId);
                return Response.status(Response.Status.NOT_FOUND).entity("Person not found").build();
            }
        } catch (Exception e) {
            LOG.error("Error updating person {}: {}", personId, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating person").build();
        }
    }

    // Method to delete a person by ID
    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam("personId") int personId) {
        try {
            person_dao.deletePerson(personId);
            LOG.info("Person "+personId+" is deleted");
            return Response.ok().entity("Person "+personId+" is deleted").build();
        } catch (Exception e) {
            LOG.error("Error deleting person" + personId+": "+ e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting person").build();
        }
    }
}
