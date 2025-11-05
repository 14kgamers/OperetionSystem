# Trabalho Prático - Gerenciamento de Memória em Sistemas Operacionais

Este repositório contém a implementação da parte prática do trabalho de Gerenciamento de Memória em Sistemas Operacionais, desenvolvido para a disciplina de **Sistemas Operacionais** no curso de **Engenharia de Software**.

## Questões Práticas

A seguir estão as questões práticas e seus respectivos detalhes:

### Questão 1: Alocação Estática vs. Dinâmica

#### Objetivo:
Implementar um programa em **C** que:
1. Declare um array estático de 5 inteiros e preencha-o com valores de 1 a 5.
2. Aloque dinamicamente um array de 10 inteiros utilizando `malloc`.
3. Preencha o array dinâmico com valores de 10 a 19.
4. Imprima os endereços de memória de ambos os arrays.
5. Calcule e exiba a diferença entre os endereços de memória.
6. Libere a memória alocada dinamicamente.

#### Código:
- **Linguagem**: C
- **Funções principais**:
    - `malloc()` para alocação dinâmica.
    - Impressão dos endereços de memória.
    - Cálculo da diferença entre endereços de memória estático e dinâmico.

---

### Questão 2: Simulação de Fragmentação de Memória

#### Objetivo:
Implementar um programa em **Python** para simular um gerenciador de memória com partições fixas.

1. Criar uma memória de 1000 unidades dividida em 5 partições fixas.
2. Implementar uma função `alocar_processo(nome, tamanho)` que utiliza o algoritmo **First-Fit** para alocar processos nas partições.
3. Calcular a fragmentação interna após cada alocação.
4. Implementar uma função `liberar_processo(nome)` para liberar a partição ocupada por um processo.
5. Testar o programa com a sequência de alocações e liberações de processos.

#### Código:
- **Linguagem**: Python
- **Funções principais**:
    - Função `alocar_processo()` que aloca o processo nas partições.
    - Função `liberar_processo()` para liberar uma partição.
    - Exibição da fragmentação interna.

---

### Questão 3: Algoritmo de Substituição de Página - FIFO

#### Objetivo:
Implementar um programa em **Java** que simula o algoritmo **FIFO** (First-In, First-Out) de substituição de página.

1. Receber como entrada o número de frames e uma sequência de referências a páginas.
2. Simular o algoritmo FIFO, carregando páginas nos frames e substituindo a página mais antiga quando necessário.
3. Contar o número de faltas de página e calcular a taxa de faltas.

#### Código:
- **Linguagem**: Java
- **Estrutura de dados**: **Queue** para gerenciar os frames de memória.
- **Funções principais**:
    - Acompanhamento de faltas de página.
    - Exibição do estado dos frames após cada referência.

---

### Questão 4: Garbage Collection em Python

#### Objetivo:
Implementar um programa em **Python** para demonstrar o funcionamento do coletor de lixo.

1. Criar uma classe `Objeto` com atributos para nome e dados, simulando o uso de memória.
2. Criar instâncias da classe e demonstrar destruição automática de objetos.
3. Demonstrar referências circulares e o comportamento do coletor de lixo.
4. Usar o módulo `gc` para forçar a coleta de lixo e exibir estatísticas.

#### Código:
- **Linguagem**: Python
- **Funções principais**:
    - Uso do coletor de lixo geracional.
    - Exibição das estatísticas do `gc`.

---

### Questão 5: Comparação de Desempenho de Alocação

#### Objetivo:
Comparar o desempenho da alocação de memória na **pilha** e no **heap**.

1. Medir o tempo de execução para criar e destruir 1.000.000 de variáveis locais (pilha) e alocar/desalocar 1.000.000 de objetos dinamicamente (heap).
2. Comparar os tempos de execução e calcular a diferença percentual de desempenho.

#### Código:
- **Linguagem**: C, Python ou Java
- **Funções principais**:
    - Medição do tempo de execução utilizando `time.time()` ou `System.nanoTime()`.

---

### Questão Bônus: Implementação de Algoritmo LRU

#### Objetivo:
Implementar o algoritmo de substituição de página **LRU** (Least Recently Used) e comparar com o algoritmo **FIFO**.

1. Receber como entrada o número de frames e uma sequência de referências.
2. Implementar o algoritmo LRU, mantendo registro do tempo de uso de cada página.
3. Comparar os resultados do LRU com o FIFO, exibindo o número de faltas de página e a taxa de faltas.

#### Código:
- **Linguagem**: Python, C ou Java
- **Estrutura de dados**: **Dicionário/mapa com timestamps** para acompanhar os tempos de uso.
- **Funções principais**:
    - Acompanhamento das faltas de página e substituição das páginas.

---

## Como Executar os Códigos

### Questão 1 (C):
1. Compile o código utilizando:
   ```bash
   gcc alocacao.c -o alocacao
