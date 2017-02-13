package uk.gov.ons.api.model;

import java.time.Instant;

public class Period {
    private String date;
    private String value;
    private String label;
    private String year;
    private String month;
    private String quarter;
    private String sourceDataset;
    private Instant updateDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getSourceDataset() {
        return sourceDataset;
    }

    public void setSourceDataset(String sourceDataset) {
        this.sourceDataset = sourceDataset;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (date != null ? !date.equals(period.date) : period.date != null) return false;
        if (value != null ? !value.equals(period.value) : period.value != null) return false;
        if (label != null ? !label.equals(period.label) : period.label != null) return false;
        if (year != null ? !year.equals(period.year) : period.year != null) return false;
        if (month != null ? !month.equals(period.month) : period.month != null) return false;
        if (quarter != null ? !quarter.equals(period.quarter) : period.quarter != null) return false;
        if (sourceDataset != null ? !sourceDataset.equals(period.sourceDataset) : period.sourceDataset != null)
            return false;
        return updateDate != null ? updateDate.equals(period.updateDate) : period.updateDate == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = date != null ? date.hashCode() : 0;
        result = prime * result + (value != null ? value.hashCode() : 0);
        result = prime * result + (label != null ? label.hashCode() : 0);
        result = prime * result + (year != null ? year.hashCode() : 0);
        result = prime * result + (month != null ? month.hashCode() : 0);
        result = prime * result + (quarter != null ? quarter.hashCode() : 0);
        result = prime * result + (sourceDataset != null ? sourceDataset.hashCode() : 0);
        result = prime * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", quarter='" + quarter + '\'' +
                ", sourceDataset='" + sourceDataset + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}