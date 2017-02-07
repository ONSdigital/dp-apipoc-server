package uk.gov.ons.api.model;

import java.time.Instant;

public class DatasetDescription {
    private Instant releaseDate;
    private String edition;
    private String unit;
    private String preUnit;
    private  String source;
    private String versionLabel;

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
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

        if (!releaseDate.equals(that.releaseDate)) return false;
        if (!edition.equals(that.edition)) return false;
        if (!unit.equals(that.unit)) return false;
        if (!preUnit.equals(that.preUnit)) return false;
        if (!source.equals(that.source)) return false;
        return versionLabel.equals(that.versionLabel);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = releaseDate.hashCode();
        result = prime * result + edition.hashCode();
        result = prime * result + unit.hashCode();
        result = prime * result + preUnit.hashCode();
        result = prime * result + source.hashCode();
        result = prime * result + versionLabel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "releaseDate=" + releaseDate +
                ", edition='" + edition + '\'' +
                ", unit='" + unit + '\'' +
                ", preUnit='" + preUnit + '\'' +
                ", source='" + source + '\'' +
                ", versionLabel='" + versionLabel + '\'' +
                '}';
    }
}
