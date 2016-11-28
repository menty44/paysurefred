/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import pg.objects.*;

/**
 *
 * @author Joseph
 */
@Stateless
@LocalBean
public class Process {
    @EJB
    private StatusFacade statusFacade;

    public StatusFacade getStatusFacade() {
        return statusFacade;
    }

    public void setStatusFacade(StatusFacade statusFacade) {
        this.statusFacade = statusFacade;
    }
        
    //Work work;
    PrintWriter out = null;
    Status status;
       
    @Schedule(minute = "*", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "9-17", dayOfWeek = "*")
    public void myTimer() throws IOException {
        System.out.println("Timer event from dailyProcessor: " + new Date());
        //Query query = em.createNamedQuery("Status.findById");
        //query.setParameter("id", 5);
        //Status status = (Status) query.getSingleResult();
        status=getStatusFacade().find(5);
        System.out.println(status.getDescription());

    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
