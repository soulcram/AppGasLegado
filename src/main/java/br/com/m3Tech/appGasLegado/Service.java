package br.com.m3Tech.appGasLegado;

import br.com.m3Tech.appGasLegado.dto.ClienteDto;
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

    @Value("${url.service}")
    private String url;

    @Value("${context.service}")
    private String contextService;

    public ClienteDto getCliente(String telefone){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();

        if(StringUtils.emptyOrNull(telefone)){
            return null;
        }


        ResponseEntity<String> responseEntity = new RequestApiClient(new RestTemplate(), HttpMethod.GET, url)
                .pathValue(contextService)
                .pathValue("cliente")
                .pathValue("telefone")
                .pathValue(telefone)
                .addHeader("Authorization", new AutorizationUtil().getAutorization())
                .addAcceptJson()
                .addContentTypeJson()
                .build()
                .enviar();

        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            return gson.fromJson(responseEntity.getBody(), ClienteDto.class);
        }

        return null;
    }
}
