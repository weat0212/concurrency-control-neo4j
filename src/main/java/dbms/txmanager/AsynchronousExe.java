package dbms.txmanager;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/30 下午 02:48
 */
public class AsynchronousExe {
    public static void main(String[] args) {
        var asyn = new AsynchronousTx();
        asyn.printAllProducts();
    }
}
