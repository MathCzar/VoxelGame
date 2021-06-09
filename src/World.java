import processing.core.PApplet;
import processing.core.PVector;
import queasycam.QueasyCam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class World {

    //our player
    Player player;

    //the chunks of the world
    HashMap<String, Chunk> chunks;
    int width;
    int length;

    //draw stuff
    PApplet app;

    //get textures
    TextureManager textureManager;



    public World(PApplet app) {

        this.app = app;

        //set up our player
        player = new Player(app, this);

        //set up our texture
        textureManager = new TextureManager(app);


        File file = new File("world");

        if(file.listFiles().length != 0) {

            for(File f: file.listFiles()) {

                chunks = new HashMap<>();

                int x = Integer.parseInt(f.getName().split("\\.")[0].split("#")[0]);
                int y = Integer.parseInt(f.getName().split("\\.")[0].split("#")[1]);

                PVector pos = new PVector(x, y);

                chunks.put(x + "#" + y, new Chunk(app, textureManager, pos, this, f.getName()));

            }

        }
        else{

            //set up chunks
            width = 2;
            length = 2;
            chunks = new HashMap<>();
            for(int x=0; x<width; x++) {
                for(int y=0; y<length; y++) {

                    PVector pos = new PVector(x, y);
                    chunks.put(x + "#" + y, new Chunk(app, textureManager, pos, this));

                }
            }

        }

    }

    public void draw() {

        for(int x=0;x<width;x++) {
            for(int y=0;y<length;y++) {

                app.pushMatrix();
                app.translate(x*1600, 0, y*1600);

                chunks.get(x + "#" + y).draw();

                app.popMatrix();

            }
        }

    }

    public String getBlock(PVector blockPos) {

        int chunkX = (int)(blockPos.x/16);
        int chunkY = (int)(blockPos.z/16);
        int blockX = (int)(blockPos.x%16);
        int blockY = (int)(blockPos.y);
        int blockZ = (int)(blockPos.z%16);

        try {

            Chunk chunk = chunks.get(chunkX + "#" + chunkY);
            return chunk.blocks[blockX][blockY][blockZ].type;

        }catch(Exception e) {}

        return "";

    }

    public void setBlock(PVector blockPos, String type) {

        //finds the chunk
        int chunkX = (int)(blockPos.x/16);
        int chunkY = (int)(blockPos.z/16);

        //finds the block
        int blockX = (int)(blockPos.x%16);
        int blockY = (int)(blockPos.y);
        int blockZ = (int)(blockPos.z%16);

        try {

            Chunk chunk = chunks.get(chunkX + "#" + chunkY);
            chunk.setBlock(blockX, blockY, blockZ, type);

        }catch(Exception e){}

    }

    public void saveFile() {

        //looping through the chunks
        Iterator it = chunks.entrySet().iterator();
        while (it.hasNext()) {

            //get chunk
            Map.Entry entry = (Map.Entry) it.next();
            Chunk chunk = (Chunk) entry.getValue();

            //write chunk to file
            chunk.save();

        }

    }

}
