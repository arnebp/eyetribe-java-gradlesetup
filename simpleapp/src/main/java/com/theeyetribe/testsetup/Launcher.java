package com.theeyetribe.testsetup;

import com.theeyetribe.clientsdk.GazeManager;
import com.theeyetribe.clientsdk.IGazeListener;
import com.theeyetribe.clientsdk.data.GazeData;

/**
 * Simple eye tracking setup using the EyeTribe Dev Kit.
 * <p/>
 * Class connects to the EyeTribe tracker using the EyeTribe Java SDK, writes
 * gaze frames to console and and terminates itself af 10 sec
 */
public class Launcher
{
    public static void main(String[] args)
    {
        final GazeManager gm = GazeManager.getInstance();
        gm.activate();
        gm.addGazeListener(new GazeListener());

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                gm.deactivate();
            }
        });

        new Thread(() ->
        {
            long periodToRun = 10 * 1000; // 10 secs

            long timestamp = System.currentTimeMillis();

            // Run program for fixed time period
            while(System.currentTimeMillis() - timestamp < periodToRun)
            {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.exit(0);

        }).start();
    }

    private static class GazeListener implements IGazeListener
    {
        @Override
        public void onGazeUpdate(GazeData gazeData)
        {
            System.out.println(gazeData.toString());
        }
    }
}
