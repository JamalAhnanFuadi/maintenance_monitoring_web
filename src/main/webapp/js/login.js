import { notifyError } from './monitoring.js';
new Vue({
    el: "#login-container",
    data: {
        username: null,
        password: null,
        authenticated: null
    },
    mounted: function () {
        this.session();
    },
    methods: {
        session: function () {
            const vm = this;
            axios.get('/monitoring/rest/authentications/session')
                .then(function (response) {
                    location.href = 'index';
                }).catch(function (error) {
                vm.authenticated = false;
            });
        },
        login: function () {
            const vm = this;
            axios.post(
                '/monitoring/rest/authentications', {
                    username: vm.username,
                    password: vm.password
                }).then(function (response) {
                location.href = 'index';
            }).catch(function (error) {
                notifyError('Failed login', 'Wrong username and/or password');
            });

            vm.password = "";

            if (vm.$refs.username.value == "") {
                vm.$refs.username.focus();
            } else {
                vm.$refs.password.focus();
            }
        }
    }
});