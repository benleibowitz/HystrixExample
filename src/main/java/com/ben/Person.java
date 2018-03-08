package com.ben;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Entity
public class Person {
    @Id
    private String id;

    @NotNull
    private String name;
}
