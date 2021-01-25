package com.aposentadoria.domain;

import com.aposentadoria.domain.enums.Perfil;
import com.aposentadoria.domain.enums.SituacaoCalculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest(classes = Usuario.class)
class UsuarioTest {

    @Test
    void deveCriarAPartirDoBuilder() {
        Usuario usuario = Usuario.Builder.create()
                .nome("Nome 1")
                .cpf("385.939.530-00")
                .email("teste@gmail.com")
                .senha("123")
                .numeroAnosRecebendo(2)
                .saldoTotal(BigDecimal.TEN)
                .valorAtualMensal(BigDecimal.ONE)
                .situacao(SituacaoCalculo.CONCLUIDO)
                .perfis(Collections.singleton(Perfil.ADMIN))
                .build();

        Assertions.assertEquals("Nome 1", usuario.getNome());
        Assertions.assertEquals("385.939.530-00", usuario.getCpf());
        Assertions.assertEquals("teste@gmail.com", usuario.getEmail());
        Assertions.assertEquals("123", usuario.getSenha());
        Assertions.assertEquals(2, usuario.getNumeroAnosRecebendo());
        Assertions.assertEquals(BigDecimal.TEN, usuario.getSaldoTotal());
        Assertions.assertEquals(BigDecimal.ONE, usuario.getValorAtualMensal());
        Assertions.assertEquals(SituacaoCalculo.CONCLUIDO, usuario.getSituacao());
        Assertions.assertEquals(Collections.singleton(Perfil.ADMIN), usuario.getPerfis());
    }

}