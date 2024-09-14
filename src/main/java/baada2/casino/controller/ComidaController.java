package baada2.casino.controller;

import baada2.casino.entity.comida.ComidaDTO;
import baada2.casino.service.comida.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comidas")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @PostMapping("/crear")
    public ResponseEntity<ComidaDTO> crearComida(@RequestBody ComidaDTO comidaDTO) {
        ComidaDTO nuevaComida = comidaService.crearComida(comidaDTO);
        return new ResponseEntity<>(nuevaComida, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ComidaDTO>> obtenerComidas() {
        List<ComidaDTO> comidas = comidaService.obtenerComidas();
        return new ResponseEntity<>(comidas, HttpStatus.OK);
    }
}
