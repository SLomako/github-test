package ru.lomakosv.tests.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PutFileContentsRepositoryRequest {

    private String message;
    private String content;
}
