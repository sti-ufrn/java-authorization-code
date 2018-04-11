Esse fluxo deve ser utilizado por aplicações que queiram acessar as informações privadas das contas de usuários dos sistemas SINFO, como por exemplo: turmas de um usuário, frequências de um discente, histórico de utilização de um usuário no restaurante universitário, etc. Assim, aplicações com esse propósito precisam seguir os passos abaixo:

  1. O usuário inicia a interação com a aplicação;

  2. A aplicação faz uma requisição GET ao authorization server através da URL /authz-server/oauth/authorize, passando os parâmetros client_id, response_type e redirect_uri como QueryParam;

    https://autenticacao.info.ufrn.brauthz-server/oauth/authorize?client_id=AppId&response_type=code&redirect_uri=http://enderecoapp.com.br/pagina

  3. O usuário é redirecionado para o authorization server. Na página de autenticação exibida, deve informar suas credenciais (username, password) e, em seguida, informar se autoriza que a aplicação utilize seus dados. Para garantir a segurança das informações dos usuários, a página de autenticação exibida é a do servidor de autenticação da Superintendência de Informática (SINFO/UFRN). Além disso, vale ressaltar que são os usuários que realizam a autorização do uso dos dados disponibilizados pelos serviços da API;
    
  4. O authorization server retorna o código de autorização para a aplicação;
    
  5. Em posse desse código, a aplicação pode usá-lo para obter um access_token para o usuário. Desse modo, ela realiza uma nova requisição, que neste caso é do tipo POST, ao authorization_server através da URL URL-BASE/authz-server/oauth/token, passando os parâmetros client_id, client_secret, redirect_uri, grant_type e code como QueryParam. Ex.:

    POST https://autenticacao.info.ufrn.br/authz-server/oauth/token?client_id=AppId&client_secret=AppSecret&redirect_uri=http://enderecoapp.com.br/pagina&grant_type=authorization_code&code=code
                                                        

  6. O authorization server retorna à aplicação um JSON contendo o access_token, token_type, refresh_token, expires_in e scope;

    { 
      “access_token”: “111”, 
      “token_type”: “bearer”, 
      "refresh_token": "abcd", 
      “expires_in”: 7431095, 
      “scope”: “read” 
     }
