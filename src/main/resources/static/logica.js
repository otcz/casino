
document.addEventListener('DOMContentLoaded', () => {
    // Selecciona todos los enlaces de navegación y las secciones
    const navLinks = document.querySelectorAll('.nav-link');
    const sections = document.querySelectorAll('.section');

    // Agrega un event listener a cada enlace de navegación
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault(); // Previene la acción por defecto del enlace

            // Elimina la clase 'active' de todas las secciones
            sections.forEach(section => section.classList.remove('active'));

            // Obtiene el ID de la sección objetivo desde el atributo 'href' del enlace
            const targetId = link.getAttribute('href').substring(1);
            const targetSection = document.getElementById(targetId);

            // Verifica si la sección objetivo existe antes de agregar la clase 'active'
            if (targetSection) {
                targetSection.classList.add('active');
            }

            // Elimina la clase 'active' de todos los enlaces
            navLinks.forEach(nav => nav.classList.remove('active'));

            // Agrega la clase 'active' al enlace clicado
            link.classList.add('active');
        });
    });
    // Obtiene el nombre del usuario desde sessionStorage
    const userName = sessionStorage.getItem('userName');
    const userGreeting = document.getElementById('user-greeting');

    if (userName) {
        userGreeting.textContent = `Hola, ${userName}!`;
    } else {
        userGreeting.textContent = 'Hola, visitante!';
    }
});
