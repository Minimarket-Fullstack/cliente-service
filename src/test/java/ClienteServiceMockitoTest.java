import com.minimarket.cliente_service.client.NotificacionClient;
import com.minimarket.cliente_service.dto.ClienteRequestDTO;
import com.minimarket.cliente_service.dto.ClienteResponseDTO;
import com.minimarket.cliente_service.model.Cliente;
import com.minimarket.cliente_service.repository.ClienteRepository;
import com.minimarket.cliente_service.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import com.minimarket.cliente_service.dto.ClienteUpdateDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceMockitoTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private NotificacionClient notificacionClient;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void obtenerTodos_deberiaRetornarClientesActivos() {
        Cliente cliente = new Cliente(1L, "12345678-9", "Daniel", "Sanchez", "daniel@test.cl", true);
        when(clienteRepository.findByActivoTrue()).thenReturn(List.of(cliente));

        List<ClienteResponseDTO> resultado = clienteService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNombre());
        verify(clienteRepository).findByActivoTrue();
    }

    @Test
    void guardar_deberiaCrearCliente() {
        ClienteRequestDTO dto = new ClienteRequestDTO("12345678-9", "Daniel", "Sanchez", "daniel@test.cl");
        Cliente guardado = new Cliente(1L, dto.getRut(), dto.getNombre(), dto.getApellido(), dto.getEmail(), true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(guardado);

        ClienteResponseDTO resultado = clienteService.guardar(dto);

        assertEquals(1L, resultado.getId());
        assertEquals("Daniel", resultado.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void obtenerPorId_deberiaBuscarClienteActivo() {
        Cliente cliente = new Cliente(1L, "12345678-9", "Daniel", "Sanchez", "daniel@test.cl", true);
        when(clienteRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(cliente));

        Optional<ClienteResponseDTO> resultado = clienteService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Daniel", resultado.get().getNombre());
        verify(clienteRepository).findByIdAndActivoTrue(1L);
    }

@Test
    void obtenerPorId_deberiaRetornarVacioSiClienteNoExiste() {
        when(clienteRepository.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        Optional<ClienteResponseDTO> resultado = clienteService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
        verify(clienteRepository).findByIdAndActivoTrue(99L);
    }

    @Test
    void actualizar_deberiaModificarClienteActivo() {
        Cliente cliente = new Cliente(1L, "12345678-9", "Daniel", "Sanchez", "daniel@test.cl", true);
        ClienteUpdateDTO dto = new ClienteUpdateDTO("Pedro", "Perez", "pedro@test.cl");
        when(clienteRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<ClienteResponseDTO> resultado = clienteService.actualizar(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());
        assertEquals("pedro@test.cl", resultado.get().getEmail());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void eliminarCli_deberiaDesactivarCliente() {
        Cliente cliente = new Cliente(1L, "12345678-9", "Daniel", "Sanchez", "daniel@test.cl", true);
        when(clienteRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(cliente));

        clienteService.eliminarCli(1L);

        assertFalse(cliente.isActivo());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void buscarPorNombre_deberiaRetornarCoincidenciasActivas() {
        Cliente cliente = new Cliente(1L, "12345678-9", "Daniel", "Sanchez", "daniel@test.cl", true);
        when(clienteRepository.findByNombreContainingIgnoreCaseAndActivoTrue("dan")).thenReturn(List.of(cliente));

        List<ClienteResponseDTO> resultado = clienteService.buscarPorNombre("dan");

        assertEquals(1, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNombre());
        verify(clienteRepository).findByNombreContainingIgnoreCaseAndActivoTrue("dan");
    }
}
