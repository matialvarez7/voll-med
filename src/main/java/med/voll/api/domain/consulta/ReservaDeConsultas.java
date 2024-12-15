package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validaciones.cancelamiento.ValidadorCancelamientoConsulta;
import med.voll.api.domain.consulta.validaciones.reserva.ValidadorConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRespository consultaRepository;
    @Autowired
    private List<ValidadorConsultas> validadores;
    @Autowired
    private List<ValidadorCancelamientoConsulta> validadoresCancelamiento;


    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {

        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidacionException("No existe un paciente con el id informado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionException("No existe un medico con el id informado");
        }

        validadores.forEach(v -> v.validar(datos));

        Medico medico = elegirMedico(datos);
        if (medico == null) {
            throw new ValidacionException("No existe un medico con el id informado");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null) {
           throw new ValidacionException("Es necesario elegir una especialidad en caso de no elegir un médico");
        }
        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(@Valid DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
