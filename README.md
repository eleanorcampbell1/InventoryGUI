# My Personal Project
### by Eleanor Campbell

## Department Store App

This application is inspired by the time I spent working in a department store and the negative effect I found its lack 
of an efficient price-checking system had on a customer's check out speed. Rather than relying on an employee searching
the store for any item of similarity, the Department Store App seeks to facilitate price-checking through the
use of an inventory.

With this application, cashiers would be able to *easily* price-check an item using its attributes and obtain a
price within a minute. Its inventory feature will build a database of items to compare to, which employees would also 
be able to isolate, add and delete items from. 


## User Stories
- As a user, I want to be able to price-check an item.
- As a user, I want to be able to add items to the inventory.
- As a user, I want to be able to remove items from the inventory.
- As a user, I want to be able to view specific items in the inventory.
- As a user, I want to be given the option to save the inventory I've built.
- As a user, I want the last saved inventory to be loaded when the program starts.

### Phase 4: Task 2
Below is a representative sample of the events that are logged when the Department Store App is run.
The inventory is loaded with the items last saved and then the user added the items named "Mini Skirt" and 
"Winter Boots" then removed the item "legs".
>Thu Nov 25 20:17:23 PST 2021  
Pembrooke Blouse has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Levi Jeans has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Checkered Shirt has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Rustic Chair has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Modern Coffee Table has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Fit For a Queen Bed has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Cool Graphic Tee has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
LaZBoy Recliner has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Night-Night Kid's Bed has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Champion Hoodie has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Shorts has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
legs has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Tights has been added to inventory.  
Thu Nov 25 20:17:23 PST 2021  
Air Jordan Sneakers has been added to inventory.  
Thu Nov 25 20:18:28 PST 2021  
Mini Skirt has been added to inventory.  
Thu Nov 25 20:19:32 PST 2021  
Winter Boots has been added to inventory.  
Thu Nov 25 20:19:37 PST 2021  
legs has been removed from inventory.  

### Phase 4: Task 3

The one thing I would do to improve my design would be to remove the association between the PriceCheckTool and 
Inventory class and have the PriceCheckTool rely only on the DepartmentStoreUI class. A possibility would be to
implement a method that clones the original inventory (pre-price check) in the DepartmentStoreUI class and then call it
from the PriceCheckTool class.# InventoryGUI
