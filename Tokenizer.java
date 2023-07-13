import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tokenizer {
    // field to store list of elements from the file or string array
    private ArrayList<String> list;

    public Tokenizer(String s) throws IOException {
        // an empty string array is created
        ArrayList<String> tempList = new ArrayList<String>();
        try {
            // bufferReader is used to read each line from a file
            try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
                // a string is created with every word on a line
                String line = reader.readLine();
                while (line != null) {
                    // words are split from the line using spaces
                    String[] words = line.split(" ");
                    // for every word in the line, it's normalized and added to the array list
                    for (int i = 0; i < words.length; i++) {
                        String str = normalize(words[i]);
                        if (str != "") {
                            tempList.add(str);
                        }
                    }
                    // the next line is read
                    line = reader.readLine();
                }
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        // the field list is assigned to the tempList
        list = tempList;
    }

    public Tokenizer(String[] s) {
        // a temp arrayList that will contain all words from the string array
        ArrayList<String> tempList = new ArrayList<String>();
        // loops around and adds every normalized word from the array to the list field
        for (int i = 0; i < s.length; i++) {
            String str = normalize(s[i]);
            tempList.add(str);
        }
        // assigns the tempList to the list field
        list = tempList;
    }

    public ArrayList<String> wordList() {
        return list;
    }

    public String normalize(String s) {
        // when the string s is empty
        if (s == null || s.length() == 0) {
            return "";
        }
        // a string builder that will only contain characters that are letters
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // only appends letters
            if (Character.isLetter(c)) {
                builder.append(Character.toLowerCase(c));
            }
        }

        String a = builder.toString();
        // the string did not have any letters
        if (a.length() == 0) {
            return "";
        } else {
            return a;
        }
    }

}
