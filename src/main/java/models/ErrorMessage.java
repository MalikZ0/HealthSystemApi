/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Acer
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
    private String errorMessage;
    private int errorCode;
    private String note;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, int errorCode, String note) {
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.note = note;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getNote() {
        return note;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" + "errorMessage=" + errorMessage + ", errorCode=" + errorCode + ", note=" + note + '}';
    }
    
}
