package models;

import java.util.List;

public class Paginate<T> {
    private int totalPages;
    private int page;
    private int perPage;
    private List<T> data;

    public Paginate (int total, int page, int perPage, List<T> data) {
        this.totalPages = total;
        this.page = page;
        this.perPage = perPage;
        this.data = data;
    }

    public int getTotalPages () {
        return totalPages;
    }

    public void setTotalPages (int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage () {
        return page;
    }

    public void setPage (int page) {
        this.page = page;
    }

    public int getPerPage () {
        return perPage;
    }

    public void setPerPage (int perPage) {
        this.perPage = perPage;
    }

    public List<T> getData () {
        return data;
    }

    public void setData (List<T> data) {
        this.data = data;
    }
}
