console.log("script loaded");

// Get theme from localStorage
let currentTheme = getTheme();

// Apply theme on page load
applyTheme(currentTheme);

// Wait for DOM
document.addEventListener("DOMContentLoaded", function () {
  changeTheme();
});

// Apply theme
function applyTheme(theme) {
  const html = document.querySelector("html");

  html.classList.remove("light", "dark");
  html.classList.add(theme);

  setTheme(theme);

  // Update all labels
  const labels = document.querySelectorAll(".theme-label");
  labels.forEach(label => {
    label.textContent = theme === "light" ? "Dark" : "Light";
  });
}

// Button click
function changeTheme() {
  const buttons = document.querySelectorAll(".theme_change_btn");

  buttons.forEach(button => {
    button.addEventListener("click", () => {
      currentTheme = currentTheme === "dark" ? "light" : "dark";
      applyTheme(currentTheme);
    });
  });
}

// Save theme
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Get theme
function getTheme() {
  return localStorage.getItem("theme") || "light";
}