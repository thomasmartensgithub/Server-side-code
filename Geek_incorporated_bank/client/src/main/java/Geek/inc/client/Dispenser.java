package Geek.inc.client;
import lejos.nxt.*;


public class Dispenser {

    public static void main(String[] args) {
        try{
            vijftig(1);
            twintig(1);
            tien(1);
        }
        catch(InterruptedException e){

        }
    }

    public static void vijftig(int a)throws InterruptedException {
        for(int i=0;i<a;i++){
            Motor.A.rotateTo(-340);
            Thread.sleep(1000);
            Motor.A.rotateTo(170);
            Motor.A.stop();
        }
    }
    public static void twintig(int b)throws InterruptedException {
        for(int i=0;i<b;i++){
            Motor.B.rotateTo(-340);
            Thread.sleep(1000);
            Motor.B.rotateTo(170);
            Motor.B.stop();
        }
    }
    public static void tien(int c)throws InterruptedException {
        for(int i=0;i<c;i++){
            Motor.C.rotateTo(-340);
            Thread.sleep(1000);
            Motor.C.rotateTo(170);
            Motor.C.stop();
        }
    }
}