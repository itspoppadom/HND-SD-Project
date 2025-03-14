package com.hospital.validation;

public class FieldValidation {

    // Attributes
    private final String fieldName;
    private final int maxLength;
    private final boolean required;
    private final String pattern;
    private final String errorMessage;

    // Constructor
    public FieldValidation(String fieldName, int maxLength, boolean required, String pattern, String errorMessage) {
        this.fieldName = fieldName;
        this.maxLength = maxLength;
        this.required = required;
        this.pattern = pattern;
        this.errorMessage = errorMessage;
    }

    // Getters
    public String getFieldName() { return fieldName; }
    public int getMaxLength() { return maxLength; }
    public boolean isRequired() { return required; }
    public String getPattern() { return pattern; }
    public String getErrorMessage() { return errorMessage; }
}