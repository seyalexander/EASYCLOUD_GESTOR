// Router & App Shell Logic
document.addEventListener('DOMContentLoaded', () => {
    // 1. Verificación básica de sesión
    const token = localStorage.getItem('jwt_token');
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // Opcional: mostrar nombre del usuario desde localStorage si existe
    const username = localStorage.getItem('username');
    if (username) {
        document.getElementById('userNameDisplay').textContent = username;
        document.getElementById('userAvatar').textContent = username.charAt(0).toUpperCase();
    }

    // 2. Elementos del DOM
    const sidebar = document.getElementById('sidebar');
    const toggleSidebarBtn = document.getElementById('toggleSidebar');
    const menuItems = document.querySelectorAll('.menu-item');
    const appContent = document.getElementById('app-content');
    const btnLogout = document.getElementById('btnLogout');

    // 3. Toggle Sidebar (Mobile/Desktop)
    toggleSidebarBtn.addEventListener('click', () => {
        sidebar.classList.toggle('open');
    });

    // 4. Logout
    btnLogout.addEventListener('click', () => {
        localStorage.removeItem('jwt_token');
        localStorage.removeItem('username');
        window.location.href = 'login.html';
    });

    // 5. Router: Carga de vistas dinámicas
    const loadView = async (route) => {
        try {
            // Mostrar loader
            appContent.innerHTML = `
                <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
                    <div class="loader" style="display: block; border-color: var(--primary); border-top-color: transparent; width: 40px; height: 40px;"></div>
                </div>
            `;
            
            // Simular un poco de delay para el efecto (opcional)
            await new Promise(r => setTimeout(r, 200));

            // Fetch del fragmento HTML
            const response = await fetch(`views/${route}.html`);
            if (!response.ok) {
                if (response.status === 404) {
                    appContent.innerHTML = `
                        <div class="card" style="text-align: center; padding: 4rem 2rem;">
                            <i class="fa-solid fa-person-digging text-primary" style="font-size: 4rem; margin-bottom: 1rem;"></i>
                            <h2>Módulo en construcción</h2>
                            <p class="text-muted">La vista para <strong>${route}</strong> aún no está implementada.</p>
                        </div>
                    `;
                    return;
                }
                throw new Error('Error al cargar la vista');
            }

            const html = await response.text();
            appContent.innerHTML = html;

            // Ejecutar scripts específicos del módulo si los hay (esto es opcional para vanilla JS)
            // Una mejor manera es disparar un evento custom:
            document.dispatchEvent(new CustomEvent('viewLoaded', { detail: route }));

        } catch (error) {
            console.error('Error:', error);
            appContent.innerHTML = `
                <div class="card" style="border-color: var(--danger); text-align: center; padding: 3rem;">
                    <h3 class="text-danger">Error al cargar la vista</h3>
                    <p>Por favor, intente de nuevo.</p>
                </div>
            `;
        }
    };

    // 6. Navegación
    menuItems.forEach(item => {
        item.addEventListener('click', (e) => {
            // Actualizar clase activa
            menuItems.forEach(i => i.classList.remove('active'));
            e.currentTarget.classList.add('active');

            // Cerrar sidebar en móvil si está abierto
            if (window.innerWidth <= 768) {
                sidebar.classList.remove('open');
            }

            // Cargar ruta
            const route = e.currentTarget.dataset.route;
            if (route) {
                loadView(route);
            }
        });
    });

    // Cargar la vista inicial por defecto (dashboard)
    loadView('dashboard');
});
