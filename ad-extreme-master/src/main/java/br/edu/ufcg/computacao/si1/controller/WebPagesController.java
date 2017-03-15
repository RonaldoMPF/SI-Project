package br.edu.ufcg.computacao.si1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.repository.UsuarioRepository;

@Controller
public class WebPagesController {
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getPageIndex(){
        ModelAndView model = new ModelAndView();
        model.setViewName("index");

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getPageLogin(){
        ModelAndView model = new ModelAndView();
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView getPageIndexUser(){
        ModelAndView model = new ModelAndView();
        model.setViewName("user/index");
        Object usuarioLogado =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuarioAtual = usuarioRepositorio.findByEmail(((UserDetails) usuarioLogado).getUsername());
        model.addObject("saldo",usuarioAtual.getSaldo());
        return model;
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public ModelAndView getPageIndexCompany(){
        ModelAndView model = new ModelAndView();
        model.setViewName("company/index");

        return model;
    }
}
