package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Drug;

public class DrugForm extends BaseForm<Drug> { 
    private final Drug drug;
    private boolean submitted = false;

    private JTextField drugIDField;
    private JTextField drugNameField;
    private JTextField sideEffectsField;
    private JTextField benefitsField;

    public DrugForm(JFrame parent, Drug drug){
        super(parent, drug, "Drug Form");
        this.drug = drug;
    
    }

        
    @Override
    protected void createFormFields() {
        drugIDField = new JTextField(20);
        drugNameField = new JTextField(20);
        sideEffectsField = new JTextField(20);
        benefitsField = new JTextField(20);

        addFormField("Drug ID", drugIDField);
        addFormField("Drug Name", drugNameField);
        addFormField("Side Effects", sideEffectsField);
        addFormField("Benefits", benefitsField);

    }
    @Override 
    protected void populateFormFields() {
        if (entity != null) { 
            drugIDField.setText(entity.getDrugID());
            drugNameField.setText(entity.getDrugName());
            sideEffectsField.setText(entity.getSideEffects());
            benefitsField.setText(entity.getBenefits());
        }
    }

    @Override
    protected void saveEntity() { 
        drug.setDrugID(drugIDField.getText());
        drug.setDrugName(drugNameField.getText());
        drug.setSideEffects(sideEffectsField.getText());
        drug.setBenefits(benefitsField.getText());

        submitted = true;
        dispose();
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    }
