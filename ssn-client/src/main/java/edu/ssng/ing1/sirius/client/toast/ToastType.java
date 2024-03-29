package edu.ssng.ing1.sirius.client.toast;

public enum ToastType {
    ERROR("Erreur", "red", "fas-times-circle", "error-toast"),
    SUCCESS("Succès", "green", "fas-check-circle" ,"success-toast"),
    WARNING("Avertissement", "orange", "fas-exclamation-triangle","warning-toast"),
    INFO("Information", "blue", "fas-info-circle","info-toast");

    private final String title;
    private final String color;
    private final String icon;
    private final String style ;

    ToastType(String title, String color, String icon, String style) {
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
    public String getIcon() {
        return icon;
    }
    public String getStyle(){
        return style ;
    }
}
