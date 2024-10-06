<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Vue -->
<script src="${pageContext.request.contextPath}/lib/vuejs/js/vue.js"></script>
<!-- Axios -->
<script src="${pageContext.request.contextPath}/lib/axios/js/axios.min.js"></script>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/js/vendor/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/js/vendor/bootstrap.min.js"></script>

<!-- Template -->
<script src="${pageContext.request.contextPath}/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>

<script>
    $(document).ready(function () {
        var path = window.location.pathname.split("/").pop();

        // Set active link
        $('.sidebar-nav a').each(function () {
            var href = $(this).attr('href');

            if (href === path) {
                $(this).addClass('active');

                // If it is inside a submenu, open the parent menu
                var parentMenu = $(this).closest('.sidebar-nav-menu');
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
            var parentMenu = $(this).parent('.sidebar-nav-menu');
            var submenu = parentMenu.find('ul');
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