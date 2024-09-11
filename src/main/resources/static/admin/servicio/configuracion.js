// Obtener todos los campos de entrada
const inputs = [
    document.getElementById('fondoCasino'),
    document.getElementById('fomento'),
    document.getElementById('fondoHabitacional'),
    document.getElementById('desayuno'),
    document.getElementById('almuerzo'),
    document.getElementById('cena'),
    document.getElementById('estancia'),
    document.getElementById('especial')
];

// Función para formatear el valor en moneda colombiana para mostrar
function formatearMoneda(valor) {
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', minimumFractionDigits: 0 }).format(valor);
}

// Evento para permitir solo números y formatear el valor
function manejarInput(event) {
    // Mantener el valor sin formateo de moneda, pero convertir a número para la base de datos
    let valor = this.value.replace(/\D/g, ''); // Eliminar caracteres no numéricos

    if (valor !== '') {
        // Mostrar el valor formateado como moneda al usuario
        this.value = formatearMoneda(Number(valor));
    }
}

// Asignar el evento a cada input para formateo en pantalla
inputs.forEach(function(input) {
    input.addEventListener('input', manejarInput);
});



// Envío de datos eliminando caracteres no numéricos
document.getElementById('configForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Capturar los valores del formulario directamente por id
    const configData = {
        fondoCasino: parseFloat(document.getElementById('fondoCasino').value.replace(/\D/g, '')) || 0,
        fomento: parseFloat(document.getElementById('fomento').value.replace(/\D/g, '')) || 0,
        fondoHabitacional: parseFloat(document.getElementById('fondoHabitacional').value.replace(/\D/g, '')) || 0,
        desayuno: parseFloat(document.getElementById('desayuno').value.replace(/\D/g, '')) || 0,
        almuerzo: parseFloat(document.getElementById('almuerzo').value.replace(/\D/g, '')) || 0,
        cena: parseFloat(document.getElementById('cena').value.replace(/\D/g, '')) || 0,
        estancia: parseFloat(document.getElementById('estancia').value.replace(/\D/g, '')) || 0,
        especial: parseFloat(document.getElementById('especial').value.replace(/\D/g, '')) || 0,
        mesAnio: document.getElementById('mesAnio').value // Esto será "YYYY-MM"
    };

    // Imprimir para verificar los valores
    console.log(configData);

    // Se utiliza el mismo endpoint, ya que el backend decidirá si crear o actualizar
    fetch('configurar-costos/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(configData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al guardar la configuración.');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('responseMessage').textContent = 'Configuración actualizada con éxito.';
        })
        .catch(error => {
            document.getElementById('responseMessage').textContent = 'Error al actualizar la configuración.';
        });
});

