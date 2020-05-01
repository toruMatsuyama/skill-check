package q003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Q003 集計と並べ替え
 *
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 *
 * 出力形式:単語=数
 *
[出力イメージ]
（省略）
highest=1
I=3
if=2
ignorance=1
（省略）

 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    static Map<String, Integer> countMap;
    static String FIND_PATTERN = "([a-zA-Z-']+)([ ,.;]?)";

    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static InputStream openDataFile() {
        return Q003.class.getResourceAsStream("data.txt");
    }

    private static void setCount(String word) {
        if (countMap.containsKey(word)) {
            int count = countMap.get(word) + 1;
            countMap.put(word, count);
        } else {
            countMap.put(word, 1);
        }
    }

    private static void wordsCount() {
        Map<String, Integer> map = new HashMap<>();
        Pattern pattern = Pattern.compile(FIND_PATTERN);
        Matcher matcher;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(openDataFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group(1);
                    if (word.equals("I")) {
                        setCount(word);
                    } else {
                        setCount(word.toLowerCase());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> sortWord() {
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.toLowerCase().compareTo(o2.toLowerCase());
                    }
                }
        );
        treeMap.putAll(countMap);
        return treeMap;
    }

    public static void main(String[] args) {
        countMap = new HashMap<String, Integer>();
        wordsCount();

        for (Entry<String, Integer> entry : sortWord().entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
// 完成までの時間: 02時間 00分