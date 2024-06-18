package app;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victoriocarvalho
 *
 * Essa é a classe Aluno que será utilizada como tipo do conteúdo das árvores nos
 * programas de teste para redigir o relatório.
 */

public class Aluno {
    private int matricula;
    private String nome;
    private List<Disciplina> discCursadas;

    public Aluno(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.discCursadas = new ArrayList<Disciplina>();
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Disciplina> getDiscCursadas() {
        return discCursadas;
    }

    public void addDiscCursada(Disciplina disciplina) {
        discCursadas.add(disciplina);
    }

    public Disciplina[] getDisciplinasCursadas() {
        return discCursadas.toArray(new Disciplina[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrícula: ").append(matricula).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Disciplinas Cursadas:\n");
        for (Disciplina disc : discCursadas) {
            sb.append("  - ").append(disc.toString()).append("\n");
        }
        return sb.toString();
    }
}
