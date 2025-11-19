package memo.controllers;

import memo.dao.CategoryDAO;
import memo.models.Category;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CategoryController extends HttpServlet {
    
    private CategoryDAO categoryDAO;

    public void init() {
        categoryDAO = new CategoryDAO();
    }

    // Handles GET requests (show list, show forms, delete action)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            listCategories(request, response);
        } else if (pathInfo.equals("/new")) {
            showNewForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deleteCategoryAction(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Handles POST requests (submit create form, submit update form)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/create")) {
            createCategoryAction(request, response);
        } else if (pathInfo.equals("/update")) {
            updateCategoryAction(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // --- Action Methods ---

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int currentUserId = 1;
        List<Category> categoryList = categoryDAO.getCategoriesByUserId(currentUserId);
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/categories/index.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/categories/form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int currentUserId = 1;
        Category category = categoryDAO.getCategoryById(id, currentUserId);
        
        if (category != null) {
            request.setAttribute("category", category);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/categories/form.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Di dalam file CategoryController.java
    private void createCategoryAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        
        if (name == null || name.trim().isEmpty()) {
            // --- KIRIM ERROR ---
            request.setAttribute("errorMessage", "Category name cannot be empty.");
            // Tampilkan halaman lagi, jangan redirect
            listCategories(request, response);
            return;
        }
        
        int currentUserId = 1; 

        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setUsersId(currentUserId);
        
        categoryDAO.createCategory(newCategory);
        
        // --- KIRIM PESAN SUKSES ---
        // Cara termudah adalah mengirimnya sebagai atribut request SEBELUM forward
        request.setAttribute("successMessage", "Category created successfully!");
        listCategories(request, response);
        
        // (Cara yang lebih canggih menggunakan 'flash message' via Session,
        // tapi ini sudah cukup untuk sekarang)
    }

    private void updateCategoryAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int currentUserId = 1;

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setUsersId(currentUserId);
        
        categoryDAO.updateCategory(category);
        response.sendRedirect(request.getContextPath() + "/categories");
    }

    private void deleteCategoryAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int currentUserId = 1;
        
        categoryDAO.deleteCategory(id, currentUserId);
        response.sendRedirect(request.getContextPath() + "/categories");
    }
}