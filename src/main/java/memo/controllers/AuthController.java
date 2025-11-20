package memo.controllers;

import memo.dao.UserDAO;
import memo.models.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.MessageDigest; // Untuk Hashing
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class AuthController extends HttpServlet {

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Default ke halaman login
            showLoginForm(request, response);
        } else if (pathInfo.equals("/login")) {
            showLoginForm(request, response);
        } else if (pathInfo.equals("/register")) {
            showRegisterForm(request, response);
        } else if (pathInfo.equals("/logout")) {
            logoutAction(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/login")) {
            loginAction(request, response);
        } else if (pathInfo.equals("/register")) {
            registerAction(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // sebagai helper password, hasing password
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 64) { // SHA-256 32 bytes = 64 hex
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // action methods

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/login.jsp");
        dispatcher.forward(request, response);
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/register.jsp");
        dispatcher.forward(request, response);
    }

    private void registerAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // mengecek apakah user terdaftar
        if (userDAO.findUserByEmail(email) != null) {
            // Kirim error kembali ke halaman register
            request.setAttribute("error", "Email sudah terdaftar.");
            showRegisterForm(request, response);
            return;
        }

        // Create user baru
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(password)); // Hash passwordnya

        // Simpan ke database
        userDAO.createUser(newUser);

        // redirect ke login page
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // cari user dari database
        User user = userDAO.findUserByEmail(email);

        // check user dan password
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            // cocok password
            
            // create new session
            HttpSession session = request.getSession();
            session.setAttribute("authUser", user); // Simpan objek User di session
            
            // redirect notes page
            response.sendRedirect(request.getContextPath() + "/notes");
        } else {
            // password salah error
            request.setAttribute("error", "Email atau password salah.");
            showLoginForm(request, response);
        }
    }

    private void logoutAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // session sedang berjalan
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate(); // Hapus session
        }
        
        // redirect ke login
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }
}