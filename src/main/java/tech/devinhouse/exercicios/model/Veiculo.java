package tech.devinhouse.exercicios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {
    private String placa;

    private String tipo;

    private String cor;

    private Integer anoDeFabricacao;

    private Integer quatidadeMultas;

    public Veiculo(String placa, String tipo, String cor, Integer anoDeFabricacao) {
        this.placa = placa;
        this.tipo = tipo;
        this.cor = cor;
        this.anoDeFabricacao = anoDeFabricacao;
        this.quatidadeMultas = 0;
    }
}
