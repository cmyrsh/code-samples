import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PalindromeGenerator
 * This sample shows generation of Palindrome strings given 2 parameters
 *  1. Total Length of string
 *  2. Number of Unique Characters in string
 */
public class PalindromeGenerator {

    final String[] lowercase = "abcdefghijklmnopqrstuvwxyz".split("");

    public String solution(int totalLength, int uniqueChars) {

        // Random Number Generator
        final Random random = new Random(uniqueChars);

         // Find length of middle part
        Integer middlelength = totalLength % 2 == 0 ? 2 : 1;
        // Find length of side part
        Integer sidelength = (totalLength - middlelength) / 2;

        System.out.println(String.format("Length %d Unique %d", totalLength, uniqueChars));

        // Create modifiable list from where charaters can be picked up
        List<String> letters = new ArrayList<>();

        letters.addAll(Arrays.asList(lowercase));

        // Pick a middle char at random
        String middlechar = new StringBuilder().append(letters.remove(random.nextInt(letters.size()))).toString();

        // Create string from randomly picked middle char. Can be of length 1 or 2.
        String middle = IntStream.range(0, middlelength).mapToObj(i -> middlechar).collect(Collectors.joining());

        // Randomly Pick chars for left part of palindrome
        List<String> left = IntStream.range(0, uniqueChars - 1)
                .mapToObj(i -> new StringBuilder().append(letters.remove(random.nextInt(letters.size()))).toString())
                .collect(Collectors.toList());
        // Make a copy of left chars. This is required if size of left part is greater than unique characters
        // Example: Left part size is 5 and unique chars are 3. Then we first exhaust List<String> left
        //          Later we pick random chars from frozen list. Which will be coming from same set
        List<String> leftfrozen = new ArrayList<>(left);

        String leftchars = IntStream.range(0, sidelength)
                .mapToObj(i -> {
                    if (left.size() == 0)
                        return leftfrozen.get(random.nextInt(leftfrozen.size()));
                    else
                        return left.remove(random.nextInt(left.size()));
                })
                .collect(Collectors.joining());

        // Right chars is just reverse of left
        String rightchars = new StringBuilder(leftchars).reverse().toString();

        return new StringBuilder(leftchars).append(middle).append(rightchars).toString();

    }

    public static void main(String[] args) {
        PalindromeGenerator palindrome = new PalindromeGenerator();
        System.out.println(palindrome.solution(5, 2));
        System.out.println(palindrome.solution(6, 2));
        System.out.println(palindrome.solution(7, 2));
        System.out.println(palindrome.solution(8, 3));
        System.out.println(palindrome.solution(9, 5));
        System.out.println(palindrome.solution(23, 12));
        System.out.println(palindrome.solution(101, 26));
        System.out.println(palindrome.solution(5, 4)); // Will not have 4 uniqe chars in string of length 5 which is a palindrome
    }
}