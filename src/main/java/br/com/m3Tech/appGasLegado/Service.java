package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.*;
import br.com.m3Tech.appGasLegado.entity.Config;
import br.com.m3Tech.appGasLegado.service.ConfigService;
import br.com.m3Tech.appGasLegado.utils.AutorizationUtil;
import br.com.m3Tech.appGasLegado.utils.LocalDateDeserializer;
import br.com.m3Tech.appGasLegado.utils.LocalTimeDeserializer;
import br.com.m3Tech.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Service {


    public ClienteDto getCliente(String telefone){
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .create();

            if (StringUtils.emptyOrNull(telefone)) {
                return null;
            }


            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
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

    public void salvarCliente(ClienteLegadoDto clienteLegadoDto){
        try {

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.PUT, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
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

    public PedidoServicoDto enviarPedido(PedidoDto pedido){
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                    .create();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.PUT, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
                    .pathValue("pedido")
                    .bodyFromObject(pedido)
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            System.out.println(responseEntity.getStatusCode());
            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                return gson.fromJson(responseEntity.getBody(), PedidoServicoDto.class);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public DadosImpressaoDto getPedido(Integer idPedido){
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                    .create();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
                    .pathValue("pedido")
                    .pathValue(idPedido.toString())
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            System.out.println(responseEntity.getStatusCode());

            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                PedidoServicoDto pedidoServicoDto = gson.fromJson(responseEntity.getBody(), PedidoServicoDto.class);
                return new DadosImpressaoDto(pedidoServicoDto);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void alterarLoja(AlterarLojaDto pedido){
        try {

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.PUT, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
                    .pathValue("pedido")
                    .pathValue("alterarLoja")
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

    public List<PedidoServicoDto> getAllPedidos(){
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                    .create();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
                    .pathValue("pedido")
                    .pathValue("diario")
                    .pathValue(ProgramaGas.nomeLoja)
                    .pathValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                Type listType = new TypeToken<List<PedidoServicoDto>>() {}.getType();
                return gson.fromJson(responseEntity.getBody(), listType);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<PedidoServicoDto> getNovosPedidos(){
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                    .create();

            ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, ProgramaGas.urlService)
                    .pathValue(ProgramaGas.contextoService)
                    .pathValue("pedido")
                    .pathValue("novos")
                    .pathValue(ProgramaGas.nomeLoja)
                    .pathValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addHeader("Authorization", new AutorizationUtil().getAutorization())
                    .addAcceptJson()
                    .addContentTypeJson()
                    .build()
                    .enviar();

            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                Type listType = new TypeToken<List<PedidoServicoDto>>() {}.getType();
                return gson.fromJson(responseEntity.getBody(), listType);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }


}
