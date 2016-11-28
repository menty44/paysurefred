/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id"),
    @NamedQuery(name = "Transaction.findByMerchantname", query = "SELECT t FROM Transaction t WHERE t.merchantname = :merchantname"),
    @NamedQuery(name = "Transaction.findByDebitcredit", query = "SELECT t FROM Transaction t WHERE t.debitcredit = :debitcredit"),
    @NamedQuery(name = "Transaction.findByAmount", query = "SELECT t FROM Transaction t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transaction.findByCommission", query = "SELECT t FROM Transaction t WHERE t.commission = :commission"),
    @NamedQuery(name = "Transaction.findByRefno", query = "SELECT t FROM Transaction t WHERE t.refno = :refno"),
    @NamedQuery(name = "Transaction.findByDelivered", query = "SELECT t FROM Transaction t WHERE t.delivered = :delivered"),
    @NamedQuery(name = "Transaction.findByDescription", query = "SELECT t FROM Transaction t WHERE t.description = :description"),
    @NamedQuery(name = "Transaction.findByCreated", query = "SELECT t FROM Transaction t WHERE t.created = :created"),
    @NamedQuery(name = "Transaction.findByModified", query = "SELECT t FROM Transaction t WHERE t.modified = :modified"),
    @NamedQuery(name = "Transaction.findByProcessed", query = "SELECT t FROM Transaction t WHERE t.processed = :processed"),
    @NamedQuery(name = "Transaction.findByCardtype", query = "SELECT t FROM Transaction t WHERE t.cardtype = :cardtype"),
    @NamedQuery(name = "Transaction.findByThreshold", query = "SELECT t FROM Transaction t WHERE t.threshold = :threshold"),
    @NamedQuery(name = "Transaction.findByClientname", query = "SELECT t FROM Transaction t WHERE t.clientname = :clientname"),
    @NamedQuery(name = "Transaction.findByEmail", query = "SELECT t FROM Transaction t WHERE t.email = :email"),
    @NamedQuery(name = "Transaction.findByCurrency", query = "SELECT t FROM Transaction t WHERE t.currency = :currency")})
public class Transaction implements Serializable {
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "merchantname")
    private String merchantname;
    @Size(max = 2)
    @Column(name = "debitcredit")
    private String debitcredit;
    @Column(name = "amount")
    private BigInteger amount;
    @Column(name = "commission")
    private BigInteger commission;
    @Size(max = 20)
    @Column(name = "refno")
    private String refno;
    @Size(max = 15)
    @Column(name = "delivered")
    private String delivered;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "processed")
    private Boolean processed;
    @Size(max = 30)
    @Column(name = "cardtype")
    private String cardtype;
    @Column(name = "threshold")
    private Boolean threshold;
    @Size(max = 255)
    @Column(name = "clientname")
    private String clientname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "currency")
    private String currency;
    @JoinColumn(name = "transactionsourceid", referencedColumnName = "id")
    @ManyToOne
    private Transactionsource transactionsourceid;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status statusid;
    @JoinColumn(name = "responsecodeid", referencedColumnName = "id")
    @ManyToOne
    private Responsecode responsecodeid;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;

    public Transaction() {
    }

    public Transaction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getDebitcredit() {
        return debitcredit;
    }

    public void setDebitcredit(String debitcredit) {
        this.debitcredit = debitcredit;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getCommission() {
        return commission;
    }

    public void setCommission(BigInteger commission) {
        this.commission = commission;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public Boolean getThreshold() {
        return threshold;
    }

    public void setThreshold(Boolean threshold) {
        this.threshold = threshold;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Responsecode getResponsecodeid() {
        return responsecodeid;
    }

    public void setResponsecodeid(Responsecode responsecodeid) {
        this.responsecodeid = responsecodeid;
    }

    public Merchant getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Merchant merchantid) {
        this.merchantid = merchantid;
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
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Transaction[ id=" + id + " ]";
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    
}
