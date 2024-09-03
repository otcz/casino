document.addEventListener("DOMContentLoaded", function() {

    const socioDocumento = localStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // Puedes ver los datos en la consola para verificar

            // Aquí puedes procesar y mostrar los datos en tu HTML
            const tableBody = document.querySelector(".availability-table tbody");

            data.forEach(comida => {
                let row = document.createElement("tr");

                // Puedes personalizar estas celdas basándote en la estructura de tu tabla
                let desayunoCell = document.createElement("td");
                desayunoCell.textContent = comida.claseComida === "DESAYUNO" ? comida.cantidad : "";

                let almuerzoCell = document.createElement("td");
                almuerzoCell.textContent = comida.claseComida === "ALMUERZO" ? comida.cantidad : "";

                let cenaCell = document.createElement("td");
                cenaCell.textContent = comida.claseComida === "CENA" ? comida.cantidad : "";

                row.appendChild(desayunoCell);
                row.appendChild(almuerzoCell);
                row.appendChild(cenaCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});
