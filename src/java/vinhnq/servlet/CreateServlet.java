/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.tblquestion.TblQuestionDAO;
import vinhnq.tblquestion.TblQuestionDTO;
import vinhnq.tblsubject.TblSubjectDAO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String CREATE_PAGE = "create.jsp";

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
        String txtAnswer1 = request.getParameter("txtAnswer1");
        String txtAnswer2 = request.getParameter("txtAnswer2");
        String txtAnswer3 = request.getParameter("txtAnswer3");
        String txtAnswer4 = request.getParameter("txtAnswer4");
        String rdOption = request.getParameter("rdOption");
        String txtPoint = request.getParameter("txtPoint");
        String txtSubject = request.getParameter("txtSubject");
        String txtContent = request.getParameter("txtContent");
        try {
            boolean isValid = true;
            isValid = (txtAnswer1 != null
                    && txtAnswer2 != null
                    && txtAnswer3 != null
                    && txtAnswer4 != null
                    && rdOption != null
                    && txtPoint != null
                    && txtSubject != null
                    && txtContent != null)
                            ? true : false;
            if (isValid) {
                float point = Float.parseFloat(txtPoint);
                TblSubjectDAO subjectDAO = new TblSubjectDAO();
                int subjectId = subjectDAO.findSubjectIdByName(txtSubject);
                if (subjectId != -1) {
                    int correctIndex = Integer.parseInt(rdOption);
                    if (correctIndex > 4 || correctIndex < 1) {
                        request.setAttribute("MESSAGE", "CorrectIndex have to  >= 1 and <= 4  ");
                    } else {
                        TblQuestionDTO questionDTO = new TblQuestionDTO(-1, txtContent, new Date(), subjectId, point, true);
                        List<TblChoiceDTO> list = new ArrayList<>();
                        TblChoiceDTO dto = new TblChoiceDTO(-1, -1, txtAnswer1, correctIndex == 1 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(-1, -1, txtAnswer2, correctIndex == 2 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(-1, -1, txtAnswer3, correctIndex == 3 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(-1, -1, txtAnswer4, correctIndex == 4 ? true : false);
                        list.add(dto);
                        TblQuestionDAO questionDAO = new TblQuestionDAO();
                        boolean result = questionDAO.createQuestion(questionDTO, list);
                        if (result) {
                            url = null;
                            response.sendRedirect(CREATE_PAGE);
                        } else {
                            request.setAttribute("MESSAGE", "Insert fail, try again !!!");
                        }
                    }
                } else {
                    request.setAttribute("MESSAGE", "Not found subject name");
                }
            }
            else
                request.setAttribute("MESSAGE", "Some infor was lost, please fill all fields !!!");

        } catch (NumberFormatException e) {
            log("NumberFormat_CreateQuestion: " + e.getMessage());
            request.setAttribute("MESSAGE", "Point or CorrectIndex isn't number");
        } catch (NamingException ex) {
            log("Naming_Create: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Sorry....Try again !!!");
        } catch (SQLException ex) {
            log("SQL_Create: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Something wrong with conflit data, try again !!!");
        } finally {
            if (url != null) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
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
