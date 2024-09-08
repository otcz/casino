document.addEventListener("DOMContentLoaded", function () {
    const socioDocumento = sessionStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            // Verificar si 'fecha' existe en la respuesta
            if (!data.fecha) {
                console.error('No se encontraron datos de comidas');
                return;
            }
            console.log(data)
            const fechas = Object.entries(data.fecha).sort((a, b) => new Date(b[0]) - new Date(a[0])); // Orden descendente
            const tableBody = document.getElementById('data-rows');

            // Verifica que 'tableBody' exista en el DOM
            if (!tableBody) {
                console.error('Elemento tableBody no encontrado');
                return;
            }

            tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar las nuevas filas

            fechas.forEach(([fecha, cantidades]) => {
                const [desayunos, almuerzos, cenas] = cantidades;
                let estancia = 0;
                let extra = 0;

                // Cálculo de estancia y extras
                if (desayunos > 0 && almuerzos > 0 && cenas > 0) {
                    estancia = 18300;
                    extra += (desayunos > 1 ? (desayunos - 1) * 7000 : 0);
                    extra += (almuerzos > 1 ? (almuerzos - 1) * 10000 : 0);
                    extra += (cenas > 1 ? (cenas - 1) * 7000 : 0);
                } else {
                    extra += desayunos * 7000;
                    extra += almuerzos * 10000;
                    extra += cenas * 7000;
                }

                // Crear la fila para la tabla
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${fecha}</td>
                    <td>${desayunos}</td>
                    <td>${almuerzos}</td>
                    <td>${cenas}</td>
                    <td>$${estancia.toLocaleString('es-CO')}</td>
                    <td>$${extra.toLocaleString('es-CO')}</td>
                `;
                tableBody.appendChild(row);
            });

            // Actualizar los totales en el pie de tabla con formato de miles
            const totalEstanciasElem = document.getElementById('total-estancias');
            const totalExtra = document.getElementById('total-extra');
            const totalEstanciasPaga = document.getElementById('total-estancias-paga');
            const totalExtraPaga = document.getElementById('total-extra-paga');
            const totalPagarElem = document.getElementById('total-pagar');
            const fondoHabitacional = document.getElementById('fondo-habitacional');
            const fondoCasino = document.getElementById('fondo-casino');
            const fomento = document.getElementById('fomento');


            if (totalEstanciasElem && totalPagarElem) {
                totalEstanciasElem.textContent = `$${data.totalEstancias.toLocaleString('es-CO')}`;
                totalExtra.textContent = `$${data.totalExtra.toLocaleString('es-CO')}`;
                totalEstanciasPaga.textContent = `$${data.totalEstanciaPagada.toLocaleString('es-CO')}`;
                totalExtraPaga.textContent = `$${data.totalExtraPagada.toLocaleString('es-CO')}`;

                totalPagarElem.textContent = `$${(data.totalPagar).toLocaleString('es-CO')}`;
                fondoCasino.textContent = `$${(data.fondoCasino).toLocaleString('es-CO')}`;
                fondoHabitacional.textContent = `$${(data.fondoHabitacional).toLocaleString('es-CO')}`;
                fomento.textContent = `$${(data.fomento).toLocaleString('es-CO')}`;
            } else {
                console.error('Elementos para totales no encontrados en el DOM');
            }
        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});
