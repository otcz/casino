document.addEventListener('DOMContentLoaded', () => {
    console.log('DOMContentLoaded event fired');

    // Verifica el contenido de sessionStorage
    console.log('sessionStorage:', sessionStorage);

    // Obtiene el nombre del usuario desde sessionStorage
    const userName = sessionStorage.getItem('userName');
    const userGreeting = document.getElementById('user-name');

    if (userName) {
        userGreeting.textContent = `${userName}`;
    } else {
        userGreeting.textContent = 'Hola, visitante!';
    }
});
