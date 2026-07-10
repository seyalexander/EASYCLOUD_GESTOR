const API_BASE_URL = 'http://localhost:8087/api/v1'; // Puerto actualizado a 8087

const api = {
    // Helper para obtener el token
    getToken() {
        return localStorage.getItem('jwt_token');
    },

    // Helper para configurar headers por defecto
    getHeaders(isFormData = false) {
        const headers = new Headers();
        const token = this.getToken();
        
        if (token) {
            headers.append('Authorization', `Bearer ${token}`);
        }
        
        if (!isFormData) {
            headers.append('Content-Type', 'application/json');
        }
        
        return headers;
    },

    // Manejador centralizado de peticiones
    async request(endpoint, options = {}) {
        const url = `${API_BASE_URL}${endpoint}`;
        
        const fetchOptions = {
            method: options.method || 'GET',
            headers: this.getHeaders(options.isFormData),
            ...options
        };

        if (options.body && !options.isFormData) {
            fetchOptions.body = JSON.stringify(options.body);
        }

        try {
            const response = await fetch(url, fetchOptions);
            
            // Si el token expiró o es inválido, redirigir a login
            if (response.status === 401 || response.status === 403) {
                localStorage.removeItem('jwt_token');
                window.location.href = 'login.html';
                return null;
            }

            const data = await response.json().catch(() => null);
            
            if (!response.ok) {
                throw { status: response.status, data: data || { message: 'Error desconocido' } };
            }
            
            return data;
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    },

    // Métodos específicos
    get(endpoint) { return this.request(endpoint, { method: 'GET' }); },
    post(endpoint, body) { return this.request(endpoint, { method: 'POST', body }); },
    put(endpoint, body) { return this.request(endpoint, { method: 'PUT', body }); },
    patch(endpoint, body) { return this.request(endpoint, { method: 'PATCH', body }); },
    delete(endpoint) { return this.request(endpoint, { method: 'DELETE' }); }
};
