package mesh;

import javax.swing.*;
import java.awt.*;

public class JDrawPanel extends JPanel {

    DataGenerator dg;

    public JDrawPanel(DataGenerator dg){
        this.dg = dg;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(dg.bfimg, 0, 0, this);

        GrainMeshCreator gmc = new GrainMeshCreator(dg);

        for(ForGrainMesh f: dg.forGrainMeshes){     //dla kazdego elementu z listy rysuje kwadrat

            if(f.color == 1)
                g2.setColor(Color.white);
            if(f.color == 0)
                g2.setColor(Color.GREEN);

            g2.drawRect(f.point.x, f.point.y, f.width,f.height);
            g2.drawLine(f.point.x, f.point.y, f.point.x +f.width, f.point.y + f.height);

        }
        repaint();


    }

    @Override
    public void repaint(){
        super.repaint();
    }


}
