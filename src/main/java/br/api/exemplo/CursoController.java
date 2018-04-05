package br.api.exemplo;

import br.api.exemplo.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @autor Gusttavo Henrique (gusttavo@info.ufrn.br)
 * @since 04/04/18
 */
@Controller
public class CursoController {

    @Autowired
    CursoService cursoService;

    String urlBase = "<URL-BASE-API>";
    String urlBaseAutenticacao = "<URL-BASE-AUTENTICACAO>";
    String versao = "<VERSAO>";

    @GetMapping("/autenticacao")
    public String formAutenticacao(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "autenticacao";
    }

    @PostMapping("/autorizacao")
    public ResponseEntity<Object> redirecionarParaPaginaAutorizacao(@ModelAttribute Credentials credentials, HttpSession session) throws URISyntaxException {
        URI url = new URI(String.format(urlBaseAutenticacao + "authz-server/oauth/authorize?client_id=%s&response_type=code&redirect_uri=http://localhost:8080/cursos", credentials.getClientId()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url);
        session.setAttribute("credentials", credentials);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/cursos")
    public String getCursos(@RequestParam(value = "code") String code, HttpSession session, Model model) {
        Credentials credentials = (Credentials) session.getAttribute("credentials");
        String accessToken = cursoService.getAccessToken(urlBaseAutenticacao, code, credentials);

        List<CursoDTO> cursos = cursoService.getCursos(urlBase, versao, credentials.getxApiKey(), accessToken);
        model.addAttribute("cursos", cursos);

        return "list";
    }

}
