package com.hospital.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.hospital.models.Doctor;
import com.hospital.models.Drug;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;
import com.hospital.models.Prescription;
import com.hospital.models.Visit;

public class CustomTableModel extends AbstractTableModel {

    //Attributes for the CustomTableModel
    private final Object[][] data;
    private final String[] columnNames;
    private final String tableType;

    //Constructor for the CustomTableModel
    public CustomTableModel(List<?> items, String tableType) {
        this.tableType = tableType;
        this.columnNames = getColumnNamesForType(tableType);
        this.data = convertListToArray(items);
    }


    //Function to get the column names for the table
    private String[] getColumnNamesForType(String type) {

        //Switch statement to get the column names based on the table type
        return switch (type.toLowerCase()) {
            case "patient" -> new String[]{"Patient ID", "First Name", "Last Name", "Address", 
                                         "Postcode", "Phone", "Email", "Insurance ID"};
            case "doctor" -> new String[]{"Doctor ID", "First Name", "Last Name", "Address", 
                                        "Email", "Specialization", "Hospital"};
            case "drug" -> new String[]{"Drug ID", "Name", "Side Effects", "Benefits"};
            case "prescription" -> new String[]{"Prescription ID", "Date Prescribed", "Dosage", 
                                              "Duration", "Comment", "Drug ID", "Doctor ID", "Patient ID"};
            case "insurance" -> new String[]{"Insurance ID", "Name", "Address", "Phone"};
            case "visit" -> new String[]{"Patient ID", "Doctor ID", "Date of Visit", 
                                       "Symptoms", "Diagnosis ID"};
            default -> throw new IllegalArgumentException("Unknown table type: " + type);
        };
    }

    //Function to convert the list of items to a 2D array
    private Object[][] convertListToArray(List<?> items) {
        if (items.isEmpty()) {
            return new Object[0][getColumnCount()];
        }
        // Create a 2D array to store the data
        Object[][] result = new Object[items.size()][getColumnCount()];
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            result[i] = switch (tableType.toLowerCase()) {
                case "patient" -> convertPatient((Patient) item);
                case "doctor" -> convertDoctor((Doctor) item);
                case "drug" -> convertDrug((Drug) item);
                case "prescription" -> convertPrescription((Prescription) item);
                case "insurance" -> convertInsurance((InsuranceCom) item);
                case "visit" -> convertVisit((Visit) item);
                default -> throw new IllegalArgumentException("Unknown table type: " + tableType);
            };
        }
        return result;
    }

    //Functions to convert the patient object to an array
    private Object[] convertPatient(Patient patient) {
        return new Object[]{
            patient.getPatientID(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getAddress(),
            patient.getPostcode(),
            patient.getPhoneNumber(),
            patient.getEmail(),
            patient.getInsuranceID()
        };
    }

    //Function to convert the doctor object to an array
    private Object[] convertDoctor(Doctor doctor) {
        return new Object[]{
            doctor.getDoctorID(),
            doctor.getFirstName(),
            doctor.getLastName(),
            doctor.getAddress(),
            doctor.getEmail(),
            doctor.getSpecialization(),
            doctor.getHospital()
        };
    }

    //Function to convert the drug object to an array
    private Object[] convertDrug(Drug drug) {
        return new Object[]{
            drug.getDrugID(),
            drug.getDrugName(),
            drug.getSideEffects(),
            drug.getBenefits()
        };
    }

    //Function to convert the prescription object to an array
    private Object[] convertPrescription(Prescription prescription) {
        return new Object[]{
            prescription.getPrescriptionID(),
            prescription.getDatePrescribed(),
            prescription.getDosage(),
            prescription.getDuration(),
            prescription.getComment(),
            prescription.getDrugID(),
            prescription.getDoctorID(),
            prescription.getPatientID()
        };
    }

    //Function to convert the insurance object to an array
    private Object[] convertInsurance(InsuranceCom insurance) {
        return new Object[]{
            insurance.getInsuranceID(),
            insurance.getCompanyName(),
            insurance.getAddress(),
            insurance.getPhone()
        };
    }

    //Function to convert the visit object to an array
    private Object[] convertVisit(Visit visit) {
        return new Object[]{
            visit.getPatientID(),
            visit.getDoctorID(),
            visit.getDateOfVisit(),
            visit.getSymptoms(),
            visit.getDiagnosisID()
        };
    }

    //Function to get the number of rows
    @Override
    public int getRowCount() {
        return data.length;
    }

    //Function to get the number of columns
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    //Function to get the value at a specific row and column
    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    //Function to get the column name
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
}