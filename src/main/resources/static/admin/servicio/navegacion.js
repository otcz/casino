// Obtener todos los enlaces del menú
const menuLinks = document.querySelectorAll('.menu-slider a');
// Obtener todas las secciones
const sections = document.querySelectorAll('section');

// Añadir evento click a cada enlace del menú
menuLinks.forEach(link => {
    link.addEventListener('click', function(event) {
        event.preventDefault(); // Evitar navegación por defecto
        const targetSection = this.getAttribute('data-section'); // Obtener la sección a mostrar

        // Ocultar todas las secciones
        sections.forEach(section => {
            section.classList.add('hidden-section');
            section.classList.remove('active-section');
        });

        // Mostrar la sección seleccionada
        document.getElementById(targetSection).classList.add('active-section');
        document.getElementById(targetSection).classList.remove('hidden-section');

        // Remover la clase 'active-link' de todos los enlaces y agregarla solo al enlace seleccionado
        menuLinks.forEach(link => {
            link.classList.remove('active-link');
        });
        this.classList.add('active-link');
    });
});

// Asegurarse de que la sección de Alimentación siempre esté visible al cargar la página
window.onload = function() {
    document.getElementById('alimentacion').classList.add('active-section');
    document.getElementById('alimentacion').classList.remove('hidden-section');
};
