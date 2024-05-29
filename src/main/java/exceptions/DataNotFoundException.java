/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author Acer
 */
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7034897190745766939L;
    
    public DataNotFoundException(String message) {
        super(message);
    }
}


