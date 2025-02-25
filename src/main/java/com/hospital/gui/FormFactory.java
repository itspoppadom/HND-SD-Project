package com.hospital.gui;

import javax.swing.JFrame;
import com.hospital.models.*;

public class FormFactory {
    public static <T> BaseForm<T> getForm(String type, JFrame parent, T entity) {
        return switch (type.toLowerCase()) {
            case "patient" -> (BaseForm<T>) new PatientForm(parent, (Patient) entity);
            case "doctor" -> (BaseForm<T>) new DoctorForm(parent, (Doctor) entity);
            case "drug" -> (BaseForm<T>) new DrugForm(parent, (Drug) entity);
            case "insurance" -> (BaseForm<T>) new InsuranceComForm(parent, (InsuranceCom) entity);
            case "prescription" -> (BaseForm<T>) new PrescriptionForm(parent, (Prescription) entity);
            case "visit" -> (BaseForm<T>) new VisitForm(parent, (Visit) entity);
            default -> throw new IllegalArgumentException("Unknown form type: " + type);
        };
    }
}