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
    	
//        ModelAndView model = new ModelAndView();
//
//        model.addObject("tipos", anuncioForm.getTipos());
//        model.setViewName("user/cadastrar_anuncio");
//
//        return model;
    }

    @RequestMapping(value = "/user/listar/anuncios", method = RequestMethod.GET)
    public ModelAndView getPageListarAnuncios(){
    	return this.anuncioController.getPageListarAnuncios(this.role);
    	
//        ModelAndView model = new ModelAndView();
//
//        model.addObject("anuncios", anuncioRep.findAll());
//
//        model.setViewName("user/listar_anuncios");
//
//        return model;
    }
    
    @RequestMapping(value = "/user/listar/meus_anuncios", method = RequestMethod.GET)
    public ModelAndView getPageListarMeusAnuncios(){
    	return this.anuncioController.getPageListarMeusAnuncios(this.role);
    }

    //Bad smell
    @RequestMapping(value = "/user/cadastrar/anuncio", method = RequestMethod.POST)
    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes){
    	return this.anuncioController.cadastroAnuncio(anuncioForm, result, attributes, role);
//        if(result.hasErrors()){
//            return getPageCadastrarAnuncio(anuncioForm);
//        }
//
//        Anuncio anuncio = new Anuncio(anuncioForm.getTitulo(), anuncioForm.getPreco(), anuncioForm.getTipo());
////        anuncio.setTitulo(anuncioForm.getTitulo());
////        anuncio.setPreco(anuncioForm.getPreco());
////        anuncio.setTipo(anuncioForm.getTipo());
//
//        anuncioService.create(anuncio);
//
//        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
//        return new ModelAndView("redirect:/user/cadastrar/anuncio");
    }

}
