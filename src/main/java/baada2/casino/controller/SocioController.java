package baada2.casino.controller;

import baada2.casino.entity.socio.LoginRequestDTO;
import baada2.casino.entity.socio.SocioDTO;
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

    @PostMapping("/crear")
    public ResponseEntity<SocioDTO> crearSocio(@RequestBody SocioDTO socioDTO) {
        try {
            SocioDTO nuevoSocio = socioService.crearSocio(socioDTO);
            return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
        } catch (Exception e) {
            // Puedes registrar el error aquí
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear socio", e);
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
}
