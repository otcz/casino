document.addEventListener("DOMContentLoaded", function () {
    const socioDocumento = sessionStorage.getItem("documento");

    fetch(`/socios/comida/${socioDocumento}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            mapCalendar(9, 2024);
        })
        .catch(error => console.error('Error al obtener las comidas:', error));
});

function mapCalendar(month, year) {
    let dias_total = getDiasEntreFechas(20, month, year, 20, month + 1, year)
    let semana = 1;
    let i_aux = 20;

    for (let i = 0; i <= dias_total; i++) {
        if (i_aux > getDiasEnMes(month, year)) {
            i_aux=1;
            month++;
        }

        if (getDayOfWeek(i_aux, month, year) == "domingo") {
            document.getElementById("semana" + semana + "-" + getDayOfWeek(i_aux, month, year)).textContent = getDayOfWeek(i_aux, month, year) + "/" + i_aux;
            semana++;
        } else {
            document.getElementById("semana" + semana + "-" + getDayOfWeek(i_aux, month, year)).textContent = getDayOfWeek(i_aux, month, year) + "/" + i_aux;
        }
        i_aux++;

    }



}

function getDayOfWeek20(month, year) {
    // Verificar si el mes es válido (debe estar entre 1 y 12)
    if (month < 1 || month > 12) {
        return "Mes inválido";
    }

    // Crear una fecha para el 20 del mes y año proporcionados
    const date = new Date(year, month - 1, 20); // Restar 1 al mes ya que los meses en JavaScript son 0-indexados (0 = Enero, 11 = Diciembre)

    // Array de los nombres de los días de la semana
    const dayNames = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];

    // Obtener el día de la semana
    const dayOfWeek = dayNames[date.getDay()];

    // Retornar el día de la semana
    return dayOfWeek;
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

