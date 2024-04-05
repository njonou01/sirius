package edu.ssng.ing1.sirius.client;

import java.util.concurrent.Callable;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PreloadService extends Service<Void> {

    private final Callable<Void> dataFunction;

    public PreloadService(Callable<Void> dataFunction) {
        this.dataFunction = dataFunction;
    }


    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                dataFunction.call();
                return null;
            }
        };
    }
}