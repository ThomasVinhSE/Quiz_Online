/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vinhnq.tblaccount.TblAccountDTO;
import vinhnq.utilities.HelperUtil;

/**
 *
 * @author Vinh
 */
@WebFilter(filterName = "JSPFilter", urlPatterns = {"/*"})
public class JSPFilter implements Filter {

    private static final boolean debug = true;
    private static final String ERROR_PAGE = "error.jsp";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public JSPFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("JSPFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("JSPFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("JSPFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        String url = null;
        try {
            String uri = ((HttpServletRequest) request).getRequestURI();
            int lastOfURI = uri.lastIndexOf("/");
            String requestURI = uri.substring(lastOfURI + 1);
            boolean isAuthorized = false;
            if (requestURI.endsWith(".jsp")) {
                Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("JSPMAP");
                if (map != null && map.containsKey(requestURI)) {

                    String value = map.get(requestURI);
                    int index = value.lastIndexOf("?");
                    if (index == -1) {
                        url = value;
                    } else {
                        HttpSession session = ((HttpServletRequest) request).getSession(false);
                        if (session != null) {
                            TblAccountDTO dto = (TblAccountDTO) session.getAttribute("ACCOUNT");
                            if (dto != null) {
                                int roleId = dto.getRoleId();
                                String role = value.substring(index + 1);
                                if (role.equals("admin") && roleId != 0) {
                                    request.setAttribute("MESSAGE", "Student can't access admin page");
                                    isAuthorized = true;
                                }
                                if (role.equals("student") && roleId != 1) {
                                    request.setAttribute("MESSAGE", "Admin can't access student page");
                                    isAuthorized = true;
                                }
                                if(!isAuthorized)
                                    url = value.substring(0, index);
                            }
                            else
                            {
                                isAuthorized = true;
                                request.setAttribute("MESSAGE", "Not Login yet !!!");
                            }
                        }
                    }
                    if (url == null) {
                        url = ERROR_PAGE;
                    }
                } else {
                    url = ERROR_PAGE;
                }
                if (url.equals(ERROR_PAGE)) {
                    if(!isAuthorized)
                        request.setAttribute("MESSAGE", "Not Found Your Requirement !!!");
                }

                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);

            } else {
                chain.doFilter(request, response);
            }

        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("JSPFilter:Initializing filter");
            }
            Map<String, String> jspMap;
            try {
                ServletContext context = filterConfig.getServletContext();
                jspMap = HelperUtil.listOfJSPFile(context.getRealPath(""));
                filterConfig.getServletContext().setAttribute("JSPMAP", jspMap);
            } catch (IOException ex) {
                log("IOException_Init_JSPFilter: " + ex.getMessage());
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("JSPFilter()");
        }
        StringBuffer sb = new StringBuffer("JSPFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
