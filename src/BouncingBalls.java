
import java.awt.*;
import javax.swing.*;

public class BouncingBalls extends JPanel {
    private Ball[] balls;
    private int delay=10;
    private double speed=6.0;
    private static int panelWidth=600;
    private static int panelHeight=600;
    private int visible = 1;


    public BouncingBalls() {
        // Set up the bouncing balls with random speeds and colors
        balls = new Ball[4];
        for (int i = 0 ; i < balls.length ;i++){
            balls[i] = new Ball(panelWidth,panelHeight,
                (float)(speed*Math.random()),
                (float)(speed*Math.random()),
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
        }


        // Create a thread to update the animation and repaint
        for (int i = 0; i < balls.length ;i++) {
            int finalI = i;
            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        // Ask the ball to move itself and then repaint
                        balls[finalI].moveBall();
                        repaint();
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ex) {
                        }
                    }

                }
            };
            thread.start();
        }


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the balls
        for (int i = 0; i < balls.length ; i++){
            if (visible == 1)
                balls[i].paintBall(g);
        }
    }

    public Ball[] getBalls(){
        return balls;
    }

    public void setSpeed(double new_speed){
        speed = new_speed;
        for (int i = 0 ; i < 4 ; i++){
            balls[i].setSpeed(speed);
        }
    }

    public void toggleVisibility(){
        visible *= -1;
    }

//    public static void main(String[] args) {
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        JFrame frame = new JFrame("Bouncing Ball");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(panelWidth, panelHeight);
//        frame.setContentPane(new BouncingBalls());
//        frame.setVisible(true);
//    }
}
