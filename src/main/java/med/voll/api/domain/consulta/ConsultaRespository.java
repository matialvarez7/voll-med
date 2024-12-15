package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRespository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndFechaBetween(@NotNull Long idMedico, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(Long aLong, @NotNull @Future LocalDateTime fecha);
}
