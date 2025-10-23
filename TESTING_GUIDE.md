# 🧪 TESTING GUIDE

## Quick Testing Checklist

---

## 📸 **TEST 1: Menu Images**

### Steps:
1. Launch app and login
2. Tap **Menu** icon (bottom navigation)
3. **Verify**: All menu items show real product images
4. Scroll through categories
5. Tap heart icon on items
6. **Verify**: Favorites section shows images too

### Expected Results:
✅ All Iced Coffee items show actual coffee images
✅ All Frappe items show frappe images
✅ All Milktea items show milktea images
✅ Favorites horizontal scroll shows images
✅ No placeholder icons (except Add-ons)

---

## 📁 **TEST 2: Access Categories**

### Steps:
1. From Dashboard, tap **menu button** (☰ top-left)
2. Scroll navigation drawer
3. Under "Menu List", tap **"Add Category"**
4. **Verify**: Categories screen opens

### Expected Results:
✅ Categories screen shows
✅ Sample "Iced Coffee" category visible
✅ Shows icon, name, and "13 items"
✅ + button visible at bottom-right
✅ Smooth slide animation

---

## ➕ **TEST 3: Create Category**

### Steps:
1. On Categories screen, tap **+ button**
2. **Verify**: Dialog appears
3. Type "Coffee" in name field
4. **Verify**: 4x3 grid of icons shown (12 icons total)
5. Tap any icon
6. **Verify**: Brown overlay + checkmark appears
7. Tap another icon
8. **Verify**: Selection moves to new icon
9. Tap **"Assign Items"**

### Expected Results:
✅ Dialog displays properly
✅ Icon grid shows 4 columns, 3 rows
✅ Icons show actual menu item images
✅ Selection indicator (brown + checkmark) works
✅ Can only select one icon at a time
✅ "Assign Items" button works

---

## 🔍 **TEST 4: Assign Items with Search**

### Steps:
1. On Assign Items screen, **verify** category info at top
2. Type "Latte" in search bar
3. **Verify**: List filters to show only latte items
4. Clear search
5. **Verify**: All items return
6. Tap checkbox on "Cappuccino"
7. **Verify**: Checkbox checks
8. Tap anywhere on "Americano" row
9. **Verify**: Checkbox toggles
10. Tap "Save"

### Expected Results:
✅ Category icon and name show at top
✅ Search filters items in real-time
✅ Items show images, name, and category
✅ Checkbox works when clicked
✅ Entire row is clickable (toggles checkbox)
✅ Multiple items can be selected
✅ Save button works

---

## 📋 **TEST 5: View Existing Category**

### Steps:
1. Go back to Categories screen
2. Tap "Iced Coffee" category
3. **Verify**: Assign Items screen opens
4. **Verify**: "Iced Coffee" info shows at top

### Expected Results:
✅ Tapping category opens assign screen
✅ Category details are correct
✅ Can search and select items

---

## 🎨 **TEST 6: Icon Selection Grid**

### Steps:
1. Create new category
2. Count icons in grid
3. **Verify**: Exactly 12 icons
4. **Verify**: 4 columns, 3 rows
5. Try selecting each icon
6. **Verify**: Each shows selection indicator

### Expected Results:
✅ Grid layout: 4 columns × 3 rows = 12 icons
✅ Icons include:
   - iced_coffee_cafe_latte
   - iced_coffee_cappuccino
   - frappe_chocchip
   - frappe_cookies_and_cream
   - milktea_wintermelon
   - milktea_taro
   - cheesecake_oreo_cheesecake
   - cheesecake_strawberry_cheesecake
   - fruittea_sunrise
   - fruitmilk_strawberrymilk
   - specials_nutellalatte
   - hotcoffee
✅ All icons are selectable
✅ Selection indicator appears correctly

---

## ✨ **TEST 7: Empty States**

### Steps:
1. Create new category but cancel
2. Delete all categories (if possible)
3. **Verify**: Empty state shows with message

### Expected Results:
✅ "No Categories Yet" emoji (📁)
✅ Message: "Tap the + button to create your first category"
✅ Empty state centered on screen

---

## 🔄 **TEST 8: Navigation & Animations**

### Steps:
1. Navigate: Dashboard → Categories
2. **Verify**: Slide-in-right animation
3. Tap Back button
4. **Verify**: Slide-out animation back to Dashboard
5. Repeat with: Categories → Assign Items → Back

### Expected Results:
✅ Forward navigation: slide-in-right
✅ Back navigation: slide-out-left
✅ Smooth transitions
✅ No lag or jank

---

## 📱 **TEST 9: Menu Images by Category**

### Test Each Category:

