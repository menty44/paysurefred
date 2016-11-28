/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "transaction")
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t where t.merchant = :merchant and t.modified > :startDate and t.modified < :endDate order by t.id desc"),
    
    @NamedQuery(name = "Transaction.findByMerchantname", query = "SELECT t FROM Transaction t WHERE t.merchantname = :merchantname"),

    @NamedQuery(name = "Transaction.findByDebitcredit", query = "SELECT t FROM Transaction t WHERE t.debitcredit = :debitcredit"),
    @NamedQuery(name = "Transaction.findByAmount", query = "SELECT t FROM Transaction t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transaction.findByCommission", query = "SELECT t FROM Transaction t WHERE t.commission = :commission"),

    @NamedQuery(name = "Transaction.findByRefno", query = "SELECT t FROM Transaction t WHERE t.refno = :refno"),
    @NamedQuery(name = "Transaction.findByDescription", query = "SELECT t FROM Transaction t WHERE t.description = :description"),
    @NamedQuery(name = "Transaction.findByCreated", query = "SELECT t FROM Transaction t WHERE t.created = :created"),
    @NamedQuery(name = "Transaction.findByModified", query = "SELECT t FROM Transaction t WHERE t.modified = :modified"),   
    @NamedQuery(name = "Transaction.findByCardtype", query = "SELECT t FROM Transaction t WHERE t.cardtype = :cardtype")})
public class Transaction implements Serializable {
    @Size(max = 15)
    @Column(name = "delivered")
    private String delivered;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @Column(name = "processed")
    private Boolean processed;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "merchantname")
    private String merchantname;
    @Column(name = "debitcredit")
    private String debitcredit;
    @Column(name = "amount")
    private BigInteger amount;
    @Column(name = "commission")
    private BigInteger commission;
    @Column(name = "refno")
    private String refno;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status status;
    @JoinColumn(name = "responsecodeid", referencedColumnName = "id")
    @ManyToOne
    private Responsecode responsecodeid;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchant;    
    @Column(name = "cardtype")
    private String cardtype;
    private String clientname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email")
    private String email;
    @Column(name = "currency")
    private String currency;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Responsecode getResponsecodeid() {
        return responsecodeid;
    }

    public void setResponsecodeid(Responsecode responsecodeid) {
        this.responsecodeid = responsecodeid;
    } 

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
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
        return "paygate.objects.Transaction[id=" + id + "]";
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
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

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
