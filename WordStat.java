import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class WordStat {
  // a Hash table to store elements
  private NewHashTable table;
  // the Tokenizer to normalize words and read from external files
  private Tokenizer toke;
  // a list with all words ranked according to their count
  private String[][] list;
  // the number of words in the table
  private int size;
  private NewHashTable tablePairs;
  private ArrayList<String> words;

  public WordStat(String s) throws IOException {
    Tokenizer t = new Tokenizer(s);
    toke = t;
    ArrayList<String> arr = t.wordList();
    // System.out.println(arr.toString());
    this.table = new NewHashTable();
    list = new String[arr.size()][arr.size()];
    for (int i = 0; i < arr.size(); i++) {
      table.put(arr.get(i), 1);
    }
    for (int i = 0; i < arr.size(); i++) {
      wordRank(arr.get(i));
    }
    tablePairs = tablePairs();
    words = new ArrayList<String>();

  }

  public WordStat(String[] s) {
    Tokenizer t = new Tokenizer(s);
    toke = t;
    ArrayList<String> arr = toke.wordList();
    // System.out.println(arr.toString());
    this.table = new NewHashTable();
    list = new String[arr.size()][arr.size()];
    for (int i = 0; i < arr.size(); i++) {
      table.put(arr.get(i), 1);
    }
    for (int i = 0; i < arr.size(); i++) {
      wordRank(arr.get(i));
    }
    tablePairs = tablePairs();
    words = new ArrayList<String>();
  }

  public int wordCount(String str) {
    // normalize the string first
    String s = toke.normalize(str);
    if (table.get(s) == -1) {
      return 0;
    }
    return table.get(s);
  }

  public int wordPairCount(String w1, String w2) {
    NewHashTable tab = tablePairs();
    return tab.get(toke.normalize(w1) + " " + toke.normalize(w2));
  }

  public int wordRank(String word) {
    // assumes the word has a rank 1
    int rank = 1;
    // normalizes the word
    int count = wordCount(toke.normalize(word));
    // the word is not in the table
    if (count == 0) {
      return -1;
    }
    // checks the count of other words in the table to determine the true rank of
    // the word
    for (int i = 0; i < table.entry().length; i++) {
      if (table.entry()[i] != null) {
        int s = wordCount(table.entry()[i].getKey());
        // if(s != count)
        // for every word with more wordCount, the word's rank is incremented
        if (s > count) {
          rank++;
        }
      }
    }
    // adds the word to the list at an index corresponding to the it's rank
    if (toke.normalize(word).length() > 0) {
      // if at that index there is no word, the word becomes the first element in list
      if (list[rank - 1][0] == null) {
        list[rank - 1][0] = toke.normalize(word);
        size++;
      }
      // if there is a word already, the two words have the same rank, hence they get
      // stored in a linked list
      else if (list[rank - 1][0] != null) {
        // System.out.println(list[rank - 1].getWord());
        int index = 1;
        boolean duplicate = false;
        for (int j = 0; j < list[rank - 1].length; j++) {
          if (list[rank - 1][j] != null && list[rank - 1][j].equals(toke.normalize(word)) == false) {
            index++;
          }
          if (list[rank - 1][j] != null && list[rank - 1][j].equals(toke.normalize(word))) {
            duplicate = true;
            break;
          }
        }
        // System.out.println("index; " + word + " " + index);
        if (duplicate == false) {
          list[rank - 1][index] = toke.normalize(word);
          size++;
        }
      }
    }
    return rank;
  }

  public String[] mostCommonWords(int k) {
    // creates an empty array with the size k
    String[] mostCW = new String[k];
    // and index to the new array
    int index = 0;
    for (int i = 0; i < list.length; i++) {
      for (int j = 0; j < list[i].length; j++) {
        if (list[i][j] != null) {
          mostCW[index++] = list[i][j];
          if (index == k) {
            return mostCW;
          }
        }
      }
    }

    return mostCW;
  }

  public String[] leastCommonWords(int k) {
    // this is the array to be returned with the least elements in order
    String[] leastWords = new String[k];
    // an array with all words in ascending order of their count
    String[] list = mostCommonWords(size);
    // the indexes to the new array(leastWords)
    int index = 0;
    // counter to keep track of the number of times words are added to the new
    // array(leasWords)
    int counter = 0;
    // starts with the last element of the list
    for (int i = list.length - 1; i >= 0; i--) {
      // list might contain nulls, so only adds valid elements from list to new array
      if (list[i] != null) {
        leastWords[index++] = list[i];
        counter++;
      }
      // at this point, we have added the required k values into the least, so no need
      // to continue looping
      if (counter == k) {
        break;
      }

    }
    return leastWords;
  }

  public NewHashTable tablePairs() {
    ArrayList<String> list = toke.wordList();
    NewHashTable table = new NewHashTable();
    for (int i = 0; i < list.size() - 1; i++) {
      table.put(toke.normalize(list.get(i)) + " " + toke.normalize(list.get(i + 1)), i);
    }

    return table;
  }

  public String[] mostCommonWordPairs(int k) {
    String[] pairs = new String[k];
    NewHashTable table = tablePairs;
    int index = 0;
    for (int i = 0; i < table.entry().length - 1; i++) {
      int temp = 0;
      String s = null;
      for (int j = 0; j < table.entry().length; j++) {
        if (table.entry()[j] != null) {
          if (table.get(table.entry()[j].getKey()) > temp && !contains(pairs, table.entry()[j].getKey())) {
            s = table.entry()[j].getKey();
            temp = table.get(table.entry()[j].getKey());
          }
        }
      }
      if (s != null) {
        pairs[index++] = s;
        if (index == k) {
          return pairs;
        }
      }
      temp = 0;
    }
    return pairs;
  }

  protected boolean contains(String[] arr, String s) {
    for (int i = 0; i < arr.length; i++) {
      if (s.equals(arr[i])) {
        return true;
      }
    }
    return false;
  }

  public int wordPairRank(String w1, String w2) {
    NewHashTable table = tablePairs();
    int count = table.get(toke.normalize(w1) + " " + toke.normalize(w2));
    if (count == -1) {
      return -1;
    }
    int rank = 1;
    for (int i = 0; i < table.entry().length; i++) {
      if (table.entry()[i] != null)
        if (count < table.get(table.entry()[i].getKey())) {
          rank++;
        }
    }
    return rank;
  }

  public String[] mostCommonCollocs(int k, String baseWord, int i) {
    // the index to the new Array
    int index = 0;
    // gets the words from the file
    ArrayList<String> list = toke.wordList();
    // new array that will have the words
    String[] newArr = new String[list.size()];
    // loop to find the words
    for (int j = 0; j < list.size() - Math.abs(i); j++) {
      // checks if the word at an index is equal to the parameter baseWord
      if (list.get(j).equals(toke.normalize(baseWord))) {
        // for i > 0, the word to be added is the proceeding one
        if (j + i >= 0) {
          if (list.get(j + i) != null) {
            newArr[index++] = list.get(j + i);
            // System.out.println("see " + newArr[1]);
          }
        }
      }
    }
    WordStat w = new WordStat(newArr);
    // System.out.println("see " + newArr[1]);
    if (w.size > k) {
      return w.mostCommonWords(k);
    } else {
      // System.out.println("..........there are fewer elements in the table than your
      // k input........\n");
      return w.mostCommonWords(w.size);
    }

  }

  /*
   * .....................................helper methods
   * ...........................................
   */

  // a helper method that ranks every word according to their count
  protected void rank() {
    for (int j = 0; j < toke.wordList().size(); j++) {
      if (toke.wordList().get(j) != null) {
        wordRank(toke.wordList().get(j));
      }
    }
  }

  /*
   * ............................helper nested
   * classes.................................................
   */

  /**
   * a hash table that extends the class HashTable. the class overrides the put
   * method to desired behavior
   * 
   * @author User
   *
   */
  private class NewHashTable extends HashTable {

    public NewHashTable(int n) {
      super(n);
      setCapacity(n);
    }

    public NewHashTable() {
      super();
    }

    @Override
    public void put(String key, int Value) {
      int index = key.hashCode() % this.capacity();
      if (index < 0) {
        index = index * -1;
      }
      if (entry()[index] == null) {
        if (this.loadFactor() > 0.7) {
          NewHashTable tab = this.rehash();
          setEntry(tab.entry());
          setCapacity(tab.capacity());
        }
        super.entry()[index] = new HashEntry(key, 1);
        setSize(size() + 1);
      } else {
        int x = 0;
        // index = index % capacity;
        while (entry()[index] != null) {
          if (entry()[index].getKey().equals(key)) {
            x = entry()[index].getValue();
            x++;
            entry()[index].setValue(x);
            return;
          }
          index++;
          index = index % capacity();
        }
        entry()[index] = new HashEntry(key, 1);
        setSize(size() + 1);
        ;

      }
    }

    protected NewHashTable rehash() {
      NewHashTable tab = new NewHashTable(capacity() * 2);
      for (int i = 0; i < capacity(); i++) {
        if (entry()[i] != null) {
          for (int j = 0; j < entry()[i].getValue(); j++) {
            tab.put(entry()[i].getKey(), entry()[i].getValue());
          }
        }
      }
      return tab;
    }
  }

  public String[][] list() {
    return list;
  }

  public void printList() {
    for (int i = 0; i < list.length; i++) {
      for (int j = 0; j < list[i].length; j++) {
        if (list[i][j] != null)
          System.out.println(list[i][j]);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String[] b = new String[14];
    b[0] = "James";
    b[1] = "John";
    b[2] = "Jux";
    b[3] = "James";
    b[4] = "Jeremiah";
    b[5] = "James";
    b[6] = "John";
    b[7] = "Joseph";
    b[8] = "James";
    b[9] = "Jamila";
    b[10] = "Jimmy";
    b[11] = "Juru";
    b[12] = "James";
    b[13] = "John";
    WordStat w = new WordStat(b);

    // Demonstration

    System.out.println("the count of \"James\": " + w.wordCount("James"));
    System.out.println("the wordPair count of \"Jamila Jimmy\": " + w.wordPairCount("Jamila", "Jimmy"));
    System.out.println("the ranking of James: " + w.wordRank("James"));
    System.out.println("the ranking of the pair \"Juru James\": " + w.wordPairRank("Juru", "James"));
    System.out.print("10 most common words:");
    String[] s = w.mostCommonWords(10);
    for (int i = 0; i < s.length; i++)
      System.out.print(" " + s[i] + ",");
    System.out.println();

    System.out.print("7 least common words:");
    String[] t = w.leastCommonWords(7);
    for (int i = 0; i < t.length; i++)
      System.out.print(" " + t[i] + ",");
    System.out.println();

    System.out.print("4 most common word Pairs:");
    String[] u = w.mostCommonWordPairs(4);
    for (int i = 0; i < u.length; i++)
      System.out.print(" " + u[i] + ",");
    System.out.println();

    System.out.print("3 most common Collocations of \"the\":");
    String[] v = w.mostCommonCollocs(3, "James", 1);
    for (int i = 0; i < v.length; i++)
      System.out.print(" " + v[i] + ",");
  }
}
