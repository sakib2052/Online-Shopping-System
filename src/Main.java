import java.util.*;

// Abstract class for Payment (Abstraction)
abstract class Payment {
    double amount; // Stores the total payment amount

    // Constructor to initialize the payment amount
    Payment(double amount) {
        this.amount = amount;
    }

    // Abstract method to be implemented by all payment types
    abstract void processPayment();
}

// Subclass for Credit Card Payment (Polymorphism)
class CreditCardPayment extends Payment {
    // Constructor that passes amount to the parent class
    CreditCardPayment(double amount) {
        super(amount);
    }

    // Overridden method to process credit card payment
    @Override
    void processPayment() {
        System.out.println("Payment of " + amount + " processed via Credit Card.");
    }
}

// Subclass for Cash on Delivery Payment (Polymorphism)
class CashOnDeliveryPayment extends Payment {
    // Constructor that passes amount to the parent class
    CashOnDeliveryPayment(double amount) {
        super(amount);
    }

    // Overridden method to process cash payment
    @Override
    void processPayment() {
        System.out.println("Payment of " + amount + " will be made on Delivery (Cash).");
    }
}

// Base Product class (Encapsulation)
class Product {
    private int id;         // Private variable to store product ID
    private String name;    // Private variable to store product name
    private double price;   // Private variable to store product price

    // Constructor to initialize product details
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getter method to return product name
    public String getName() {
        return name;
    }

    // Getter method to return product price
    public double getPrice() {
        return price;
    }

    // Method to display product information
    public void displayProduct() {
        System.out.println("Product ID: " + id + ", Name: " + name + ", Price: " + price);
    }
}

// Subclass for Electronic Products (Inheritance)
class ElectronicProduct extends Product {
    private int warrantyYears; // Extra property for electronic products

    // Constructor that initializes parent and child class properties
    public ElectronicProduct(int id, String name, double price, int warrantyYears) {
        super(id, name, price); // Call parent class constructor
        this.warrantyYears = warrantyYears;
    }

    // Overridden method to show product info + warranty
    @Override
    public void displayProduct() {
        super.displayProduct(); // Display base product details
        System.out.println("Warranty: " + warrantyYears + " years");
    }
}

// Subclass for Clothing Products (Inheritance)
class ClothingProduct extends Product {
    private String size; // Extra property for clothing

    // Constructor that initializes parent and child class properties
    public ClothingProduct(int id, String name, double price, String size) {
        super(id, name, price); // Call parent class constructor
        this.size = size;
    }

    // Overridden method to show product info + size
    @Override
    public void displayProduct() {
        super.displayProduct(); // Display base product details
        System.out.println("Size: " + size);
    }
}

// Customer class (Encapsulation)
class Customer {
    private String name;   // Private variable for customer name
    private String email;  // Private variable for customer email

    // Constructor to initialize customer data
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Method to show customer information
    public void displayCustomer() {
        System.out.println("Customer Name: " + name + ", Email: " + email);
    }
}

// Interface for order processing (Abstraction)
interface OrderProcessor {
    void processOrder(); // Method to be implemented by classes handling orders
}

// Order class that implements OrderProcessor interface
class Order implements OrderProcessor {
    private Customer customer;         // The customer placing the order
    private List<Product> productList; // List to store all selected products
    private double totalAmount;        // Total amount of the order

    // Constructor that initializes customer and creates an empty cart
    public Order(Customer customer) {
        this.customer = customer;
        this.productList = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // Method to add a product to the order
    public void addProduct(Product p) {
        productList.add(p);          // Add product to list
        totalAmount += p.getPrice(); // Update total amount
    }

    // Method to apply discount to the total
    public void applyDiscount(double percent) {
        totalAmount -= (totalAmount * percent / 100);
    }

    // Implementation of processOrder() from interface
    @Override
    public void processOrder() {
        System.out.println("\n====== ORDER SUMMARY ======");
        customer.displayCustomer(); // Show customer info
        System.out.println("\nProducts Ordered:");
        for (Product p : productList) {
            p.displayProduct(); // Show each product info
        }
        System.out.println("\nTotal Amount: " + totalAmount);
    }

    // Getter for total amount
    public double getTotalAmount() {
        return totalAmount;
    }
}

// Main class to run the system
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner for user input

        // Step 1: Take customer details
        System.out.print("Enter Customer Name: ");
        String cname = sc.nextLine();
        System.out.print("Enter Customer Email: ");
        String cemail = sc.nextLine();
        Customer customer = new Customer(cname, cemail); // Create customer object

        // Step 2: Create a new order for this customer
        Order order = new Order(customer);

        // Step 3: Ask how many products user wants to add
        System.out.print("\nEnter number of products you want to buy: ");
        int count = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Step 4: Loop through each product input
        for (int i = 1; i <= count; i++) {
            System.out.println("\nProduct " + i + ":");
            System.out.print("Enter 1 for Electronic Product, 2 for Clothing: ");
            int type = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Product Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            // If product is electronic
            if (type == 1) {
                System.out.print("Enter Warranty (in years): ");
                int warranty = sc.nextInt();
                sc.nextLine();
                order.addProduct(new ElectronicProduct(id, name, price, warranty));
            }
            // If product is clothing
            else {
                System.out.print("Enter Size: ");
                String size = sc.nextLine();
                order.addProduct(new ClothingProduct(id, name, price, size));
            }
        }

        // Step 5: Apply discount (optional)
        System.out.print("\nEnter discount percentage (0 if none): ");
        double discount = sc.nextDouble();
        if (discount > 0) {
            order.applyDiscount(discount);
        }

        // Step 6: Process and display the order summary
        order.processOrder();

        // Step 7: Choose and process payment
        System.out.print("\nChoose Payment Method (1 = Credit Card, 2 = Cash on Delivery): ");
        int payOption = sc.nextInt();

        Payment payment; // Reference for polymorphism
        if (payOption == 1) {
            payment = new CreditCardPayment(order.getTotalAmount());
        } else {
            payment = new CashOnDeliveryPayment(order.getTotalAmount());
        }

        // Step 8: Process the selected payment
        payment.processPayment();

        // Step 9: Display thank-you message
        System.out.println("\nThank you for shopping with us!");
        sc.close(); // Close the scanner
    }
}
