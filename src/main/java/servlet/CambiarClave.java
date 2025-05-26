/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UsuarioJpaController;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wilbert
 */
@WebServlet(name = "CambiarClave", urlPatterns = {"/cambiarclave"})
public class CambiarClave extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String logiUsua = request.getParameter("logiUsua");
        String passUsua = request.getParameter("passUsua");   // SHA-1 desde el cliente
        String nuevaPass = request.getParameter("newPass");   // SHA-1 desde el cliente

        try {
            if (logiUsua == null || passUsua == null || nuevaPass == null
                    || logiUsua.isEmpty() || passUsua.isEmpty() || nuevaPass.isEmpty()) {
                out.print("{\"resultado\": \"error\", \"mensaje\": \"Parámetros incompletos\"}");
                return;
            }

            UsuarioJpaController dao = new UsuarioJpaController();
            Usuario usuario = dao.buscarPorLogin(logiUsua);

            if (usuario == null) {
                out.print("{\"resultado\": \"error\", \"mensaje\": \"Usuario no encontrado\"}");
                return;
            }

            if (!usuario.getPassUsua().equals(passUsua)) {
                out.print("{\"resultado\": \"error\", \"mensaje\": \"La contraseña actual es incorrecta\"}");
                return;
            }

            // Cambiar la contraseña
            usuario.setPassUsua(nuevaPass);
            dao.edit(usuario);
            out.print("{\"resultado\": \"ok\", \"mensaje\": \"Contraseña cambiada exitosamente\"}");

        } catch (Exception e) {
            e.printStackTrace();  // Para debugging en servidor
            out.print("{\"resultado\": \"error\", \"mensaje\": \"Error interno al actualizar la contraseña\"}");
        } finally {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
