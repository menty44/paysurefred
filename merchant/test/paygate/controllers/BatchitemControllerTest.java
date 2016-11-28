/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.io.*;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.junit.*;

/**
 *
 * @author paysure
 */
public class BatchitemControllerTest {
    //@PersistenceContext(unitName = "merchantPU")
    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public BatchitemControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception { 
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {   
        em.getTransaction().begin();
      
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
    }

    /**
     * Test of getSelected method, of class BatchitemController.
     */
    @Test
    public void upload() throws FileNotFoundException, IOException{               
        File f=new File("./test/paygate/controllers/temp.csv");
        System.out.println(f.getName());
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        String content = "";
        while ((content = br.readLine()) != null){
            System.out.println(content);
            //StringTokenizer st = new StringTokenizer(content, ",");
            
            
        }
        
        
        
    }
            
}
