package baada2.casino.controller;


import baada2.casino.entity.configuraciones.CostoFondosEstanciaEntity;
import baada2.casino.entity.configuraciones.CostoFondosEstanciasDTO;
import baada2.casino.service.configuraciones.CostoFondosEstanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/servicio/configurar-costos")
public class CostoFondosEstanciaController {

    @Autowired
    private CostoFondosEstanciaService costoService;

    @PostMapping("/crear")
    public ResponseEntity<CostoFondosEstanciaEntity> crearOActualizarCosto(@RequestBody CostoFondosEstanciasDTO costoDTO) {
        try {
            // Llamamos al servicio para crear o actualizar el costo
            System.out.println(costoDTO.toString());
            CostoFondosEstanciaEntity costoGuardado = costoService.crearOActualizarCosto(costoDTO);
            return new ResponseEntity<>(costoGuardado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/consultar/fecha/{mesAnio}")
    public ResponseEntity<CostoFondosEstanciaEntity> obtenerCostoPorFecha(@PathVariable String mesAnio) {
        try {
            // Consultar por fecha usando el servicio
            CostoFondosEstanciaEntity costo = costoService.obtenerCostoPorFecha(mesAnio);
            if (costo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(costo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
