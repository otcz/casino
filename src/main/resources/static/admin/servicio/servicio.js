document.addEventListener('DOMContentLoaded', function () {
    // Elementos del formulario
    const buscarBtn = document.getElementById('buscar-btn');
    const buscarSocioInput = document.getElementById('buscar-socio');
    const nombreSocioInput = document.getElementById('nombre-socio');
    const cantidadEstanciasInput = document.getElementById('cantidad-estancias');
    const tipoServicioSelect = document.getElementById('tipo-servicio');
    const fechaServicioInput = document.getElementById('fecha-servicio');
    const habilitarHabitacionCheckbox = document.getElementById('habilitar-habitacion');
    const valorHabitacionInput = document.getElementById('valor-habitacion');
    const habilitarFomentoCheckbox = document.getElementById('habilitar-fomento');
    const valorFomentoInput = document.getElementById('valor-fomento');
    const valorServicioInput = document.getElementById('valor-servicio');
    const submitBtn = document.querySelector('button[type="submit"]');

    // Función para habilitar o deshabilitar campos
    function toggleFields(enable) {
        cantidadEstanciasInput.disabled = !enable;
        tipoServicioSelect.disabled = !enable;
        fechaServicioInput.disabled = !enable;
        habilitarHabitacionCheckbox.disabled = !enable;
        habilitarFomentoCheckbox.disabled = !enable;
        valorServicioInput.disabled = !enable;  // Habilitar o deshabilitar valor del servicio
        submitBtn.disabled = !enable;

        if (!enable) {
            nombreSocioInput.value = '';
            valorHabitacionInput.disabled = true;
            valorFomentoInput.disabled = true;
        }
    }

    // Lógica para buscar socio
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

                    // Actualizar el valor del servicio al encontrar un socio
                    actualizarValorServicio();

                    // Lógica adicional si se habilitan las opciones adicionales
                    habilitarHabitacionCheckbox.addEventListener('change', function () {
                        valorHabitacionInput.disabled = !this.checked;
                    });

                    habilitarFomentoCheckbox.addEventListener('change', function () {
                        valorFomentoInput.disabled = !this.checked;
                    });
                } else {
                    alert('Socio no encontrado.');
                    toggleFields(false); // Deshabilitar campos
                }
            })
            .catch(error => {
                console.error('Error al consultar socio:', error);
                alert('Ocurrió un error al consultar el socio.');
                toggleFields(false); // Deshabilitar campos
            });
    });

    // Inicialmente deshabilitar todos los campos
    toggleFields(false);

    // Establecer la fecha actual en el campo de fecha
    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // Mes de 1 a 12
    const dd = String(today.getDate()).padStart(2, '0'); // Día de 1 a 31

    const currentDate = `${yyyy}-${mm}-${dd}`;
    fechaServicioInput.value = currentDate;

    // Función para configurar el combobox para que siempre inicie en "Desayuno"
    function configurarServicioPorHora() {
        tipoServicioSelect.value = '1'; // Desayuno por defecto
    }

    // Función para actualizar el valor del servicio basado en el tipo de servicio seleccionado y el día de la semana
    function actualizarValorServicio() {
        const selectedServicio = tipoServicioSelect.value;
        const selectedDate = new Date(fechaServicioInput.value);
        const dayOfWeek = selectedDate.getDay(); // 0 (Domingo) a 6 (Sábado)

        let valor = 0;

        if (selectedServicio === '1') { // Desayuno
            valor = dayOfWeek === 7 ? 8000 : 7000;
        } else if (selectedServicio === '2') { // Almuerzo
            valor = dayOfWeek === 7 ? 25000 : 10000;
        } else if (selectedServicio === '3') { // Cena
            valor = 7000; // Valor fijo para la cena
        }

        valorServicioInput.value = valor.toLocaleString(); // Actualizar el valor del servicio
    }

    // Configurar el combobox y el valor del servicio al cargar la página
    configurarServicioPorHora();
    actualizarValorServicio();

    // Actualizar el valor del servicio cuando se cambia el tipo de servicio o la fecha
    tipoServicioSelect.addEventListener('change', actualizarValorServicio);
    fechaServicioInput.addEventListener('change', actualizarValorServicio);
});
