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
        Game.pausar(true);
    }
    public void reSet(){
        Game.reSet();
        Gui.reSet();
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
        c.onPressed();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        gui.onClick(e.getX(), e.getY(), e.getClickCount());
        c.onReleased();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        c.mover(e.getX(), e.getY());
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        c.mover(e.getX(), e.getY());
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 49){ //1
            setDebug(true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 27){ //ESC
            System.exit(ABORT);
        }
        if(e.getKeyCode() == 49){ //1 
            setDebug(false);
        }
        if(e.getKeyCode() == 32){ //Barra espaciadora 
            Game.pausar();
        }
        if(e.getKeyCode() == 82){ //R 
            reSet();
        }
        if((e.getKeyCode() == 87)||(e.getKeyCode() == 38)){ //W o Arriba
            gui.addSeleccion(-1);
        }else if((e.getKeyCode() == 83)||(e.getKeyCode() == 40)){ //S o Abajo
            gui.addSeleccion(1);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
