<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
<%@include file="pages/css_import.jsp"%>
<title>UMX</title>
</head>

<body class="nav-md">
 <!-- Loading Screen -->
 <div class="loader"></div>
 <div class="container body">
  <div class="main_container">
   <!-- side nav -->
   <%@include file="pages/side_menu.jsp"%>
   <!-- /side nav -->
   <!-- top navigation -->
   <%@include file="pages/top_nav.jsp"%>
   <!-- /top navigation -->
   <!-- page content -->
   <main class="right_col" role="main" id="vue">
    <div class="x_panel">
     <header class="x_title">
      <div class="row">
       <h2>Server Management</h2>
      </div>
     </header>
     <article class="x_content">
      <section class="row">
       <div class="table-responsive">
        <table id="serverDT" class="table table-hover" style="width: 100%;">
         <thead>
          <tr>
           <th class="tableheader_name">Name</th>
           <th class="tableheader_url">URL</th>
           <th class="tableheader_username">Username</th>
           <th class="tableheader_configurations">Configurations</th>
           <th class="tableheader_refresh">Refresh</th>
           <th class="tableheader_update">Update</th>
           <th class="tableheader_delete">Delete</th>
          </tr>
         </thead>
         <tbody>
          <!-- populated by js -->
         </tbody>
        </table>
       </div>
      </section>
     </article>
    </div>
    <!-- Add Server Modal -->
    <div class="modal fade" id="addServerModal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static"
     @keyup.esc="cancelAdd()">
     <div class="modal-dialog" role="document">
      <div class="modal-content">
       <div class="modal-body">
        <div class="container-fluid">
         <div class="x_title">
          <button type="button" class="close" aria-label="Close" onclick="main.cancelAdd()">
           <span aria-hidden="true">&times;</span>
          </button>
          <h3 class="modal-title">Add Server</h3>
         </div>
         <div class="x_content">
          <form class="form-horizontal" onsubmit="main.add()">
           <div class="form-group">
            <label class="control-label col-md-3">Server Name</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="newServer.name" />
             </div>
             <span class="col-md-12">e.g. Test Server 1</span>
            </div>
           </div>
           <div class="form-group">
            <label class="control-label col-md-3">Server URL</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="newServer.url" />
             </div>
             <span class="col-md-12"> e.g. http://192.168.0.10:8080</span>
            </div>
           </div>
           <div class="form-group">
            <label class="control-label col-md-3">Username</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="newServer.username" />
             </div>
            </div>
           </div>
           <div class="form-group">
            <label class="control-label col-md-3">Password</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="password" class="form-control" v-model="newServer.password" />
             </div>
            </div>
           </div>
           <div class="ln_solid"></div>
           <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 button-align">
            <button type="button" class="btn btn-sm btn-primary iconed-button" onclick="main.add()">
             <i class="fa fa-fw fa-plus"></i> <span>Add</span>
            </button>
            <button type="button" class="btn btn-sm btn-default iconed-button" onclick="main.cancelAdd()">
             <span>Cancel</span>
            </button>
           </div>
          </form>
         </div>
        </div>
       </div>
      </div>
     </div>
    </div>
    <!-- /Add Server Modal -->

    <!-- Remove Server Modal -->
    <div class="modal fade" id="removeServerModal" tabindex="-1" role="dialog" data-keyboard="false"
     data-backdrop="static" @keyup.esc="cancelRemove()">
     <div class="modal-dialog" role="document">
      <div class="modal-content">
       <div class="modal-body">
        <div class="container-fluid">
         <div class="x_title">
          <button type="button" class="close" aria-label="Close" onclick="main.cancelRemove()">
           <span aria-hidden="true">&times;</span>
          </button>
          <h3 class="modal-title">Remove Server</h3>
         </div>
         <div class="x_content">
          <form class="form-horizontal" onsubmit="main.remove()">
           <div class="form-group text-danger remove-app-padding">
            <h5>
             Are you sure you want to remove Server <strong>{{selectedServer.name}}</strong> ?
            </h5>
            <h5>This operation cannot be undone!</h5>
           </div>
           <div class="ln_solid"></div>
           <div class="col-xs-12 text-right">
            <button type="button" class="btn btn-sm btn-danger iconed-button" onclick="main.remove()">
             <i class="fa fa-fw fa-trash"></i> <span>Remove</span>
            </button>
            <button type="button" class="btn btn-sm btn-default iconed-button" onclick="main.cancelRemove()">
             <span>Cancel</span>
            </button>
           </div>
          </form>
         </div>
        </div>
       </div>
      </div>
     </div>
    </div>
    <!-- /Remove Server Modal -->

    <!-- Update Server Modal -->
    <div class="modal fade" id="updateServerModal" tabindex="-1" role="dialog" data-keyboard="false"
     data-backdrop="static" @keyup.esc="cancelUpdate()">
     <div class="modal-dialog" role="document">
      <div class="modal-content">
       <div class="modal-body">
        <div class="container-fluid">
         <div class="x_title">
          <button type="button" class="close" aria-label="Close" onclick="main.cancelUpdate()">
           <span aria-hidden="true">&times;</span>
          </button>
          <h3 class="modal-title">Update Server {{selectedServer.name}}</h3>
         </div>
         <div class="x_content">
          <form class="form-horizontal" onsubmit="main.updateApplication()">
           <div class="form-group">
            <label class="control-label col-md-3">Name</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="selectedServer.name" disabled />
             </div>
            </div>
           </div>
           <div class="form-group">
            <label class="control-label col-md-3">Server URL</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="selectedServer.url" />
             </div>
            </div>
           </div>

           <div class="form-group">
            <label class="control-label col-md-3">Username</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="text" class="form-control" v-model="selectedServer.username" />
             </div>
            </div>
           </div>

           <div class="form-group">
            <label class="control-label col-md-3">Password</label>
            <div class="col-md-8 row">
             <div class="col-md-12">
              <input type="password" class="form-control" v-model="selectedServer.password" />
             </div>
            </div>
           </div>

           <div class="ln_solid"></div>
           <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 button-align">
            <button type="button" class="btn btn-sm btn-info iconed-button" onclick="main.update()">
             <i class="fa fa-fw fa-pencil"></i> <span>Update</span>
            </button>
            <button type="button" class="btn btn-sm btn-default iconed-button" onclick="main.cancelUpdate()">
             <span>Cancel</span>
            </button>
           </div>
          </form>
         </div>
        </div>
       </div>
      </div>
     </div>
    </div>
    <!-- /Update Server Modal -->
   </main>
  </div>
  <!-- /page content -->
  <!-- footer content -->
  <%@include file="pages/footer.jsp"%>
  <!-- /footer content -->
 </div>
 <!-- javascript imports -->
 <%@include file="pages/js_import.jsp"%>
 <script src="js/serverManagement.js"></script>
</body>

</html>