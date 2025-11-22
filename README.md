# Sistema de Estacionamento em Java

Sistema completo de gerenciamento de estacionamento desenvolvido em Java, utilizando Programação Orientada a Objetos (POO), ArrayList e LocalDateTime.

## Características

- ✅ Menu interativo no console
- ✅ Registro de entrada e saída de veículos
- ✅ Cálculo automático de valores (R$ 5,00 por hora)
- ✅ Controle de vagas disponíveis
- ✅ Relatórios de veículos estacionados e histórico completo
- ✅ Validações e tratamento de erros

## Estrutura do Projeto

### Classes

1. **Veiculo.java**
   - Representa um veículo no estacionamento
   - Atributos: placa, modelo, tipo, dataEntrada, dataSaida
   - Métodos para calcular tempo de permanência e valor

2. **Estacionamento.java**
   - Gerencia o estacionamento
   - Utiliza ArrayList para armazenar veículos
   - Controla capacidade e vagas disponíveis
   - Gera relatórios

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
   - Registra placa, modelo e tipo do veículo
   - Verifica disponibilidade de vagas
   - Valida se o veículo já está estacionado

2. **Registrar saída de veículo**
   - Calcula tempo de permanência
   - Calcula valor a pagar
   - Libera a vaga

3. **Consultar veículo estacionado**
   - Busca veículo pela placa
   - Mostra informações e valor estimado

4. **Mostrar vagas disponíveis**
   - Exibe situação atual do estacionamento
   - Mostra percentual de ocupação

5. **Relatório de veículos estacionados**
   - Lista todos os veículos atualmente estacionados

6. **Relatório completo (histórico)**
   - Mostra histórico de todos os veículos atendidos

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
3. Consultar veículo estacionado
4. Mostrar vagas disponíveis
5. Relatório de veículos estacionados
6. Relatório completo (histórico)
0. Sair

Escolha uma opção: 1

=== REGISTRAR ENTRADA ===
Placa do veículo: ABC-1234
Modelo do veículo: Honda Civic
Tipo do veículo (Carro/Moto/Outro): Carro

✓ Veículo registrado com sucesso!
Vagas disponíveis: 19
```

## Observações

- O sistema possui capacidade padrão de 20 vagas (pode ser alterado no construtor)
- O valor cobrado é de R$ 5,00 por hora (arredondado para cima)
- O tempo mínimo cobrado é de 1 hora
- As datas são formatadas no padrão brasileiro (dd/MM/yyyy HH:mm)


