document.getElementById("register-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const grado = document.getElementById("grado").value;
    const nombre = document.getElementById("name").value;
    const documento = document.getElementById("documento").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const errorMessage = document.getElementById("error-message");
    const submitButton = document.querySelector("#register-form button[type='submit']");

    // Limpiar mensaje de error antes de enviar la solicitud
    errorMessage.textContent = "";
    errorMessage.style.display = "none";
    submitButton.disabled = false;

    // Validación de longitud de la contraseña
    if (password.length < 5) {
        errorMessage.textContent = "La contraseña debe tener más de 4 caracteres.";
        errorMessage.style.display = "block";
        errorMessage.style.color = "red";
        return;
    }

    // Validación de contraseñas coincidentes
    if (password !== confirmPassword) {
        errorMessage.textContent = "Las contraseñas no coinciden.";
        errorMessage.style.display = "block";
        errorMessage.style.color = "red";
        return;
    }

    // Enviar la solicitud de registro al backend
    fetch('/socios/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ grado, nombre, documento, password })
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Analiza la respuesta JSON
            } else {
                throw new Error('Error al registrar socio');
            }
        })
        .then(data => {
            // Guardar el nombre del socio en sessionStorage
            sessionStorage.setItem('userName', data.nombre);
            sessionStorage.setItem('documento', data.documento);

            // Mostrar mensaje de registro exitoso en verde
            errorMessage.textContent = "Registro exitoso. Redirigiendo en 3 segundos...";
            errorMessage.style.color = "green";
            errorMessage.style.display = "block";

            // Deshabilitar el botón de envío y cambiar su texto
            submitButton.disabled = true;
            submitButton.textContent = "Registro exitoso";

            // Cronómetro de cuenta regresiva de 3 segundos
            let countdown = 3;
            const countdownInterval = setInterval(() => {
                countdown--;
                errorMessage.textContent = `Registro exitoso. Redirigiendo en ${countdown} segundos...`;

                // Desvanecer el mensaje conforme avanza el tiempo
                errorMessage.style.opacity = 1 - (3 - countdown) * 0.33;

                if (countdown === 0) {
                    clearInterval(countdownInterval);
                    // Redirigir a la página de inicio después de 3 segundos
                    window.location.href = 'index.html';
                }
            }, 1000);
        })
        .catch(error => {
            // Mostrar mensaje de error
            errorMessage.textContent = error.message;
            errorMessage.style.color = "red"; // Asegurar que los mensajes de error sean en rojo
            errorMessage.style.display = "block";
        });
});
