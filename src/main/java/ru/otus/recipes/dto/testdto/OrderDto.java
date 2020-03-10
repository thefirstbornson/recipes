package ru.otus.recipes.dto.testdto;

public class OrderDto {
    long id;
    String comment;
    ClientDto client;

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
}
