import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.MouseEvent;
import queasycam.QueasyCam;
import java.lang.reflect.Field;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Scanner;

public class Player extends QueasyCam {

    PApplet app;
    PVector center;
    World world;
    String typedMessage;
    String printedMessage;
    int mEnd;
    int mTotalChar;

    int curX, curY, curZ;
    int preCurX, preCurY, preCurZ;

    public Player(PApplet app, World world) {

        super(app);
        super.draw();

        app.registerMethod("mouseEvent", this);

        this.world = world;
        this.app = app;
        app.perspective(app.PI/3, app.width/(float)app.height, 0.01f, 10000);

        curX = -1;
        curY = -1;
        curZ = -1;
        preCurX = -1;
        preCurY = -1;
        preCurZ = -1;

        //typedMessage = "test test test test test no yes, 1000, !";
        //mTotalChar = typedMessage.length();
        //for(int mStart = 0; mStart < mTotalChar && mEnd <= mTotalChar; mStart++) {
        //    printedMessage = typedMessage.substring(mStart, mEnd);
        //    mStart=mStart*10;
        //    mEnd=mEnd*10;
        //    if(mEnd>mTotalChar){
        //        mTotalChar=mEnd;
        //    }
        //}


    }

    public void draw() {

        //cast our ray
        castRay();

        //draw oru cursor
        if(curX!=-1) {

            app.pushMatrix();
            app.translate(100*(curX/100)+50, 100*(curY/100)+50, 100*(curZ/100)+50);
            app.fill(0);
            app.noFill();
            app.strokeWeight(10);
            app.box(100);
            app.popMatrix();

        }

        //begin draw out HUD
        app.pushMatrix();
        app.camera();
        app.hint(PConstants.DISABLE_DEPTH_TEST);

        //draw fps on the screen
        app.textSize(50);
        app.fill(255, 0, 0);
        app.text("FPS: " + (int)app.frameRate, 0, 50);

        //draw the chat box
        //app.textSize(25);
        //app.fill(0, 255, 0);
        //app.text(printedMessage, 0, 800);

        //stop drawing the HUD
        app.hint(PConstants.ENABLE_DEPTH_TEST);
        app.popMatrix();

        super.draw();
    }

    public void castRay() {

        //get access to illegal variable
        try {

            Field field = getClass().getSuperclass().getDeclaredField("center");
            field.setAccessible(true);
            center = (PVector)field.get(this);

        }catch(Exception e) {}

        boolean tmp = true;
        int tselX = (int)(position.x);//(center.x-position.x));
        int tselY = (int)(position.y);//(center.y-position.y));
        int tselZ = (int)(position.z);//+(center.z-position.z));

        for(int i=2; i<500*8; i++) {

            //if type of block isn't air
            if(world.getBlock(new PVector(tselX/100, tselY/100, tselZ/100)).equals("grass")) {

                //mark location of cursor
                curX = tselX;
                curY = tselY;
                curZ = tselZ;

                //break
                tmp = false;
                break;

            }

            preCurX = tselX;
            preCurY = tselY;
            preCurZ = tselZ;

            //increment look position
            tselX = (int)(position.x+(center.x-position.x)/8*i);
            tselY = (int)(position.y+(center.y-position.y)/8*i);
            tselZ = (int)(position.z+(center.z-position.z)/8*i);

        }

        if(tmp) {

            curX = -1;
            curY = -1;
            curZ = -1;

        }


    }

    public void mouseEvent(MouseEvent event) {

        if(event.getAction()==MouseEvent.PRESS) {

            if(event.getButton()==37) {

                world.setBlock(new PVector(curX/100, curY/100, curZ/100), "air");

            }

            if(event.getButton()==39) {

                world.setBlock(new PVector(preCurX/100, preCurY/100, preCurZ/100), "grass");

            }


        }

    }

}
