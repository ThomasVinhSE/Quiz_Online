/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String SEARCH_SERVLET = "SearchServlet";
    private final String UPDATE_PAGE = "update.jsp";

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

        String answerId1 = request.getParameter("answer1_Id");
        String answerId2 = request.getParameter("answer2_Id");
        String answerId3 = request.getParameter("answer3_Id");
        String answerId4 = request.getParameter("answer4_Id");

        String rdOption = request.getParameter("rdOption");
        String txtPoint = request.getParameter("txtPoint");
        String txtSubject = request.getParameter("txtSubject1");
        String txtContent = request.getParameter("txtContent");
        String chkActive = request.getParameter("chkActive");
        String txtQuestionId = request.getParameter("txtQuestionId");
        try {
            int questionId = Integer.parseInt(txtQuestionId);
            boolean isValid = true;
            isValid = (txtAnswer1 != null
                    && txtAnswer2 != null
                    && txtAnswer3 != null
                    && txtAnswer4 != null
                    && answerId1 != null
                    && answerId2 != null
                    && answerId3 != null
                    && answerId4 != null
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
                        TblQuestionDTO questionDTO = new TblQuestionDTO(questionId, txtContent, new Date(), subjectId, point, (chkActive != null) ? true : false);
                        List<TblChoiceDTO> list = new ArrayList<>();
                        TblChoiceDTO dto = new TblChoiceDTO(Integer.parseInt(answerId1), -1, txtAnswer1, correctIndex == 1 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(Integer.parseInt(answerId2), -1, txtAnswer2, correctIndex == 2 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(Integer.parseInt(answerId3), -1, txtAnswer3, correctIndex == 3 ? true : false);
                        list.add(dto);
                        dto = new TblChoiceDTO(Integer.parseInt(answerId4), -1, txtAnswer4, correctIndex == 4 ? true : false);
                        list.add(dto);
                        TblQuestionDAO questionDAO = new TblQuestionDAO();
                        boolean result = questionDAO.updateQuestion(questionDTO, list);
                        if (result) {
                            String isSearch = request.getParameter("isSearch");
                            if (isSearch == null) {
                                url = null;
                                response.sendRedirect(UPDATE_PAGE);
                            } else {
                                url = SEARCH_SERVLET;
                            }
                        } else {
                            request.setAttribute("MESSAGE", "Update fail, try again !!!");
                        }
                    }
                } else {
                    request.setAttribute("MESSAGE", "Not found subject name");
                }
            } else {
                request.setAttribute("MESSAGE", "Some infor was lost, please fill all fields !!!");
            }

        } catch (NumberFormatException e) {
            log("NumberFormat_CreateQuestion: " + e.getMessage());
            request.setAttribute("MESSAGE", "Some update infor isn't a number, try again!!!");
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
