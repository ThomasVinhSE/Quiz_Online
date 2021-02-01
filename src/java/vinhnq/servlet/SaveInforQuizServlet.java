/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.tblquestion.TblQuestionDTO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "SaveInforQuizServlet", urlPatterns = {"/SaveInforQuizServlet"})
public class SaveInforQuizServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HOME_PAGE = "home.jsp";
    private final String SUBMIT_SERVLET = "SubmitServlet";

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
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[] entry = (Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[]) session.getAttribute("QUIZ");
                if (entry != null && entry.length > 0) {
                    String txtPage = request.getParameter("txtPage");
                    String rdOption1 = request.getParameter("rdOption1");
                    String rdOption2 = request.getParameter("rdOption2");
                    String question1 = request.getParameter("questionId1");
                    String question2 = request.getParameter("questionId2");
                    int page = Integer.parseInt(txtPage);
                    int index = 2 * (page - 1);
                    if (index < 0 || index >= entry.length) {
                        request.setAttribute("MESSAGE", "Select index out of range");
                    } else {
                        Date realTime = (Date) session.getAttribute("START");
                        Date currentDate = new Date();
                        long time = (currentDate.getTime() - realTime.getTime())/1000;
                        session.setAttribute("TIME", time);
                        if (question1 != null) {
                            int option1 = rdOption1 != null ? Integer.parseInt(rdOption1) : 0;
                            int idForFirst = Integer.parseInt(question1);
                            if (option1 > 4 || option1 < 0) {
                                option1 = 0;
                            }
                            Map.Entry<TblQuestionDTO, List<TblChoiceDTO>> question = entry[index];
                            int questionId = question.getKey().getQuestionId();
                            if (idForFirst == questionId) {
                                if (option1 != 0) {
                                    question.getValue().get(option1 - 1).setIsChoose(true);
                                }
                                url = HOME_PAGE;
                            }

                        }
                        if (rdOption2 != null && question2 != null) {
                            int option2 = rdOption2 != null ? Integer.parseInt(rdOption2) : 0;
                            int idForSecond = Integer.parseInt(question2);
                            if (option2 > 4 || option2 < 0) {
                                option2 = 0;
                            }
                            Map.Entry<TblQuestionDTO, List<TblChoiceDTO>> question = entry[index + 1];
                            int questionId = question.getKey().getQuestionId();
                            if (idForSecond == questionId && option2 != 0) {
                                if (option2 != 0) {
                                    question.getValue().get(option2 - 1).setIsChoose(true);
                                }
                                url = HOME_PAGE;
                            }
                        }
                        if (request.getParameter("isSubmit") != null) {
                            url = null;
                            response.sendRedirect(SUBMIT_SERVLET);
                        }
                    }

                } else {
                    request.setAttribute("MESSAGE", "No current quiz is taking !!!, try again");
                }
            }
        } catch (NumberFormatException e) {
            log("NumberFormat_SaveInfor: " + e.getMessage());
            request.setAttribute("MESSAGE", "Some input field must a number");
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
