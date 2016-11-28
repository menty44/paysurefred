package utils;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import paygate.objects.Data;
import paygate.objects.Purchase;

public class DataItem {
    
    
    @PersistenceContext(unitName = "webdirectPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    

    public void saveToDb(String dataitems, Purchase p) {
        try {

            Data d = new Data();

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(dataitems));
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dataitem");
            utx.begin();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    d.setItemname(getTagValue("itemname", eElement));
                    BigInteger itemprice = new BigInteger(getTagValue("itemprice", eElement));
                    d.setItemprice(itemprice);
                    d.setQuantity(Integer.parseInt(getTagValue("quantity", eElement)));
                    BigInteger subtotal = new BigInteger(getTagValue("subtotal", eElement));
                    d.setSubtotal(subtotal);
                    d.setPurchaseid(p);

                    //DataJpaController djp = new DataJpaController();
                    //djp.create(d); 

                    em.persist(d);
                    utx.commit();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(DataItem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
}
