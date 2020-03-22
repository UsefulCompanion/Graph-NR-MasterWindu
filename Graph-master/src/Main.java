public class Main {

    public static Window MasterWindu;

    public static int GFps = 0, GTicks = 0;

    public static void main(String[] args){

        Graph HerrGraf = new Graph(4);
        HerrGraf.insertNode("Node1", 0, 0);
        HerrGraf.insertNode("Node2", 100, 200);
        HerrGraf.insertNode("Node3", 123, 456);
        HerrGraf.insertNode("Node4", 300, 300);
        HerrGraf.insertPath("Node1", "Node3", 111);
        HerrGraf.insertPath("Node1", "Node4", 255);
        HerrGraf.insertPath("Node2", "Node3", 420);
        HerrGraf.insertPath("Node2", "Node4", 123);
        HerrGraf.insertPath("Node3", "Node4", 321);
        System.out.println(HerrGraf.toString());

        MasterWindu = new Window(800, 600, "MatrixReloaded", HerrGraf);

        //Execute commands
        HerrGraf.searchShortestWay("Node1", "Node3");
        HerrGraf.searchShortestWay("Node1", "Node2");
        HerrGraf.searchShortestWay("Node2", "Node1");


        //------

        run();
    }

    public static void render() {
        MasterWindu.render();
    }

    public static void tick() {

    }

    public static void run() {

        MasterWindu.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (true) {//Clean work
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                GFps = frames;
                GTicks = updates;
                frames = 0;
                updates = 0;
            }
        }
    }

}
