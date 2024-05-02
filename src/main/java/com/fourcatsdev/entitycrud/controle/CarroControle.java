package com.fourcatsdev.entitycrud.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fourcatsdev.entitycrud.Enumeration.TipoCambio;
import com.fourcatsdev.entitycrud.Enumeration.TipoCarro;
import com.fourcatsdev.entitycrud.Enumeration.TipoDirecao;
import com.fourcatsdev.entitycrud.modelo.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fourcatsdev.entitycrud.excecao.CarroNotFoundException;
import com.fourcatsdev.entitycrud.servico.CarroServico;

import javax.validation.Valid;


@Controller
public class CarroControle {

	private static String caminhoImagens = "C:\\Users\\daniel.vicenzo\\Downloads\\imagens/";
	@Autowired
	private CarroServico carroServico;
	
	@GetMapping("/carros")
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
		model.addAttribute("tipoCarro", TipoCarro.values());
		model.addAttribute("tipoCambio", TipoCambio.values());
		model.addAttribute("tipoDirecao", TipoDirecao.values());

        return "novo-carro";
    }
	
	@PostMapping("/gravar")
	public String gravarEstudante(@ModelAttribute("novoEstudante") @Valid Carro carro,
								  BindingResult erros,
								  RedirectAttributes attributes, @RequestParam("file")MultipartFile arquivo) {
		if(erros.hasErrors()) {
			return "novo-carro";
		}

		try{
			if (!arquivo.isEmpty()){
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens+String.valueOf(carro.getId())+arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				carro.setCaminhoImagem(String.valueOf(carro.getId())+arquivo.getOriginalFilename());
			}
		}catch (IOException e){
			e.printStackTrace();
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
        return "lista-carro";
    }

	@GetMapping("/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens+imagem);

		System.out.println(caminhoImagens);
		if (imagem != null || imagem.trim().length()>0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
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
