import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Estacionamento {
    private List<Veiculo> veiculos;
    private int capacidadeTotal;
    
    public Estacionamento(int capacidadeTotal) {
        this.veiculos = new ArrayList<>();
        this.capacidadeTotal = capacidadeTotal;
    }
    
    // Registra entrada de veículo
    public boolean registrarEntrada(String placa, String modelo, String tipo) {
        // Verifica se já existe um veículo com a mesma placa estacionado
        if (buscarVeiculoEstacionado(placa) != null) {
            return false; // Veículo já está estacionado
        }
        
        // Verifica se há vagas disponíveis
        if (getVagasOcupadas() >= capacidadeTotal) {
            return false; // Estacionamento lotado
        }
        
        Veiculo veiculo = new Veiculo(placa, modelo, tipo);
        veiculos.add(veiculo);
        return true;
    }
    
    // Registra saída de veículo e calcula valor
    public double registrarSaida(String placa) {
        Veiculo veiculo = buscarVeiculoEstacionado(placa);
        if (veiculo == null) {
            return -1; // Veículo não encontrado
        }
        
        veiculo.setDataSaida(LocalDateTime.now());
        return veiculo.calcularValor();
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
}


