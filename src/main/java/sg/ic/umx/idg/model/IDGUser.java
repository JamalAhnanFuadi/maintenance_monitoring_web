package sg.ic.umx.idg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IDGUser {
    @JsonProperty("name")
    private Item name;

    @JsonProperty("fields")
    private List<NameValuePair> fields;

    private Map<String, String> attributeMap;

    public IDGUser() {
        attributeMap = new HashMap<>();
    }

    public Item getName() {
        return name;
    }

    public List<NameValuePair> getFields() {
        return fields;
    }

    public void setName(Item name) {
        this.name = name;
    }

    public void setFields(List<NameValuePair> fields) {
        this.fields = fields;
        fields.forEach(pair -> {
            if (pair.getValue().length() > 0) {
                attributeMap.put(pair.getName(), pair.getValue());
            }
        });
    }

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
