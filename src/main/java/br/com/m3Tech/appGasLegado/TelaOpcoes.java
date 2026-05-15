package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.OpcoesDto;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.utils.ImpressoraUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class TelaOpcoes extends JFrame {
    private JButton botaoEditarCliente;
    private JButton botaoReimprimir;
    private JButton botaoEnviarParaLoja;
    private JButton botaoEnviarParaEntregador;

    private String telefone;
    private Integer idPedido;

    public TelaOpcoes(OpcoesDto opcoesDto) {
        this.telefone = opcoesDto.getTelefone();
        this.idPedido = opcoesDto.getIdPedido();
        this.initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Opções");
        UiComponents.applyFrameDefaults(this);

        botaoEditarCliente = createActionButton("Editar cliente");
        botaoEditarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoClientesActionPerformed(evt);
            }
        });

        botaoReimprimir = createActionButton("Reimprimir pedido");
        botaoReimprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoReimprimirActionPerformed(evt);
            }
        });

        botaoEnviarParaLoja = createActionButton("Enviar para loja");
        botaoEnviarParaLoja.setVisible(ProgramaGas.servico);
        botaoEnviarParaLoja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoEnviarParaLojaActionPerformed(evt);
            }
        });

        botaoEnviarParaEntregador = createActionButton("Enviar para entregador");
        botaoEnviarParaEntregador.setVisible(ProgramaGas.servico);
        botaoEnviarParaEntregador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botaoEnviarParaEntregadorActionPerformed(evt);
            }
        });

        JPanel actions = UiComponents.cardPanel("Ações do pedido");
        actions.setLayout(new GridLayout(0, 1, 0, AppTheme.PAD_SM));
        actions.add(botaoEditarCliente);
        actions.add(botaoReimprimir);
        if (ProgramaGas.servico) {
            actions.add(botaoEnviarParaLoja);
            actions.add(botaoEnviarParaEntregador);
        }

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG));
        root.add(UiComponents.headerBar("Opções"), BorderLayout.NORTH);
        root.add(actions, BorderLayout.CENTER);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 340), Math.max(getHeight(), 320));
    }

    private JButton createActionButton(String text) {
        JButton button = UiComponents.primaryButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return button;
    }

    private void botaoClientesActionPerformed(ActionEvent evt) {
        (new TelaCliente(telefone)).setVisible(true);
        this.dispose();
    }

    private void botaoReimprimirActionPerformed(ActionEvent evt) {
        if (br.com.m3Tech.utils.StringUtils.emptyOrNull(ProgramaGas.Impressora)) {
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
