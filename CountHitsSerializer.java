
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class countHitsSerializer{
    
    public static void serialize(Object obj, String fileName){
        //used to compactly serialize any object (each different object type will need a specialized deserialzing method
        try{
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.close();
            file.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static Map<Integer,Map<Integer,Integer>> deserializeSymbolCounts(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<Integer,Map<Integer,Integer>> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<Integer,Map<Integer,Integer>>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
    
    public static Map<String,Integer> deserializeSymbolConversions(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<String,Integer> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<String,Integer>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
    
    public static Map<Integer,Map<Integer,Set<Integer>>> deserializePayTable(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<Integer,Map<Integer,Set<Integer>>> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<Integer,Map<Integer,Set<Integer>>>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
    
    public static Map<Integer,Set<Integer>> deserializeWildSubs(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<Integer,Set<Integer>> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<Integer,Set<Integer>>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
    
    
    public static Map<Integer,Map<Integer,Integer>> deserializeValidLines(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<Integer,Map<Integer,Integer>> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<Integer,Map<Integer,Integer>>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
    
    public static Map<Integer,Integer> deserializeLineMultiplicity(String fileName){
        //specifically deserializes maps of the type below
        //used specifically to store generated Lines
        Map<Integer,Integer> inputMap = new HashMap<>();
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            inputMap = (Map<Integer,Integer>) in.readObject();
            file.close();
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }     
        return inputMap;
    }
}
