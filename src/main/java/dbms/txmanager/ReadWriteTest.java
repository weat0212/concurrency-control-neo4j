package dbms.txmanager;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/29 下午 03:14
 */
public class ReadWriteTest {
    public static void main(String[] args) {
        ReadWriteTx readWriteTx = new ReadWriteTx();
        Long temp = readWriteTx.addPerson("Andy");
        System.out.println("<id>:"+temp);
        readWriteTx.driver.close();
    }
}
