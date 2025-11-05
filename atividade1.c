#include <stdio.h>
#include <stdlib.h>

int main() {
    // 1. Declarando um array estático de 5 inteiros e preenchendo-o com valores de 1 a 5
    int array_estatico[5] = {1, 2, 3, 4, 5};

    // 2. Alocando dinamicamente um array de 10 inteiros usando malloc
    int *array_dinamico = (int *)malloc(10 * sizeof(int));

    // Verificando se a alocação dinâmica foi bem-sucedida
    if (array_dinamico == NULL) {
        printf("Erro na alocação de memória!\n");
        return 1; // Sai do programa em caso de erro na alocação
    }

    // 3. Preenchendo o array dinâmico com valores de 10 a 19
    for (int i = 0; i < 10; i++) {
        array_dinamico[i] = 10 + i;
    }

    // 4. Imprimindo os valores e os endereços de memória de ambos os arrays
    printf("Array Estático: \n");
    for (int i = 0; i < 5; i++) {
        printf("Valor: %d, Endereço: %p\n", array_estatico[i], (void*)&array_estatico[i]);
    }

    printf("\nArray Dinâmico: \n");
    for (int i = 0; i < 10; i++) {
        printf("Valor: %d, Endereço: %p\n", array_dinamico[i], (void*)&array_dinamico[i]);
    }

    // 5. Calculando e exibindo a diferença entre os endereços (para demonstrar que estão em áreas diferentes da memória)
    printf("\nDiferença entre os endereços de memória:\n");
    printf("Endereço do primeiro elemento do array estático: %p\n", (void*)&array_estatico[0]);
    printf("Endereço do primeiro elemento do array dinâmico: %p\n", (void*)&array_dinamico[0]);

    ptrdiff_t diff = (ptrdiff_t)&array_dinamico[0] - (ptrdiff_t)&array_estatico[0];
    printf("Diferença entre os endereços: %td bytes\n", diff);

    // 6. Liberando a memória alocada dinamicamente
    free(array_dinamico);
    
    return 0;
}
