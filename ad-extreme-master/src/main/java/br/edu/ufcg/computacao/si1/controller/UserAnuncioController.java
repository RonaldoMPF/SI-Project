package br.edu.ufcg.computacao.si1.controller;

import br.edu.ufcg.computacao.si1.model.form.AnuncioForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserAnuncioController {
    
    @Autowired
    private AnuncioController anuncioController;
    
    private String role;
    
    public UserAnuncioController() {
		super();
		this.anuncioController = new AnuncioController();
		this.role = "user";
	}

	@RequestMapping(value = "/user/cadastrar/anuncio", method = RequestMethod.GET)
    public ModelAndView getPageCadastrarAnuncio(AnuncioForm anuncioForm){
    	return this.anuncioController.getPageCadastrarAnuncio(anuncioForm, this.role);
    }

    @RequestMapping(value = "/user/listar/anuncios", method = RequestMethod.GET)
    public ModelAndView getPageListarAnuncios(){
    	return this.anuncioController.getPageListarAnuncios(this.role);
    }
    
    @RequestMapping(value = "/user/listar/meus_anuncios", method = RequestMethod.GET)
    public ModelAndView getPageListarMeusAnuncios(){
    	return this.anuncioController.getPageListarMeusAnuncios(this.role);
    }

    @RequestMapping(value = "/user/cadastrar/anuncio", method = RequestMethod.POST)
    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes){
    	return this.anuncioController.cadastroAnuncio(anuncioForm, result, attributes, role);
    }
}
