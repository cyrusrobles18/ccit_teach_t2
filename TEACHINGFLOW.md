# Progressive Java Workshop (Console Inventory App) — Java 8+ (No OOP)

This workshop is delivered **flow-by-flow**.  
Each flow introduces one concept, then adds code that **builds toward one complete Inventory App**.

**Constraints**
- Java 8+ compatible
- Console only
- No classes/objects (no OOP)
- Use: `Scanner`, variables, `if/switch/while`, arrays, methods, (optional) file I/O

---

## Demo Products (Use for Live Coding + Testing)

Use the following sample products during the workshop so everyone tests the same data.

### Option A — Manual Input (Recommended for beginners)
Add these using the **[1] Add Product** menu:

| ID  | Name              | Price  | Qty |
|-----|-------------------|--------|-----|
| 101 | Cement (40kg)     | 270.00 | 50  |
| 102 | Steel Bar 10mm    | 185.50 | 120 |
| 103 | Plywood 1/2 inch  | 720.00 | 30  |
| 104 | Paint (White)     | 550.00 | 18  |
| 105 | Nails 2 inch      | 45.00  | 200 |

**Quick demo scripts**
- Search demo: search ID `103`
- Update demo: update ID `104` qty to `25`
- Delete demo: delete ID `105`

### Option B — CSV Seed File (Fast demo)
If you are using **Flow 9 (Save/Load CSV)**, create a file named **`inventory.csv`** in the same folder as `Main.java` with this content:

```csv
id,name,price,qty
101,Cement (40kg),270.00,50
102,Steel Bar 10mm,185.50,120
103,Plywood 1/2 inch,720.00,30
104,Paint (White),550.00,18
105,Nails 2 inch,45.00,200
```

Then run the program and it should print something like:
```
Loaded 5 item(s) from file.
```

### Option C — (Optional) Hardcoded Seed Button
If you want an instant demo without typing or file setup, you can add a temporary menu item:
- `[9] Load Demo Products`

Then implement it by assigning values into the arrays and setting `count = 5`.
(Do this only for demo; remove later.)

---

---

## Flow 1 → Input/Output (Scanner) → Read 1 product and display it demo the FLOW 8 so that it can compare the with/out error handling

### Teach
- `main()` entry point
- `Scanner` input
- Parse `int`, `double`
- Print output

### Code (Stage 1: `Main.java`)
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        System.out.println("----- PRODUCT -----");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.printf("Price: %.2f%n", price);
        System.out.println("Qty: " + qty);

        sc.close();
    }
}
```

**Checkpoint:** Student can type product details and see it printed.

---

## Flow 2 → Control Structures (while + switch) → Menu skeleton

### Teach
- Build a menu that repeats
- `while(true)` loop
- `switch(choice)` routes actions

### Code (Stage 2: replace `Main.java`)
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== INVENTORY MENU ===");
            System.out.println("[1] Add Product");
            System.out.println("[2] View Products");
            System.out.println("[0] Exit");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Add Product (coming soon)");
                    break;
                case 2:
                    System.out.println("View Products (coming soon)");
                    break;
                case 0:
                    System.out.println("Goodbye.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println();
        }
    }
}
```

**Checkpoint:** Menu loops until Exit.

---

## Flow 3 → Arrays (Storage) → Add product into arrays

### Teach
- No OOP: use **parallel arrays**
- `count` tracks how many products we stored
- Store at index `count`, then `count++`

### Code (Stage 3: replace `Main.java`)
```java
import java.util.Scanner;

public class Main {

    static final int MAX = 100;
    static int[] ids = new int[MAX];
    static String[] names = new String[MAX];
    static double[] prices = new double[MAX];
    static int[] qtys = new int[MAX];
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== INVENTORY MENU ===");
            System.out.println("[1] Add Product");
            System.out.println("[2] View Products");
            System.out.println("[0] Exit");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addProduct(sc);
                    break;
                case 2:
                    System.out.println("View Products (coming soon)");
                    break;
                case 0:
                    System.out.println("Goodbye.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println();
        }
    }

    static void addProduct(Scanner sc) {
        if (count >= MAX) {
            System.out.println("Inventory is full.");
            return;
        }

        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        ids[count] = id;
        names[count] = name;
        prices[count] = price;
        qtys[count] = qty;
        count++;

        System.out.println("Added.");
    }
}
```

