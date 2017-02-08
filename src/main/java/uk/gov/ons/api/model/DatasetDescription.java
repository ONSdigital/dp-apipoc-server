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

        if (!title.equals(that.title)) return false;
        if (!summary.equals(that.summary)) return false;
        if (!keywords.equals(that.keywords)) return false;
        if (!metaDescription.equals(that.metaDescription)) return false;
        if (!nationalStatistic.equals(that.nationalStatistic)) return false;
        if (!contact.equals(that.contact)) return false;
        if (!releaseDate.equals(that.releaseDate)) return false;
        if (!nextRelease.equals(that.nextRelease)) return false;
        if (!edition.equals(that.edition)) return false;
        if (!datasetId.equals(that.datasetId)) return false;
        if (!unit.equals(that.unit)) return false;
        if (!preUnit.equals(that.preUnit)) return false;
        if (!source.equals(that.source)) return false;
        return versionLabel.equals(that.versionLabel);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = title.hashCode();
        result = prime * result + summary.hashCode();
        result = prime * result + keywords.hashCode();
        result = prime * result + metaDescription.hashCode();
        result = prime * result + nationalStatistic.hashCode();
        result = prime * result + contact.hashCode();
        result = prime * result + releaseDate.hashCode();
        result = prime * result + nextRelease.hashCode();
        result = prime * result + edition.hashCode();
        result = prime * result + datasetId.hashCode();
        result = prime * result + unit.hashCode();
        result = prime * result + preUnit.hashCode();
        result = prime * result + source.hashCode();
        result = prime * result + versionLabel.hashCode();
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
