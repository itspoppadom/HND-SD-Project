package com.hospital.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationConfig {
    private static final Map<String, FieldValidation[]> VALIDATION_RULES = new HashMap<>();

    static {
        // Patient validation rules
        VALIDATION_RULES.put("patient", new FieldValidation[] {
            new FieldValidation("patientID", 10, true, "^[A-Z0-9]+$", "Patient ID must be alphanumeric and uppercase"),
            new FieldValidation("firstName", 20, true, "^[A-Za-z\\s]+$", "First name must contain only letters"),
            new FieldValidation("lastName", 30, true, "^[A-Za-z\\s]+$", "Last name must contain only letters"),
            new FieldValidation("address", 40, true, null, "Address is required"),
            new FieldValidation("postcode", 8, true, "^[A-Z0-9\\s]+$", "Invalid postcode format"),
            new FieldValidation("phone", 18, true, "^[0-9\\-\\+]+$", "Invalid phone number format"),
            new FieldValidation("email", 40, true, "^[A-Za-z0-9+_.-]+@(.+)$", "Invalid email format"),
            new FieldValidation("insuranceID", 10, false, "^[A-Z0-9]+$", "Insurance ID must be alphanumeric and cannot exceed 10 characters")
        });
        
        VALIDATION_RULES.put("doctor", new FieldValidation[] {
            new FieldValidation("doctorID", 5, true, "^[A-Z0-9]+$", "Doctor ID must be alphanumeric and uppercase"),
            new FieldValidation("firstName", 18, true, "^[A-Za-z\\s]+$", "First name must contain only letters"),
            new FieldValidation("lastName", 30, true, "^[A-Za-z\\s]+$", "Last name must contain only letters"),
            new FieldValidation("address", 50, true, null, "Address is required"),
            new FieldValidation("email", 40, true, "^[A-Za-z0-9+_.-]+@(.+)$", "Invalid email format"),
            new FieldValidation("specialization", 30, true, "^[A-Za-z\\s]+$", "Specialization must contain only letters"),
            new FieldValidation("hospital", 64, false, null, "Hospital cannot exceed 64 characters")
        });

        VALIDATION_RULES.put("drug", new FieldValidation[] {
            new FieldValidation("drugID", 8, true, "^[A-Z0-9]+$", "Drug ID must be alphanumeric and uppercase"),
            new FieldValidation("name", 40, true, "^[A-Za-z\\s]+$", "Name must contain only letters"),
            new FieldValidation("sideEffects", 60, true, null, "Side effects are required"),
            new FieldValidation("benefits", 255, true, null, "Benefits are required")
        }); 

        VALIDATION_RULES.put("prescription", new FieldValidation[] {
            new FieldValidation("prescriptionID", 15, true, "^[A-Z0-9]+$", "Prescription ID must be alphanumeric and uppercase"),
            new FieldValidation("datePrescribed", 10, true, "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", "Invalid date format (YYYY-MM-DD)"),
            new FieldValidation("dosage", 10, true, "^[0-9]", "Dosage must contain only numbers"),
            new FieldValidation("duration", 10, true, "^[0-9]", "Duration must contain only numbers"),
            new FieldValidation("comment", 255, false, null, null),
            new FieldValidation("drugID", 8, true, "^[A-Z0-9]+$", "Drug ID must be alphanumeric and uppercase"),
            new FieldValidation("doctorID", 5, true, "^[A-Z0-9]+$", "Doctor ID must be alphanumeric and uppercase"),
            new FieldValidation("patientID", 10, true, "^[A-Z0-9]+$", "Patient ID must be alphanumeric and uppercase")
        });

        VALIDATION_RULES.put("insurance", new FieldValidation[] {
            new FieldValidation("insuranceID", 10, true, "^[A-Z0-9]+$", "Insurance ID must be alphanumeric and uppercase"),
            new FieldValidation("name", 40, true, "^[A-Za-z\\s]+$", "Name must contain only letters"),
            new FieldValidation("address", 40, true, null, "Address is required"),
            new FieldValidation("phone", 16, true, "^[0-9\\-\\+]+$", "Invalid phone number format")
        });

        VALIDATION_RULES.put("visit", new FieldValidation[] {
            new FieldValidation("patientID", 10, true, "^[A-Z0-9]+$", "Patient ID must be alphanumeric and uppercase"),
            new FieldValidation("doctorID", 5, true, "^[A-Z0-9]+$", "Doctor ID must be alphanumeric and uppercase"),
            new FieldValidation("dateOfVisit", 10, true, "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", "Invalid date format (YYYY-MM-DD)"),
            new FieldValidation("symptoms", 255, true, null, "Symptoms are required"),
            new FieldValidation("diagnosisID", 12, false, "^[A-Z0-9]+$", "Diagnosis ID must be alphanumeric and uppercase")
        });

    }

    public static FieldValidation[] getValidationRules(String entityType) {
        return VALIDATION_RULES.get(entityType.toLowerCase());
    }
}
