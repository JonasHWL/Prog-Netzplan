package com.example.line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

public class LineE {

    //Line line;
    //Circle startPoint, endPoint;
    Random rand = new Random();
    /**
     * ArrayList which stores all Points
     * Change Points Class to abstract FlächenEinheiten
     */
    ArrayList<Points> pointsList = new ArrayList<>();

    public LineE(Pane root){
        createLine(root);
    }

    /**
     * Create all the Lines and Points
     * @param root Jonas erklär mal
     */
    private void createLine(Pane root){
        /*
        //Erstellung aller Punkte (Bald Flächeineinheiten)
        for (int i = 0; i < 5; i++){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            pointsList.add(new Points(xPos*20, yPos*20));
        }

        //Durch die pointsList durchgehen, um alle Punkte ein Paar zu geben
        for (int i = 0; i < pointsList.size(); i++){
            //Punkte A
            Points current = pointsList.get(i);
            //Punkt B, kontrolliert, ob das i element das letzte ist um als Paar das erste zu geben
            if(i+1 < pointsList.size()){
                Points next = pointsList.get(i+1);
                line = new Line(current.x(), current.y(), next.x(), next.y());
                //Falls es
            } else {
                line = new Line(current.x(), current.y(), pointsList.getFirst().x(), pointsList.getFirst().y());
            }
            //Die Liniengeneration von Jonas
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            startPoint = createDraggablePoint(line.getStartX(), line.getStartY());
            endPoint = createDraggablePoint(line.getEndX(), line.getEndY());
            root.getChildren().addAll(line, startPoint, endPoint);
        }

         */
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

            // Create the corner point
            int cornerX = next.x;
            int cornerY = current.y;

            // Draw the first line (horizontal)
            Line line1 = new Line(current.x, current.y, cornerX, cornerY);
            line1.setStroke(Color.BLACK);
            line1.setStrokeWidth(2);
            Circle startPoint1 = createDraggablePoint(line1.getStartX(), line1.getStartY());
            //Circle endPoint1 = createDraggablePoint(line1.getEndX(), line1.getEndY(), "blue");
            //root.getChildren().addAll(line1, startPoint1, endPoint1);
            root.getChildren().addAll(line1, startPoint1);
            //root.getChildren().addAll(line1);

            // Draw the second line (vertical)
            Line line2 = new Line(cornerX, cornerY, next.x, next.y);
            line2.setStroke(Color.BLACK);
            line2.setStrokeWidth(2);
            //Circle startPoint2 = createDraggablePoint(line2.getStartX(), line2.getStartY(), "blue");
            Circle endPoint2 = createDraggablePoint(line2.getEndX(), line2.getEndY());
            //root.getChildren().addAll(line2, startPoint2, endPoint2);
            root.getChildren().addAll(line2, endPoint2);
            //root.getChildren().addAll(line2);
        }


        /*
            int Z1 = rand.nextInt(100);
            int Z4 = rand.nextInt(100);
            int StartX = Z1;
            int StartY = Z4;


            for(int i = 0; i <= 5; i++) {

                int StopX = rand.nextInt(400);
                int StopY = rand.nextInt(400);
                // SX SY EX EY
                line = new Line(StartX, StartY, StopX, StopY);
                line.setStroke(Color.BLACK);
                line.setStrokeWidth(2);

                startPoint = createDraggablePoint(line.getStartX(), line.getStartY());
                endPoint = createDraggablePoint(line.getEndX(), line.getEndY());

                root.getChildren().addAll(line, startPoint, endPoint);

                StartX = StopX;
                StartY = StopY;
            }

         */
    }

    private Circle createDraggablePoint(double x, double y) {
        Circle point = new Circle(x, y, 5, Color.RED);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        return point;
    }

    private record Points(int x, int y) {

    }
}