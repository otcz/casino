document.addEventListener('DOMContentLoaded', () => {
    const navLinks = document.querySelectorAll('.nav-link');
    const sections = document.querySelectorAll('.section');

    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();

            // Eliminar la clase 'active' de todas las secciones
            sections.forEach(section => section.classList.remove('active'));

            // Agregar la clase 'active' a la sección correspondiente
            const targetId = link.getAttribute('href').substring(1);
            document.getElementById(targetId).classList.add('active');

            // Eliminar la clase 'active' de todos los enlaces
            navLinks.forEach(nav => nav.classList.remove('active'));

            // Agregar la clase 'active' al enlace clicado
            link.classList.add('active');
        });
    });
});
