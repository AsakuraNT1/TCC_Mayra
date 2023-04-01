package com.example.tccfinal10;

import android.os.SystemClock;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.Queue;

public class PowerThread extends Thread {

    RequestQueue queue;
    StringRequest PowerRequest;
    private boolean mustRun = true;

    PowerThread(RequestQueue queue, StringRequest PowerRequest) {

        this.queue = queue;
        this.PowerRequest = PowerRequest;

    }

    public void run() {

        while (mustRun) {
            queue.add(PowerRequest);
            SystemClock.sleep(2000);
        }

    }

    public void setMustRun(boolean mustRun) {
        this.mustRun = mustRun;
    }
}
