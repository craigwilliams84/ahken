package io.kauri.ahken.script.reader;

import io.kauri.ahken.AhkenSettings;
import io.kauri.ahken.model.RawScript;
import io.kauri.ahken.script.exception.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A ScriptReader implementation that reads scripts from the configured local directory.
 *
 * The local directory can be specified by setting the ahken.scripts.location property.
 *
 * @author Craig Williams
 */
@Component
@AllArgsConstructor
public class FileScriptReader implements ScriptReader {

    private AhkenSettings settings;

    @Override
    public List<RawScript> readScriptStreams() {

        try {
            try (Stream<Path> paths = Files.walk(Paths.get(settings.getScriptsLocation()))) {
                return paths
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .map((file) -> fileToRawScript(file))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new ScriptException("Script location cannot be read", e);
        }
    }

    private RawScript fileToRawScript(File file) {
        try {
            return new RawScript(file.getName(), new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ScriptException(e);
        }
    }
}
