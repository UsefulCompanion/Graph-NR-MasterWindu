import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends Canvas{
    private Graph graph;

    public Window(int w, int h, String title, Graph graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));

        JFrame frame = new JFrame(title);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
            ///////////////////////////////////////DRAW HERE
            //g.setColor(new Color(255, 100, 50));
            g.setColor(new Color(50, 100, 255));
            g.fillRect(0, 0, getWidth(), getHeight());

            graph.render(g);

            g.setColor(Color.black);
            g.drawString("FPS: " + Main.GFps, 10, getHeight()-40);
            g.drawString("Ticks: " + Main.GTicks, 10, getHeight()-20);
            ///////////////////////////////////////DRAW HERE
        g.dispose();
        bs.show();
    }

    public Graph getGraph(){
        return graph;
    }

}
