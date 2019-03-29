package datamodels.Cinema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "times", "title" })

// Cinema listing class
public class Listing implements Serializable
{
    @JsonProperty("times")
    private List<String> times = null;

    @JsonProperty("title")
    private String title;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("times")
    public List<String> getTimes() { return times; }

    @JsonProperty("times")
    public void setTimes(List<String> times) { this.times = times; }

    @JsonProperty("title")
    public String getTitle() { return title; }

    @JsonProperty("title")
    public void setTitle(String title) { this.title = title; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
}