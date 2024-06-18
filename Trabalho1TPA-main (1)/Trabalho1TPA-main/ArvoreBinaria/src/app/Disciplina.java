package app;

import java.util.List;
import java.util.ArrayList;

public class Disciplina {
    private int codigo;
    private String titulo;
    private int hora;
    private final List<Disciplina> preRequisitos;

    // Construtor: inicializa a disciplina com código, título, hora e uma lista vazia de pré-requisitos
    public Disciplina(int codigo, String titulo, int hora) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.hora = hora;
        this.preRequisitos = new ArrayList<Disciplina>();
    }

    // Adiciona uma disciplina à lista de pré-requisitos
    public void adicionarPreRequisito(Disciplina disciplina) {
        preRequisitos.add(disciplina);
    }

    // Pega o código da disciplina
    public int getCodigo() {
        return codigo;
    }

    // Pega o título da disciplina
    public String getTitulo() {
        return titulo;
    }

    // Pega a carga horária da disciplina
    public int getHora() {
        return hora;
    }

    // Pega a lista de pré-requisitos
    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }

    // Gera uma string com as infos da disciplina
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código ").append(codigo).append(": ").append(titulo)
                .append(" - ").append(hora).append("h");

        // Se tiver pré-requisitos, adiciona eles na string
        if (!preRequisitos.isEmpty()) {
            sb.append("\nPré-requisitos:");
            for (Disciplina pre : preRequisitos) {
                sb.append("\n  - ").append(pre.getTitulo());
            }
        }
        return sb.toString();
    }
}
