package me.elxris.involucion.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

import me.elxris.involucion.Frame;
import me.elxris.involucion.Game;
public class Cuerpo implements Art{
    private Random rndm = new Random();
    private double posX, posY, tarX, tarY, rapidez, angulo, tarAngulo, size;
    private static int limitX = 500, limitY = 500;
    private int lados, especie, mutar, tipo, vida, hambre, respira, resistencia;
    private Color color;
    public static final Dibujos dibujos = new Dibujos(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
    
    public Cuerpo(){
        color = new Color(rndm.nextInt(156)+100, rndm.nextInt(156)+100, rndm.nextInt(156)+100);
        setRapidez(2);
        setSize(10);
        setLados(3);
        setEspecie(1);
        setMutar(2);
        setVida(10000);
        setHambre(100);
        setRespira(200);
        setResistencia(400);
        setAngulo(rndm.nextDouble()*2);
    }
    
    public Cuerpo(int x, int y){
        this();
        setPosX((double)x);
        setPosY((double)y);
        setTarX(getPosX());
        setTarY(getPosY());
    }
    
    public void estado(Graphics g){
        if(getTipo() >= 10){
            //Hambre
            Polygon p = new Polygon();
            p.addPoint((int)(getPosX()-(getHambre()+getResistencia())/100), (int)(getPosY()-getSize()));
            p.addPoint((int)(getPosX()+(getHambre()+getResistencia())/100), (int)(getPosY()-getSize()));
            p.addPoint((int)(getPosX()+(getHambre()+getResistencia())/100), (int)(getPosY()-getSize()+2));
            p.addPoint((int)(getPosX()-(getHambre()+getResistencia())/100), (int)(getPosY()-getSize()+2));
            g.setColor(Color.MAGENTA);
            g.fillPolygon(p);
            //Oxigeno
            p = new Polygon();
            p.addPoint((int)(getPosX()-(getRespira()+getResistencia())/100), (int)(getPosY()-getSize()-3));
            p.addPoint((int)(getPosX()+(getRespira()+getResistencia())/100), (int)(getPosY()-getSize()-3));
            p.addPoint((int)(getPosX()+(getRespira()+getResistencia())/100), (int)(getPosY()-getSize()-1));
            p.addPoint((int)(getPosX()-(getRespira()+getResistencia())/100), (int)(getPosY()-getSize()-1));
            g.setColor(Color.CYAN);
            g.fillPolygon(p);
            //Vida
            p = new Polygon();
            p.addPoint((int)(getPosX()-(getVida())/1000), (int)(getPosY()-getSize()-7));
            p.addPoint((int)(getPosX()+(getVida())/1000), (int)(getPosY()-getSize()-7));
            p.addPoint((int)(getPosX()+(getVida())/1000), (int)(getPosY()-getSize()-5));
            p.addPoint((int)(getPosX()-(getVida())/1000), (int)(getPosY()-getSize()-5));
            g.setColor(Color.ORANGE);
            g.fillPolygon(p);
        }
        if(Frame.getDebug()){
            g.setColor(Color.WHITE);
            g.drawLine((int)getPosX(), (int)getPosY(), (int)getTarX(), (int)getTarY());
            g.drawString("Time: "+(getVida()), (int)getTarX(), (int)getTarY());
        }
    }

    public Polygon forma(){
        Polygon p = new Polygon();
        double grados = 2/(double)getLados();
        for(double i = 0; i < 2; i += grados){
            p.addPoint((int)(Math.cos((i+getAngulo())*Math.PI)*getSize()-Math.sin((i+getAngulo())*Math.PI)*getSize()+getPosX()),
                    (int)(Math.sin((i+getAngulo())*Math.PI)*getSize()+Math.cos((i+getAngulo())*Math.PI)*getSize()+getPosY()));
        }
        return p;
    }
    
    public void dibujar(Graphics g) {
        if(!Game.getPausa()){
            if(vivir()){
                return;
            }
            if(getTipo()>=10){
                if(getRespira()<0){
                    getComida(getTipoRespira(getTipo()));
                }
                if(getHambre()<0){
                    getComida(getTipoAlimento(getTipo()));
                }
                //Funciones de evolucion
                if(rndm.nextInt(500+(5*Game.art.size())) == 0){
                    procrear();
                    desprender();
                }
            }else{
                addAngulo(getRapidez()/10);
            }
            mover();
        }
        //Dibujar
        if(getTipo() < 10){
            dibujos.dibujo(getTipo()+1, g, true, getPosX(), getPosY(), getSize(), getAngulo());
        }else{
            g.setColor(getColor());
            g.fillPolygon(forma());
        }
        estado(g);
    }
    public void mover(){
        double dX, dY, hyp, ang, diff, sX, sY, pX, pY;
        dX = getTarX() - getPosX();
        dY = getTarY() - getPosY();
        hyp = Math.hypot(dX, dY);
        if(hyp < getSize()){
            addTarX(rndm.nextInt(limitX));
            addTarY(rndm.nextInt(limitY));
        }
        dX = getTarX() - getPosX();
        dY = getTarY() - getPosY();
        if(dX < 0){
            sX = -1;
        }else{
            sX = 1;
        }
        if(dY < 0){
            sY = -1;
        }else{
            sY = 1;
        }
        ang = Math.atan(Math.abs(dY/dX));
        setTarAngulo(ang);
        diff = (getTarAngulo()-getAngulo());
        addAngulo(diff);
        pX = Math.cos(getAngulo())*getRapidez()*sX;
        if(dX > limitX/2){
            pX = -pX;
        }
        addPosX(pX);
        pY = Math.sin(getAngulo())*getRapidez()*sY;
        if(dY > limitY/2){
            pY = -pY;
        }
        addPosY(pY);
    }
    public double getPosX(){
        return posX;
    }
    
    public void setPosX(double x){
        x %= limitX;
        if( x < -getSize() ){
            x = getSize() + limitX + x;
        }
        posX = x;
    }
    
    public void addPosX(double x){
        setPosX(getPosX()+x);
    }
    
    public double getPosY(){
        return posY;
    }
    
    public void setPosY(double y){
        y %= limitY;
        if( y < -getSize() ){
            y = getSize() + limitY + y;
        }
        posY = y;
    }
    
    public void addPosY(double y){
        setPosY(getPosY()+y);
    }
    
    public double getSize(){
        return size;
    }
    
    public void setSize(double d){
        if(d < 0){
            size = 0;
        }else{
            size = d;
        }
    }
    
    public void addSize(int n){
        setSize(getSize()+n);
    }
    
    public double getTarX(){
        return tarX;
    }
    
    public void setTarX(double x){
        x %= limitX;
        if(x < 0){
            x = limitX + x;
        }
        tarX = x;
    }
    
    public void addTarX(double x){
        setTarX(getTarX()+x);
    }
    public double getTarY(){
        return tarY;
    }
    
    public void setTarY(double y){
        y %= limitY;
        if(y < 0){
            y = limitY + y;
        }
        tarY = y;
    }
    
    public void addTarY(double y){
        setTarY(getTarY()+y);
    }
    
    public double getAngulo(){
        return angulo;
    }
    
    public void setAngulo(double n){
        angulo = n%2;
    }
    
    public void addAngulo(double n){
        double v = .3491/Frame.getFps()*getRapidez();
        if(n > v){
            n = v;
        }else if(n < -v){
            n = -v;
        }
        setAngulo(getAngulo()+n);
    }
    
    public double getTarAngulo(){
        return tarAngulo;
    }
    
    public void setTarAngulo(double n){
        tarAngulo = n%2;
    }
    
    public void addTarAngulo(double n){
        setTarAngulo(getTarAngulo()+n);
    }
    
    public int getEspecie(){
        return especie;
    }
    
    public void setEspecie(int e){
        especie = e;
    }
    
    public void addEspecie(int n){
        setEspecie(getEspecie()+n);
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color c){
        color = c;
    }
    
    public int[] getArrayColor(){
        int[] result = new int[3];
        result[0] = color.getRed();
        result[1] = color.getGreen();
        result[2] = color.getBlue();
        return result;
    }
    
    public void setArrayColor(int[] input){
        color = new Color(input[0], input[1], input[2]);
    }
    
    public void addColor(int r, int g, int b){
        int[] col = getArrayColor();
        col[0] += r;
        col[1] += g;
        col[2] += b;
        setArrayColor(col);
    }
    
    public int getLados(){
        return lados;
    }
    
    public void setLados(int l){
        lados = l;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public void setTipo(int t){
        switch(t){
        case 0:
        case 1:
        case 2:
        case 3:
            setSize(.5);
            setRapidez(.1);
            setVida(100000);
            break;
        default:
            setLados(t-7);
            break;
        }
        tipo = t;
    }
    
    public void getComida(int type){
        double min, min2, minX, minY;
        Cuerpo comida = null;
        min = 12345;
        minX = 0;
        minY = 0;
        for(Cuerpo c : Game.art){
            if((c.getTipo() == type)&& c.getVida()>1){
                min2 = min;
                min = Math.min(min, Math.hypot(c.getPosX()-getPosX(), c.getPosY()-getPosY()));
                if(min2 != min){
                    minX = c.getPosX();
                    minY = c.getPosY();
                    comida = c;
                }
            }
        }
        if(min != 12345){
            setTarX(minX);
            setTarY(minY);
            if(min < getSize()*2 && comida.getVida()>1){
                if(type < 2){
                    addHambre(1200);
                }else{
                    addRespira(1200);
                }
                addVida(500);
                addSize(1);
                comida.muerte();
                setTarX(getPosX());
                setTarY(getPosY());
                Game.score += getEspecie();
            }
        }
    }
    
    public int getHambre(){
        return hambre;
    }
    
    public void setHambre(int v){
        hambre = v;
    }
    
    public void addHambre(int v){
        setHambre(getHambre()+v);
    }
    
    public int getVida(){
        return vida;
    }
    
    public void setVida(int n){
        vida = n;
    }
    
    public void addVida(int n){
        setVida(getVida()+n);
    }
    
    public int getMutar(){
        return mutar;
    }
    
    public void setMutar(int n){
        mutar = n;
    }
    
    public void addMutar(int n){
        setMutar(getMutar()+n);
    }
    
    public void muerte(){
        setVida(0);
    }
    
    public boolean vivir(){
        addVida(-1);
        addHambre(-1);
        addRespira(-1);
        return !isVivo();
    }
    
    public boolean isVivo(){
        if(getVida() <= 0){
            if(getSize() > 0){
                addSize(-1);
            }else{
                return false;
            }
        }else if(getTipo() >= 10){
            if(getHambre() < -getResistencia()){
                muerte();
            }
            if(getRespira() < -getResistencia()){
                muerte();
            }
        }
        return true;
    }
    
    public void procrear(){
        //Reunir capacidades de ambiente como del cuerpo
        if(getSize() > 12){
            Cuerpo c = new Cuerpo();
            c.setTipo(getTipo());
            c.setPosX(getPosX());
            c.setTarX(getPosX());
            c.setPosY(getPosY());
            c.setTarY(getPosY());
            c.setSize(getSize()/2);
            c.setColor(getColor());
            c.addColor(rndm.nextInt(getMutar()*2)-getMutar(), rndm.nextInt(getMutar()*2)-getMutar(),
                    rndm.nextInt(getMutar()*2)-getMutar());
            c.setEspecie(getEspecie()+rndm.nextInt(getMutar()));
            c.setLados(getLados());
            c.setResistencia(getResistencia()+(rndm.nextInt(getMutar())));
            c.setRapidez(getRapidez()+rndm.nextInt(getMutar()));
            c.setMutar(getMutar()+(rndm.nextInt(getMutar())));
            Game.addCuerpo(c);
            setSize(getSize()-5);
            Game.score += c.getEspecie();
        }
    }
    
    public void desprender(){
        Cuerpo c = new Cuerpo();
        int tipo = 1-getTipoAlimento(getTipo());
        if(rndm.nextBoolean()){
            tipo = 5-getTipoRespira(getTipo());
        }
        c.setTipo(tipo);
        c.setPosX(getPosX());
        c.setTarX(getPosX());
        c.setPosY(getPosY());
        c.setTarY(getPosY());
        Game.addCuerpo(c);
    }

    public int getRespira() {
        return respira;
    }

    public void setRespira(int respira) {
        this.respira = respira;
    }
    
    public void addRespira(int n){
        setRespira(getRespira()+n);
    }
    
    public int getTipoAlimento(int n){
        return n%2;
    }
    
    public int getTipoRespira(int n){
        return ((n%3)%2)+2;
    }
    
    public double getRapidez(){
        return rapidez;
    }
    
    public void setRapidez(double n){
        rapidez = n;
    }
    
    public void addRapidez(double n){
        setRapidez(getRapidez()+n);
    }
    
    public int getResistencia(){
        return resistencia;
    }
    
    public void setResistencia(int n){
        resistencia = n;
    }
    
    public void addResistencia(int n){
        setResistencia(getResistencia()+n);
    }
}