#### Iced Coffee (13 items):
- ☕ Cappuccino → `iced_coffee_cappuccino.png`
- ☕ Americano → `iced_coffee_americano.png`
- ☕ Cafe Latte → `iced_coffee_cafe_latte.png`
- ☕ Caramel Macchiato → `iced_coffee_caramel_macchiato.png`
- ☕ Cafe Mocha → `iced_coffee_cafe_mocha.png`
- ☕ French Vanilla → `iced_coffee_french_vanilla.png`
- ☕ Hazelnut Latte (NEW) → `iced_coffee_hazelnut_latte.png`
- ☕ Salted Caramel Latte (NEW) → `iced_coffee_salted_caramel_latte.png`
- ☕ Matcha Latte → `iced_coffee_matcha_latte.png`
- ☕ Triple Chocolate Mocha → `iced_coffee_triple_chocolate_mocha.png`
- ☕ Dirty Matcha → `iced_coffee_dirty_matcha.png`
- ☕ Tiramisu Latte (NEW) → `iced_coffee_tiramisu_latte.png`
- ☕ Spanish Latte → `iced_coffee_spanish_latte.png`

#### Frappe (11 items):
- 🥤 All items should show respective frappe images

#### Milktea (11 items):
- 🧋 All items should show respective milktea images

#### Cheesecake (6 items):
- 🍰 All items should show respective cheesecake images

#### Specials (4 items):
- ⭐ All items should show respective specials images

---

## 🐛 **KNOWN ISSUES TO IGNORE**

### These are normal and will resolve when building:
- ❌ "package R does not exist" → **Ignore** (Android Studio issue)
- ❌ "cannot find symbol" → **Ignore** (classpath warning)
- ❌ "is not on classpath" → **Ignore** (will resolve on build)

### These are ACTUAL errors to fix:
- ✅ App crashes on launch
- ✅ Images don't load
- ✅ Buttons don't work
- ✅ Navigation fails

---

## ✅ **SUCCESS CRITERIA**

All of these should work:
- ✅ Menu shows 70+ items with images
- ✅ Favorites show images
- ✅ Categories list loads
- ✅ Create category dialog opens
- ✅ Icon grid shows 12 icons (4x3)
- ✅ Icon selection works with visual feedback
- ✅ Assign items screen opens
- ✅ Search filters items
- ✅ Checkboxes work
- ✅ Entire row is clickable
- ✅ Save button works
- ✅ Back navigation works
- ✅ Animations are smooth

---

## 📸 **VISUAL CHECKLIST**

### Menu Page:
- ✅ Real product photos (not placeholders)
- ✅ Heart icon for favorites (top-right of card)
- ✅ Brown + button (bottom-right of card)
- ✅ "NEW" badges on special items
- ✅ 2-column grid layout

### Categories Page:
- ✅ List of categories with icons
- ✅ "X items" count displayed
- ✅ Arrow on right side
- ✅ Brown + button floating

### Create Dialog:
- ✅ "Create Category" title
- ✅ Input field with "ex. Coffee" hint
- ✅ "Category Icon" label
- ✅ 4×3 grid = 12 icons
- ✅ Brown overlay on selected
- ✅ White checkmark on selected
- ✅ Two brown buttons at bottom

### Assign Items:
- ✅ Search bar at top
- ✅ Category info section
- ✅ Items with images
- ✅ Checkboxes aligned right
- ✅ Brown "Save" button at bottom

---

## 🎯 **QUICK TEST SEQUENCE**

**5-Minute Full Test:**

1. ✅ Open Menu → See images
2. ✅ Open Categories → See list
3. ✅ Tap + → See dialog
4. ✅ Select icon → See selection
5. ✅ Tap Assign Items → See list
6. ✅ Search "Latte" → See filtered
7. ✅ Check 3 items → See selected
8. ✅ Tap Save → Return to categories
9. ✅ Tap Back → Return to dashboard

**Done! ✅**

---

## 🆘 **TROUBLESHOOTING**

### Problem: Images don't show
**Solution**: 
- Make sure PNG files are in `app/src/main/res/drawable/`
- Check file names match exactly (case-sensitive)
- Clean and rebuild project

### Problem: Categories page doesn't open
**Solution**:
- Check `AndroidManifest.xml` has CategoriesActivity
- Verify navigation drawer setup
- Check Dashboard has correct Intent

### Problem: Icon grid doesn't show
**Solution**:
- Verify GridLayoutManager span count = 4
- Check icon list has 12 items
- Verify layout files exist

### Problem: Search doesn't work
**Solution**:
- Check TextWatcher is set up
- Verify filter logic in adapter
- Check EditText ID matches

---

## 🎉 **IF EVERYTHING WORKS:**

**CONGRATULATIONS!** 🎊

Your app is fully functional with:
- ✅ Real menu images
- ✅ Complete categories system
- ✅ Icon selection grid
- ✅ Search functionality
- ✅ Beautiful UI matching design

**You're ready to:**
- Add more features
- Connect to database
- Deploy to production
- Show to stakeholders

---

**Happy Testing! 🧪✨**

