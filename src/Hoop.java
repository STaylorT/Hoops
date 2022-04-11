import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Hoop extends JFrame implements ChangeListener {

    // set up bouncing balls
    BouncingBalls bb = new BouncingBalls();
    // set up the moving curves
    AnimateCurve animCurve = new AnimateCurve(bb);

    private int toggleCurveVisibility = 1;
    private int ballVisibility = 1;
    private static int lines = -1;

    JButton hideBalls = new JButton("Hide Balls"),
            hideCurve = new JButton("Hide Curve"),
            hideLines = new JButton("Hide Lines"),
            quit  = new JButton("Quit");

    JSlider speedSlider;



    public Hoop() {
        super("Hoop");

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // control panel
        JPanel controls = new JPanel();

        /* Hide buttons */
        hideBalls.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                bb.toggleVisibility();
                ballVisibility *= -1;
            }
        });
        controls.add(hideBalls);

        hideCurve.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                toggleCurveVisibility *= -1;
            }
        });
        controls.add(hideCurve);

        // hide lines button
        hideLines.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                lines *= -1;
            }
        });
        controls.add(hideLines);

        /* quit button */
        quit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        controls.add(quit);

        // Create the slider to control  the speed
        speedSlider = new JSlider(JSlider.HORIZONTAL,
                1, 10, 3);
        speedSlider.addChangeListener(this);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPreferredSize(new Dimension(450, 80));

        controls.add(speedSlider);
        // add the curve to the Ball JPANel
        bb.add(animCurve);

        this.setLayout(new BorderLayout());
        add(bb, BorderLayout.CENTER );
        add(controls, BorderLayout.PAGE_END);
        this.setSize(615, 735);
        setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        bb.setSpeed((double)speedSlider.getValue());
    }


    public class AnimateCurve extends JComponent implements Runnable{

        /* points of balls  */
        int xs[] = new int[4],
                ys[] = new int[4];

        // previous points of balls
        int[] startx = new int[4],
            starty = new int[4];

        // currently, animating?
        boolean animating = false;

        Ball[] balls;

        Bspline curve = new Bspline();

        public AnimateCurve(BouncingBalls bballs){

            setPreferredSize(new Dimension(700, 700));
            balls = bballs.getBalls();
            for (int i = 0 ; i < 4 ; i++){
                xs[i] = balls[i].getX();
                ys[i] = balls[i].getY();
            }
        }

        public void paint(Graphics g){
            // we always want the curve
            if (toggleCurveVisibility == 1)
                curve.paintCurve(g);

            // unless animating, draw lines btwn pts
            if (ballVisibility == 1 && lines == -1) {
                for (int i = 1; i < 4; i++) {
                    g.drawLine(balls[i-1].getX(), balls[i-1].getY(), balls[i].getX(), balls[i].getY());
                }
            }
        }

        // get new points
        public void getBallCoords(){
            for (int i = 0 ; i < 4 ; i++){
                startx[i] = xs[i];
                starty[i] = ys[i];
                xs[i] = balls[i].getX();
                ys[i] = balls[i].getY();
            }
        }

        public void startAnimation(){
            new Thread(this).start();
        }

        public void run(){
            try{
                animating = true;

                // set end points
                int[] endx;
                int[] endy;
                endx = xs;
                endy = ys;

                double[] stepx = new double[4];
                double[] stepy = new double[4];

                // for each of the balls
                for (int i = 0 ; i < 4 ; i++){
                    // set to computed end point to avoid round error
                    curve.resetCurve();
                    xs[i] = endx[i];
                    ys[i] = endy[i];
                    for (int k = 0 ; k < 4 ; k++){
                        curve.addPoint(xs[k], ys[k]);
                    }

                    animating = false;
                    repaint();
                }

            }catch(Exception e){}
        }

    }

    public static void main(String[] args) {
        Hoop hoop = new Hoop();
        while (1==1){
            hoop.animCurve.getBallCoords();
            hoop.animCurve.startAnimation();


        }
    }
}
