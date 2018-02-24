package com.ben;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Person {
    @Id
    private long id;

    private String name;
}
