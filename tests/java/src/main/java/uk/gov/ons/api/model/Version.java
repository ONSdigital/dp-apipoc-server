package uk.gov.ons.api.model;

import java.time.Instant;

public class Version {
    private String uri;
    private Instant updateDate;
    private String correctionNotice;
    private String label;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getCorrectionNotice() {
        return correctionNotice;
    }

    public void setCorrectionNotice(String correctionNotice) {
        this.correctionNotice = correctionNotice;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (uri != null ? !uri.equals(version.uri) : version.uri != null) return false;
        if (updateDate != null ? !updateDate.equals(version.updateDate) : version.updateDate != null) return false;
        if (correctionNotice != null ? !correctionNotice.equals(version.correctionNotice) : version.correctionNotice != null)
            return false;
        return label != null ? label.equals(version.label) : version.label == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = uri != null ? uri.hashCode() : 0;
        result = prime * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = prime * result + (correctionNotice != null ? correctionNotice.hashCode() : 0);
        result = prime * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Version{" +
                "uri='" + uri + '\'' +
                ", updateDate=" + updateDate +
                ", correctionNotice='" + correctionNotice + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}