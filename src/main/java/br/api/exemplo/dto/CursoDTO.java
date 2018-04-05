package br.api.exemplo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @autor Gusttavo Henrique (gusttavo@info.ufrn.br)
 * @since 05/04/18
 */
public class CursoDTO {

    @JsonProperty("id-curso")
    private Integer idCurso;

    @JsonProperty("curso")
    private String curso;

    @JsonProperty("nivel")
    private String nivel;

    @JsonProperty("id-unidade")
    private Long idUnidade;

    @JsonProperty("unidade")
    private String unidade;

    @JsonProperty("municipio")
    private String municipio;

    @JsonProperty("id-situacao-curso")
    private Long idSituacaoCurso;

    @JsonProperty("id-modalidade-educacao")
    private Long idModalidadeEducacao;

    @JsonProperty("tipo-curso")
    private String tipoCurso;

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Long getIdSituacaoCurso() {
        return idSituacaoCurso;
    }

    public void setIdSituacaoCurso(Long idSituacaoCurso) {
        this.idSituacaoCurso = idSituacaoCurso;
    }

    public Long getIdModalidadeEducacao() {
        return idModalidadeEducacao;
    }

    public void setIdModalidadeEducacao(Long idModalidadeEducacao) {
        this.idModalidadeEducacao = idModalidadeEducacao;
    }

    public String getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }
}
