package br.com.m3Tech.appGasLegado.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class UiComponents {

    private UiComponents() {
    }

    public static void applyFrameDefaults(JFrame frame) {
        frame.getContentPane().setBackground(AppTheme.BACKGROUND);
        positionTopLeft(frame);
    }

    public static void positionTopLeft(JFrame frame) {
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();
        }
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
        frame.setLocation(insets.left, insets.top);
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(AppTheme.fontLabelBold());
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(0, 40));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return button;
    }

    public static JButton secondaryButton(String text) {
        JButton button = primaryButton(text);
        button.putClientProperty("JButton.buttonType", "roundRect");
        return button;
    }

    public static JPanel cardPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(AppTheme.SURFACE);
        panel.setOpaque(true);
        TitledBorder titled = BorderFactory.createTitledBorder(
                new LineBorder(AppTheme.BORDER, 1, true),
                title != null ? title : "",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                AppTheme.fontLabelBold(),
                AppTheme.TEXT
        );
        panel.setBorder(new CompoundBorder(titled, new EmptyBorder(AppTheme.PAD_SM, AppTheme.PAD, AppTheme.PAD, AppTheme.PAD)));
        return panel;
    }

    public static JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(AppTheme.SURFACE);
        panel.setOpaque(true);
        panel.setBorder(new CompoundBorder(
                new LineBorder(AppTheme.BORDER, 1, true),
                new EmptyBorder(AppTheme.PAD, AppTheme.PAD, AppTheme.PAD, AppTheme.PAD)
        ));
        return panel;
    }

    public static JPanel headerBar(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.SURFACE);
        header.setBorder(new CompoundBorder(
                new LineBorder(AppTheme.BORDER, 1, false),
                new EmptyBorder(AppTheme.PAD, AppTheme.PAD_LG, AppTheme.PAD, AppTheme.PAD_LG)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.fontTitle());
        titleLabel.setForeground(AppTheme.PRIMARY_DARK);
        header.add(titleLabel, BorderLayout.WEST);
        return header;
    }

    public static JPanel kpiCard(String label, Color valueColor) {
        JPanel card = cardPanel(label);
        card.setLayout(new BorderLayout(0, 4));
        JLabel value = new JLabel("0", SwingConstants.CENTER);
        value.setFont(AppTheme.fontKpi());
        value.setForeground(valueColor);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    public static JLabel kpiValueLabel(JPanel kpiCard) {
        Component[] components = kpiCard.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel && ((JLabel) c).getFont().getSize() >= 24) {
                return (JLabel) c;
            }
        }
        if (kpiCard.getComponentCount() > 0 && kpiCard.getComponent(0) instanceof JLabel) {
            return (JLabel) kpiCard.getComponent(0);
        }
        return new JLabel("0");
    }

    public static void styleTable(JTable table) {
        table.setFont(AppTheme.font(Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setGridColor(AppTheme.BORDER);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(AppTheme.TEXT);
        table.setBackground(AppTheme.SURFACE);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(AppTheme.fontLabelBold());
            header.setBackground(AppTheme.BACKGROUND);
            header.setForeground(AppTheme.TEXT);
            header.setReorderingAllowed(false);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? AppTheme.SURFACE : new Color(248, 250, 252));
                }
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void styleErrorLabel(JLabel label) {
        label.setForeground(AppTheme.DANGER);
        label.setFont(AppTheme.font(Font.PLAIN, 11));
    }

    public static void markFieldError(JTextField field) {
        field.setBackground(AppTheme.ERROR_FIELD);
    }

    public static void clearFieldError(JTextField field) {
        field.setBackground(UIManager.getColor("TextField.background"));
    }
}
