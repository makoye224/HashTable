import java.io.IOException;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class WordStatTest {

    @Test
    public void wordCountTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        assertEquals(41, stat.wordCount("you"));
        assertEquals(14, stat.wordCount("it"));
        assertEquals(5, stat.wordCount("medium"));

    }

    @Test
    public void wordRankTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        assertEquals(1, stat.wordRank("you"));
        assertEquals(5, stat.wordRank("or"));
        assertEquals(51, stat.wordRank("under"));

    }

    @Test
    public void wordPairCountTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        assertEquals(10, stat.wordPairCount("if", "you"));
        assertEquals(10, stat.wordPairCount("this", "etext"));
        assertEquals(7, stat.wordPairCount("project", "gutenberg"));

    }

    @Test
    public void wordPairRankTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        assertEquals(1, stat.wordPairRank("if", "you"));
        assertEquals(1, stat.wordPairRank("this", "etext"));
        assertEquals(3, stat.wordPairRank("project", "gutenberg"));

    }

    @Test
    public void mostCommonWordTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        String[] a = stat.mostCommonWords(4);
        assertEquals("you", a[0]);
        assertEquals("the", a[1]);
        assertEquals("of", a[2]);
        assertEquals("to", a[3]);

    }

    @Test
    public void mostCommonwordPairRankTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        String[] a = stat.mostCommonWordPairs(4);
        assertEquals("if you", a[0]);
        assertEquals("this etext", a[1]);
        assertEquals("project gutenberg", a[2]);
        assertEquals("of the", a[3]);
    }

    @Test
    public void leastCommonWordsTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        String[] a = stat.leastCommonWords(4);
        assertEquals("addition", a[0]);
        assertEquals("modification", a[1]);
        assertEquals("alteration", a[2]);
        assertEquals("distribution", a[3]);

    }

    @Test
    public void mostCommonCollocsTest() throws IOException {
        WordStat stat = new WordStat("\\Users\\User\\Desktop\\captmidn.txt");
        String[] a = stat.mostCommonCollocs(4, "the", 1);
        assertEquals("project", a[0]);
        assertEquals("first", a[1]);
        assertEquals("money", a[2]);
        assertEquals("person", a[3]);

    }

    @Test
    public void putTest() {
        HashTable table = new HashTable();
        table.put("Data Structures", 2022);
        assertEquals(2022, table.get("Data Structures"));
    }

    @Test
    public void getTest() {
        HashTable table = new HashTable();
        table.put("Data Structures", 2022);
        assertEquals(2022, table.get("Data Structures"));
    }

    @Test
    public void updateTest() {
        HashTable table = new HashTable();
        table.put("Data Structures", 2022);
        table.update("Data Structures", 2023);
        assertEquals(2023, table.get("Data Structures"));
    }

    @Test
    public void getKeyTest() {
        HashEntry entry = new HashEntry("CWRU", 1826);
        assertEquals("CWRU", entry.getKey());
    }

    @Test
    public void getValueTest() {
        HashEntry entry = new HashEntry("CWRU", 1826);
        assertEquals(1826, entry.getValue());
    }

    @Test
    public void setValueTest() {
        HashEntry entry = new HashEntry("CWRU", 1826);
        entry.setValue(1800);
        assertEquals(1800, entry.getValue());
    }

    @Test
    public void normalizeTest() throws IOException {
        Tokenizer toke = new Tokenizer("\\Users\\User\\Desktop\\captmidn.txt");
        assertEquals("casewesternreserveuniversity", toke.normalize("CasE WES%$# Te rnreserve UnivErsity"));
    }

    @Test
    public void wordListTest() {
        String[] b = new String[3];
        b[0] = "James";
        b[1] = "John";
        b[2] = "Jux";
        Tokenizer toke = new Tokenizer(b);
        ArrayList<String> a = toke.wordList();
        assertEquals("james", a.get(0));
        assertEquals("john", a.get(1));
        assertEquals("jux", a.get(2));
    }

}
