package org.game.gui;

import org.game.profile.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JTextField nameField;
    private JButton createProfileButton;
    private JButton loadProfileButton;
    private JLabel infoLabel;
    private ProfilePanel profilePanel;

    public MainMenu(ProfilePanel profilePanel) {
        this.profilePanel = profilePanel;
        setLayout(new GridLayout(4, 1));
        setupUIComponents();
    }

    private void setupUIComponents() {
        nameField = new JTextField("Enter your name");
        createProfileButton = new JButton("Create Profile");
        loadProfileButton = new JButton("Load Profile");
        infoLabel = new JLabel();

        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new profile
                Profile profile = new Profile(nameField.getText(), 0);

                // Save the profile to an XML file
                try {
                    Profile.appendProfileToXml(profile);
                    infoLabel.setText("Profile created and saved.");

                    // Get reference to ProfilePanel and update it with loaded profile
                    profilePanel.updateProfile(profile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    infoLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        loadProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                // Load the profile from an XML file
                try {
                    Profile profile = Profile.loadProfileFromXml("profile.xml", name);
                    infoLabel.setText("Profile loaded: " + profile.getName() + ", XP: " + profile.getXp());

                    // Get reference to ProfilePanel and update it with loaded profile
                    profilePanel.updateProfile(profile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    infoLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        add(nameField);
        add(createProfileButton);
        add(loadProfileButton);
        add(infoLabel);
    }

    public JButton getCreateProfileButton() {
        return createProfileButton;
    }

    public JButton getLoadProfileButton() {
        return loadProfileButton;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public JTextField getNameField() {
        return nameField;
    }
}
