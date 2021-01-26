/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
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
import vinhnq.utilities.PagingModel;

/**
 *
 * @author Vinh
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String SEARCH_PAGE = "adminHome.jsp";
    private final String UPDATE_PAGE = "update.jsp";
    private final String DELETE_PAGE = "delete.jsp";

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
        String isUpdate = request.getParameter("isUpdate");
        String isDelete = request.getParameter("isDelete");

        try {
            if (isDelete != null || isUpdate != null) {
                String questionId = request.getParameter("txtQuestionId");
                if (questionId != null) {
                    int id = Integer.parseInt(questionId);
                    TblQuestionDAO dao = new TblQuestionDAO();
                    Pair<TblQuestionDTO, List<TblChoiceDTO>> pair = dao.searchById(id);
                    if (isDelete != null) {
                        url = DELETE_PAGE;
                    } else {
                        url = UPDATE_PAGE;
                    }
                    request.setAttribute("PAIR", pair);
                } else {
                    request.setAttribute("MESSAGE", "Not input question id yet !!!");
                }

            } else {
                String txtQuestionName = request.getParameter("txtQuestionName");
                String subjectName = request.getParameter("txtSubject");
                String status = request.getParameter("txtStatus");
                String txtIndex = request.getParameter("txtIndex");
                System.out.println(txtQuestionName+"-"+subjectName+"-"+status+"-"+txtIndex);
                boolean isValid = (txtQuestionName != null
                        && subjectName != null
                        && status != null 
                        && txtIndex != null ) ? true : false;
                if(isValid) 
                {
                    int checkActive = Integer.parseInt(status);
                    int index = Integer.parseInt(txtIndex);
                    if (checkActive == 0 || checkActive == 1) {
                        TblSubjectDAO subjectDAO = new TblSubjectDAO();
                        int subjectId = subjectDAO.findSubjectIdByName(subjectName);
                        if (subjectId != -1)
                        {
                            TblQuestionDAO questionDAO = new TblQuestionDAO();
                            int size = questionDAO.getQuestionForMap(txtQuestionName, subjectId, checkActive == 1? true : false,index);
                            Map<TblQuestionDTO, List<TblChoiceDTO>> map = questionDAO.getMap();
                            request.setAttribute("MAP", map);
                            int numberOfPage = PagingModel.getNumberOfPage(size, 10);
                            request.setAttribute("PAGE", numberOfPage);
                            url = SEARCH_PAGE;
                        } 
                        else 
                        {
                            request.setAttribute("MESSAGE", "Not found subjectId, try again !!!");
                        }
                    } 
                    else
                        request.setAttribute("MESSAGE", "Not found right input status, try again !!!");
                }
                else
                    request.setAttribute("MESSAGE", "Some input field be wrong, try again !!!");

            }
        } catch (NumberFormatException e) {
            log("NumberFormat_Search: " + e.getMessage());
            request.setAttribute("MESSAGE", "Some input field isn't a number ( format )");
        } catch (NamingException ex) {
            log("Naming_Search: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Sorry....Try again !!!");
        } catch (SQLException ex) {
            log("SQL_Search: " + ex.getMessage());
            request.setAttribute("MESSAGE", "Something wrong with conflit data, try again !!!");
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
