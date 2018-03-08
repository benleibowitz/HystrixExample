package com.ben;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonServiceURLConfig {
    private String host;

    private int port;
}
