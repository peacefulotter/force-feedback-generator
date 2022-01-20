package com.peacefulotter.ffserver.control;

import at.wisch.joystick.FFJoystick;
import com.peacefulotter.ffserver.websocket.FFPoll;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FFRecorder
{
    private static ScheduledExecutorService executor;
    private static Queue<Float> angles;

    private static final Runnable recordWheelAngle = () -> {
        FFPoll angle = FFControl.getPoll();
        System.out.println("polling " + angle.getAxisAngle());
        angles.add( angle.getAxisAngle() );
    };

    public static void startRecording()
    {
        angles = new ArrayDeque<>();
        // if ( !executor.isTerminated() )
        //    return;
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(recordWheelAngle, 0, 1, TimeUnit.SECONDS);
    }

    public static Queue<Float> stopRecording()
    {
        if ( executor == null )
            return null;

        executor.shutdown();
        return angles;
    }
}
