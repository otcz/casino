const buscarBtn = document.getElementById('buscar-btn');
const buscarSocioInput = document.getElementById('buscar-socio');
const nombreSocioInput = document.getElementById('nombre-socio');
const cantidadEstanciasInput = document.getElementById('cantidad-estancias');
const tipoServicioSelect = document.getElementById('tipo-servicio');
const fechaServicioInput = document.getElementById('fecha-servicio');
const valorHabitacionInput = document.getElementById('valor-habitacion');
const valorFomentoInput = document.getElementById('valor-fomento');
const valorServicioInput = document.getElementById('valor-servicio');
const rfidCheck = document.getElementById('rfid-check'); // Añadido para RFID
const pagadoCheck = document.getElementById('pagado'); // Añadido para el campo "Pagado"
const submitBtn = document.querySelector('button[type="submit"]');
let costos = null;

configurarServicioPorHora();
setFechaActual();
actualizarValorServicio();

tipoServicioSelect.addEventListener('change', actualizarValorServicio);
fechaServicioInput.addEventListener('change', actualizarValorServicio);

// Agregar evento 'submit' al formulario
document.getElementById('form-servicios').addEventListener('submit', async (event) => {
    event.preventDefault(); // Evitar comportamiento por defecto del formulario

    // Objeto con los datos del formulario
    const data = {
        documento: buscarSocioInput.value,
        valorComida: convertirMonedaANumero(valorServicioInput.value),
        cantidad: cantidadEstanciasInput.value,
        tipoServicio: tipoServicioSelect.value,
        fechaServicio: fechaServicioInput.value,
        rfid: rfidCheck.checked,
        pagado: pagadoCheck.checked,
        costoFondosEstanciaId: costos.id
    };
    console.log(data)
    try {
        // Enviar solicitud POST al backend
        const response = await fetch(`/socios/consultarid?documento=${buscarSocioInput.value}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            nombreSocioInput.value = result.nombre;
            buscarSocioInput.select()
            decirNombre("Bienvenido,"+result.nombre)
        } else {
            console.error('Error al agregar el servicio:', response.statusText);
            alert('Error al agregar el servicio');
        }
    } catch (error) {
        console.error('Error en la solicitud:', error);
        alert('Error en la solicitud');
    }
});

async function consultarCostoFondosEstancia() {
    const mesAnio = obtenerMesAnioActual();
    try {
        const response = await fetch(`configurar-costos/consultar/fecha/${mesAnio}`);
        if (!response.ok) {
            throw new Error('No se encontró información para la fecha proporcionada.');
        }
        const data = await response.json();
        costos = data;
        return data;
    } catch (error) {
        alert("Se debe configurar el valor de cada comida para la fecha"+mesAnio)
        console.error('Error al consultar costos fondos estancia:', error);
        return null;
    }
}

async function actualizarValorServicio() {
    const selectedServicio = tipoServicioSelect.value;
    const selectedDate = new Date(fechaServicioInput.value);
    const dayOfWeek = selectedDate.getDay();

    try {
        if (!costos) {
            costos = await consultarCostoFondosEstancia();
        }
        if (!costos) {
            console.error("No se encontraron valores para las estancias.");
            valorServicioInput.value = '';
            return;
        }

        let valor = 0;

        if (selectedServicio === '1') {
            valor = dayOfWeek === 7 ? costos.desayuno : costos.desayuno;
        } else if (selectedServicio === '2') {
            valor = dayOfWeek === 7 ? costos.especial : costos.almuerzo;
        } else if (selectedServicio === '3') {
            valor = costos.cena;
        }

        valorServicioInput.value = formatearMoneda(valor);
    } catch (error) {
        console.error('Error al obtener el valor del servicio:', error);
    }
}

function configurarServicioPorHora() {
    const ahora = new Date();
    const hora = ahora.getHours();
    const minutos = ahora.getMinutes();
    let tipoServicio = 1;

    if (hora >= 1 && (hora < 10 || (hora === 10 && minutos === 0))) {
        tipoServicio = 1;
    } else if (hora === 10 && minutos > 0 || (hora > 10 && hora < 14)) {
        tipoServicio = 2;
    } else {
        tipoServicio = 3;
    }

    tipoServicioSelect.value = tipoServicio;
    return tipoServicio;
}

function setFechaActual() {
    const fechaActual = new Date().toISOString().split('T')[0];
    document.getElementById('fecha-servicio').value = fechaActual;
}

function obtenerMesAnioActual() {
    const fechaActual = new Date();
    const año = fechaActual.getFullYear();
    const mes = String(fechaActual.getMonth() + 1).padStart(2, '0');
    return `${año}-${mes}`;
}

function formatearMoneda(valor) {
    return new Intl.NumberFormat('es-ES', {
        style: 'currency',
        currency: 'COP'
    }).format(valor);
}

function convertirMonedaANumero(valorFormateado) {
    return Number(valorFormateado.replace(/\D/g, ''));
}

function decirNombre(texto) {
    const speech = new SpeechSynthesisUtterance(texto);
    speech.lang = 'es-ES';  // Puedes cambiar el idioma si es necesario
    window.speechSynthesis.speak(speech);
}