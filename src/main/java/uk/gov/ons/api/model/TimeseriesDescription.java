package uk.gov.ons.api.model;

import java.time.Instant;
import java.util.Date;

public class TimeseriesDescription {
    private String title;
    private Contact contact;
    private Instant releaseDate;
    private Date nextRelease;
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

    public Date getNextRelease() {
        return nextRelease;
    }

    public void setNextRelease(Date nextRelease) {
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

        if (!title.equals(that.title)) return false;
        if (!contact.equals(that.contact)) return false;
        if (!releaseDate.equals(that.releaseDate)) return false;
        if (!nextRelease.equals(that.nextRelease)) return false;
        if (!datasetId.equals(that.datasetId)) return false;
        if (!datasetUri.equals(that.datasetUri)) return false;
        if (!cdid.equals(that.cdid)) return false;
        if (!unit.equals(that.unit)) return false;
        if (!preUnit.equals(that.preUnit)) return false;
        if (!source.equals(that.source)) return false;
        if (!date.equals(that.date)) return false;
        if (!number.equals(that.number)) return false;
        return sampleSize.equals(that.sampleSize);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = title.hashCode();
        result = prime * result + contact.hashCode();
        result = prime * result + releaseDate.hashCode();
        result = prime * result + nextRelease.hashCode();
        result = prime * result + datasetId.hashCode();
        result = prime * result + datasetUri.hashCode();
        result = prime * result + cdid.hashCode();
        result = prime * result + unit.hashCode();
        result = prime * result + preUnit.hashCode();
        result = prime * result + source.hashCode();
        result = prime * result + date.hashCode();
        result = prime * result + number.hashCode();
        result = prime * result + sampleSize.hashCode();
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
