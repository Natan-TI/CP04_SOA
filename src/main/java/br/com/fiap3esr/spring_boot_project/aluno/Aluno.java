package br.com.fiap3esr.spring_boot_project.aluno;

import br.com.fiap3esr.spring_boot_project.endereco.Endereco;
import br.com.fiap3esr.spring_boot_project.endereco.DadosEndereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "alunos")
@Getter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String nome;

    @Email @NotBlank
    private String email;

    @NotBlank private String telefone;

    @NotBlank private String cpf;

    @Embedded
    @NotNull @Valid
    private Endereco endereco;

    public Aluno(DadosCadastroAluno d) {
        this.nome = d.nome();
        this.email = d.email();
        this.telefone = d.telefone();
        this.cpf = d.cpf().replaceAll("\\D", "");
        this.endereco = new Endereco(d.endereco());
    }
}
