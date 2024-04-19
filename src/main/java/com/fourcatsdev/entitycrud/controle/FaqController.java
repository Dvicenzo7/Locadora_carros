package com.fourcatsdev.entitycrud.controle;

import com.fourcatsdev.entitycrud.excecao.CarroNotFoundException;
import com.fourcatsdev.entitycrud.excecao.FaqNotFoundException;
import com.fourcatsdev.entitycrud.modelo.Carro;
import com.fourcatsdev.entitycrud.modelo.Faq;
import com.fourcatsdev.entitycrud.modelo.Usuario;
import com.fourcatsdev.entitycrud.servico.FaqService;
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

import javax.validation.Valid;
import java.util.List;

@Controller
public class FaqController {

    @Autowired
    FaqService faqService;


    @GetMapping("/novoFaq")
    public String adicionarFaq(Model model) {
        model.addAttribute("faq", new Faq());
        return "/novo-faq";
    }
    @PostMapping("/gravarFaq")
    public String gravarFaq(@ModelAttribute("novaFaq") @Valid Faq faq,
                                  BindingResult erros,
                                  RedirectAttributes attributes) {
        if(erros.hasErrors()) {
            return "novo-faq";
        }
        faqService.criarFaq(faq);
        attributes.addFlashAttribute("mensagem", "Faq salvo com sucesso!");
        return "redirect:/faqs";
    }

    @GetMapping("/faqs")
    public String listarFaqs(Model model) {
        List<Faq> faqs = faqService.buscarFaq();
        model.addAttribute("listaFaqs",faqs);
        return "lista-faq";
    }

//    @PostMapping("/buscarFaq")
//    public String buscarEstudantes(Model model, @Param("titulo") String nome) {
//        if (nome == null) {
//            return "redirect:/";
//        }
//        List<Faq> faqs = faqService.buscarTodosFaqPorNome(nome);
//        model.addAttribute("listaFaqs",faqs);
//        return "lista-faq";
//    }

    @GetMapping("/apagarFaq/{id}")
    public String apagarEstudante(@PathVariable("id") long id, RedirectAttributes attributes) {
        try {
            faqService.apagarFaq(id);
        } catch (FaqNotFoundException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/faqs";
    }

    @GetMapping("/editarFaq/{id}")
    public String editarForm(@PathVariable("id") long id, RedirectAttributes attributes,
                             Model model) {
        try {
            Faq faq = faqService.buscarFaqPorId(id);
            model.addAttribute("objetoFaq", faq);
            return "alterar-faq";
        } catch (FaqNotFoundException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/editarFaq/{id}")
    public String editarEstudante(@PathVariable("id") long id,
                                  @ModelAttribute("objetoFaq") @Valid Faq faq,
                                  BindingResult erros) {
        if (erros.hasErrors()) {
            faq.setId(id);
            return "alterar-Faq";
        }
        faqService.alterarFaq(faq);
        return "redirect:/faqs";
    }

}
