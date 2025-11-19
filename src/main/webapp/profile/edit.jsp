<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile - MEMO</title>
    <!-- Kita asumsikan Anda sudah memiliki Tailwind (dari file JSP lain) -->
    <script src="https://cdn.tailwindcss.com"></script>
    <script>tailwind.config = { darkMode: 'class' }</script>
</head>
<body class="font-sans antialiased bg-gray-100 dark:bg-gray-900">
    
    <%-- TODO: Tambahkan navigasi Anda di sini --%>
    <%-- <jsp:include page="../layouts/navigation.jsp" /> --%>

    <!-- Header -->
    <header class="bg-white dark:bg-gray-800 shadow">
        <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
            <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
                Profile
            </h2>
        </div>
    </header>

    <!-- Konten (Menerjemahkan @include) -->
    <div class="py-12">
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8 space-y-6">
            
            <!-- Include Form Informasi Profil -->
            <div class="p-4 sm:p-8 bg-white dark:bg-gray-800 shadow sm:rounded-lg">
                <div class="max-w-xl">
                    <jsp:include page="partials/update-profile-information-form.jsp" />
                </div>
            </div>

            <!-- Include Form Update Password -->
            <div class="p-4 sm:p-8 bg-white dark:bg-gray-800 shadow sm:rounded-lg">
                <div class="max-w-xl">
                    <jsp:include page="partials/update-password-form.jsp" />
                </div>
            </div>
            
            <!-- Include Form Hapus User -->
            <div class="p-4 sm:p-8 bg-white dark:bg-gray-800 shadow sm:rounded-lg">
                <div class="max-w-xl">
                    <%-- Anda tidak mengirimkan file ini, tapi kita bisa buatkan placeholder --%>
                    <jsp:include page="partials/delete-user-form.jsp" />
                </div>
            </div>
            
        </div>
    </div>
</body>
</html>