var main;

$(document).ready(function () {

    // Display Notification (called upon successful Add Campaign)
    let hash = String(window.location.hash);
    if (hash.localeCompare('') !== 0) {
        let message = '';
        if (hash.localeCompare('#success') === 0) {
            message = 'Successfully imported business roles';
        }

        notifySuccess(message);
        removeHash();
    }

    initVue();

    $('#importModal').on('hide.bs.modal', function () {
        main.data = '';
    });

    $('#importModal').on('shown.bs.modal', function () {
        $('#importModal textarea').focus();
    });
});

function initVue() {
  main = new Vue(
        {
            el: '#vue',
            data: {
                dataTable: null,
                dataTableDetail: null,
                data: '',
                bizRoles: [],
                selectedBizRole: {}
            },
            mounted: function () {
                this.list()
            },
            methods: {
                list: function () {
                    const vm = this;

                    axios.get('/umx/system/businessRoles').then(function (response) {
                        const localRoles = JSON.parse(JSON.stringify(response.data.roles));
                        vm.bizRoles = localRoles;
                        vm.update();
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get business roles data');
                        console.log(error);
                    });
                },
                doImport: function () {
                    const vm = this;
                    axios.post('/umx/system/businessRoles/import', {
                        data: vm.data,
                    }).then(function (response) {
                        // redirects to same page with hash
                        window.location.href = window.location.href + '#success';
                        window.location.reload();
                    }).catch(function (error) {
                        notifyError('Network Error', 'Failed to get import business roles data');
                        console.log(error);
                    });
                },
                doExport: function () {
                    window.location.href = '/umx/system/businessRoles/export';
                },
                update: function () {
                    const vm = this;
                    if ($.fn.DataTable.isDataTable('#businessRoleDT')) {
                        vm.dataTable.destroy();
                    }

                    // (Re)Initialize Datatable
                    vm.dataTable = $('#businessRoleDT').DataTable({
                        dom: '<"myfilter"f><"mylength"l>tip',
                        data: vm.bizRoles,
                        columns: [
                            {
                                render: function(data, type, row){
                                    return row.name
                                }
                            },
                            {
                                render: function(data, type, row){
                                    return '' +
                                    '<ul>' + 
                                        row.hrRole.split(";").sort().map(function(v, i){
                                            return '<li>'+v+'</li>';
                                        }).join("") +
                                    '</ul>';
                                }
                            },
                            {
                                render: function(data, type, row){
                                    return '' +
                                    '<ul>' + 
                                        // Unique applications name
                                        row.applicationRoles.map(br => br.application).filter((v, i, a) => a.indexOf(v) === i).sort().map(function(v, i){
                                            return '<li>'+v+'</li>';
                                        }).join("") +
                                    '</ul>';
                                }
                            },
                            {
                                render: function(data, type, row){
                                    return '<button onclick="main.openDetail(\''+ row.id +'\')" class="btn btn-primary"><em class="fa fa-info"></em> Details</button>'
                                }
                            }
                        ]
                    });

                    // Add Start, Stop and Delete Campaign Buttons
                    $('#businessRoleDT_wrapper div.myfilter').append('' +
                        '<button id="importButton" type="button" ' +
                        '    class="btn btn-default btn-sm iconed-button" ' +
                        '    data-toggle="modal" data-target="#importModal">' +
                        '    <i class="fa fa-fw fa-upload"></i>' +
                        '    <span> Import (Upload) </span>' +
                        '</button>' +
                        '<button id="exportButton" type="button" ' +
                        '    class="btn btn-default btn-sm iconed-button" ' +
                        '    data-toggle="modal" onclick="main.doExport()">' +
                        '    <i class="fa fa-fw fa-download"></i>' +
                        '    <span> Export (Download) </span>' +
                        '</button>' +
                        '');

                    // Set focus on search when page loaded
                    if ($('#importModal').is(':visible') == false) {
                        $('div.dataTables_filter input').focus();
                    }

                },
                cancelImport: function () {
                    $('#importModal').modal('toggle');
                },
                openDetail: function(idBizRole){
                    const vm = this;

                    vm.selectedBizRole = vm.bizRoles.find(br => br.id == idBizRole);

                    if ($.fn.DataTable.isDataTable('#businessRoleDetailsDT')) {
                        vm.dataTableDetail.destroy();
                    }
    
                    // (Re)Initialize Datatable
                    vm.dataTableDetail = $('#businessRoleDetailsDT').DataTable({
                        dom: '<"myfilter"f><"mylength"l>tip',
                        data: vm.selectedBizRole.applicationRoles,
                        columns: [
                            {
                                render: function(data, type, row){
                                    return row.application
                                }
                            },
                            {
                                render: function(data, type, row){
                                    return row.role;
                                }
                            }
                        ]
                    });
    
                    $("#detailsModal").modal("show");
                },
                generateTempId: function(length) {
                    var result           = '';
                    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
                    var charactersLength = characters.length;
                    for ( var i = 0; i < length; i++ ) {
                       result += characters.charAt(Math.floor(Math.random() * charactersLength));
                    }
                    return result;
                }
            },
            updated: function () {
                const vm = this;
                vm.$nextTick(function () {
                    vm.update();
                });
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
