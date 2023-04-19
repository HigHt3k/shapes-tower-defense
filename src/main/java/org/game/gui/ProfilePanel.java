package org.game.gui;

import org.game.profile.Profile;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private Profile profile;
    private JLabel nameLabel;
    private JLabel xpLabel;

    public ProfilePanel(Profile profile) {
        this.profile = profile;
        setupUIComponents();
    }

    private void setupUIComponents() {
        setLayout(new GridLayout(2, 1));

        nameLabel = new JLabel("Name: " + profile.getName());
        xpLabel = new JLabel("Available XP: " + profile.getXp());

        add(nameLabel);
        add(xpLabel);
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
        nameLabel.setText("Name: " + profile.getName());
        xpLabel.setText("Available XP: " + profile.getXp());
    }
}



