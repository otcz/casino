document.addEventListener('DOMContentLoaded', () => {
    // Obtiene el nombre del usuario desde sessionStorage
    const userName = sessionStorage.getItem('userName');
    const userGreeting = document.getElementById('user-name');

    if (userName) {
        userGreeting.textContent = `${userName}`;
    } else {
        userGreeting.textContent = 'Hola, visitante!';
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const currentMonthElement = document.getElementById("currentMonth");

    let currentDate = new Date();
    let currentMonth = currentDate.getMonth();
    let currentYear = ", "+currentDate.getFullYear();

    const monthNames = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];

    function updateMonth() {
        currentMonthElement.textContent = `${monthNames[currentMonth]} ${currentYear}`;
    }

    window.changeMonth = function(direction) {
        currentMonth += direction;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        } else if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        updateMonth();
    };

    updateMonth(); // Set the initial month
});



// Ejemplo de uso

