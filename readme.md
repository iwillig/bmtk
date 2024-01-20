# Book and mapping tool kit (BMTK)

This project is a collection of command line tools that helps folks
author books in Markdown.

## Pandoc

It follows the pandoc method for building
ebooks. https://pandoc.org/MANUAL.html#epubs

Pandoc uses a yaml file metadata and folder of markdown files.

Example of metadata file
```yaml
title:
- type: main
  text: My Book
- type: subtitle
  text: An investigation of metadata
creator:
- role: author
  text: John Smith
- role: editor
  text: Sarah Jones
identifier:
- scheme: DOI
  text: doi:10.234234.234/33
publisher:  My Press
rights: © 2007 John Smith, CC BY-NC
ibooks:
  version: 1.3.4
```

## Citation Style Language

Pandoc also supports a couple of different citation styles. This tool uses, CSL YAML

https://docs.citationstyles.org/en/stable/specification.html

## Tools

- `check-grammar`: Command line tool that parses markdown and uses language tools.
- `publish pdf`: From a folder of markdown files, uses pandoc to generate.
- `publish ebook`: From a folder of markdown files.
- `index build`: Builds a SQLite Index from your markdown folder.

## Installation
### Make
### JVM
#### Install clojure-cli
Please follow the Clojure docs to install clojure-cli
[Install docs](https://clojure.org/guides/install_clojure)
#### Install JVM libraries and compile Clojure
```shell
make rebel
user=>(dev)
:ok
dev=>
```
### Mapnik (optional)
#### Ubuntu
```shell
sudo apt install libmapnik3.1 python3-mapnik libmapnik-dev
```

### Yarn
```shell
yarn
```

## Usage


### check-grammar

Checks the grammar of a folder of markdown files. Prints a list of
grammatical errors based on the language-tools library. Initial focus
is English, but language tools supports a wide range of languages.

https://dev.languagetool.org/java-api.html

TODO:

    - Rust style error reporting
    https://jvns.ca/blog/2022/12/02/a-couple-of-rust-error-messages/
    -

```shell
check-grammar --help
check-grammar --folder chapters
check-grammar --fail-fast --folder chapters
```
