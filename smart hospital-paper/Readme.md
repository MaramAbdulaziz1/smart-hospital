# 📄 README Local LaTeX Setup for the Group Paper

--

### Requirements
- VS Code
- LaTeX Workshop extension

### A LaTeX distribution:
- Windows: TeX Live (recommended), or MiKTeX
- Mac: MacTeX
- Linux: sudo apt install texlive-full (note: big size)

```bash
📁 Project Structure
smart-hospital-paper/
│
├── 📁memoirthesis.tex             <- Main file (compile this)
├── 📁frontmatter/                 <- Title, abstract, declaration, etc.
├── 📁chapters/                    <- Chapter .tex files
├── 📁thesisbiblio.bib             <- Bibliography file
# ├── 📁images/, figs/, ...           <- Figures (optional)
└── 📁memoirthesis.pdf             <- Output (after compilation)
```
<details>
<summary> How to Compile</summary>

1. Open the folder in **VS Code**
2. Open `memoirthesis.tex`
3. Press `Ctrl + Alt + B` to **build the PDF**
4. Press `Ctrl + Alt + V` to **preview the output**

💡 If `Ctrl + Alt + B` doesn’t work, run this in the terminal:

```bash
latexmk -pdf memoirthesis.tex
</details> ```
