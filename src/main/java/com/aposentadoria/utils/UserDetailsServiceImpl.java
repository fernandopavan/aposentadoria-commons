package com.aposentadoria.utils;

import com.aposentadoria.domain.QUsuario;
import com.aposentadoria.domain.Usuario;
import com.aposentadoria.repositories.UsuarioRepository;
import com.aposentadoria.security.UserSS;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findOne(QUsuario.usuario.email.eq(email));

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(usuario.get().getId(), usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getPerfis());
    }
}
