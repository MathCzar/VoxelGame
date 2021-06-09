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

        world = new World(this);

    }

    public void draw() {

        background(0);

        world.draw();

    }

    public void dispose() {

        world.saveFile();

        super.dispose();

    }
}
