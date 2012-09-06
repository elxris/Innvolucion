package me.elxris.involucion;

public class Tick implements Runnable{
    private Game game;
    private int delay;
    public Tick(Game g, int frames){
        game = g;
        delay = 1000 / frames;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep((long) delay);
                game.repaint();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
