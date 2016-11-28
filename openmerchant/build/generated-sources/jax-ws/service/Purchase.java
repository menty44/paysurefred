
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for purchase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clientname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="genderid" type="{http://service/}gender" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantid" type="{http://service/}merchant" minOccurs="0"/>
 *         &lt;element name="paysuredate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="purchasedate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="recievinginstitution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secret" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shippingcost" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="statusid" type="{http://service/}status" minOccurs="0"/>
 *         &lt;element name="systemtraceno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionsourceid" type="{http://service/}transactionsource" minOccurs="0"/>
 *         &lt;element name="transactiontype" type="{http://service/}transactiontype" minOccurs="0"/>
 *         &lt;element name="transmission" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visits" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchase", propOrder = {
    "age",
    "amount",
    "clientname",
    "currency",
    "description",
    "email",
    "genderid",
    "id",
    "merchantid",
    "paysuredate",
    "purchasedate",
    "recievinginstitution",
    "referenceno",
    "secret",
    "shippingcost",
    "statusid",
    "systemtraceno",
    "token",
    "transactionsourceid",
    "transactiontype",
    "transmission",
    "visits"
})
public class Purchase {

    protected Integer age;
    protected Integer amount;
    protected String clientname;
    protected String currency;
    protected String description;
    protected String email;
    protected Gender genderid;
    protected Integer id;
    protected Merchant merchantid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar paysuredate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar purchasedate;
    protected String recievinginstitution;
    protected String referenceno;
    protected String secret;
    protected Integer shippingcost;
    protected Status statusid;
    protected String systemtraceno;
    protected String token;
    protected Transactionsource transactionsourceid;
    protected Transactiontype transactiontype;
    protected String transmission;
    protected Integer visits;

    /**
     * Gets the value of the age property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAge(Integer value) {
        this.age = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmount(Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the clientname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientname() {
        return clientname;
    }

    /**
     * Sets the value of the clientname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientname(String value) {
        this.clientname = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the genderid property.
     * 
     * @return
     *     possible object is
     *     {@link Gender }
     *     
     */
    public Gender getGenderid() {
        return genderid;
    }

    /**
     * Sets the value of the genderid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gender }
     *     
     */
    public void setGenderid(Gender value) {
        this.genderid = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the merchantid property.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getMerchantid() {
        return merchantid;
    }

    /**
     * Sets the value of the merchantid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setMerchantid(Merchant value) {
        this.merchantid = value;
    }

    /**
     * Gets the value of the paysuredate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaysuredate() {
        return paysuredate;
    }

    /**
     * Sets the value of the paysuredate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaysuredate(XMLGregorianCalendar value) {
        this.paysuredate = value;
    }

    /**
     * Gets the value of the purchasedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPurchasedate() {
        return purchasedate;
    }

    /**
     * Sets the value of the purchasedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPurchasedate(XMLGregorianCalendar value) {
        this.purchasedate = value;
    }

    /**
     * Gets the value of the recievinginstitution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecievinginstitution() {
        return recievinginstitution;
    }

    /**
     * Sets the value of the recievinginstitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecievinginstitution(String value) {
        this.recievinginstitution = value;
    }

    /**
     * Gets the value of the referenceno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceno() {
        return referenceno;
    }

    /**
     * Sets the value of the referenceno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceno(String value) {
        this.referenceno = value;
    }

    /**
     * Gets the value of the secret property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the value of the secret property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecret(String value) {
        this.secret = value;
    }

    /**
     * Gets the value of the shippingcost property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShippingcost() {
        return shippingcost;
    }

    /**
     * Sets the value of the shippingcost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShippingcost(Integer value) {
        this.shippingcost = value;
    }

    /**
     * Gets the value of the statusid property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatusid() {
        return statusid;
    }

    /**
     * Sets the value of the statusid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatusid(Status value) {
        this.statusid = value;
    }

    /**
     * Gets the value of the systemtraceno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemtraceno() {
        return systemtraceno;
    }

    /**
     * Sets the value of the systemtraceno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemtraceno(String value) {
        this.systemtraceno = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the transactionsourceid property.
     * 
     * @return
     *     possible object is
     *     {@link Transactionsource }
     *     
     */
    public Transactionsource getTransactionsourceid() {
        return transactionsourceid;
    }

    /**
     * Sets the value of the transactionsourceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transactionsource }
     *     
     */
    public void setTransactionsourceid(Transactionsource value) {
        this.transactionsourceid = value;
    }

    /**
     * Gets the value of the transactiontype property.
     * 
     * @return
     *     possible object is
     *     {@link Transactiontype }
     *     
     */
    public Transactiontype getTransactiontype() {
        return transactiontype;
    }

    /**
     * Sets the value of the transactiontype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transactiontype }
     *     
     */
    public void setTransactiontype(Transactiontype value) {
        this.transactiontype = value;
    }

    /**
     * Gets the value of the transmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets the value of the transmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmission(String value) {
        this.transmission = value;
    }

    /**
     * Gets the value of the visits property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVisits() {
        return visits;
    }

    /**
     * Sets the value of the visits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVisits(Integer value) {
        this.visits = value;
    }

}
