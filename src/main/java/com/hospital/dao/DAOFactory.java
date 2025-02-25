package com.hospital.dao;



public class DAOFactory {
    @SuppressWarnings("unchecked")
    public static <T> BaseDAO<T> getDAO(String type) {
        return switch (type.toLowerCase()) {
            case "patient" -> (BaseDAO<T>) new PatientDAO();
            case "doctor" -> (BaseDAO<T>) new DoctorDAO();
            case "drug" -> (BaseDAO<T>) new DrugDAO();
            case "prescription" -> (BaseDAO<T>) new PrescriptionDAO();
            case "insurance" -> (BaseDAO<T>) new InsuranceComDAO();
            case "visit" -> (BaseDAO<T>) new VisitDAO();
            default -> throw new IllegalArgumentException("Unknown table type: " + type);
        };
    }
    
}