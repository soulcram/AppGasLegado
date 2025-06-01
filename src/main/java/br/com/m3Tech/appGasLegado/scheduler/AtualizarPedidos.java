package br.com.m3Tech.appGasLegado.scheduler;


import br.com.m3Tech.appGasLegado.ProgramaGas;
import br.com.m3Tech.appGasLegado.Vendas;
import br.com.m3Tech.appGasLegado.dto.DadosImpressaoDto;
import br.com.m3Tech.appGasLegado.dto.PedidoServicoDto;
import br.com.m3Tech.appGasLegado.utils.ImpressoraUtils;
import br.com.m3Tech.utils.BooleanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import java.util.List;

@Service
@Slf4j
public class AtualizarPedidos {
	
	@Scheduled(cron = "*/10 * * * * *") // Executa a cada 30 seg
    public void reimprimir() {

		if(!BooleanUtils.defaultFalseIfNull(ProgramaGas.servico)){
			return;
		}

		if(!BooleanUtils.defaultFalseIfNull(ProgramaGas.atualizarTabelaPedidos)){
			return;
		}

		ProgramaGas.atualizarTabelaPedidos = false;
		DefaultTableModel model = (DefaultTableModel)ProgramaGas.tabela1.getModel();
		model.setNumRows(0);
		List<PedidoServicoDto> allPedidos = new br.com.m3Tech.appGasLegado.Service().getAllPedidos();

		for(PedidoServicoDto pedidoServicoDto : allPedidos){
			String endereco = pedidoServicoDto.getLogradouro() + ", " + pedidoServicoDto.getNumeroResidencia() + " - " + pedidoServicoDto.getBairro() + "    Prox: " + pedidoServicoDto.getComplemento();

			String[] conteudo = new String[]{pedidoServicoDto.getPedido(), pedidoServicoDto.getNomeCliente(), pedidoServicoDto.getTelefoneCliente(), endereco, pedidoServicoDto.getFormaPagamento(), pedidoServicoDto.getEntregador(), pedidoServicoDto.getStatus(), pedidoServicoDto.getHora(), pedidoServicoDto.getIdPedido().toString()};
			model.addRow(conteudo);
		}

		Vendas.pedidosAberto();

    }

}
