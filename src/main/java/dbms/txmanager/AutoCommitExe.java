package dbms.txmanager;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/30 下午 02:57
 */
public class AutoCommitExe {
    public static void main(String[] args) {
        var autocommit = new AutoCommitTx();
        autocommit.readProductTitles();
    }
}
