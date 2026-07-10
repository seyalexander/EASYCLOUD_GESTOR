document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');
    const btnLogin = document.getElementById('btnLogin');

    // Si ya hay token, redirigir directo al app
    if (localStorage.getItem('jwt_token')) {
        window.location.href = 'index.html';
    }

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Reset estados
        loginError.style.display = 'none';
        btnLogin.classList.add('loading');
        btnLogin.disabled = true;

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            // Simulamos un retraso de red (1 segundo) para que se vea la animación de carga
            await new Promise(resolve => setTimeout(resolve, 1000));
            
            // Generamos un token simulado para poder entrar al Dashboard
            const token = 'dummy_token_simulado';
            
            localStorage.setItem('jwt_token', token);
            localStorage.setItem('username', username || 'Usuario de Prueba');
            
            // Redirigir directamente al Dashboard (index.html)
            window.location.href = 'index.html';
            
        } catch (error) {
            // Esta parte no se ejecutará ahora, pero se deja por estructura
            console.error('Error de login:', error);
            loginError.style.display = 'block';
            
            // Efecto shake opcional
            const card = document.querySelector('.auth-card');
            card.style.animation = 'none';
            card.offsetHeight; // trigger reflow
            card.style.animation = 'shake 0.5s';
        } finally {
            btnLogin.classList.remove('loading');
            btnLogin.disabled = false;
        }
    });
});

// Agregar animación de shake dinámicamente si hay error
const style = document.createElement('style');
style.innerHTML = `
    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
        20%, 40%, 60%, 80% { transform: translateX(5px); }
    }
`;
document.head.appendChild(style);
