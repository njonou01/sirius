package edu.ssng.ing1.sirius.business.server.notifyProcess;

public enum NotificationType {
    NEW_CONNECTION("Votre Amie  est connecté"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " est connecté ";
                 
        }
    },
    NEW_ACTIVITY("Une nouvelle activité a été créée"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " a créé l'activité "+messageArgument[1];
                 
        }
    },
    INVITE_ACTIVITY("Vous etes invit2"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " Vous Invite participer à l'activité : "+messageArgument[1];
                 
        }
    },
    ACCEPTED_ACTIVITY("Activité accepté"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " Viens d'accepter votre invitation";
                 
        }
    },
    DENY_INVITATION("Activité accepté"){
        @Override
        public String getMessage(String ...messageArgument) {
            return "Votre Amie " + messageArgument[0] +  " a refusé votre invitation";
                 
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
