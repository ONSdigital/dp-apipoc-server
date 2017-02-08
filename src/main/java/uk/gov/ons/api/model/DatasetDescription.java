package uk.gov.ons.api.model;

import java.time.Instant;
import java.util.List;

public class DatasetDescription {
    private String title;
    private String summary;
    private List<String> keywords;
    private String metaDescription;
    private Boolean nationalStatistic;
    private Contact contact;
    private Instant releaseDate;
    private String nextRelease;
    private String edition;
    private String datasetId;
    private String unit;
    private String preUnit;
    private String source;
    private String versionLabel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Boolean getNationalStatistic() {
        return nationalStatistic;
    }

    public void setNationalStatistic(Boolean nationalStatistic) {
        this.nationalStatistic = nationalStatistic;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
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

    public String getVersionLabel() {
        return versionLabel;
    }

    public void setVersionLabel(String versionLabel) {
        this.versionLabel = versionLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasetDescription that = (DatasetDescription) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (keywords != null ? !keywords.equals(that.keywords) : that.keywords != null) return false;
        if (metaDescription != null ? !metaDescription.equals(that.metaDescription) : that.metaDescription != null)
            return false;
        if (nationalStatistic != null ? !nationalStatistic.equals(that.nationalStatistic) : that.nationalStatistic != null)
            return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (nextRelease != null ? !nextRelease.equals(that.nextRelease) : that.nextRelease != null) return false;
        if (edition != null ? !edition.equals(that.edition) : that.edition != null) return false;
        if (datasetId != null ? !datasetId.equals(that.datasetId) : that.datasetId != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (preUnit != null ? !preUnit.equals(that.preUnit) : that.preUnit != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return versionLabel != null ? versionLabel.equals(that.versionLabel) : that.versionLabel == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = title != null ? title.hashCode() : 0;
        result = prime * result + (summary != null ? summary.hashCode() : 0);
        result = prime * result + (keywords != null ? keywords.hashCode() : 0);
        result = prime * result + (metaDescription != null ? metaDescription.hashCode() : 0);
        result = prime * result + (nationalStatistic != null ? nationalStatistic.hashCode() : 0);
        result = prime * result + (contact != null ? contact.hashCode() : 0);
        result = prime * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = prime * result + (nextRelease != null ? nextRelease.hashCode() : 0);
        result = prime * result + (edition != null ? edition.hashCode() : 0);
        result = prime * result + (datasetId != null ? datasetId.hashCode() : 0);
        result = prime * result + (unit != null ? unit.hashCode() : 0);
        result = prime * result + (preUnit != null ? preUnit.hashCode() : 0);
        result = prime * result + (source != null ? source.hashCode() : 0);
        result = prime * result + (versionLabel != null ? versionLabel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DatasetDescription{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", keywords=" + keywords +
                ", metaDescription='" + metaDescription + '\'' +
                ", nationalStatistic=" + nationalStatistic +
                ", contact=" + contact +
                ", releaseDate=" + releaseDate +
                ", nextRelease=" + nextRelease +
                ", edition='" + edition + '\'' +
                ", datasetId='" + datasetId + '\'' +
                ", unit='" + unit + '\'' +
                ", preUnit='" + preUnit + '\'' +
                ", source='" + source + '\'' +
                ", versionLabel='" + versionLabel + '\'' +
                '}';
    }
}
