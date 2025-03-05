package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.InsuranceCom;


public class InsuranceComForm extends BaseForm<InsuranceCom> {
    private final InsuranceCom insurance;
    private boolean submitted = false;


    private JTextField insuranceIDField;
    private JTextField companyNameField;
    private JTextField addressField;
    private JTextField phoneField;

    public InsuranceComForm(JFrame parent, InsuranceCom insurance){
        super(parent, insurance,"Add Insurance Company");
        this.insurance = insurance; 
    }
    @Override
    protected void createFormFields(){
        insuranceIDField = new JTextField(20);
        companyNameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);

        addFormField("Insurance ID", insuranceIDField);
        addFormField("Company Name", companyNameField);
        addFormField("Address", addressField);
        addFormField("Phone", phoneField);
    }
    @Override
    protected void populateFormFields(){
        if (entity != null) {
            insuranceIDField.setText(entity.getInsuranceID());
            companyNameField.setText(entity.getCompanyName());
            addressField.setText(entity.getAddress());
            phoneField.setText(entity.getPhone());
        }
    }

    @Override
    protected void saveEntity(){
        insurance.setInsuranceID(insuranceIDField.getText());
        insurance.setCompanyName(companyNameField.getText());
        insurance.setAddress(addressField.getText());
        insurance.setPhone(phoneField.getText());

        submitted = true;
        dispose();
    }
    public boolean isSubmitted(){
        return submitted;
    }
}