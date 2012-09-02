package me.elxris.involucion;

public class Tick implements Runnable{
    private Game game;
    private int delay, fps, fpsCount;
    private long last = 0;
    public Tick(Game g, int frames){
        game = g;
        delay = 1000 / frames;
    }
    
    @Override
    public void run() {
        while(true){
            contarFPS();
            try {
                Thread.sleep((long) delay);
                game.repaint();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void contarFPS(){
        fps++;
        long time = System.currentTimeMillis();
        if((time-last)>1000){
            System.out.print(fps+" ");
            if(fpsCount++ > 20){
                System.out.println();
                fpsCount = 0;
            }
            fps = 0;
            last = time;
        }
    }
}
