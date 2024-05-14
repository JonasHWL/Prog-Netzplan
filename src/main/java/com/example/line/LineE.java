package com.example.line;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.Random;

public class LineE {

    Line line;
    Circle startPoint, endPoint;
    Random rand = new Random();

    public LineE(Pane root){
        createLine(root);
    }

    private void createLine(Pane root){

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
    }

    private Circle createDraggablePoint(double x, double y) {
        Circle point = new Circle(x, y, 5, Color.RED);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        return point;
    }
}