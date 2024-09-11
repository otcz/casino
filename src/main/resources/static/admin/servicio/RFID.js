// Función para manejar el estado del campo de búsqueda de socio y otros campos en la sección "Agregar Servicios"
function toggleSocioInputServicios() {
    const rfidCheck = document.getElementById('rfid-check');
    const buscarSocio = document.getElementById('buscar-socio');

    // Campos adicionales a activar/desactivar
    const nombreSocio = document.getElementById('nombre-socio');
    const valorServicio = document.getElementById('valor-servicio');
    const cantidadEstancias = document.getElementById('cantidad-estancias');
    const tipoServicio = document.getElementById('tipo-servicio');
    const fechaServicio = document.getElementById('fecha-servicio');

    // Si el checkbox está seleccionado, deshabilitar el campo de búsqueda de socio y habilitar los demás campos
    if (rfidCheck.checked) {
        buscarSocio.setAttribute('readonly', true); // Desactivar campo de búsqueda de socio
        buscarSocio.value = '';
        nombreSocio.value='';
        // Habilitar los campos relacionados
        cantidadEstancias.removeAttribute('disabled');
        tipoServicio.removeAttribute('disabled');
        fechaServicio.removeAttribute('disabled');
    } else {
        buscarSocio.removeAttribute('readonly'); // Volver a activar el campo de búsqueda de socio

        // Deshabilitar los campos relacionados
        cantidadEstancias.setAttribute('disabled', true);
        tipoServicio.setAttribute('disabled', true);
        fechaServicio.setAttribute('disabled', true);
    }
}

// Función para manejar el estado del campo de búsqueda de socio en la sección "Habitación y Fomentos"
function toggleSocioInputHabitacionFomento() {
    const rfidCheck2 = document.getElementById('rfid-check2');
    const buscarSocio2 = document.getElementById('buscar-socio2');

    // Si el checkbox está seleccionado, deshabilitar el campo, si no, habilitarlo
    if (rfidCheck2.checked) {
        buscarSocio2.textContent="";
        buscarSocio2.setAttribute('readonly', true);
    } else {
        buscarSocio2.removeAttribute('readonly');
    }
}



