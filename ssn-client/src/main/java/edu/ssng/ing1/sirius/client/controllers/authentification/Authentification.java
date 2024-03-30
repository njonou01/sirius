package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;


import edu.ssng.ing1.sirius.client.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;


public class Authentification {
    @FXML
    private Button athBtn;

    @FXML
    private Button btn;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchor;

    @FXML
    private AnchorPane primaryanchor;

    @FXML
    private Text authLabelSign;

    @FXML
    private Text authCommentSign;

    @FXML
    private Label appTitle;

    private boolean isSignUp;

    private double initPosOfAnchor;

    Router router = Router.getInstance();
    private Pane signinPane;
    private Pane signupStartPane;
    private Pane signupSchoolPane;
    private Pane signupFinalPane;
    private Pane signupStudentInfoPane;
    private SignUpStartContoller signUpStartcontroller;
    private SignUpStudentInfoController signUpStudentController;
    private SignUpSchoolController signUpSchoolController;
    private SignUpFinalController signUpFinalController;
    private String appName = "S-Connect";

    @FXML
    public void changePosition(ActionEvent event) {
        double distBtwnStackandprimary = primaryanchor.getWidth() - stackPane.getWidth();
        reverseNodes(this.anchor, this.athBtn, this.authLabelSign, this.authCommentSign, this.appTitle);
        this.anchor.setTranslateX(isSignUp ? 0 : -initPosOfAnchor);
        this.stackPane.setTranslateX(!isSignUp ? distBtwnStackandprimary : 0);

        this.isSignUp = !this.isSignUp;

        if (isSignUp) {
            setASignupPaneVisible(this.signupStartPane);
            this.signinPane.setVisible(false);
            router.getStage().setTitle(appName + " " + "Sign Up");
        } else {
            setASignupPaneVisible(null);
            this.signinPane.setVisible(true);
            router.getStage().setTitle(appName + " " + "Sign In");
        }

        this.athBtn.setText(isSignUp ? "Sign In" : "Sign Up");
    }

    private void reverseNode(Node node) {
        Scale scale = new Scale(-1, 1);
        scale.setPivotX(node.getBoundsInLocal().getWidth() / 2);
        scale.setPivotY(node.getBoundsInLocal().getHeight() / 2);
        node.getTransforms().add(scale);
    }

    private void reverseNodes(Node... nodes) {
        for (Node node : nodes)
            reverseNode(node);
    }

    public void initialize() throws IOException {
        FXMLLoader signinLoader = router.getParentNode("signin");
        FXMLLoader signupStartLoader = router.getParentNode("signup-start");
        FXMLLoader signupStudentInfoLoader = router.getParentNode("signup-student-info");
        FXMLLoader signupSchoolLoader = router.getParentNode("signup-school");
        FXMLLoader signupFinalLoader = router.getParentNode("signup-final");

        this.signinPane = (Pane) signinLoader.load();
        this.signupStartPane = (Pane) signupStartLoader.load();
        this.signupStudentInfoPane = (Pane) signupStudentInfoLoader.load();
        this.signupSchoolPane = (Pane) signupSchoolLoader.load();
        this.signupFinalPane = (Pane) signupFinalLoader.load();

        this.signUpStartcontroller = signupStartLoader.getController();
        signinLoader.getController();
        this.signUpStudentController = signupStudentInfoLoader.getController();
        this.signUpSchoolController = signupSchoolLoader.getController();
        this.signUpFinalController = signupFinalLoader.getController();

        this.signUpStartcontroller.getNextPageBtn().setOnAction(event -> {
            if (this.signUpStartcontroller.handleContinious()) {
                setASignupPaneVisible(signupStudentInfoPane);
            }
        });

        this.signUpStudentController.getNextBtn().setOnAction(event -> {
            if (this.signUpStudentController.handleContinious()) {
                setASignupPaneVisible(signupSchoolPane);
            }
        });

        this.signUpSchoolController.getNextBtn().setOnAction(event -> {
            if (this.signUpSchoolController.handleContinious()) {
                setASignupPaneVisible(signupFinalPane);
            }
        });

        this.signUpFinalController.getSignupBtn().setOnAction(event -> {
            router.navigateTo("main");
        });
        this.stackPane.getChildren().addAll(this.signinPane,
                this.signupStartPane,
                this.signupStudentInfoPane,
                this.signupSchoolPane,
                this.signupFinalPane);

        setASignupPaneVisible(null);

        this.signinPane.setVisible(true);

        this.isSignUp = false;
        this.initPosOfAnchor = anchor.getLayoutX();
        this.signupStartPane.setVisible(false);

        this.appTitle.setText("SSN-APP");
        this.athBtn.setText("Sign Up".toUpperCase());
        this.authLabelSign.setText("Don't have an account Yet ?");
        this.authCommentSign.setText("Lorem ipsum dolor sit amet, consectetur" +
                "adipiscing elit. Sed ultricies gravida sem et tempor");
    }

    private void setASignupPaneVisible(Pane pane) {
        if (pane == null)
            for (Node pane_ : this.stackPane.getChildren())
                pane_.setVisible(false);
        else
            for (Node pane_ : this.stackPane.getChildren())
                pane_.setVisible(pane.equals((Pane) pane_));
    }

}