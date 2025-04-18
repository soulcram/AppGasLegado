package br.com.m3Tech.appGasLegado;

import programagas.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

public class TelaMenu extends JFrame {
    private JButton botaoClientes;
    private JButton botaoProdutos;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JPanel jPanel1;

    public TelaMenu() {
        this.initComponents();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.botaoClientes = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton5 = new JButton();
        this.botaoProdutos = new JButton();
        this.setDefaultCloseOperation(2);
        this.setTitle("Menu");
        this.jPanel1.setBackground(new Color(255, 255, 255));
        this.botaoClientes.setBackground(new Color(0, 163, 253));
        this.botaoClientes.setFont(new Font("Tahoma", 1, 12));
        this.botaoClientes.setText("Clientes");
        this.botaoClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoClientesActionPerformed(evt);
            }
        });
        this.jButton2.setBackground(new Color(0, 163, 253));
        this.jButton2.setFont(new Font("Tahoma", 1, 12));
        this.jButton2.setText("Endereços");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        this.jButton3.setBackground(new Color(0, 163, 253));
        this.jButton3.setFont(new Font("Tahoma", 1, 12));
        this.jButton3.setText("Pedidos");
        this.jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        this.jButton4.setBackground(new Color(0, 163, 253));
        this.jButton4.setFont(new Font("Tahoma", 1, 12));
        this.jButton4.setText("Funcionarios");
        this.jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        this.jButton5.setBackground(new Color(0, 163, 253));
        this.jButton5.setFont(new Font("Tahoma", 1, 12));
        this.jButton5.setText("Configurações");
        this.jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        this.botaoProdutos.setBackground(new Color(0, 163, 253));
        this.botaoProdutos.setFont(new Font("Tahoma", 1, 12));
        this.botaoProdutos.setText("Produtos");
        this.botaoProdutos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoProdutosActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(60, 60, 60).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.botaoProdutos, -2, 275, -2).addComponent(this.jButton5, -2, 275, -2).addComponent(this.jButton4, -2, 275, -2).addComponent(this.jButton3, -2, 275, -2).addComponent(this.jButton2, -2, 275, -2).addComponent(this.botaoClientes, -2, 275, -2)).addContainerGap(65, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(44, 44, 44).addComponent(this.botaoClientes).addGap(18, 18, 18).addComponent(this.jButton2).addGap(18, 18, 18).addComponent(this.jButton3).addGap(18, 18, 18).addComponent(this.jButton4).addGap(18, 18, 18).addComponent(this.botaoProdutos).addGap(18, 18, 18).addComponent(this.jButton5).addContainerGap(167, 32767)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, Alignment.TRAILING, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jPanel1, Alignment.TRAILING, -1, -1, 32767));
        this.pack();
    }

    private void botaoClientesActionPerformed(ActionEvent evt) {
        (new TelaCliente("1159295445")).setVisible(true);
        this.dispose();
    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        (new TelaConfig()).setVisible(true);
        this.dispose();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        (new CadastroEndereço()).setVisible(true);
        this.dispose();
    }

    private void jButton4ActionPerformed(ActionEvent evt) {
        (new TelaFuncionarios()).setVisible(true);
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

