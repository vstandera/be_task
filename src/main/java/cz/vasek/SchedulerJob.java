package cz.vasek;

import cz.vasek.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This is a schedule job.
 */
public class SchedulerJob {
    private Timer timer;
    private TimerTask timerTask;

    /**
     * Schedule job to write sum to console
     */
    public void schedule() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Utils.writeAllPayments(true);
            }
        };
        timer.schedule(timerTask, 60000, 60000);
    }

    /**
     * Cancel the scheduleTask
     */
    public void cancelTask() {
        timerTask.cancel();
        timer.cancel();

    }
}
