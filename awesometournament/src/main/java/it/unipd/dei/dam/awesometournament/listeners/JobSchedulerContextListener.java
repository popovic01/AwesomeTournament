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

/**
 * Listens for servlet context events to initialize and destroy the job scheduler.
 * 
 * NOTE: this is meant to be working in a real-life web application where the server
 * is running 24/7.
 */
public class JobSchedulerContextListener implements ServletContextListener {

    protected final static Logger LOGGER = LogManager.getLogger(ServletContextListener.class,
            StringFormatterMessageFactory.INSTANCE);

    private ScheduledExecutorService scheduler;

    /**
     * Initializes the job scheduler when the servlet context is initialized.
     *
     * @param sce The ServletContextEvent object representing the servlet context initialization event.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        long initialDelay = calculateInitialDelayUntilMidnight();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                MatchCreatorJob.execute(-1);
            } catch (Exception e) {
                LOGGER.info(e); 
            }
        }, initialDelay, 24, TimeUnit.HOURS);
    }

    /**
     * Destroys the job scheduler when the servlet context is destroyed.
     *
     * @param sce The ServletContextEvent object representing the servlet context destruction event.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    /**
     * Calculates the initial delay until midnight from the current time.
     *
     * @return The initial delay in seconds until midnight from the current time.
     */
    private long calculateInitialDelayUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.with(LocalTime.MAX).plusDays(1);
        Duration durationUntilNextMidnight = Duration.between(now, nextMidnight);
        return durationUntilNextMidnight.toSeconds();
    }
}
