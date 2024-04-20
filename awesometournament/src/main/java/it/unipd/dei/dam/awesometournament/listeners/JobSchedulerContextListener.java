package it.unipd.dei.dam.awesometournament.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.jobs.MatchCreatorJob;

public class JobSchedulerContextListener implements ServletContextListener {

    protected final static Logger LOGGER = LogManager.getLogger(ServletContextListener.class,
            StringFormatterMessageFactory.INSTANCE);

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize scheduler
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Calculate initial delay until next midnight
        long initialDelay = calculateInitialDelayUntilMidnight();

        // Schedule the job to run daily at midnight
        scheduler.scheduleAtFixedRate(() -> {
            // Execute your job here
            try {
                MatchCreatorJob.execute(-1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.info(e); 
            }
        }, initialDelay, 24, TimeUnit.HOURS);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Shutdown scheduler
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private long calculateInitialDelayUntilMidnight() {
        // Calculate the initial delay until the next midnight
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.with(LocalTime.MAX).plusDays(1); // Next midnight
        Duration durationUntilNextMidnight = Duration.between(now, nextMidnight);
        return durationUntilNextMidnight.toSeconds();
    }
}
