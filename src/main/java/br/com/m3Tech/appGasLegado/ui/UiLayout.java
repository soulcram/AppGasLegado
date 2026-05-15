package br.com.m3Tech.appGasLegado.ui;

import javax.swing.*;
import java.awt.*;

public final class UiLayout {

    private UiLayout() {
    }

    public static JPanel formRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(AppTheme.PAD_SM, 0));
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(AppTheme.fontLabelBold());
        label.setForeground(AppTheme.TEXT);
        label.setPreferredSize(new Dimension(140, 28));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    public static JPanel formPanel(String title) {
        JPanel panel = UiComponents.cardPanel(title);
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    public static void addFormRow(JPanel panel, int row, JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, AppTheme.PAD_SM, 0);
        panel.add(component, gbc);
    }

    public static void styleInfoLabel(JLabel label) {
        label.setFont(AppTheme.fontSubtitle());
        label.setForeground(AppTheme.TEXT);
    }
}
