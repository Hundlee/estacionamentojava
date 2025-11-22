import java.time.LocalDateTime;
import java.time.Duration;

public class Veiculo {
    private String placa;
    private String modelo;
    private String tipo; // Carro, Moto, etc.
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    
    public Veiculo(String placa, String modelo, String tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.dataEntrada = LocalDateTime.now();
        this.dataSaida = null;
    }
    
    // Getters e Setters
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
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
    
    // Calcula o tempo de permanência em horas
    public long calcularTempoPermanencia() {
        LocalDateTime fim = (dataSaida != null) ? dataSaida : LocalDateTime.now();
        Duration duracao = Duration.between(dataEntrada, fim);
        return duracao.toHours() + (duracao.toMinutes() % 60 > 0 ? 1 : 0); // Arredonda para cima
    }
    
    // Calcula o valor a pagar (R$ 5,00 por hora)
    public double calcularValor() {
        long horas = calcularTempoPermanencia();
        if (horas == 0) horas = 1; // Mínimo de 1 hora
        return horas * 5.0;
    }
    
    @Override
    public String toString() {
        if (dataSaida == null) {
            return String.format("Placa: %s | Modelo: %s | Tipo: %s | Entrada: %s | Status: Estacionado",
                    placa, modelo, tipo, dataEntrada.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } else {
            return String.format("Placa: %s | Modelo: %s | Tipo: %s | Entrada: %s | Saída: %s | Valor: R$ %.2f",
                    placa, modelo, tipo,
                    dataEntrada.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    dataSaida.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    calcularValor());
        }
    }
}


