/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paysure
 */
@Entity
@Table(name = "purchase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p"),
    @NamedQuery(name = "Purchase.findLatest", query = "SELECT p FROM Purchase p where p.merchantid = :merchantid  and p.transactionsourceid = :transactionsourceid order by p.id desc"),
    @NamedQuery(name = "Purchase.findById", query = "SELECT p FROM Purchase p WHERE p.id = :id"),
    @NamedQuery(name = "Purchase.findByToken", query = "SELECT p FROM Purchase p WHERE p.token = :token"),
    @NamedQuery(name = "Purchase.findByAge", query = "SELECT p FROM Purchase p WHERE p.age = :age"),
    @NamedQuery(name = "Purchase.findByVisits", query = "SELECT p FROM Purchase p WHERE p.visits = :visits"),
    @NamedQuery(name = "Purchase.findByAmount", query = "SELECT p FROM Purchase p WHERE p.amount = :amount"),
    @NamedQuery(name = "Purchase.findByShippingcost", query = "SELECT p FROM Purchase p WHERE p.shippingcost = :shippingcost"),
    @NamedQuery(name = "Purchase.findByRecievinginstitution", query = "SELECT p FROM Purchase p WHERE p.recievinginstitution = :recievinginstitution"),
    @NamedQuery(name = "Purchase.findBySystemtraceno", query = "SELECT p FROM Purchase p WHERE p.systemtraceno = :systemtraceno"),
    @NamedQuery(name = "Purchase.findByReferenceno", query = "SELECT p FROM Purchase p WHERE p.referenceno = :referenceno"),
    @NamedQuery(name = "Purchase.findByTransmission", query = "SELECT p FROM Purchase p WHERE p.transmission = :transmission"),
    @NamedQuery(name = "Purchase.findByPurchasedate", query = "SELECT p FROM Purchase p WHERE p.purchasedate = :purchasedate"),
    @NamedQuery(name = "Purchase.findByPaysuredate", query = "SELECT p FROM Purchase p WHERE p.paysuredate = :paysuredate"),
    @NamedQuery(name = "Purchase.findBySecret", query = "SELECT p FROM Purchase p WHERE p.secret = :secret"),
    @NamedQuery(name = "Purchase.findByClientname", query = "SELECT p FROM Purchase p WHERE p.clientname = :clientname"),
    @NamedQuery(name = "Purchase.findByEmail", query = "SELECT p FROM Purchase p WHERE p.email = :email"),
    @NamedQuery(name = "Purchase.findpurchase", query = "SELECT p FROM Purchase p WHERE p.referenceno = :referenceno and p.amount = :amount order by p.id desc"),
    @NamedQuery(name = "Purchase.findByDescription", query = "SELECT p FROM Purchase p WHERE p.description = :description")})
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "token")
    private String token;
    @Column(name = "age")
    private Integer age;
    @Column(name = "visits")
    private Integer visits;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "shippingcost")
    private Integer shippingcost;
    @Size(max = 100)
    @Column(name = "recievinginstitution")
    private String recievinginstitution;
    @Size(max = 20)
    @Column(name = "systemtraceno")
    private String systemtraceno;
    @Size(max = 20)
    @Column(name = "referenceno")
    private String referenceno;
    @Size(max = 20)
    @Column(name = "transmission")
    private String transmission;
    @Column(name = "purchasedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedate;
    @Column(name = "paysuredate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paysuredate;
    @Size(max = 10)
    @Column(name = "secret")
    private String secret;
    @Size(max = 255)
    @Column(name = "clientname")
    private String clientname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "purchaseid")
    private List<Data> dataList;
    @JoinColumn(name = "transactiontype", referencedColumnName = "id")
    @ManyToOne
    private Transactiontype transactiontype;
    @JoinColumn(name = "transactionsourceid", referencedColumnName = "id")
    @ManyToOne
    private Transactionsource transactionsourceid;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status statusid;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;
    @JoinColumn(name = "genderid", referencedColumnName = "id")
    @ManyToOne
    private Gender genderid;
    @Column(name = "currency")
    private String currency;

    public Purchase() {
    }

    public Purchase(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getShippingcost() {
        return shippingcost;
    }

    public void setShippingcost(Integer shippingcost) {
        this.shippingcost = shippingcost;
    }

    public String getRecievinginstitution() {
        return recievinginstitution;
    }

    public void setRecievinginstitution(String recievinginstitution) {
        this.recievinginstitution = recievinginstitution;
    }

    public String getSystemtraceno() {
        return systemtraceno;
    }

    public void setSystemtraceno(String systemtraceno) {
        this.systemtraceno = systemtraceno;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public Date getPaysuredate() {
        return paysuredate;
    }

    public void setPaysuredate(Date paysuredate) {
        this.paysuredate = paysuredate;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public Transactiontype getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(Transactiontype transactiontype) {
        this.transactiontype = transactiontype;
    }

    public Transactionsource getTransactionsourceid() {
        return transactionsourceid;
    }

    public void setTransactionsourceid(Transactionsource transactionsourceid) {
        this.transactionsourceid = transactionsourceid;
    }

    public Status getStatusid() {
        return statusid;
    }

    public void setStatusid(Status statusid) {
        this.statusid = statusid;
    }

    public Merchant getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Merchant merchantid) {
        this.merchantid = merchantid;
    }

    public Gender getGenderid() {
        return genderid;
    }

    public void setGenderid(Gender genderid) {
        this.genderid = genderid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }       

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "service.Purchase[ id=" + id + " ]";
    }
    
}
