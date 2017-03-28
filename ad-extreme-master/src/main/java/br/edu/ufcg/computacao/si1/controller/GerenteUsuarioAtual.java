package br.edu.ufcg.computacao.si1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.repository.UsuarioRepository;

//OCORREU NULL POINTER

public class GerenteUsuarioAtual {
	
	@Autowired
	private static UsuarioRepository usuarioRep;
	
	public static Usuario getUsuarioAtual(){
	   	Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuarioAtual = usuarioRep.findByEmail(((UserDetails) usuarioLogado).getUsername());
		
		return usuarioAtual;
    }

}
