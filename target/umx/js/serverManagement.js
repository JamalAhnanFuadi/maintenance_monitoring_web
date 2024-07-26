var main; 

$(document).ready(function () {
    initVue();

    // event listeners
    $('#addServerModal').on('hidden.bs.modal', function () {      
       main.clearAdd();
    });
    
    $('#addServerModal').on('shown.bs.modal', function () {
      $('#addServerModal form .form-group:first-child input').focus();
  });

});

function initVue() {
   main = new Vue({
        el: '#vue',
        data: {
          selectedServer : {
            id : '',
            name: '',
            url : '',
            username :'',
            password: ''
          }, 
          dataTable: null,
          servers : [],
          newServer : {
            name : '',
            url : '',
            username :'',
            password :''
          }
        },
        mounted: function () {
            this.list();
        },
        methods: {
            list: function () {
                const vm = this;
                axios.get('/umx/system/servers')
                    .then(function (response) {
                      vm.servers = response.data.servers;
                       vm.display();
                    }).catch(function (error) {
                        notifyError('Network Error', 'failed to get server list');
                        console.log(error);
                    });
            },
            clearAdd: function(){
              const vm = this;
              vm.newServer.name = '';
              vm.newServer.url = '';
              vm.newServer.username = '';
              vm.newServer.password = '';
            },
            add: function () {
                const vm = this;
               
                const json = {
                    name:vm.newServer.name,
                    url: vm.newServer.url,
                    username: vm.newServer.username,
                    password: vm.newServer.password
                };

                axios.post('/umx/system/servers', json)
                    .then(function (response) {
                        vm.clearAdd();
                        notifySuccess('Successfully added new server');
                    }).catch(function (error) {
                        console.log(error);
                        if (error.response.status == 409) {
                              notifyError('Server Name Conflict', `A Server '${vm.newServer.name}' already exists`);
                        }else{
                               notifyError('Network Error', 'Failed to add new server');
                        }
                    }).then(function () {
                      $('#addServerModal').modal('hide');
                      vm.list();
                    });
            },
            refresh: function (serverId) {
              const vm = this;
              notifySuccess("Configuration Refresh Started");
              axios.get(
                  '/umx/system/servers/' + serverId + '/configurations/refresh'
              ).then(function (response) {
                  notifySuccess('Configuration Refresh Completed');
              }).catch(function (error) {
                notifyError('Network Error', 'Failed to refresh server configurations');
                console.log(error);
              }).then(function(){
                vm.list();
              });
          },
            remove: function () {
                const vm = this;
                axios.delete('/umx/system/servers/' + vm.selectedServer.id)
                .then(function (response) {
                    vm.list();
                    notifySuccess('Successfully deleted server');
                }).catch(function (error) {
                  if(error.response.status == 409){
                    notifyError('Unable to Delete', 'Server still in use by applications');
                    console.log(error);
                  }else{
                    notifyError('Network Error', 'Failed to delete server');
                    console.log(error);
                  }
                }).then(function(){
                  $('#removeServerModal').modal('hide');
                });
            },
            update: function () {
                const vm = this;
                const json = {
                    name: vm.selectedServer.name,
                    url: vm.selectedServer.url,
                    username: vm.selectedServer.username,
                    password: vm.selectedServer.password
                };
                axios.put(
                    '/umx/system/servers/' + vm.selectedServer.id, json
                ).then(function (response) {
                    vm.list();
                    notifySuccess('Successfully updated server');
                }).catch(function (error) {
                    notifyError('Network Error', 'Failed to update server');
                    console.log(error);
                }).then(function(){
                  $('#updateServerModal').modal('hide');
                });
            },
            display: function () {
                const vm = this;

                if ($.fn.DataTable.isDataTable('#serverDT')) {
                    vm.dataTable.destroy();
                }

                // / / (Re)Initialize Datatable
               vm.dataTable = $('#serverDT').DataTable({
                    dom: '<"myfilter"f><"mylength"l>tip',
                    data: vm.servers,
                    initComplete: function () {
                        $('#serverDT tbody').on('click', '.updateServerButton', function (event) {
                            const tableRow = $(this).parents('tr');
                            var selected = vm.dataTable.row(tableRow).data();
                            vm.selectedServer = {
                                id : selected.id,
                                name: selected.name,
                                url: selected.url,
                                username: selected.username,
                                password: selected.password
                            }
                        });

                        $('#serverDT tbody').on('click', '.removeServerButton', function (event) {
                            const tableRow = $(this).parents('tr');
                            vm.selectedServer = vm.dataTable.row(tableRow).data();
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
                          targets: 'tableheader_url',
                          data: 'url',
                          defaultContents: '',
                          render: function (data, type, row) {
                              return data;
                          }
                        },
                        {
                            targets: 'tableheader_username',
                            data: 'username',
                            defaultContents: '',
                            render: function (data, type, row) {
                                return data;
                            }
                        },
                        {
                          targets: 'tableheader_configurations',
                          data: null,
                          defaultContents: '',
                          render: function (data, type, row) {
                            if(row.configurations.length > 0){
                              const configHTML = row.configurations.map(function (config) {
                                return `<li>${config}</li>`;
                            }).join('');
                            
                            return `<ul>${configHTML}</ul>`;
                          }else{
                            return 'No Configurations Found';
                          }
                            }
                      },
                        {
                          targets: 'tableheader_refresh',
                          data: null,
                          width: "50px",
                          orderable: false,
                          defaultContents: '',
                          render: function (data, type, row) {
                              return '<button type="button" ' +
                                  '    class="btn btn-link" onclick="main.refresh(\'' + row.id +'\')">' +
                                  '    <i class="fa fa-fw fa-refresh"></i>' +
                                  '</button>';
                          }
                      },
                        {
                            targets: 'tableheader_update',
                            data: null,
                            width: "50px",
                            orderable: false,
                            defaultContents: '',
                            render: function (data, type, row) {
                                return '<button type="button" ' +
                                    '    class="btn btn-link updateServerButton" data-toggle="modal" ' +
                                    '    data-target="#updateServerModal">' +
                                    '    <i class="fa fa-fw fa-pencil"></i>' +
                                    '</button>';
                            }
                        },
                        {
                            targets: 'tableheader_delete',
                            data: null,
                            width: "50px",
                            orderable: false,
                            defaultContents: '',
                            render: function (data, type, row) {
                                return '' +
                                    '<button' +
                                    '    type="button" ' +
                                    '    class="btn btn-link removeServerButton" data-toggle="modal" ' +
                                    '    data-target="#removeServerModal">' +
                                    '    <i class="fa fa-fw fa-trash"></i>' +
                                    '</button>';
                            }
                        }
                    ]
                });

                // / / Add Start, Stop and Delete Campaign Buttons
               $('div.myfilter').append('' +
                    '<button id="addApplicationButton" type="button" ' +
                    '    class="btn btn-primary btn-sm iconed-button" data-toggle="modal"' +
                    '    data-target="#addServerModal">' +
                    '    <i class="fa fa-fw fa-plus"></i>' +
                    '    <span>Add Server</span>' +
                    '</button>' +
                    '');

                // / / Set focus on search when page loaded
               if ($('#addApplicationModal').is(':visible') == false &&
                    $('#removeApplicationModal').is(':visible') == false &&
                    $('#updateApplicationModal').is(':visible') == false) {

                    $('div.dataTables_filter input').focus();
                }
            },
            cancelAdd: function () {
                $('#addServerModal').modal('hide');
            },
            cancelRemove: function () {
              this.selectedServer = {
                id : '',
                name: '',
                url : '',
                username :'',
                password: ''
              };
                $('#removeServerModal').modal('hide');
            },
            cancelUpdate: function () {
             this.selectedServer = {
                        id : '',
                        name: '',
                        url : '',
                        username :'',
                        password: ''
                      };
                $('#updateServerModal').modal('hide');
            }
        }
    });
}

function removeHash() {
    // / / removes hash from URL
   let scrollV, scrollH, loc = window.location;
    if ('pushState' in history)
        history.pushState('', document.title, loc.pathname + loc.search);
    else {
        // / / Prevent scrolling by storing the page's current scroll offset
       scrollV = document.body.scrollTop;
        scrollH = document.body.scrollLeft;

        loc.hash = '';

        // / / Restore the scroll offset, should be flicker free
       document.body.scrollTop = scrollV;
        document.body.scrollLeft = scrollH;
    }
}
