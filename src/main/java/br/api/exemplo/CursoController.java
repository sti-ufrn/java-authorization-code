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

    @GetMapping("/")
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
    public String getCursos(@RequestParam(value = "code", defaultValue = "") String code, HttpSession session, Model model) {

        Credentials credentials = (Credentials) session.getAttribute("credentials");
        if (credentials == null || code.isEmpty())
            return "redirect:/";

        String accessToken = getToken(code, credentials, session);

        List<CursoDTO> cursos = cursoService.getCursos(urlBase, versao, credentials.getxApiKey(), accessToken);
        model.addAttribute("cursos", cursos);

        return "list";
    }

    private String getToken(String code, Credentials credentials, HttpSession session) {

        String accessToken = (String) session.getAttribute("accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            accessToken = cursoService.getAccessToken(urlBaseAutenticacao, code, credentials);
            session.setAttribute("accessToken", accessToken);
        }

        return accessToken;
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) throws URISyntaxException {
        session.invalidate();

        URI url = new URI(urlBaseAutenticacao+"authz-server/j_spring_cas_security_logout");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}
