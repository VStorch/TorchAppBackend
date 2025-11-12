package com.torchapp.demo.dtos.petShopnIformation;

import java.util.List;

// DTO para criar/atualizar Pet Shop Information
public class PetShopInformationRequestDTO {
    private String name;
    private String description;
    private List<String> services;
    private String instagram;
    private String facebook;
    private String website;
    private String whatsapp;
    private String commercialPhone;
    private String commercialEmail;
    private Long userId;

    // Construtores
    public PetShopInformationRequestDTO() {
    }

    public PetShopInformationRequestDTO(String name, String description, List<String> services,
                                        String instagram, String facebook, String website,
                                        String whatsapp, String commercialPhone,
                                        String commercialEmail, Long userId) {
        this.name = name;
        this.description = description;
        this.services = services;
        this.instagram = instagram;
        this.facebook = facebook;
        this.website = website;
        this.whatsapp = whatsapp;
        this.commercialPhone = commercialPhone;
        this.commercialEmail = commercialEmail;
        this.userId = userId;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCommercialPhone() {
        return commercialPhone;
    }

    public void setCommercialPhone(String commercialPhone) {
        this.commercialPhone = commercialPhone;
    }

    public String getCommercialEmail() {
        return commercialEmail;
    }

    public void setCommercialEmail(String commercialEmail) {
        this.commercialEmail = commercialEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