**Checkpoint:** Add stores values into arrays.

---

## Flow 4 → Loops → View products

### Teach
- Loop over `0..count-1`
- Print a simple table

### Code changes (Stage 4)
**A) Replace `case 2` with:**
```java
case 2:
    viewProducts();
    break;
```

**B) Add this method at the bottom:**
```java
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
```

**Checkpoint:** Add multiple products then view list.

---

## Flow 5 → Searching (linear search) → Search by ID

### Teach
- Find item index by scanning array
- Return index if found, else `-1`

### Code changes (Stage 5)
**A) Add menu option:**
```java
System.out.println("[3] Search Product (by ID)");
```

**B) Add case:**
```java
case 3:
    searchProduct(sc);
    break;
```

**C) Add these methods:**
```java
static int indexOfId(int id) {
    for (int i = 0; i < count; i++) {
        if (ids[i] == id) return i;
    }
    return -1;
}

static void searchProduct(Scanner sc) {
    System.out.print("Enter ID to search: ");
    int id = Integer.parseInt(sc.nextLine());

    int idx = indexOfId(id);
    if (idx == -1) {
        System.out.println("Not found.");
        return;
    }

    System.out.printf("FOUND: %d | %s | %.2f | %d%n",
            ids[idx], names[idx], prices[idx], qtys[idx]);
}
```

**Checkpoint:** Search prints the found product.

---

## Flow 6 → Update → Update name/price/qty by ID

### Teach
- Search first, then update arrays at that index

### Code changes (Stage 6)
**A) Add menu option:**
```java
System.out.println("[4] Update Product (by ID)");
```

**B) Add case:**
```java
case 4:
    updateProduct(sc);
    break;
```

**C) Add method:**
```java
static void updateProduct(Scanner sc) {
    System.out.print("Enter ID to update: ");
    int id = Integer.parseInt(sc.nextLine());

    int idx = indexOfId(id);
    if (idx == -1) {
        System.out.println("Not found.");
        return;
    }

    System.out.printf("Current: %d | %s | %.2f | %d%n",
            ids[idx], names[idx], prices[idx], qtys[idx]);

    System.out.println("[1] Update Name");
    System.out.println("[2] Update Price");
    System.out.println("[3] Update Quantity");
    System.out.print("Choose: ");
    int c = Integer.parseInt(sc.nextLine());

    if (c == 1) {
        System.out.print("New name: ");
        names[idx] = sc.nextLine();
        System.out.println("Updated.");
    } else if (c == 2) {
        System.out.print("New price: ");
        prices[idx] = Double.parseDouble(sc.nextLine());
        System.out.println("Updated.");
    } else if (c == 3) {
        System.out.print("New quantity: ");
        qtys[idx] = Integer.parseInt(sc.nextLine());
        System.out.println("Updated.");
    } else {
        System.out.println("Invalid choice.");
    }
}
```

**Checkpoint:** Update reflects in View Products.

---

## Flow 7 → Delete (shift left) → Delete by ID

### Teach
- Arrays do not auto-shrink
- To delete: shift elements left
- Decrease `count`

### Code changes (Stage 7)
**A) Add menu option:**
```java
System.out.println("[5] Delete Product (by ID)");
```

**B) Add case:**
```java
case 5:
    deleteProduct(sc);
    break;
```

**C) Add method:**
```java
static void deleteProduct(Scanner sc) {
    System.out.print("Enter ID to delete: ");
    int id = Integer.parseInt(sc.nextLine());

    int idx = indexOfId(id);
    if (idx == -1) {
        System.out.println("Not found.");
        return;
    }

    for (int i = idx; i < count - 1; i++) {
        ids[i] = ids[i + 1];
        names[i] = names[i + 1];
        prices[i] = prices[i + 1];
        qtys[i] = qtys[i + 1];
    }

    count--;
    System.out.println("Deleted.");
}
```

**Checkpoint:** Deleted item disappears from View Products.

---

## Flow 8 → Input validation helpers → No crash on wrong inputs

### Teach
- Users may type letters instead of numbers
- Use helper methods that repeat until valid

### Code changes (Stage 8)
**A) Add these helper methods:**
```java
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
```

**B) Replace direct parses gradually. Examples:**
```java
int choice = readInt(sc, "Choose: ");
int id = readInt(sc, "ID: ");
double price = readDouble(sc, "Price: ");
int qty = readInt(sc, "Quantity: ");
```

