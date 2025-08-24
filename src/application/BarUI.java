package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BarUI extends Application {
	private VBox selectedItemsBox = new VBox();
    public List<Bottle> selectedBottles = new ArrayList<>();
    private Text totalAmountText = new Text("₹0.00");
    private Text taxAmountText = new Text("₹0.00");
    private Text finalAmountText = new Text("₹0.00");

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bar Billing System");
        
        BorderPane root = new BorderPane();
        
        VBox header = createHeader();
        root.setTop(header);
        
        ScrollPane scrollPane = createBottleGrid();
        root.setCenter(scrollPane);
        
        VBox billingPanel = createBillingPanel();
        scrollPane.setStyle("-fx-background: #1A1A1A;");
        root.setRight(billingPanel);
        
        Scene scene = new Scene(root, 1200, 800);
        Image logo = new Image("logo.jpg");
        primaryStage.getIcons().add(logo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createHeader() {
        VBox header = new VBox();
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #E42620;");
        
        ImageView titleImage = new ImageView(new Image("Title.png"));
        titleImage.setFitHeight(30);
        titleImage.setPreserveRatio(true);
        header.getChildren().add(titleImage);
        
        return header;
    }
    
    private ScrollPane createBottleGrid() {
        BottleInventory inventory = new BottleInventory();
        List<Bottle> bottles = inventory.getAllBottles();
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        
        int col = 0;
        int row = 0;
        
        for (Bottle bottle : bottles) {
            VBox bottleBox = createBottleCard(bottle);
            
            grid.add(bottleBox, col, row);
            
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
        
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);
        return scrollPane;
    }
    
    private VBox createBottleCard(Bottle bottle) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #1A1A1A; -fx-border-color: #F08A24; -fx-border-radius: 15;");
        card.setPrefWidth(220);
        
        try {
            ImageView imageView = new ImageView(bottle.getImagePath());
            imageView.setFitHeight(150);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            card.getChildren().add(imageView);
        } catch (Exception e) {
            Text imagePlaceholder = new Text("[Bottle Image]");
            card.getChildren().add(imagePlaceholder);
        }
        
        Text nameText = new Text(bottle.getName());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameText.setFill(Color.ORANGE);
        
        Text priceText = new Text(String.format("₹%.2f", bottle.getPrice()));
        priceText.setFill(Color.ORANGE);
        
        HBox starsBox = new HBox(2);
        starsBox.setAlignment(Pos.CENTER);
        int fullStars = (int)bottle.getRating();
        boolean hasHalfStar = (bottle.getRating() - fullStars) >= 0.5;
        
        for (int i = 0; i < fullStars; i++) {
            Text star = new Text("★");
            star.setFill(Color.ORANGE);
            starsBox.getChildren().add(star);
        }
        
        if (hasHalfStar) {
            Text halfStar = new Text("½");
            halfStar.setFill(Color.ORANGE);
            starsBox.getChildren().add(halfStar);
        }
        
        Text ratingText = new Text(String.format(" (%.1f)", bottle.getRating()));
        ratingText.setFill(Color.ORANGE);
        starsBox.getChildren().add(ratingText);
        
        Button addButton = new Button("Add to Bill");
        addButton.setStyle("-fx-background-color: #FF5722;");
        addButton.setOnAction(e -> {
            selectedBottles.add(bottle);
            updateBill();
        });
        
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
        	selectedBottles.remove(bottle);
        	updateBill();
        });
        
        card.getChildren().addAll(nameText, priceText, starsBox, addButton, removeButton);
        return card;
    }
    
    private VBox createBillingPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(300);
        panel.setStyle("-fx-background-color: #1A1A1A;");
        
        Text title = new Text("Bill Summary");
        title.setFill(Color.ORANGE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        selectedItemsBox.setPrefHeight(300);
        
        VBox summaryBox = new VBox(5);
        
        HBox totalBox = new HBox(10);
        totalBox.setAlignment(Pos.CENTER_LEFT);
        Text totalLabel = new Text("Total :");
        totalLabel.setFill(Color.ORANGE);

        totalAmountText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalAmountText.setFill(Color.ORANGE);
        totalBox.getChildren().addAll(totalLabel, totalAmountText);
        
        HBox taxBox = new HBox(10);
        taxBox.setAlignment(Pos.CENTER_LEFT);
        Text taxLabel = new Text("Tax (" + TaxCalculator.getTaxRate() + "%) :");
        taxLabel.setFill(Color.ORANGE);
        taxAmountText.setFont(Font.font("Arial",FontWeight.BOLD, 14));
        taxAmountText.setFill(Color.ORANGE);
        taxBox.getChildren().addAll(taxLabel, taxAmountText);
        
        HBox finalBox = new HBox(10);
        finalBox.setAlignment(Pos.CENTER_LEFT);
        Text finalLabel = new Text("Final Amount :");
        finalLabel.setFill(Color.ORANGE);
        finalAmountText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        finalAmountText.setFill(Color.ORANGE);
        finalBox.getChildren().addAll(finalLabel, finalAmountText);
        
        summaryBox.getChildren().addAll(totalBox, taxBox, finalBox);
        
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #90EE90; -fx-text-fill: black;");
        checkoutButton.setPrefWidth(150);
        checkoutButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkout Successful");
            alert.setHeaderText("Thank you for your purchase! \n\n\n");
            alert.setContentText("Total : "+totalAmountText.getText()+"\n"+"____________________________________________________________________"+"\n\n"+"Tax Paid : "+ taxAmountText.getText()+"\n"+"____________________________________________________________________"+"\n\n"+"Final Amount Paid: " + finalAmountText.getText());
            alert.showAndWait();
            
            selectedBottles.clear();
            updateBill();
        });
        
         
        Button clearButton = new Button("Clear Bill");
        clearButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: black;");
        clearButton.setPrefWidth(150);
        clearButton.setOnAction(e -> {
            selectedBottles.clear();
            selectedItemsBox.getChildren().clear();
            updateBill();
        });
        
        panel.getChildren().addAll(title,selectedItemsBox, summaryBox, checkoutButton, clearButton);
        
        return panel;
    }
    
    private void updateBill() {
    	selectedItemsBox.getChildren().clear();

        List<String> displayedNames = new ArrayList<>();
        
        for (Bottle bottle : selectedBottles) {
            String name = bottle.getName();
            if (!displayedNames.contains(name)) {
                int count = 0;
                double totalPrice = 0.0;
                
                for (Bottle b : selectedBottles) {
                    if (b.getName().equals(name)) {
                        count++;
                        totalPrice += b.getPrice();
                    }
                }
                
                Text bottleText = new Text(name + (count > 1 ? " ×" + count : "") + " — ₹" + String.format("%.2f", totalPrice));
                bottleText.setFill(Color.ORANGE);
                selectedItemsBox.getChildren().add(bottleText);
                displayedNames.add(name);
            }
        }

        double totalAmount = 0;

        for (Bottle bottle : selectedBottles) {
            totalAmount += bottle.getPrice();
        }

        double taxAmount = TaxCalculator.getTaxAmount(totalAmount);
        double finalAmount = TaxCalculator.calculateTax(totalAmount);

        totalAmountText.setText(String.format("₹%.2f", totalAmount));
        taxAmountText.setText(String.format("₹%.2f", taxAmount));
        finalAmountText.setText(String.format("₹%.2f", finalAmount));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}