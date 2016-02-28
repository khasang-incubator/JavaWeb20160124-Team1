package io.khasang.wlogs.model;

import io.khasang.wlogs.form.DeleteDataForm;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogManager{
    private static final int RECORDS_COUNT_TO_KEEP_ALIVE = 1000;

    private String tableName;
    @Autowired
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
        Integer recordsToKeepAliveCount = deleteDataForm.getRecordsToKeepAliveCount();
        if (deleteDataForm.isCriteriaEmpty()) {
            throw new Exception("You must use one of available filters.");
        } else if (null != recordsToKeepAliveCount && recordsToKeepAliveCount > 0) {
                return this.deleteDataTable.deleteAllExceptLastNRecords(recordsToKeepAliveCount);
        } else if (null != intervalType) {
            if (intervalSize == null || intervalSize < 1) {
                throw new Exception("Required interval size.");
            }
        }
        return this.deleteDataTable.deleteByCriteria(deleteDataForm.getErrorSource(), deleteDataForm.getErrorLevel(),
                intervalType, intervalSize);
    }

    public void createTable() {
        this.deleteDataTable.createTable();
    }

    // TODO: move away from here.... where??
    public void loadFixtures() {
        String fileName = "log.sample";
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            // TODO: bad idea.... how to calculate lines in the file?
            LineNumberReader readerTmp = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource(fileName).getFile())));
            Stream<String> linesTmp = readerTmp.lines();
            final Integer linesCount = (int) linesTmp.count();
            linesTmp.close();
            readerTmp.close();
            // -----
            LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(loader.getResource(fileName).getFile())));
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

