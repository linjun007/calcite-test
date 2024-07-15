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

    /**
     * 自定义agg函数：
     * com.calcite.fn.MaxByFn 默认一个
     * com.calcite.fn.MaxByFnForLimit 可以传limit
     * 由于时间原因这个中实现方式不是很优，应该语法文件思路来做
     *
     */
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

    /**
     * 扩展语法文件 ：src/main/codegen/templates/Parser.jj
     * 3.CAST:行号 6129-6157
             * |
             *         <CAST> { f = SqlStdOperatorTable.CAST; }
             *         {List<SqlNode> array = new ArrayList<SqlNode>();}
             *         {array.add(SqlLiteral.createSymbol(SqlJsonConstructorNullClause.NULL_ON_NULL, getPos()));}
             *
             *        { s = span(); }
             *        <LPAREN>
             *            (
             *
             *                e = Expression(ExprContext.ACCEPT_SUB_QUERY) { args.add(e); }
             *                |
             *               <LBRACKET>
             *                 e = Expression(ExprContext.ACCEPT_SUB_QUERY){array.add(e);}
             *                 (
             *                     <COMMA> e = Expression(ExprContext.ACCEPT_SUB_QUERY){ array.add(e);}
             *                 )*
             *               <RBRACKET>
             *               { args.add((SqlStdOperatorTable.JSON_ARRAY.createCall(s.end(this), array))); }
             *
             *            )
             *        <AS>
             *            (
             *                dt = DataType() { args.add(dt); }
             *            |
             *                <INTERVAL> e = IntervalQualifier() { args.add(e); }
             *            )
             *        <RPAREN> {
             *            return f.createCall(s.end(this), args);
             *        }
     * 2.BOOL:行号：5741
     *   |
     *        <BOOL> { s.add(this); sqlTypeName = SqlTypeName.BOOLEAN; }
     *
     *  3.  自定义CURRENTTIME()函数 src/main/java/com/calcite/fn/CurrentTimeFn.java
     *  4.PLUS op
     *    1)com.calcite.fn.CurrentTimeFn#add(java.sql.Timestamp, java.lang.Integer) 为了解析
     *    2)com.calcite.fn.CurrentTimeFn#add(java.lang.Long, int) 为什么要有这个函数，为了执行，因为calcite中Timestamp类型回转换为long,可以优化
     *    3)plus 参数验证 org.apache.calcite.sql.type.OperandTypes#TIMESTAMPE_NUMERIC
     *    4) 内置方法：org.apache.calcite.util.BuiltInMethod#Timestamp_INTEGER
     *    5) org.apache.calcite.adapter.enumerable.RexImpTable.DatetimeArithmeticImplementor#implementSafe(org.apache.calcite.adapter.enumerable.RexToLixTranslator, org.apache.calcite.rex.RexCall, java.util.List)
     *    6)TODO 1+ CURRENTTIME()
     *
     */
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

    /**
     * 替换where：src/main/java/org/apache/calcite/sql/validate/SqlValidatorImpl.java
     *
     * org.apache.calcite.sql.validate.SqlValidatorImpl#replaceWhereSqlIdentifier(org.apache.calcite.sql.SqlSelect, org.apache.calcite.sql.SqlNode)
     *
     */
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
