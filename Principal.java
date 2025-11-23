import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe principal do sistema de estacionamento.
 * Gerencia o menu interativo e as operações do sistema.
 */
public class Principal {
    private static Estacionamento estacionamento;
    private static Scanner scanner;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Método principal que inicia o sistema.
     * @param args Argumentos da linha de comando
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        estacionamento = new Estacionamento(20); // Capacidade de 20 vagas
        
        System.out.println("========================================");
        System.out.println("   SISTEMA DE ESTACIONAMENTO");
        System.out.println("========================================\n");
        
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
        
        System.out.println("\nSistema encerrado. Obrigado!");
        scanner.close();
    }
    
    /**
     * Exibe o menu principal com todas as opções disponíveis.
     */
    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Registrar entrada de veículo");
        System.out.println("2. Registrar saída de veículo");
        System.out.println("3. Pesquisar veículo por placa");
        System.out.println("4. Mostrar vagas disponíveis");
        System.out.println("5. Mostrar todos os veículos presentes");
        System.out.println("6. Relatório de faturamento");
        System.out.println("7. Total arrecadado por dia");
        System.out.println("8. Mostrar fila de espera");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    /**
     * Lê a opção escolhida pelo usuário.
     * @return Número da opção ou -1 se inválida
     */
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Processa a opção escolhida pelo usuário.
     * @param opcao Opção selecionada no menu
     */
    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                registrarEntrada();
                break;
            case 2:
                registrarSaida();
                break;
            case 3:
                pesquisarVeiculo();
                break;
            case 4:
                mostrarVagas();
                break;
            case 5:
                mostrarTodosVeiculos();
                break;
            case 6:
                estacionamento.gerarRelatorioFaturamento();
                break;
            case 7:
                mostrarTotalArrecadadoPorDia();
                break;
            case 8:
                mostrarFilaEspera();
                break;
            case 0:
                break;
            default:
                System.out.println("\nOpção inválida! Tente novamente.");
        }
    }
    
    /**
     * Registra a entrada de um veículo no estacionamento.
     * Permite escolher entre usar a hora atual ou informar manualmente.
     */
    private static void registrarEntrada() {
        System.out.println("\n=== REGISTRAR ENTRADA ===");
        
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim();
        
        if (placa.isEmpty()) {
            System.out.println("Erro: Placa não pode estar vazia!");
            return;
        }
        
        // Menu de seleção de tipo de veículo
        System.out.println("\nTipo do veículo:");
        System.out.println("1. Carro");
        System.out.println("2. Moto");
        System.out.print("Escolha uma opção (1 ou 2): ");
        String opcaoTipo = scanner.nextLine().trim();
        
        String tipo;
        if (opcaoTipo.equals("1")) {
            tipo = "Carro";
        } else if (opcaoTipo.equals("2")) {
            tipo = "Moto";
        } else {
            System.out.println("Opção inválida! Será usado 'Carro' como padrão.");
            tipo = "Carro";
        }
        
        // Pergunta se deseja informar hora de entrada manualmente
        System.out.print("Deseja informar a hora de entrada manualmente? (S/N): ");
        String resposta = scanner.nextLine().trim();
        
        LocalDateTime dataEntrada = null;
        boolean usarHoraManual = resposta.equalsIgnoreCase("S");
        
        if (usarHoraManual) {
            System.out.print("Informe a data e hora de entrada (dd/MM/yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine().trim();
            
            try {
                dataEntrada = LocalDateTime.parse(dataHoraStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato de data inválido! Use: dd/MM/yyyy HH:mm");
                System.out.println("Exemplo: 25/12/2024 14:30");
                return;
            }
        }
        
        // Tenta registrar a entrada
        boolean sucesso;
        if (usarHoraManual && dataEntrada != null) {
            sucesso = estacionamento.registrarEntrada(placa, tipo, dataEntrada);
        } else {
            sucesso = estacionamento.registrarEntrada(placa, tipo);
        }
        
        if (sucesso) {
            // Verifica se o veículo foi para a fila de espera (verifica após o registro)
            boolean estaNaFila = false;
            for (Veiculo v : estacionamento.getFilaEspera()) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    estaNaFila = true;
                    break;
                }
            }
            
            if (estaNaFila) {
                System.out.println("\n✓ Veículo adicionado à fila de espera!");
                System.out.println("Posição na fila: " + estacionamento.getTamanhoFilaEspera());
                if (usarHoraManual && dataEntrada != null) {
                    System.out.println("Hora de entrada: " + dataEntrada.format(formatter));
                }
            } else {
                System.out.println("\n✓ Veículo registrado com sucesso!");
                if (usarHoraManual && dataEntrada != null) {
                    System.out.println("Hora de entrada: " + dataEntrada.format(formatter));
                }
                System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
            }
        } else {
            if (estacionamento.buscarVeiculoEstacionado(placa) != null) {
                System.out.println("\n✗ Erro: Veículo com esta placa já está estacionado!");
            } else {
                // Verifica se está na fila
                boolean naFila = false;
                for (Veiculo v : estacionamento.getFilaEspera()) {
                    if (v.getPlaca().equalsIgnoreCase(placa)) {
                        naFila = true;
                        break;
                    }
                }
                if (naFila) {
                    System.out.println("\n✗ Erro: Veículo já está na fila de espera!");
                } else {
                    System.out.println("\n✗ Erro: Não foi possível registrar a entrada!");
                }
            }
        }
    }
    
    private static void registrarSaida() {
        System.out.println("\n=== REGISTRAR SAÍDA ===");
        
        // Mostra lista de veículos estacionados
        exibirListaVeiculosEstacionados();
        
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim();
        
        if (placa.isEmpty()) {
            System.out.println("Erro: Placa não pode estar vazia!");
            return;
        }
        
        double valor = estacionamento.registrarSaida(placa);
        
        if (valor >= 0) {
            Veiculo veiculo = estacionamento.buscarVeiculoEstacionado(placa);
            if (veiculo == null) {
                // Busca no histórico
                for (Veiculo v : estacionamento.getTodosVeiculos()) {
                    if (v.getPlaca().equalsIgnoreCase(placa) && v.getDataSaida() != null) {
                        veiculo = v;
                        break;
                    }
                }
            }
            
            System.out.println("\n✓ Saída registrada com sucesso!");
            if (veiculo != null) {
                System.out.println("Tempo de permanência: " + veiculo.getTempoPermanenciaFormatado());
                System.out.println("Horas cobradas: " + veiculo.calcularTempoPermanencia() + " hora(s)");
            }
            System.out.println("Valor a pagar: R$ " + String.format("%.2f", valor));
            System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
            
            // Informa se um veículo da fila entrou automaticamente
            if (estacionamento.getTamanhoFilaEspera() > 0) {
                System.out.println("ℹ Um veículo da fila de espera entrou automaticamente!");
                System.out.println("Veículos restantes na fila: " + estacionamento.getTamanhoFilaEspera());
            }
        } else {
            System.out.println("\n✗ Erro: Veículo não encontrado ou já foi retirado!");
        }
    }
    
    /**
     * Pesquisa um veículo pela placa e mostra informações se estiver estacionado.
     */
    private static void pesquisarVeiculo() {
        System.out.println("\n=== PESQUISAR VEÍCULO POR PLACA ===");
        
        // Mostra lista de veículos estacionados
        exibirListaVeiculosEstacionados();
        
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim();
        
        if (placa.isEmpty()) {
            System.out.println("Erro: Placa não pode estar vazia!");
            return;
        }
        
        Veiculo veiculo = estacionamento.buscarVeiculoEstacionado(placa);
        
        if (veiculo != null) {
            // Calcula as tarifas por hora
            String tarifaHora;
            if (veiculo.getTipo().equalsIgnoreCase("Moto")) {
                tarifaHora = "R$ 8,00 (1ª hora) / R$ 5,00 (hora adicional)";
            } else {
                tarifaHora = "R$ 12,00 (1ª hora) / R$ 8,00 (hora adicional)";
            }
            
            System.out.println("\n✓ Veículo encontrado no estacionamento:");
            System.out.println("Placa: " + veiculo.getPlaca());
            System.out.println("Tipo: " + veiculo.getTipo());
            System.out.println("Hora de entrada: " + veiculo.getDataEntrada().format(formatter));
            System.out.println("Tempo de permanência: " + veiculo.getTempoPermanenciaFormatado());
            System.out.println("Valor por hora: " + tarifaHora);
            System.out.println("Valor estimado: R$ " + String.format("%.2f", veiculo.calcularValor()) + 
                             " (cobrado por " + veiculo.calcularTempoPermanencia() + " hora(s))");
        } else {
            System.out.println("\n✗ Veículo não encontrado ou já foi retirado!");
        }
    }
    
    /**
     * Mostra todos os veículos atualmente estacionados (apenas as placas).
     */
    private static void mostrarTodosVeiculos() {
        System.out.println("\n=== TODOS OS VEÍCULOS PRESENTES ===");
        
        java.util.List<Veiculo> veiculos = estacionamento.getVeiculosEstacionados();
        
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo estacionado no momento.");
        } else {
            System.out.println("Veículos estacionados:");
            int numero = 1;
            for (Veiculo v : veiculos) {
                System.out.println(numero + ". " + v.getPlaca() + " - Tempo estacionado: " + v.getTempoPermanenciaFormatado());
                numero++;
            }
        }
        System.out.println("====================================\n");
    }
    
    /**
     * Mostra a quantidade de vagas disponíveis e ocupadas.
     */
    private static void mostrarVagas() {
        System.out.println("\n=== SITUAÇÃO DAS VAGAS ===");
        System.out.println("Capacidade total: " + estacionamento.getCapacidadeTotal() + " vagas");
        System.out.println("Vagas ocupadas: " + estacionamento.getVagasOcupadas());
        System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        
        double percentualOcupacao = (estacionamento.getVagasOcupadas() * 100.0) / estacionamento.getCapacidadeTotal();
        System.out.println("Percentual de ocupação: " + String.format("%.1f", percentualOcupacao) + "%");
        System.out.println("==========================\n");
    }
    
    /**
     * Exibe lista completa de veículos estacionados com informações detalhadas.
     * Usado internamente para auxiliar outras operações.
     */
    private static void exibirListaVeiculosEstacionados() {
        java.util.List<Veiculo> veiculos = estacionamento.getVeiculosEstacionados();
        
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo estacionado no momento.\n");
            return;
        }
        
        System.out.println("\n--- Veículos Estacionados ---");
        int numero = 1;
        for (Veiculo v : veiculos) {
            System.out.println(numero + ". Placa: " + v.getPlaca() + 
                             " | Tipo: " + v.getTipo() +
                             " | Entrada: " + v.getDataEntrada().format(formatter));
            numero++;
        }
        System.out.println("-----------------------------\n");
    }
    
    /**
     * Mostra o total arrecadado em um dia específico.
     */
    private static void mostrarTotalArrecadadoPorDia() {
        System.out.println("\n=== TOTAL ARRECADADO POR DIA ===");
        System.out.print("Informe a data (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine().trim();
        
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, dateFormatter);
            double total = estacionamento.getTotalArrecadadoPorDia(data);
            
            System.out.println("\nData: " + data.format(dateFormatter));
            System.out.println("Total arrecadado: R$ " + String.format("%.2f", total));
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use: dd/MM/yyyy");
            System.out.println("Exemplo: 25/12/2024");
        }
        System.out.println("==================================\n");
    }
    
    /**
     * Mostra a fila de espera de veículos.
     */
    private static void mostrarFilaEspera() {
        System.out.println("\n=== FILA DE ESPERA ===");
        
        java.util.List<Veiculo> fila = estacionamento.getFilaEspera();
        
        if (fila.isEmpty()) {
            System.out.println("Nenhum veículo na fila de espera.");
        } else {
            System.out.println("Total de veículos aguardando: " + fila.size());
            System.out.println("\nVeículos na fila:");
            int posicao = 1;
            for (Veiculo v : fila) {
                System.out.println(posicao + ". Placa: " + v.getPlaca() + 
                                 " | Tipo: " + v.getTipo() +
                                 " | Entrada: " + v.getDataEntrada().format(formatter));
                posicao++;
            }
        }
        System.out.println("======================\n");
    }
}


