package q004;

/**
 * Q004 ソートアルゴリズム
 *
 * ListManagerクラスをnewして、小さい順に並び変えた上でcheckResult()を呼び出してください。
 *
 * 実装イメージ:
 * ListManager data = new ListManager();
 * // TODO 並び換え
 * data.checkResult();
 *
 * - ListManagerクラスを修正してはいけません
 * - ListManagerクラスの dataList を直接変更してはいけません
 * - ListManagerクラスの比較 compare と入れ替え exchange を使って実現してください
 */
public class Q004 {
    public static void main(String[] args) {
        ListManager data = new ListManager();

        for (int i = 0; i < 100; i += 1) {
            for (int f = 0; f < 100; f += 1) {
                int check = data.compare(i, f);
                if (check == -1) {
                    data.exchange(i, f);
                }
            }
        }

        data.checkResult();
    }
}
// 完成までの時間: 00時間 10分