package tech.devinhouse.exercicios.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.exercicios.model.Veiculos;


    @Repository
    public interface VeiculoRepository extends JpaRepository<Veiculos, String> {

    }

