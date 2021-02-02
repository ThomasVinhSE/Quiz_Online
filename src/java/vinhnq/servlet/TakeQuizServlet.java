/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.tblquestion.TblQuestionDAO;
import vinhnq.tblquestion.TblQuestionDTO;
import vinhnq.tblsubject.TblSubjectDTO;
import vinhnq.utilities.PagingModel;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "TakeQuizServlet", urlPatterns = {"/TakeQuizServlet"})
public class TakeQuizServlet extends HttpServlet {

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
        String txtSubjectId = request.getParameter("txtSubjectId");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                if (session.getAttribute("QUIZ") != null) {
                    Date realTime = (Date) session.getAttribute("START");
                    Date currentDate = new Date();
                    long time = (currentDate.getTime() - realTime.getTime()) / 1000;
                    session.setAttribute("TIME", time);
                    url = HOME_PAGE;
                } else {
                    boolean isValid = (txtSubjectId != null) ? true : false;
                    if (isValid) {
                        int id = Integer.parseInt(txtSubjectId);
                        ServletContext context = request.getServletContext();
                        List<TblSubjectDTO> list = (List<TblSubjectDTO>) context.getAttribute("SUBJECT");
                        if (list != null) {
                            boolean check = false;
                            int time = -1;
                            int number = -1;
                            String subject = "";
                            for (TblSubjectDTO tblSubjectDTO : list) {
                                int subjectId = tblSubjectDTO.getSubjectId();
                                if (subjectId == id) {
                                    subject = tblSubjectDTO.getName();
                                    time = tblSubjectDTO.getTimeForQuiz();
                                    number = tblSubjectDTO.getNumOfQuestion();
                                    check = true;
                                    break;
                                }
                            }
                            if (check) {
                                TblQuestionDAO dao = new TblQuestionDAO();
                                dao.getQuestionForMap2(id, number);
                                Map<TblQuestionDTO, List<TblChoiceDTO>> map = dao.getMap();
                                if (map != null) {
                                    if (map.size() == number) {
                                        Set<Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>> entrySet = map.entrySet();
                                        Map.Entry<TblQuestionDTO, List<TblChoiceDTO>>[] entry = entrySet.toArray(new Map.Entry[entrySet.size()]);
                                        Date realTime = new Date();
                                        session.setAttribute("START", new Date());
                                        session.setAttribute("QUIZ", entry);
                                        session.setAttribute("TIME", 0);
                                        int page = PagingModel.getNumberOfPage(map.keySet().size(), 2);
                                        session.setAttribute("PAGE", page);
                                        session.setAttribute("INITTIME", time);
                                        url = HOME_PAGE;
                                    } else {
                                        url = HOME_PAGE + "?txtSubjectId=" + txtSubjectId + "&txtNumber=" + number + "&txtTime=" + time
                                                + "&isCheck=" + subject + "&txtMessage=" + "Not enough question to take quiz !!!";
                                        response.sendRedirect(url);
                                        url = null;
                                    }
                                } else {
                                    request.setAttribute("MESSAGE", "Not found any question !!!");
                                }
                            } else {
                                request.setAttribute("MESSAGE", "Not found by subjectId");
                            }
                        } else {
                            request.setAttribute("MESSAGE", "No subject current in system");
                        }
                    } else {
                        request.setAttribute("MESSAGE", "SubjectId input field was lost, try again !!!");
                    }
                }
            }
        } catch (NumberFormatException e) {
            log("NumberFormat_TakeQuiz: " + e.getMessage());
            request.setAttribute("MESSAGE", "SubjectId must a number");
        } catch (NamingException ex) {
            log("Naming_TakeQuiz: " + ex.getMessage());
            request.setAttribute("MESSAGE", "System have error, try again !!!");
        } catch (SQLException ex) {
            log("SQL_TakeQuiz: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Data conflict, try again !!!");
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
