/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exoticatravels;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author Mhd Najw
 */
public class RegistrationAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
            Connector cn = new Connector();
            Connection con = cn.createConnection();
            
            RegistrationBean rBean = (RegistrationBean)form;
            String uName = rBean.getUsername();
            String uID = rBean.getUserid();
            String pwd = rBean.getPassword();
            String rpwd = rBean.getReenterpassword();
            String address = rBean.getAddress();
            String state = rBean.getState();
            String country = rBean.getCountry();
            String group="costumer";
            String msg;
            Statement checkUserID = con.createStatement();
            ResultSet rs = checkUserID.executeQuery("Select UserName From Registration WHERE UserID='"+uID+"'");

            if (rs.next())
        {
                msg = "Specified User ID alredy exists";
		request.setAttribute("errMsg",msg);
		return mapping.findForward(ERROR);
        }
            else if (!pwd.equals(rpwd) || pwd=="")
	{
		msg = "Password not entered or entered passwords do not match";
		request.setAttribute("errMsg",msg);
		return mapping.findForward(ERROR);
	}
            else
	{
                String insertQuery = "INSERT INTO Registration VALUES('"+uName+"','"+uID+"','"+pwd+"','"+address+"','"+state+"','"+country+"','"+group+"')";
                System.out.println(insertQuery);
                Statement addUser = con.createStatement();
                addUser.executeUpdate(insertQuery);
                request.setAttribute("NewUser", uName);
        }        
                return mapping.findForward(SUCCESS);
    }
}
