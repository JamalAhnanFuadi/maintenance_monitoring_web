package sg.ic.umx.idg.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty("name")
    private String name;
    @JsonProperty("names")
    private List<String> names;
    @JsonProperty("dataType")
    private String dataType;

    public Item() {
        // Empty Constructor
    }

    public List<String> getNames() {
        return names;
    }

    public String getDataType() {
        return dataType;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
