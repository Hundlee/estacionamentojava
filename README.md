# Sistema de Estacionamento em Java

Sistema completo de gerenciamento de estacionamento desenvolvido em Java, utilizando Programação Orientada a Objetos (POO), ArrayList e LocalDateTime.

## Características

- ✅ Menu interativo no console
- ✅ Registro de entrada e saída de veículos
- ✅ Cálculo automático de valores diferenciados por tipo de veículo
- ✅ Controle de vagas disponíveis
- ✅ Fila de espera quando o estacionamento está lotado
- ✅ Relatórios de veículos estacionados e faturamento
- ✅ Consulta de total arrecadado por dia
- ✅ Validações e tratamento de erros

## Estrutura do Projeto

### Classes

1. **Veiculo.java**
   - Representa um veículo no estacionamento
   - Atributos: placa, tipo, dataEntrada, dataSaida
   - Métodos para calcular tempo de permanência e valor
   - Cálculo de valor diferenciado: Carro (R$12,00 1ª hora + R$8,00 adicional) e Moto (R$8,00 1ª hora + R$5,00 adicional)

2. **Estacionamento.java**
   - Gerencia o estacionamento
   - Utiliza ArrayList para armazenar veículos
   - Controla capacidade e vagas disponíveis
   - Gerencia fila de espera quando lotado
   - Gera relatórios de faturamento
   - Calcula total arrecadado por dia

3. **Principal.java**
   - Classe principal com menu interativo
   - Gerencia todas as operações do sistema

## Como Compilar e Executar

### Compilação

```bash
javac *.java
```

### Execução

```bash
java Principal
```

## Funcionalidades do Menu

1. **Registrar entrada de veículo**
   - Registra placa e tipo do veículo (Carro ou Moto)
   - Seleção de tipo através de menu numerado (1 - Carro, 2 - Moto)
   - Opção de informar hora de entrada manualmente ou usar hora atual
   - Verifica disponibilidade de vagas
   - Se estiver lotado, adiciona à fila de espera
   - Valida se o veículo já está estacionado ou na fila

2. **Registrar saída de veículo**
   - Mostra lista de veículos estacionados
   - Calcula tempo de permanência
   - Calcula valor a pagar conforme tipo de veículo
   - Libera a vaga automaticamente
   - Se houver fila de espera, o primeiro veículo entra automaticamente

3. **Pesquisar veículo por placa**
   - Mostra lista de veículos estacionados
   - Busca veículo pela placa
   - Exibe informações detalhadas: placa, tipo, hora de entrada, tempo de permanência
   - Mostra valor por hora (tarifa aplicada)
   - Mostra valor estimado total

4. **Mostrar vagas disponíveis**
   - Exibe situação atual do estacionamento
   - Mostra capacidade total, vagas ocupadas e disponíveis
   - Mostra percentual de ocupação

5. **Mostrar todos os veículos presentes**
   - Lista todos os veículos atualmente estacionados
   - Mostra placa e tempo estacionado de cada veículo

6. **Relatório de faturamento**
   - Mostra o total arrecadado desde o início

7. **Total arrecadado por dia**
   - Permite consultar o total arrecadado em uma data específica
   - Formato de data: dd/MM/yyyy

8. **Mostrar fila de espera**
   - Lista todos os veículos aguardando vaga
   - Mostra posição na fila e informações de cada veículo

## Tecnologias Utilizadas

- Java
- POO (Programação Orientada a Objetos)
- ArrayList (java.util.ArrayList)
- LocalDateTime (java.time.LocalDateTime)
- Scanner (java.util.Scanner) para entrada de dados

## Exemplo de Uso

```
=== MENU PRINCIPAL ===
1. Registrar entrada de veículo
2. Registrar saída de veículo
3. Pesquisar veículo por placa
4. Mostrar vagas disponíveis
5. Mostrar todos os veículos presentes
6. Relatório de faturamento
7. Total arrecadado por dia
8. Mostrar fila de espera
0. Sair

Escolha uma opção: 1

=== REGISTRAR ENTRADA ===
Placa do veículo: ABC-1234

Tipo do veículo:
1. Carro
2. Moto
Escolha uma opção (1 ou 2): 1

Deseja informar a hora de entrada manualmente? (S/N): N

✓ Veículo registrado com sucesso!
Vagas disponíveis: 19
```

### Exemplo de Pesquisa

```
Escolha uma opção: 3

=== PESQUISAR VEÍCULO POR PLACA ===

--- Veículos Estacionados ---
1. Placa: ABC-1234 | Tipo: Carro | Entrada: 22/11/2025 14:30
2. Placa: XYZ-5678 | Tipo: Moto | Entrada: 22/11/2025 15:00
-----------------------------

Placa do veículo: ABC-1234

✓ Veículo encontrado no estacionamento:
Placa: ABC-1234
Tipo: Carro
Hora de entrada: 22/11/2025 14:30
Tempo de permanência: 2 hora(s) e 15 minuto(s)
Valor por hora: R$ 12,00 (1ª hora) / R$ 8,00 (hora adicional)
Valor estimado: R$ 20,00 (cobrado por 3 hora(s))
```

## Observações

- O sistema possui capacidade padrão de 20 vagas (pode ser alterado no construtor)
- **Valores cobrados:**
  - **Carro**: R$ 12,00 (1ª hora) + R$ 8,00 por hora adicional
  - **Moto**: R$ 8,00 (1ª hora) + R$ 5,00 por hora adicional
- O tempo é arredondado para cima (qualquer fração de hora conta como hora completa)
- O tempo mínimo cobrado é de 1 hora
- As datas são formatadas no padrão brasileiro (dd/MM/yyyy HH:mm)
- Quando o estacionamento está lotado, os veículos são adicionados à fila de espera
- Ao registrar uma saída, se houver fila de espera, o primeiro veículo entra automaticamente


