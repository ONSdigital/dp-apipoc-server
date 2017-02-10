package uk.gov.ons.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Records {
    @SerializedName("start_index")
    private Integer startIndex;
    @SerializedName("items_per_page")
    private Integer itemsPerPage;
    @SerializedName("total_items")
    private Integer totalItems;
    private List<Record> items;

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

    public List<Record> getItems() {
        return items;
    }

    public void setItems(List<Record> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Records datasets = (Records) o;

        if (!startIndex.equals(datasets.startIndex)) return false;
        if (!itemsPerPage.equals(datasets.itemsPerPage)) return false;
        if (!totalItems.equals(datasets.totalItems)) return false;
        return items.equals(datasets.items);
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
        return "Datasets{" +
                "startIndex=" + startIndex +
                ", itemsPerPage=" + itemsPerPage +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}
