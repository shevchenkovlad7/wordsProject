package service;
import object.Obj;
import java.io.*;
import java.util.*;

import static config.AppConfig.getFileDirectory;

/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 06.03.17
 */
public class WordService {
    public static void main(String[] args) {
        List<String> allLines = new ArrayList<>();
        Map<String, List<String>> mapByLetter = new LinkedHashMap<>();
        try {
            loadWords(allLines, mapByLetter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("words is loaded");

        Map<Integer, List<Obj>> map = new LinkedHashMap<>();
        int firstLength = 0;
        int secondLength = 0;

        long start = System.nanoTime();
        int countAllConcatWords = 0;

        for (String line : allLines) {
            Set<String> concatWords = new LinkedHashSet<>();
            int lineLength = line.length();

            int count = 0;
            boolean flag = true;
            while (flag && count != lineLength) {
                List<String> listByLetter = mapByLetter.get(String.valueOf(line.charAt(count)));
                if (lineLength - count == 1) {
                    break;
                }
                for (int j = listByLetter.size() -1; j >= 0; j--) {
                    String word = listByLetter.get(j);

                    if (line.startsWith(word, count) && !line.equals(word) && !word.concat("s").equals(line)) {
                        List<Obj> objs = map.get(word.length());
                        int index = -1;
                        if (objs != null) {
                            index = objs.indexOf(new Obj(word));
                        }
                        if (index < 0) {
                            concatWords.add(word);
                        } else {
                            concatWords.addAll(objs.get(index).getConcatWords());
                        }
                        count += word.length();
                        break;
                    }
                    if (j == 0) {
                        flag = false;
                    }

                }
            }
            if (count == lineLength) {
                if (firstLength < lineLength) {
                    secondLength = firstLength;
                    firstLength = lineLength;
                }
                List<Obj> set = map.get(lineLength);
                if (set == null) {
                    set = new LinkedList<>();
                    map.put(lineLength, set);
                }
                Obj obj = new Obj(line, concatWords);
                set.add(obj);
                countAllConcatWords++;
                System.out.println("add —> " + obj);
            }
        }

        long end = System.nanoTime();
        System.out.println("Time = " + (end - start));

        List<Obj> first = map.get(firstLength);
        System.out.println("FirstLength = " + firstLength + " —> " + Arrays.toString(first.toArray(new Obj[]{})));
        List<Obj> second = map.get(secondLength);
        System.out.println("SecondLength = " + secondLength + " —> " + Arrays.toString(second.toArray(new Obj[]{})));
        System.out.println();
        System.out.println("Size all concat words = " + countAllConcatWords);
    }

    private static void loadWords(List<String> allLines, Map<String, List<String>> map) throws FileNotFoundException {

        File file = new File(getFileDirectory());
        Scanner scanner = new Scanner(file);
        char firstLetter = 'a';
        List<String> listByLetter = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (word.equals("ya")){
                System.out.println();
            }
            if (!word.isEmpty()) {
                char firstChar = word.charAt(0);
                if (firstLetter != firstChar) {
                    map.put(String.valueOf(firstLetter), listByLetter);
                    firstLetter = firstChar;
                    listByLetter = new ArrayList<>();
                }
                listByLetter.add(word);

                allLines.add(word);
            }
        }
        map.put(String.valueOf(firstLetter), listByLetter);
    }
}


