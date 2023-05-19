package tech.devinhouse.exercicios.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.exercicios.exception.FalhaExclusaoVeiculoComMultasException;
import tech.devinhouse.exercicios.exception.RegistroExistenteException;
import tech.devinhouse.exercicios.exception.RegistroNaoEncontradoException;
import tech.devinhouse.exercicios.model.Veiculos;
import tech.devinhouse.exercicios.repository.VeiculoRepository;

import java.util.List;

@Service
@Slf4j
public class VeiculoService {

    @Autowired
    private VeiculoRepository repo;

    public Veiculos inserir(Veiculos veiculo) {
        boolean existe = repo.existsById(veiculo.getPlaca());
        if (existe){
            log.info("Veiculo com placa ja cadastrada {}", veiculo.getPlaca());
            throw new RegistroExistenteException();
        }
        veiculo = repo.save(veiculo);
        return veiculo;
    }

    public List<Veiculos> listar() {
        return repo.findAll();
    }

    public Veiculos obter(String placa) {
        return repo.findById(placa)
                .orElseThrow(RegistroNaoEncontradoException::new);
    }

    public void excluir(String placa) {
        Veiculos veiculo = repo.findById(placa)
                .orElseThrow(RegistroNaoEncontradoException::new);
        if (veiculo.getQtdMultas() != 0)
            throw new FalhaExclusaoVeiculoComMultasException();
        repo.deleteById(placa);
    }

    public Veiculos adicionarMulta(String placa) {
        Veiculos veiculo = repo.findById(placa)
                .orElseThrow(RegistroNaoEncontradoException::new);
        int qtd = veiculo.getQtdMultas() + 1;
                 veiculo.setQtdMultas(qtd);
        veiculo = repo.save(veiculo);
        return veiculo;
    }
}
