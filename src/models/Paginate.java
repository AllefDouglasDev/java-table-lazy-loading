package models;

import java.util.List;

public class Paginate<T> {
    private int totalDataSize;
    private int page;
    private int perPage;
    private List<T> data;

    public Paginate (int totalDataSize, int page, int perPage, List<T> data) {
        this.totalDataSize = totalDataSize;
        this.page = page;
        this.perPage = perPage;
        this.data = data;
    }

    public int getTotalPages () {
        return (int) Math.ceil(totalDataSize / (double) perPage);
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

    public int getTotalDataSize () {
        return totalDataSize;
    }

    public void setTotalDataSize (int totalDataSize) {
        this.totalDataSize = totalDataSize;
    }

    public static int calculateSizeToLoad (int perPage, int page, int usersSize) {
        int sizeToLoad = perPage;
        if ((page * perPage) > usersSize) {
            sizeToLoad = perPage - ((page * perPage) - usersSize);
        }
        return sizeToLoad;
    }
}
