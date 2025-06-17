package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.DadosImpressaoDto;
import br.com.m3Tech.appGasLegado.dto.OpcoesDto;
import br.com.m3Tech.appGasLegado.service.PedidoService;
import br.com.m3Tech.appGasLegado.utils.ImpressoraUtils;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.print.*;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static br.com.m3Tech.appGasLegado.utils.ImpressoraUtils.SemAcento;
import static br.com.m3Tech.appGasLegado.utils.ImpressoraUtils.gerarNotaTermica;

@Slf4j
public class TelaOpcoes extends JFrame {
    private JButton botaoEditarCliente;
    private JButton botaoReimprimir;
    private JButton botaoEnviarParaLoja;
    private JButton botaoEnviarParaEntregador;
    private JPanel jPanel1;



    private String telefone;
    private Integer idPedido;

    private Boolean pedidoLocal;

    public TelaOpcoes(OpcoesDto opcoesDto) {
        this.telefone = opcoesDto.getTelefone();
        this.idPedido = opcoesDto.getIdPedido();
        this.pedidoLocal = opcoesDto.getPedidoLocal();

        this.initComponents();
    }

    private void initComponents() {

        this.setDefaultCloseOperation(2);
        this.setTitle("Opções");

        this.jPanel1 = new JPanel();
        this.jPanel1.setBackground(new Color(255, 255, 255));

        this.botaoEditarCliente = new JButton();
        this.botaoEditarCliente.setBackground(new Color(0, 163, 253));
        this.botaoEditarCliente.setFont(new Font("Tahoma", 1, 12));
        this.botaoEditarCliente.setText("Editar Cliente");
        this.botaoEditarCliente.setBounds(10,10,200,30);
        this.botaoEditarCliente.setVisible(true);
        this.botaoEditarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoClientesActionPerformed(evt);
            }
        });

        this.botaoReimprimir = new JButton();
        this.botaoReimprimir.setBackground(new Color(0, 163, 253));
        this.botaoReimprimir.setFont(new Font("Tahoma", 1, 12));
        this.botaoReimprimir.setText("Reimprimir Pedido");
        this.botaoReimprimir.setBounds(10,60,200,30);
        this.botaoReimprimir.setVisible(true);
        this.botaoReimprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoReimprimirActionPerformed(evt);
            }
        });

        this.botaoEnviarParaLoja = new JButton();
        this.botaoEnviarParaLoja.setBackground(new Color(0, 163, 253));
        this.botaoEnviarParaLoja.setFont(new Font("Tahoma", 1, 12));
        this.botaoEnviarParaLoja.setText("Enviar para Loja");
        this.botaoEnviarParaLoja.setBounds(10,110,200,30);
        this.botaoEnviarParaLoja.setVisible(ProgramaGas.servico);
        this.botaoEnviarParaLoja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoEnviarParaLojaActionPerformed(evt);
            }
        });

        this.botaoEnviarParaEntregador = new JButton();
        this.botaoEnviarParaEntregador.setBackground(new Color(0, 163, 253));
        this.botaoEnviarParaEntregador.setFont(new Font("Tahoma", 1, 12));
        this.botaoEnviarParaEntregador.setText("Enviar para Entregador");
        this.botaoEnviarParaEntregador.setBounds(10,160,200,30);
        this.botaoEnviarParaEntregador.setVisible(ProgramaGas.servico);
        this.botaoEnviarParaEntregador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoEnviarParaEntregadorActionPerformed(evt);
            }
        });




        jPanel1.setBounds(10,10,300,300);
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.WHITE);

        jPanel1.add(botaoEditarCliente);
        jPanel1.add(botaoReimprimir);
        jPanel1.add(botaoEnviarParaLoja);
        jPanel1.add(botaoEnviarParaEntregador);

        this.setBounds(10, 10, 300, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(jPanel1);
        this.repaint();


    }

    private void botaoClientesActionPerformed(ActionEvent evt) {
        (new TelaCliente(telefone)).setVisible(true);
        this.dispose();
    }

    private void botaoReimprimirActionPerformed(ActionEvent evt) {


        if(StringUtils.emptyOrNull(ProgramaGas.Impressora)){
            log.error("nenhuma Impressora Configurada como padrão.");
            return;
        }

        ImpressoraUtils.reimprimirPedido(this.idPedido);


        this.dispose();
    }

    private void botaoEnviarParaLojaActionPerformed(ActionEvent evt) {

        (new TelaEnviarParaLoja(idPedido)).setVisible(true);
        this.dispose();
    }

    private void botaoEnviarParaEntregadorActionPerformed(ActionEvent evt) {

        (new TelaEnviarParaEntregador(idPedido)).setVisible(true);
        this.dispose();
    }

}

