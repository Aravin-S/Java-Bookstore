package bookstoreapplication;

import javafx.scene.control.CheckBox;

public class Book {
    
    private String title;
    private int price;
    public CheckBox select;

    public Book(final String title, final int price) {
        this.title = title;
        this.price = price;
        select = new CheckBox();
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
    
    public CheckBox getSelect(){
        return select;
    }
    
    public void setSelect(CheckBox select){
        this.select = select;
    }
}

