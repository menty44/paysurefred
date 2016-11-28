/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigDecimal;
import objects.Transaction;

/**
 *
 * @author gachanja
 */
public class Utils {
    public Transaction computeCommission(Transaction transaction) {
        BigDecimal merchantRate = transaction.getMerchantid().getRateid().getMerchantrate().divide(BigDecimal.valueOf(100));
        transaction.setCommission(transaction.getAmount().multiply(merchantRate));
        return transaction;
    }
}
