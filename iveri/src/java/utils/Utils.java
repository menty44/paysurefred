/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import paygate.objects.Transaction;

/**
 *
 * @author gachanja
 */
public class Utils {
    public Transaction computeCommission(Transaction transaction) {
        BigInteger merchantRate = transaction.getMerchantid().getRateid().getMerchantrate();
        transaction.setCommission(transaction.getAmount().multiply(merchantRate));
        return transaction;
    }
}
