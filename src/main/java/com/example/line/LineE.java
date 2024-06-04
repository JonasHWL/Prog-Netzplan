package com.example.line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

public class LineE {
    //private final int seed = randSeed.nextInt();
    private final long seed = 9223372036854775807L; //Hardcoded seed als Beispiel
    private final Random rand = new Random(seed);
    /**
     * ArrayList which stores all Points
     * Change Points Class to abstract FlächenEinheiten
     */
    private final ArrayList<Points> pointsList = new ArrayList<>();

    /**
     * ArrayList which stores all Circles
     */
    private final ArrayList<Circle> circleList = new ArrayList<>();

    /**
     * ArrayList which stores all Lines
     */
    private final ArrayList<Line> lineList = new ArrayList<>();

    public LineE(Pane root){
        createLine(root);
    }

    /**
     * Create all the Lines and Points
     * @param root Jonas erklär mal
     */
    private void createLine(Pane root){
        for (int i = 0; i < 5; i++){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            pointsList.add(new Points(xPos*20, yPos*20));
        }

        for (int i = 0; i < pointsList.size(); i++) {
            // Current point A
            Points current = pointsList.get(i);
            // Next point B, check if the current element is the last to loop back to the first
            Points next;
            if (i + 1 < pointsList.size()) {
                next = pointsList.get(i + 1);
            } else {
                next = pointsList.getFirst();
            }

            Points middle = new Points( (current.x+next.x)/2, (current.y+next.y)/2);

            //Create a circle and store it in circleList
            Circle circlePoint = createPoint(current.x, current.y);
            circleList.add(circlePoint);

            //Create a circle and store it in circleList
            circlePoint = createPoint(next.x, next.y);
            circleList.add(circlePoint);

            Line line;

            //Create line (horizontal) to middle
            line = new Line(current.x,current.y,current.x,middle.y);
            lineList.add(addLine(line));

            //Create line (vertical) to middle and store it in lineList
            line = new Line(current.x,middle.y,middle.x,middle.y);
            lineList.add(addLine(line));

            //Create line (horizontal) and store it in lineList
            line = new Line(middle.x, middle.y, middle.x, next.y);
            lineList.add(addLine(line));

            // Create the second line (vertical) and store it in lineList
            line = new Line(middle.x, next.y, next.x, next.y);
            lineList.add(addLine(line));
        }

        //Draw all Lines
        for (Line line : lineList) {
            root.getChildren().addAll(line);
        }
        //Draw all Circles
        for (Circle circle : circleList) {
            root.getChildren().addAll(circle);
        }

    }

    private Circle createPoint(double x, double y) {
        Circle point = new Circle(x, y, 5, Color.RED);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        return point;
    }

    private Line addLine(Line line){
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        return line;
    }

    private record Points(int x, int y) { }
}