**Checkpoint:** Typing invalid numbers no longer crashes.

---

## Error Handling Examples (Practical)

Use these mini-examples during Flow 8 to demonstrate **why** we add validation and `try/catch` patterns.

### Example 1 — Invalid Number Input (Before vs After)

**Before (crashes if user types letters):**
```java
System.out.print("Quantity: ");
int qty = Integer.parseInt(sc.nextLine()); // if input is "abc" → crash
```

**After (safe; keeps asking until valid):**
```java
int qty = readInt(sc, "Quantity: ");
```

**Sample console run (safe version):**
```
Quantity: abc
Invalid number. Try again.
Quantity: -5
(accepted as number, but you can still validate non-negative separately)
Quantity: 10
```

---

### Example 2 — Non-Negative Validation (Price/Qty must not be negative)

Add this inside `addProduct(...)` after reading values:

```java
double price = readDouble(sc, "Price: ");
int qty = readInt(sc, "Quantity: ");

if (price < 0 || qty < 0) {
    System.out.println("Error: Price and quantity must be non-negative.");
    return;
}
```

**Sample console run:**
```
Price: -99
Quantity: 5
Error: Price and quantity must be non-negative.
```

---

### Example 3 — Duplicate ID Handling (Business rule validation)

Use this check to prevent duplicates:

```java
int id = readInt(sc, "ID: ");
if (indexOfId(id) != -1) {
    System.out.println("Error: ID already exists. Try another ID.");
    return;
}
```

**Sample console run:**
```
ID: 101
Error: ID already exists. Try another ID.
```

---

### Example 4 — “Not Found” Handling (Search/Update/Delete)

Always guard update/delete with a “not found” message:

```java
int id = readInt(sc, "Enter ID: ");
int idx = indexOfId(id);

if (idx == -1) {
    System.out.println("Error: Product not found.");
    return;
}
```

**Sample console run:**
```
Enter ID: 999
Error: Product not found.
```

---

### Example 5 — File Save Error Handling (Try/Catch)

Show that file operations can fail (permissions, locked file, invalid path).  
This pattern prevents the program from crashing:

```java
static void saveToFile() {
    BufferedWriter bw = null;
    try {
        bw = new BufferedWriter(new FileWriter(DATA_FILE));
        bw.write("id,name,price,qty");
        bw.newLine();
        // ... write data ...
        System.out.println("Saved to " + DATA_FILE);
    } catch (IOException e) {
        System.out.println("Error saving file: " + e.getMessage());
    } finally {
        try { if (bw != null) bw.close(); } catch (IOException ignored) {}
    }
}
```

**Sample console run (if file write fails):**
```
Error saving file: Access is denied
```

---

### Quick Teaching Tip (1–2 minutes)
Ask learners to intentionally type:
- letters where numbers are expected (`abc`)
- a negative price (`-50`)
- a duplicate ID
- an ID that doesn’t exist (`999`)

Then show how each error is handled **without crashing**.


---

## Flow 9 (Optional) → File Handling → Save/Load CSV

### Teach
- Save data to a CSV file
- Load file on startup

### Code changes (Stage 9)
**A) Add imports if not present:**
```java
import java.io.*;
import java.util.Scanner;
```

**B) Add constant near top:**
```java
static final String DATA_FILE = "inventory.csv";
```

**C) Add menu option:**
```java
System.out.println("[6] Save to File");
```

**D) Add case:**
```java
case 6:
    saveToFile();
    break;
```

**E) Call load on startup (inside `main` after Scanner is created):**
```java
loadFromFile();
```

**F) Add methods:**
```java
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
            if (first) { first = false; continue; }
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
        System.out.println("Warning: could not load file properly.");
        count = 0;
    } finally {
        try { if (br != null) br.close(); } catch (IOException ignored) {}
    }
}
```

**Checkpoint:** Items persist even after program restarts.

---

## Compile and Run (Java 8+)

```bash
javac Main.java
java Main
```

If you need Java 8 compatibility while using a newer JDK:
```bash
javac -source 1.8 -target 1.8 Main.java
java Main
```

---

## Suggested Teaching Notes (Fast Delivery)

- After each flow: run the program and test quickly.
- Keep inputs simple (IDs like 101, names like "Cement", etc.)
- Emphasize: arrays are temporary storage; file handling makes it persistent.
