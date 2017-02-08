package uk.gov.ons.api.model;

import java.time.Instant;

public class TimeseriesDescription {
    private String title;
    private Contact contact;
    private Instant releaseDate;
    private String nextRelease;
    private String datasetId;
    private String datasetUri;
    private String cdid;
    private String unit;
    private String preUnit;
    private String source;
    private String date;
    private String number;
    private String sampleSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getNextRelease() {
        return nextRelease;
    }

    public void setNextRelease(String nextRelease) {
        this.nextRelease = nextRelease;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetUri() {
        return datasetUri;
    }

    public void setDatasetUri(String datasetUri) {
        this.datasetUri = datasetUri;
    }

    public String getCdid() {
        return cdid;
    }

    public void setCdid(String cdid) {
        this.cdid = cdid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPreUnit() {
        return preUnit;
    }

    public void setPreUnit(String preUnit) {
        this.preUnit = preUnit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeseriesDescription that = (TimeseriesDescription) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (nextRelease != null ? !nextRelease.equals(that.nextRelease) : that.nextRelease != null) return false;
        if (datasetId != null ? !datasetId.equals(that.datasetId) : that.datasetId != null) return false;
        if (datasetUri != null ? !datasetUri.equals(that.datasetUri) : that.datasetUri != null) return false;
        if (cdid != null ? !cdid.equals(that.cdid) : that.cdid != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (preUnit != null ? !preUnit.equals(that.preUnit) : that.preUnit != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return sampleSize != null ? sampleSize.equals(that.sampleSize) : that.sampleSize == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = title != null ? title.hashCode() : 0;
        result = prime * result + (contact != null ? contact.hashCode() : 0);
        result = prime * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = prime * result + (nextRelease != null ? nextRelease.hashCode() : 0);
        result = prime * result + (datasetId != null ? datasetId.hashCode() : 0);
        result = prime * result + (datasetUri != null ? datasetUri.hashCode() : 0);
        result = prime * result + (cdid != null ? cdid.hashCode() : 0);
        result = prime * result + (unit != null ? unit.hashCode() : 0);
        result = prime * result + (preUnit != null ? preUnit.hashCode() : 0);
        result = prime * result + (source != null ? source.hashCode() : 0);
        result = prime * result + (date != null ? date.hashCode() : 0);
        result = prime * result + (number != null ? number.hashCode() : 0);
        result = prime * result + (sampleSize != null ? sampleSize.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeseriesDescription{" +
                "title='" + title + '\'' +
                ", contact=" + contact +
                ", releaseDate=" + releaseDate +
                ", nextRelease=" + nextRelease +
                ", datasetId='" + datasetId + '\'' +
                ", datasetUri='" + datasetUri + '\'' +
                ", cdid='" + cdid + '\'' +
                ", unit='" + unit + '\'' +
                ", preUnit='" + preUnit + '\'' +
                ", source='" + source + '\'' +
                ", date='" + date + '\'' +
                ", number='" + number + '\'' +
                ", sampleSize='" + sampleSize + '\'' +
                '}';
    }
}
