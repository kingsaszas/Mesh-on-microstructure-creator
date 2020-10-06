package mesh;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    ArrayList<Point> nodes;
    ArrayList<ForGrainMesh> forGrainMeshes;
    ArrayList<Grain> grains;

    BufferedImage bfimg;

    Node rootNode;

    int width;
    int height;

    int tolerance = 11;
    int IDselected;

    public DataGenerator(){
        nodes = new ArrayList<>();                    // do utworzenia ogolnie siatki
        forGrainMeshes = new ArrayList<>();          // do zapisu elementów ktore utwworza siatke nad ziarnem
        grains = new ArrayList<>();                 //do zapisu ziaren

    }

}
