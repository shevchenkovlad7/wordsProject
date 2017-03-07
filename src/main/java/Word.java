import java.io.*;
import java.util.*;

/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 06.03.17
 */
public class Word {
    public static void main(String[] args) {

        List<String> allLines = new ArrayList<>();
        Map<String, List<String>>mapByLetter = new LinkedHashMap<>();
        try {
            loadWords(allLines, mapByLetter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("words is loaded");

        Map<Integer, Set<Obj>> map = new LinkedHashMap<>();
        int firstLength = 0;
        int secondLength = 0;

        long start = System.nanoTime();
        for (int i = allLines.size() - 1; i >= 0; i--) {
            Set<String> concatWords = new LinkedHashSet<>();
            String line = allLines.get(i);

            if (line.length() < secondLength) {
                continue;
            }

            int count = 0;
            boolean flag = true;
            while (flag && count != line.length()) {
                List<String> listByLetter = mapByLetter.get(String.valueOf(line.charAt(count)));
                for (int j = listByLetter.size() - 1; j >= 0; j--) {
                    String word = listByLetter.get(j);

                    if (word.concat("s").equals(line)) continue;
                    if (line.startsWith(word, count) && !line.equals(word)) {
                        concatWords.add(word);
                        count += word.length();
                        break;
                    }
                    if (j == 0) {
                        flag = false;
                    }
                }
            }
            if (count == line.length()) {
                int length = line.length();
                if (firstLength < length) {
                    firstLength = length;
                } else if (firstLength != length && secondLength < length) {
                    secondLength = length;
                }
                Set<Obj> set = map.get(length);
                if (set == null) {
                    set = new HashSet<>();
                    map.put(length, set);
                }
                set.add(new Obj(line, concatWords));
                System.out.println("add —> " + line);
            }
        }

        long end = System.nanoTime();
        System.out.println("Time = " + (end - start));

        Set<Obj> first = map.get(firstLength);
        System.out.println("FirstLength = " + firstLength + " —> " + Arrays.toString(first.toArray(new Obj[]{})));
        Set<Obj> second = map.get(secondLength);
        System.out.println("SecondLength = " + secondLength + " —> " + Arrays.toString(second.toArray(new Obj[]{})));
    }

    private static void loadWords(List<String> allLines, Map<String, List<String>> map) throws FileNotFoundException {
        File file = new File("/home/vshevchenko/words.txt");
        Scanner scanner = new Scanner(file);
        char firstLetter = 'a';
        List<String> listByLetter = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (!word.isEmpty()) {
                char firstChar = word.charAt(0);
                if (firstLetter != firstChar) {
                    map.put(String.valueOf(firstLetter), listByLetter);
                    firstLetter = firstChar;
                    listByLetter = new ArrayList<>();
                } else {
                    listByLetter.add(word);
                }
                allLines.add(word);
            }
        }
        map.put(String.valueOf(firstLetter), listByLetter);
    }

    private static Set<String> asSet(String[] mas) {
        Set<String> list = new TreeSet<>();
        for (int i = 0; i < mas.length; i++) {
            String s = mas[i];
            if (!s.isEmpty()) {
                list.add(mas[i]);
            }
        }
        return list;
    }
}


