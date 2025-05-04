package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.ClienteDto;
import br.com.m3Tech.appGasLegado.dto.ClienteLegadoDto;
import br.com.m3Tech.appGasLegado.dto.PedidoDto;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.appGasLegado.utils.LocalDateDeserializer;
import br.com.m3Tech.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


public class Service {


    public ClienteDto getCliente(String telefone){
        try {
            Config config = new ConfigService().getConfig();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .create();

            if (StringUtils.emptyOrNull(telefone)) {
                return null;
            }


            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, config.getUrlService().trim())
                    .pathValue(config.getContextService().trim())
                    .pathValue("cliente")
                    .pathValue("telefone")
                    .pathValue(telefone)
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                return gson.fromJson(responseEntity.getBody(), ClienteDto.class);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void enviarPedido(PedidoDto pedido){
        try {
            Config config = new ConfigService().getConfig();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.PUT, config.getUrlService().trim())
                    .pathValue(config.getContextService().trim())
                    .pathValue("pedido")
                    .bodyFromObject(pedido)
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            System.out.println(responseEntity.getStatusCode());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void salvarCliente(ClienteLegadoDto clienteLegadoDto){
        try {
            Config config = new ConfigService().getConfig();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.PUT, config.getUrlService().trim())
                    .pathValue(config.getContextService().trim())
                    .pathValue("cliente")
                    .bodyFromObject(clienteLegadoDto)
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            System.out.println(responseEntity.getStatusCode());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
