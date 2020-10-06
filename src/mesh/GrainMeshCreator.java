package mesh;

import java.awt.*;

public class GrainMeshCreator {

    DataGenerator dg;

    GrainMeshCreator(DataGenerator dg){
        this.dg = dg;
    }

    void IDSearch(){

        Boolean isFounded;
        int ID=0;

        for(int h = 0; h < dg.bfimg.getHeight(); h++) {
            for (int w = 0; w<dg.bfimg.getWidth();w++) {
                isFounded = false;

                Color getColor = new Color(dg.bfimg.getRGB(w,h));

                for(Grain grain: dg.grains)
                    if(grain.color.getRGB() == getColor.getRGB())
                        isFounded = true;

                    if(!isFounded){
                        ID++;
                        dg.grains.add(new Grain(ID,getColor,w,h));
                    }
            }
        }


        for(Grain g: dg.grains){
            System.out.println("ID: " + g.ID+"   point: "+ g.x +" , " +g.y);
        }

        System.out.println("wymiary: "+ dg.bfimg.getWidth() +" "+ dg.bfimg.getHeight());

    }


    int CheckFill(Rectangle rect, Grain g){
        Boolean thatColor = false, isMixed = false, anotherColor = false;


        for(int w = rect.p.x; w<(rect.p.x + rect.width);w++){
            for(int h = rect.p.y;h<(rect.p.y + rect.height);h++) {

                Color readColor = new Color(dg.bfimg.getRGB(w, h));

                if (readColor.getRGB() == g.color.getRGB())
                    thatColor = true;
                else
                    anotherColor = true;

                if (thatColor && anotherColor)
                    isMixed = true;

            }

        }

        if(isMixed)
            return 0;
        else if(thatColor)
            return 1;
        else
            return 1000;
    }

    public void createmesh(Node root, Grain g){


        if(CheckFill(root.rect, g) == 0 || CheckFill(root.rect, g) == 1) {

            int width = root.rect.width / 2;
            int height = root.rect.height / 2;


            if (dg.tolerance < root.rect.width && dg.tolerance < root.rect.height) {

                Node n1 = new Node(new Rectangle(new Point(root.rect.p.x, root.rect.p.y), width, height), null, null, null, null);
                Node n2 = new Node(new Rectangle(new Point(root.rect.p.x + width, root.rect.p.y), width, height), null, null, null, null);
                Node n3 = new Node(new Rectangle(new Point(root.rect.p.x, root.rect.p.y + height), width, height), null, null, null, null);
                Node n4 = new Node(new Rectangle(new Point(root.rect.p.x + width, root.rect.p.y + height), width, height), null, null, null, null);

                root.n1 = n1;
                root.n2 = n2;
                root.n3 = n3;
                root.n4 = n4;

                createmesh(n1, g);
                createmesh(n2, g);
                createmesh(n3, g);
                createmesh(n4, g);

            }
        }

    }

    void addToForGrainMesh(Node root, Grain g){
        int color = 10;
        if(CheckFill(root.rect, g)==1){             //jest docelowy kolor tylko i wyłącznie
            color = 1;
            dg.forGrainMeshes.add(new ForGrainMesh(new Point(root.rect.p.x, root.rect.p.y),root.rect.width,root.rect.height,color));
        }

        if(root.n1==null && root.n2==null && root.n3==null && root.n4==null){
            if(CheckFill(root.rect, g)==0){         //jest mixed, ale nie ma juz dzieci
                color = 0;
                dg.forGrainMeshes.add(new ForGrainMesh(new Point(root.rect.p.x, root.rect.p.y),root.rect.width,root.rect.height,color));
            }
        }

        if(root.n1 != null)
            addToForGrainMesh(root.n1, g);
        if(root.n2 != null)
            addToForGrainMesh(root.n2, g);
        if(root.n3 != null)
            addToForGrainMesh(root.n3, g);
        if(root.n4 != null)
            addToForGrainMesh(root.n4, g);

    }

    public void constructor(int width, int height){
        Rectangle first = new Rectangle(new Point(0,0),width,height);
        dg.rootNode = new Node(first,null,null,null,null);

    }
}
