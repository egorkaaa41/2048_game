package sample;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class Main extends JFrame  {
    public Main(){
        setTitle("2048");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(326,375);
       setLocation(550,325);
       add(new GameField());
       setResizable(false);
       setVisible(true);
    }

    public static void main(String[] args) throws NumberFormatException{
        GameField.BESTRECORD = Integer.parseInt(input().trim());
        Main main = new Main();


    }
    public static String input() {

        try(FileReader reader = new FileReader("C:/Users/Егор/IdeaProjects/2048/record.txt"))
        {
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){

                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }

            }
            String str = String.copyValueOf(buf);
            return str;
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


        return null;

}
public static void output(int bestrecord){
        delete();
        try {
            String output = String.valueOf(bestrecord);
    OutputStream out = new FileOutputStream("C:/Users/Егор/IdeaProjects/2048/record.txt");
    out.write(output.getBytes(),0,output.length());

    } catch (Exception e) {
            e.printStackTrace();
        }



}
public static void delete(){
    try {
        FileWriter fstream1 = new FileWriter("C:/Users/Егор/IdeaProjects/2048/record.txt");// конструктор с одним параметром - для перезаписи
        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
        out1.write(""); // очищаем, перезаписав поверх пустую строку
        out1.close(); // закрываем
    } catch (Exception e)
    {System.err.println("Error in file cleaning: " + e.getMessage());}
}
}



