package it.csi.sigas.sigasbl.scheduled;

import java.util.concurrent.ExecutorService;

public interface IExecutorServiceProvider {

    ExecutorService getExecutorService();

    boolean shutdown();

    void reinitialize();
}
