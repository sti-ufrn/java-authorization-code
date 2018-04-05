package br.api.exemplo;

import br.api.exemplo.dto.CursoDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor Gusttavo Henrique (gusttavo@info.ufrn.br)
 * @since 04/04/18
 */
@Service
public class CursoService {

    /**
     * Método que requisita o access_token associado ao usuário que realizou a autenticação.
     *
     * @param urlBaseAutenticacao
     * @param code
     * @param credentials
     * @return
     */
    public String getAccessToken(String urlBaseAutenticacao, String code, Credentials credentials) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(urlBaseAutenticacao + "authz-server/oauth/token?client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=http://localhost:8080/cursos", credentials.getClientId(), credentials.getClientSecret(), code);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(responseEntity.getBody());
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            return (String) jsonObject.get("access_token");
        }

        return null;
    }

    /**
     * Método que busca na API.Sistemas os cursos da instituição.
     *
     * @param urlBase
     * @param versao
     * @param xApiKey
     * @param token
     */
    public List<CursoDTO> getCursos(String urlBase, String versao, String xApiKey, String token) {
        RestTemplate restTemplate = new RestTemplate();
        List<CursoDTO> cursos = new ArrayList<>();

        String url = urlBase + "curso/" + versao + "/cursos";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "bearer " + token);
        headers.add("x-api-key", xApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if ( responseEntity.getStatusCode() == HttpStatus.OK ) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            String result = responseEntity.getBody();
            try {
                if (result != null ) {
                    if (result.charAt(0) == '[') {
                        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, CursoDTO.class);
                        cursos.addAll(objectMapper.readValue(result, type));
                    } else {
                        cursos.add(objectMapper.readValue(result, CursoDTO.class));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return cursos;
        }

        return null;
    }

}