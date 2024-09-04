document.addEventListener("DOMContentLoaded", function () {
    const socioDocumento = sessionStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            mapCalendar(8, 2024);
            updateComida(8, 2024, data)
            totales(8, 2024, data)
            totales_semanales(8, 2024, data)
            suma_semanales()
        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});

function suma_semanales() {
    let total = 0;
    for (let j = 1; j <= 5; j++) {
        for (let k = 1; k <= 3; k++) {
            console.log(document.getElementById("semana" + j + "-" + k + "S").textContent)
            if (parseInt(document.getElementById("semana" + j + "-" + k + "S").textContent) > 0) {
                total = total + parseInt(document.getElementById("semana" + j + "-" + k + "S").textContent);
                document.getElementById("semana" + j + "-total_semanal").textContent = total;
            }
        }
        total = 0;
    }
}

function totales_semanales(month, year, data) {
    let total = 0;
    for (let j = 1; j <= 5; j++) {
        for (let k = 1; k <= 3; k++) {
            for (let i = 1; i <= 7; i++) {
                if (parseInt((document.getElementById("semana" + j + "-" + i + "-" + k).textContent)) > 0) {
                    total = total + parseInt((document.getElementById("semana" + j + "-" + i + "-" + k).textContent));
                    document.getElementById("semana" + j + "-" + k + "S").textContent = total;
                }
                total = 0;
            }

        }

    }
}

function totales(month, year, data) {
    let total = 0;
    for (let j = 1; j <= 5; j++) {
        for (let i = 1; i <= 7; i++) {
            for (let k = 1; k <= 3; k++) {
                if (parseInt((document.getElementById("semana" + j + "-" + i + "-" + k).textContent)) > 0) {
                    total++;
                }
                if (total == 3) {
                    document.getElementById("semana" + j + "-" + i + "" + i).textContent = 1;
                }

            }

            total = 0;
        }
    }
}

function updateComida(month, year, data) {
    for (let j = 1; j <= 5; j++) {
        for (let i = 1; i <= 7; i++) {
            data.forEach(item => {
                if (convertirFecha(document.getElementById("semana" + j + "-" + i).textContent.trim(), month, year) === item.fecha) {
                    document.getElementById("semana" + j + "-" + i + "-" + item.claseComida).textContent = item.cantidad;
                }
            });
        }
    }
}


function mapCalendar(month, year, clase_comida) {
    try {
        let dias_total = getDiasEntreFechas(21, month, year, 20, month + 1, year)
        let semana = 1;
        let i_aux = 21;
        let dia_aux = 1;
        for (let i = 0; i <= dias_total; i++) {
            if (i_aux > getDiasEnMes(month, year)) {
                i_aux = 1;
                month++;
            }
            if (dia_aux > 7) {
                dia_aux = 1;
            }

            if (dia_aux == 7) {
                document.getElementById("semana" + semana + "-" + dia_aux).textContent = getDayOfWeek(i_aux, month, year) + "." + i_aux;
                semana++;
            } else {
                document.getElementById("semana" + semana + "-" + dia_aux).textContent = getDayOfWeek(i_aux, month, year) + "." + i_aux;
            }
            i_aux++;
            dia_aux++;
        }
    } catch (e) {
        console.log("error")
    }

}

function getDayOfWeek(day, month, year) {
    // Verificar si el mes es válido (debe estar entre 1 y 12)
    if (month < 1 || month > 12) {
        return "Mes inválido";
    }

    // Verificar si el día es válido (debe estar entre 1 y 31)
    if (day < 1 || day > 31) {
        return "Día inválido";
    }

    // Crear una fecha con el día, mes y año proporcionados
    const date = new Date(year, month - 1, day); // Restar 1 al mes ya que los meses en JavaScript son 0-indexados (0 = Enero, 11 = Diciembre)

    // Array de los nombres de los días de la semana
    const dayNames = ["domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"]

    // Obtener el día de la semana
    const dayOfWeek = dayNames[date.getDay()];

    // Retornar el día de la semana
    return dayOfWeek;
}

function getDiasEnMes(month, year) {
    // Verificar si el mes es válido (debe estar entre 1 y 12)
    if (month < 1 || month > 12) {
        return "Mes inválido";
    }

    // Crear una fecha para el primer día del siguiente mes
    // y obtener el último día del mes actual
    const fecha = new Date(year, month, 0); // Mes es 0-indexado (0 = Enero, 11 = Diciembre)

    // Obtener el número de días en el mes
    const diasEnMes = fecha.getDate();

    return diasEnMes;
}

function getDiasEntreFechas(diaInicio, mesInicio, anioInicio, diaFin, mesFin, anioFin) {
    // Crear objetos Date para las fechas de inicio y fin
    const fechaInicio = new Date(anioInicio, mesInicio - 1, diaInicio);
    const fechaFin = new Date(anioFin, mesFin - 1, diaFin);

    // Calcular la diferencia en milisegundos entre ambas fechas
    const diferenciaMilisegundos = fechaFin - fechaInicio;

    // Convertir la diferencia de milisegundos a días
    const diasEntreFechas = Math.floor(diferenciaMilisegundos / (1000 * 60 * 60 * 24));

    // Retornar los días entre las fechas
    return diasEntreFechas;
}

function convertirFecha(formatoOriginal, mes, year) {
    // Divide la cadena en partes utilizando el punto como delimitador
    let partesFecha = formatoOriginal.split('.');

    // Obtén el día de las partes divididas
    let dia = partesFecha[1];

    // Asegúrate de que el mes esté en formato de dos dígitos
    let mesFormateado = mes.toString().padStart(2, '0');

    // Forma la fecha en formato YYYY-MM-DD
    let fechaFormateada = `${year}-${mesFormateado}-${dia}`;

    return fechaFormateada;
}

function sumarDosNumeros(num1, num2) {
    try {
        // Intenta convertir los argumentos a números
        let valor1 = parseFloat(num1);
        let valor2 = parseFloat(num2);

        // Verifica si ambos valores son números válidos
        if (isNaN(valor1) || isNaN(valor2)) {
            return 0;
        }

        // Retorna la suma de los dos números
        return valor1 + valor2;
    } catch (error) {
        return 0;
    }
}

function contarEstancias(texto) {
    if (texto.trim() === "") {
        return 1;
    }
}

