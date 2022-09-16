package com.bruceewu.configor.helper;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private final Handler handler;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final List<IUpdater> observers = new ArrayList<>();

    private ThreadPool() {
        handler = new Handler(Looper.getMainLooper());
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> {
            for (IUpdater item : HOLDER.INSTANCE.observers) {
                main(item::update);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static class HOLDER {
        private static final ThreadPool INSTANCE = new ThreadPool();
    }

    private static ThreadPool getInstance() {
        return HOLDER.INSTANCE;
    }

    public static void work(Runnable action) {
        getInstance().executor.submit(action);
    }

    public static void main(Runnable action) {
        main(action, 0);
    }

    public static void main(Runnable action, int delayMillis) {
        if (delayMillis <= 0) {
            getInstance().handler.post(action);
        } else {
            getInstance().handler.postDelayed(action, delayMillis);
        }
    }

    public synchronized static void registerObserver(IUpdater updater) {
        if (!HOLDER.INSTANCE.observers.contains(updater)) {
            HOLDER.INSTANCE.observers.add(updater);
        }
    }

    public synchronized static void unRegisterObserver(IUpdater updater) {
        HOLDER.INSTANCE.observers.remove(updater);
    }

    public interface IUpdater {
        void update();
    }

    public static class TimeRepeator implements IUpdater {
        private final int DURATION;
        private final IUpdater updater;
        private int cur;

        public TimeRepeator(int duration, IUpdater updater) {
            this.DURATION = duration;
            this.updater = updater;
        }

        @Override
        public void update() {
            if (++cur % DURATION == 0) {
                cur = 0;
                updater.update();
            }
        }
    }
}
