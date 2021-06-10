import processing.core.PApplet;

public class VoxelGame extends PApplet{

    World world;

    public static void main(String[] args) {
        PApplet.main("VoxelGame");
    }

    public void settings() {
        size(1000, 800, P3D);
    }

    public void setup() {

        //gets the app from the world class
        world = new World(this);

    }

    public void draw() {

        //draws a block background
        background(0);

        //draws the world
        world.draw();

    }

    public void dispose() {

        //saves the chunks
        world.saveFile();

        super.dispose();

    }
}
