package datamodels.Cinema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

PLAIN OLD JAVA OBJECTS FROM JSON_SCHEMA
- http://www.jsonschema2pojo.org/
*/

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"distance", "name", "id"})
public class Cinema implements Serializable
{
    @JsonProperty("distance")
    private Double distance;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("distance")
    public Double getDistance() { return distance; }

    @JsonProperty("distance")
    public void setDistance(Double distance) { this.distance = distance; }

    @JsonProperty("name")
    public String getName() { return name; }

    @JsonProperty("name")
    public void setName(String name) { this.name = name; }

    @JsonProperty("id")
    public String getId() { return id; }

    @JsonProperty("id")
    public void setId(String id) { this.id = id; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
}

