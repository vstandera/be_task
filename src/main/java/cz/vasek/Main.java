package cz.vasek;

import cz.vasek.utils.IoUtils;
import cz.vasek.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The main class of the Application.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        SchedulerJob schedulerJob = new SchedulerJob();
        try {
            // This is run schedule Job for every 60 seconds.
            schedulerJob.schedule();
            IoUtils ioUtils = new IoUtils();
            if (args.length == 1) {
                // this load exhangeRates file
                ioUtils.readFile(args[0], false);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String readLine = "";
            while (!readLine.equalsIgnoreCase("quit")) {
                System.out.print("Enter Amount and Currency format( \"CZK 100\" ) \n:");
                readLine = br.readLine();
                if (readLine.equalsIgnoreCase("quit")) {
                   // This cancel scheduled job
                    schedulerJob.cancelTask();
                    continue;
                }
                // this parse FILE name from console example of command "FILE C:\Repository\be_task\src\main\resources\InputFile\input.txt"
                final String fileName = ioUtils.parseFileName(readLine);
                if (fileName != null) {
                    // this loaded the Payments
                    ioUtils.readFile(fileName, true);
                } else {
                    // this save the Payment.
                    ioUtils.handlePayments(readLine);
                    System.out.println("This is your string: " + readLine);
                }
            }
            // this calculate and write sum of payments and exchange to USD.
            Utils.writeAllPayments(false);
            System.out.println("This is Vasek app.");
        } catch (Exception e) {
            throw e;
        } finally {
            // This cancel scheduled job
            schedulerJob.cancelTask();
        }
    }


}
