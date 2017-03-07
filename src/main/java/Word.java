import java.io.*;
import java.util.*;

/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 06.03.17
 */
public class Word {
    public static void main(String[] args) throws IOException {

            List<String> allLines = new ArrayList<>();
            try {
                loadWords(allLines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("words is loaded");

            Map<Integer, Set<Obj>>map = new LinkedHashMap<>();
            int firstLength = 0;
            int secondLength = 0;

            long start = System.nanoTime();
            for (int i = allLines.size() - 1; i > 0; i--) {
                Set<String> concatWords = new TreeSet<>();
                String line = allLines.get(i);
                if (line.length() < secondLength) {
                    continue;
                }

                Set<String> mas = asSet(line.split(allLines.get(i - 1)));
                if (!mas.isEmpty()) {
                    mas.add(allLines.get(i - 1));
                }

                Iterator<String> iterator = mas.iterator();

                while (iterator.hasNext()) {
                    String afterSplit = iterator.next();
                    if (afterSplit.equals("s")) {
                        break;
                    }
                    if (allLines.contains(afterSplit) && !afterSplit.equals(line)) {
                        iterator.remove();
                        concatWords.add(afterSplit);
                        continue;
                    }
                    int count;
                    for (int j = allLines.size() -1; j >= 0 && j != i && j != i - 1; j--) {
                        String word = allLines.get(j);
                        String[] mas1 = afterSplit.split(word);
                        if (mas1.length > 1) {
                            mas.remove(afterSplit);
                            mas.addAll(asSet(mas1));
                            iterator = mas.iterator();
                            concatWords.add(word);
                            break;
                        }
                    }

                }
                if (mas.isEmpty()) {
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

        private static void loadWords(List<String> allLines) throws FileNotFoundException {
            File file = new File("/home/vshevchenko/words.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                if (!word.isEmpty())
                    allLines.add(word);
            }
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


