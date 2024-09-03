document.getElementById('form-servicios').addEventListener('submit', function(event) {
    event.preventDefault();  // Evitar que se recargue la página al enviar el formulario

    // Obtener los valores del formulario
    const socioId = document.getElementById('buscar-socio').value;
    const tipoServicio = document.getElementById('tipo-servicio').value;
    const valorServicio = document.getElementById('valor-servicio').value;
    const fechaServicio = document.getElementById('fecha-servicio').value;
    const cantidadEstancias = document.getElementById('cantidad-estancias').value;
    const pago = false; // O el valor que corresponda según tu lógica

    // Crear el objeto ComidaDTO para enviar al backend
    const comidaDTO = {
        socioId: socioId,
        claseComida: tipoServicio,  // Tipo de servicio como número
        valorComida: parseFloat(valorServicio),
        fecha: fechaServicio,
        cantidad: parseInt(cantidadEstancias),
        pago: pago
    };

    // Enviar la solicitud POST
    fetch('/comidas/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(comidaDTO),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Comida creada con éxito:', data);
            alert("RESPUESTA->"+data)
            // Aquí podrías mostrar un mensaje de éxito o redirigir a otra página
        })
        .catch((error) => {
            console.error('Error al crear comida:', error);
            // Aquí podrías mostrar un mensaje de error
        });
});
