package br.edu.ufcg.computacao.si1.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.form.AnuncioForm;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;

@Controller
public class AnuncioController {

	@Autowired
    private AnuncioServiceImpl anuncioService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
    
    public AnuncioController() {
	}

	@SuppressWarnings("static-access")
	public ModelAndView getPageCadastrarAnuncio(AnuncioForm anuncioForm, String role){
        ModelAndView model = new ModelAndView();

        model.addObject("tipos", anuncioForm.getTipos());
        model.setViewName(role + "/cadastrar_anuncio");

        return model;
    }

    public ModelAndView getPageListarAnuncios(String role){
        ModelAndView model = new ModelAndView();

        model.addObject("anuncios", anuncioService.getAll());

        model.setViewName(role + "/listar_anuncios");

        return model;
    }
    
    public ModelAndView getPageListarMeusAnuncios(String role){
        ModelAndView model = new ModelAndView();

        model.addObject("meus_anuncios", this.getAnunciosDoUsuario());

        model.setViewName(role + "/listar_meus_anuncios");

        return model;
    }

    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes, String role){
        if(result.hasErrors()){
            return getPageCadastrarAnuncio(anuncioForm, role);
        }

        Anuncio anuncio = new Anuncio(anuncioForm.getTitulo(), anuncioForm.getPreco(),
        		anuncioForm.getTipo(), this.getNomeCriador(), this.getEmailCriador());

        anuncioService.create(anuncio);
        Usuario usuarioAtual = this.getUsuarioAtual();
        usuarioAtual.addListaAnuncios(anuncio.get_id());
        usuarioService.update(usuarioAtual);

        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
        return new ModelAndView("redirect:/"+ role +"/cadastrar/anuncio");
    }
    
    private String getEmailCriador(){
		return this.getUsuarioAtual().getEmail();
	}
	
    private String getNomeCriador(){	  
		return this.getUsuarioAtual().getNome();
	}
    
    private Usuario getUsuarioAtual(){
    	Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Usuario usuarioAtual = usuarioService.getUserByEmail(((UserDetails) usuarioLogado).getUsername());
		
		return usuarioAtual;
    }
    
    private List<Anuncio> getAnunciosDoUsuario(){
    	
    	List<Anuncio> listaAnuncios = new ArrayList<Anuncio>();
    	
    	for (Long id : getUsuarioAtual().getMeusAnuncios()) {
			listaAnuncios.add(anuncioService.getOneById(id));
		}

    	return listaAnuncios;
    }
    
}
