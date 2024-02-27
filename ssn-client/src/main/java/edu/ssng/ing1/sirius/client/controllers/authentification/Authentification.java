package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/**
 * Test
 */
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
    private SignInController signIncontroller;
    private SignUpStudentInfoController signUpStudentController;
    private SignUpSchoolController signUpSchoolController;
    private SignUpFinalController signUpFinalController;

    private HashMap<String, Pane> stackPaneMap;

    // private SignUpStartContoller signUpStartContoller;

    @FXML
    public void changePosition(ActionEvent event) {
        double distBtwnStackandprimary = primaryanchor.getWidth() - stackPane.getWidth();
        reverseNodes(this.anchor, this.athBtn, this.authLabelSign, this.authCommentSign, this.appTitle);
        this.anchor.setTranslateX(isSignUp ? 0 : -initPosOfAnchor);
        this.stackPane.setTranslateX(!isSignUp ? distBtwnStackandprimary : 0);

        this.isSignUp = !this.isSignUp;

        // this.signinPane.setVisible(!isSignUp);
        // this.signupStartPane.setVisible(isSignUp);

        if (isSignUp) {
            setASignupPaneVisible(this.signupStartPane);
            this.signinPane.setVisible(false);
        } else {
            setASignupPaneVisible(null);
            this.signinPane.setVisible(true);
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
        for (Node node : nodes) {
            reverseNode(node);
        }
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
        this.signIncontroller = signinLoader.getController();
        this.signUpStudentController = signupStudentInfoLoader.getController();
        this.signUpSchoolController = signupSchoolLoader.getController();
        this.signUpFinalController = signupFinalLoader.getController();

        this.signUpStartcontroller.getNextPageBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setASignupPaneVisible(signupStudentInfoPane);
            }
        });

        this.signUpStudentController.getNextBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setASignupPaneVisible(signupSchoolPane);
            }
        });

        this.signUpSchoolController.getNextBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setASignupPaneVisible(signupFinalPane);
            }
        });

        this.signUpFinalController.getSignupBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                router.navigateTo("main");
            }
        });
        this.stackPane.getChildren().addAll(this.signinPane,
                this.signupStartPane,
                this.signupStudentInfoPane,
                this.signupSchoolPane,
                this.signupFinalPane);

        setASignupPaneVisible(null);

        this.signinPane.setVisible(true);
        // this.signupStartPane.setVisible(false);
        // this.signupStudentInfoPane.setVisible(false);
        // this.signupSchoolPane.setVisible(false);
        // this.signupFinalPane.setVisible(false);

        this.isSignUp = false;
        this.initPosOfAnchor = anchor.getLayoutX();
        this.signupStartPane.setVisible(false);
        // this.stackPane.getChildren().addAll(this.signinPane, this.signupStartPane);
        this.appTitle.setText("SSN-APP");
        this.athBtn.setText("Sign Up".toUpperCase());
        this.authLabelSign.setText("Don't have an account Yet ?");
        this.authCommentSign.setText("Lorem ipsum dolor sit amet, consectetur" +
                "adipiscing elit. Sed ultricies gravida sem et tempor");
    }

    private void eventGoToNextPage() {

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