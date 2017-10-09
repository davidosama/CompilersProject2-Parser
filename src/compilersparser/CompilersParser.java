package compilersparser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class CompilersParser {
    public static String [] text;
    public static int i=0;
    public static ArrayList <String> output = new ArrayList();
    
    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        String s = scan.nextLine();
        String s = null;
        try{
            s = new String(Files.readAllBytes(Paths.get("scanner_output.txt")));
        }
        catch(IOException ex){
            
        }
        text = s.split(" ");
        program();
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("parser_output.txt"));
            for(int j=0; j<output.size(); j++){
                bw.write(output.get(j));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException ex){
            
        }
    }
    
    public static void match(String str){
        if(text[i].equals(str)){
            if(i < text.length-1)
                i++;
        }
        else{
//            if(i < text.length-1)
//                i++;
            
            System.out.println("ERROR!");
            output.add("ERROR!");
            System.exit(0);
        }
    }
    
    public static void program (){
        stmt_seq();
        System.out.println("program found");
        output.add("program found");
    }
    
    public static void stmt_seq (){
        stmt();
        while (text[i].equals(";")){
            match(";");
            stmt();
        }
//        System.out.println("stmt_seq found");
//        output.add("stmt_seq found");
    }
    
    public static void stmt (){
        if(text[i].equals("if"))
            if_stmt();
        else if(text[i].equals("repeat"))
            repeat_stmt();
        else if(text[i].equals("identifier"))
            assign_stmt();
        else if(text[i].equals("read"))
            read_stmt();
        else if(text[i].equals("write"))
            write_stmt();
        else
            match("ERR");
//        System.out.println("stmt found");
//        output.add("stmt found");
    }
    
    public static void if_stmt (){
        match("if");
        exp();
        match("then");
        stmt_seq();
        if(text[i].equals("else")){
            match("else");
            stmt_seq();
        }
        match("end");
        System.out.println("if_stmt found");
        output.add("if_stmt found");
    }
    
    public static void repeat_stmt(){
        match("repeat");
        stmt_seq();
        match("until");
        exp();
        System.out.println("repeat_stmt found");
        output.add("repeat_stmt found");
    }
    
    public static void assign_stmt(){
        match("identifier");
        match(":=");
        exp();
        System.out.println("assign_stmt found");
        output.add("assign_stmt found");
    }
    
    public static void read_stmt(){
        match("read");
        match("identifier");
        System.out.println("read_stmt found");
        output.add("read_stmt found");
    }
    
    public static void write_stmt(){
        match("write");
        exp();
        System.out.println("write_stmt found");
        output.add("write_stmt found");
    }
    
    public static void exp(){
        simple_exp();
        if(text[i].equals("<") || text[i].equals("=")){
            comp_op();
            simple_exp();
        }
//        System.out.println("exp found");
//        output.add("exp found");
    }
    
    public static void comp_op(){
        if(text[i].equals("<"))
            match("<");
        else if(text[i].equals("="))
            match("=");
//        System.out.println("comp_op found");
//        output.add("comp_op found");
    }
    
    public static void simple_exp(){
        term();
        while(text[i].equals("+") || text[i].equals("-")){
            addop();
            term();
        }
//        System.out.println("simple_exp found");
//        output.add("simple_exp found");
    }
    
    public static void addop(){
        if(text[i].equals("+"))
            match("+");
        else if(text[i].equals("-"))
            match("-");
//        System.out.println("addop found");
//        output.add("addop found");
    }
    
    public static void term(){
        factor();
        while(text[i].equals("*") || text[i].equals("/")){
            mulop();
            factor();
        }
//        System.out.println("term found");
//        output.add("term found");
    }
    
    public static void mulop(){
        if(text[i].equals("*"))
            match("*");
        else if(text[i].equals("/"))
            match("/");
//        System.out.println("mulop found");
//        output.add("mulop found");
    }
    
    public static void factor(){
        switch (text[i]) {
            case "(":
                match("(");
                exp();
                match(")");
                break;
            case "number":
                match("number");
                break;
            case "identifier":
                match("identifier");
                break;
            default:
                break;
        }
//        System.out.println("factor found");
//        output.add("factor found");
    }
}
