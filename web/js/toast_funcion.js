document.addEventListener('DOMContentLoaded', (event) => {
    const toastElement = document.getElementById('miToast');
    if (toastElement) {
        const toast = new bootstrap.Toast(toastElement);
        toast.show();
    }
});