/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.tblaccount.TblAccountDTO;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.tblhistory.TblHistoryDAO;
import vinhnq.tblhistory.TblHistoryDTO;
import vinhnq.tblquestion.TblQuestionDTO;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "SubmitServlet", urlPatterns = {"/SubmitServlet"})
public class SubmitServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HOME_PAGE = "home.jsp";

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
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("ACCOUNT");
                Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[] entry = (Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[]) session.getAttribute("QUIZ");
                Date startTime = (Date) session.getAttribute("START");
                String txtTimeQuiz = request.getParameter("txtTimeQuiz");
                float time = txtTimeQuiz != null ? Float.parseFloat(txtTimeQuiz) : 1;
                int timeInMilisecond = (60*60-(int) Math.floor(time * 60)) * 1000;
                Date endTime = new Date(startTime.getTime() + timeInMilisecond);
                float total = 0;
                int numberOfCorrect = 0;
                int numberOfInCorrect = 0;
                int subjectId = -1;
                for (Map.Entry<TblQuestionDTO, List<TblChoiceDTO>> entry1 : entry) {
                    TblQuestionDTO dto = entry1.getKey();
                    List<TblChoiceDTO> choiceList = entry1.getValue();
                    if (subjectId == -1) {
                        subjectId = dto.getSubjectId();
                    }
                    float point = dto.getPoint();
                    for (TblChoiceDTO tblChoiceDTO : choiceList) {
                        if (tblChoiceDTO.isIsCorrect()) {
                            if (tblChoiceDTO.isIsChoose()) {
                                total += point;
                                numberOfCorrect++;
                            } else {
                                numberOfInCorrect++;
                            }
                        }
                    }
                }
                TblHistoryDAO historyDAO = new TblHistoryDAO();
                boolean result = historyDAO.addHistory(account.getEmail(), subjectId, startTime, endTime, numberOfCorrect, numberOfInCorrect, total);
                if (result) {
                    url = HOME_PAGE;
                    session.removeAttribute("QUIZ");
                    session.removeAttribute("PAGE");
                    session.removeAttribute("TIME");
                    session.removeAttribute("START");
                    TblHistoryDTO dto = new TblHistoryDTO(-1, account.getEmail(), subjectId, startTime, endTime, numberOfCorrect, numberOfInCorrect, total);
                    session.setAttribute("RESULT", dto);
                } else {
                    request.setAttribute("MESSAGE", "Can't insert history");
                }
            }
        } catch (NumberFormatException e) {
            log("NumberFormat_Submit: " + e.getMessage());
            request.setAttribute("MESSAGE", "Time is not a number");
        } catch (NamingException ex) {
            log("Naming_Submit: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Some error occured, try again!!");
        } catch (SQLException ex) {
            log("SQL_Submit: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Data conflict, try again !!!");
        } finally {
            if (url.equals(ERROR_PAGE)) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
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
