/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Acer
 */
public class Billing {
    private int id;
    private Patient patient;
    private double amount;
    private String paid;

    // Constructor
    public Billing() {
    }

    public Billing(int id, Patient patient, double amount, String paid) {
        this.id = id;
        this.patient = patient;
        this.amount = amount;
        this.paid = paid;
    }
    

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaid() {    
        return paid;
    }
    public void setPaid(String paid) {
        this.paid = paid;
    }
    
    // to string
    @Override
    public String toString() {
        return "Billing{" + "patient : " + patient + ", amount : " + amount + ", paid : " + paid + "}";
    }
}
