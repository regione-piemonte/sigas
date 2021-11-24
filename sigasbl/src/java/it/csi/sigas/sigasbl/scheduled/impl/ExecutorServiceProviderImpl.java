package it.csi.sigas.sigasbl.scheduled.impl;

import it.csi.sigas.sigasbl.scheduled.IExecutorServiceProvider;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ExecutorServiceProviderImpl implements IExecutorServiceProvider {

    protected static Logger logger = Logger.getLogger(ExecutorServiceProviderImpl.class);
    private final int THREAD_POOL_SIZE = 3;

    private ExecutorService executorService;

    public ExecutorServiceProviderImpl() {
        logger.info("Initialize executor service pool #: " + THREAD_POOL_SIZE);
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public boolean shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            return executorService.isShutdown();
        }
        return Boolean.FALSE;
    }

    public void reinitialize() {
        shutdown();
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
}
