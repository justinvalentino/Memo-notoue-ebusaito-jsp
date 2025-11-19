<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section>
    <header>
        <h2 class="text-lg font-medium text-gray-900 dark:text-gray-100">
            Profile Information
        </h2>
        <p class="mt-1 text-sm text-gray-600 dark:text-gray-400">
            Update your account's profile information and email address.
        </p>
    </header>

    <!-- Targetkan rute POST di ProfileController -->
    <form method="post" action="${pageContext.request.contextPath}/profile/update-info" class="mt-6 space-y-6">
        
        <div>
            <label for="name" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Name</label>
            <!-- Ambil data dari ${authUser} yang ada di session -->
            <input id="name" name="name" type="text" class="mt-1 block w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900" 
                   value="${authUser.name}" required autofocus />
            <!-- TODO: Tampilkan error validasi jika ada -->
        </div>

        <div>
            <label for="email" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Email</label>
            <input id="email" name="email" type="email" class="mt-1 block w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900" 
                   value="${authUser.email}" required />
            <!-- TODO: Tampilkan error validasi jika ada -->
        </div>

        <div class="flex items-center gap-4">
            <button type="submit" class="inline-flex items-center px-4 py-2 bg-blue-600 border rounded-md font-semibold text-xs text-white uppercase hover:bg-blue-700">Save</button>
            
            <!-- Tampilkan pesan 'Saved.' jika status-nya 'profile-updated' -->
            <c:if test="${status == 'profile-updated'}">
                <p class="text-sm text-gray-600 dark:text-gray-400">Saved.</p>
            </c:if>
        </div>
    </form>
</section>