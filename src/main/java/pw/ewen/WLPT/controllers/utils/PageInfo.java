package pw.ewen.WLPT.controllers.utils;

/**
 * created by wenliang on 2021/3/21
 * 作为分页查询时候匹配参数值的辅助类
 */
public class PageInfo {
    int pageIndex = 0;
    int pageSize = 20;
    String sortDirection = "ASC";
    String sortField = "";
    String filter = "";

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
