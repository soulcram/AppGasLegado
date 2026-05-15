package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import programagas.TelaPedidosConsulta;
import programagas.TelaProdutos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TelaMenu extends JFrame {
    private JButton botaoClientes;
    private JButton botaoProdutos;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;

    public TelaMenu() {
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Menu");
        UiComponents.applyFrameDefaults(this);

        botaoClientes = createMenuButton("Clientes");
        botaoClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoClientesActionPerformed(evt);
            }
        });

        jButton2 = createMenuButton("Endereços");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3 = createMenuButton("Pedidos");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4 = createMenuButton("Funcionários");
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        botaoProdutos = createMenuButton("Produtos");
        botaoProdutos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoProdutosActionPerformed(evt);
            }
        });

        jButton5 = createMenuButton("Configurações");
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        JPanel grid = new JPanel(new GridLayout(3, 2, AppTheme.PAD, AppTheme.PAD));
        grid.setOpaque(false);
        grid.add(botaoClientes);
        grid.add(jButton2);
        grid.add(jButton3);
        grid.add(jButton4);
        grid.add(botaoProdutos);
        grid.add(jButton5);

        JPanel card = UiComponents.cardPanel("Menu principal");
        card.setLayout(new BorderLayout());
        card.add(grid, BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(new EmptyBorder(AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG));
        root.add(UiComponents.headerBar("Menu"), BorderLayout.NORTH);
        root.add(card, BorderLayout.CENTER);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 480), Math.max(getHeight(), 420));
    }

    private JButton createMenuButton(String text) {
        JButton button = UiComponents.primaryButton(text);
        button.setPreferredSize(new java.awt.Dimension(200, 52));
        return button;
    }

    private void botaoClientesActionPerformed(ActionEvent evt) {
        (new TelaCliente("")).setVisible(true);
        this.dispose();
    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        (new TelaConfig()).setVisible(true);
        this.dispose();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void jButton4ActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void botaoProdutosActionPerformed(ActionEvent evt) {
        (new TelaProdutos()).setVisible(true);
        this.dispose();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        (new TelaPedidosConsulta()).setVisible(true);
        this.dispose();
    }
}
