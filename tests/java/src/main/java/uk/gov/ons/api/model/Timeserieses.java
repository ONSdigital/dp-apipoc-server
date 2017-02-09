package uk.gov.ons.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Timeserieses {
    @SerializedName("start_index")
    private Integer startIndex;
    @SerializedName("items_per_page")
    private Integer itemsPerPage;
    @SerializedName("total_items")
    private Integer totalItems;
    private List<Timeseries> items;

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Timeseries> getItems() {
        return items;
    }

    public void setItems(List<Timeseries> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timeserieses that = (Timeserieses) o;

        if (!startIndex.equals(that.startIndex)) return false;
        if (!itemsPerPage.equals(that.itemsPerPage)) return false;
        if (!totalItems.equals(that.totalItems)) return false;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = startIndex.hashCode();
        result = prime * result + itemsPerPage.hashCode();
        result = prime * result + totalItems.hashCode();
        result = prime * result + items.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Timeserieses{" +
                "startIndex=" + startIndex +
                ", itemsPerPage=" + itemsPerPage +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}
