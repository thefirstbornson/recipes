package ru.otus.recipes.dto.testdto;

public class ItemDto {
    long id ;
    String name;
    AddressDto vendorAddress;

    public AddressDto getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(AddressDto vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public ItemDto(long id, String name, AddressDto vendorAddress) {
        this.id = id;
        this.name = name;
        this.vendorAddress = vendorAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}