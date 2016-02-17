package io.khasang.wlogs.model;

import com.mysql.jdbc.DatabaseMetaData;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class BackupDB {

    public String backupDatabase(String numTable) {
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("mysqldump -u root -proot wlogs " + numTable + " > backup.sql");
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/wlogs", "root", "root");
            DatabaseMetaData md = (DatabaseMetaData) connection.getMetaData ();
            ResultSet resultSet = md.getTables(null, null, "%", null);
            StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append("List table: " + "<br>");
            while (resultSet.next()) {
                stringBuilder.append("<form>");
                stringBuilder.append("<p><button formaction='/'>Backup table: ")
                        .append(resultSet.getString(3)) // столбец 3 = имя таблицы
                        .append("</button></p>");
                stringBuilder.append("</form>");
            }
            resultSet.close ();
            return stringBuilder.toString();
        } catch (Exception e) {
            return "Have error: " + e;
        }
    }
}
