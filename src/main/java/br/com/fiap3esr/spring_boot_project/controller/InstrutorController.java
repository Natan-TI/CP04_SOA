package br.com.fiap3esr.spring_boot_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap3esr.spring_boot_project.instrutor.DadosCadastroInstrutor;
import br.com.fiap3esr.spring_boot_project.instrutor.DadosListagemInstrutor;
import br.com.fiap3esr.spring_boot_project.instrutor.Instrutor;
import br.com.fiap3esr.spring_boot_project.instrutor.InstrutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarInstrutor(@RequestBody @Valid DadosCadastroInstrutor dados) {
        //System.out.println(dados);
        repository.save(new Instrutor(dados));
    }

    /*@GetMapping
    public List<Instrutor> listarInstrutores() {
        return repository.findAll();
    }*/

    @GetMapping
    public Page<DadosListagemInstrutor> listar(
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable
    ) {
        return repository.findAll(pageable).map(DadosListagemInstrutor::new);
    }
}