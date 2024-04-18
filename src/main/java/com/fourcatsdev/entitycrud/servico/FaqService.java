package com.fourcatsdev.entitycrud.servico;

import com.fourcatsdev.entitycrud.excecao.CarroNotFoundException;
import com.fourcatsdev.entitycrud.excecao.FaqNotFoundException;
import com.fourcatsdev.entitycrud.modelo.Carro;
import com.fourcatsdev.entitycrud.modelo.Faq;
import com.fourcatsdev.entitycrud.repositorio.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaqService {

    @Autowired
    FaqRepository faqRepository;

    public Faq criarFaq(Faq faq){
        return faqRepository.save(faq);
    }

    public List<Faq> buscarFaq() {
        return faqRepository.findAll();
    }


//    public List<Faq> buscarTodosFaqPorNome(String nome) {
//        return faqRepository.findByNomeContainingIgnoreCase(nome);
//    }

    public void apagarFaq(Long id) throws FaqNotFoundException {
        Faq faq = buscarFaqPorId(id);
        faqRepository.delete(faq);
    }

    public Faq buscarFaqPorId(Long id) throws FaqNotFoundException {
        Optional<Faq> opt = faqRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new FaqNotFoundException("Faq com id : " + id + " não existe");
        }
    }
    public Faq alterarFaq(Faq faq) {
        return faqRepository.save(faq);
    }


}
