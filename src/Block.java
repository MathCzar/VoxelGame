import processing.core.PApplet;
import processing.core.PImage;

public class Block {

    private int scale;

    private PApplet app;
    private PImage texture;
    private PImage front, top, bottom;
    String type;

    public Block(PApplet app, TextureManager textureManager, String type) {

        //scales the block
        scale = 100;


        this.app = app;
        this.type = type;

        //gets the textures from the "TextureManager" class
        front = textureManager.front;
        top = textureManager.top;
        bottom = textureManager.bottom;

    }

    public void draw() {

        //draw all the faces of the block
        this.drawFront();
        this.drawBack();
        this.drawLeft();
        this.drawRight();
        this.drawTop();
        this.drawBottom();

    }

    public void drawFront() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(front);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(0 * scale, 0 * scale, 0 * scale, 0, 0);
        app.vertex(1 * scale, 0 * scale, 0 * scale, 1, 0);
        app.vertex(1 * scale, 1 * scale, 0 * scale, 1, 1);
        app.vertex(0 * scale, 1 * scale, 0 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

    public void drawBack() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(front);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(0 * scale, 0 * scale, 1 * scale, 0, 0);
        app.vertex(1 * scale, 0 * scale, 1 * scale, 1, 0);
        app.vertex(1 * scale, 1 * scale, 1 * scale, 1, 1);
        app.vertex(0 * scale, 1 * scale, 1 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

    public void drawLeft() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(front);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(0 * scale, 0 * scale, 0 * scale, 0, 0);
        app.vertex(0 * scale, 0 * scale, 1 * scale, 1, 0);
        app.vertex(0 * scale, 1 * scale, 1 * scale, 1, 1);
        app.vertex(0 * scale, 1 * scale, 0 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

    public void drawRight() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(front);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(1 * scale, 0 * scale, 0 * scale, 0, 0);
        app.vertex(1 * scale, 0 * scale, 1 * scale, 1, 0);
        app.vertex(1 * scale, 1 * scale, 1 * scale, 1, 1);
        app.vertex(1 * scale, 1 * scale, 0 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

    public void drawTop() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(top);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(0 * scale, 0 * scale, 0 * scale, 0, 0);
        app.vertex(1 * scale, 0 * scale, 0 * scale, 1, 0);
        app.vertex(1 * scale, 0 * scale, 1 * scale, 1, 1);
        app.vertex(0 * scale, 0 * scale, 1 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

    public void drawBottom() {

        //start drawing face
        app.beginShape();

        //apply texture
        app.texture(bottom);
        app.textureMode(app.NORMAL);

        //give vertices
        app.vertex(0 * scale, 1 * scale, 0 * scale, 0, 0);
        app.vertex(1 * scale, 1 * scale, 0 * scale, 1, 0);
        app.vertex(1 * scale, 1 * scale, 1 * scale, 1, 1);
        app.vertex(0 * scale, 1 * scale, 1 * scale, 0, 1);

        //stop drawing face
        app.endShape();

    }

}
