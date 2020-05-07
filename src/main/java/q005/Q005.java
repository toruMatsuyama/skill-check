package q005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static q005.WorkData.PositionType;
import static q005.WorkData.PositionType.Butyo;
import static q005.WorkData.PositionType.Ippan;
import static q005.WorkData.PositionType.Katyo;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {

    public static void main(String[] args) {
        List<WorkData> workDataList = getData();
        sumTimePosition(workDataList);
        sumTimePCode(workDataList);
        sumTimeMember(workDataList);
    }

    private static void sumTimePosition(List<WorkData> workDataList) {
        Map<String, Integer> positionData = new LinkedHashMap<>();
        int sumTimeButyo = 0;
        int sumTimeKatyo = 0;
        int sumTimeIppan = 0;

        for (WorkData workData : workDataList) {
            switch (Objects.requireNonNull(PositionType.getByName(workData.getPosition()))) {
                case Butyo:
                    sumTimeButyo += workData.getWorkTime();
                    break;
                case Katyo:
                    sumTimeKatyo += workData.getWorkTime();
                    break;
                case Ippan:
                    sumTimeIppan += workData.getWorkTime();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + workData.getPosition());
            }
        }

        positionData.put(Butyo.getName(), sumTimeButyo);
        positionData.put(Katyo.getName(), sumTimeKatyo);
        positionData.put(Ippan.getName(), sumTimeIppan);

        printData(positionData);
    }

    private static void sumTimePCode(List<WorkData> workDataList) {
        Map<String, Integer> pCodeData = new HashMap<>();

        for (WorkData workData : workDataList) {
            pCodeData.merge(workData.getpCode(), workData.getWorkTime(), Integer::sum);
        }

        printData(pCodeData);
    }

    private static void sumTimeMember(List<WorkData> workDataList) {
        Map<String, Integer> memberData = new TreeMap<>();

        for (WorkData workData : workDataList) {
            memberData.merge(workData.getNumber(), workData.getWorkTime(), Integer::sum);
        }

        printData(memberData);
    }

    private static void printData(Map<String, Integer> dataList) {
        for (Map.Entry<String, Integer> entry : dataList.entrySet()) {
            System.out.println(entry.getKey() + ": " + subTime(entry.getValue()));
        }
    }

    private static String subTime(int sec) {
        int minutes = sec / 60;
        return minutes / 60 + "時間" + minutes % 60 + "分";
    }

    private static InputStream openDataFile() {
        return Q005.class.getResourceAsStream("data.txt");
    }

    public static List<WorkData> getData() {
        List<WorkData> workDataList = new ArrayList<>();
        String line;
        int index = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(openDataFile()))) {
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    index += 1;
                    continue;
                }
                String[] data = line.split(",");
                workDataList.add(mapper(data));
                index += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workDataList;
    }

    private static WorkData mapper(String[] data) {
        WorkData workData = new WorkData();
        workData.setNumber(data[0]);
        workData.setDepartment(data[1]);
        workData.setPosition(data[2]);
        workData.setpCode(data[3]);
        workData.setWorkTime(Integer.parseInt(data[4]));
        return workData;
    }

}
// 完成までの時間: 2時間 00分