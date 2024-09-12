package baada2.casino.controller;

import baada2.casino.entity.comida.TablaDTO;
import baada2.casino.entity.socio.*;
import baada2.casino.service.comida.ComidaService;
import baada2.casino.service.socios.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;
    @Autowired
    private ComidaService comidaService;

    @PostMapping("/crear")
    public ResponseEntity<SocioRegistroDTO> crearSocio(@RequestBody SocioRegistroDTO socioDTO) {
        try {
            SocioRegistroDTO nuevoSocio = socioService.crearSocio(socioDTO);
            return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear socio", e);
        }
    }

    @PutMapping("/actualizar-servicios")
    public ResponseEntity<SocioFomentoDTO> editarSocio(@RequestBody SocioFomentoDTO socioDTO) {
        try {
            // Actualizar el socio
            SocioEntity socioExistente = socioService.actualizarSocio(socioDTO);

            // Verificar si el documento está vacío
            if (socioExistente.getDocumento().isEmpty()) {
                return ResponseEntity.ok().build(); // Retorna 200 OK sin cuerpo
            } else {
                // Aquí se pueden manejar otros casos si es necesario
                return ResponseEntity.ok().body(socioDTO); // Retorna 200 OK con el objeto actualizado
            }
        } catch (Exception e) {
            // Manejo centralizado de errores
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar socio", e);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<SocioDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            SocioDTO socio = socioService.autenticarSocio(loginRequest.getDocumento(), loginRequest.getPassword());
            if (socio == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(socio, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el inicio de sesión", e);
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<SocioDTO>> obtenerSocios() {
        try {
            List<SocioDTO> socios = socioService.obtenerSocios();
            if (socios.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(socios, HttpStatus.OK);
        } catch (Exception e) {
            // Puedes registrar el error aquí
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener socios", e);
        }
    }

    @GetMapping("/consultarid")
    public ResponseEntity<SocioDTO> obtenerSocioPorDocumento(@RequestParam("documento") String documento) {
        try {
            SocioDTO socio = socioService.obtenerSocioPorDocumento(documento);
            if (socio == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No se encontró el socio
            }
            return new ResponseEntity<>(socio, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener socio", e);
        }
    }

    @GetMapping("/consultaridfomento")
    public ResponseEntity<SocioFomentoDTO> obtenerSocioFomento(@RequestParam("documento") String documento) {
        try {
            SocioFomentoDTO socio = socioService.getSocioFomentosNombreCard(documento);
            if (socio == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No se encontró el socio
            }
            return new ResponseEntity<>(socio, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener socio", e);
        }
    }

    @GetMapping("/comida/{documento}")
    public ResponseEntity<TablaDTO> obtenerComidasPorSocio(@PathVariable String documento) {
        TablaDTO comidas = comidaService.obtenerComidasPorSocio(documento);
        return ResponseEntity.ok(comidas);
    }
}
