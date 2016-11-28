/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import com.iveri.enterprise.Enterprise;
import com.iveri.enterprise.ResultException;
import com.iveri.enterprise.ResultExceptionAction;
import com.iveri.enterprise.ResultStatus;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.iveri.bouncycastle.util.encoders.Base64;
import paygate.objects.Token;
import paygate.objects.Transaction;
import paygate.utils.UtilToken;

/**
 *
 * @author Joseph
 */
@Stateless
public class TokenManagementBean implements TokenManagementBeanLocal {

    @PersistenceContext(unitName = "merchantPU")
    private EntityManager em;
    @EJB
    private UtilToken utilToken;

    @Override
    public List<Token> getAllTokens() {
        return em.createNamedQuery("Token.findByInstruction", Token.class).setParameter("instruction", "reserve").getResultList();
    }

    @Override
    public void deleteToken(int tokenid) {
        Token token = em.find(Token.class, tokenid);
        if (token != null) {
            em.remove(token);
        }
    }

    private static String Decrypt(String encryptedText, String key) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] encryptedTextBytes = new Base64().decode(encryptedText);
        byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);

        return new String(decryptedTextBytes);
    }

    @Override
    public void tokenReverse(int tokenid) {
        Token token = em.find(Token.class, tokenid);
        if (token != null) {
            try {
                String mode = "Live";
                String key = "770A8A65DA156D24EE2A093277530142";
                Enterprise e = new Enterprise("host", "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50", ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
                e.prepare("Transaction", "AuthorisationReversal", "c41bdbb6-0277-4342-a231-0d6e71ff10ab", mode);
                e.setAttribute("MerchantReference", String.valueOf(System.currentTimeMillis()));
                e.setTag("Amount", token.getAmount().toString()); // R1.23
                e.setTag("Currency", token.getCurrency());
                e.setTag("ElectronicCommerceIndicator", "SecureChannel");
                try {
                    e.setTag("CCNumber", Decrypt(token.getTokeno(), key));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                e.setTag("ExpiryDate", token.getExpirydate());
                //e.setTag("Email", "josetosh06@gmail.com");
                //e.setTag("CardHolderAuthenticationID","xid");
                e.setTag("TransactionIndex", token.getTransactionindex());
                ResultStatus resultStatus = e.execute();
                System.out.println("Result : " + resultStatus.getValue());
                if (resultStatus == ResultStatus.UNSUCCESSFUL) {
                    // Display the result (eg. DENIED or ApplicationID not configured)
                    System.out.println("Result code : " + e.getResultCode());
                    System.out.println("Result source : " + e.getResultSource());
                    System.out.println("Result description : " + e.getResultDescription());
                } else if (resultStatus == ResultStatus.SUCCESSFUL || resultStatus == ResultStatus.SUCCESSFUL_WITH_WARNING) {
                    System.out.println("Successful Transaction Test");
                    System.out.println("RequestID : " + e.getAttribute("RequestID"));
                    System.out.println("Acquirer Reference : " + e.getTag("AcquirerReference"));
                    System.out.println("Acquirer Date : " + e.getTag("AcquirerDate"));
                    System.out.println("Acquirer Time : " + e.getTag("AcquirerTime"));
                    System.out.println("Authorisation Code : " + e.getTag("AuthorisationCode"));
                    System.out.println("Amount : " + e.getTag("Amount"));
                    System.out.println("Terminal : " + e.getTag("Terminal"));
                    System.out.println("Transaction Index : " + e.getTag("TransactionIndex"));
                    System.out.println("MyEmail : " + e.getTag("Email"));
                    System.out.println(System.getProperty("java.home"));
                }
                try {
                    //e.setTag("AuthroisationCode", "777JLW");
                    //e.setTag("RequestID", "450A3A00-1FFE-4B37-8BFD-73548D6DF1B4");
                    //e.setTag("CCNumber", "5334323080002613");
                    //e.setTag("ExpiryDate", "092014");
                    //e.setTag("CCNumber", "4054570200033602");
                    //e.setTag("ExpiryDate", "022016");
                    System.out.println(e.getLoggableResponse());
                } catch (TransformerConfigurationException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                em.remove(token);
                em.flush();
            } catch (ResultException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void tokenDebit(int tokenid) {
        Token token = em.find(Token.class, tokenid);
        String key = "770A8A65DA156D24EE2A093277530142";
        if (token != null) {
            try {
                System.out.println("Card Is : " + Decrypt(token.getTokeno(), "770A8A65DA156D24EE2A093277530142"));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("Token Searched Is : " + token.getTransactionindex());

                String mode = "Live";
                Enterprise e = new Enterprise("host", "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50", ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
                e.prepare("Transaction", "Debit", "c41bdbb6-0277-4342-a231-0d6e71ff10ab", mode);
                e.setAttribute("MerchantReference", String.valueOf(System.currentTimeMillis()));
                e.setTag("Amount", token.getAmount().toString()); // R1.23
                e.setTag("Currency", token.getCurrency());
                e.setTag("ElectronicCommerceIndicator", "SecureChannel");
                try {
                    e.setTag("CCNumber", Decrypt(token.getTokeno(), key));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                e.setTag("ExpiryDate", token.getExpirydate());
                //e.setTag("Email", "josetosh06@gmail.com");
                //e.setTag("CardHolderAuthenticationID","xid");
                e.setTag("TransactionIndex", token.getTransactionindex());
                //e.setTag("AuthroisationCode", "777JLW");
                //e.setTag("RequestID", "450A3A00-1FFE-4B37-8BFD-73548D6DF1B4");
                //e.setTag("CCNumber", "5334323080002613");
                //e.setTag("ExpiryDate", "092014");
                //e.setTag("CCNumber", "4054570200033602");
                //e.setTag("ExpiryDate", "022016");
                System.out.println(e.getLoggableRequest());

                ResultStatus resultStatus = e.execute();
                System.out.println("Result : " + resultStatus.getValue());
                if (resultStatus == ResultStatus.UNSUCCESSFUL) {
                    // Display the result (eg. DENIED or ApplicationID not configured)
                    System.out.println("Result code : " + e.getResultCode());
                    System.out.println("Result source : " + e.getResultSource());
                    System.out.println("Result description : " + e.getResultDescription());
                } else if (resultStatus == ResultStatus.SUCCESSFUL || resultStatus == ResultStatus.SUCCESSFUL_WITH_WARNING) {
                    System.out.println("Successful Transaction Test");
                    System.out.println("RequestID : " + e.getAttribute("RequestID"));
                    System.out.println("Acquirer Reference : " + e.getTag("AcquirerReference"));
                    System.out.println("Acquirer Date : " + e.getTag("AcquirerDate"));
                    System.out.println("Acquirer Time : " + e.getTag("AcquirerTime"));
                    System.out.println("Authorisation Code : " + e.getTag("AuthorisationCode"));
                    System.out.println("Amount : " + e.getTag("Amount"));
                    System.out.println("Terminal : " + e.getTag("Terminal"));
                    System.out.println("Transaction Index : " + e.getTag("TransactionIndex"));
                    System.out.println("MyEmail : " + e.getTag("Email"));
                    System.out.println(System.getProperty("java.home"));
                }

                Transaction transaction = new Transaction();
                transaction.setAmount(new BigInteger(token.getAmount().toString()));
                transaction.setStatus(utilToken.findStatus());
                transaction.setResponsecodeid(utilToken.findResponsecode("0"));
                transaction.setCreated(new Date());
                transaction.setDescription(token.getDescription());
                transaction.setModified(new Date());
                transaction.setMerchant(token.getMerchantid());
                transaction.setMerchantname(token.getMerchantid().getName());
                transaction.setCardtype("visa");
                transaction.setClientname(token.getClientname());
                transaction.setEmail(token.getEmail());
                transaction.setCurrency(token.getCurrency());

                utilToken.createTransaction(transaction);

                /*
                 * try { System.out.println("Token Date: " + token.getId());
                 *
                 * String mode = "Test"; String key =
                 * "770A8A65DA156D24EE2A093277530142"; Enterprise e = new
                 * Enterprise("host", "4327DA6D-FB2B-4C5E-A209-70BA4EB8FB50",
                 * ResultExceptionAction.RESULT_EXCEPTION_ON_UNSUCCESSFUL);
                 * e.prepare("Transaction", "Debit",
                 * "3a687c08-c840-43d6-ae4f-71a3fd619b6e", mode);
                 * e.setAttribute("MerchantReference",
                 * String.valueOf(System.currentTimeMillis()));
                 * e.setTag("Amount", token.getAmount().toString()); // R1.23
                 * e.setTag("Currency", token.getCurrency());
                 * e.setTag("ElectronicCommerceIndicator", "SecureChannel"); try
                 * { e.setTag("CCNumber", Decrypt(token.getTokeno(), key)); }
                 * catch (NoSuchAlgorithmException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } catch (NoSuchPaddingException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } catch (InvalidKeyException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } catch (IllegalBlockSizeException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } catch (BadPaddingException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } catch (UnsupportedEncodingException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); } e.setTag("ExpiryDate", token.getExpirydate());
                 * e.setTag("TransactionIndex", token.getTransactionindex());
                 *
                 * System.out.println("Enterprise Class Is :
                 * "+e.getLoggableRequest());
                 *
                 * System.out.println(e.getLoggableRequest()); ResultStatus
                 * resultStatus = e.execute(); System.out.println("Result : " +
                 * resultStatus.getValue()); if (resultStatus ==
                 * ResultStatus.UNSUCCESSFUL) { // Display the result (eg.
                 * DENIED or ApplicationID not configured)
                 * System.out.println("Result code : " + e.getResultCode());
                 * System.out.println("Result source : " + e.getResultSource());
                 * System.out.println("Result description : " +
                 * e.getResultDescription()); } else if (resultStatus ==
                 * ResultStatus.SUCCESSFUL || resultStatus ==
                 * ResultStatus.SUCCESSFUL_WITH_WARNING) {
                 * System.out.println("Successful Transaction Test");
                 * System.out.println("RequestID : " +
                 * e.getAttribute("RequestID")); System.out.println("Acquirer
                 * Reference : " + e.getTag("AcquirerReference"));
                 * System.out.println("Acquirer Date : " +
                 * e.getTag("AcquirerDate")); System.out.println("Acquirer Time
                 * : " + e.getTag("AcquirerTime"));
                 * System.out.println("Authorisation Code : " +
                 * e.getTag("AuthorisationCode")); System.out.println("Amount :
                 * " + e.getTag("Amount")); System.out.println("Terminal : " +
                 * e.getTag("Terminal")); System.out.println("Transaction Index
                 * : " + e.getTag("TransactionIndex"));
                 * System.out.println("Merchant Reference: " +
                 * e.getAttribute("MerchantReference"));
                 * System.out.println("Transaction Has Gone Through");
                 *
                 * Transaction transaction = new Transaction();
                 *
                 *
                 *
                 *
                 * }
                 *
                 *
                 * System.out.println("Token T-Index : " +
                 * token.getTransactionindex()); System.out.println("Token
                 * ToneNo : " + token.getTokeno()); System.out.println("Token
                 * Amount : " + token.getAmount()); System.out.println("Token
                 * NameonCard : " + token.getNameoncard());
                 * System.out.println("Token ClientName : " +
                 * token.getClientname()); System.out.println("Token Email : " +
                 * token.getEmail()); System.out.println("Token Currency : " +
                 * token.getCurrency());
                 *
                 * } catch (ResultException ex) {
                 * Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE,
                 * null, ex); }
                 */
            } catch (ResultException ex) {
                Logger.getLogger(TokenManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        em.remove(token);
        em.flush();
    }
}
