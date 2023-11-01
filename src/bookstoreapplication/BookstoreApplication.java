/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.CheckBoxTableCell;


/**
 *
 * @author Aravin
 */
public class BookstoreApplication extends Application {
    
    Owner owner = new Owner();
    
    Customer customer;
    int totalCost;
    
    HBox hb = new HBox();
    Button booksButton = new Button("Books");
    Button customersButton = new Button("Customers");
    Button logoutButton = new Button("Logout");
    Button backButton = new Button("Back");
    Button loginButton = new Button("Login");
    Button buyButton = new Button("Buy");
    Button buyPButton = new Button("Redeem Points and Buy");
    //Button buyButton = new Button("Buy");
    //Button rpBuyButton = new Button("Redeem Points and Buy");
    Label userLabel = new Label("Username:");
    TextField uTextField = new TextField();
    Label passLabel = new Label("Password:");
    PasswordField passField = new PasswordField();
    
    TableView<Book> booksTable = new TableView<>();
    final TableView.TableViewFocusModel<Book> defaultFocusModel = booksTable.getFocusModel();
    ObservableList<Book> bookList = FXCollections.observableArrayList();
    
    public ObservableList<Book> addBooks(){ //This is to add the books to the array but it gets an error when uncommented. Has to do with owner class
        bookList.addAll(owner.books);
        return bookList;
    }
    
    TableView<Customer> customersTable = new TableView<>();
    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public ObservableList<Customer> addCustomers(){ //This is to add the customers to the array but it gets an error when uncommented. Has to do with owner class
        customerList.addAll(owner.customers);
        return customerList;
    }
    
    
    public VBox ownerStart(){
        
        VBox ownS = new VBox(); 
        ownS.setStyle("-fx-background-color: #FFFFFF;"); //This is a temp background color. You guys can pick one
        ownS.setAlignment(Pos.CENTER);
        ownS.setSpacing(100);
        ownS.setPadding(new Insets(70,0,25,0));
        
        VBox buttonLayout = new VBox();
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(30);
        buttonLayout.getChildren().addAll(booksButton, customersButton, logoutButton);
        booksButton.setPrefSize(200,100);
        customersButton.setPrefSize(200,100);
        logoutButton.setPrefSize(200, 100);
        ownS.getChildren().addAll(buttonLayout);
                
         return ownS;
    }
    
