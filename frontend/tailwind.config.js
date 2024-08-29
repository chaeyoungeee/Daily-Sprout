module.exports = {
  mode: "jit",
  content: ["./src/**/**/*.{js,ts,jsx,tsx,html,mdx}", "./src/**/*.{js,ts,jsx,tsx,html,mdx}"],
  darkMode: "class",
  theme: {
    screens: { md: { max: "1050px" }, sm: { max: "550px" } },
    extend: {
      colors: {
        black: { 900: "#09090a", "900_3f": "#0000003f" },
        gray: { 500: "#a1a1aa", 800: "#3f3f46", 900: "#18181b", "900_01": "#27272a" },
        green: {
          400: "#4ade80",
          700: "#16a34a",
          800: "#15803d",
          900: "#166534",
          "900_01": "#14532d",
          a200: "#86efac",
          a700: "#22c55e",
        },
        teal: { 900: "#064e3b" },
        white: { a700: "#ffffff" },
      },
      boxShadow: { xs: "2.78px 11.12px 16px 0 #0000003f" },
      fontFamily: { inter: "Inter" },
    },
  },
  plugins: [require("@tailwindcss/forms")],
};
