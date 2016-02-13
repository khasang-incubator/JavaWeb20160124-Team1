package io.khasang.wlogs.model;

import io.khasang.wlogs.service.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LogLoader {
    public void load() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            BufferedReader input = new BufferedReader(new FileReader(loader.getResource("dev.log").getFile()));
            String line;
            while (null != (line = input.readLine())) {
                Pattern pattern = Pattern.compile("^\\[(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\]\\s([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+):\\s(.*)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    final String occurredAt = matcher.group(1);
                    final String errorSource = matcher.group(2);
                    final String errorLevel = matcher.group(3);
                    final String errorDescription = matcher.group(4);
                    System.out.println(occurredAt);
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSource.getInstance());
                    jdbcTemplate.execute(
                        "INSERT INTO wlogs(occurred_at, error_level, error_source, error_description) VALUES(?,?,?,?)",
                        new PreparedStatementCallback<Boolean>() {
                            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                                DateFormat formatter = new SimpleDateFormat("yy-MM-dd h:m:s");
                                try {
                                    java.util.Date occurredAtDate = formatter.parse(occurredAt);
                                    ps.setDate(1, new Date(occurredAtDate.getTime()));
                                    ps.setString(2, errorLevel);
                                    ps.setString(3, errorSource);
                                    ps.setString(4, errorDescription);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return ps.execute();
                            }
                        }
                    );
                }

            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
