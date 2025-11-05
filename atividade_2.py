import time  # Usado para adicionar pausas entre as etapas da simulação

# ===============================================================
# DEFINIÇÃO DAS PARTIÇÕES DE MEMÓRIA
# ===============================================================
# Cada partição é um dicionário que representa um bloco de memória com:
# - id: identificador da partição
# - tamanho: capacidade total da partição
# - processo: guarda o processo alocado ou None se estiver livre
memoria = [
    {"id": 1, "tamanho": 100, "processo": None},
    {"id": 2, "tamanho": 150, "processo": None},
    {"id": 3, "tamanho": 200, "processo": None},
    {"id": 4, "tamanho": 250, "processo": None},
    {"id": 5, "tamanho": 300, "processo": None}
]


# ===============================================================
# FUNÇÃO: alocar_processo()
# ===============================================================
# Tenta colocar um processo em uma partição livre e suficientemente grande.
# Calcula a fragmentação interna (espaço desperdiçado dentro da partição).
def alocar_processo(nome, tamanho):
    for particao in memoria:
        # Verifica se a partição está livre e se cabe o processo
        if particao["processo"] is None and tamanho <= particao["tamanho"]:
            # Aloca o processo nessa partição
            particao["processo"] = {"nome": nome, "tamanho": tamanho}

            # Calcula a fragmentação interna (espaço não usado)
            fragmentacao = particao["tamanho"] - tamanho

            print(f"✅ Processo {nome} alocado na partição {particao['id']} "
                  f"(fragmentação interna = {fragmentacao} unidades)")
            return  # Sai da função após alocar
    # Se nenhuma partição for adequada, mostra mensagem de erro
    print(f"❌ Não foi possível alocar o processo {nome} ({tamanho} unidades)")


# ===============================================================
# FUNÇÃO: liberar_processo()
# ===============================================================
# Libera uma partição da memória que contenha o processo indicado.
def liberar_processo(nome):
    for particao in memoria:
        # Se encontrar o processo na partição, libera
        if particao["processo"] and particao["processo"]["nome"] == nome:
            particao["processo"] = None
            print(f"🗑️ Processo {nome} liberado da partição {particao['id']}")
            return
    # Caso o processo não esteja na memória
    print(f"⚠️ Processo {nome} não encontrado na memória.")


# ===============================================================
# FUNÇÃO: exibir_memoria()
# ===============================================================
# Exibe o estado atual da memória mostrando:
# - quais partições estão ocupadas e por quais processos
# - o tamanho do processo e a fragmentação interna
def exibir_memoria():
    print("\n📊 Estado atual da memória:")
    for particao in memoria:
        if particao["processo"]:
            nome = particao["processo"]["nome"]
            tam_proc = particao["processo"]["tamanho"]
            frag = particao["tamanho"] - tam_proc
            print(f" - Partição {particao['id']} ({particao['tamanho']}): "
                  f"{nome} ({tam_proc} unidades, fragmentação {frag})")
        else:
            print(f" - Partição {particao['id']} ({particao['tamanho']}): livre")
    print()


# ===============================================================
# FUNÇÃO: calcular_fragmentacao_total()
# ===============================================================
# Soma toda a fragmentação interna (espaço desperdiçado dentro das partições).
def calcular_fragmentacao_total():
    total = 0
    for particao in memoria:
        if particao["processo"]:
            total += particao["tamanho"] - particao["processo"]["tamanho"]
    return total


# ===============================================================
# SIMULAÇÃO DE GERENCIAMENTO DE MEMÓRIA
# ===============================================================
print("🚀 Simulação de Gerenciador de Memória\n")

# Função auxiliar para criar pausas entre as ações (para visualizar melhor)
def pausa():
    time.sleep(1.5)  # Espera 1.5 segundos entre cada etapa


# ---------------------------------------------------------------
# ETAPA 1: Alocar processo P1 (ocupa 90 unidades)
# ---------------------------------------------------------------
alocar_processo("P1", 90)
pausa()

# ---------------------------------------------------------------
# ETAPA 2: Alocar processo P2 (ocupa 140 unidades)
# ---------------------------------------------------------------
alocar_processo("P2", 140)
pausa()

# ---------------------------------------------------------------
# ETAPA 3: Alocar processo P3 (ocupa 180 unidades)
# ---------------------------------------------------------------
alocar_processo("P3", 180)
pausa()

# ---------------------------------------------------------------
# ETAPA 4: Liberar o processo P2 da memória
# ---------------------------------------------------------------
liberar_processo("P2")
pausa()

# ---------------------------------------------------------------
# ETAPA 5: Tentar alocar processo P4 (100 unidades)
# Deve ocupar a partição onde P2 estava
# ---------------------------------------------------------------
alocar_processo("P4", 100)
pausa()

# ---------------------------------------------------------------
# ETAPA 6: Tentar alocar processo P5 (350 unidades)
# Deve falhar pois não há partição grande o suficiente
# ---------------------------------------------------------------
alocar_processo("P5", 350)
pausa()

# ---------------------------------------------------------------
# ETAPA 7: Exibir o estado final da memória
# ---------------------------------------------------------------
exibir_memoria()
pausa()

# ---------------------------------------------------------------
# ETAPA 8: Calcular e mostrar a fragmentação interna total
# ---------------------------------------------------------------
print(f"💾 Fragmentação interna total: {calcular_fragmentacao_total()} unidades")
