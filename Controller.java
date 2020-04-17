package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    private Label end;
    @FXML
    private Label current;
    @FXML
    private Label start;
    @FXML
    private Label tl;
    @FXML
    private Label tm;
    @FXML
    private Label tr;
    @FXML
    private Label ml;
    @FXML
    private Label mm;
    @FXML
    private Label mr;
    @FXML
    private Label bl;
    @FXML
    private Label bm;
    @FXML
    private Label br;

    String startingString = "851432067";
    String pathToVictory = new StringBuilder(howToSolve(startingString)).reverse().toString();

    public void initialize(){
        start.setText(startingString);
        end.setText("123456780");
        current.setText(startingString);
        visualize();
    }

    public void visualize(){
        tl.setText((String.valueOf(current.getText().charAt(0))));
        tm.setText((String.valueOf(current.getText().charAt(1))));
        tr.setText((String.valueOf(current.getText().charAt(2))));
        ml.setText((String.valueOf(current.getText().charAt(3))));
        mm.setText((String.valueOf(current.getText().charAt(4))));
        mr.setText((String.valueOf(current.getText().charAt(5))));
        bl.setText((String.valueOf(current.getText().charAt(6))));
        bm.setText((String.valueOf(current.getText().charAt(7))));
        br.setText((String.valueOf(current.getText().charAt(8))));
    }


    public void showNextStep(){
        if(pathToVictory.equals("steps")){
            current.setText("unsolvable");
        }else{
            if(pathToVictory.length()!=0){
                int moveZeroBy = 0;
                if(pathToVictory.charAt(pathToVictory.length()-1)=='u'){
                    moveZeroBy = -3;
                }
                if(pathToVictory.charAt(pathToVictory.length()-1)=='d'){
                    moveZeroBy = 3;
                }
                if(pathToVictory.charAt(pathToVictory.length()-1)=='l'){
                    moveZeroBy = -1;
                }
                if(pathToVictory.charAt(pathToVictory.length()-1)=='r'){
                    moveZeroBy = 1;
                }
                char[] currentContent = current.getText().toCharArray();
                int indexOfZero = current.getText().indexOf('0');
                currentContent[indexOfZero]=currentContent[indexOfZero+moveZeroBy];
                currentContent[indexOfZero+moveZeroBy]='0';
                current.setText(new String(currentContent));
                visualize();
                pathToVictory = pathToVictory.substring(0,pathToVictory.length()-1);
            }
        }
    }

    public String howToSolve(String start) {
        ArrayList<ArrayList<String>> queue = new ArrayList<>(); //numbers, history, flag
        ArrayList<String> queueStart = new ArrayList<>();
        queueStart.add(start);
        queueStart.add("");
        queueStart.add("alive");
        queue.add(queueStart);
        int steps = 0;
        while (steps < 32) {
            System.out.println(steps);
            steps++;
            ArrayList<ArrayList<String>> addToQueue = new ArrayList<>();
            for (ArrayList<String> nodes : queue) {
                if(nodes.get(2).equals("alive")) {
                    if (nodes.get(0).equals("123456780")) {
                        return nodes.get(1);
                    }
                    nodes.set(2, "dead");
                    int indexOfZero = nodes.get(0).indexOf("0");
                    int numberOfKids = 0;
                    ArrayList<String> whereToMove = new ArrayList<>();
                    ArrayList<Integer> moveZeroBy = new ArrayList<>();
                    if (indexOfZero == 0 || indexOfZero == 1 || indexOfZero == 2 || indexOfZero == 3 || indexOfZero == 4 || indexOfZero == 5) {
                        numberOfKids++;
                        moveZeroBy.add(3);
                        whereToMove.add("d");
                    }
                    if (indexOfZero == 0 || indexOfZero == 1 || indexOfZero == 3 || indexOfZero == 4 || indexOfZero == 6 || indexOfZero == 7) {
                        numberOfKids++;
                        moveZeroBy.add(1);
                        whereToMove.add("r");
                    }
                    if (indexOfZero == 3 || indexOfZero == 4 || indexOfZero == 5 || indexOfZero == 6 || indexOfZero == 7 || indexOfZero == 8) {
                        numberOfKids++;
                        moveZeroBy.add(-3);
                        whereToMove.add("u");
                    }
                    if (indexOfZero == 1 || indexOfZero == 2 || indexOfZero == 4 || indexOfZero == 5 || indexOfZero == 7 || indexOfZero == 8) {
                        numberOfKids++;
                        moveZeroBy.add(-1);
                        whereToMove.add("l");
                    }

                    for (int i = numberOfKids-1; i >= 0; i--) {
                        ArrayList<String> placeholderArrayList = new ArrayList<>();
                        char[] placeholderCharArray = nodes.get(0).toCharArray();
                        placeholderCharArray[indexOfZero] = placeholderCharArray[indexOfZero + moveZeroBy.get(i)];
                        placeholderCharArray[indexOfZero + moveZeroBy.get(i)] = '0';
                        placeholderArrayList.add(new String(placeholderCharArray));
                        placeholderArrayList.add(nodes.get(1) + whereToMove.get(i));
                        placeholderArrayList.add("alive");
                        addToQueue.add(placeholderArrayList);
                    }
                }
            }
            for (ArrayList<String> toBeAddedToQueue : addToQueue) {
                boolean flag = true;
                for (ArrayList<String> toCompare : queue){
                    if (toBeAddedToQueue.get(0).equals(toCompare.get(0))) {
                        flag = false;
                        break;
                    }
                }
                if(flag){queue.add(toBeAddedToQueue);}
            }
        }
        return "steps";
    }
}
