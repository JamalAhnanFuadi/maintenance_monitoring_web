<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Vue -->
<script src="/monitoring/lib/vuejs/js/vue.js"></script>
<!-- Axios -->
<script src="/monitoring/lib/axios/js/axios.min.js"></script>

<!-- jQuery -->
<script src="/monitoring/js/vendor/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/monitoring/js/vendor/bootstrap.min.js"></script>

<!-- Template -->
<script src="/monitoring/js/plugins.js"></script>
<script src="/monitoring/js/app.js"></script>

<!-- Custom Theme Scripts -->
<!-- <script src="/monitoring/js/custom.js"></script> -->
<!-- <script src="/monitoring/js/monitoring.js"></script> -->


<script>
    $(document).ready(function () {
        var path = window.location.pathname.split("/").pop();

        // Set active link
        $('.sidebar-nav a').each(function () {
            var href = $(this).attr('href');

            console.log("path : " + path);
            console.log("href : " + href);

            if (href === path) {
                $(this).addClass('active');

                // If it is inside a submenu, open the parent menu
                var parentMenu = $(this).closest('.sidebar-nav-menu');
                console.log("parentMenu : " + parentMenu);
                if (parentMenu.length > 1) {
                    console.log("Parent menu found for:", href);
                    parentMenu.addClass('open');
                    parentMenu.find('ul').show();
                } else {
                    console.log("No parent menu found for:", href);
                }
            }
        });

        // Toggle submenu
        $('.sidebar-nav-menu > a').click(function (e) {
            e.preventDefault();
            console.log("error : " + e.preventDefault());
            var parentMenu = $(this).parent('.sidebar-nav-menu');
            var submenu = parentMenu.find('ul');

            console.log("parentMenu : " + parentMenu);
            console.log("submenu : " + submenu);


            if (parentMenu.hasClass('open')) {
                submenu.slideUp();
                parentMenu.removeClass('open');
            } else {
                submenu.slideDown();
                parentMenu.addClass('open');
            }
        });
    });
</script>