<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section>
    <header>
        <h2 class="text-lg font-medium text-gray-900 dark:text-gray-100">
            Update Password
        </h2>
        <p class="mt-1 text-sm text-gray-600 dark:text-gray-400">
            Ensure your account is using a long, random password to stay secure.
        </p>
    </header>

    <!-- Targetkan rute POST di ProfileController -->
    <form method="post" action="${pageContext.request.contextPath}/profile/update-password" class="mt-6 space-y-6">

        <div>
            <label for="update_password_current_password" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Current Password</label>
            <input id="update_password_current_password" name="current_password" type="password" class="mt-1 block w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900" required />
            <!-- Tampilkan error jika password tidak cocok -->
            <c:if test="${error == 'password-mismatch'}">
                 <p class="text-sm text-red-600 dark:text-red-400 mt-2">Current password does not match.</p>
            </c:if>
        </div>

        <div>
            <label for="update_password_password" class="block font-medium text-sm text-gray-700 dark:text-gray-300">New Password</label>
            <input id="update_password_password" name="password" type="password" class="mt-1 block w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900" required />
        </div>

        <div>
            <label for="update_password_password_confirmation" class="block font-medium text-sm text-gray-700 dark:text-gray-300">Confirm Password</label>
            <input id="update_password_password_confirmation" name="password_confirmation" type="password" class="mt-1 block w-full rounded-md shadow-sm border-gray-300 dark:border-gray-700 dark:bg-gray-900" required />
        </div>

        <div class="flex items-center gap-4">
            <button type="submit" class="inline-flex items-center px-4 py-2 bg-blue-600 border rounded-md font-semibold text-xs text-white uppercase hover:bg-blue-700">Save</button>
            
            <!-- Tampilkan pesan 'Saved.' jika status-nya 'password-updated' -->
            <c:if test="${status == 'password-updated'}">
                <p class="text-sm text-gray-600 dark:text-gray-400">Saved.</p>
            </c:if>
        </div>
    </form>
</section>