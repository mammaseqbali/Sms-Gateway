package gateway.template;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public final class TemplateConfig
{
    private static final Path DEFAULT_TEMPLATES = Path.of("gateway","src","gateway","template","templates");
    private static final Path DEFAULT_CLASS_DIR = Path.of("target","jte-classes");

    private TemplateConfig() {}

    public static Path TemplatesPath()
    {
        String prop = System.getProperty("jte.templates");
        if (prop != null && !prop.isBlank()) return Path.of(prop);
        if (Files.isDirectory(DEFAULT_TEMPLATES)) return DEFAULT_TEMPLATES.toAbsolutePath();
        Path alt = Path.of("src","main","jte");
        if (Files.isDirectory(alt)) return alt.toAbsolutePath();
        return DEFAULT_TEMPLATES.toAbsolutePath();
    }

    public static Path classDirectory()
    {
        try
        {
            if (!Files.exists(DEFAULT_CLASS_DIR)) Files.createDirectories(DEFAULT_CLASS_DIR);
            return DEFAULT_CLASS_DIR.toAbsolutePath();
        }
        catch (IOException e)
        {
            try
            {
                return Files.createTempDirectory("jte-classes").toAbsolutePath();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Failed to create jte class directory", ex);
            }
        }
    }
}