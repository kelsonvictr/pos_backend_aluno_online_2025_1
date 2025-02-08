package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodosAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPeloId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno aluno) {
       //PRIMEIRO PASSO: VER SE O ALUNO EXISTE NO BD
       Optional<Aluno> alunoDoBancoDeDados =
               buscarAlunoPeloId(id);

       // E SE NÃO EXISTIR ESSE ALUNO
        if (alunoDoBancoDeDados.isEmpty()) {
            throw new
                    ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Aluno não encontrado no banco de dados"
                    );
        }

        // SE CHEGAR AQUI, SIGNIFICA QUE EXISTE ALUNO COM ESSE ID!
        // VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Aluno alunoEditado = alunoDoBancoDeDados.get();

        // COM ESSE ALUNO EDITADO DE CIMA, FAÇO
        // OS SETS NECESSÁRIOS PARA ATUALIZAR OS ATRIBUTOS DELE
        alunoEditado.setNome(aluno.getNome());
        alunoEditado.setEmail(aluno.getEmail());
        alunoEditado.setCpf(aluno.getCpf());

        // COM O ALUNO TOTALMENTE EDITADO ACIMA
        // EU DEVOLVO ELE EDITADO/ATUALIZADO PARA O BANCO DE DADOS
        alunoRepository.save(alunoEditado);
    }
}
