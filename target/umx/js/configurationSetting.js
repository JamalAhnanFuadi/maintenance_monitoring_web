var storedApplicationIdName = 'umx.application.id';
var storedApplicationId = '';

$(document).ready(function () {
    storedApplicationId = localStorage.getItem(storedApplicationIdName) || '';
    initVue();
});

function initVue() {
 new Vue({
        el: '#vue',
        data: {
            applicationId: storedApplicationId,
            applications: [],
            settings: [],
            settingsMap: {
                'name': 'Name',
                'mail.server.host': 'Email Server Host',
                'mail.server.port': 'Email Server Port',
                'mail.sender': 'Email Sender',
                'mail.subject': 'Email Subject',
                'mail.content': 'Email Content',
                'umx.url': 'UMX URL',
                'mail.enable': 'Enable Mail Notification',
                'ce.api.key':'CE API Key',
                'rule.servicenow.application.name':'Application Name to push rules to ServiceNow'
            }
        },
        mounted: function () {
            this.listApplication();
            this.listSetting();
        },
        methods: {
            applicationChanged: function () {
                localStorage.setItem(storedApplicationIdName, this.applicationId);
            },
            listApplication: function () {
                const vm = this;
                axios.get('/umx/system/applications')
                    .then(function (response) {
                        vm.applications = response.data.applications;
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get application list');
                        console.log(error);
                    });
            },
            listSetting: function () {
                const vm = this;
                axios.get('/umx/system/settings')
                    .then(function (response) {
                        vm.settings = response.data.settings;
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get settings data');
                        console.log(error);
                    });
            },
            saveSetting: function () {                
                axios.post('/umx/system/settings', this.settings)
                    .then(function (response) {
                        notifySuccess('Successfully saved');
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to save');
                        console.log(error);
                    });
            },
            resetSetting: function () {
                this.listSetting();
                notifyInfo('Settings reset back to last saved settings.');
            }
        }
    });
}
