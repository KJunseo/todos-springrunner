package practice.sr.todoapp.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import practice.sr.todoapp.web.model.SiteProperties;

@ControllerAdvice
public class GlobalControllerAdvice {

    private SiteProperties siteProperties;

    public GlobalControllerAdvice(SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }

    @ModelAttribute("site")
    public SiteProperties siteProperties() {
        return siteProperties;
    }
}
