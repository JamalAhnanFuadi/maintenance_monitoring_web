var storedApplicationIdName = 'umx.application.id';
var storedApplicationId = '';
var main; 
$(document).ready(function () {

    // Display Notification (called upon successful Add Campaign)
    let hash = String(window.location.hash);
    if (hash.localeCompare('') !== 0) {
        let message = '';
        if (hash.localeCompare('#success') === 0) {
            message = 'Successfully imported application';
        }

        notifySuccess(message);
        removeHash();
    }

    storedApplicationId = localStorage.getItem(storedApplicationIdName) || '';
    initVue();

    // event listeners
    $('#addApplicationModal').on('show.bs.modal', function () {
        $(':input', '#addApplicationModal form .form-group').each(function () {
            if (!['newMailSubject', 'newMailBody'].includes(this.id)) this.value = '';
        });
        
        // Populate Select
        main.servers.map(function(server) {
          $('#newServerSelect').append($('<option>', {
            value: server.id,
            text: server.name
        }));       
        });
    });
    
    $('#newServerSelect').change(function(){
      var serverId = $(this).val();
      
      // Clear Configuration Select
      $('#newConfigurationSelect').children().remove().end()
      .append('<option disabled selected value=>Select a Configuration</option>') ;

      main.servers.forEach(function(server){
        // Get Configurations if serverId matches
        if(serverId === server.id){
          
          server.configurations.map(function(config){
            $('#newConfigurationSelect').append($('<option>', {
              value: config,
              text: config
          }));   
          });
          return;
        }
      });
    });

    $('#addApplicationModal').on('hidden.bs.modal', function () {
      
      main.newApplication.name = '';
      main.newApplication.server = '';
      main.newApplication.configName = '';
      main.newApplication.emails = [];
      main.newApplication.attribtes = [];
      main.newApplication.mailSubject = main.defaultMailSubject;
      main.newApplication.mailBody = main.defaultMailBody;
      
        // Clear Server Select
        $('#newServerSelect').children().remove().end()
        .append('<option disabled selected value=>Select a Server</option>') ;
        
        // Clear Configuration Select
        $('#newConfigurationSelect').children().remove().end()
        .append('<option disabled selected value=>Select a Configuration</option>') ;
    });

    $('#updateApplicationModal').on('hidden.bs.modal', function () {
        const resetValue = {};
        resetValue.attributes = [];
        resetValue.configurationName = '';
        resetValue.id = '';
        resetValue.name = '';
        resetValue.recipients = [];

        main.setData(resetValue);
    });

    $('#addApplicationModal').on('shown.bs.modal', function () {
        $('#addApplicationModal form .form-group:first-child input').focus();
    });
});