    public Group booksScreen(){
        
        Group bT = new Group();
        hb.getChildren().clear(); //uses one HBox. Clears the buttons from the HBox just used in OwnerStart
        booksTable.getItems().clear();
        booksTable.getColumns().clear();
        booksTable.setFocusModel(defaultFocusModel);
        
        Label label = new Label("Books");
        label.setFont(new Font("Calibri", 18)); //Feel free to change font and size. They are just temp
        
        TableColumn<Book, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title")); //I think this has to be changed to whatever variable the other book file uses to declare book name which i think it's title
        
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        booksTable.setItems(addBooks());
        booksTable.getColumns().addAll(nameColumn, priceColumn);
        
        final TextField addNewBook = new TextField();
        addNewBook.setPromptText("Title");
        addNewBook.setMaxWidth(nameColumn.getPrefWidth());
        final TextField addBookPrice = new TextField();
        addBookPrice.setMaxWidth(priceColumn.getPrefWidth());
        addBookPrice.setPromptText("Price");
        addNewBook.setStyle("-fx-background-color: #FFFFFF;"); //Temp Colour
        addBookPrice.setStyle("-fx-background-color: #FFFFFF;"); //Temp Colour
        
        VBox center = new VBox();
        final Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #FFFFFF;");
        Label addErr = new Label("Invalid Input");
        addErr.setTextFill(Color.color(1,0,0));
        
         addButton.setOnAction(e -> {
            try {
                int price = Integer.parseInt(addBookPrice.getText());
                owner.addBook(new Book(addNewBook.getText(), price));
                //makes new book and adds it to arraylist
                booksTable.getItems().clear(); //refreshs the page so the book can be accessed
                booksTable.setItems(addBooks());
                addNewBook.clear(); //clears text fields
                addBookPrice.clear();
                center.getChildren().remove(addErr); //removes a previous Invalid Input error if there was one
            }
            catch (Exception exception){
                if(!center.getChildren().contains(addErr)){
                    center.getChildren().add(addErr);
                }
            }
        });
         
        final Button delButton = new Button("Delete");
        delButton.setStyle("-fx-background-color: #FFFFFF;");
        delButton.setOnAction(e -> {
            
            Book selectedItem = booksTable.getSelectionModel().getSelectedItem(); //selects highlighted row
            booksTable.getItems().remove(selectedItem); //removes from table view as soon as delete pressed
            owner.delBook(selectedItem); //actually removes from the arraylist PROBLEM ACCESSING BOOKS FROM OWNER CLASS. HAS TO BE CONNECTED
            
        });
        
      //Layout 
      
        hb.getChildren().addAll(addNewBook, addBookPrice);
        hb.setSpacing(3);
        hb.setAlignment(Pos.CENTER);
        
        HBox buttons = new HBox();
        buttons.getChildren().addAll(addButton, delButton);
        buttons.setSpacing(3);
        buttons.setPadding(new Insets(0, 0, 0, 100));  //Having trouble moving the add and delete buttons. If you guys can try and move them pls
        buttons.setAlignment(Pos.CENTER);

        HBox back = new HBox();
        back.setPadding(new Insets(5));
        back.getChildren().addAll(backButton);

        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);
        center.setPadding(new Insets(0, 0, 0, 150));
        center.getChildren().addAll(label, booksTable, hb);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #D3D3D3;");
        vbox.setPadding(new Insets(0, 200, 60, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(center, back, buttons);


        bT.getChildren().addAll(vbox);
        
        return bT;
    }
    
     public Group customerScreen() {
        Group cT = new Group();
        hb.getChildren().clear();
        customersTable.getItems().clear();
        customersTable.getColumns().clear();

        Label label = new Label("Customers");
        label.setFont(new Font("Calibri", 18));

        //username column
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(140);
        usernameColumn.setStyle("-fx-alignment: CENTER;");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        //password column
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(140);
        passwordColumn.setStyle("-fx-alignment: CENTER;");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        //points column
        TableColumn<Customer, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(100);
        pointsColumn.setStyle("-fx-alignment: CENTER;");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        customersTable.setItems(addCustomers());
        customersTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(usernameColumn.getPrefWidth());
        final TextField addPassword = new TextField();
        addPassword.setMaxWidth(passwordColumn.getPrefWidth());
        addPassword.setPromptText("Password");
        addPassword.setStyle("-fx-background-color: #FFFFFF;");
        addUsername.setStyle("-fx-background-color: #FFFFFF;");

        VBox center = new VBox();
        Text customerAddErr = new Text("Customer already exists!");
        customerAddErr.setFill(Color.color(1,0,0));
        final Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #FFFFFF;");
        addButton.setOnAction(e -> { 
            try {owner.addCustomer(new Customer(addUsername.getText(), addPassword.getText(), 0));
                //makes new book and adds it to arraylist
                customersTable.getItems().clear(); //refreshs the page so the book can be accessed
                customersTable.setItems(addCustomers());
                addUsername.clear(); //clears text fields
                addPassword.clear();
                center.getChildren().remove(customerAddErr);
            } catch (Exception exception) {
                if(!center.getChildren().contains(customerAddErr)){
                    center.getChildren().add(customerAddErr);
                }
            }
            /*
            Code needed to add new customer when button clicked
            */
        });

        final Button delButton = new Button("Delete");
        delButton.setStyle("-fx-background-color: #FFFFFF;");
        delButton.setOnAction(e -> {
            Customer selectedItem = customersTable.getSelectionModel().getSelectedItem();
            customersTable.getItems().remove(selectedItem); //remove from tableview
            owner.delCustomer(selectedItem); //removes from the actual arraylist
        });

        //Layout
        
        hb.getChildren().addAll(addUsername, addPassword);
        hb.setSpacing(3);
        hb.setAlignment(Pos.CENTER);
        
        HBox buttons = new HBox();
        buttons.getChildren().addAll(addButton, delButton);
        buttons.setSpacing(3);
        buttons.setPadding(new Insets(0, 0, 0, 100));  //Having trouble moving the add and delete buttons. If you guys can try and move them pls
        buttons.setAlignment(Pos.CENTER);

        HBox back = new HBox();
        back.setPadding(new Insets(5));
        back.getChildren().addAll(backButton);

        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);
        center.setPadding(new Insets(0, 0, 0, 120));
        center.getChildren().addAll(label, customersTable, hb);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #D3D3D3;");
        vbox.setPadding(new Insets(0, 200, 60, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(center, back, buttons);

        cT.getChildren().addAll(vbox);
        return cT;
     }
     
      public GridPane loginScreen() {
       
        GridPane gp1 = new GridPane();
        Label welcome = new Label("Welcome to the Bookstore App!");
        Label error = new Label();
        welcome.setAlignment(Pos.TOP_CENTER);
        gp1.setAlignment(Pos.CENTER); 
        //add a Button in the GridPane at column 0 and row 0
        gp1.add(userLabel, 0, 1);
        //add a TextField in the GridPane at column 0 and row 1
        gp1.add(uTextField, 0, 2);
        gp1.add(passLabel, 0, 3);
        gp1.add(passField, 0, 4);
        gp1.add(loginButton, 0, 5);
        gp1.add(welcome, 0, 0);
        
        
        return gp1;
    }
     
    public Group customerStart(Customer c){
        
        customer = c;
        Group bT = new Group();
        hb.getChildren().clear(); //uses one HBox. Clears the buttons from the HBox just used in OwnerStart
        booksTable.getItems().clear();
        booksTable.getColumns().clear();
        booksTable.setFocusModel(defaultFocusModel);
        
        Label label = new Label("Books");
        label.setFont(new Font("Calibri", 18)); //Feel free to change font and size. They are just temp
        
        TableColumn<Book, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title")); //I think this has to be changed to whatever variable the other book file uses to declare book name which i think it's title
        
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn<Book, String> selectColumn = new TableColumn<>("Select");
        selectColumn.setMinWidth(100);
        selectColumn.setStyle("-fx-alignment: CENTER;");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        
        booksTable.setItems(addBooks());
        booksTable.getColumns().addAll(nameColumn, priceColumn, selectColumn);
        
        VBox center = new VBox();
        
        buyButton.setStyle("-fx-background-color: #FFFFFF;");
        Label addErr = new Label("Invalid Input");
        addErr.setTextFill(Color.color(1,0,0));
         
        buyPButton.setStyle("-fx-background-color: #FFFFFF;");
       
      //Layout 

        HBox buttons = new HBox();
        buttons.getChildren().addAll(buyButton, buyPButton);
        buttons.setSpacing(3);
        buttons.setPadding(new Insets(0, 0, 0, 100));  //Having trouble moving the add and delete buttons. If you guys can try and move them pls
        buttons.setAlignment(Pos.CENTER);

        HBox logout = new HBox();
        logout.setPadding(new Insets(5));
        logout.getChildren().addAll(logoutButton);

        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);
        center.setPadding(new Insets(0, 0, 0, 150));
        center.getChildren().addAll(label, booksTable, hb);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #D3D3D3;");
        vbox.setPadding(new Insets(0, 200, 60, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(center, logout, buttons);

        bT.getChildren().addAll(vbox);
        
        return bT;
    }
    
    public GridPane customer_cost(Customer c) {

        Label total_cost;
        GridPane gp1 = new GridPane();
        
            total_cost = new Label("Total Cost: " + totalCost + " | ");
        
        Label total_points =  new Label("Your Points: " + c.getPoints() + " | ");
        
        Label status;
        if (c.getStatus() == 1){
            status =  new Label("Status: Gold ");
        } else {
            status =  new Label("Status: Silver ");
        }

        gp1.setAlignment(Pos.CENTER); 
        //add a Button in the GridPane at column 0 and row 0
        gp1.add(total_cost, 0, 1);
        gp1.add(total_points, 1, 1);
        gp1.add(status, 2, 1);

        //add a TextField in the GridPane at column 0 and row 1

        gp1.add(logoutButton, 0, 4);

        return gp1;
    }
     
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Bookstore Application");  
        primaryStage.setScene(new Scene(loginScreen(), 605, 550));
        primaryStage.show(); 
        
        loginButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if ((uTextField.getText().compareTo("admin") == 0) && (passField.getText().compareTo("admin") == 0)) { 
                        primaryStage.setScene(new Scene(ownerStart(), 605, 550));
                        uTextField.clear();
                        passField.clear();
                    }
                    for (int i = 0; i < owner.customers.size(); i++) {
                        if ((owner.customers.get(i).getUserName().compareTo(uTextField.getText()) == 0) && (owner.customers.get(i).getPassword().compareTo(passField.getText()) == 0 )) {
                            primaryStage.setScene(new Scene(customerStart(owner.customers.get(i)), 605, 550));//replace scene with second pane
                            uTextField.clear();
                            passField.clear();
                        }
                    }                
                }
            }    
        );
        
