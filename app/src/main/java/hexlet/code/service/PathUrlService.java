package hexlet.code.service;

public class PathUrlService {

    public static String rootPath() {
        return "/";
    }

    public static String urlsPost() {
        return "/templates/urls";
    }

    public static String urlsGet() {
        return "/templates/urls";
    }

    public static String urlsPath(String id) {
        return "/templates/urls/" + id;
    }

    public static String check(String id) {
        return "templates/urls/" + id + "/checks";
    }
}
