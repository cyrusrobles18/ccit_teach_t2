# Progressive Java Inventory App

Small, instructor-friendly inventory program for Java 8+. It’s purposely written **without classes/objects** so beginners focus on control flow, arrays, methods, validation, and simple file I/O. Use it as a live-coding demo or hands-on exercise during meetups, bootcamps, or campus events.

## Event Overview
- **Audience:** 1st Year CCIT Students 
- **Format:** Progressive flows that grow one file (`src/Main.java`) into a complete CRUD console app.
- **Assets:** (`inventory.csv`)

![Event Poster 2](lib/imgs/POSTER2.jpg)
![Event Poster 1](lib/imgs/POSTER1.jpg)


## What the App Does
- Add products with ID, name, price, quantity (parallel arrays, `MAX = 100`).
- View all products in a table-like printout.
- Search, update, and delete by ID with basic validation.
- Save to and load from `inventory.csv` (simple comma-separated file).
- Optional **[9] Load Demo Products** button to seed five construction-material items instantly.

## Run It
```bash
# from repo root
javac -d bin src/Main.java
java -cp bin Main
```
- Data file path: `inventory.csv` in the project root (auto-created on save; auto-loaded on start if present).
- Works on any Java 8+ runtime; no external libraries.

## CSV Format
```
id,name,price,qty
101,Cement (40kg),270.00,50
102,Steel Bar 10mm,185.50,120
103,Plywood 1/2 inch,720.00,30
104,Paint (White),550.00,18
105,Nails 2 inch,45.00,200
```
Feel free to replace with your own dataset; keep the header row.

## Customizing for Your Own Inventory Console
- Swap the demo items to match your domain (tech store, library, food menu).
- Tweak `MAX` if you want a larger in-memory cap.
- Hide the `[9]` menu entry once students are comfortable typing data.
- Drop the posters into your slides or registration page; paths stay stable inside the repo.
