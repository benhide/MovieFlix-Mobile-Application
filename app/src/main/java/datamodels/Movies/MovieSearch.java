package datamodels.Movies;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

PLAIN OLD JAVA OBJECTS FROM JSON_SCHEMA
- http://www.jsonschema2pojo.org/
*/

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "page", "total_results", "total_pages", "results"})
public class MovieSearch implements Serializable
{
    @JsonProperty("page")
    private Integer page;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("results")
    private List<MovieSearchResults> results = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("page")
    public Integer getPage() { return page; }

    @JsonProperty("page")
    public void setPage(Integer page) { this.page = page; }

    public MovieSearch withPage(Integer page) { this.page = page; return this; }

    @JsonProperty("total_results")
    public Integer getTotalResults() { return totalResults; }

    @JsonProperty("total_results")
    public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }
    public MovieSearch withTotalResults(Integer totalResults) { this.totalResults = totalResults; return this; }

    @JsonProperty("total_pages")
    public Integer getTotalPages() { return totalPages; }

    @JsonProperty("total_pages")
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }
    public MovieSearch withTotalPages(Integer totalPages) { this.totalPages = totalPages; return this; }

    @JsonProperty("results")
    public List<MovieSearchResults> getResults() { return results; }

    @JsonProperty("results")
    public void setResults(List<MovieSearchResults> results) { this.results = results; }
    public MovieSearch withResults(List<MovieSearchResults> results) { this.results = results; return this; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
    public MovieSearch withAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); return this; }

    @Override
    @NonNull
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("page",                     page)
                .append("totalResults",             totalResults)
                .append("totalPages",               totalPages)
                .append("results",                  results)
                .append("additionalProperties",     additionalProperties)
                .toString();
    }
}