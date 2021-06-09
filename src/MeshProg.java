import com.sun.org.apache.xpath.internal.objects.XBoolean;
import processing.core.PApplet;
import java.awt.*;
import java.util.ArrayList;

public class MeshProg extends PApplet {

    int[][] grid;
    ArrayList<Rectangle> meshes;
    boolean swap;

    public static void main(String[] args) {
        PApplet.main("MeshProg");
    }

    public void settings() {

        size(800, 800);

    }

    public void setup() {

        grid = new int[16][16];
        for(int x=0;x<16;x++) {
            for (int y = 0; y < 16; y++) {

                grid[x][y] = 0;

            }
        }

        meshes = new ArrayList<>();
        swap = false;

    }

    public void draw() {

        background(0);

        if(!swap) {

            drawGrid();

        }
        else {

            drawRectz();

        }

    }

    public void drawRectz() {

        for(Rectangle r:meshes) {

            rect(r.x*50, r.y*50, r.width*50, r.height*50);


        }

    }

    public void mouseDragged() {

        int x = mouseX/50;
        int y = mouseY/50;

        try {
            if (mousePressed) {
                if (mouseButton == RIGHT) {

                    grid[x][y] = 0;

                }
                if (mouseButton == LEFT) {

                    grid[x][y] = 1;

                }
            }
        } catch(Exception e) {}

    }

    public void keyPressed() {

        if(key == 's') {

            if(!swap) {
                genMeshes();
            }
            swap = !swap;

        }

    }

    public void drawGrid() {

        for(int x=0;x<16;x++) {
            for (int y = 0; y < 16; y++) {
                if(grid[x][y]==0) {

                    fill(255, 0, 0);

                }
                else {

                    fill(0, 255, 0);

                }
                rect(x*50, y*50, 50, 50);

            }
        }

    }

    public void genMeshes() {

        //clear all previous meshes
        meshes.clear();

        //create a copy of the grid
        boolean[][] mask = new boolean[16][16];
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {

                mask[x][y] = grid[x][y] == 1;

            }
        }

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {

                //if this is marked
                if(mask[x][y]) {

                    continue;

                }

                //find the width of the rectangle that fits
                int startX = x;
                int endX = startX;
                for(;endX<16&&!mask[endX][y];endX++);
                endX--;

                //find the height of the rectangle that fits
                int startY = y;
                int endY = startY;
                boolean clear = true;
                for(;endY<16&&clear;endY++) {

                    for(int i=startX;i<=endX;i++) {

                        if(mask[i][endY]) {

                            clear = false;
                            endY--;
                            break;

                        }
                    }
                }
                endY--;

                //find rectangle from points
                Rectangle r = new Rectangle(startX, startY, endX-startX+1, endY-startY+1);
                meshes.add(r);

                for(int i=startX;i<=endX;i++) {
                    for(int a=startY;a<=endY;a++) {
                        mask[i][a] = true;
                    }
                }

            }
        }

    }

}
