import processing.core.PApplet;
import processing.core.PImage;

public class TextureManager {

    public PImage texture;
    public PImage front, top, bottom;

    public TextureManager(PApplet app) {

        try{

            texture = app.loadImage("res/grass.jpg");

        }catch(Exception e){}

        front = texture.get(0, 384, 384, 384);
        top = texture.get(384, 0, 384, 384);
        bottom = texture.get(384, 384 * 2, 384, 384);


        //create 16x16 grid of our front texture
        PImage image = new PImage(384*16, 384*16);
        for(int x=0;x<16;x++) {

            for(int y=0;y<16;y++) {

                image.set(x*384, y*384, front);

            }

        }

        front = image;

        //create 16x16 grid of our top texture
        image = new PImage(384*16, 384*16);
        for(int x=0;x<16;x++) {

            for(int y=0;y<16;y++) {

                image.set(x*384, y*384, top);

            }

        }

        top = image;

        //create 16x16 grid of our bottom texture
        image = new PImage(384*16, 384*16);
        for(int x=0;x<16;x++) {

            for(int y=0;y<16;y++) {

                image.set(x*384, y*384, bottom);

            }

        }

        bottom = image;

    }

}
