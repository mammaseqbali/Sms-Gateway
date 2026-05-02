package gateway.template;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;

import java.nio.file.Path;

public final class TemplateEngineProvider
{
    private static TemplateEngine engine;

    private TemplateEngineProvider()
    {}

    public static TemplateEngine getEngine()
    {
        if (engine == null)
        {
            synchronized (TemplateEngineProvider.class)
            {
                if (engine == null)
                {
                    Path templatesPath = TemplateConfig.TemplatesPath();
                    var resolver = new DirectoryCodeResolver(templatesPath);

                    engine = TemplateEngine.create(
                            resolver,
                            TemplateConfig.classDirectory(),
                            ContentType.Html,
                            TemplateEngineProvider.class.getClassLoader()
                    );
                }
            }
        }
        return engine;
    }
}