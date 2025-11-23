import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Classe que gerencia o estacionamento.
 * Controla a lista de veículos, vagas disponíveis, total arrecadado e fila de espera.
 */
public class Estacionamento {
    private List<Veiculo> veiculos;
    private int capacidadeTotal;
    private double totalArrecadado; // Total arrecadado com todas as saídas
    private Queue<Veiculo> filaEspera; // Fila de veículos aguardando vaga
    
    /**
     * Construtor do estacionamento.
     * @param capacidadeTotal Número total de vagas disponíveis
     */
    public Estacionamento(int capacidadeTotal) {
        this.veiculos = new ArrayList<>();
        this.capacidadeTotal = capacidadeTotal;
        this.totalArrecadado = 0.0;
        this.filaEspera = new LinkedList<>();
    }
    
    /**
     * Registra entrada de veículo com hora atual.
     * @param placa Placa do veículo
     * @param tipo Tipo do veículo
     * @return true se a entrada foi registrada com sucesso, false caso contrário
     */
    public boolean registrarEntrada(String placa, String tipo) {
        return registrarEntrada(placa, tipo, LocalDateTime.now());
    }
    
    /**
     * Registra entrada de veículo com hora de entrada específica.
     * Se o estacionamento estiver cheio, adiciona à fila de espera.
     * @param placa Placa do veículo
     * @param tipo Tipo do veículo
     * @param dataEntrada Data e hora de entrada do veículo
     * @return true se a entrada foi registrada com sucesso, false se já está estacionado, 
     *         ou adiciona à fila se estiver cheio (retorna true mas veículo fica na fila)
     */
    public boolean registrarEntrada(String placa, String tipo, LocalDateTime dataEntrada) {
        // Verifica se já existe um veículo com a mesma placa estacionado
        if (buscarVeiculoEstacionado(placa) != null) {
            return false; // Veículo já está estacionado
        }
        
        // Verifica se o veículo já está na fila de espera
        for (Veiculo v : filaEspera) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return false; // Veículo já está na fila
            }
        }
        
        Veiculo veiculo = new Veiculo(placa, tipo, dataEntrada);
        
        // Verifica se há vagas disponíveis
        if (getVagasOcupadas() >= capacidadeTotal) {
            // Estacionamento lotado - adiciona à fila de espera
            filaEspera.offer(veiculo);
            return true; // Retorna true mas veículo fica na fila
        }
        
        // Há vaga disponível - estaciona diretamente
        veiculos.add(veiculo);
        return true;
    }
    
    /**
     * Registra saída de veículo, calcula valor e adiciona ao total arrecadado.
     * Quando uma vaga é liberada, o primeiro veículo da fila de espera entra automaticamente.
     * @param placa Placa do veículo
     * @return Valor a pagar se a saída foi registrada, -1 se veículo não encontrado
     */
    public double registrarSaida(String placa) {
        Veiculo veiculo = buscarVeiculoEstacionado(placa);
        if (veiculo == null) {
            return -1; // Veículo não encontrado
        }
        
        veiculo.setDataSaida(LocalDateTime.now());
        double valor = veiculo.calcularValor();
        totalArrecadado += valor; // Adiciona ao total arrecadado
        
        // Verifica se há veículos na fila de espera e se há vaga disponível
        if (!filaEspera.isEmpty()) {
            Veiculo proximoVeiculo = filaEspera.poll(); // Remove o primeiro da fila
            veiculos.add(proximoVeiculo); // Estaciona o veículo da fila
        }
        
        return valor;
    }
    
    // Busca veículo estacionado pela placa
    public Veiculo buscarVeiculoEstacionado(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa) && v.getDataSaida() == null) {
                return v;
            }
        }
        return null;
    }
    
    // Retorna lista de veículos estacionados
    public List<Veiculo> getVeiculosEstacionados() {
        List<Veiculo> estacionados = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (v.getDataSaida() == null) {
                estacionados.add(v);
            }
        }
        return estacionados;
    }
    
    // Retorna lista de todos os veículos (histórico)
    public List<Veiculo> getTodosVeiculos() {
        return new ArrayList<>(veiculos);
    }
    
    // Retorna número de vagas ocupadas
    public int getVagasOcupadas() {
        return getVeiculosEstacionados().size();
    }
    
    // Retorna número de vagas disponíveis
    public int getVagasDisponiveis() {
        return capacidadeTotal - getVagasOcupadas();
    }
    
    // Retorna capacidade total
    public int getCapacidadeTotal() {
        return capacidadeTotal;
    }
    
    // Gera relatório de veículos estacionados
    public void gerarRelatorioEstacionados() {
        List<Veiculo> estacionados = getVeiculosEstacionados();
        System.out.println("\n=== RELATÓRIO DE VEÍCULOS ESTACIONADOS ===");
        System.out.println("Total de vagas: " + capacidadeTotal);
        System.out.println("Vagas ocupadas: " + getVagasOcupadas());
        System.out.println("Vagas disponíveis: " + getVagasDisponiveis());
        System.out.println("\nVeículos estacionados:");
        
        if (estacionados.isEmpty()) {
            System.out.println("Nenhum veículo estacionado no momento.");
        } else {
            for (Veiculo v : estacionados) {
                System.out.println("- " + v.toString());
            }
        }
        System.out.println("==========================================\n");
    }
    
    // Gera relatório completo (histórico)
    public void gerarRelatorioCompleto() {
        System.out.println("\n=== RELATÓRIO COMPLETO ===");
        System.out.println("Total de veículos atendidos: " + veiculos.size());
        System.out.println("\nHistórico completo:");
        
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo registrado.");
        } else {
            for (Veiculo v : veiculos) {
                System.out.println("- " + v.toString());
            }
        }
        System.out.println("==========================\n");
    }
    
    /**
     * Retorna o total arrecadado até o momento.
     * @return Total arrecadado em reais
     */
    public double getTotalArrecadado() {
        return totalArrecadado;
    }
    
    /**
     * Gera relatório de faturamento mostrando o total arrecadado.
     */
    public void gerarRelatorioFaturamento() {
        System.out.println("\n=== RELATÓRIO DE FATURAMENTO ===");
        System.out.println("Total arrecadado: R$ " + String.format("%.2f", totalArrecadado));
        System.out.println("===============================\n");
    }
    
    /**
     * Calcula e retorna o total arrecadado em um dia específico.
     * @param data Data para consultar o faturamento
     * @return Total arrecadado no dia especificado
     */
    public double getTotalArrecadadoPorDia(LocalDate data) {
        double total = 0.0;
        for (Veiculo v : veiculos) {
            if (v.getDataSaida() != null) {
                LocalDate dataSaida = v.getDataSaida().toLocalDate();
                if (dataSaida.equals(data)) {
                    total += v.calcularValor();
                }
            }
        }
        return total;
    }
    
    /**
     * Retorna a quantidade de veículos na fila de espera.
     * @return Número de veículos aguardando vaga
     */
    public int getTamanhoFilaEspera() {
        return filaEspera.size();
    }
    
    /**
     * Retorna a fila de espera (cópia para não modificar a original).
     * @return Lista de veículos na fila de espera
     */
    public List<Veiculo> getFilaEspera() {
        return new ArrayList<>(filaEspera);
    }
}


