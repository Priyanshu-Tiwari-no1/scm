console.log("script loaded");

let currentTheme = getTheme();
applyTheme(currentTheme);

function applyTheme(theme) {
  const html = document.querySelector("html");

  // Remove both themes just to ensure clean state
  html.classList.remove("light", "dark");
  html.classList.add(theme);

  // Save theme to localStorage
  setTheme(theme);

  // Update button label
  const label = document.querySelector("#theme_change_btn span");
  if (label) label.textContent = theme === "light" ? "Dark" : "Light";
}

function changeTheme() {
  const changeThemeButton = document.querySelector("#theme_change_btn");

  if (!changeThemeButton) {
    console.error("Theme change button not found!");
    return;
  }

  changeThemeButton.addEventListener("click", () => {
    console.log("Theme change button clicked");

    // Toggle theme
    currentTheme = currentTheme === "dark" ? "light" : "dark";

    // Apply the new theme
    applyTheme(currentTheme);
  });
}

changeTheme();

// LocalStorage helpers
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  const theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
