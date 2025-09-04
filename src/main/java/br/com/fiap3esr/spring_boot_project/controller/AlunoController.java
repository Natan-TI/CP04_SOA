package br.com.fiap3esr.spring_boot_project.controller;

import br.com.fiap3esr.spring_boot_project.aluno.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroAluno dados) {
        repository.save(new Aluno(dados));
    }

    @GetMapping
    public Page<DadosListagemAluno> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return repository.findAll(pageable).map(DadosListagemAluno::new);
    }
}
