package hexlet.code.controllers;

import hexlet.code.dto.BuildUrlPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.UrlCheckModel;
import hexlet.code.model.UrlModel;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepositiry;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;


@Slf4j
public class UrlController {

    public static void index(Context ctx) {
        BuildUrlPage page = new BuildUrlPage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("index.jte", Collections.singletonMap("page", page));


    }

    public static void addUrl(Context ctx) throws SQLException, MalformedURLException {
        String inputUrl = Objects.requireNonNull(ctx.formParam("url")).trim();

        URI parsedUrl;
        try {
            parsedUrl = new URI(inputUrl);
        } catch (Exception e)  {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect("/");
            return;
        }

        String name = String
                .format(
                        "%s://%s%s",
                        parsedUrl.getScheme(),
                        parsedUrl.getHost(),
                        parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()
                )
                .toLowerCase();

        if (UrlRepositiry.find(name).isPresent()) {
            ctx.sessionAttribute("flash", "URL уже существует");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect("/templates/urls");
            return;
        }

        UrlModel nameUrl = new UrlModel(name);
        UrlRepositiry.save(nameUrl);
        ctx.sessionAttribute("flash", "URL успешно добавлен!");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect("/templates/urls");
    }

    public static void urlList(Context ctx) throws SQLException {
        List<UrlModel> urls = UrlRepositiry.getEntities();
        Map<Long, UrlCheckModel> urlChecks = UrlCheckRepository.findLatestChecks();
        var page = new UrlsPage(urls, urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepositiry.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url № " + id + " not found"));

        List<UrlCheckModel> checks = UrlCheckRepository.find(id);
        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException {

        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepositiry.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url # " + urlId + " not found"));
        String check = url.getName();

        try {
            HttpResponse<String> response = Unirest.get(check).asString();

            String html = response.getBody();
            Document doc = Jsoup.parse(html);


            int status = response.getStatus();
            String title = doc.title();
            String h1 = null;
            String description = null;

            Element h1Element = doc.selectFirst("h1");
            if (h1Element != null) {
                h1 = h1Element.text();
            }

            Element metaDescriptionElement = doc.selectFirst("meta[name=description]");
            if (metaDescriptionElement != null) {
                description = metaDescriptionElement.attr("content");
            }
            var checkUrl = new UrlCheckModel(urlId, status, title, h1, description);
            UrlCheckRepository.saveCheck(checkUrl);
            ctx.sessionAttribute("flash", "URL успешно проверен");
            ctx.sessionAttribute("flashType", "success");
            show(ctx);
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            show(ctx);
        } catch (Exception e) {
            ctx.sessionAttribute("flash", e.getMessage());
            ctx.sessionAttribute("flashType", "danger");
            show(ctx);
        }
    }
}
