/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vinhnq.tblaccount.ErrorRegisterBean;
import vinhnq.tblaccount.TblAccountDAO;
import vinhnq.tblaccount.TblAccountDTO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String REGISTER_PAGE = "register.jsp";
    private final String LOGIN_PAGE = "login.jsp";
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
        String txtEmail = request.getParameter("txtEmail");
        String txtPassword = request.getParameter("txtPassword");
        String txtConfirmPassword = request.getParameter("txtConfirmPassword");
        String txtName = request.getParameter("txtName");
        ErrorRegisterBean error = new ErrorRegisterBean();
        boolean isError = false;
        String url = ERROR_PAGE;
        try {
            if (txtEmail != null && !txtEmail.matches("[^@]*[^.@]+@[A-Za-z0-9]+([.][a-zA-Z0-9]+){1,2}")) {
                error.setErrorEmail("Wrong format of email abc123@abc123.abc123(.abc123)");
                isError = true;
            } else if (txtEmail == null || txtEmail.equals("")) {
                isError = true;
                error.setErrorEmail("Not input email yet");
            }
            if(txtName == null || txtName.equals(""))
            {
                error.setErrorName("Not input Name yet");
                isError = true;
            }
            if (txtPassword != null && !txtPassword.equals("")) {
                if (txtConfirmPassword != null && !txtConfirmPassword.equals("")) {
                    if (!txtConfirmPassword.equals(txtPassword)) {
                        error.setErrorMatching("Password and ConfirmPassword must matching");
                        isError = true;
                    }
                }
                else
                {
                    error.setErrorConfirmPassword("Not input Confirm Password yet");
                    isError = true;
                }

            } else {
                error.setErrorPassword("Not input Password yet");
                isError = true;
            }
            if(!isError)
            {
                TblAccountDAO dao = new TblAccountDAO();
                TblAccountDTO dto = new TblAccountDTO(txtEmail, txtName, txtPassword, 0, "New");
                boolean result = dao.register(dto);
                if(result)
                   url = LOGIN_PAGE;
            }
            else
            {
                url = REGISTER_PAGE;
                request.setAttribute("ERROR", error);
            }
        } catch (NamingException ex) {
            log("Naming_Register: "+ex.getMessage());
            request.setAttribute("MESSAGE", "Sorry....Try again!");
        } catch (SQLException ex) {
            log("SQL_Register: "+ex.getMessage());
            if(ex.getMessage().contains("duplicate"))
            {
                url = REGISTER_PAGE;
                error.setIsDuplicate("This email was existed");
                request.setAttribute("ERROR", error);
            }
            else
                request.setAttribute("MESSAGE", "Sorry....Try again!");
        } catch (NoSuchAlgorithmException ex) {
            log("NoSuchAlgorithm_Register: "+ex.getMessage());
            request.setAttribute("MESSAGE", "Sorry....Try again!");
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
