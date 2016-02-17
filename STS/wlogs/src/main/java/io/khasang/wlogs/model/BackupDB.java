package io.khasang.wlogs.model;

import com.mysql.jdbc.DatabaseMetaData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by vvv on 17.02.16.
 */
public class BackupDB {

    public String backupDatabase(int numTable) {
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec(
                    "mysqldump -u root -proot wlogs > backup.sql");
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                return "Backup create successful!";
            } else {
                return "Backup not successful!";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e;
        }
    }

    public String showTable() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:wlogs", "root", "root");
            DatabaseMetaData md = (DatabaseMetaData) connection.getMetaData ();
            ResultSet resultSet = md.getTables (connection.getCatalog(), null, "TAB_%", null);
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next ()) {
                stringBuilder.append(resultSet.getString(3)); // столбец 3 = имя таблицы
            }
            resultSet.close ();
            return stringBuilder.toString();
        } catch (Exception e) {
            return "Have error: " + e;
        }
    }
}
