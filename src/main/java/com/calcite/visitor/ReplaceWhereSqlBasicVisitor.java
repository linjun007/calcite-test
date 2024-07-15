package com.calcite.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlDataTypeSpec;
import org.apache.calcite.sql.SqlDynamicParam;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlIntervalQualifier;
import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.type.OperandTypes;
import org.apache.calcite.sql.util.SqlBasicVisitor;

public class ReplaceWhereSqlBasicVisitor extends SqlBasicVisitor<Void> {
    Map<String, SqlNode> name2SqlIdentifier = new HashMap<>();

    public ReplaceWhereSqlBasicVisitor(Map<String, SqlNode> param) {
        super();
        name2SqlIdentifier = param;
    }

    @Override
    public Void visit(SqlLiteral literal) {
        return super.visit(literal);
    }

    @Override
    public Void visit(SqlCall call) {
        if (call instanceof SqlBasicCall) {
            List<SqlNode> sqlNodes = call.getOperandList();
            for (int i = 0; i < sqlNodes.size(); i++) {
                if (sqlNodes.get(i) instanceof SqlIdentifier) {
                    String name = sqlNodes.get(i).toString();
                    if (name2SqlIdentifier.get(name) != null) {
                        call.setOperand(i, (SqlNode) name2SqlIdentifier.get(name).clone());
                    }
                } else {
                    sqlNodes.get(i).accept(this);
                }
            }

            return null;
        } else {
            return super.visit(call);
        }
    }

    @Override
    public Void visit(SqlNodeList nodeList) {
        return super.visit(nodeList);
    }

    @Override
    public Void visit(SqlIdentifier id) {
        return super.visit(id);
    }

    @Override
    public Void visit(SqlDataTypeSpec type) {
        return super.visit(type);
    }

    @Override
    public Void visit(SqlDynamicParam param) {
        return super.visit(param);
    }

    @Override
    public Void visit(SqlIntervalQualifier intervalQualifier) {
        return super.visit(intervalQualifier);
    }
}
