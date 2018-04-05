package br.api.exemplo;

/**
 * @autor Gusttavo Henrique (gusttavo@info.ufrn.br)
 * @since 04/04/18
 */
public class Credentials {

    private String xApiKey;

    private String clientId;

    private String clientSecret;

    public String getxApiKey() {
        return xApiKey;
    }

    public void setxApiKey(String xApiKey) {
        this.xApiKey = xApiKey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}