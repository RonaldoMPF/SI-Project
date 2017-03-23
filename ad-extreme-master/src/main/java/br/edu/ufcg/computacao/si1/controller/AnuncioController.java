package br.edu.ufcg.computacao.si1.controller;


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
import br.edu.ufcg.computacao.si1.repository.AnuncioRepository;
import br.edu.ufcg.computacao.si1.repository.UsuarioRepository;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;

@Controller
public class AnuncioController {

	@Autowired
    private AnuncioServiceImpl anuncioService;

    @Autowired
    private AnuncioRepository anuncioRep;
    
    @Autowired
    private UsuarioRepository usuarioRep;
    
    public AnuncioController() {
	}

	public ModelAndView getPageCadastrarAnuncio(AnuncioForm anuncioForm, String role){
        ModelAndView model = new ModelAndView();

        model.addObject("tipos", anuncioForm.getTipos());
        model.setViewName(role + "/cadastrar_anuncio");

        return model;
    }

    public ModelAndView getPageListarAnuncios(String role){
        ModelAndView model = new ModelAndView();

        model.addObject("anuncios", anuncioRep.findAll());

        model.setViewName(role + "/listar_anuncios");

        return model;
    }

    //Bad smell
    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes, String role){
        if(result.hasErrors()){
            return getPageCadastrarAnuncio(anuncioForm, role);
        }

        
        Anuncio anuncio = new Anuncio(anuncioForm.getTitulo(), anuncioForm.getPreco(), anuncioForm.getTipo(), this.getNomeCriador(), this.getEmailCriador());
//        anuncio.setTitulo(anuncioForm.getTitulo());
//        anuncio.setPreco(anuncioForm.getPreco());
//        anuncio.setTipo(anuncioForm.getTipo());

        anuncioService.create(anuncio);

        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
        return new ModelAndView("redirect:/"+ role +"/cadastrar/anuncio");
    }
    
    private String getEmailCriador(){
    	Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuarioAtual = usuarioRep.findByEmail(((UserDetails) usuarioLogado).getUsername());
	  
		return usuarioAtual.getEmail();
	}
	
    private String getNomeCriador(){
    	Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuarioAtual = usuarioRep.findByEmail(((UserDetails) usuarioLogado).getUsername());
	  
		return usuarioAtual.getN();
	}
}
