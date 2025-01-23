package hexlet.code.controllers;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

public class TemplateEngineController {

    public static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = TemplateEngineController.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

        return templateEngine;
    }
}
