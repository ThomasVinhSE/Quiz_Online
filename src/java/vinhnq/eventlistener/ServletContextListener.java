/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.eventlistener;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import vinhnq.tblsubject.TblSubjectDAO;

/**
 *
 * @author Vinh
 */
public class ServletContextListener implements javax.servlet.ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            TblSubjectDAO subjectDAO = new TblSubjectDAO();
            subjectDAO.getAllForList();
            ServletContext context = sce.getServletContext();
            context.setAttribute("SUBJECT", subjectDAO.getList());
        } catch (NamingException ex) {
            Logger.getLogger(ServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
