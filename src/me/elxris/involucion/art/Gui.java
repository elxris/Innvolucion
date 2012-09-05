package me.elxris.involucion.art;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

import me.elxris.involucion.Game;

public class Gui implements Art {
    private final int z = 500;
    private final int w = 200;
    private final int b = z/20; //25
    private final int c = z/50; //10
    private final int d = c/2;//5
    private final int[] x = {z, z+w, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b, z+b, z+3*b};
    private final int[] y = {0, z, b-c, 3*b-c, 3*b, 5*b, 5*b+c, 7*b+c, 7*b+2*c, 9*b+2*c, 9*b+3*c, 11*b+3*c, 11*b+4*c, 13*b+4*c, 13*b+5*c, 15*b+5*c, 15*b+6*c, 17*b+6*c};
    private Dibujos dibujos;
    private int seleccion;
    private static int variables[];
    private Random rndm = new Random(529269819);
    public Gui(){
        dibujos = new Dibujos(x, y);
        setSeleccion(1);
        reSet();
    }
    public void dibujar(Graphics g) {
        for(int i = 0; i < x.length/2; i++){
            g.setColor(colores(i));
            g.fillPolygon(forma(i));
            dibujos.dibujo(i, g, false, 0, 0, 1, 0);
            g.setColor(Color.RED);
            g.fillPolygon(dibujarSeleccion());
            drawTexto(g);
            if(!Game.getPausa()){
                loteria();
            }
        }
    }
    public Polygon dibujarSeleccion(){
        Polygon p = new Polygon();
        int i = getSeleccion()*2;
        p.addPoint(x[i], y[i]+2*b-d);
        p.addPoint(x[i+1], y[i]+2*b-d);
        p.addPoint(x[i+1], y[i+1]);
        p.addPoint(x[i], y[i+1]);
        return p;
    }
    public int getSeleccion(){
        return seleccion;
    }
    public void setSeleccion(int i){
        seleccion = i;
    }
    public Polygon forma(int i){
        Polygon p = new Polygon();
        i *= 2;
        p.addPoint(x[i], y[i]);
        p.addPoint(x[i+1], y[i]);
        p.addPoint(x[i+1], y[i+1]);
        p.addPoint(x[i], y[i+1]);
        return p;
    }
    public Color colores(int i){
        Color c;
        switch (i) {
        case 0:
            c = new Color(153,153,153); //Gris
            break;
        case 1:
        case 2:
        case 3:
        case 4:
            c = new Color(195,255,104); //Verde
            break;
        case 5:
        case 6:
        case 7:
        case 8:
            c = new Color(255,152,94);//Azul
            break;
        default:
            c = new Color(0,0,0); //Negro
            break;
        }
        return c;
    }
    public void drawTexto(Graphics g){
        Font f = new Font("Arial", Font.BOLD, 18);
        g.setFont(f);
        int posX = z+3*b+c;
        g.setColor(Color.GREEN);
        int i = 1;
        g.drawString(String.format("Food: %d", variables[0]), posX, y[2*i++]+b+c);
        g.drawString(String.format("Waste: %d", variables[1]), posX, y[2*i++]+b+c);
        g.drawString(String.format("Oxygen: %d", variables[2]), posX, y[2*i++]+b+c);
        g.drawString(String.format("Dioxide: %d", variables[3]), posX, y[2*i++]+b+c);
        g.setColor(Color.ORANGE);
        g.drawString(String.format("F-D: %d", variables[4]), posX, y[2*i++]+b+c);
        g.drawString(String.format("W-O: %d", variables[5]), posX, y[2*i++]+b+c);
        g.drawString(String.format("F-O: %d", variables[6]), posX, y[2*i++]+b+c);
        g.drawString(String.format("W-D: %d", variables[7]), posX, y[2*i++]+b+c);
        g.setColor(Color.WHITE);
        g.drawString(String.format("Score: %d", Game.score), b, b);
        if(Game.getPausa()){
            g.drawString("PAUSED (PRESS SPACEBAR)", b, z-(2*b));
        }
        f = new Font("Arial", Font.PLAIN, 12);
        g.setFont(f);
        if(Game.getPausa()){
            g.drawString("Click on the squares to select any item (food or species).", 2*b, 2*b);
            g.drawString("If species are named F-D, means he needs Food and Dioxide.", 2*b, 3*b);
            g.drawString("The bar indicates: life>orange, oxygen>cyan and hunger>pink.", 2*b, 4*b);
            g.drawString("Basically when they are hungry they look for Food/Waste", 2*b, 5*b);
            g.drawString("or if they are choking they look for Oxygen/Dioxide.", 2*b, 6*b);
            g.drawString("ESC - Close, R - Restart, SpaceBar - Pause", 2*b, 7*b);
            g.drawString("Num 1 - Debug Stuff and Paths", 2*b, 8*b);
        }
        g.drawString("Involucion By @elxirs. LudumDare #24 v0.3", c, z-d);
    }
    public void click(int x, int y){
        Boolean gui = false;
        for(int i = 0; i < this.x.length/2; i++){
            if(forma(i).intersects(x, y, 1, 1)){
                gui = true;
                switch(i){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    setSeleccion(i);
                    break;
                default:
                    break;
                }
            }
        }
        if(!gui){
            Cuerpo cuerpo = new Cuerpo(x, y);
            if(getSeleccion()<5){
                cuerpo.setTipo(getSeleccion()-1);
            }else{
                cuerpo.setTipo(getSeleccion()+5);
            }
            if(variables[getSeleccion()-1] > 0){
                variables[getSeleccion()-1]--;
                Game.addCuerpo(cuerpo);
            }
        }
    }
    public void loteria(){
        if(rndm.nextInt(100000/(Game.score+1))==0){
            variables[rndm.nextInt(4)]++;
        }
    }
    public static void reSet(){
        int[] var = {5, 5, 5, 5, 5, 5, 5, 5};
        variables = var;
    }
}
