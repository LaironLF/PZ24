package com.laironlf.pz24;

import android.view.MotionEvent;
import android.view.View;

public class SwipeListener implements View.OnTouchListener {


    private double startX, startY;
    private double finalX, finalY;

//    public SwipeListener(View view){
//        view.setOnTouchListener(this);
//    }

    /*
    The length of the finger movement after which the swipe will be made
     */
    public double maxX = 200;
    public double maxY = 200;

    public void swipeLeft(){
    }
    public void swipeRight(){

    }

    public void clearCords(){
        startX = 0;
        startY = 0;
        finalX = 0;
        finalY = 0;
    }

    private void CalculateSwipeType(double startX, double startY, double finalX, double finalY){
        double lengthXMovement = finalX - startX;
        double lengthYMovement = finalY - startY;
        if (Math.abs(lengthXMovement) > maxX && Math.abs(lengthYMovement) < maxY){
            if (lengthXMovement < 0){
                clearCords();
                swipeLeft();
            }
            if (lengthXMovement > 0){
                clearCords();
                swipeRight();
            }
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = motionEvent.getX();
                startY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                finalX = motionEvent.getX();
                finalY = motionEvent.getY();
                CalculateSwipeType(startX, startY, finalX, finalY);
                break;
        }
//        CalculateSwipeType(startX, startY, finalX, finalY);
        return true;
    }
}
