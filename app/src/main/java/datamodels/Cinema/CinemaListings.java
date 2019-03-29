package datamodels.Cinema;

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
@JsonPropertyOrder({ "status", "listings", "day" })

// Cinema listings class
public class CinemaListings
{
    @JsonProperty("status")
    private String status;

    @JsonProperty("listings")
    private List<Listing> listings = null;

    @JsonProperty("day")
    private String day;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() { return status; }

    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

    @JsonProperty("listings")
    public List<Listing> getListings() { return listings; }

    @JsonProperty("listings")
    public void setListings(List<Listing> listings) { this.listings = listings; }

    @JsonProperty("day")
    public String getDay() { return day; }

    @JsonProperty("day")
    public void setDay(String day) { this.day = day; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
}


