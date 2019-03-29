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
@JsonPropertyOrder({"vote_count", "id", "video", "vote_average", "title", "popularity", "poster_path", "original_language", "original_title", "genre_ids", "backdrop_path", "adult", "overview", "release_date" })
public class MovieSearchResults implements Serializable
{
    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("video")
    private Boolean video;

    @JsonProperty("vote_average")
    private Integer voteAverage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds = null;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("adult")
    private Boolean adult;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("vote_count")
    public Integer getVoteCount() { return voteCount; }

    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }
    public MovieSearchResults withVoteCount(Integer voteCount) { this.voteCount = voteCount; return this; }

    @JsonProperty("id")
    public Integer getId() { return id; }

    @JsonProperty("id")
    public void setId(Integer id) { this.id = id; }
    public MovieSearchResults withId(Integer id) { this.id = id; return this; }

    @JsonProperty("video")
    public Boolean getVideo() { return video; }

    @JsonProperty("video")
    public void setVideo(Boolean video) { this.video = video; }
    public MovieSearchResults withVideo(Boolean video) { this.video = video; return this; }

    @JsonProperty("vote_average")
    public Integer getVoteAverage() { return voteAverage; }

    @JsonProperty("vote_average")
    public void setVoteAverage(Integer voteAverage) { this.voteAverage = voteAverage; }
    public MovieSearchResults withVoteAverage(Integer voteAverage) { this.voteAverage = voteAverage; return this; }

    @JsonProperty("title")
    public String getTitle() { return title; }

    @JsonProperty("title")
    public void setTitle(String title) { this.title = title; }
    public MovieSearchResults withTitle(String title) { this.title = title; return this; }

    @JsonProperty("popularity")
    public Double getPopularity() { return popularity; }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) { this.popularity = popularity; }
    public MovieSearchResults withPopularity(Double popularity) { this.popularity = popularity; return this; }

    @JsonProperty("poster_path")
    public String getPosterPath() { return posterPath; }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public MovieSearchResults withPosterPath(String posterPath) { this.posterPath = posterPath; return this; }

    @JsonProperty("original_language")
    public String getOriginalLanguage() { return originalLanguage; }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }
    public MovieSearchResults withOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; return this; }

    @JsonProperty("original_title")
    public String getOriginalTitle() { return originalTitle; }

    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }
    public MovieSearchResults withOriginalTitle(String originalTitle) { this.originalTitle = originalTitle;return this; }

    @JsonProperty("genre_ids")
    public List<Integer> getGenreIds() { return genreIds; }

    @JsonProperty("genre_ids")
    public void setGenreIds(List<Integer> genreIds) { this.genreIds = genreIds; }

    public MovieSearchResults withGenreIds(List<Integer> genreIds) { this.genreIds = genreIds; return this; }

    @JsonProperty("backdrop_path")
    public String getBackdropPath() { return backdropPath; }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }
    public MovieSearchResults withBackdropPath(String backdropPath) { this.backdropPath = backdropPath; return this; }

    @JsonProperty("adult")
    public Boolean getAdult() { return adult; }

    @JsonProperty("adult")
    public void setAdult(Boolean adult) { this.adult = adult; }
    public MovieSearchResults withAdult(Boolean adult) { this.adult = adult;return this; }

    @JsonProperty("overview")
    public String getOverview() { return overview; }

    @JsonProperty("overview")
    public void setOverview(String overview) { this.overview = overview; }
    public MovieSearchResults withOverview(String overview) { this.overview = overview; return this; }

    @JsonProperty("release_date")
    public String getReleaseDate() { return releaseDate; }

    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public MovieSearchResults withReleaseDate(String releaseDate) { this.releaseDate = releaseDate; return this; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() { return this.additionalProperties; }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); }
    public MovieSearchResults withAdditionalProperty(String name, Object value) { this.additionalProperties.put(name, value); return this; }

    @Override
    @NonNull
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("voteCount",                voteCount)
                .append("id",                       id)
                .append("video",                    video)
                .append("voteAverage",              voteAverage)
                .append("title",                    title)
                .append("popularity",               popularity)
                .append("posterPath",               posterPath)
                .append("originalLanguage",         originalLanguage)
                .append("originalTitle",            originalTitle)
                .append("genreIds",                 genreIds)
                .append("backdropPath",             backdropPath)
                .append("adult",                    adult)
                .append("overview",                 overview)
                .append("releaseDate",              releaseDate)
                .append("additionalProperties",     additionalProperties)
                .toString();
    }
}
