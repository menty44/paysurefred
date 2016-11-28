/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author gachanja
 */
public class CloseCart {

    String response;

    public void callUrl(String merchantUrl) {

        BufferedReader in;
        try {
            URL url = new URL(merchantUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = in.readLine();
            in.close();
        } catch (IOException ex) {
        }
    }
}
