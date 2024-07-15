import com.calcite.util.CalciteUtil;
import com.calcite.util.ResultSetPrinter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用自定义的 csv 源，查询数据
 * 贵公司这套测试题非常有意思,我对calcite有着非常浓厚的兴趣，平时有时间也会阅读集成calcite相关框架的源码积累calcite，由于时间的原因考试题的有些不是最佳解答方式
 * 希望能加入贵公司专注做calcite相关开发 谢谢～
 * 语法文来自开源 calcite-1.35.0
 * 我的博客：
     * https://blog.csdn.net/linjunjunjun/article/details/124831731?spm=1001.2014.3001.5501
     * https://blog.csdn.net/linjunjunjun/article/details/124831612?spm=1001.2014.3001.5501
     * https://blog.csdn.net/linjunjunjun/article/details/123432245?spm=1001.2014.3001.5501
     *
     * https://juejin.cn/user/1697301685870103/posts
 *
 */
public class Test {

    private Connection connection = null;
    private Statement statement = null;

    public void init() {
        String filePath = "/model.json";
        try {
            connection = CalciteUtil.getConnect(filePath);
            statement = connection.createStatement();
        } catch (Exception ex) {
            System.out.println("init error");
        }
    }

    @org.junit.jupiter.api.Test
    public void testMaxBy() {
        try {
            init();
            String sql = "select MAX_BY(NAME3,ID1), MAX_BY(NAME3,ID1,3) from TEST_CSV.TEST";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetPrinter.printResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.jupiter.api.Test
    public void testCast() {
        try {
            init();
            String sql = "select cast([12,23,23,67,78,89,90,null,NAME3] AS varchar)," +
                    " cast(0 AS BOOL) ,(CURRENTTIME() +1)" +
                    "  from TEST_CSV.TEST ";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetPrinter.printResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @org.junit.jupiter.api.Test
    public void testScope() {
        try {
            init();
            String sql = "select NAME3 as name , NAME3 as NAME3 from TEST_CSV.TEST" +
                    " where 'first' = name and 1=1";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetPrinter.printResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @org.junit.jupiter.api.Test
    public void testFanout() {
        // TODO

    }
}
