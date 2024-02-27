package com.module3.repository.Impl;

import com.module3.repository.Repository;
import com.module3.util.Annotation.Column;
import com.module3.util.Annotation.Id;
import com.module3.util.Annotation.Table;
import com.module3.util.MySqlConnect.MySQLConnect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RepositoryImpl<T> implements Repository<T> {
    @Override
    public List<T> fìndAllByPagination(Class<T> entityClass, Integer pageNumber) {
        List<T> result = new ArrayList<>();
        try  (Connection conn = new MySQLConnect().getConnection()) {
            if (pageNumber > 0){
                int offset = (pageNumber-1)*10;
                String sql = MessageFormat.format("SELECT * FROM {0} LIMIT 10 OFFSET {1}", tblName(entityClass), offset);
                System.out.println(sql);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                rs.getFetchSize();
                while (rs.next()) {
                    T entity = readResultSet(rs, entityClass);
                    result.add(entity);
                }
            } else {
                System.err.println("page error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();
        try  (Connection conn = new MySQLConnect().getConnection()) {
            String sql = MessageFormat.format("SELECT * FROM {0}", tblName(entityClass));
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                T entity = readResultSet(rs, entityClass);
                result.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T findId(Class<T> entityClass, Object... keys) {
        try  (Connection conn = new MySQLConnect().getConnection()) {
            List<Field> keysField = getKey(entityClass);
            String keysName = keysField.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(" AND "));
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1}", tblName(entityClass), keysName);
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            int index = 1;
            for (Object key : keys)
                ps.setObject(index++, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                T entity = readResultSet(rs, entityClass);
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T add(T entity) {
        try (Connection conn = new MySQLConnect().getConnection()) {
            List<Field> fields = getColumns((Class<T>) entity.getClass());
            String columns = fields.stream().map(this::colName).collect(Collectors.joining(","));
            String values = fields.stream().map(f -> "?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})", tblName((Class<T>) entity.getClass()), columns, values);
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            int index = 1;
            for(Field f : fields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T edit(T entity) {
        try (Connection conn = new MySQLConnect().getConnection()) {
            List<Field> updateFields = getColumnsIgnoreKey((Class<T>) entity.getClass());
            List<Field> keyFields = getKey((Class<T>) entity.getClass());

            String columns = updateFields.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
            String key = keyFields.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("UPDATE {0} SET {1} WHERE {2}", tblName((Class<T>) entity.getClass()), columns, key);
            PreparedStatement ps = conn.prepareStatement(sql);
            int index = 1;
            for(Field f : updateFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            for(Field f : keyFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(Class<T> entityClass, Object... keys) {
        try  (Connection conn = new MySQLConnect().getConnection()) {
            List<Field> keysField = getKey(entityClass);
            String keysName = keysField.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("DELETE FROM {0} WHERE {1}", tblName(entityClass), keysName);
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            int index = 1;
            for (Object key : keys)
                ps.setObject(index++, key);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private T readResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        T entity = clazz.getDeclaredConstructor().newInstance();
        List<Field> fields = getColumns(clazz);
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
    private String colName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column))
            return column.name();
        return null;
    }
    private String tblName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        if (Objects.nonNull(table))
            return table.name();
        return null;
    }
    private List<Field> getColumns(Class<T> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Objects.nonNull(f.getAnnotation(Column.class)))
                .collect(Collectors.toList());
    }
    private List<Field> getColumnsIgnoreKey(Class<T> entityClass) {
        List<Field> fields = getColumns(entityClass);
        return fields.stream()
                .filter(f -> Objects.isNull(f.getAnnotation(Id.class)))
                .collect(Collectors.toList());
    }
    private List<Field> getKey(Class<T> entityClass) {
        List<Field> fields = getColumns(entityClass);
        return fields.stream()
                .filter(f -> Objects.nonNull(f.getAnnotation(Id.class)))
                .collect(Collectors.toList());
    }
}