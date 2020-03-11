package ru.otus.recipes.dto.testdto;

public class AddressDto {
    long id;
    int index;
    String city;
    String street;
    int building;
    int apartment;

    public AddressDto(long id, int index, String city, String street, int building, int apartment) {
        this.id = id;
        this.index = index;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }
}
