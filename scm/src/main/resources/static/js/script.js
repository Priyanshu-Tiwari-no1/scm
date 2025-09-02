const themeBtn = document.getElementById("theme-change-btn");
const themeLabel = document.getElementById("theme-label");
let currentTheme = getTheme();

changeTheme(currentTheme);

themeBtn.addEventListener("click", () => {
  currentTheme = currentTheme === "light" ? "dark" : "light";
  setTheme(currentTheme);
  changeTheme(currentTheme);
});

function changeTheme(theme) {
  document.documentElement.classList.remove("light", "dark");
  document.documentElement.classList.add(theme);

  if (theme === "dark") {
    themeLabel.textContent = "Dark";
    themeLabel.style.color = "black"; // Dark text black
  } else {
    themeLabel.textContent = "Light";
    themeLabel.style.color = "black"; // Light text white
  }
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
