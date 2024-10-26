package robotic.system.util.filter;

public class FilterRequest {
    private String column;
    private String filterType; 
    private String value;

    public FilterRequest() {}

    public FilterRequest(String column, String filterType, String value) {
        this.column = column;
        this.filterType = filterType;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
