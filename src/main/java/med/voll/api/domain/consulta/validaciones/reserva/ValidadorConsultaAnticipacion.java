package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaAnticipacion implements ValidadorConsultas{
    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var horaActual = LocalDateTime.now();
        var diferenciaMinutos = Duration.between(horaActual, fechaConsulta).toMinutes();
        if (diferenciaMinutos < 30) {
            throw new ValidacionException("Las reservas deben realizarse con al menos 30 minutos de anticipación");
        }
    }
}
