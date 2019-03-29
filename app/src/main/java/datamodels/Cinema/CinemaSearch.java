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
@JsonPropertyOrder({ "status", "postcode", "cinemas" })

// Cinema search class
public class CinemaSearch implements Serializable
{
    @JsonProperty("status")
    private String status;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("cinemas")
    private List<Cinema> cinemas = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() { return status; }

    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

    @JsonProperty("postcode")
    public String getPostcode() { return postcode; }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) { this.postcode = postcode; }

    @JsonProperty("cinemas")
    public List<Cinema> getCinemas() { return cinemas; }

    @JsonProperty("cinemas")
    public void setCinemas(List<Cinema> cinemas) { this.cinemas = cinemas; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
}