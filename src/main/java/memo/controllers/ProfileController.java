package memo.controllers;

import memo.dao.UserDAO;
import memo.models.User;
import memo.models.Note;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ProfileController extends HttpServlet {

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }
    
    // Helper untuk Hashing (SAMA SEPERTI DI AuthController)
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Menampilkan halaman profil
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Cek apakah user sudah login
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        // Ambil data user terbaru dari DB (bukan hanya dari session)
        User sessionUser = (User) session.getAttribute("authUser");
        User freshUser = userDAO.findUserById(sessionUser.getId());
        
        // Set ulang session dengan data terbaru (jika ada perubahan)
        session.setAttribute("authUser", freshUser);
        
        // Tampilkan halaman edit
        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile/edit.jsp");
        dispatcher.forward(request, response);
    }

    // Menangani form update
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        User sessionUser = (User) session.getAttribute("authUser");
        String action = request.getPathInfo(); // Cek URL (/update-info atau /update-password)
        
        if (action.equals("/update-info")) {
            // --- Logika dari update-profile-information-form ---
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            
            User userToUpdate = userDAO.findUserById(sessionUser.getId());
            userToUpdate.setName(name);
            userToUpdate.setEmail(email);
            
            userDAO.updateUserInfo(userToUpdate);
            request.setAttribute("status", "profile-updated"); // Kirim pesan sukses

        } else if (action.equals("/update-password")) {
            // --- Logika dari update-password-form ---
            String currentPassword = request.getParameter("current_password");
            String newPassword = request.getParameter("password");
            
            User user = userDAO.findUserById(sessionUser.getId());
            
            // Cek apakah password saat ini cocok
            if (user.getPassword().equals(hashPassword(currentPassword))) {
                // Jika cocok, update password
                userDAO.updateUserPassword(user.getId(), hashPassword(newPassword));
                request.setAttribute("status", "password-updated"); // Kirim pesan sukses
            } else {
                // Jika tidak cocok, kirim error
                request.setAttribute("error", "password-mismatch");
            }
        }
        
        // Muat ulang halaman dengan pesan sukses/error
        doGet(request, response);
    }
}