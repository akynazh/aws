# Paper

## PREPARE

1. Install tex: `wget https://mirrors.rit.edu/CTAN/systems/mac/mactex/BasicTeX.pkg`
2. Config environment variable: `export PATH="$PATH:/usr/local/texlive/2024basic/bin/universal-darwin"`
3. Install packages: `sudo tlmgr install scheme-full && sudo tlmgr update --all --self`
4. Get and build xduts: `git clone https://github.com/note286/xduts && cd xduts && xetex xduts.ins && l3build doc`

## WRITE

1. Refer: xduts.pdf
2. Write: main.tex
3. Compile: `xelatex main.tex && biber main && xelatex main.tex`
4. Auto Compile: `latexmk -pdf -xelatex -pvc main.tex` (Or use: Vsce-latexWorkshop)

## Word

```sh
# brew install pandoc-crossref

pandoc main.tex \
--filter pandoc-crossref \
--citeproc --csl ieee.csl \
--bibliography=reference.bib \
-M reference-section-title=Reference \
-M autoEqnLabels \
-M tableEqns \
-t docx+native_numbering \
--number-sections \
-o main.docx
```

## NOTE

### LATEX 模版

https://github.com/note286/xduts

### 外文翻译

外文翻译 10000+ 单词

### 论文文字复制比

文字复制比 ≤30% 为合格，若文字复制比 >30%，则该论文直接判定为重做。优秀毕业论文的论文文字复制比应 ≤10%。

### 论文格式检查

学校 SPOC 平台智能分析学生毕业论文格式是否符合撰写规范及相关国家标准《GB7713 学位论文撰写格式》。
