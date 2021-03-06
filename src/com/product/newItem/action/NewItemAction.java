package com.product.newItem.action;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.product.newItem.dao.NewItemDao;
import com.product.newItem.service.NewItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XIJ on 4/10/2016.
 */
public class NewItemAction extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private NewItemService service;

    /**
     * Constructor of the object.
     */
    public NewItemAction() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        if (DriverManager.getDrivers().nextElement()!=null){
            try{
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Find new Items!!!");
        String path = request.getContextPath();
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        List<Map<String, Object>> items= service.showNewItems();
        System.out.println("items: "+items);
        if(items!=null){
            System.out.println("New Items!!!");
            request.getSession().setAttribute("items", items);
            response.sendRedirect(path + "/index.jsp");
        }else{
            items = new ArrayList<>();
            request.getSession().setAttribute("items", items);
            System.out.println("No Items....");
            response.sendRedirect(path + "/index.jsp");
        }
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
        service = new NewItemDao();
    }
}
