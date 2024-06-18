package lib;

import java.util.Comparator;

public class ArvoreAVL <T> extends ArvoreBinaria<T>{

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void adicionar(T novoValor) {
        raiz = adicionarRecursivamente(raiz, novoValor);
    }

    private No<T> adicionarRecursivamente(No<T> noAtual, T novoValor) {
        if (noAtual == null) {
            return new No<>(novoValor);
        }

        int comparacao = comparador.compare(novoValor, noAtual.getValor());

        if (comparacao < 0) {
            noAtual.setFilhoEsquerda(adicionarRecursivamente(noAtual.getFilhoEsquerda(), novoValor));
        } else if (comparacao > 0) {
            noAtual.setFilhoDireita(adicionarRecursivamente(noAtual.getFilhoDireita(), novoValor));
        } else {
            System.out.println("valor duplicado");
            return noAtual;
        }

        //coisa nova
        atualizarAlturaEVerificarBalanceamento(noAtual);

        // coisa nova
        return balancear(noAtual);
    }

    @Override
    public void remover(T valor) {
        raiz = removerRecursivo(raiz, valor);
    }

    private No<T> removerRecursivo(No<T> no, T valor) {
        if (no == null) {
            return no;
        }

        int comparacao = comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            no.setFilhoEsquerda(removerRecursivo(no.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), valor));
        } else {
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita();
            } else if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda();
            }

            no.setValor(encontrarMinimo(no.getFilhoDireita()).getValor());
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), no.getValor()));
        }

        atualizarAlturaEVerificarBalanceamento(no);

        return balancear(no);
    }

    private int altura(No<T> no) {
        if (no == null) {
            return -1;
        }
        return Math.max(altura(no.getFilhoEsquerda()), altura(no.getFilhoDireita())) + 1;
    }

    private int fatorDeEquilibrio(No<T> no) {
        if (no == null) {
            return 0;
        }
        return altura(no.getFilhoEsquerda()) - altura(no.getFilhoDireita());
    }

    private void atualizarAlturaEVerificarBalanceamento(No<T> no) {
        if (no != null) {
            // Primeiro, atualize a altura dos 2 filhos
            atualizarAlturaEVerificarBalanceamento(no.getFilhoEsquerda());
            atualizarAlturaEVerificarBalanceamento(no.getFilhoDireita());

            // atualiza a altura da árvore
            no.setAltura(1 + Math.max(altura(no.getFilhoEsquerda()), altura(no.getFilhoDireita())));
        }
    }

    private No<T> balancear(No<T> no) {
        int fator = fatorDeEquilibrio(no);

        if (fator > 1) {
            if (fatorDeEquilibrio(no.getFilhoEsquerda()) < 0) {
                no.setFilhoEsquerda(rotacaoEsquerda(no.getFilhoEsquerda()));
            }
            return rotacaoDireita(no);
        }
        if (fator < -1) {
            if (fatorDeEquilibrio(no.getFilhoDireita()) > 0) {
                no.setFilhoDireita(rotacaoDireita(no.getFilhoDireita()));
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }
    private No<T> rotacaoDireita(No<T> y) {
        No<T> x = y.getFilhoEsquerda();
        No<T> T2 = x.getFilhoDireita();

        x.setFilhoDireita(y);
        y.setFilhoEsquerda(T2);

        y.setAltura(Math.max(altura(y.getFilhoEsquerda()), altura(y.getFilhoDireita())) + 1);
        x.setAltura(Math.max(altura(x.getFilhoEsquerda()), altura(x.getFilhoDireita())) + 1);

        return x;
    }

    private No<T> rotacaoEsquerda(No<T> x) {
        No<T> y = x.getFilhoDireita();
        No<T> T2 = y.getFilhoEsquerda();

        y.setFilhoEsquerda(x);
        x.setFilhoDireita(T2);

        x.setAltura(Math.max(altura(x.getFilhoEsquerda()), altura(x.getFilhoDireita())) + 1);
        y.setAltura(Math.max(altura(y.getFilhoEsquerda()), altura(y.getFilhoDireita())) + 1);

        return y;
    }

    private No<T> rotacaoEsquerdaDireita(No<T> x){
        x.setFilhoEsquerda(rotacaoEsquerda(x.getFilhoEsquerda()));
        return rotacaoDireita(x);
    }

    private No<T> rotacaoDireitaEsquerda(No<T> x){
        x.setFilhoDireita(rotacaoDireita(x.getFilhoDireita()));
        return rotacaoEsquerda(x);
    }


}