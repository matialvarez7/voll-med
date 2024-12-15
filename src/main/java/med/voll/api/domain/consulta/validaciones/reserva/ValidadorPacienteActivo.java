package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorConsultas {

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos) {
        var pacienteActivo = repository.findActivoById(datos.idPaciente());
        if (!pacienteActivo) {
            throw new ValidacionException("El paciente debe estar activo para realizar una reserva de consulta");
        }
    }
}
