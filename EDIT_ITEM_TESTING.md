# 🧪 EDIT ITEM - TESTING GUIDE

## Quick Test Checklist ✅

---

## 📍 **ACCESS TEST**

### Steps:
1. Launch app and login
2. From Dashboard, tap **☰** (menu button)
3. Under "Menu List", tap **"Add Item"**

### Expected:
✅ Edit Item screen opens
✅ Smooth slide-in-right animation
✅ Title shows "Edit Item"
✅ All fields visible

---

## 📸 **PHOTO UPLOAD TEST**

### Test 1: Choose Photo
1. Tap **"📁 Choose Photo"** button
2. Select image from gallery
3. **Verify**: Image appears in photo placeholder

### Test 2: Take Photo
1. Tap **"📷 Take Photo"** button
2. **Verify**: "Camera feature - Coming Soon" toast appears

---

## 📝 **ITEM DETAILS TEST**

### Test 1: Name Field
1. Tap name field
2. Type "Test Latte"
3. **Verify**: Text appears correctly

### Test 2: Category Dropdown
1. Tap category spinner
2. **Verify**: 12 categories show:
   - Choose Category*
   - Iced Coffee
   - Frappe
   - Coffee Frappe
   - Milktea Classic
   - Loreta's Specials
   - Cheesecake
   - Fruit Tea and Lemonade
   - Fruit Milk
   - Fruit Soda
   - Hot Coffee
   - Add ons
3. Select "Iced Coffee"
4. **Verify**: Selection saved

---

## 🎚️ **SIZE NAVIGATION TEST**

### Test 1: Add Size (+ Button)
1. Fill in "Small" size fields
2. Tap **+ button**
3. **Verify**: "Size: Medium" label appears
4. **Verify**: Fields are now for Medium size
5. Tap **+ button** again
6. **Verify**: "Size: Large" label appears
7. Tap **+ button** again
8. **Verify**: Toast "Maximum 3 sizes allowed"

### Test 2: Remove Size (- Button)
1. From Large, tap **- button**
2. **Verify**: Returns to Medium
3. Tap **- button** again
4. **Verify**: Returns to Small
5. Tap **- button** again
6. **Verify**: Toast "Cannot remove the first size"

### Test 3: Data Persistence
1. On Small:
   - Enter Size Name: "Short"
   - Enter Price: "78"
2. Tap **+ button** (go to Medium)
3. Tap **- button** (return to Small)
4. **Verify**: "Short" and "78" are still there

---

## 🔢 **SIZE FIELDS TEST**

### Test 1: Size Name
1. Clear field
2. Type "Grande"
3. **Verify**: Text appears

### Test 2: Cost Field
1. Try to click cost field
2. **Verify**: Field is disabled (cannot edit)
3. **Verify**: Hint text shows "₱0.00"
4. **Verify**: Helper text: "Auto calculated based on ingredients"

### Test 3: Price Field
1. Click price field
2. Type "125.50"
3. **Verify**: Number appears
4. **Verify**: Only numbers and decimal allowed

### Test 4: SKU Field
1. Click SKU field
2. Type "CL-S-001"
3. **Verify**: Text appears correctly

---

## 🧪 **INGREDIENTS TEST**

### Test 1: Add Ingredient
1. Tap **"+ Add ingredient sect..."** button
2. **Verify**: Dialog appears with "Add Ingredient" title
3. Type "Espresso Shot"
4. Tap **"Add"**
5. **Verify**: Dialog closes
6. **Verify**: "Espresso Shot" appears in list
7. **Verify**: Toast "Ingredient added"

### Test 2: Add Multiple Ingredients
1. Tap **"+ Add ingredient sect..."**
2. Add "Milk"
3. Tap **"+ Add ingredient sect..."**
4. Add "Ice"
5. **Verify**: All 3 ingredients show in list

### Test 3: Remove Ingredient
1. On "Milk" ingredient, tap **X button**
2. **Verify**: "Milk" removed from list
3. **Verify**: Other ingredients remain

### Test 4: Empty Ingredient Name
1. Tap **"+ Add ingredient sect..."**
2. Leave field empty
3. Tap **"Add"**
4. **Verify**: Toast "Please enter ingredient name"
5. **Verify**: Dialog doesn't close

### Test 5: Cancel Add Ingredient
1. Tap **"+ Add ingredient sect..."**
2. Type something
3. Tap **"Cancel"**
4. **Verify**: Dialog closes
5. **Verify**: Ingredient not added

### Test 6: Ingredients Per Size
1. On Small, add "Espresso Shot"
2. Tap **+ button** (go to Medium)
3. **Verify**: Ingredient list is empty
4. Add "Double Espresso"
5. Tap **- button** (back to Small)
6. **Verify**: "Espresso Shot" is still there

---

## 💾 **SAVE FUNCTIONALITY TEST**

