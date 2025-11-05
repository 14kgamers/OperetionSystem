package sistemas_operacionais;

import java.util.Queue;
import java.util.LinkedList;
import java.text.DecimalFormat;

/**
 * Esta classe simula o algoritmo de substituição de página FIFO (First-In, First-Out).
 */
public class fifo {

    /**
     * Executa a simulação do algoritmo FIFO.
     *
     * @param numFrames O número de frames (quadros) disponíveis na memória.
     * @param sequenciaPaginas A sequência de referências de páginas.
     */
    public static void simularFIFO(int numFrames, int[] sequenciaPaginas) {
        
        // Usamos uma Queue (implementada como LinkedList) para gerenciar os frames.
        // A 'cabeça' da fila (removida com poll()) é a página mais antiga.
        // A 'cauda' da fila (adicionada com add()) é a página mais recente.
        Queue<Integer> frames = new LinkedList<>();
        
        int pageFaults = 0;
        int pageHits = 0;

        System.out.println("Iniciando Simulação FIFO com " + numFrames + " frames:\n");
        System.out.println("Sequência de Referências: " + java.util.Arrays.toString(sequenciaPaginas));
        System.out.println("-----------------------------------------------------------------------------------");

        // Itera sobre cada referência de página na sequência
        for (int paginaAtual : sequenciaPaginas) {
            String status = "";
            String detalhesSubstituicao = "";

            // 1. Verificar se a página já está na memória (Page Hit)
            if (frames.contains(paginaAtual)) {
                pageHits++;
                status = "Page Hit";
            } else {
                // 2. Não está na memória (Page Fault)
                pageFaults++;
                status = "Page Fault";

                // 3. Verificar se há espaço livre na memória
                if (frames.size() < numFrames) {
                    frames.add(paginaAtual); // Adiciona a nova página ao final da fila
                } else {
                    // 4. Memória cheia, aplicar substituição FIFO
                    
                    // Remove a página mais antiga (que está no início da fila)
                    int paginaRemovida = frames.poll(); 
                    
                    // Adiciona a nova página (que se torna a mais recente)
                    frames.add(paginaAtual); 
                    
                    detalhesSubstituicao = "(substituiu " + paginaRemovida + ")";
                }
            }

            // Exibir o estado atual após a referência
            // Usamos printf para formatar a saída de forma alinhada
            System.out.printf("Referência: %-3d | Frames: %-20s | %-10s %-20s | Total Faults: %d\n",
                    paginaAtual, frames.toString(), status, detalhesSubstituicao, pageFaults);
        }

        // --- Exibir Estatísticas Finais ---
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("\n--- Estatísticas Finais ---");
        
        int totalReferencias = sequenciaPaginas.length;
        // Evita divisão por zero se a sequência for vazia
        double taxaFaltas = (totalReferencias > 0) ? (double) pageFaults / totalReferencias : 0;

        // Formata a taxa como uma porcentagem (ex: 76,92%)
        DecimalFormat df = new DecimalFormat("#.##%"); 

        System.out.println("Total de Referências de Página: " + totalReferencias);
        System.out.println("Total de Faltas de Página (Page Faults): " + pageFaults);
        System.out.println("Total de Acertos de Página (Page Hits): " + pageHits);
        System.out.println("Taxa de Faltas de Página: " + df.format(taxaFaltas) + 
                           " (" + pageFaults + " / " + totalReferencias + ")");
    }

    /**
     * Método principal para executar o simulador com os dados do exemplo.
     */
    public static void main(String[] args) {
        // Dados de entrada do exemplo fornecido
        int numFrames = 3;
        int[] sequenciaPaginas = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2};

        // Executar a simulação
        simularFIFO(numFrames, sequenciaPaginas);
    }
}