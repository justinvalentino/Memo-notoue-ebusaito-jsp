<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage Categories - MEMO</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = { darkMode: 'class' }
    </script>
</head>
<body class="font-sans antialiased bg-gray-100 dark:bg-gray-900">
    
    <%-- 
      Kita skip layout <x-app-layout> untuk saat ini.
      Anda bisa tambahkan <jsp:include page="../layouts/navigation.jsp" /> 
      jika nanti Anda membuat file navigasi.
    --%>
    
    <header class="bg-white dark:bg-gray-800 shadow">
        <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
            <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
                Manage Categories
            </h2>
        </div>
    </header>

    <div class="py-12">
        <div class="max-w-4xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-white dark:bg-gray-800 overflow-hidden shadow-xl sm:rounded-lg p-6 lg:p-8">

                <h3 class="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Create New Category</h3>
                
                <form action="${pageContext.request.contextPath}/categories/create" method="POST" class="mb-8">
                    <div class="flex items-center space-x-2">
                        <input 
                            type="text" 
                            name="name" 
                            placeholder="Enter category name"
                            required
                            class="w-full border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 rounded-lg shadow-sm"
                        />
                        <button type="submit" class="inline-flex items-center px-4 py-2 bg-green-600 border rounded-lg font-semibold text-xs text-white uppercase hover:bg-green-700">
                            +
                        </button>
                    </div>
                    <c:if test="${not empty errorMessage}">
                        <p class="text-sm text-red-600 dark:text-red-400 mt-2">${errorMessage}</p>
                    </c:if>
                </form>

                <hr class="my-6 border-gray-200 dark:border-gray-700" />

                <h3 class="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Existing Categories</h3>

                <c:if test="${not empty successMessage}">
                    <div class="p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg dark:bg-green-700 dark:text-green-100">
                        ${successMessage}
                    </div>
                </c:if>

                <div class="space-y-4">
                    <c:forEach var="cat" items="${categoryList}">
                        <div class="flex items-center justify-between p-3 bg-gray-50 dark:bg-gray-700 rounded-lg shadow-sm">
                            <span class="text-gray-900 dark:text-gray-100 font-semibold">
                                ${cat.name}
                                <span class="text-sm text-gray-500 dark:text-gray-400">
                                    (${cat.noteCount} notes)
                                </span>
                            </span>

                            <%-- 
                              Terjemahan Delete Form. 
                              Form HTML tidak bisa method="DELETE".
                              Jadi kita gunakan link <a> biasa yang menargetkan rute GET /delete
                              di CategoryController kita.
                            --%>
                            <a href="${pageContext.request.contextPath}/categories/delete?id=${cat.id}" 
                               class="inline-flex items-center px-3 py-1 bg-red-600 border rounded-md font-semibold text-xs text-white uppercase hover:bg-red-700"
                               onclick="return confirm('Are you sure you want to delete the category: ${cat.name}? All ${cat.noteCount} associated notes will be uncategorized.');">
                                Delete
                            </a>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty categoryList}">
                        <p class="text-gray-500 dark:text-gray-400">No categories found. Start adding some!</p>
                    </c:if>
                </div>

            </div>
        </div>
    </div>
</body>
</html>