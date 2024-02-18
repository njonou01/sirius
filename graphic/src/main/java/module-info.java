module edu.ssng.ing1.sirius.client {
    
    requires javafx.controls ;
    requires javafx.fxml;
    opens edu.ssng.ing1.sirius.client to javafx.fxml ;
    exports edu.ssng.ing1.sirius.client ;
}
