/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.controllers;

import java.util.List;
import javax.ejb.Local;
import paygate.objects.Token;

/**
 *
 * @author Joseph
 */
@Local
public interface TokenManagementBeanLocal {    
    List<Token> getAllTokens();
    void deleteToken(int tokenid);
    void tokenReverse(int tokenid);
    void tokenDebit(int tokenid);
}
