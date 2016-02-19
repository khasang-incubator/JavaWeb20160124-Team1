package io.khasang.wlogs.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashMap;

public class DeleteDataForm {
    public enum DateIntervalType {
        // TODO: move strings to resources
        DAY("День"), WEEK("Неделя"), MONTH("Месяц"), YEAR("Год");

        private String view;

        private DateIntervalType(String view) {
            this.view = view;
        }
    }

    private Boolean agreeTerms = false;
    private Boolean deleteAll = false;
    private String errorSource;
    private String errorLevel;
    private Integer dateIntervalSize = 0;
    private DateIntervalType dateIntervalType;

    public LinkedHashMap<String, String> getDateIntervalTypes() {
        LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();
        for (DateIntervalType type : DateIntervalType.values()) {
            types.put(type.name(), type.view);
        }
        return types;
    }

    public Integer getDateIntervalSize() {
        return dateIntervalSize;
    }

    public void setDateIntervalSize(Integer dateIntervalSize) {
        this.dateIntervalSize = dateIntervalSize;
    }

    public DateIntervalType getDateIntervalType() {
        return dateIntervalType;
    }

    public void setDateIntervalType(DateIntervalType dateIntervalType) {
        this.dateIntervalType = dateIntervalType;
    }

    public Boolean getAgreeTerms() {
        return agreeTerms;
    }

    public void setAgreeTerms(Boolean agreeTerms) {
        this.agreeTerms = agreeTerms;
    }

    public Boolean getDeleteAll() {
        return deleteAll;
    }

    public void setDeleteAll(Boolean deleteAll) {
        this.deleteAll = deleteAll;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    // TODO: remove method after form validator research
    public Boolean isCriteriaEmpty() {
        return null == dateIntervalType && null == errorLevel && null == errorSource;
    }
}
