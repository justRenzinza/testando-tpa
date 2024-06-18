package lib;

import java.util.Comparator;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 *
 * @author Erick Loyola
 * @author Renzo Avance
 * @author Bruno Mian
 * @author Rodolfo Luiz
 * @author Thalison Vinicius
 */
public class ArvoreBinaria<T> implements IArvoreBinaria<T> {

    protected No<T> raiz = null;
    protected Comparator<T> comparador;

    protected No<T> atual = null;
    private Stack<No<T>> pilha = new Stack<>();

    // private ArrayList<No<T>> pilhaNavegadora = null;
    // private boolean primeiraChamada = true;

    public ArvoreBinaria(Comparator<T> comp) {

        comparador = comp;
    }

    @Override
    public void adicionar(T novoValor) {
        if (raiz == null) {
            // Se a árvore estiver vazia, inicializa a pilha navegadora para métodos auxiliares da árvore.
            // pilhaNavegadora = new ArrayList<>();
            raiz = new No<>(novoValor); // Define o novo valor como a raiz da árvore.
            return;
        }

        atual = raiz;

        while (true) {
            // Compara o novo valor com o valor do nó atual.
            int comparacao = comparador.compare(novoValor, atual.getValor());

            if (comparacao < 0) {
                if (atual.getFilhoEsquerda() == null) {
                    // Se não houver filho esquerdo do nó atual, adiciona o novo valor como filho esquerdo.
                    atual.setFilhoEsquerda(new No<>(novoValor));
                    return; // Encerra o método após adicionar o novo valor.
                }
                atual = atual.getFilhoEsquerda(); // Avança para o filho esquerdo.
            } else if (comparacao > 0) {
                if (atual.getFilhoDireita() == null) {
                    // Se não houver filho direito do nó atual, adiciona o novo valor como filho direito.
                    atual.setFilhoDireita(new No<>(novoValor));
                    return; // Encerra o método após adicionar o novo valor.
                }
                atual = atual.getFilhoDireita(); // Avança para o filho direito.
            } else {
                // Se o comparador retornar 0, significa que o valor já existe na árvore, então não faz nada.
                return;
            }
        }
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivo(raiz, valor);
    }

    private T pesquisarRecursivo(No<T> no, T valor) {
        if (no == null) {
            return null;
        }

        int comparacao = comparador.compare(valor, no.getValor());
        if (comparacao == 0) {
            return no.getValor();
        } else if (comparacao < 0) {
            return pesquisarRecursivo(no.getFilhoEsquerda(), valor);
        } else {
            return pesquisarRecursivo(no.getFilhoDireita(), valor);
        }
    }

    @Override
    public T pesquisar(T valor, Comparator comparador) {
        return pesquisarRecursivo(raiz, valor, comparador);
    }

    private T pesquisarRecursivo(No<T> no, T valor, Comparator comparador) {
        if (no == null) {
            return null;
        }

        int comparacao = comparador.compare(valor, no.getValor());
        if (comparacao == 0) {
            return no.getValor();
        } else {

            T encontradoEsquerda = pesquisarRecursivo(no.getFilhoEsquerda(), valor, comparador);
            T encontradoDireita = pesquisarRecursivo(no.getFilhoDireita(), valor, comparador);
            if (encontradoEsquerda != null) {
                return encontradoEsquerda;
            } else {
                return encontradoDireita;
            }
        }
    }

    @Override
    public T remover(T valor) {
        this.raiz = removerRecursivo(this.raiz, valor);
        return valor;
    }

    public No<T> removerRecursivo(No <T> no, T valor){
        // caso não tenha nenhum nó na arvore, importante fazer essa verificação
        if (no == null){
            return no;
        }

        if (this.comparador.compare(valor, no.getValor()) < 0) {
            no.setFilhoEsquerda(removerRecursivo(no.getFilhoEsquerda(), valor));
        } else if (this.comparador.compare(valor, no.getValor()) > 0) {
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), valor));
        } else {
            // nesse caso verificaremos se o nó não tem filhos, ou se ele tem apenas 1 filho
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita();
            } else if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda();
            }

            // agora nesse caso verificaremos se o nó tem 2 filhos (se chegou até aqui, é porque tem)
            no.setValor(acharMinimo(no.getFilhoDireita()).getValor());
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), no.getValor()));
        }

        return no;
    }



    public No<T> acharMinimo(No<T> no) {
        // lógica pra encontrar o minímo
        No<T> atual = no;
        while (atual.getFilhoEsquerda() != null) {
            atual = atual.getFilhoEsquerda();
        }
        return atual;
    }

    @Override
    public int altura() {
        if (raiz == null) {
            return -1; // Retorna -1 para árvore vazia
        }

        Stack<No<T>> pilha = new Stack<>();
        Stack<Integer> alturas = new Stack<>();
        int alturaMaxima = -1;
        int alturaAtual = 0;
        No<T> noAtual = raiz;

        while (noAtual != null || !pilha.isEmpty()) {
            if (noAtual != null) {
                pilha.push(noAtual);
                alturas.push(++alturaAtual);
                noAtual = noAtual.getFilhoEsquerda();
            } else {
                noAtual = pilha.pop();
                alturaAtual = alturas.pop();
                if (alturaAtual > alturaMaxima) {
                    alturaMaxima = alturaAtual;
                }
                noAtual = noAtual.getFilhoDireita();
            }
        }
        return alturaMaxima;
    }

    public int quantidadeNos() {
        return contarNos(raiz);
    }

    private int contarNos(No<T> no) {
        if (no == null) {
            return 0; // Nó nulo
        }
        return 1 + contarNos(no.getFilhoEsquerda()) + contarNos(no.getFilhoDireita());
    }

    @Override
    public String caminharEmNivel() {
        StringBuilder resultado = new StringBuilder();
        if (raiz == null) {
            return resultado.toString(); // Árvore vazia
        }

        Queue<No<T>> fila = new ArrayDeque<>();
        fila.offer(raiz);

        while (fila.size() > 0) {
            No<T> noAtual = fila.poll();
            resultado.append(noAtual.getValor()).append(" ");

            if (noAtual.getFilhoEsquerda() != null) {
                fila.offer(noAtual.getFilhoEsquerda());
            }
            if (noAtual.getFilhoDireita() != null) {
                fila.offer(noAtual.getFilhoDireita());
            }
        }

        return resultado.toString().trim();
    }

    @Override
    public String caminharEmOrdem() {
        if (raiz == null) {
            return "[]"; // Retorna uma string vazia se a árvore estiver vazia.
        }

        StringBuilder resultado = new StringBuilder("[");
        caminharEmOrdemRecursivo(raiz, resultado);
        resultado.setLength(resultado.length() - 2);
        resultado.append("]");

        return resultado.toString();
    }

    private void caminharEmOrdemRecursivo(No<T> no, StringBuilder resultado) {
        if (no == null) {
            return;
        }
        caminharEmOrdemRecursivo(no.getFilhoEsquerda(), resultado);

        resultado.append(no.getValor().toString()).append(", \n");

        caminharEmOrdemRecursivo(no.getFilhoDireita(), resultado);
    }

    public void emOrdem(NoVisitor<T> visitor) {
        emOrdemRecursivo(raiz, visitor);
    }

    private void emOrdemRecursivo(No<T> no, NoVisitor<T> visitor) {
        if (no == null) {
            return;
        }
        emOrdemRecursivo(no.getFilhoEsquerda(), visitor);
        visitor.visit(no.getValor());
        emOrdemRecursivo(no.getFilhoDireita(), visitor);
    }

    public No<T> getRaiz() {
        return raiz;
    }

}
