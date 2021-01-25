package com.aposentadoria.domain;

import com.aposentadoria.domain.enums.Perfil;
import com.aposentadoria.domain.enums.SituacaoCalculo;
import com.aposentadoria.domain.pattern.AbstractEntity;
import com.aposentadoria.domain.pattern.EntityBuilder;
import com.aposentadoria.domain.validation.UsuarioValid;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@UsuarioValid
@Table(name = "USUARIOS")
public class Usuario extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "Preenchimento do nome obrigatório")
    @Length(min = 3, max = 120, message = "O nome deve ter entre {min} e {max} caracteres")
    private String nome;

    @CPF(message = "CPF inválido")
    @NotEmpty(message = "Preenchimento do CPF é obrigatório")
    private String cpf;

    @Email(message = "E-mail inválido")
    @NotEmpty(message = "Preenchimento do e-mail obrigatório")
    private String email;

    @NotEmpty(message = "Preenchimento da senha obrigatória")
    private String senha;

    private Integer numeroAnosRecebendo;

    private BigDecimal saldoTotal = BigDecimal.ZERO;

    private BigDecimal valorAtualMensal = BigDecimal.ZERO;

    private SituacaoCalculo situacao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Integer> perfis = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Integer getNumeroAnosRecebendo() {
        return numeroAnosRecebendo;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public BigDecimal getValorAtualMensal() {
        return valorAtualMensal;
    }

    public SituacaoCalculo getSituacao() {
        return situacao;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public static final class Builder extends EntityBuilder<Usuario> {

        public Builder(Usuario entity, EntityBuilder.State state) {
            super(entity, state);
        }

        public static Builder create() {
            return new Builder(new Usuario(), State.NEW);
        }

        public static Builder from(Usuario entity) {
            return new Builder(entity, State.BUILT);
        }

        public Builder nome(String nome) {
            entity.nome = nome;
            return this;
        }

        public Builder cpf(String cpf) {
            entity.cpf = cpf;
            return this;
        }

        public Builder email(String email) {
            entity.email = email;
            return this;
        }

        public Builder senha(String senha) {
            entity.senha = senha;
            return this;
        }

        public Builder numeroAnosRecebendo(Integer numeroAnosRecebendo) {
            entity.numeroAnosRecebendo = numeroAnosRecebendo;
            return this;
        }

        public Builder saldoTotal(BigDecimal saldoTotal) {
            entity.saldoTotal = saldoTotal;
            return this;
        }

        public Builder valorAtualMensal(BigDecimal valorAtualMensal) {
            entity.valorAtualMensal = valorAtualMensal;
            return this;
        }

        public Builder situacao(SituacaoCalculo situacao) {
            entity.situacao = situacao;
            return this;
        }

        public Builder perfis(Set<Perfil> perfis) {
            List<Integer> perfisId = perfis.stream().map(Perfil::getId).collect(Collectors.toList());
            entity.perfis.addAll(perfisId);
            return this;
        }
    }

}
