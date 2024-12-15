package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConConsultaMismaFechaYHorario implements ValidadorConsultas {

    @Autowired
    private ConsultaRespository respository;

    public void validar(DatosReservaConsulta datos) {
        var medicoNoDisponible = respository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(), datos.fecha());
        if (medicoNoDisponible) {
            throw new ValidacionException("No está disponible la reserva para ese horario");
        }
    }
}
