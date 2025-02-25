package com.hospital.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseForm<T> extends JDialog {
    protected T entity;
    protected boolean submitted = false;
    protected JPanel mainPanel;
    protected GridBagConstraints gbc;

    public BaseForm(JFrame parent, T entity, String title) {
        super(parent, title, true);
        this.entity = entity;
        initializeForm();
    }

    protected void initializeForm() {
        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        setContentPane(mainPanel);
        createFormFields();
        createButtons();
        
        pack();
        setLocationRelativeTo(getOwner());
    }

    protected abstract void createFormFields();
    protected abstract void saveEntity();

    protected void createButtons() {
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            saveEntity();
            submitted = true;
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);
    }

    protected void addFormField(String label, javax.swing.JComponent component) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel(label + ":"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(component, gbc);
    }

    public boolean isSubmitted() {
        return submitted;
    }
}