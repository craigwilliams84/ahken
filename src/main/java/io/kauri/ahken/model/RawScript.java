package io.kauri.ahken.model;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RawScript {

    String identifier;

    InputStream stream;
}
