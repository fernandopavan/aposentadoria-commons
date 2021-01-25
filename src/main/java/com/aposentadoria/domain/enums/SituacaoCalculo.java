package com.aposentadoria.domain.enums;

public enum SituacaoCalculo {

    ERRO(0, "Erro no cálculo"),
    PROCESSANDO(1, "Processando cálculo"),
    CONCLUIDO(2, "Cálculo concluido");

    private int id;
    private String descricao;

    SituacaoCalculo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoCalculo toEnum(Integer id) {
        if (id == null) {
            return null;
        }

        for (SituacaoCalculo x : SituacaoCalculo.values()) {
            if (id.equals(x.getId())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + id);
    }


}
