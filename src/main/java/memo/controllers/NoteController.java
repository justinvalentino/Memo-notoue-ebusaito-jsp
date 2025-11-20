package memo.controllers;

import memo.dao.NoteDAO;
import memo.dao.CategoryDAO;
import memo.models.Note;
import memo.models.Category;
import memo.models.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class NoteController extends HttpServlet {
    
    private NoteDAO noteDAO;
    private CategoryDAO categoryDAO;

    public void init() {
        noteDAO = new NoteDAO();
        categoryDAO = new CategoryDAO();
    }

    private User getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("authUser");
        }
        return null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = getAuthUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            listNotes(request, response, user);
        } else if (pathInfo.equals("/new")) {
            showNewNoteForm(request, response, user);
        } else if (pathInfo.equals("/edit")) {
            showEditNoteForm(request, response, user);
        } else if (pathInfo.equals("/delete")) {
            deleteNoteAction(request, response, user);
        } else if (pathInfo.equals("/archive")) {
            archiveNoteAction(request, response, user);
        } else if (pathInfo.equals("/trash")) {
            listTrashNotes(request, response, user);
        } else if (pathInfo.equals("/restore")) {
            restoreNoteAction(request, response, user);
        } else if (pathInfo.equals("/force-delete")) {
            forceDeleteNoteAction(request, response, user);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Handles POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = getAuthUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.equals("/create")) {
            createNoteAction(request, response, user);
        } else if (pathInfo != null && pathInfo.equals("/update")) {
            updateNoteAction(request, response, user);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void listNotes(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("search");
        List<Note> notesList = noteDAO.getNotesByUserId(user.getId(), searchTerm);
        
        request.setAttribute("notesList", notesList);
        request.setAttribute("searchTerm", searchTerm);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/notes/index.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewNoteForm(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        // Load categories untuk dropdown
        List<Category> categories = categoryDAO.getCategoriesByUserId(user.getId());
        request.setAttribute("categoriesList", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/notes/form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditNoteForm(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Note existingNote = noteDAO.getNoteById(id, user.getId());

            if (existingNote != null) {
                request.setAttribute("note", existingNote);
                
                // Load categories untuk dropdown
                List<Category> categories = categoryDAO.getCategoriesByUserId(user.getId());
                request.setAttribute("categoriesList", categories);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/notes/edit-form.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void createNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        Integer categoryId = null;
        try {
            String catIdParam = request.getParameter("categoryId");
            if (catIdParam != null && !catIdParam.isEmpty()) {
                categoryId = Integer.parseInt(catIdParam);
            }
        } catch (NumberFormatException e) { /* ignore */ }

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        newNote.setUsersId(user.getId());
        newNote.setCategoriesId(categoryId);
        
        noteDAO.createNote(newNote);
        response.sendRedirect(request.getContextPath() + "/notes");
    }

    private void updateNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            
            Integer categoryId = null;
            try {
                String catIdParam = request.getParameter("categoryId");
                if (catIdParam != null && !catIdParam.isEmpty()) {
                    categoryId = Integer.parseInt(catIdParam);
                }
            } catch (NumberFormatException e) {}

            Note noteToUpdate = new Note();
            noteToUpdate.setId(id);
            noteToUpdate.setTitle(title);
            noteToUpdate.setContent(content);
            noteToUpdate.setCategoriesId(categoryId);
            noteToUpdate.setUsersId(user.getId());
            
            noteDAO.updateNote(noteToUpdate);
            response.sendRedirect(request.getContextPath() + "/notes");

        } catch (NumberFormatException e) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void deleteNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            noteDAO.deleteNote(id, user.getId());
            response.sendRedirect(request.getContextPath() + "/notes");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void archiveNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            noteDAO.archiveNote(id, user.getId());
            response.sendRedirect(request.getContextPath() + "/notes");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void listTrashNotes(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        List<Note> archivedNotes = noteDAO.getArchivedNotesByUserId(user.getId());
        request.setAttribute("notesList", archivedNotes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/notes/trash.jsp");
        dispatcher.forward(request, response);
    }

    private void restoreNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            noteDAO.restoreNote(id, user.getId());
            response.sendRedirect(request.getContextPath() + "/notes/trash");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void forceDeleteNoteAction(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            noteDAO.forceDeleteNote(id, user.getId());
            response.sendRedirect(request.getContextPath() + "/notes/trash");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}