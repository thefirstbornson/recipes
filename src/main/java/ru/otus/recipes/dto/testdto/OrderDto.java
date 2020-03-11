package ru.otus.recipes.dto.testdto;

import java.util.List;

public class OrderDto {
    long id;
    String comment;
    ClientDto client;
    List<ItemDto> items;
    VendorDto vendor;

    public OrderDto(long id, String comment, ClientDto client) {
        this.id = id;
        this.comment = comment;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public VendorDto getVendor() {
        return vendor;
    }

    public void setVendor(VendorDto vendor) {
        this.vendor = vendor;
    }
}
