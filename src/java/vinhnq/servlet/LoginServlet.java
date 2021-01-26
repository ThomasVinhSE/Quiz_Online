/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.tblaccount.TblAccountDAO;
import vinhnq.tblaccount.TblAccountDTO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HOME_PAGE = "home.jsp";
    private final String ADMIN_PAGE = "adminHome.jsp";

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
        String url = ERROR_PAGE;
        String txtEmail = request.getParameter("txtEmail");
        String txtPassword = request.getParameter("txtPassword");
        try {
            TblAccountDAO dao = new TblAccountDAO();
            if (txtEmail != null && txtPassword != null) {
                TblAccountDTO dto = dao.checkLogin(txtEmail, txtPassword);
                if(dto != null)
                {
                    int roleId = dto.getRoleId();
                    HttpSession session = request.getSession();
                    Enumeration<String> attr = session.getAttributeNames();
                    while(attr.hasMoreElements())
                    {
                        String name = attr.nextElement();
                        session.removeAttribute(name);
                    }
                    session.setAttribute("ACCOUNT", dto);
                    if(roleId == 1)
                        url = HOME_PAGE;
                    else
                        url = ADMIN_PAGE;
                }
                else
                    request.setAttribute("MESSAGE", "Account not exits !!!");
            }
            else
                request.setAttribute("MESSAGE", "Invalid Email Or Password (null) !!!");

        } catch (NamingException ex) {
           log("Naming_Login: "+ex.getMessage());
        } catch (SQLException ex) {
            log("SQL_Login: "+ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log("NoSuchAlgorithm_Login: "+ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
