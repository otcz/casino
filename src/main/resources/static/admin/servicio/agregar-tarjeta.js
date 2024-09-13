document.getElementById('buscar-btn3').addEventListener('click', function (event) {
    event.preventDefault(); // Evitar la acción por defecto del botón
    // Obtener el número de documento del socio
    const documentoIdentidad = document.getElementById('buscar-socio3').value;

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
                document.getElementById('nombre-socio3').value = data.nombre;
            } else {
                alert('Socio no encontrado.');
            }
        })
        .catch(error => {
            console.error('Error al consultar socio:', error);
            alert('Ocurrió un error al consultar el socio.');
        });
});


document.getElementById('agregar-tarjeta-btn').addEventListener('click', function(event) {
    event.preventDefault(); // Evitar el envío por defecto del formulario

    // Obtener los valores del formulario
    const documento = document.getElementById('buscar-socio3').value;
    const idCard = document.getElementById('numero-tarjeta').value;

    // Validar si el socio y los campos están correctos antes de enviar
    if (!documento) {
        alert('Por favor ingresa el documento de identidad del socio.');
        return;
    }

    // Crear el objeto con los datos para enviar
    const servicioData = {
        documento: documento,
        idCard: idCard,
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
