package com.ben;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person implements Serializable {
    private static final long serialVersionUID = 1091853598015252017L;

    @Id
    private String id;

    @NotNull
    private String name;
}
