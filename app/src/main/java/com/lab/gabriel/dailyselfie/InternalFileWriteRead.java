package com.lab.gabriel.dailyselfie;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by fefochico on 18/11/2015.
 */
public class InternalFileWriteRead {
    Context context;
    String fileName;
    public InternalFileWriteRead(Context c, String filename){
        this.context = c;
        this.fileName=filename;
    }

    public void writeLine(String name){
        if(!context.getFileStreamPath(fileName).exists()){
            try{
                FileOutputStream fos= context.openFileOutput(fileName, Context.MODE_PRIVATE);
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
                pw.println(name);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try{
                FileOutputStream fos= context.openFileOutput(fileName, Context.MODE_APPEND);
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
                pw.println(name);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> readName(){
        ArrayList<String> lines= new ArrayList<>();
        if(context.getFileStreamPath(fileName).exists()){
            try{
                FileInputStream fis = context.openFileInput(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String line = "";
                while(null != (line = br.readLine())){
                    lines.add(line);
                }
                br.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }
}
