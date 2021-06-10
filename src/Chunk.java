import processing.core.PApplet;
import processing.core.PVector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Chunk {

    PApplet app;

    World world;

    Block[][][] blocks;
    int width, height, depth;

    SubChunk[] subChunks;

    TextureManager textureManager;
    PVector pos;

    int renderDistance = 60;

    public Chunk(PApplet app, TextureManager textureManager, PVector pos, World world, String fileName) {

        this.app = app;
        this.pos = pos;
        this.textureManager = textureManager;
        this.world = world;

        width = 16;
        height = 256;
        depth = 16;
        blocks = new Block[width][height][depth];

        try {
            Scanner scan = new Scanner(new FileInputStream("world/" + fileName));

            String s = scan.nextLine();

            int i = 0;

            for(int x=0; x<width;x++) {
                for(int y=0; y<height;y++) {
                    for(int z=0; z<depth;z++) {

                        char c = s.charAt(i);

                        String type = c =='0'?"grass":"air";

                        blocks[x][y][z] = new Block(app, textureManager, type);

                        i++;

                    }
                }
            }

        } catch (Exception e) { e.printStackTrace();}

        subChunks = new SubChunk[16];
        for(int i=0;i<16;i++) {

            subChunks[i] = new SubChunk(this, i);

        }
        for(int i=0;i<16;i++) {

            subChunks[i].genMeshes();

        }

    }

    public Chunk(PApplet app, TextureManager textureManager, PVector pos, World world) {

        this.app = app;
        this.pos = pos;
        this.textureManager = textureManager;
        this.world = world;

        //set up our blocks
        width = 16;
        height = 256;
        depth = 16;
        blocks = new Block[width][height][depth];
        for(int x=0; x<width;x++) {
            for(int y=0; y<height;y++) {

                for(int z=0; z<depth;z++) {

                    String type = app.noise((x+pos.x*16)*0.08f, y*0.08f, (z+pos.y*16)*0.08f)>0.5?"grass":"air";
                    blocks[x][y][z] = new Block(app, textureManager, type);

                }
            }
        }

        subChunks = new SubChunk[16];
        for(int i=0;i<16;i++) {

            subChunks[i] = new SubChunk(this, i);

        }
        for(int i=0;i<16;i++) {

            subChunks[i].genMeshes();

        }

    }

    public void draw() {

        PVector p = world.player.position;
        PVector newPlayer = new PVector(p.x/100, p.y/100, p.z/100);
        if(distance(newPlayer)<renderDistance) {

            for (SubChunk s : subChunks) {

                s.draw();

            }

        }

    }

    public double distance(PVector playerPos) {

        double x1 = pos.x;
        double y1 = pos.y;
        double x2 = playerPos.x;
        double y2 = playerPos.z;
        return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );

    }

    public void setBlock(int x, int y, int z, String type) {

        try {

            Block block = blocks[x][y][z];
            block.type = type;
            subChunks[y/16].genMeshes();

        } catch(Exception e) {}

    }

    //saves the file to the "world" directory
    public void save() {

        int cx = (int) pos.x;
        int cy = (int) pos.y;

        try {

            PrintWriter writer = new PrintWriter(new FileOutputStream("world/" + cx + "#" + cy + ".chunk"));

            String out = "";

            for(int x=0; x<width;x++) {
                for(int y=0; y<height;y++) {
                    for(int z=0; z<depth;z++) {

                        int t = 0;

                        if(!blocks[x][y][z].type.equals("air")) {

                            t = 1;

                        }

                        out += (t + " ");

                    }
                }
            }

            writer.println(out);

            writer.close();

        }catch (Exception e) {
            System.out.println(e);
        }

    }
}
