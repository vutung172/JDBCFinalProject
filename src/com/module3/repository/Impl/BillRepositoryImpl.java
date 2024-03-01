package com.module3.repository.Impl;

import com.module3.MySql.Views.View;
import com.module3.entity.Account;
import com.module3.entity.BillDetail;
import com.module3.entity.Employee;
import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Index;
import com.module3.util.MySqlConnect.MySQLConnect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BillRepositoryImpl implements com.module3.repository.BillRepository {
    @Override
    public List<BillDetail> findByBillId(long billId) {
        List<BillDetail> billDetailList = new ArrayList<>();
        try  (Connection conn = new MySQLConnect().getConnection()) {
            List<Field> keysField = getIndexes(Account.class);
            keysField.addAll(getIndexes(Employee.class));
            String keysName = keysField.stream().map(f -> colName(f) + " LIKE concat('%',?,'%')").collect(Collectors.joining(" OR "));
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1}", View.billToBillDetail, keysName);
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= keysField.size(); i++)
                ps.setObject(i, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BillDetail entity = readResultSet(rs);
                billDetailList.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetailList;
    }
    private List<Field> getIndexes(Class entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Objects.nonNull(f.getAnnotation(Index.class)))
                .collect(Collectors.toList());
    }
    private String colName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column))
            return column.name();
        return null;
    }
    private BillDetail readResultSet(ResultSet rs) throws Exception {
        BillDetail entity = new BillDetail();
        List<Field> fields = getColumns();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(Date.class)) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                field.set(entity, fmt.parse(rs.getString(colName(field))));
            } else {
                field.set(entity, rs.getObject(colName(field)));
            }
        }
        return entity;
    }
    private List<Field> getColumns() {
        Field[] fields = Account.class.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Objects.nonNull(f.getAnnotation(Column.class)))
                .collect(Collectors.toList());
    }
}
