package me.elxris.involucion;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import me.elxris.involucion.art.Gui;
import me.elxris.involucion.art.Puntero;

public class Frame extends JFrame implements MouseListener, MouseMotionListener, KeyListener{
    private Game game;
    private Thread thread;
    private Tick tick;
    private static int frames = 20;
    private Puntero c;
    private Gui gui;
    private static boolean debug = false;
    public Frame(){
        //Parte escencial
        super("Involution LD24");
        game = new Game();
        tick = new Tick(game, frames);
        thread = new Thread(tick);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setLocation(100, 100);
        //Establece un cursor en blanco
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
        //Parte gráfica
        add(game);
        c = new Puntero();
        gui = new Gui();
        game.addGui(gui);
        game.addGui(c);
    }
    public void run(){
        thread.run();
    }
    public static int getFps(){
        return frames;
    }
    public static boolean getDebug(){
        return debug;
    }
    public void setDebug(boolean n){
        debug = n;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        gui.click(e.getX(), e.getY());
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        c.mover(e.getX(), e.getY());
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        c.mover(e.getX(), e.getY());
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == 27){ //ESC
            System.exit(ABORT);
        }
        if(e.getKeyCode() == 49){ //1 
            setDebug(true);
        }
        if(e.getKeyCode() == 50){ //2 
            Tick.pausar(true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == 49){ //1 
            setDebug(false);
        }
        if(e.getKeyCode() == 50){ //2 
            Tick.pausar(false);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
