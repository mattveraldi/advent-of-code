import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java solutions to [day one](https://adventofcode.com/2023/day/1)
 * 
 * @author Matteo Veraldi <mattveraldi@gmail.com>
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final int one = partOne();
        final int two = partTwo();
        System.out.println(one + " " + two);
        assert one == 53334;
        assert two == 52834;
    }

    /**
     * For the first part I scan the file line by line,
     * for each line I use regex to find digits and
     * string concatenation to create the number to add after casting it to
     * integers.
     * 
     * @summary the solution to the first part
     */
    public static Integer partOne() throws FileNotFoundException {
        final File file = new File("input.txt");
        final Scanner scanner = new Scanner(file);
        final Pattern pattern = Pattern.compile("(\\d)");
        int result = 0;
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            final Matcher matcher = pattern.matcher(line);
            final List<String> groups = new ArrayList<>();
            while (matcher.find()) {
                groups.add(matcher.group());
            }
            final int targetNumber = Integer.parseInt(groups.get(0) + groups.get(groups.size() - 1));
            result += targetNumber;
        }
        scanner.close();
        return result;
    }

    /**
     * For the second part I scan the file char by char,
     * while reading the char I do two things:
     * - If the char is a EOL, i will finish reading the current line
     * and apply the same logics of part one
     * - If the char is NOT a EOL, i will add it to the current line,
     * replace name numbers to their relative digit and continue reading.
     * 
     * @summary the solution to the first part
     */
    public static Integer partTwo() throws FileNotFoundException {
        final File file = new File("input.txt");
        int result = 0;
        try (Scanner scanner = new Scanner(file).useDelimiter("")) {
            final Pattern pattern = Pattern.compile("(\\d)");
            final Map<String, String> numbersConversionMap = getNumbersConvertionMap();
            String line = "";
            while (scanner.hasNext()) {
                char character = scanner.next().charAt(0);
                if (character == '\n') {
                    final Matcher matcher = pattern.matcher(line);
                    final List<String> groups = new ArrayList<>();
                    while (matcher.find()) {
                        groups.add(matcher.group());
                    }
                    final int targetNumber = Integer.parseInt(groups.get(0) + groups.get(groups.size() - 1));
                    result += targetNumber;
                    line = "";
                } else {
                    line += character;
                    for (Entry<String, String> entry : numbersConversionMap.entrySet()) {
                        if (line.contains(entry.getKey())) {
                            line = line.replace(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
            scanner.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> getNumbersConvertionMap() {
        final Map<String, String> numbersHelper = new HashMap<>();
        numbersHelper.put("one", "1");
        numbersHelper.put("two", "2");
        numbersHelper.put("three", "3");
        numbersHelper.put("four", "4");
        numbersHelper.put("five", "5");
        numbersHelper.put("six", "6");
        numbersHelper.put("seven", "7");
        numbersHelper.put("eight", "8");
        numbersHelper.put("nine", "9");
        return numbersHelper;
    }

}
