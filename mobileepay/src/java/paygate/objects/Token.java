/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t"),
    @NamedQuery(name = "Token.findById", query = "SELECT t FROM Token t WHERE t.id = :id"),
    @NamedQuery(name = "Token.findByToken", query = "SELECT t FROM Token t WHERE t.token = :token"),
    @NamedQuery(name = "Token.findByAge", query = "SELECT t FROM Token t WHERE t.age = :age"),
    @NamedQuery(name = "Token.findByVisits", query = "SELECT t FROM Token t WHERE t.visits = :visits"),
    @NamedQuery(name = "Token.findByAmount", query = "SELECT t FROM Token t WHERE t.amount = :amount"),
    @NamedQuery(name = "Token.findByShippingcost", query = "SELECT t FROM Token t WHERE t.shippingcost = :shippingcost"),
    @NamedQuery(name = "Token.findByRecievinginstitution", query = "SELECT t FROM Token t WHERE t.recievinginstitution = :recievinginstitution"),
    @NamedQuery(name = "Token.findBySystemtraceno", query = "SELECT t FROM Token t WHERE t.systemtraceno = :systemtraceno"),
    @NamedQuery(name = "Token.findByReferenceno", query = "SELECT t FROM Token t WHERE t.referenceno = :referenceno"),
    @NamedQuery(name = "Token.findByTransmission", query = "SELECT t FROM Token t WHERE t.transmission = :transmission"),
    @NamedQuery(name = "Token.findByTokendate", query = "SELECT t FROM Token t WHERE t.tokendate = :tokendate"),
    @NamedQuery(name = "Token.findByPaysuredate", query = "SELECT t FROM Token t WHERE t.paysuredate = :paysuredate"),
    @NamedQuery(name = "Token.findBySecret", query = "SELECT t FROM Token t WHERE t.secret = :secret"),
    @NamedQuery(name = "Token.findByClientname", query = "SELECT t FROM Token t WHERE t.clientname = :clientname"),
    @NamedQuery(name = "Token.findByEmail", query = "SELECT t FROM Token t WHERE t.email = :email"),
    @NamedQuery(name = "Token.findByDescription", query = "SELECT t FROM Token t WHERE t.description = :description"),
    @NamedQuery(name = "Token.findByCurrency", query = "SELECT t FROM Token t WHERE t.currency = :currency"),
    @NamedQuery(name = "Token.findByTokeno", query = "SELECT t FROM Token t WHERE t.tokeno = :tokeno"),
    @NamedQuery(name = "Token.findBySecuritycode", query = "SELECT t FROM Token t WHERE t.securitycode = :securitycode"),
    @NamedQuery(name = "Token.findByNameoncard", query = "SELECT t FROM Token t WHERE t.nameoncard = :nameoncard"),
    @NamedQuery(name = "Token.findByExpirydate", query = "SELECT t FROM Token t WHERE t.expirydate = :expirydate"),
    @NamedQuery(name = "Token.findByInstruction", query = "SELECT t FROM Token t WHERE t.instruction = :instruction"),
    @NamedQuery(name = "Token.findByTransactionindex", query = "SELECT t FROM Token t WHERE t.transactionindex = :transactionindex")})
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
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
    @Column(name = "tokendate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokendate;
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
    @Size(max = 2147483647)
    @Column(name = "currency")
    private String currency;
    @Size(max = 2147483647)
    @Column(name = "tokeno")
    private String tokeno;
    @Column(name = "securitycode")
    private Integer securitycode;
    @Size(max = 2147483647)
    @Column(name = "nameoncard")
    private String nameoncard;
    @Size(max = 6)
    @Column(name = "expirydate")
    private String expirydate;
    @Size(max = 2147483647)
    @Column(name = "instruction")
    private String instruction;
    @Size(max = 2147483647)
    @Column(name = "transactionindex")
    private String transactionindex;
    @JoinColumn(name = "transactiontype", referencedColumnName = "id")
    @ManyToOne
    private Transactiontype transactiontype;
    @JoinColumn(name = "transactionsourceid", referencedColumnName = "id")
    @ManyToOne
    private Transactionsource transactionsourceid;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status statusid;
    @JoinColumn(name = "responsecode", referencedColumnName = "id")
    @ManyToOne
    private Responsecode responsecode;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;
    @JoinColumn(name = "genderid", referencedColumnName = "id")
    @ManyToOne
    private Gender genderid;
    @JoinColumn(name = "countryofresidence", referencedColumnName = "id")
    @ManyToOne
    private Country countryofresidence;

    public Token() {
    }

    public Token(Integer id) {
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

    public Date getTokendate() {
        return tokendate;
    }

    public void setTokendate(Date tokendate) {
        this.tokendate = tokendate;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTokeno() {
        return tokeno;
    }

    public void setTokeno(String tokeno) {
        this.tokeno = tokeno;
    }

    public Integer getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(Integer securitycode) {
        this.securitycode = securitycode;
    }

    public String getNameoncard() {
        return nameoncard;
    }

    public void setNameoncard(String nameoncard) {
        this.nameoncard = nameoncard;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getTransactionindex() {
        return transactionindex;
    }

    public void setTransactionindex(String transactionindex) {
        this.transactionindex = transactionindex;
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

    public Responsecode getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(Responsecode responsecode) {
        this.responsecode = responsecode;
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

    public Country getCountryofresidence() {
        return countryofresidence;
    }

    public void setCountryofresidence(Country countryofresidence) {
        this.countryofresidence = countryofresidence;
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
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Token[ id=" + id + " ]";
    }
    
}
