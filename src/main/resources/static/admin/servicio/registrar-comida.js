let costos = null;  // Inicializamos la variable

const rfidCheck = document.getElementById('rfid-check');
document.addEventListener('DOMContentLoaded', async function () {
    const buscarBtn = document.getElementById('buscar-btn');
    const buscarSocioInput = document.getElementById('buscar-socio');
    const nombreSocioInput = document.getElementById('nombre-socio');
    const cantidadEstanciasInput = document.getElementById('cantidad-estancias');
    const tipoServicioSelect = document.getElementById('tipo-servicio');
    const fechaServicioInput = document.getElementById('fecha-servicio');
    const valorHabitacionInput = document.getElementById('valor-habitacion');
    const valorFomentoInput = document.getElementById('valor-fomento');
    const valorServicioInput = document.getElementById('valor-servicio');
    const submitBtn = document.querySelector('button[type="submit"]');

    function toggleFields(enable) {
        cantidadEstanciasInput.disabled = !enable;
        tipoServicioSelect.disabled = !enable;
        fechaServicioInput.disabled = !enable;
        submitBtn.disabled = !enable;

        if (!enable) {
            nombreSocioInput.value = '';
            valorHabitacionInput.disabled = true;
            valorFomentoInput.disabled = true;
        }
    }

    buscarBtn.addEventListener('click', function () {
        const documentoIdentidad = buscarSocioInput.value.trim();

        if (documentoIdentidad === '') {
            alert('Por favor ingrese un documento de identidad válido.');
            return;
        }

        fetch(`/socios/consultarid?documento=${documentoIdentidad}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Socio no encontrado');
                }
                return response.json();
            })
            .then(data => {
                if (data) {
                    nombreSocioInput.value = data.nombre;
                    toggleFields(true); // Habilitar campos
                    actualizarValorServicio();
                } else {
                    alert('Socio no encontrado.');
                    toggleFields(false);
                }
            })
            .catch(error => {
                console.error('Error al consultar socio:', error);
                alert('Ocurrió un error al consultar el socio.');
                toggleFields(false);
            });
    });

    toggleFields(false);

    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const dd = String(today.getDate()).padStart(2, '0');
    const currentDate = `${yyyy}-${mm}-${dd}`;
    fechaServicioInput.value = currentDate;

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

    function formatearMoneda(valor) {
        return new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0
        }).format(valor).replace(/\D00$/, '');
    }

    valorServicioInput.addEventListener('input', function () {
        let valor = this.value.replace(/\D/g, '');

        if (valor !== '') {
            this.value = formatearMoneda(Number(valor));
        }
    });

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

    configurarServicioPorHora();
    await actualizarValorServicio();

    tipoServicioSelect.addEventListener('change', actualizarValorServicio);
    fechaServicioInput.addEventListener('change', actualizarValorServicio);

    document.getElementById('form-servicios').addEventListener('input', function () {
        const socioId = document.getElementById('buscar-socio').value;
        const valorComida = document.getElementById('valor-servicio').value;
        const cantidadEstancias = document.getElementById('cantidad-estancias').value;
        const fechaServicio = document.getElementById('fecha-servicio').value;

        if (socioId && valorComida && cantidadEstancias && fechaServicio) {
            document.getElementById('agregar-servicio-btn').disabled = false;
        } else {
            document.getElementById('agregar-servicio-btn').disabled = true;
        }
    });

    document.getElementById('form-servicios').addEventListener('submit', async function (e) {
        e.preventDefault();

        const socioId = document.getElementById('buscar-socio').value;
        const cantidad = document.getElementById('cantidad-estancias').value;
        const claseComida = document.getElementById('tipo-servicio').value;
        const fecha = document.getElementById('fecha-servicio').value;
        const valorComida = document.getElementById('valor-servicio').value;
        const pago = document.getElementById('pagado').checked;

        if (!costos) {
            costos = await consultarCostoFondosEstancia();
        }

        const data = {
            costoFondosEstanciaId: costos.id,
            socioId: socioId,
            cantidad: cantidad,
            claseComida: claseComida,
            fecha: fecha,
            valorComida: convertirMonedaANumero(valorComida),
            pago: pago
        };

        fetch('/comidas/crear', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                decirNombre("Bienvenido, "+document.getElementById('nombre-socio').value);
            })
            .catch(error => {
                console.error('Error al agregar servicio:', error);
                alert('Hubo un error al agregar el servicio');
            });
    });

    let isProcessing = false; // Variable de bloqueo para evitar repeticiones


    buscarSocioInput.addEventListener('change', function () {
        if (isProcessing) return; // Evitar que el evento se dispare de nuevo mientras se está procesando
        isProcessing = true; // Bloquear el evento durante el procesamiento

        const documentoIdentidad = buscarSocioInput.value.trim();

        // Verificar si el campo tiene un valor suficiente, por ejemplo, longitud mínima de caracteres
        if (documentoIdentidad.length >= 8 && rfidCheck.checked) {  // Supongamos que el documento tiene al menos 8 dígitos
            fetch(`/socios/consultarid?documento=${documentoIdentidad}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Socio no encontrado');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data) {
                        nombreSocioInput.value = data.nombre;
                        toggleFields(true); // Habilitar campos
                        actualizarValorServicio();

                        return fetch('/comidas/crear', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                socioId: documentoIdentidad,
                                claseComida: tipoServicioSelect.value,
                                cantidad: cantidadEstanciasInput.value,
                                fecha: fechaServicioInput.value,
                                valorComida: convertirMonedaANumero(valorServicioInput.value),
                                pago: document.getElementById('pagado').checked,
                                costoFondosEstanciaId: costos.id,
                            })
                        });
                    } else {
                        alert('Socio no encontrado.');
                        toggleFields(false);
                    }
                })
                .then(response => response.json())
                .then(data => {
                    if (data) {
                        decirNombre("Bienvenido, " + document.getElementById('nombre-socio').value);

                        setTimeout(() => {
                            buscarSocioInput.value = '';  // Limpia el campo
                            buscarSocioInput.focus();     // Enfoca el campo para una nueva entrada
                        }, 0);
                    }
                })
                .catch(error => {
                    console.error('Error al procesar:', error);
                    alert('Ocurrió un error al procesar los datos.');
                    toggleFields(false);
                })
                .finally(() => {
                    // Reactivar el evento después de finalizar el procesamiento
                    isProcessing = false;
                });
        } else {
            alert('Por favor, ingrese un documento válido.');
            isProcessing = false; // Desbloquear si no cumple con las condiciones
        }
    });



});

// Funciones auxiliares
function obtenerMesAnioActual() {
    const fechaActual = new Date();
    const año = fechaActual.getFullYear();
    const mes = String(fechaActual.getMonth() + 1).padStart(2, '0');
    return `${año}-${mes}`;
}

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
        console.error('Error al consultar costos fondos estancia:', error);
        return null;
    }
}

function convertirMonedaANumero(valorFormateado) {
    return Number(valorFormateado.replace(/\D/g, ''));
}

function decirNombre(texto) {
    const speech = new SpeechSynthesisUtterance(texto);
    speech.lang = 'es-ES';  // Puedes cambiar el idioma si es necesario
    window.speechSynthesis.speak(speech);
}
