package com.aposentadoria.domain.validation;

import com.aposentadoria.domain.QUsuario;
import com.aposentadoria.domain.Usuario;
import com.aposentadoria.domain.enums.Perfil;
import com.aposentadoria.domain.exception.FieldMessage;
import com.aposentadoria.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioValidator implements ConstraintValidator<UsuarioValid, Usuario> {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void initialize(UsuarioValid ann) {
    }

    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (repository == null) {
            return true;
        }

        Long id = usuario.getId();

        list.addAll(existsEmail(list, id, usuario.getEmail()));
        list.addAll(existsCpf(list, id, usuario.getCpf()));
        list.addAll(validaBeneficiario(list, id, usuario));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private List<FieldMessage> existsEmail(List<FieldMessage> list, Long id, String email) {
        Usuario existsEmail = repository.findOne(QUsuario.usuario.email.eq(email)).orElse(null);

        if ((id == null && existsEmail != null) || (id != null && existsEmail != null && !id.equals(existsEmail.getId()))) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        return list;
    }

    private List<FieldMessage> existsCpf(List<FieldMessage> list, Long id, String cpf) {
        Usuario existsCpf = repository.findOne(QUsuario.usuario.cpf.eq(cpf)).orElse(null);

        if ((id == null && existsCpf != null) || (id != null && existsCpf != null && !id.equals(existsCpf.getId()))) {
            list.add(new FieldMessage("cpf", "CPF já existente"));
        }

        return list;
    }

    private List<FieldMessage> validaBeneficiario(List<FieldMessage> list, Long id, Usuario usuario) {
        Integer numeroAnosRecebendo = usuario.getNumeroAnosRecebendo();

        if ((numeroAnosRecebendo == null || numeroAnosRecebendo == 0) && usuario.getPerfis().contains(Perfil.BENEFICIARIO)) {
            list.add(new FieldMessage("numeroAnosRecebendo", "Preenchimento do número de anos recebendo é obrigatório"));
        }

        return list;
    }
}

