package br.com.m3Tech.appGasLegado.ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class AppTheme {

    public static final Color PRIMARY = new Color(0, 150, 255);
    public static final Color PRIMARY_DARK = new Color(0, 112, 204);
    public static final Color BACKGROUND = new Color(244, 246, 248);
    public static final Color SURFACE = Color.WHITE;
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);
    public static final Color SUCCESS = new Color(22, 163, 74);
    public static final Color DANGER = new Color(220, 38, 38);
    public static final Color WARNING = new Color(217, 119, 6);
    public static final Color ERROR_FIELD = new Color(254, 226, 226);

    public static final int PAD = 12;
    public static final int PAD_SM = 8;
    public static final int PAD_LG = 16;

    private static final String FONT_FAMILY = resolveFontFamily();

    private AppTheme() {
    }

    public static void install() {
        try {
            FlatLightLaf.setup();
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 6);
            UIManager.put("ScrollBar.width", 12);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new Dimension(0, 0));
            UIManager.put("Table.rowHeight", 28);
            UIManager.put("Button.minimumWidth", 80);
            UIManager.put("defaultFont", font(Font.PLAIN, 12));
        } catch (Exception e) {
            System.err.println("FlatLaf não disponível, usando L&F padrão: " + e.getMessage());
        }
    }

    public static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    public static Font fontTitle() {
        return font(Font.BOLD, 20);
    }

    public static Font fontSubtitle() {
        return font(Font.BOLD, 14);
    }

    public static Font fontKpi() {
        return font(Font.BOLD, 36);
    }

    public static Font fontLabel() {
        return font(Font.PLAIN, 12);
    }

    public static Font fontLabelBold() {
        return font(Font.BOLD, 12);
    }

    public static void styleContentPane(Container contentPane) {
        contentPane.setBackground(BACKGROUND);
    }

    public static EmptyBorder padding() {
        return new EmptyBorder(PAD, PAD, PAD, PAD);
    }

    public static EmptyBorder paddingSm() {
        return new EmptyBorder(PAD_SM, PAD_SM, PAD_SM, PAD_SM);
    }

    private static String resolveFontFamily() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] families = ge.getAvailableFontFamilyNames();
        for (String preferred : new String[]{"Segoe UI", "Inter", "Roboto", "Dialog"}) {
            for (String f : families) {
                if (f.equalsIgnoreCase(preferred)) {
                    return f;
                }
            }
        }
        return "Dialog";
    }
}
