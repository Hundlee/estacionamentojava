import java.util.Scanner;

public class Principal {
    private static Estacionamento estacionamento;
    private static Scanner scanner;
    
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
    
    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Registrar entrada de veículo");
        System.out.println("2. Registrar saída de veículo");
        System.out.println("3. Consultar veículo estacionado");
        System.out.println("4. Mostrar vagas disponíveis");
        System.out.println("5. Relatório de veículos estacionados");
        System.out.println("6. Relatório completo (histórico)");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                registrarEntrada();
                break;
            case 2:
                registrarSaida();
                break;
            case 3:
                consultarVeiculo();
                break;
            case 4:
                mostrarVagas();
                break;
            case 5:
                estacionamento.gerarRelatorioEstacionados();
                break;
            case 6:
                estacionamento.gerarRelatorioCompleto();
                break;
            case 0:
                break;
            default:
                System.out.println("\nOpção inválida! Tente novamente.");
        }
    }
    
    private static void registrarEntrada() {
        System.out.println("\n=== REGISTRAR ENTRADA ===");
        
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim();
        
        if (placa.isEmpty()) {
            System.out.println("Erro: Placa não pode estar vazia!");
            return;
        }
        
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine().trim();
        
        if (modelo.isEmpty()) {
            modelo = "Não informado";
        }
        
        System.out.print("Tipo do veículo (Carro/Moto/Outro): ");
        String tipo = scanner.nextLine().trim();
        
        if (tipo.isEmpty()) {
            tipo = "Carro";
        }
        
        if (estacionamento.registrarEntrada(placa, modelo, tipo)) {
            System.out.println("\n✓ Veículo registrado com sucesso!");
            System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        } else {
            if (estacionamento.buscarVeiculoEstacionado(placa) != null) {
                System.out.println("\n✗ Erro: Veículo com esta placa já está estacionado!");
            } else {
                System.out.println("\n✗ Erro: Estacionamento lotado! Não há vagas disponíveis.");
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
                System.out.println("Tempo de permanência: " + veiculo.calcularTempoPermanencia() + " hora(s)");
            }
            System.out.println("Valor a pagar: R$ " + String.format("%.2f", valor));
            System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        } else {
            System.out.println("\n✗ Erro: Veículo não encontrado ou já foi retirado!");
        }
    }
    
    private static void consultarVeiculo() {
        System.out.println("\n=== CONSULTAR VEÍCULO ===");
        
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
            System.out.println("\n✓ Veículo encontrado:");
            System.out.println(veiculo.toString());
            System.out.println("Tempo de permanência: " + veiculo.calcularTempoPermanencia() + " hora(s)");
            System.out.println("Valor estimado: R$ " + String.format("%.2f", veiculo.calcularValor()));
        } else {
            System.out.println("\n✗ Veículo não encontrado ou já foi retirado!");
        }
    }
    
    private static void mostrarVagas() {
        System.out.println("\n=== SITUAÇÃO DAS VAGAS ===");
        System.out.println("Capacidade total: " + estacionamento.getCapacidadeTotal() + " vagas");
        System.out.println("Vagas ocupadas: " + estacionamento.getVagasOcupadas());
        System.out.println("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        
        double percentualOcupacao = (estacionamento.getVagasOcupadas() * 100.0) / estacionamento.getCapacidadeTotal();
        System.out.println("Percentual de ocupação: " + String.format("%.1f", percentualOcupacao) + "%");
    }
    
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
                             " | Modelo: " + v.getModelo() + 
                             " | Tipo: " + v.getTipo() +
                             " | Entrada: " + v.getDataEntrada().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            numero++;
        }
        System.out.println("-----------------------------\n");
    }
}


