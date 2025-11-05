import gc
import sys
from typing import Optional

# ============================================================
# 1. DEFINIÇÃO DA CLASSE OBJETO
# ============================================================

class Objeto:
    def __init__(self, nome):
        self.nome = nome
        # Alocamos um array grande para simular uso de memória
        self.dados = [0] * 10**6
        print(f"Objeto '{self.nome}' criado. (id={id(self)})")

    def __del__(self):
        # __del__ é chamado quando o objeto é destruído
        print(f"Objeto '{self.nome}' destruído. (id={id(self)})")


# ============================================================
# 2. DEMONSTRAÇÃO DE CENÁRIOS
# ============================================================

# Ativamos o garbage collector (caso esteja desativado)
gc.enable()

# Mostramos estatísticas iniciais
print("\n=== ESTATÍSTICAS INICIAIS DO GC ===")
print(gc.get_stats())


# ============================================================
# CENÁRIO 1 - COLETA POR CONTAGEM DE REFERÊNCIAS
# ============================================================

print("\n=== CENÁRIO 1: DESTRUIÇÃO AUTOMÁTICA POR CONTAGEM DE REFERÊNCIAS ===")

obj1 = Objeto("obj1")

# Mostramos quantas referências o Python tem a este objeto
print(f"Referências para obj1: {sys.getrefcount(obj1)}")

# Removemos a referência principal
obj1 = None

# Nesse momento, como não há mais referências, o objeto é coletado automaticamente
print("obj1 = None -> o objeto deve ser destruído imediatamente.")


# ============================================================
# CENÁRIO 2 - REFERÊNCIAS CIRCULARES
# ============================================================

print("\n=== CENÁRIO 2: REFERÊNCIAS CIRCULARES ===")
class Nodo:
    ref: Optional["Nodo"] = None

    def __init__(self, nome):
        self.nome = nome
        print(f"Nodo '{self.nome}' criado. (id={id(self)})")

    def __del__(self):
        print(f"Nodo '{self.nome}' destruído. (id={id(self)})")
        print(f"Nodo '{self.nome}' destruído. (id={id(self)})")


# Criamos dois objetos que se referenciam mutuamente (ciclo)
a = Nodo("A")
b = Nodo("B")
a.ref = b
b.ref = a

# Mesmo removendo as referências externas, o ciclo impede destruição imediata
a = None
b = None

print("Referências circulares criadas e removidas (a, b = None).")
print("O GC precisará intervir para destruir esses objetos.")

# Forçamos a coleta de lixo manualmente
print("\nExecutando gc.collect()...")
coletados = gc.collect()
print(f"Objetos coletados: {coletados}")


# ============================================================
# CENÁRIO 3 - COLETA GERACIONAL E ESTATÍSTICAS
# ============================================================

print("\n=== CENÁRIO 3: COLETA GERACIONAL ===")

# O coletor do Python usa três gerações (0, 1, 2)
# Objetos sobrevivem a coletas e vão sendo "promovidos" para gerações mais altas

objetos = [Objeto(f"temp{i}") for i in range(3)]
print(f"Número de objetos temporários criados: {len(objetos)}")

# Mostramos estatísticas antes da coleta
print("\nEstatísticas antes da coleta:")
print(gc.get_stats())

# Removemos as referências
objetos = None

# Forçamos coletas específicas
print("\nForçando coleta de cada geração:")
for i in range(3):
    coletados = gc.collect(i)
    print(f"Geração {i}: {coletados} objetos coletados")

# Estatísticas após a coleta
print("\nEstatísticas após a coleta:")
print(gc.get_stats())


