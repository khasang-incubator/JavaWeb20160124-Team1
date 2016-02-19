package io.khasang.wlogs.model;

import io.khasang.wlogs.form.DeleteDataForm;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogManager {
    private static final int RECORDS_COUNT_TO_KEEP_ALIVE = 1000;

    private String tableName;
    private JdbcTemplate jdbcTemplate;
    private LogRepository logRepository;
    private TransactionTemplate sharedTransactionTemplate;
    private DeleteDataTable deleteDataTable;

    public void setDeleteDataTable(DeleteDataTable deleteDataTable) {
        this.deleteDataTable = deleteDataTable;
    }

    public void setSharedTransactionTemplate(TransactionTemplate sharedTransactionTemplate) {
        this.sharedTransactionTemplate = sharedTransactionTemplate;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int delete(DeleteDataForm deleteDataForm) throws Exception {
        // TODO: research validation of forms
        if (!deleteDataForm.getAgreeTerms()) {
            throw new Exception("You must read and accept terms before proceed.");
        }
        if (deleteDataForm.getDeleteAll()) {
            Integer recordsTotal = this.logRepository.countAll();
            this.deleteDataTable.deleteAll();
            return recordsTotal;
        }
        Integer intervalSize = deleteDataForm.getDateIntervalSize();
        DeleteDataForm.DateIntervalType intervalType = deleteDataForm.getDateIntervalType();
        if (deleteDataForm.isCriteriaEmpty()) {
            throw new Exception("You must use one of available filters.");
        }
        if (null != intervalType && (intervalSize == null || intervalSize < 1)) {
            throw new Exception("Required interval size.");
        }
        return this.deleteDataTable.deleteByCriteria(deleteDataForm.getErrorSource(), deleteDataForm.getErrorLevel(),
                                                     intervalType, intervalSize);
    }

    public int delete (Map<String, String[]> userData) throws Exception {
        Boolean userUnderstandTerms = this.getCheckBoxValueFormHelper("understand_terms", Boolean.FALSE, userData);
        if (!userUnderstandTerms) {
            throw new Exception("You must read and accept terms before proceed.");
        }
        Boolean totalAnnihilation = this.getCheckBoxValueFormHelper("total_annihilation", Boolean.FALSE, userData);
        if (totalAnnihilation) {
            Integer recordsTotal = this.logRepository.countAll();
            this.deleteDataTable.deleteAll();
            return recordsTotal;
        }
        Integer intervalSize = Integer.valueOf(this.getInputValueFormHelper("date_interval_size", Boolean.TRUE,userData));
        DeleteDataTable.DateIntervalType intervalType = DeleteDataTable.DateIntervalType.valueOf(
                this.getInputValueFormHelper("date_interval_id", Boolean.TRUE, userData));
        return this.deleteDataTable.deleteByDateInterval(intervalType, intervalSize);
    }

    // TODO: use jstl form tag or implement FormHelperClass to handle user request data.
    private String getInputValueFormHelper(String fieldName, Boolean required, Map<String, String[]> userData) throws Exception {
        String value = "";
        if (userData.containsKey(fieldName)) {
            String[] requestParam = userData.get(fieldName);
            value = requestParam[0];
        } else if (required) {
            throw new Exception("Missed required field: " + fieldName);
        }
        return value;
    }

    // TODO: use jstl form tag or implement FormHelperClass to handle user request data.
    private Boolean getCheckBoxValueFormHelper(String checkBoxName, Boolean required, Map<String, String[]> userData) throws Exception {
        Boolean userUnderstandTerms = Boolean.FALSE;
        if (userData.containsKey(checkBoxName)) {
            String[] requestParam = userData.get(checkBoxName);
            userUnderstandTerms = requestParam[0].equals("on") ? Boolean.TRUE : Boolean.FALSE;
        } else if (required) {
            throw new Exception("Missed required field: " + checkBoxName);
        }
        return userUnderstandTerms;
    }

    public int deleteAllExceptLastNRecords(final int recordsAmountToKeepAlive) throws DataAccessException {
        Integer logRecordsCountTotal = logRepository.countAll();
        if (recordsAmountToKeepAlive >= logRecordsCountTotal) {
            return 0;
        }
        final String temporaryTableName = tableName + "_" + UUID.randomUUID().toString().replace("-", "");
        sharedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.execute("CREATE TEMPORARY TABLE IF NOT EXISTS :temporaryTableName AS (SELECT * FROM :tableName ORDER BY occurred_at DESC LIMIT :limit)"
                        .replace(":tableName", tableName).replace(":temporaryTableName", temporaryTableName)
                        .replace(":limit", String.valueOf(recordsAmountToKeepAlive)));
                jdbcTemplate.execute("DELETE FROM :tableName".replace(":tableName", tableName));
                jdbcTemplate.execute("INSERT INTO :tableName SELECT * FROM :temporaryTableName"
                        .replace(":tableName", tableName).replace(":temporaryTableName", temporaryTableName));
                jdbcTemplate.execute("DROP TABLE :temporaryTableName".replace(":temporaryTableName", temporaryTableName));
            }
        });
        return logRecordsCountTotal - recordsAmountToKeepAlive;
    }

    public void createTable() {
        this.deleteDataTable.createTable();
    }

    // TODO: move away from here.... where??
    public void loadFixtures() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            // TODO: bad idea.... how to calculate lines in the file?
            LineNumberReader readerTmp = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource("dev.log").getFile())));
            Stream<String> linesTmp = readerTmp.lines();
            final Integer linesCount = (int) linesTmp.count();
            linesTmp.close();
            readerTmp.close();
            // -----
            LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource("dev.log").getFile())));
            final Stream<String> lines = reader.lines();
            final Iterator<String> linesIterator = lines.iterator();
            final Pattern pattern = Pattern.compile("^\\[(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\]\\s([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+):\\s(.*)");
            final String sql = "INSERT INTO :tableName(occurred_at, error_level, error_source, error_description) VALUES(?,?,?,?)"
                    .replace(":tableName", tableName);
            sharedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            if (linesIterator.hasNext()) {
                                String line = linesIterator.next();
                                Matcher matcher = pattern.matcher(line);
                                if (matcher.matches()) {
                                    final String occurredAt = matcher.group(1);
                                    final String errorSource = matcher.group(2);
                                    final String errorLevel = matcher.group(3);
                                    final String errorDescription = matcher.group(4);
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
                                }
                            }
                        }
                        public int getBatchSize() {
                            return linesCount;
                        }
                    });
                }
            });
            lines.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
