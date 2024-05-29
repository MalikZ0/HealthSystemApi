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
import exceptions.*;
import models.Person;


public class PersonDAO {

  protected static Map<Integer, Person> persons = new TreeMap<>();

  public void addPerson(Person person) {
    if (person == null) {
        throw new DataNotFoundException("person cannot be null"); 
    }
    int id = checkId(); 
    person.setId(id);
    persons.put(id, person);
  }

  public Person getPersonById(int id) throws DataNotFoundException {
    if (persons.containsKey(id)) {
      return persons.get(id);
    }
    // Throw exception if not found
    throw new DataNotFoundException("Person with ID " + id + " not found"); 
  }

  public List<Person> getAllPerson() {
    // Return a copy to avoid modification of original list
    return new ArrayList<>(persons.values());
  }

  public void updatePerson(Person updatedPerson) {
    if (persons.containsKey(updatedPerson.getId())) {
      // Update person attributes
      persons.put(updatedPerson.getId(), updatedPerson);
    } else {
      // Throw exception if not found
      throw new DataNotFoundException("Person with ID " + updatedPerson.getId() + " not found for update");
    }
  }

  public void deletePerson(int id) {
    if (persons.containsKey(id)) {
      persons.remove(id);
    } else {
      throw new DataNotFoundException("Person with ID " + id + " not found for deletion");
    }    
  }
  
  // check if id is unique ID for a person 
  private int checkId() {
    int maxUserId = 0;
    for (Integer key : persons.keySet()) {
        if (key > maxUserId) {
            maxUserId = key;
        }
    }
    return maxUserId + 1;
  }
  
  // Getter method to access the private map
    public Map<Integer, Person>  getMap() {
        return persons;
    }
}