function initVue() {
   main = new Vue({
        el: '#vue',
        data: {
            applicationId: storedApplicationId,
            applicationName: '',
            server : '',
            data: null,
            dataTable: null,
            fields: ['Recipients', 'Attributes'],
            applicationData: [],
            
            newApplication: {
              name :'',
              server : '',
              configName:'',
              email: '',
              emails: [],
              attribute: '',
              attributes :[],
              mailSubject : '',
              mailBody: ''
            },
            
            newApplicationName: '',
            newApplicationServer: '',
            newApplicationConfigName: '',
            newApplicationEmail: '',
            newApplicationEmails: [],
            newApplicationAttribute: '',
            newApplicationAttributes: [],
            newMailBody: '',
            newMailSubject: '',
            
            applications: [],
            attributes: '',
            attributess: [],
            recipients: '',
            recipientss: [],
            configurationName: '',
            mailSubject: '',
            mailBody: '',
            defaultMailSubject: '',
            defaultMailBody: '',
            settings: [],
            servers: [],
            emailValidation: [{
                classes: 'emailValidationInvalid',
                rule: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
                disableAdd: true
            }]
        },
        mounted: function () {
            const vm = this;
            vm.listServers();
            vm.listApplication();
            vm.getDefaultSettings();
        },
        methods: {
            applicationChanged: function () {
                const vm = this;
                localStorage.setItem(storedApplicationIdName, this.applicationId);
                this.applicationName = $('#application-selection option:selected').text();
                vm.list();
            },
            listServers: function(){
              const vm = this;
              axios.get('/umx/system/servers')
                  .then(function (response) {
                    vm.servers = response.data.servers;
                  });
            },
            listApplication: function () {
                const vm = this;
                axios.get('/umx/system/applications')
                    .then(function (response) {
                        const localApplications = JSON.parse(JSON.stringify(response.data.applications));
                        vm.applications.length = 0;
                        localApplications.forEach(function (application, index) {
                            vm.$set(vm.applications, index, application);
                        });
                        vm.list();
                    }).catch(function (error) {
                        notifyError('Network Error', 'failed to get application list');
                        console.log(error);
                    });
            },
            add: function () {
                const vm = this;
                const json = {
                    name: vm.newApplication.name,
                    configurationName: vm.newApplication.configName,
                    recipients: vm.simpleStringArray(vm.newApplication.emails),
                    attributes: vm.simpleStringArray(vm.newApplication.attributes),
                    mailBody: vm.newApplication.mailBody,
                    mailSubject: vm.newApplication.mailSubject,
                    server : {
                      id : vm.newApplication.server
                    }
                };

                axios.post('/umx/system/applications', json)
                    .then(function (response) {
                      
                      vm.newApplication = {
                              name :'',
                              server : '',
                              configName:'',
                              email: '',
                              emails: [],
                              attribute: '',
                              attributes :[],
                              mailSubject : vm.defaultMailSubject,
                              mailBody: vm.defaultMailBody
                            };
                        vm.listApplication();
                        notifySuccess('Successfully added new application');
                        
                    }).catch(function (error) {
                        console.log(error);
                        const errorResponseCode = error.response.status;
                        if(errorResponseCode == 409){
                          notifyError('Application Name Conflict', `An application '${vm.newApplicationName}' already exists`);
                        }else{
                          notifyError('Network Error', 'Failed to add new application');
                        }
                    }).then(function(){
                      $('#addApplicationModal').modal('hide');
                    });
            },
            remove: function () {
                const vm = this;
                axios.delete(
                    '/umx/system/applications/' + vm.applicationId).then(function (response) {
                    vm.listApplication();

                    // Check if App deleted is selected
                    if (vm.applicationId == localStorage.getItem('umx.application.id')) {
                        localStorage.setItem('umx.application.id', '');
                    }
                    
                    notifySuccess('Successfully removed application');
                }).catch(function (error) {
                    notifyError('Network Error', 'Failed to get delete application');
                    console.log(error);
                }).then(function(){
                  $('#removeApplicationModal').modal('hide');
                });
            },
            updateApplication: function () {
                const vm = this;
                const json = {
                    name: vm.applicationName,
                    configurationName: vm.configurationName,
                    recipients: vm.simpleStringArray(vm.recipientss),
                    attributes: vm.simpleStringArray(vm.attributess),
                    mailBody: vm.mailBody,
                    mailSubject: vm.mailSubject,
                    server : {
                      id : vm.server.id
                    }
                };
                axios.put(
                    '/umx/system/applications/' + vm.applicationId, json
                ).then(function (response) {
                    vm.listApplication();
                    notifySuccess('Successfully updated application');
                    $('#updateApplicationModal').modal('hide');
                }).catch(function (error) {
                    notifyError('Network Error', 'Failed to get application data');
                    console.log(error);
                });
            },
            list: function () {
                const vm = this;
                axios.get('/umx/system/applications/' + vm.applicationId)
                    .then(function (response) {
                        vm.applicationData = response.data;
                        vm.applicationName = $('#application-selection option:selected').text();
                        vm.update();
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get application data');
                        console.log(error);
                    });
            },
            setData: function (data) {
                const vm = this;
                const application = JSON.parse(JSON.stringify(data));
                if (application.hasOwnProperty('id')) { vm.applicationId = application.id; }
                if (application.hasOwnProperty('name')) { vm.applicationName = application.name; }
                if (application.hasOwnProperty('configurationName')) { vm.configurationName = application.configurationName; }
                if (application.hasOwnProperty('mailBody')) { vm.mailBody = application.mailBody; }
                if (application.hasOwnProperty('mailSubject')) { vm.mailSubject = application.mailSubject; }
                if (application.hasOwnProperty('server')) { vm.server = application.server; }

                // expects JSON array (populate vue-tags-input)
                if (application.hasOwnProperty('recipients')) {
                    vm.recipientss = application.recipients.map((attribute) => {
                        return { text: attribute };
                    });
                }

                if (application.hasOwnProperty('attributes')) {
                    vm.attributess = application.attributes.map((attribute) => {
                        return { text: attribute };
                    });
                }
            },
            update: function () {
                const vm = this;

                if ($.fn.DataTable.isDataTable('#applicationDT')) {
                    vm.dataTable.destroy();
                }

                // (Re)Initialize Datatable
                vm.dataTable = $('#applicationDT').DataTable({
                    dom: '<"myfilter"f><"mylength"l>tip',
                    data: vm.applications,
                    initComplete: function () {
                        $('#applicationDT tbody').on('click', '.updateApplicationButton', function (event) {
                            const tableRow = $(this).parents('tr');
                            const application = vm.dataTable.row(tableRow).data();
                            main.setData(application);
                        });

                        $('#applicationDT tbody').on('click', '.removeApplicationButton', function (event) {
                            const tableRow = $(this).parents('tr');
                            const application = vm.dataTable.row(tableRow).data();
                            main.setData(application);
                        });
                    },
                    columnDefs: [
                        {
                            targets: 'tableheader_name',
                            data: 'name',
                            defaultContents: '',
                            render: function (data, type, row) {
                                return data;
                            }
                        },
                        {
                          targets: 'tableheader_serverName',
                          data: 'server.name',
                          defaultContents: '',
                          render: function (data, type, row) {
                              return data;
                          }
                        },
                        {
                            targets: 'tableheader_configurationName',
                            data: 'configurationName',
                            defaultContents: '',
                            render: function (data, type, row) {
                                return data;
                            }
                        },
                        {
                            targets: 'tableheader_recipients',
                            data: null,
                            defaultContents: '',
                            render: function (data, type, row) {
                                const recipients = row.recipients;
                                const recipientsHTML = recipients.map(function (recipient) {
                                    return `<li>${recipient}</li>`;
                                }).join('');
                                return `<ul>${recipientsHTML}</ul>`;
                            }
                        },
                        {
                            targets: 'tableheader_attributes',
                            data: null,
                            defaultContents: '',
                            render: function (data, type, row) {
                                const attributes = row.attributes;
                                const attributesHTML = attributes.map(function (attribute) {
                                    return `<li>${attribute}</li>`;
                                }).join('');
                                return `<ul>${attributesHTML}</ul>`;
                            }
                        },
                        {
                          targets: 'tableheader_status',
                          data: null,
                          width: "50px",
                          orderable: false,
                          defaultContents: '',
                          render: function (data, type, row) {
                            if(row.status){
                              return   '<i class="fa fa-3x fa-check-circle" style="color:green"></i>';
                            }else{
                              return   '<i class="fa fa-3x fa-exclamation-triangle" style="color:red"></i>';
                            }
                          }
                      },
                        {
                            targets: 'tableheader_updateApplication',
                            data: null,
                            width: "50px",
                            orderable: false,
                            defaultContents: '',
                            render: function (data, type, row) {
                                return '<button type="button" ' +
                                    '    class="btn btn-link updateApplicationButton" data-toggle="modal" ' +
                                    '    data-target="#updateApplicationModal">' +
                                    '    <i class="fa fa-fw fa-pencil"></i>' +
                                    '</button>';
                            }
                        },
                        {
                            targets: 'tableheader_deleteApplication',
                            data: null,
                            width: "50px",
                            orderable: false,
                            defaultContents: '',
                            render: function (data, type, row) {
                                return '' +
                                    '<button' +
                                    '    type="button" ' +
                                    '    class="btn btn-link removeApplicationButton" data-toggle="modal" ' +
                                    `    data-target="#removeApplicationModal">` +
                                    '    <i class="fa fa-fw fa-trash"></i>' +
                                    '</button>';
                            }
                        }
                    ]
                });

                // Add Start, Stop and Delete Campaign Buttons
                $('div.myfilter').append('' +
                    '<button id="addApplicationButton" type="button" ' +
                    '    class="btn btn-primary btn-sm iconed-button" data-toggle="modal"' +
                    '    data-target="#addApplicationModal">' +
                    '    <i class="fa fa-fw fa-plus"></i>' +
                    '    <span>Add Application</span>' +
                    '</button>' +
                    '');

                // Set focus on search when page loaded
                if ($('#addApplicationModal').is(':visible') == false &&
                    $('#removeApplicationModal').is(':visible') == false &&
                    $('#updateApplicationModal').is(':visible') == false) {

                    $('div.dataTables_filter input').focus();
                }
            },
            cancelAdd: function () {
                $('#addApplicationModal').modal('hide');
            },
            cancelRemove: function () {
                $('#removeApplicationModal').modal('hide');
            },
            cancelUpdate: function () {
                $('#updateApplicationModal').modal('hide');
            },

            // Convert vue-tags-input return value from object to array
            simpleStringArray: function (tags) {
                return tags.map(tag => tag.text);
            },

            getDefaultSettings: function () {
                const vm = this;
                axios.get('/umx/system/settings?random=' + Math.random())
                    .then(function (response) {
                        vm.settings = response.data.settings;
                        vm.settings.forEach(element => {
                            if (element.name == 'mail.subject') {
                                vm.defaultMailSubject = element.value;
                            }
                            if (element.name == 'mail.content') {
                                vm.defaultMailBody = element.value;
                            }
                        });

                        vm.newApplication.mailSubject = vm.defaultMailSubject;
                        vm.newApplication.mailBody = vm.defaultMailBody;
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get settings data');
                        console.log(error);
                    });
            }
        }
    });
}

function removeHash() {
    // removes hash from URL
    let scrollV, scrollH, loc = window.location;
    if ('pushState' in history)
        history.pushState('', document.title, loc.pathname + loc.search);
    else {
        // Prevent scrolling by storing the page's current scroll offset
        scrollV = document.body.scrollTop;
        scrollH = document.body.scrollLeft;

        loc.hash = '';

        // Restore the scroll offset, should be flicker free
        document.body.scrollTop = scrollV;
        document.body.scrollLeft = scrollH;
    }
}
