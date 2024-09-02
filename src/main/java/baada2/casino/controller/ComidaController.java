package baada2.casino.controller;

import baada2.casino.entity.comida.ComidaDTO;
import baada2.casino.service.comida.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comidas")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @PostMapping
    public ComidaDTO crearComida(@RequestBody ComidaDTO comidaDTO) {
        return comidaService.crearComida(comidaDTO);
    }

    @GetMapping
    public List<ComidaDTO> obtenerComidas() {
        return comidaService.obtenerComidas();
    }
}
