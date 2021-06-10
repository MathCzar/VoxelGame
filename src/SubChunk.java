import processing.core.PShape;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class SubChunk {

    Chunk parent;
    int index;

    ArrayList<PShape> meshes;

    public SubChunk(Chunk parent, int index) {
        this.parent = parent;
        this.index = index;

        meshes = new ArrayList<>();
    }

    public void draw() {

        PVector p = parent.world.player.position;
        PVector newPlayer = new PVector(p.x/100, p.y/100, p.z/100);

        if(distance(newPlayer)<parent.renderDistance) {
            for(PShape s:meshes) {

                parent.app.shape(s);

            }
        }

    }

    public double distance(PVector playerPos) {

        double x1 = parent.pos.x;
        double y1 = index*16;
        double z1 = parent.pos.y;

        double x2 = playerPos.x;
        double y2 = playerPos.y;
        double z2 = playerPos.z;

        return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));

    }

    public void genMeshes() {

        //clears the meshes
        meshes.clear();

        for(int z=0; z<16;z++) {
            boolean[][] fMask = new boolean[16][16];
            boolean[][] bMask = new boolean[16][16];
            boolean[][] lMask = new boolean[16][16];
            boolean[][] rMask = new boolean[16][16];
            boolean[][] tMask = new boolean[16][16];
            boolean[][] btMask = new boolean[16][16];
            for(int x=0;x<16;x++) {
                for(int y=0;y<16;y++) {

                    //front section
                    fMask[x][y] = true;

                    //handle front block
                    if(!parent.blocks[x][y+index*16][z].type.equals("air")) {

                        //if chunk border
                        if(z==0) {

                            fMask[x][y] = false;

                        }
                        //if block in front of us is air
                        else if(parent.blocks[x][y+index*16][z-1].type.equals("air")) {

                            fMask[x][y] = false;

                        }
                    }

                    //back section
                    bMask[x][y] = true;
                    //handle back block
                    if(!parent.blocks[x][y+index*16][z].type.equals("air")) {

                        //if chunk border
                        if(z==15) {

                            bMask[x][y] = false;

                        }
                        //if block behind of this block is air
                        else if(parent.blocks[x][y+index*16][z+1].type.equals("air")) {

                            bMask[x][y] = false;

                        }
                    }

                    //left section
                    lMask[x][y] = true;
                    //if this block isn't air
                    if(!parent.blocks[z][y+index*16][x].type.equals("air")) {

                        //if chunk border
                        if(z==0) {

                            lMask[x][y] = false;

                        }
                        //if block in front of us is air
                        else if(parent.blocks[z-1][y+index*16][x].type.equals("air")) {

                            lMask[x][y] = false;

                        }
                    }

                    //right section
                    rMask[x][y] = true;
                    //if this block isn't air
                    if(!parent.blocks[z][y+index*16][x].type.equals("air")) {

                        //if chunk border
                        if(z==15) {

                            rMask[x][y] = false;

                        }
                        //if block in front of us is air
                        else if(parent.blocks[z+1][y+index*16][x].type.equals("air")) {

                            rMask[x][y] = false;

                        }
                    }

                    //top section
                    tMask[x][y] = true;
                    //handle front block
                    if(!parent.blocks[x][z+index*16][y].type.equals("air")) {

                        //if chunk border
                        if(z==0&&index==0) {

                            tMask[x][y] = false;

                        }
                        //if block in front of us is air
                        else if(parent.blocks[x][(z-1)+index*16][y].type.equals("air")) {

                            tMask[x][y] = false;

                        }
                    }

                    //bottom section
                    btMask[x][y] = true;
                    //handle front block
                    if(!parent.blocks[x][z+index*16][y].type.equals("air")) {

                        //if chunk border
                        if(z==15&&index==15) {

                            btMask[x][y] = false;

                        }
                        //if block in front of us is air
                        else if(parent.blocks[x][(z+1)+index*16][y].type.equals("air")) {

                            btMask[x][y] = false;

                        }
                    }

                }

            }

            ArrayList<Rectangle> fMesh = maskToMesh(fMask);
            for(Rectangle r:fMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.front);

                pShape.vertex(r.x*100, r.y*100+index*1600, z*100, 0, 0);
                pShape.vertex((r.x+r.width)*100, r.y*100+index*1600, z*100, r.width*384, 0);
                pShape.vertex((r.x+r.width)*100, (r.y+r.height)*100+index*1600, z*100, r.width*384, r.height*384);
                pShape.vertex((r.x)*100, (r.y+r.height)*100+index*1600, z*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

            ArrayList<Rectangle> bMesh = maskToMesh(bMask);
            for(Rectangle r:bMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.front);

                pShape.vertex(r.x*100, r.y*100+index*1600, (z+1)*100, 0, 0);
                pShape.vertex((r.x+r.width)*100, r.y*100+index*1600, (z+1)*100, r.width*384, 0);
                pShape.vertex((r.x+r.width)*100, (r.y+r.height)*100+index*1600, (z+1)*100, r.width*384, r.height*384);
                pShape.vertex((r.x)*100, (r.y+r.height)*100+index*1600, (z+1)*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

            ArrayList<Rectangle> lMesh = maskToMesh(lMask);
            for(Rectangle r:lMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.front);

                pShape.vertex(z*100, r.y*100+index*1600, r.x*100, 0, 0);
                pShape.vertex(z*100, r.y*100+index*1600, (r.x+r.width)*100, r.width*384, 0);
                pShape.vertex(z*100, (r.y+r.height)*100+index*1600, (r.x+r.width)*100, r.width*384, r.height*384);
                pShape.vertex(z*100, (r.y+r.height)*100+index*1600, (r.x)*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

            ArrayList<Rectangle> rMesh = maskToMesh(rMask);
            for(Rectangle r:rMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.front);

                pShape.vertex((z+1)*100, r.y*100+index*1600, r.x*100, 0, 0);
                pShape.vertex((z+1)*100, r.y*100+index*1600, (r.x+r.width)*100, r.width*384, 0);
                pShape.vertex((z+1)*100, (r.y+r.height)*100+index*1600, (r.x+r.width)*100, r.width*384, r.height*384);
                pShape.vertex((z+1)*100, (r.y+r.height)*100+index*1600, (r.x)*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

            ArrayList<Rectangle> tMesh = maskToMesh(tMask);
            for(Rectangle r:tMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.top);

                pShape.vertex(r.x*100, z*100+index*1600, r.y*100, 0, 0);
                pShape.vertex((r.x+r.width)*100, z*100+index*1600, r.y*100, r.width*384, 0);
                pShape.vertex((r.x+r.width)*100, z*100+index*1600, (r.y+r.height)*100, r.width*384, r.height*384);
                pShape.vertex((r.x)*100, z*100+index*1600, (r.y+r.height)*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

            ArrayList<Rectangle> btMesh = maskToMesh(btMask);
            for(Rectangle r:btMesh) {

                PShape pShape = parent.app.createShape();
                pShape.beginShape();
                pShape.noStroke();
                pShape.texture(parent.textureManager.bottom);

                pShape.vertex(r.x*100, (z+1)*100+index*1600, r.y*100, 0, 0);
                pShape.vertex((r.x+r.width)*100, (z+1)*100+index*1600, r.y*100, r.width*384, 0);
                pShape.vertex((r.x+r.width)*100, (z+1)*100+index*1600, (r.y+r.height)*100, r.width*384, r.height*384);
                pShape.vertex((r.x)*100, (z+1)*100+index*1600, (r.y+r.height)*100, 0, r.height*384);

                pShape.endShape();
                meshes.add(pShape);
            }

        }

    }

    public ArrayList<Rectangle> maskToMesh(boolean[][] mask) {

        ArrayList<Rectangle> meshList = new ArrayList<>();


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
                Rectangle r = new Rectangle(startX, startY, (endX-startX)+1, (endY-startY)+1);
                meshList.add(r);

                for(int i=startX;i<=endX;i++) {
                    for(int a=startY;a<=endY;a++) {
                        mask[i][a] = true;
                    }
                }

            }
        }
        return meshList;

    }

}
