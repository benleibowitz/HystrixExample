package com.ben;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 1091853598015252017L;

    @Id
    @JsonProperty("id")
    private String id;

    @NotNull
    @JsonProperty("name")
    private String name;
}
