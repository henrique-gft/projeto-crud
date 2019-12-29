package com.com.henrique.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.com.henrique.model.StatusTitulo;
import com.com.henrique.model.Titulo;
import com.com.henrique.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private Titulos titulos;
	
	@ModelAttribute(value = "statusEnum")
	public List<StatusTitulo> statusEnum(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	@ModelAttribute(value = "titulos")
	public List<Titulo> titulosPesquisados(){
		return titulos.findAll();
		
	}
	
	@RequestMapping("")
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors) {		
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		if (errors.hasErrors()) {
			
			return mv;
			
		}
		
		titulos.save(titulo);
		mv.addObject("mensagem", "Título salvo com sucesso!");
		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Optional<Titulo> update = titulos.findById(codigo);
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject(update.get());
		return mv;
		
	}
	
	@RequestMapping("/excluir/{codigo}")
	public ModelAndView excluir(@PathVariable Long codigo) {
		titulos.deleteById(codigo);
		ModelAndView mv = new ModelAndView("redirect:/titulos");
		return mv;
		
	}
	
}
