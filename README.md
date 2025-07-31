# ⚠️ Project Guidelines

To ensure consistent development practices across the team, please follow the rules below:

### 📁 Structure and File Separation

- **Separate HTML and CSS**: Do not write CSS inside `<style>` tags or directly in HTML files. All styling should be placed in `.css` files (e.g. `style.css`) and linked via the `<link>` tag.

---

### 🧹 Code Formatting and Consistency

- **Install and enable the following VS Code extensions:**
  - [EditorConfig for VS Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig)
  - [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) (Recommended for frontend development)

- **Enable auto-format on save**
  - Set `"editor.formatOnSave": true` in settings
  - Set Prettier as the default formatter

---

### 🧾 Coding Standards

- Use **2 spaces for indentation**
- Use **LF** as the line ending (cross-platform consistency)
- Use **UTF-8** encoding for all files
- Ensure there is **a newline at the end of every file** (handled by EditorConfig)

---

### 🔄 Git Commit Guidelines

- Write **clear commit messages** that explain the changes made

---

Using the Shared Layout

To maintain a consistent design across all pages, we use a shared layout that includes:

✅ Sidebar navigation

✅ Top navigation bar

✅ Patient info card

✅ Dynamic page title


How to Use It in Your Page?

1-Add the Shared CSS

Make sure to include the shared layout styles and your page-specific styles at the top of your HTML:

html

<link rel="stylesheet" th:href="@{/css/sharedLayout.css}">
<link rel="stylesheet" th:href="@{/css/yourPageStyles.css}">


1-Wrap Your Page Content in the Shared Layout

Use this structure in your HTML file to reuse the shared components:

html

<div class="layout-container">
  <!-- Sidebar -->
  <div th:replace="~{layouts/sharedLayout :: sidebar}"></div>

  <!-- Main Content -->
  <main class="content">
    <!-- Top Navigation -->
    <div th:replace="~{layouts/sharedLayout :: topNav}"></div>

    <!-- Patient Info Card (optional) -->
    <div th:replace="~{layouts/sharedLayout :: patientCard}"></div>

    <!-- Page-specific content -->
    <section class="page-content">
      <!-- Your specific page content goes here -->
    </section>
  </main>
</div>

3-Add a Dynamic Title

If you want to change the page title dynamically, just pass it from the controller using a model attribute.

Example Controller:

java

package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class VitalsHistoryPageController {

  @GetMapping("/vitalsHistoryPage")
  public String showVitalsHistory(Model model) {
    model.addAttribute("pageTitle", "Vitals History");
    return "vitalsHistoryPage";
  }
}
