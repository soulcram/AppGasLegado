package br.com.m3Tech.appGasLegado.scheduler;


import br.com.m3Tech.appGasLegado.ProgramaGas;
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
public class ReimprimirPedidoService {
	
	@Scheduled(cron = "*/30 * * * * *") // Executa a cada 30 seg
    public void reimprimir() {

		if(!BooleanUtils.defaultFalseIfNull(ProgramaGas.servico)){
			return;
		}

		if(ProgramaGas.tabela1 == null){
			log.error("Tabela de vendas ainda não inicializada.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel)ProgramaGas.tabela1.getModel();

		List<PedidoServicoDto> allPedidos = new br.com.m3Tech.appGasLegado.Service().getNovosPedidos();

		if(allPedidos == null || allPedidos.isEmpty()){
			return;
		}
		log.info( "Reimprimindo pedidos");
		for(PedidoServicoDto pedidoServicoDto : allPedidos){
			log.info( "Reimprimindo pedido: {}", pedidoServicoDto.getIdPedido());
			String endereco = pedidoServicoDto.getLogradouro() + ", " + pedidoServicoDto.getNumeroResidencia() + " - " + pedidoServicoDto.getBairro() + "    Prox: " + pedidoServicoDto.getComplemento();

			String[] conteudo = new String[]{pedidoServicoDto.getPedido(), pedidoServicoDto.getNomeCliente(), pedidoServicoDto.getTelefoneCliente(), endereco, pedidoServicoDto.getFormaPagamento(), "Funcionário", "Status", pedidoServicoDto.getHora(), pedidoServicoDto.getIdPedido().toString()};
			model.addRow(conteudo);

			DadosImpressaoDto dadosImpressaoDto = new DadosImpressaoDto(pedidoServicoDto);

			ImpressoraUtils.reimprimir(dadosImpressaoDto);
		}

        
    }

}
