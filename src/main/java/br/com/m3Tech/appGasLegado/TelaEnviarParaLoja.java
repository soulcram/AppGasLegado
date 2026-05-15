package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.AlterarLojaDto;
import br.com.m3Tech.appGasLegado.ui.AppTheme;
import br.com.m3Tech.appGasLegado.ui.UiComponents;
import br.com.m3Tech.appGasLegado.ui.UiLayout;
import br.com.m3Tech.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class TelaEnviarParaLoja extends JFrame {

    private JButton bEnviar;
    private JComboBox<String> boxLojas;
    private Integer idPedido;

    public TelaEnviarParaLoja(Integer idPedido) {
        try {
            this.idPedido = idPedido;
            this.initComponents();
            this.boxLojas.removeAllItems();
            this.boxLojas.addItem("Consigaz Piraju");
            this.boxLojas.addItem("Consigaz 3 Marias");
            this.boxLojas.addItem("Gasbom Cocaia");
            this.boxLojas.addItem("Consigaz 3 Coracoes");
        } catch (Exception var9) {
            log.error(var9.getMessage() + "  Local:  " + var9.getLocalizedMessage());
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Enviar para loja");
        UiComponents.applyFrameDefaults(this);

        boxLojas = new JComboBox<>();
        bEnviar = UiComponents.primaryButton("Enviar");
        bEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });

        JPanel form = UiLayout.formPanel("Selecione a loja");
        UiLayout.addFormRow(form, 0, UiLayout.formRow("Loja", boxLojas));

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        footer.add(bEnviar);

        JPanel root = new JPanel(new BorderLayout(0, AppTheme.PAD));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG, AppTheme.PAD_LG));
        root.add(UiComponents.headerBar("Enviar para loja"), BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        getContentPane().add(root);
        pack();
        setSize(Math.max(getWidth(), 420), Math.max(getHeight(), 220));
    }

    private void bEnviarActionPerformed(ActionEvent evt) {
        String lojaSelecionada = boxLojas.getSelectedItem().toString();

        if (StringUtils.emptyOrNull(lojaSelecionada)) {
            log.error("Nenhuma loja Selecionada");
            return;
        }

        if (lojaSelecionada.equals(ProgramaGas.nomeLoja)) {
            log.error("Loja Selecionada já é a loja atual");
            return;
        }

        AlterarLojaDto alterarLojaDto = new AlterarLojaDto();
        alterarLojaDto.setLoja(lojaSelecionada);
        alterarLojaDto.setIdPedido(this.idPedido);

        new Service().alterarLoja(alterarLojaDto);
        this.dispose();
    }
}
