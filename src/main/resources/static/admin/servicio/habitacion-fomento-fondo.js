document.addEventListener('DOMContentLoaded', function () {
    // Elementos del formulario
    const buscarBtn = document.getElementById('buscar-btn2');
    const buscarSocioInput = document.getElementById('buscar-socio2');
    const nombreSocioInput = document.getElementById('nombre-socio2');
    const valorHabitacion = document.getElementById('valor-habitacion');
    const valorCasino = document.getElementById('valor-casino');
    const valorFomento = document.getElementById('valor-fomento');
    const habitacionCheck = document.getElementById('valor-habitacion-check');
    const casinoCheck = document.getElementById('valor-casino-check');
    const fomentoCheck = document.getElementById('valor-fomento-check');
    const submitBtn = document.querySelector('button[type="submit"]');

    // Desactivar todos los campos al iniciar (excepto la búsqueda de socio)
    function toggleFields(enable) {
        submitBtn.disabled = !enable;
    }

    // Lógica para buscar socio
    buscarBtn.addEventListener('click', async function () {
        const documentoIdentidad = buscarSocioInput.value.trim();

        if (documentoIdentidad === '') {
            alert('Por favor ingrese un documento de identidad válido.');
            return;
        }

        try {
            const socioResponse = await fetch(`/socios/consultaridfomento?documento=${documentoIdentidad}`);
            if (!socioResponse.ok) {
                throw new Error('Socio no encontrado');
            }
            const socioData = await socioResponse.json();
            console.log(socioData);  // Verificar respuesta en consola

            if (socioData) {
                nombreSocioInput.value = socioData.nombre || 'No disponible';  // Asignar el nombre o mostrar 'No disponible'
                toggleFields(true); // Habilitar botón de submit si se encuentra el socio
                asignarValores(socioData); // Asignar valores a los campos de fondo y checkbox
                habilitarCheckboxes(true); // Habilitar checkboxes
            } else {
                alert('Socio no encontrado.');
                toggleFields(false); // Deshabilitar botón si no se encuentra el socio
                habilitarCheckboxes(false); // Deshabilitar checkboxes si no se encuentra socio
            }

            // ** Llamada a consultarCostoFondosEstancia aquí **
            const costos = await consultarCostoFondosEstancia();
            if (costos) {
                console.log(costos)
                actualizarCamposCostos(costos);
            }
        } catch (error) {
            console.error('Error al consultar socio:', error);
            alert('Ocurrió un error al consultar el socio.');
            toggleFields(false); // Deshabilitar botón en caso de error
            habilitarCheckboxes(false); // Deshabilitar checkboxes en caso de error
        }
    });

    // Función para asignar los valores a los campos de valor y checkbox
    function asignarValores(data) {
        // Asignar valores, aunque los campos sigan deshabilitados
        valorHabitacion.value = data.fondoHabitacional ? 1 : 0;
        valorCasino.value = data.fondoCasino ? 1 : 0;
        valorFomento.value = data.fomento ? 1 : 0;

        // Asignar los valores a los checkboxes
        habitacionCheck.checked = data.fondoHabitacional;
        casinoCheck.checked = data.fondoCasino;
        fomentoCheck.checked = data.fomento;
    }

    // Función para habilitar o deshabilitar los checkboxes
    function habilitarCheckboxes(enable) {
        habitacionCheck.disabled = !enable;
        casinoCheck.disabled = !enable;
        fomentoCheck.disabled = !enable;
    }

    // ** Nueva función para actualizar los campos con los costos **
    function actualizarCamposCostos(costos) {
        // Actualizar los valores de los campos de acuerdo a los costos obtenidos
        valorHabitacion.value = costos.fondoHabitacional || 0;
        valorCasino.value = costos.fondoCasino || 0;
        valorFomento.value = costos.fomento || 0;
    }
});

// Evento al botón "Agregar Servicio"
document.getElementById('agregar-servicio-btn').addEventListener('click', function(event) {
    event.preventDefault(); // Evitar el envío por defecto del formulario

    // Obtener los valores del formulario
    const documento = document.getElementById('buscar-socio2').value;
    const habitacionActivo = document.getElementById('valor-habitacion-check').checked;
    const casinoActivo = document.getElementById('valor-casino-check').checked;
    const fomentoActivo = document.getElementById('valor-fomento-check').checked;

    // Validar si el socio y los campos están correctos antes de enviar
    if (!documento) {
        alert('Por favor ingresa el documento de identidad del socio.');
        return;
    }

    // Crear el objeto con los datos para enviar
    const servicioData = {
        documento: documento,
        fondoHabitacional: habitacionActivo,
        fondoCasino: casinoActivo,
        fomento: fomentoActivo
    };

    // Enviar la solicitud al backend (AJAX o fetch)
    fetch('/socios/actualizar-servicios', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(servicioData)
    })
        .then(response => {
            if (response.ok) {
                alert('Servicios actualizados exitosamente.');
                // Aquí puedes actualizar la interfaz según sea necesario
            } else {
                alert('Error al actualizar los servicios.');
            }
        })
        .catch(error => {
            console.error('Error al realizar la solicitud:', error);
            alert('Hubo un problema al intentar actualizar los servicios.');
        });
});
// Función para consultar costos
async function consultarCostoFondosEstancia() {
    const mesAnio = obtenerMesAnioActual();

    try {
        const response = await fetch(`configurar-costos/consultar/fecha/${mesAnio}`);
        if (!response.ok) {
            throw new Error('No se encontró información para la fecha proporcionada.');
        }
        const data = await response.json();
        return data; // Retornar los datos de costos
    } catch (error) {
        console.error('Error al consultar los costos:', error.message);
        return null;
    }
}

function obtenerMesAnioActual() {
    const fechaActual = new Date();
    const año = fechaActual.getFullYear();
    const mes = String(fechaActual.getMonth() + 1).padStart(2, '0');
    return `${año}-${mes}`;
}



