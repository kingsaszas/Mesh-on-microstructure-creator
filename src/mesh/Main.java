package mesh;

        import javax.imageio.ImageIO;
        import javax.swing.*;
        import javax.swing.border.TitledBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.image.BufferedImage;
        import java.io.File;
        import java.io.IOException;

public class Main extends JFrame {

    private JDrawPanel jDrawPanel;
    private JPanel mainpanel, buttonpanel;
    private JButton saveMeshButton, createMesh, meshonevery;
    private JTextField idnumberField;
    private JLabel info;


    DataGenerator dg;

    public Main(String title){
        super(title);
        this.dg = new DataGenerator();


        idnumberField = new JTextField("0");
        idnumberField.setBorder(new TitledBorder("grain's ID"));

        saveMeshButton = new JButton("save mesh");
        createMesh = new JButton("create mesh");
        meshonevery = new JButton(("mesh on all grains"));
        info = new JLabel("<html>choose ID<br>from 1 to 19</html>", SwingConstants.CENTER );

        buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(8,1));
        buttonpanel.add(info);
        buttonpanel.add(idnumberField);
        buttonpanel.add(createMesh);
        buttonpanel.add(meshonevery);
        buttonpanel.add(saveMeshButton);

        //====================================================================image

        try{
            BufferedImage img = ImageIO.read(new File("1_Ebsd.bmp"));
            dg.bfimg = img;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        //==============================================================Grains' creator
        dg.width = dg.bfimg.getWidth();
        dg.height = dg.bfimg.getHeight();

        GrainMeshCreator gmc = new GrainMeshCreator(dg);
        gmc.constructor(dg.width,dg.height);         //do siatki

        gmc.IDSearch();         //numerujemy ziarna

        //====================================================================buttons

        createMesh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dg.forGrainMeshes.clear();

                int id =  Integer.parseInt(idnumberField.getText());
                dg.IDselected = id;

                for(Grain g: dg.grains){
                    if(g.ID == id){
                        gmc.createmesh(dg.rootNode,g);
                        gmc.addToForGrainMesh(dg.rootNode,g);
                        break;
                    }
                }

                jDrawPanel.repaint();

            }
        });

        meshonevery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dg.forGrainMeshes.clear();
                for (Grain g : dg.grains) {
                    gmc.createmesh(dg.rootNode, g);
                    gmc.addToForGrainMesh(dg.rootNode, g);

                    jDrawPanel.repaint();
                }
            }

        });


        saveMeshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    Container c = jDrawPanel; //getContentPane();
                    BufferedImage bf = new BufferedImage(c.getWidth(),c.getHeight(),BufferedImage.TYPE_INT_RGB);
                    c.paint(bf.getGraphics());
                    ImageIO.write(bf, "png", new File("saveimage.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        jDrawPanel = new JDrawPanel(dg);

        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        mainpanel.add(BorderLayout.EAST,buttonpanel);
        mainpanel.add(BorderLayout.CENTER,jDrawPanel);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300,100);
        this.setContentPane(mainpanel);
        this.setSize(new Dimension(510,400));
        this.setResizable(false);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        Main main = new Main("mesh on grains");
        main.jDrawPanel.repaint();

    }
}
