package io.khasang.wlogs.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/** @author Sergey Orlov
 * @version 16.02.2016
 * This class provides model for structuring data of typeerror table*/

public class TypeErrorModel extends LogModel {
    private int id;
    private String error_level;
    private String critical;

    public String getCritical() {
        return critical;
    }

    public String getError_level() {
        return error_level;
    }

    public int getId() {
        return id;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public void setError_level(String error_level) {
        this.error_level = error_level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static TypeErrorModel createFromResultSet(ResultSet resultSet) throws SQLException{
        TypeErrorModel typeErrorModel = new TypeErrorModel();
            typeErrorModel.setId(resultSet.getInt("id"));
            typeErrorModel.setError_level(resultSet.getString("error_level"));
            typeErrorModel.setCritical(resultSet.getString("critical"));

        try {
            typeErrorModel.setOccurredAt(resultSet.getDate("occurred_at"));
            typeErrorModel.setErrorSource(resultSet.getString("error_source"));
            typeErrorModel.setErrorDescription(resultSet.getString("error_description"));
        } catch (SQLException e) {
            System.out.println("Исключение обработано.Используется модель TypeErrorModel без Join c LogModel: "+
                    typeErrorModel.getClass());
        }
        return typeErrorModel;
    }
}
