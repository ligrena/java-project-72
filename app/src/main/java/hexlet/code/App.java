package hexlet.code;

import hexlet.code.controllers.TemplateEngineController;
import hexlet.code.controllers.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.service.BDService;
import hexlet.code.service.PathUrlService;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static Javalin getApp() throws SQLException {

        BaseRepository.dataSource = BDService.isConnectDB();

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(TemplateEngineController.createTemplateEngine()));
        });

        addRoutes(app);

        return app;
    }

    private static void addRoutes(Javalin app) {
        app.get(PathUrlService.rootPath(), UrlController::index);
        app.post(PathUrlService.urlsPost(), UrlController::addUrl);
        app.get(PathUrlService.urlsGet(), UrlController::urlList);
        app.get(PathUrlService.urlsPath("{id}"), UrlController::show);
        app.post(PathUrlService.check("{id}"), UrlController::check);
    }
}
