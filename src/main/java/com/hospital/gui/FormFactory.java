package com.hospital.gui;

import javax.swing.JFrame;

import com.hospital.models.Doctor;
import com.hospital.models.Drug;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;
import com.hospital.models.Prescription;
import com.hospital.models.Visit;

public class FormFactory {

    // Method to get the correct type of form
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