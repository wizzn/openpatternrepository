package nl.rug.search.opr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.rug.search.opr.entities.pattern.Pattern;
import nl.rug.search.opr.entities.pattern.Tag;
import nl.rug.search.opr.pattern.TagLocal;

/**
 *
 * @author Ben Ripkens <bripkens.dev@gmail.com>
 */
public class TagServlet extends HttpServlet {

    @EJB
    private TagLocal tagLocal;




    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            String tagParam = request.getParameter("q");

            if (tagParam == null) {
                out.write("[]");
                return;
            }

            tagParam = tagParam.trim();

            if (tagParam.isEmpty()) {
                out.write("[]");
                return;
            }

            Tag tag = tagLocal.getByName(tagParam);

            if (tag == null) {
                out.write("[]");
                return;
            }

            out.write("[");
            Pattern currentPattern = null;
            for (Iterator<Pattern> it = tag.getTagPatterns().iterator(); it.
                    hasNext();) {
                currentPattern = it.next();

                String name = currentPattern.getName().replace("\"", "\\\"");
                out.write("{\"name\":\"");
                out.write(name);
                out.write("\", \"slug\":\"");
                out.write(currentPattern.getUniqueName());
                out.write("\"}");

                if (it.hasNext()) {
                    out.write(",");
                }
            }
            out.write("]");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">



    /**
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>




}



