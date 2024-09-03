document.addEventListener("DOMContentLoaded", function() {
    const socioDocumento = sessionStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            console.log(data); // Verifica los datos en la consola


        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});

// Función para obtener el número de semana de una fecha
function getWeekNumber(date) {
    const firstDayOfYear = new Date(date.getFullYear(), 0, 1);
    const days = Math.floor((date - firstDayOfYear) / (24 * 60 * 60 * 1000));
    return Math.ceil((days + firstDayOfYear.getDay() + 1) / 7);
}

// Función para actualizar la tabla
function updateTable(comidas) {
    comidas.forEach(comida => {
        const fecha = new Date(comida.fecha);
        const weekNumber = getWeekNumber(fecha);
        const dayOfWeek = fecha.getDay(); // 0 = Domingo, 1 = Lunes, etc.
        const tipoComida = claseComidaMap[comida.claseComida];

        // Encuentra la tabla de la semana correcta
        const table = Array.from(document.querySelectorAll('h2')).find(h2 => h2.textContent === `SEMANA ${weekNumber}`).nextElementSibling;

        if (table) {
            const row = Array.from(table.querySelectorAll('tbody tr')).find(r => r.querySelector('td').textContent === tipoComida);
            if (row) {
                const cells = row.querySelectorAll('td');
                const cell = cells[dayOfWeek + 1];
                cell.textContent = (parseInt(cell.textContent) || 0) + comida.cantidad;
            }
        }
    });
}

const claseComidaMap = {
    "1": "Desayuno",
    "2": "Almuerzo",
    "3": "Cena"
};
