document.getElementById("login-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const documento = document.getElementById("documento").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");

    // Limpiar el mensaje de error antes de enviar la solicitud
    errorMessage.textContent = "";
    errorMessage.style.display = "none";

    fetch('/socios/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ documento, password })
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Analiza la respuesta JSON
            } else if (response.status === 401) {
                throw new Error('Usuario o contraseña incorrectos');
            } else {
                throw new Error('Error en el inicio de sesión');
            }
        })
        .then(data => {
            // Guardar el nombre del socio en sessionStorage y redirigir a casino.html
            sessionStorage.setItem('userName', data.nombre); // Asegúrate de que 'nombre' sea la propiedad correcta
            sessionStorage.setItem('documento', data.documento); // Asegúrate de que 'nombre' sea la propiedad correcta
            window.location.href = 'casino.html'; // Cambia esta URL si es necesario
        })
        .catch(error => {
            // Mostrar mensaje de error
            errorMessage.textContent = error.message;
            errorMessage.style.display = "block";
        });
});
