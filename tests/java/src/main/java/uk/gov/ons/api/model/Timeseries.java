package uk.gov.ons.api.model;

import java.util.List;

public class Timeseries {
    private String uri;
    private String type;
    private Description description;
    private List<String> searchBoost;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public List<String> getSearchBoost() {
        return searchBoost;
    }

    public void setSearchBoost(List<String> searchBoost) {
        this.searchBoost = searchBoost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timeseries that = (Timeseries) o;

        if (!uri.equals(that.uri)) return false;
        if (!type.equals(that.type)) return false;
        if (!description.equals(that.description)) return false;
        return searchBoost.equals(that.searchBoost);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = uri.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + description.hashCode();
        result = prime * result + searchBoost.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Timeseries{" +
                "uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                ", description=" + description +
                ", searchBoost=" + searchBoost +
                '}';
    }
}
