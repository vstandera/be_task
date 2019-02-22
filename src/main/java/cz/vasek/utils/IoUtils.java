package cz.vasek.utils;

import cz.vasek.domain.Payments;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Utils for handle files.
 */
public class IoUtils {
    final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * This read a file and save Payments or ExchangeRates
     * @param fileName name of the file
     * @param isPaymentFile if it is true - parse and save Payments
     *                      if it is false - parse and save ExchangeRates
     * @throws IOException
     */
    public void readFile(String fileName, boolean isPaymentFile) throws IOException {
        Path path = Paths.get(fileName).toAbsolutePath();
        try (Scanner scanner =  new Scanner(path, ENCODING.name())){
            while (scanner.hasNextLine()){
               if (isPaymentFile){
                    handlePayments(scanner.nextLine());
               }else{
                   Utils.parseExchangeRate(scanner.nextLine());
               }
            }
        }
    }

    /**
     * Save payments.
     * @param readLine string of the payment for example "USD 100"
     */
    public void handlePayments(String readLine) {
        Payments.getInstance().added(Utils.parseToPayment(readLine));
    }

    /**
     * Parse fileName for Payments
     * @param fileName File name String.
     * @return
     */
    public String parseFileName(String fileName){
        StringTokenizer strTkn = new StringTokenizer(fileName, " ");
        if (strTkn.countTokens() == 2) {
            if (strTkn.hasMoreTokens()) {
                if (strTkn.nextToken().equalsIgnoreCase("FILE")){
                    if (strTkn.hasMoreTokens()) {
                        return strTkn.nextToken();
                    }
                }
            }
        }
        return null;
    }

}
