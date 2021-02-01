/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.detaihistory.DetailHistoryDAO;
import vinhnq.tblaccount.TblAccountDTO;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.tblhistory.TblHistoryDAO;
import vinhnq.tblhistory.TblHistoryDTO;
import vinhnq.tblquestion.TblQuestionDTO;
import vinhnq.utilities.HelperUtil;

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
        Connection cn = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("ACCOUNT");
                Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[] entry = (Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[]) session.getAttribute("QUIZ");
                Date startTime = (Date) session.getAttribute("START");
                long time = (long) session.getAttribute("TIME");
                long timeInMilisecond = time*1000;
                Date endTime = new Date(startTime.getTime() + timeInMilisecond);
                float total = 0;
                int numberOfCorrect = 0;
                int numberOfInCorrect = 0;
                int subjectId = -1;
                DetailHistoryDAO detailDAO = new DetailHistoryDAO();
                TblHistoryDAO historyDAO = new TblHistoryDAO();
          
                cn = HelperUtil.makeConnection();
                if (cn != null) {
                    cn.setAutoCommit(false);
                }
                boolean result = false;
                Map<Integer, Integer> detailMap = new HashMap<>();
                for (Map.Entry<TblQuestionDTO, List<TblChoiceDTO>> entry1 : entry) {
                    TblQuestionDTO dto = entry1.getKey();
                    List<TblChoiceDTO> choiceList = entry1.getValue();
                    int questionId = dto.getQuestionId();
                    if (subjectId == -1) {
                        subjectId = dto.getSubjectId();
                    }
                    float point = dto.getPoint();
                    int choiceId = -1;
                    for (TblChoiceDTO tblChoiceDTO : choiceList) {
                        if (tblChoiceDTO.isIsChoose()) {
                            choiceId = tblChoiceDTO.getChoiceId();
                            detailMap.put(questionId, choiceId);
                        }
                        if (tblChoiceDTO.isIsCorrect()) {
                            if (tblChoiceDTO.isIsChoose()) {
                                total += point;
                                numberOfCorrect++;
                            } else {
                                numberOfInCorrect++;
                            }
                        }
                    }
                    if (choiceId == -1) {
                        detailMap.put(questionId, choiceId);
                    }
                }
                result = historyDAO.addHistory(cn,account.getEmail(), subjectId, startTime, endTime, numberOfCorrect, numberOfInCorrect, total);
                if (result) {
                    int nextId = historyDAO.getNextId(cn);
                    Set<Integer> questionSet = detailMap.keySet();
                    for (Integer questionId : questionSet) {
                        int choiceId = detailMap.get(questionId);
                        result = detailDAO.addRow(cn, nextId, questionId, choiceId);
                        if (!result) {
                            break;
                        }
                    }
                    if (result) {
                        cn.commit();
                        cn.close();
                        url = HOME_PAGE;
                        session.removeAttribute("QUIZ");
                        session.removeAttribute("PAGE");
                        session.removeAttribute("TIME");
                        session.removeAttribute("START");
                        session.removeAttribute("INITTIME");
                        TblHistoryDTO dto = new TblHistoryDTO(-1, account.getEmail(), subjectId, startTime, endTime, numberOfCorrect, numberOfInCorrect, total);
                        session.setAttribute("RESULT", dto);
                    }
                    else
                    {
                        cn.rollback();
                        cn.close();
                    }
                    
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
            if(cn != null)
                try {
                    cn.close();
            } catch (SQLException ex1) {
                log("ConnectionError_Submit: "+ex1.getMessage());
            }
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
