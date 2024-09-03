document.addEventListener("DOMContentLoaded", function() {
    const socioDocumento = sessionStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // Verifica los datos en la consola


        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});