### Test 1: Save Without Name
1. Leave name field empty
2. Select category
3. Tap **"Save"**
4. **Verify**: Toast "Please enter item name"
5. **Verify**: Stays on Edit Item screen

### Test 2: Save Without Category
1. Enter name "Test Item"
2. Leave category as "Choose Category*"
3. Tap **"Save"**
4. **Verify**: Toast "Please select a category"
5. **Verify**: Stays on Edit Item screen

### Test 3: Successful Save
1. Enter name "Test Latte"
2. Select category "Iced Coffee"
3. Fill in size details
4. Tap **"Save"**
5. **Verify**: Toast "Item saved successfully! ✅"
6. **Verify**: Returns to Dashboard
7. **Verify**: Smooth slide animation

---

## 🔄 **NAVIGATION TEST**

### Test 1: Back Button
1. Fill in some fields
2. Tap **← back button**
3. **Verify**: Returns to Dashboard
4. **Verify**: Smooth animation

### Test 2: Android Back Button
1. Fill in some fields
2. Press Android back button
3. **Verify**: Returns to Dashboard
4. **Verify**: Smooth animation

---

## 🎨 **UI/UX TEST**

### Visual Elements:
✅ Photo placeholder is 120x120dp rounded
✅ Brown buttons use #8B6F47 color
✅ Helper text is gray (#999999)
✅ Cost field is visibly disabled
✅ Size icon displays correctly
✅ Current size label shows in brown
✅ Ingredient cards have rounded corners
✅ Remove buttons are red

### Interactions:
✅ All text fields accept input
✅ Spinner opens dropdown
✅ Buttons have ripple effect
✅ ScrollView scrolls smoothly
✅ Dialog appears centered
✅ Toast messages are readable

---

## 🔍 **EDGE CASES TEST**

### Test 1: Very Long Name
1. Enter a very long item name (50+ characters)
2. **Verify**: Text wraps or scrolls

### Test 2: Very Long Ingredient Name
1. Add ingredient with long name
2. **Verify**: Displays correctly in card

### Test 3: Decimal Prices
1. Enter price "125.99"
2. **Verify**: Accepts decimal
3. Enter price "125.999"
4. **Verify**: Handles correctly

### Test 4: Special Characters in SKU
1. Enter SKU "CL-S#001"
2. **Verify**: Accepts special characters

### Test 5: Rapid Button Tapping
1. Tap + button rapidly
2. **Verify**: Doesn't skip sizes
3. **Verify**: No crashes

---

## 📊 **COMPLETE TEST RESULTS**

| Feature | Test | Expected Result |
|---------|------|-----------------|
| Access | Dashboard → Add Item | ✅ Opens correctly |
| Photo | Choose from gallery | ✅ Image loads |
| Photo | Take photo button | ✅ Shows toast |
| Name | Text input | ✅ Accepts text |
| Category | Dropdown | ✅ 12 options |
| Size + | Navigate forward | ✅ Changes size |
| Size - | Navigate backward | ✅ Changes size |
| Size Data | Persistence | ✅ Data saved |
| Size Name | Input | ✅ Accepts text |
| Cost | Disabled field | ✅ Cannot edit |
| Price | Number input | ✅ Accepts numbers |
| SKU | Text input | ✅ Accepts text |
| Add Ingredient | Dialog | ✅ Opens dialog |
| Ingredient | Add | ✅ Adds to list |
| Ingredient | Remove | ✅ Removes from list |
| Ingredient | Empty name | ✅ Shows error |
| Per Size | Different lists | ✅ Independent |
| Save | No name | ✅ Shows error |
| Save | No category | ✅ Shows error |
| Save | Valid data | ✅ Saves & returns |
| Back | Navigation | ✅ Returns smoothly |

---

## 🎯 **SUCCESS CRITERIA**

All of these must work:
- ✅ Edit Item screen opens from Dashboard
- ✅ Photo can be selected from gallery
- ✅ Item name can be entered
- ✅ Category can be selected from 12 options
- ✅ Can navigate between 3 sizes (Small, Medium, Large)
- ✅ Size data persists when switching
- ✅ Ingredients can be added and removed
- ✅ Each size has independent ingredients
- ✅ Save validates required fields
- ✅ Successful save returns to Dashboard
- ✅ Back button works correctly
- ✅ All animations are smooth

---

## 🐛 **KNOWN ISSUES (To Ignore)**

These are classpath warnings (will resolve on build):
- ❌ "package R does not exist"
- ❌ "cannot find symbol"
- ❌ "is not on classpath"

---

## ✨ **IF EVERYTHING WORKS:**

**CONGRATULATIONS!** 🎉

Your Edit Item page is:
- ✅ Fully functional
- ✅ Matches design perfectly
- ✅ Ready for data integration
- ✅ User-friendly and intuitive

**Next Steps:**
- Add database integration
- Implement camera functionality
- Add auto cost calculation
- Connect to inventory system

---

**Happy Testing! 🧪✨**

Access: **Dashboard → ☰ → Add Item**

