# Thesis

## PREPARE

1. Install tex: `wget https://mirrors.rit.edu/CTAN/systems/mac/mactex/BasicTeX.pkg`
2. Config environment variable: `export PATH="$PATH:/usr/local/texlive/2024basic/bin/universal-darwin"`
3. Install packages: `sudo tlmgr install scheme-full && sudo tlmgr update --all --self`
4. Get and build xduts: `git clone https://github.com/note286/xduts && cd xduts && xetex xduts.ins && l3build doc`

## UPDATE

- Update xduts by git-pull: `cd thesis(xduts) && git pull && xetex xduts.ins`
- Update xduts by git-download:
  - `git clone https://github.com/note286/xduts`
  - check update files
  - only xduts.dtx: `mv xduts/xduts.dtx thesis/xduts.dtx`
  - more changed files: `mv xduts/xxx thesis/xxx`
  - `cd thesis(xduts) && xetex xduts.ins`

## WRITE

1. Refer: xduts.pdf
2. Write: main.tex
3. Compile: `xelatex main.tex && biber main && xelatex main.tex`
4. Auto Compile: `latexmk -pdf -xelatex -pvc main.tex` (Or use: Vsce-latexWorkshop)

<!-- ## WORD

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
``` -->

## TODO

- [x] 第二章位置调整
- [x] 非功能需求 to 性能测试
- [x] 测试章节 1 环境 2 3
- [x] 软件实现 类图、序列图（具体到类）
- [x] 章介绍，至少 3 行
- [x] 图片位置调整，尽量不要有空行
- [ ] 上次检测后的 Times 字体问题

## NOTE

### LATEX 模版

https://github.com/note286/xduts

### 外文翻译

外文翻译 10000+ 单词

### 论文文字复制比

文字复制比 ≤30% 为合格，若文字复制比 >30%，则该论文直接判定为重做。优秀毕业论文的论文文字复制比应 ≤10%。

### 论文格式检查

学校 SPOC 平台智能分析学生毕业论文格式是否符合撰写规范及相关国家标准《GB7713 学位论文撰写格式》。

## Template

普通表格

```tex
\begin{longtblr}
    [
    caption        = {用户表 (t\_user)},
    label          = {tab:user}
    ]
    {
        colspec={Q[c,m]X[c,m]},
        hlines,vlines,cell{2-Z}{1}={},
        row{1}         = {font=\bfseries},
        rowhead        = 1,
    }
```

普通表格（用例相关表，第一列加粗）

```tex
\begin{longtblr}
[
caption        = {},
label          = {}
]
{
    colspec={Q[c,m]X[c,m]},
    hlines,vlines,cell{2-Z}{1}={},
    cell{1-Z}{1}={font=\bfseries},
    cell{1-Z}{2}={halign=l}
}
```


三线表格:

```tex
\begin{longtblr}
[
caption        = {},
label          = {}
]
{
colspec        = {Q[c,m]X[l,m]},
hline{1,Z}     = {wd=.08em},
hline{2}       = {wd=.05em},
row{even[2-Z]} = {bg=gray9!50},
row{1}         = {font=\bfseries},
rowhead        = 1,
}
```