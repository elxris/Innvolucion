package me.elxris.involucion.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Puntero implements Art {
    private int posX, posY;
    private int an, al;
    public Puntero(){
        posX = 0;
        posY = 0;
        an = 10;
        al = 10;
    }
    public void onPressed(){
        an = 20;
    }
    public void onReleased(){
        an = 10;
    }
    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);
        Polygon p = new Polygon();
        p.addPoint(posX+(an/2), posY+al);
        p.addPoint(posX-(an/2), posY+al);
        p.addPoint(posX, posY);
        g.fillPolygon(p);
    }
    public void mover(int x, int y){
        posX = x;
        posY = y;
    }
}
