package com.calcite.schema.csv;

import com.calcite.fn.CurrentTimeFn;
import com.calcite.fn.MaxByFn;
import com.calcite.fn.MaxByFnForLimit;
import java.util.Map;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AggregateFunctionImpl;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;

public class CsvSchemaFactory implements SchemaFactory {
    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
        parentSchema.add("MAX_BY", AggregateFunctionImpl.create(MaxByFn.class));
        parentSchema.add("MAX_BY", AggregateFunctionImpl.create(MaxByFnForLimit.class));
        parentSchema.add("CURRENTTIME", ScalarFunctionImpl.create(CurrentTimeFn.class, "eval"));
        return new CsvSchema(String.valueOf(operand.get("dataFile")));
    }


}
