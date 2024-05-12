package edu.ssng.ing1.sirius.business.server.notifyProcess;

public enum NotificationType {
    NEW_CONNECTION("Votre Amie  est connecté"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " est connecté ";
                 
        }
    },
    DISCONNECTION_STUDENT("Votre Amie  est connecté"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " est déconnecté ";
                 
        }
    },
    END_ACTIVITY("Votre Activité est terminée"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Activité " + messageArgument[0] +  " est terminée le "+messageArgument[1];
                 
        }
    };
    

    private String valeur;
 

    NotificationType(String message) {
        this.valeur = message;
        
       
    }

    public String getValeur() {
        return valeur;
    }

    public abstract String getMessage(String ...messageArgument);

    

    
}
