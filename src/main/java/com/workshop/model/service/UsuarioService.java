package com.workshop.model.service;

import com.workshop.exception.UsuarioNull;
import com.workshop.model.dao.DaoFactory;
import com.workshop.model.dao.UsuarioInterface;
import com.workshop.model.entities.Usuario;

public class UsuarioService {

    private UsuarioInterface usuarioDao;
    public UsuarioService() {
        usuarioDao = DaoFactory.createUsuarioDao();
    }

    public Usuario login(Usuario usuario){
        if(usuario.getUsername() == null || usuario.getSenha() == null)
            throw new UsuarioNull("Usuario nulo, por favor preencha os campos");

        Usuario dbUsuario = usuarioDao.getUsuario(usuario.getUsername());
        if(dbUsuario == null)
            throw new UsuarioNull("Username ou senha incorretos");

        if(!dbUsuario.getSenha().equals(usuario.getSenha()))
            throw new UsuarioNull("Username ou senha incorretos");

        return dbUsuario;
    }
}
