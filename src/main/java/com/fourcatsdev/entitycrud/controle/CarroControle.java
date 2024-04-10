package com.fourcatsdev.entitycrud.controle;

import java.util.List;

import com.fourcatsdev.entitycrud.modelo.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fourcatsdev.entitycrud.excecao.CarroNotFoundException;
import com.fourcatsdev.entitycrud.servico.CarroServico;

import jakarta.validation.Valid;

@Controller
public class CarroControle {
	
	@Autowired
	private CarroServico carroServico;
	
	@GetMapping("/")
    public String listarEstudantes(Model model) {	
		List<Carro> carros = carroServico.buscarTodosEstudantes();
		model.addAttribute("listaEstudantes",carros);
		return "lista-carro";
    }

	@GetMapping("/home")
	public String pagina() {
		return "home"; //
	}
	
	@PostMapping("/buscar")
    public String buscarEstudantes(Model model, @Param("nome") String nome) {	
		if (nome == null) {
			return "redirect:/";
		}
		List<Carro> carros = carroServico.buscarTodosEstudantesPorNome(nome);
		model.addAttribute("listaEstudantes",carros);
		return "lista-carro";
    }
	
	@GetMapping("/novo")
    public String novoEstudante(Model model) {	
		Carro carro = new Carro();
		model.addAttribute("novoEstudante",carro);
        return "novo-carro";
    }
	
	@PostMapping("/gravar")
	public String gravarEstudante(@ModelAttribute("novoEstudante") @Valid Carro carro,
			BindingResult erros,
			RedirectAttributes attributes) {
		if(erros.hasErrors()) {
			return "novo-carro";
		}
		carroServico.criarEstudante(carro);
		attributes.addFlashAttribute("mensagem", "Carro salvo com sucesso!");
        return "redirect:/novo";
    }
	
	@GetMapping("/apagar/{id}")
    public String apagarEstudante(@PathVariable("id") long id, RedirectAttributes attributes) {	
		try {
			carroServico.apagarEstudante(id);
		} catch (CarroNotFoundException e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
		}
        return "redirect:/";
    }
	
	
	@GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") long id, RedirectAttributes attributes,
    		Model model) {	
		try {
			Carro carro = carroServico.buscarEstudantePorId(id);
			model.addAttribute("objetoEstudante", carro);
			return "alterar-carro";
		} catch (CarroNotFoundException e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
		}
        return "redirect:/";
    }
	
	@PostMapping("/editar/{id}")
	public String editarEstudante(@PathVariable("id") long id, 
								@ModelAttribute("objetoEstudante") @Valid Carro carro,
								BindingResult erros) {
		if (erros.hasErrors()) {
			carro.setId(id);
	        return "alterar-carro";
	    }
		carroServico.alterarEstudante(carro);
		return "redirect:/";
	}

}