        uTextField.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    TextField tf = (TextField)e.getSource();
                   //obtains username
                }
            } 
        );
        
        passField.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PasswordField pf = (PasswordField)e.getSource();
                    //obtains password
                }
            } 
        );
        
       primaryStage.setScene(new Scene(loginScreen(), 605, 550));
       
       booksButton.setOnAction(e -> primaryStage.setScene(new Scene(booksScreen(), 605, 550)));
       customersButton.setOnAction(e -> primaryStage.setScene(new Scene(customerScreen(), 605, 550)));
       backButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerStart(), 605, 550)));
       logoutButton.setOnAction(e -> {
           primaryStage.setScene(new Scene(loginScreen(), 605, 550));
           owner.finalize();
       });
       buyButton.setOnAction(e -> {
           totalCost = 0;
           for (int i = 0; i < owner.books.size(); i++) {   
                if (owner.books.get(i).getSelect().isSelected()) {
                    totalCost += owner.books.get(i).getPrice();
                }  
            }
           customer.addPoints(totalCost * 10);
           primaryStage.setScene(new Scene(customer_cost(customer), 605, 550));
       });
       buyPButton.setOnAction(e -> {
           totalCost = 0;
           for (int i = 0; i < owner.books.size(); i++) {   
                if (owner.books.get(i).getSelect().isSelected()) {
                    totalCost += owner.books.get(i).getPrice();
                }  
            }
           //customer.addPoints(totalCost * -100);
           
           if (customer.getPoints() > (totalCost * 100)) {
               customer.addPoints(totalCost * -100);
               primaryStage.setScene(new Scene(customer_cost(customer), 605, 550));
           } else {

               totalCost -= (customer.getPoints() / 100);
               customer.addPoints(totalCost * -100);
               customer.addPoints(totalCost * 10);
               primaryStage.setScene(new Scene(customer_cost(customer), 605, 550));
           }
       });
       primaryStage.show(); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
