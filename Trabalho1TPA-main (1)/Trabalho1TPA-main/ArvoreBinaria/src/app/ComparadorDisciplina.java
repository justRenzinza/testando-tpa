package app;

import java.util.Comparator;

public class ComparadorDisciplina implements Comparator<Disciplina> {
    @Override
    public int compare(Disciplina d1, Disciplina d2) {
        return Integer.compare(d1.getCodigo(), d2.getCodigo());
    }
}