package sistemas_operacionais;

import java.util.ArrayList;
import java.util.List;

/**
 * Este programa compara o tempo de execução da alocação de memória
 * na Pilha (Stack) vs. no Heap.
 *
 * Para Java:
 * - "Alocação na Pilha": Simulada chamando uma função milhões de vezes.
 * Cada chamada de função cria um novo "stack frame" com variáveis locais,
 * que é a operação de alocação/desalocação na pilha.
 * - "Alocação no Heap": Simulada criando milhões de novos objetos
 * usando a palavra-chave 'new'.
 */
public class alocacao_stackvsHeap {

    // Constantes da simulação
    private static final int NUM_ITERACOES = 1_000_000; // 1 milhão
    private static final int NUM_TESTES = 10;           // Para calcular a média

    /**
     * 'Sink' (dissipador) volátil.
     * Usado para garantir que o compilador JIT (Just-In-Time) do Java
     * não otimize e remova nossas alocações, que é um risco comum
     * em micro-benchmarks. Ao atribuir o resultado a um campo volátil,
     * forçamos a execução do código.
     */
    private static volatile Object sink;

    /**
     * Ponto de entrada principal.
     */
    public static void main(String[] args) {
        System.out.println("Iniciando Comparação de Alocação: Stack vs. Heap");
        System.out.println("Iterações por teste: " + String.format("%,d", NUM_ITERACOES));
        System.out.println("Número de testes (para média): " + NUM_TESTES);
        System.out.println("---------------------------------------------------------");

        // --- Fase de Aquecimento (Warm-up) ---
        // Essencial em Java para permitir que o JIT compile o código
        // antes de começarmos a medir de verdade.
        System.out.println("Executando fase de aquecimento (JIT compilation)...");
        testarPilha();
        testarHeap();
        System.out.println("Aquecimento concluído.");
        System.out.println("---------------------------------------------------------");

        // --- Testes Reais ---
        List<Long> temposPilha = new ArrayList<>();
        List<Long> temposHeap = new ArrayList<>();

        System.out.println("Executando testes...");

        for (int i = 0; i < NUM_TESTES; i++) {
            temposPilha.add(testarPilha());
            temposHeap.add(testarHeap());
            System.out.print("."); // Indicador de progresso
        }
        System.out.println("\nTestes concluídos.");
        System.out.println("---------------------------------------------------------");

        // --- Cálculo e Exibição dos Resultados ---

        // Usamos double para manter a precisão dos nanossegundos
        double mediaPilhaNs = calcularMedia(temposPilha);
        double mediaHeapNs = calcularMedia(temposHeap);

        // Converter para milissegundos para melhor legibilidade
        double mediaPilhaMs = mediaPilhaNs / 1_000_000.0;
        double mediaHeapMs = mediaHeapNs / 1_000_000.0;

        // Cálculo da diferença
        double diferenca = mediaHeapMs - mediaPilhaMs;
        double percentual = (diferenca / mediaPilhaMs) * 100.0;

        System.out.println("\n--- Resultados (Média de " + NUM_TESTES + " execuções) ---");
        System.out.printf("Tempo Médio de Alocação na PILHA: %.4f ms\n", mediaPilhaMs);
        System.out.printf("Tempo Médio de Alocação no HEAP:  %.4f ms\n", mediaHeapMs);
        System.out.println();
        System.out.printf("Diferença: O Heap foi %.4f ms mais lento.\n", diferenca);
        System.out.printf("Desempenho: A alocação no Heap foi %.2f%% mais lenta que na Pilha.\n", percentual);

        System.out.println("---------------------------------------------------------");

        // --- Explicação dos Resultados (Conforme solicitado) ---
        System.out.println("\n--- Explicação dos Resultados ---");
        System.out.println(
            "1. Por que a Pilha (Stack) é tão rápida?\n" +
            "   O teste da pilha (`testarPilha`) chama `funcaoPilha()` 1 milhão de vezes.\n" +
            "   Cada chamada é uma operação de 'push' (alocação) e 'pop' (desalocação) no stack.\n" +
            "   Isso é extremamente rápido porque é apenas a movimentação de um ponteiro na CPU.\n" +
            "   A 'variavelLocal' é criada e destruída quase instantaneamente.\n"
        );
        System.out.println(
            "2. Por que o Heap é mais lento?\n" +
            "   O teste do heap (`testarHeap`) executa `new Object()` 1 milhão de vezes.\n" +
            "   A operação 'new' instrui a JVM a encontrar espaço na memória heap, o que\n" +
            "   exige um algoritmo de gerenciamento de memória (para encontrar um bloco livre).\n" +
            "   Isso é muito mais complexo do que mover o ponteiro da pilha.\n"
        );
        System.out.println(
            "3. Variações e o 'Custo Oculto' (Garbage Collector - GC):\n" +
            "   O tempo do Heap pode variar MUITO entre as execuções. Por quê?\n" +
            "   Ao criar 1.000.000 de objetos, o 'Garbage Collector' (GC) do Java é\n" +
            "   provavelmente forçado a rodar durante o teste para limpar objetos antigos\n" +
            "   e liberar espaço (as 'pausas do GC'). Isso adiciona um custo significativo\n" +
            "   e variável ao tempo de alocação no heap, que não existe na pilha.\n"
        );
    }

    /**
     * Executa o teste de alocação na Pilha (Stack).
     * Simula alocação/desalocação na pilha chamando uma função repetidamente.
     * @return Tempo gasto em nanossegundos.
     */
    private static long testarPilha() {
        long inicio = System.nanoTime();
        for (int i = 0; i < NUM_ITERACOES; i++) {
            funcaoPilha();
        }
        long fim = System.nanoTime();
        return (fim - inicio);
    }

    /**
     * Função auxiliar que cria variáveis locais na pilha.
     * A simples chamada/retorno desta função é o teste.
     */
    private static void funcaoPilha() {
        long variavelLocal = 10; // Alocado na pilha
        sink = variavelLocal; // Usa o 'sink' para evitar otimização
    }

    /**
     * Executa o teste de alocação no Heap.
     * Simula alocação no heap criando novos objetos.
     * @return Tempo gasto em nanossegundos.
     */
    private static long testarHeap() {
        long inicio = System.nanoTime();
        for (int i = 0; i < NUM_ITERACOES; i++) {
            sink = new Object(); // Alocado no heap
        }
        long fim = System.nanoTime();
        return (fim - inicio);
    }

    /**
     * Calcula a média de uma lista de tempos (em Long).
     * @param tempos Lista de tempos em nanossegundos.
     * @return A média.
     */
    private static double calcularMedia(List<Long> tempos) {
        // Remove os 25% maiores e 25% menores (média aparada)
        // para reduzir o impacto de outliers (ex: pausas do GC).
        int tamanho = tempos.size();
        int trim = (int) (tamanho * 0.25);
        
        return tempos.stream()
                .sorted() // Ordena os tempos
                .skip(trim) // Pula os 25% menores
                .limit(tamanho - 2 * trim) // Pega o 50% central
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0); // Retorna 0 se a lista estiver vazia
    }
}