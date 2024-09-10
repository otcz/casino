document.addEventListener('DOMContentLoaded', function () {
    // Elementos del formulario
    const buscarBtn = document.getElementById('buscar-btn2');
    const buscarSocioInput = document.getElementById('buscar-socio2');
    const nombreSocioInput = document.getElementById('nombre-socio2');

    const valorHabitacion = document.getElementById('valor-habitacion');
    const valorCasino = document.getElementById('valor-casino');
    const valorFomento = document.getElementById('valor-fomento');

    const submitBtn = document.querySelector('button[type="submit"]');

    // Función para habilitar o deshabilitar campos
    function toggleFields(enable) {
        submitBtn.disabled = !enable;
        if (!enable) {
            nombreSocioInput.value = '';
        }
    }

    // Función para asignar los valores a los campos de valor
    function asignarValores(data) {
        // Validar los campos antes de asignarlos
        valorHabitacion.value = formatearMoneda(data.fondoHabitacional || 0);
        valorCasino.value = formatearMoneda(data.fondoCasino || 0);
        valorFomento.value = formatearMoneda(data.fomento || 0);
    }


    // Función para formatear el valor en moneda colombiana
    function formatearMoneda(valor) {
        return new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0
        }).format(valor).replace(/\D00$/, '');
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
                    console.log(data);
                    nombreSocioInput.value = data.nombre;
                    toggleFields(true); // Habilitar campos
                    asignarValores(data); // Asignar valores a los campos de fondo
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

    // Eventos para formatear los valores de los campos en moneda colombiana
    const inputs = [valorHabitacion, valorCasino, valorFomento];

    inputs.forEach(input => {
        input.addEventListener('input', function () {
            let valor = this.value.replace(/\D/g, '');
            if (valor !== '') {
                this.value = formatearMoneda(Number(valor));
            }
        });
    });
});
