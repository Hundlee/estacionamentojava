import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Classe que representa um veículo no estacionamento.
 * Armazena informações da placa, tipo, hora de entrada e saída.
 */
public class Veiculo {
    private String placa;
    private String tipo; // Carro, Moto, etc.
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    
    /**
     * Construtor que cria um veículo com hora de entrada atual.
     * @param placa Placa do veículo
     * @param tipo Tipo do veículo (Carro, Moto, etc.)
     */
    public Veiculo(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.dataEntrada = LocalDateTime.now();
        this.dataSaida = null;
    }
    
    /**
     * Construtor que permite definir a hora de entrada manualmente.
     * @param placa Placa do veículo
     * @param tipo Tipo do veículo (Carro, Moto, etc.)
     * @param dataEntrada Data e hora de entrada do veículo
     */
    public Veiculo(String placa, String tipo, LocalDateTime dataEntrada) {
        this.placa = placa;
        this.tipo = tipo;
        this.dataEntrada = dataEntrada;
        this.dataSaida = null;
    }
    
    // Getters e Setters
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
    
    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    
    public LocalDateTime getDataSaida() {
        return dataSaida;
    }
    
    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    /**
     * Calcula o tempo real de permanência para exibição.
     * Retorna o tempo exato decorrido (horas e minutos).
     * @return String formatada com o tempo de permanência
     */
    public String getTempoPermanenciaFormatado() {
        LocalDateTime fim = (dataSaida != null) ? dataSaida : LocalDateTime.now();
        Duration duracao = Duration.between(dataEntrada, fim);
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes() % 60;
        
        if (horas == 0 && minutos == 0) {
            return "menos de 1 minuto";
        } else if (horas == 0) {
            return minutos + " minuto(s)";
        } else if (minutos == 0) {
            return horas + " hora(s)";
        } else {
            return horas + " hora(s) e " + minutos + " minuto(s)";
        }
    }
    
    /**
     * Calcula o tempo de permanência em horas para cobrança.
     * Não existe fração de hora: 1 hora e 1 minuto = 2 horas.
     * @return Número de horas (arredondado para cima)
     */
    public long calcularTempoPermanencia() {
        LocalDateTime fim = (dataSaida != null) ? dataSaida : LocalDateTime.now();
        Duration duracao = Duration.between(dataEntrada, fim);
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes() % 60;
        
        // Se houver qualquer fração de hora (minutos > 0), conta como hora adicional
        if (minutos > 0 || horas == 0) {
            horas += 1;
        }
        
        return horas;
    }
    
    /**
     * Calcula o valor a pagar baseado no tipo de veículo.
     * Carro: primeira hora R$12,00 + R$8,00 por hora adicional
     * Moto: primeira hora R$8,00 + R$5,00 por hora adicional
     * Outros: primeira hora R$12,00 + R$8,00 por hora adicional
     * @return Valor total a pagar
     */
    public double calcularValor() {
        long horas = calcularTempoPermanencia();
        if (horas == 0) horas = 1; // Mínimo de 1 hora
        
        double primeiraHora;
        double horaAdicional;
        
        // Define valores conforme o tipo de veículo
        if (tipo.equalsIgnoreCase("Moto")) {
            primeiraHora = 8.0;
            horaAdicional = 5.0;
        } else {
            // Carro ou outros tipos
            primeiraHora = 12.0;
            horaAdicional = 8.0;
        }
        
        // Calcula: primeira hora + horas adicionais
        if (horas == 1) {
            return primeiraHora;
        } else {
            return primeiraHora + (horas - 1) * horaAdicional;
        }
    }
    
    @Override
    public String toString() {
        if (dataSaida == null) {
            return String.format("Placa: %s | Tipo: %s | Entrada: %s | Status: Estacionado",
                    placa, tipo, dataEntrada.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } else {
            return String.format("Placa: %s | Tipo: %s | Entrada: %s | Saída: %s | Valor: R$ %.2f",
                    placa, tipo,
                    dataEntrada.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    dataSaida.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    calcularValor());
        }
    }
}


