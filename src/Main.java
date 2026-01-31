import java.io.*;
import java.util.Scanner;

public class Main {

    static final int MAX = 100;

    // Parallel arrays (no OOP)
    static int[] ids = new int[MAX];
    static String[] names = new String[MAX];
    static double[] prices = new double[MAX];
    static int[] qtys = new int[MAX];
    static int count = 0;

    static final String DATA_FILE = "inventory.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load existing data (optional file feature)
        loadFromFile();

        while (true) {
            printMenu();
            int choice = readInt(sc, "Choose: ");

            switch (choice) {
                case 1:
                    addProduct(sc);
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    searchProduct(sc);
                    break;
                case 4:
                    updateProduct(sc);
                    break;
                case 5:
                    deleteProduct(sc);
                    break;
                case 6:
                    saveToFile();
                    break;
                case 9:
                    loadDemoProducts();
                    break;
                case 0:
                    // autosave on exit (optional)
                    saveToFile();
                    System.out.println("Goodbye.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println();
        }
    }

    static void printMenu() {
        // System.out.println("=== SIMPLE INVENTORY APP (Java 8+) ===");
        System.out.println("[1] Add Product");
        System.out.println("[2] View Products");
        System.out.println("[3] Search Product (by ID)");
        System.out.println("[4] Update Product (by ID)");
        System.out.println("[5] Delete Product (by ID)");
        System.out.println("[6] Save to File");
        System.out.println("[9] Load Demo Products");
        System.out.println("[0] Exit");
    }

    // ---------------- Core Features ----------------

    static void addProduct(Scanner sc) {
        if (count >= MAX) {
            System.out.println("Inventory is full.");
            return;
        }

        int id = readInt(sc, "ID: ");

        // Duplicate ID handling
        if (indexOfId(id) != -1) {
            System.out.println("Error: ID already exists. Try another ID.");
            return;
        }

        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            System.out.print("Name: ");
            name = sc.nextLine().trim();
        }

        double price = readDouble(sc, "Price: ");
        int qty = readInt(sc, "Quantity: ");

        // Non-negative validation
        if (price < 0 || qty < 0) {
            System.out.println("Error: Price and quantity must be non-negative.");
            return;
        }

        ids[count] = id;
        names[count] = name;
        prices[count] = price;
        qtys[count] = qty;
        count++;

        System.out.println("Added successfully.");
    }

    static void viewProducts() {
        if (count == 0) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("ID | Name | Price | Qty");
        System.out.println("------------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%d | %s | %.2f | %d%n", ids[i], names[i], prices[i], qtys[i]);
        }
    }

    static void searchProduct(Scanner sc) {
        int id = readInt(sc, "Enter ID to search: ");
        int idx = indexOfId(id);

        if (idx == -1) {
            System.out.println("Error: Product not found.");
            return;
        }

        System.out.printf("FOUND: %d | %s | %.2f | %d%n",
                ids[idx], names[idx], prices[idx], qtys[idx]);
    }

    static void updateProduct(Scanner sc) {
        int id = readInt(sc, "Enter ID to update: ");
        int idx = indexOfId(id);

        if (idx == -1) {
            System.out.println("Error: Product not found.");
            return;
        }

        System.out.printf("Current: %d | %s | %.2f | %d%n",
                ids[idx], names[idx], prices[idx], qtys[idx]);

        System.out.println("[1] Update Name");
        System.out.println("[2] Update Price");
        System.out.println("[3] Update Quantity");
        int c = readInt(sc, "Choose: ");

        if (c == 1) {
            System.out.print("New name: ");
            String newName = sc.nextLine().trim();
            while (newName.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
                System.out.print("New name: ");
                newName = sc.nextLine().trim();
            }
            names[idx] = newName;
            System.out.println("Updated.");
        } else if (c == 2) {
            double newPrice = readDouble(sc, "New price: ");
            if (newPrice < 0) {
                System.out.println("Error: Price must be non-negative.");
                return;
            }
            prices[idx] = newPrice;
            System.out.println("Updated.");
        } else if (c == 3) {
            int newQty = readInt(sc, "New quantity: ");
            if (newQty < 0) {
                System.out.println("Error: Quantity must be non-negative.");
                return;
            }
            qtys[idx] = newQty;
            System.out.println("Updated.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void deleteProduct(Scanner sc) {
        int id = readInt(sc, "Enter ID to delete: ");
        int idx = indexOfId(id);

        if (idx == -1) {
            System.out.println("Error: Product not found.");
            return;
        }

        // shift left to remove
        for (int i = idx; i < count - 1; i++) {
            ids[i] = ids[i + 1];
            names[i] = names[i + 1];
            prices[i] = prices[i + 1];
            qtys[i] = qtys[i + 1];
        }

        count--;
        System.out.println("Deleted.");
    }

    // ---------------- Helpers ----------------

    static int indexOfId(int id) {
        for (int i = 0; i < count; i++) {
            if (ids[i] == id) return i;
        }
        return -1;
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                return Double.parseDouble(raw);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    // ---------------- File Save / Load (CSV) ----------------

    static void saveToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(DATA_FILE));
            bw.write("id,name,price,qty");
            bw.newLine();

            for (int i = 0; i < count; i++) {
                String safeName = names[i].replace(",", " ");
                bw.write(ids[i] + "," + safeName + "," + prices[i] + "," + qtys[i]);
                bw.newLine();
            }

            System.out.println("Saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        } finally {
            try { if (bw != null) bw.close(); } catch (IOException ignored) {}
        }
    }

    static void loadFromFile() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(DATA_FILE));
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // header
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int qty = Integer.parseInt(parts[3].trim());

                if (count < MAX && indexOfId(id) == -1) {
                    ids[count] = id;
                    names[count] = name;
                    prices[count] = price;
                    qtys[count] = qty;
                    count++;
                }
            }

            System.out.println("Loaded " + count + " item(s) from file.");
        } catch (Exception e) {
            System.out.println("Warning: could not load file properly. Starting empty.");
            count = 0;
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }
    }

    // ---------------- Demo Products ----------------

    static void loadDemoProducts() {
        // Overwrite current data with demo set
        count = 0;

        addDemo(101, "Cement (40kg)", 270.00, 50);
        addDemo(102, "Steel Bar 10mm", 185.50, 120);
        addDemo(103, "Plywood 1/2 inch", 720.00, 30);
        addDemo(104, "Paint (White)", 550.00, 18);
        addDemo(105, "Nails 2 inch", 45.00, 200);

        System.out.println("Demo products loaded (5 items).");
    }

    static void addDemo(int id, String name, double price, int qty) {
        if (count >= MAX) return;
        ids[count] = id;
        names[count] = name;
        prices[count] = price;
        qtys[count] = qty;
        count++;
    }
}
