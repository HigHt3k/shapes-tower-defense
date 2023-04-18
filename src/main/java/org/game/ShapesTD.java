package org.game;

import org.game.gui.MainFrame;

import javax.swing.*;

public class ShapesTD {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}