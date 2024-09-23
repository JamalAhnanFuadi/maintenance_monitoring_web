<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta charset="utf-8">
<meta name="description" content="">
<meta name="author" content="tsi">
<meta name="robots" content="noindex, nofollow">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">

<!-- Icons -->
<!-- The following icons can be replaced with your own, they are used by desktop and mobile browsers -->
<link rel="shortcut icon" href="img/favicon.png">
<link rel="apple-touch-icon" href="img/icon57.png" sizes="57x57">
<link rel="apple-touch-icon" href="img/icon72.png" sizes="72x72">
<link rel="apple-touch-icon" href="img/icon76.png" sizes="76x76">
<link rel="apple-touch-icon" href="img/icon114.png" sizes="114x114">
<link rel="apple-touch-icon" href="img/icon120.png" sizes="120x120">
<link rel="apple-touch-icon" href="img/icon144.png" sizes="144x144">
<link rel="apple-touch-icon" href="img/icon152.png" sizes="152x152">
<link rel="apple-touch-icon" href="img/icon180.png" sizes="180x180">
<!-- END Icons -->

<!-- Stylesheets -->
<!-- Bootstrap is included in its original form, unaltered -->
<link rel="stylesheet" href="css/bootstrap.min.css">

<!-- Related styles of various icon packs and plugins -->
<link rel="stylesheet" href="css/plugins.css">

<!-- The main stylesheet of this template. All Bootstrap overwrites are defined in here -->
<link rel="stylesheet" href="css/main.css">

<!-- Include a specific file here from css/themes/ folder to alter the default theme of the template -->

<!-- The themes stylesheet of this template (for using specific theme color in individual elements - must included last) -->
<link rel="stylesheet" href="css/themes.css">
<!-- END Stylesheets -->

<!-- Modernizr (browser feature detection library) -->
<script src="js/vendor/modernizr.min.js"></script>

<style>
    .sidebar-nav-menu.open > ul {
        display: block;
    }
    .sidebar-nav-menu ul {
        display: none;
    }
    .active {
        font-weight: bold;
    }
</style>
<style>
    .table-responsive{
        max-height: 100%; /* Atur tinggi maksimum kontainer */
        overflow-y: auto; /* Gulir vertikal */
        overflow-x: auto; /* Gulir horizontal jika perlu */
        border: 1px solid #ccc; /* Border kontainer */
    }

    table {
        border-collapse: collapse;
        width: 100%; /* Lebar tabel */
    }

    th, td {
        border: 1px solid #000; /* Border sel */
        padding: 10px;
        text-align: left;
        vertical-align: top;
        min-height: 40px; /* Tinggi minimum untuk setiap sel */
    }
    th {
        border: 3px solid #000; /* Garis tebal untuk header */
        padding: 10px;
        background-color: #f2f2f2; /* Warna latar untuk header */
        text-align: left;
    }

    td {
        border: 2px solid #000; /* Garis untuk sel */
        padding: 10px;
        text-align: left;
    }


</style>
<style>
.search-container {
position: relative; /* Untuk memposisikan ikon */
width: 100%; /* Lebar penuh */
}

#SearchInput {
width: 100%; /* Lebar penuh */
padding: 10px 40px; /* Ruang di dalam input (kanan untuk ikon) */
border: 1px solid #ccc; /* Garis tepi abu-abu */
border-radius: 5px; /* Sudut membulat */
font-size: 16px; /* Ukuran font */
box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Bayangan ringan */
transition: border-color 0.3s; /* Transisi saat fokus */
}

#SearchInput:focus {
border-color: #007bff; /* Ubah warna tepi saat fokus */
outline: none; /* Menghilangkan outline default */
}

#SearchInput::placeholder {
color: #999; /* Warna placeholder */
opacity: 1; /* Mengatur opacity */
}
</style>