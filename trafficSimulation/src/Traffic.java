import javax.swing.JFrame;
import java.awt.BorderLayout;


class Traffic{

    JFrame frame = new JFrame("Traffic Simulation!");
    Road road=new Road();

    public Traffic(){
        frame.setSize(1300, 700);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(road, BorderLayout.CENTER);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Vehicle daCar=new Vehicle(100, 100);

        road.add(daCar);
        
        
    }
        
    public static void main(String[] args){
        new Traffic();
    }
}
