package sg.ic.umx.idg.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFieldsResponse {
    @JsonProperty("array")
    private List<String> fieldList;

    public UserFieldsResponse() {
        this.fieldList = new ArrayList<>();
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }
}
