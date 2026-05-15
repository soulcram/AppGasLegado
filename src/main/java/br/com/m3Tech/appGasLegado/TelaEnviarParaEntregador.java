package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.AlterarEntregadorDto;
import br.com.m3Tech.appGasLegado.dto.EntregadorDisponivelDto;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.ui.UiLayout;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Slf4j
public class TelaEnviarParaEntregador extends JFrame {

    private JButton bEnviar;
    private JComboBox<String> boxEntregadores;
    private Integer idPedido;

    public TelaEnviarParaEntregador(Integer idPedido) {
        try {
            this.idPedido = idPedido;
            this.initComponents();
            carregarBoxEntregadores();
        } catch (Exception var9) {
            log.error(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }
    }

    private void carregarBoxEntregadores() {
        this.boxEntregadores.removeAllItems();
        List<EntregadorDisponivelDto> entregadores = new Service().getEntregadores(idPedido);

        for (EntregadorDisponivelDto item : entregadores) {
            this.boxEntregadores.addItem(item.toString());
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Enviar para entregador");
        UiComponents.applyFrameDefaults(this);

        boxEntregadores = new JComboBox<>();
        bEnviar = UiComponents.primaryButton("Enviar");
        bEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });

        JPanel form = UiLayout.formPanel("Selecione o entregador");
        UiLayout.addFormRow(form, 0, UiLayout.formRow("Entregador", boxEntregadores));

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        footer.add(bEnviar);

        JPanel root = new JPanel(new BorderLayout(0, AppTheme.PAD));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG));
        root.add(UiComponents.headerBar("Enviar para entregador"), BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 520), Math.max(getHeight(), 220));
    }

    private void bEnviarActionPerformed(ActionEvent evt) {
        String entregadorSelecionado = boxEntregadores.getSelectedItem().toString();

        if (StringUtils.emptyOrNull(entregadorSelecionado)) {
            log.error("Nenhuma entregador Selecionada");
            return;
        }

        String[] split = entregadorSelecionado.split(" \\| ");

        AlterarEntregadorDto alterarEntregadorDto = new AlterarEntregadorDto();
        alterarEntregadorDto.setIdPedido(this.idPedido);
        alterarEntregadorDto.setIdEntregador(Integer.valueOf(split[0]));
        alterarEntregadorDto.setNomeEntregador(split[1]);

        new Service().alterarEntregador(alterarEntregadorDto);
        ProgramaGas.atualizarTabelaPedidos = true;
        this.dispose();
    }
}
