package tech.devinhouse.exercicios.controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.exercicios.dto.VeiculoRequest;
import tech.devinhouse.exercicios.dto.VeiculoResponse;
import tech.devinhouse.exercicios.model.Veiculos;
import tech.devinhouse.exercicios.service.VeiculoService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/veiculos")
@Slf4j
public class VeiculoController {
    @Autowired
    private VeiculoService service;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<VeiculoResponse> adicionar(@RequestBody @Valid VeiculoRequest request) {
        log.debug("Dados da request: {} ", request);
        Veiculos veiculo = modelMapper.map(request, Veiculos.class);
        veiculo = service.inserir(veiculo);
        log.info("Placa {} cadastrada com sucesso!", veiculo.getPlaca());
        var resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> consultar() {
        List<Veiculos> veiculos = service.listar();
        List<VeiculoResponse> veiculosResp = veiculos.stream()
                .map(v -> modelMapper.map(v, VeiculoResponse.class)).toList();
        return ResponseEntity.ok(veiculosResp);
    }

    @GetMapping("{placa}")
    public ResponseEntity<VeiculoResponse> consultar(@PathVariable String placa) {
        Veiculos veiculo = service.obter(placa);
        VeiculoResponse resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("{placa}")
    public ResponseEntity excluir(@PathVariable String placa) {
        service.excluir(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{placa}/multas")
    public ResponseEntity multar(@PathVariable String placa) {
        var veiculo = service.adicionarMulta(placa);
        var resp = modelMapper.map(veiculo, VeiculoResponse.class);
        return ResponseEntity.ok(resp);
    }




}
