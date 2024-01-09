import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static AssemblyProgram assemblyProgram = new AssemblyProgram();
    public static void main(String args[]) throws IllegalStateException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose one of the command to execute : MV, ADD, SHOW, EXIT");
        boolean flag = true;
        assemblyProgram.registers = readFromFile();
        while(flag){
            String input = sc.nextLine();
            String[] word = input.split("[\\s,]+");
            switch (word[0].toUpperCase()){
                case "MV" -> {
                    assemblyProgram.executeProgram(new Instructions(Operations.MV, new String[]{word[1], word[2]}));
                    storeRegisterValues(assemblyProgram.registers);
                }
                case "ADD" -> {
                    assemblyProgram.executeProgram(new Instructions(Operations.ADD, new String[]{word[1], word[2]}));
                    storeRegisterValues(assemblyProgram.registers);
                }
                case "SHOW" -> {
                    assemblyProgram.executeProgram(new Instructions(Operations.SHOW, new String[]{word[1]}));
                    storeRegisterValues(assemblyProgram.registers);
                }
                case "SHOWALL" -> {
                    for (Map.Entry<String, Integer> entry : assemblyProgram.registers.entrySet()){
                        System.out.println(entry.getKey()+":"+entry.getValue());
                    }
                }
                case "IFGT" -> {
                    assemblyProgram.executeProgram(new Instructions(Operations.IFGT,new String[]{word[1], word[2]}));
                    storeRegisterValues(assemblyProgram.registers);
                }
                case "ENDIF"->{
                    assemblyProgram.endIfFunction();
                    storeRegisterValues(assemblyProgram.registers);
                }
                case "EXIT","QUIT","-1" -> {
                    flag = false;
                    System.out.println("This program has been terminated");
                }
                default ->
                        System.out.println("Unexpected value: " + word[0]);

            }
        }
    }
    private static void storeRegisterValues(HashMap<String,Integer> registers){
        try{
            FileWriter writer = new FileWriter("register_values.txt");
            for (Map.Entry<String, Integer> entry : registers.entrySet()){
                writer.append(entry.getKey() + " " + entry.getValue() + "\n");
             }
             writer.close();
         } catch (IOException e) {
             e.printStackTrace();

         }
    }
    private static HashMap<String,Integer> readFromFile() throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("register_values.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                int value = Integer.parseInt(parts[1]);
                if(!map.containsKey(key))
                    map.put(key, value);
            }
        }
        return map;
    }

    
}
