import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 06.03.17
 */
public class Word {
    public static void main(String[] args) throws IOException {
        String fileDirectory = "/home/vshevchenko/words.txt";
        Path path = Paths.get(fileDirectory);
        Scanner scanner = new Scanner(path);
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> all = allLines;

        TreeSet<String> concatLine = new TreeSet();
        Map<Integer, List<String>> map = new TreeMap<>();
        Integer count = new Integer(0);

        for (int j=0; j<allLines.size(); j++){
            String line = allLines.get(j);
            for(int i=0; i< all.size(); i++){
                if(allLines.get(i).equals(allLines.get(j))){
                    continue;
                }
                if((line.contains(all.get(i)))){
                    line = line.replaceAll(all.get(i),"");
                    if(line.isEmpty()){
                        Integer key = allLines.get(j).length();
                        String value = allLines.get(j);
                        List<String> valueList;
                        if ((map.get(key)!=null) && (!map.get(key).isEmpty())) {
                            valueList = map.get(key);
                            valueList.add(value);
                            map.put(key, valueList);
                            count++;
                        }else {
                            valueList = new ArrayList<>();
                            valueList.add(value);
                            map.put(key, valueList);
                            count++;
                        }
                        break;
                    }
                }
            }
        }
        System.out.println(map.keySet());
        TreeSet<Integer> set = new TreeSet<>(map.keySet());
        Integer maxKey =  set.last();
        Integer secondMaxKey = set.lower(maxKey);

        System.out.println("The longest word : " + map.get(maxKey).toString());
        System.out.println("2nd longest word : "+ map.get(secondMaxKey).toString());

        System.out.println(count);


    }
}


