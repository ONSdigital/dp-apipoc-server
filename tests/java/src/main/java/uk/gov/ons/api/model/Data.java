package uk.gov.ons.api.model;

import java.util.List;

public class Data {
    private List<Period> years;
    private List<Period> quarters;
    private List<Period> months;
    private List<Relation> relatedDatasets;
    private List<Relation> relatedDocuments;
    private List<Relation> relatedData;
    private List<Version> versions;
    private String type;
    private String uri;
    private Description description;

    public List<Period> getYears() {
        return years;
    }

    public void setYears(List<Period> years) {
        this.years = years;
    }

    public List<Period> getQuarters() {
        return quarters;
    }

    public void setQuarters(List<Period> quarters) {
        this.quarters = quarters;
    }

    public List<Period> getMonths() {
        return months;
    }

    public void setMonths(List<Period> months) {
        this.months = months;
    }

    public List<Relation> getRelatedDatasets() {
        return relatedDatasets;
    }

    public void setRelatedDatasets(List<Relation> relatedDatasets) {
        this.relatedDatasets = relatedDatasets;
    }

    public List<Relation> getRelatedDocuments() {
        return relatedDocuments;
    }

    public void setRelatedDocuments(List<Relation> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }

    public List<Relation> getRelatedData() {
        return relatedData;
    }

    public void setRelatedData(List<Relation> relatedData) {
        this.relatedData = relatedData;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (years != null ? !years.equals(data.years) : data.years != null) return false;
        if (quarters != null ? !quarters.equals(data.quarters) : data.quarters != null) return false;
        if (months != null ? !months.equals(data.months) : data.months != null) return false;
        if (relatedDatasets != null ? !relatedDatasets.equals(data.relatedDatasets) : data.relatedDatasets != null)
            return false;
        if (relatedDocuments != null ? !relatedDocuments.equals(data.relatedDocuments) : data.relatedDocuments != null)
            return false;
        if (relatedData != null ? !relatedData.equals(data.relatedData) : data.relatedData != null) return false;
        if (versions != null ? !versions.equals(data.versions) : data.versions != null) return false;
        if (type != null ? !type.equals(data.type) : data.type != null) return false;
        if (uri != null ? !uri.equals(data.uri) : data.uri != null) return false;
        return description != null ? description.equals(data.description) : data.description == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = years != null ? years.hashCode() : 0;
        result = prime * result + (quarters != null ? quarters.hashCode() : 0);
        result = prime * result + (months != null ? months.hashCode() : 0);
        result = prime * result + (relatedDatasets != null ? relatedDatasets.hashCode() : 0);
        result = prime * result + (relatedDocuments != null ? relatedDocuments.hashCode() : 0);
        result = prime * result + (relatedData != null ? relatedData.hashCode() : 0);
        result = prime * result + (versions != null ? versions.hashCode() : 0);
        result = prime * result + (type != null ? type.hashCode() : 0);
        result = prime * result + (uri != null ? uri.hashCode() : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "years=" + years +
                ", quarters=" + quarters +
                ", months=" + months +
                ", relatedDatasets=" + relatedDatasets +
                ", relatedDocuments=" + relatedDocuments +
                ", relatedData=" + relatedData +
                ", versions=" + versions +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", description=" + description +
                '}';
    }
}
