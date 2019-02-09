package sample;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class GameField extends JPanel  {
    private final int DOT = 80;
    private final int SIZE = 326;
    private final int WEIGHT = 4;
    public static int SCORE = 0;
    public static int BESTRECORD = 0;
    private int DOT_X;
    private int DOT_Y;
    private Image sixteen;
    private Image two;
    private Image four;
    private Image eight;
    private Image thirty_two;
    private Image Sixty_four;
    private Image One_hundred;
    private Image Two_hundred;
    private Image Five_hundred;
    private Image One_thousand;
    private Image Two_thousand;
    private boolean inGame = true;
    Direction direction;
    private int[][] field;




    public GameField(){
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);





    }
    public void initGame(){

        field = new int[WEIGHT][WEIGHT];
        for(int i = 0;i < field.length;i++){
            for(int j = 0;j < field.length;j++){
                field[i][j] = 0;
            }
        }
        for (int i = 0;i < 2;i++) {
            createTwo();
        }







    }

    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        g.setFont(new Font("Score",Font.BOLD,25));
        g.drawString("score: " + SCORE,160,340);
        g.setColor(Color.red);
        g.drawString("best: " + BESTRECORD,0,340);
        if (inGame) {
            for(int i = 0;i < field.length;i++) {
                for (int j = 0; j < field.length; j++) {
                    if(field[i][j] == 2){
                        g.drawImage(two, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 4){
                        g.drawImage(four, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 8){
                        g.drawImage(eight, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 32){
                        g.drawImage(thirty_two, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 64){
                        g.drawImage(Sixty_four, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 128){
                        g.drawImage(One_hundred, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 256){
                        g.drawImage(Two_hundred, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 512){
                        g.drawImage(Five_hundred, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 1024){
                        g.drawImage(One_thousand, j*DOT, i*DOT,80,80,this);
                    }
                    if(field[i][j] == 2048){
                        g.drawImage(Two_thousand, j*DOT, i*DOT,80,80,this);
                        inGame = false;
                    }
                    if(field[i][j] == 16){
                        g.drawImage(sixteen, j*DOT, i*DOT,80,80,this);
                    }


                }
            }


        }
        else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.black);
            // g.setFont(f);
            g.drawString(str,100,SIZE/2);

        }
    }
    public void loadImages(){
        ImageIcon iia = new ImageIcon("16.png");
        sixteen = iia.getImage();
        ImageIcon iia1 = new ImageIcon("2.png");
        two = iia1.getImage();
        ImageIcon iia2 = new ImageIcon("4.png");
        four = iia2.getImage();
        ImageIcon iia3 = new ImageIcon("8.png");
        eight = iia3.getImage();
        ImageIcon iia4 = new ImageIcon("32.png");
        thirty_two = iia4.getImage();
        ImageIcon iia5 = new ImageIcon("64.png");
        Sixty_four = iia5.getImage();
        ImageIcon iia6 = new ImageIcon("128.png");
        One_hundred = iia6.getImage();
        ImageIcon iia7 = new ImageIcon("256.png");
        Two_hundred = iia7.getImage();
        ImageIcon iia8 = new ImageIcon("512.png");
        Five_hundred = iia8.getImage();
        ImageIcon iia9 = new ImageIcon("1024.jpg");
        One_thousand = iia9.getImage();
        ImageIcon iia10 = new ImageIcon("2048.png");
        Two_thousand = iia10.getImage();

    }
    public void setColumn(int i, int[] newColumn) {
        field[i] = newColumn;
    }

    public int[] getColumn(int i) {
        return field[i];
    }


    public void setLine(int i, int[] newLine) {
        for(int j = 0; j< WEIGHT; j++){
            field[j][i] = newLine[j];
        }
    }
    public int[] getLine(int i) {
        int[] ret = new int[WEIGHT];

        for(int j = 0; j< WEIGHT; j++){
            ret[j] = field[j][i];
        }

        return ret;
    }
    public void logic(){
        if(inGame){
            if(move()){
                createTwo();
            }


        }
        else {
            Main.output(BESTRECORD);}

        repaint();
    }
    public boolean move(){
        boolean ret = false;

        switch (direction){
            case RIGHT:
            case LEFT:

                for(int i = 0; i< WEIGHT; i++){

                    /*Запрашиваем очередной столбец*/

                    int[] arg = getColumn(i);
                    if(direction==Direction.RIGHT){
                        int[] tmp = new int[arg.length];
                        for(int e = 0; e < tmp.length; e++){
                            tmp[e] = arg[tmp.length-e-1];
                        }
                        arg = tmp;
                    }



                    /*Пытаемся сдвинуть числа в этом столбце*/
                       ShiftRowResult result = shiftRow (arg);




                    /*Возвращаем линию в исходный порядок*/
                    if(direction==Direction.RIGHT){
                        int[] tmp = new int[result.shiftedRow.length];
                        for(int e = 0; e < tmp.length; e++){
                            tmp[e] = result.shiftedRow[tmp.length-e-1];
                        }
                        result.shiftedRow = tmp;
                    }


                    /*Записываем изменённый столбец*/
                    setColumn(i, result.shiftedRow);
                    ret = ret || result.didAnythingMove;

                }
                isFull();
                break;
            case DOWN:
            case UP:
                for(int i = 0; i< WEIGHT; i++){
                    /*Запрашиваем очередную строку*/
                    int[] arg = getLine(i);

                    /*В зависимости от направления сдвига, меняем или не меняем порядок чисел на противоположный*/
                    if(direction==Direction.DOWN){
                        int[] tmp = new int[arg.length];
                        for(int e = 0; e < tmp.length; e++){
                            tmp[e] = arg[tmp.length-e-1];
                        }
                        arg = tmp;
                    }

                    /*Пытаемся сдвинуть числа в этом столбце*/
                    ShiftRowResult result = shiftRow (arg);


                    /*Возвращаем линию в исходный порядок*/
                    if(direction==Direction.DOWN){
                        int[] tmp = new int[result.shiftedRow.length];
                        for(int e = 0; e < tmp.length; e++){
                            tmp[e] = result.shiftedRow[tmp.length-e-1];
                        }
                        result.shiftedRow= tmp;
                    }

                    /*Записываем изменённую строку*/
                   setLine(i, result.shiftedRow);
                    ret = ret || result.didAnythingMove;



                }
                isFull();
                break;

        }
        return ret;
    }
    private  ShiftRowResult shiftRow (int[] oldRow) {
        ShiftRowResult ret = new ShiftRowResult();


        int[] oldRowWithoutZeroes = new int[oldRow.length];
        {
            int q = 0;

            for (int i = 0; i < oldRow.length; i++) {
                if(oldRow[i] != 0){
                    if(q != i){
                        /*
                         * Это значит, что мы передвинули ячейку
                         * на место какого-то нуля (пустой плитки)
                         */
                        ret.didAnythingMove = true;
                    }
                    oldRowWithoutZeroes[q] = oldRow[i];
                    q++;
                }
            }

        }

        ret.shiftedRow = new int[oldRowWithoutZeroes.length];

        {
            int q = 0;

            {
                int i = 0;


                while (i < oldRowWithoutZeroes.length) {
                    if ((i + 1 < oldRowWithoutZeroes.length) && (oldRowWithoutZeroes[i] == oldRowWithoutZeroes[i + 1])
                            && oldRowWithoutZeroes[i] != 0) {
                        ret.didAnythingMove = true;
                            ret.shiftedRow[q] = oldRowWithoutZeroes[i] * 2;
                        SCORE += ret.shiftedRow[q];
                        if(SCORE > BESTRECORD){
                            BESTRECORD = SCORE;
                        }
                            i++;
                        } else{
                            ret.shiftedRow[q] = oldRowWithoutZeroes[i];
                        }

                        q++;
                        i++;
                    }

                }
            }
            return ret;
        }

    public void createTwo() {

        boolean placed = false;



        while (!placed) {

            DOT_X = new Random().nextInt(WEIGHT);
            DOT_Y = new Random().nextInt(WEIGHT);
            if (field[DOT_X][DOT_Y] == 0) {
                field[DOT_X][DOT_Y] = 2;
                placed = true;
            }


        }

    }
    public void isFull(){
        int a = 0;
        for (int d = 0; d < field.length; d++) {
            for (int j = 0; j < field.length; j++) {
                if (field[d][j] != 0) {
                    a = a + 1;
                }
            }
        }
        if (a == 16) {
            inGame = false;
        }

    }



    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT){
                direction = Direction.LEFT;
                logic();

            }
            if(key == KeyEvent.VK_RIGHT){
                direction = Direction.RIGHT;
                logic();
            }

            if(key == KeyEvent.VK_UP){
                direction = Direction.UP;
                logic();
            }
            if(key == KeyEvent.VK_DOWN){
                direction = Direction.DOWN;
                logic();
            }
        }
    }


}
