package edu.northeastern.tinyurl.model;

import java.io.Serializable;

public class UrlMappingRequest implements Serializable {
    private static final long serialVersionUID = -2224190559469070508L;
    private String customUrl;
    private String originalUrl;

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
