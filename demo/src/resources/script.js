document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.onsubmit = function(event) {
        const login = document.querySelector('input[name="login"]').value;
        const pass = document.querySelector('input[name="pass"]').value;

        if (login.trim() === "" || pass.trim() === "") {
            alert("Пожалуйста, заполните все поля!");
            event.preventDefault(); // Останавливает отправку формы
            return false;
        }

        console.log("Форма отправляется для пользователя: " + login);
    };
});