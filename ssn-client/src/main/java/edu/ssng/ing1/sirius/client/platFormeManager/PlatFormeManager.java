package edu.ssng.ing1.sirius.client.platFormeManager;

import javafx.application.Platform;

public class PlatFormeManager {
    public static Platform platform;

    public static void initPlatform(Platform platformInstance) {
        platform = platformInstance;
    }

    public static Platform getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("La plateforme JavaFX n'a pas été initialisée.");
        }
        return platform;
    }

    public static void runOnFXThread(Runnable runnable) {
        getPlatform().runLater(runnable);
    }
}
