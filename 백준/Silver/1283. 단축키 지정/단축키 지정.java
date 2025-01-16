import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String[] options = new String[n];
        Set<Character> usedShortcuts = new HashSet<>();
        String[] result = new String[n];

        for (int i = 0; i < n; i++) {
            options[i] = sc.nextLine();
        }

        for (int i = 0; i < n; i++) {
            String[] words = options[i].split(" ");
            boolean assigned = false;

            // Step 1: Check first letters of each word
            for (int j = 0; j < words.length; j++) {
                char firstChar = Character.toLowerCase(words[j].charAt(0));
                if (!usedShortcuts.contains(firstChar)) {
                    usedShortcuts.add(firstChar);
                    words[j] = "[" + words[j].charAt(0) + "]" + words[j].substring(1);
                    assigned = true;
                    break;
                }
            }

            // Step 2: Check all characters in the option
            if (!assigned) {
                String fullOption = String.join(" ", words);
                for (int j = 0; j < fullOption.length(); j++) {
                    char c = Character.toLowerCase(fullOption.charAt(j));
                    if (c != ' ' && !usedShortcuts.contains(c)) {
                        usedShortcuts.add(c);
                        fullOption = fullOption.substring(0, j) + "[" + fullOption.charAt(j) + "]" + fullOption.substring(j + 1);
                        result[i] = fullOption;
                        assigned = true;
                        break;
                    }
                }
            }

            // If no shortcut was assigned, keep the original option
            if (!assigned) {
                result[i] = String.join(" ", words);
            } else if (result[i] == null) {
                result[i] = String.join(" ", words);
            }
        }

        // Print the result
        for (String line : result) {
            System.out.println(line);
        }
    }
}