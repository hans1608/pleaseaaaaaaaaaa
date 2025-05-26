/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wilbert
 */
@WebServlet(name = "ListarUsuarios", urlPatterns = {"/listarusuarios"})
public class ListarUsuarios extends HttpServlet {

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
        boolean valido = true; // cambiar si tienes validación: util.JwtUtil.validarToken(token);
        try (PrintWriter out = response.getWriter()) {
            if (!valido) {
                JSONObject error = new JSONObject();
                error.put("resultado", "error");
                error.put("mensaje", "Token inválido");
                out.print(error.toString());
                return;
            }

            EntityManagerFactory emf = null;
            EntityManager em = null;

            try {
                emf = Persistence.createEntityManagerFactory("com.mycompany_pleaseaaaaaaaaaa_war_1.0-SNAPSHOTPU");
                em = emf.createEntityManager();

                // Asumo que tienes NamedQuery "Usuario.findAll" que trae Usuario
                List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();

                JSONArray jsonArray = new JSONArray();

                for (Usuario u : usuarios) {
                    JSONObject obj = new JSONObject();
                    obj.put("codiUsua", u.getCodiUsua());
                    obj.put("ndniUsua", u.getNdniUsua());
                    obj.put("logiUsua", u.getLogiUsua());
                    obj.put("passUsua", u.getPassUsua());
                    obj.put("niveUsua", u.getNiveUsua());
                    obj.put("actiUsua", u.getActiUsua());
                    jsonArray.put(obj);
                }

                out.print(jsonArray.toString());

            } finally {
                if (em != null && em.isOpen()) em.close();
                if (emf != null && emf.isOpen()) emf.close();
            }
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
        processRequest(request, response);
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
