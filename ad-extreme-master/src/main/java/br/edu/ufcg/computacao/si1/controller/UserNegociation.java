package br.edu.ufcg.computacao.si1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;

@Controller
public class UserNegociation {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private AnuncioServiceImpl anuncioService;
	
	@RequestMapping(value = "/user/listar/comprar", method = RequestMethod.POST)
	public ModelAndView compra(RedirectAttributes attributes,
    		@RequestParam(value = "idAnuncio") long idAnuncio){
		
		Anuncio anuncio = anuncioService.getOneById(idAnuncio);
		Usuario comprador = this.getUsuarioAtual();
		Usuario vendendor = usuarioService.getUserByEmail(anuncio.getCriadorEmail());
		
		if (comprador.equals(vendendor)) {
			attributes.addFlashAttribute("msgCompraFalha", "Você não pode comprar seu própio produto!");
		}
		else if (anuncio.getTipo().equals("emprego")) {
			attributes.addFlashAttribute("msgCompraFalha", "Você não pode comprar um serviço!");
		}
		else {
			double precoAnuncio = anuncio.getPreco();
			
			comprador.debitar(precoAnuncio);
			vendendor.creditar(precoAnuncio);
			vendendor.retiraAnuncio(idAnuncio);
			
			anuncioService.delete(idAnuncio);

	        attributes.addFlashAttribute("msgCompraSucesso", "Compra realizada com sucesso!");
		}
        return new ModelAndView("redirect:/user/listar/anuncios");
	}
	
    private Usuario getUsuarioAtual(){
    	Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Usuario usuarioAtual = usuarioService.getUserByEmail(((UserDetails) usuarioLogado).getUsername());
		
		return usuarioAtual;
    }
	

}
