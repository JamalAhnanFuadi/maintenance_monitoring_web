import {notifyError} from './monitoring.js';

new Vue({
    el: "#landingPage",
    data: {
        fullname: 'Guest' // Default value
    },
    mounted: function () {
        this.session();
        this.retrieveUser();
    },
    methods: {
        session: function () {
            const vm = this;
            axios.get('/monitoring/rest/authentications/session')
                .then(function (response) {
                    if (response.status === 200) {
                        vm.authenticated = true; // or some other logic
                    }
                }).catch(function (error) {
                notifyError('Unauthorized', '');
                location.href = 'login';
                vm.authenticated = false;
            });
        },
        retrieveUser: function () {
            const vm = this;
            axios.get(
                '/monitoring/rest/authentications/profile')
                .then(function (response) {
                    if (response.status === 200) {
                        let data = response.data;
                        vm.fullname = data.fullname; // Update Vue data
                        sessionStorage.setItem("fullname", data.fullname); // Save to sessionStorage if needed
                    }
                }).catch(function (error) {
                notifyError('Error', error);
                console.log(error)
            });
        }
    }
});