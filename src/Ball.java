
import java.awt.*;
import java.lang.Math;
public class Ball {
    // Box height and width
    private int width;
    private int height;

    // Ball Size
    private float radius = 10;
    private float diameter = radius * 2;

    // Center of ball
    private float X = radius + 20;
    private float Y = radius + 20;

    // Color of ball
    private Color ballColor;

    // Direction
    private float dx = 3;
    private float dy = 3;

    // Constructor sets default speed (dx, dy) and color (blue)
    // Still need the width/height
    public Ball(int w, int h) {
        this.width = w;
        this.height = h;
        this.ballColor = Color.BLUE;
    }

    // Constructor to set custom speed and color
    public Ball(int w, int h, float indx, float indy, Color c) {
        this.width = w;
        this.height = h;
        this.dx = indx;
        this.dy = indy;
        this.ballColor = c;
    }

    // Make a step and change the ball parameters
    public void moveBall () {

        // Update the X and Y coordinates
        X = X + dx;
        Y = Y + dy;

        // Check boundaries and if necessary update the
        // x component of the direction vector, dx
        if ((X - radius) < 0) {
            dx = -dx;
            X = radius;
            randomizeColor();
        }
        else if ((X + radius) > width) {
            dx = -dx;
            X = width - radius;
            randomizeColor();
        }

        // Check boundaries and if necessary update the
        // y component of the direction vector, dy
        if ((Y - radius) < 0) {
            dy = -dy;
            Y = radius;
            randomizeColor();
        }
        else if ((Y + radius) > height) {
            dy = -dy;
            Y = height - radius;
            randomizeColor();
        }
    }

    // Accessors
    // Might also want mutators here (i.e., setX())
    // so that the starting point of a ball can be randomized
    public int getX () {
        return (int)X;
    }

    public int getY () {
        return (int)Y;
    }

    public Color getColor () {
        return ballColor;
    }

    public void setColor (Color c) {
        this.ballColor = c;
    }

    public void randomizeColor(){
        this.ballColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    public void setSpeed(double new_speed){
        dx = (float) ((float)(dx)/Math.abs(dx) * ((new_speed)));
        dy = (float) ((float)(dy)/Math.abs(dy) * ((new_speed)));
    }

    // Draw into a provided graphics context
    public void paintBall (Graphics g) {
        g.setColor(ballColor);
        g.fillOval((int)(X-radius), (int)(Y-radius), (int)diameter, (int)diameter);
    }
}
