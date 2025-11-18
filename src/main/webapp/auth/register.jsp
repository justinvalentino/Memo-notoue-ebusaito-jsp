<%-- Import JSTL Core Library (PENTING!) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register - MEMO</title>

    <script src="https://cdn.tailwindcss.com"></script>
    
    <script>
        tailwind.config = {
            darkMode: 'class', // atau 'media'
        }
    </script>
</head>
<body class="font-sans text-gray-900 antialiased">
    <div class="min-h-screen flex flex-col sm:justify-center items-center pt-6 sm:pt-0 bg-gray-100 dark:bg-gray-900">
        
        <%-- Ini adalah "Card" dari guest.blade.php --%>
        <div class="w-full sm:max-w-md mt-6 px-6 py-4 bg-white dark:bg-gray-800 shadow-md overflow-hidden sm:rounded-lg">
            
            <%-- Ini adalah isi dari register.blade.php --%>

            <c:if test="${not empty error}">
                <div class="mb-4 font-medium text-sm text-red-600 dark:text-red-400">
                    ${error}
                </div>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/auth/register">

                <div>
                    <label for="name" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Name</label>
                    <input id="name" class="block mt-1 w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300" 
                           type="text" name="name" required autofocus autocomplete="name" />
                </div>

                <div class="mt-4">
                    <label for="email" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Email</label>
                    <input id="email" class="block mt-1 w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300" 
                           type="email" name="email" required autocomplete="username" />
                </div>

                <div class="mt-4">
                    <label for="password" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Password</label>
                    <input id="password" class="block mt-1 w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300"
                           type="password" name="password" required autocomplete="new-password" />
                </div>

                <div class="mt-4">
                    <label for="password_confirmation" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Confirm Password</label>
                    <input id="password_confirmation" class="block mt-1 w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300"
                           type="password" name="password_confirmation" required autocomplete="new-password" />
                </div>

                <div class="flex items-center justify-end mt-4">
                    <a class="underline text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100" 
                       href="${pageContext.request.contextPath}/auth/login">
                        Already registered?
                    </a>

                    <button type="submit" class="ms-4 inline-flex items-center px-4 py-2 bg-blue-600 border border-transparent rounded-md font-semibold text-xs text-white uppercase tracking-widest hover:bg-blue-700">
                        Register
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>