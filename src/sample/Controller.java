package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    public GridPane gridPane;

    @FXML
    public Button button00;
    @FXML
    public Button button10;
    @FXML
    public Button button20;
    @FXML
    public Button button01;
    @FXML
    public Button button11;
    @FXML
    public Button button21;
    @FXML
    public Button button02;
    @FXML
    public Button button12;
    @FXML
    public Button button22;

    @FXML
    public Button cancelButton;
    @FXML
    public Button newGameButton;

    @FXML
    public Label playerOneScore;
    @FXML
    public Label playerTwoScore;

    private final List<Button> buttons = new ArrayList<>();
    private Button[][] buttonsLayout = new Button[3][3];
    private int[][] tableO = new int[3][3];
    private int[][] tableX = new int[3][3];
    int timesClicked = 0;
    int playerTurn = 1;

    private int playerOnePoints = 0;
    private int playerTwoPoints = 0;

    public void initialize(){
        buttons.add(button00);
        buttons.add(button10);
        buttons.add(button20);
        buttons.add(button01);
        buttons.add(button11);
        buttons.add(button21);
        buttons.add(button02);
        buttons.add(button12);
        buttons.add(button22);

        buttonsLayout = new Button[][]{{button00,button01,button02},{button10,button11,button12},
            {button20,button21,button22}};

        for(Button button:buttons){
            button.setBackground(Background.EMPTY);
        }

        cancelButton.setBackground(Background.EMPTY);
        newGameButton.setBackground(Background.EMPTY);

        // After changes in controller this will make your stage transparent
        gridPane.setBackground(Background.EMPTY);

    }

    @FXML
    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        System.out.println("Game was closed!");
    }

    @FXML
    public void newGameButtonOnAction(){
        playerTurn = 1;
        timesClicked = 0;
        tableO = new int[3][3];
        tableX = new int[3][3];
        for(Button button:buttons){
            button.setText(" ");
            button.setDisable(false);
        }
        System.out.println("A new game was launched!");
    }

    private boolean gameIsOver(){
        boolean result = false;

        int oColumn0 = 0;
        int oColumn1 = 0;
        int oColumn2 = 0;
        int oRow0 = 0;
        int oRow1 = 0;
        int oRow2 = 0;
        int oDiag0=0;
        int oDiag1 = 0;

        int xColumn0 = 0;
        int xColumn1 = 0;
        int xColumn2 = 0;
        int xRow0 = 0;
        int xRow1 = 0;
        int xRow2 = 0;
        int xDiag0 = 0;
        int xDiag1 = 0;

        if(timesClicked>4){

            for(int i=0;i<tableO[0].length;i++){
                oRow0 += tableO[0][i];
                oRow1 += tableO[1][i];
                oRow2 += tableO[2][i];
                oColumn0 += tableO[i][0];
                oColumn1 += tableO[i][1];
                oColumn2 += tableO[i][2];
                oDiag0 += tableO[i][i];
                oDiag1 += tableO[i][2-i];

                xRow0 += tableX[0][i];
                xRow1 += tableX[1][i];
                xRow2 += tableX[2][i];
                xColumn0 += tableX[i][0];
                xColumn1 += tableX[i][1];
                xColumn2 += tableX[i][2];
                xDiag0 += tableX[i][i];
                xDiag1 += tableX[i][2-i];
            }

            List<Integer> sumsO = List.of(oRow0,oRow1,oRow2,oColumn0,oColumn1,oColumn2,oDiag1,oDiag0);
            List<Integer> sumsX = List.of(xRow0,xRow1,xRow2,xColumn0,xColumn1,xColumn2,xDiag0,xDiag1);

            if(sumsO.contains(3)){
                result = true;
            }else if(sumsX.contains(3)){
                result = true;
            }

        }

        if(timesClicked==9){
            result = true;
        }

        if(result){
            for (Button button:buttons){
                button.setDisable(true);
            }
        }

        return result;

    }

    private boolean eceededClicks(){
        return timesClicked==9;
    }

    private void setButtonAction(Button button){
        if(playerTurn==1){
            button.setText("X");
            button.setTextFill(Color.BLUE);
            playerTurn = 2;
        }else{
            button.setText("O");
            button.setTextFill(Color.RED);
            playerTurn = 1;
        }
        timesClicked++;
        System.out.println("Text: " + button.getText() + " player turn: " + playerTurn
                + " Times a button was clicked: " + timesClicked);
    }

    private void finishProcess(){
        System.out.println("Game is over.");
        if(!eceededClicks()){
            if(playerTurn==1){
                playerOnePoints++;
                playerOneScore.setText("Score: " + playerOnePoints);
            }else{
                playerTwoPoints++;
                playerTwoScore.setText("Score: " + playerTwoPoints);
            }
        }else{
            System.out.println("Tie.");
        }
    }

    public void buttonProcess(int x,int y){
        if(tableX[x][y]+tableO[x][y]==0){
            setButtonAction(buttonsLayout[x][y]);
            if(playerTurn == 1){
                tableO[x][y] = 1;
            }else{
                tableX[x][y] = 1;
            }

            if(gameIsOver()){
                finishProcess();
            }
        }else if(gameIsOver()){
            finishProcess();
        }
    }

    @FXML
    public void button00OnAction(){
        buttonProcess(0,0);
    }
    @FXML
    public void button10OnAction(){
        buttonProcess(1,0);
    }
    @FXML
    public void button20OnAction(){
        buttonProcess(2,0);
    }
    @FXML
    public void button01OnAction(){
        buttonProcess(0,1);
    }
    @FXML
    public void button11OnAction(){
        buttonProcess(1,1);
    }
    @FXML
    public void button21OnAction(){
        buttonProcess(2,1);
    }
    @FXML
    public void button02OnAction(){
        buttonProcess(0,2);
    }
    @FXML
    public void button12OnAction(){
        buttonProcess(1,2);
    }
    @FXML
    public void button22OnAction(){
        buttonProcess(2,2);
    }

}
