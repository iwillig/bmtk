(ns check-grammar
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clj-antlr.core :as antlr]
   [puget.printer :as puget])
  (:import
   (java.io File)
   (org.jsoup Jsoup)
   (org.languagetool.markup AnnotatedTextBuilder AnnotatedText)
   (org.languagetool.rules RuleMatch)
   (org.languagetool Languages)
   (org.languagetool JLanguageTool)
   (org.commonmark.node AbstractVisitor Text)
   (org.commonmark.parser Parser)
   (org.commonmark.renderer.html HtmlRenderer)))

(set! *warn-on-reflection* true)

(defn j-lang-tool
  [short-code]
  (let [lang (Languages/getLanguageForShortCode short-code)]
    (JLanguageTool. lang)))

(defn check-string
  [^JLanguageTool lang-tool ^AnnotatedText text]
  (into
   []
   (map (fn [^RuleMatch match]
          {::message  (.getMessage match)
           ::match    match
           ::from-pos (.getFromPos match)
           ::to-pos   (.getToPos match)
           ::suggested-replacments
           (seq (.getSuggestedReplacements match))}))
   (.check lang-tool text)))

(defn active-rules
  [^JLanguageTool lang-tool]
  (into []
        (.getAllActiveRules lang-tool)))

;; error reporting

;; https://github.com/tobias/validatrix/blob/master/src/validatrix/validate.clj#L142-L173



;; https://github.com/projectodd/vdx

;; https://languagetool.org/development/api/org/languagetool/markup/AnnotatedTextBuilder.html

;; import org.commonmark.node.*;
;; import org.commonmark.parser.Parser;
;; import org.commonmark.renderer.html.HtmlRenderer;

;; Parser parser = Parser.builder().build();
;; Node document = parser.parse("This is *Sparta*");
;; HtmlRenderer renderer = HtmlRenderer.builder().build();
;; renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"


(defn list-markdown-files
  [path]
  (into []
        (filter (fn [^java.io.File x]
                  (and
                   (.isFile x)
                   (str/ends-with? (.getName x) ".md"))))
        ;; Sort the files by name.
        (sort
         (file-seq
          (io/file path)))))


;; class WordCountVisitor extends AbstractVisitor {
;;     int wordCount = 0;

;;     @Override
;;     public void visit(Text text) {
;;         // This is called for all Text nodes. Override other visit methods for other node types.

;;         // Count words (this is just an example, don't actually do it this way for various reasons).
;;         wordCount += text.getLiteral().split("\\W+").length;

;;         // Descend into children (could be omitted in this case because Text nodes don't have children).
;;         visitChildren(text);
;;     }
;; }

(defn visitor
  []
  (proxy [AbstractVisitor] []
    (visit [^AbstractVisitor this ^Text text]
      (println
       text
       (into []
             (.getSourceSpans text)))
      (.visitChildren this text)
      )))

(comment


  )



(defn parse-markdown
  [^java.io.File file]
  (let [parser (.build (Parser/builder))]
    (.parse parser (slurp file))))





(comment
  (def doc
    (parse-markdown (first (list-markdown-files "chapters"))))

  (.accept doc (visitor))

  )





;;org.languagetool.markup
;;Class AnnotatedTextBuilder

;; Use this builder to create input of text with markup for LanguageTool, so that it can check only the plain text parts and ignore the markup, yet still calculate the positions of errors so that they refer to the complete text, including markup.

;; It's up to you to split the input into parts that are plain text and parts that are markup.

;; For example, text with XML markup like

;;    Here is <b>some text</b>


;; needs to be prepared like this:

;;  new AnnotatedTextBuilder()
;;    .addText("Here is ").addMarkup("<b>").addText("some text").addMarkup("</b>")
;;    .build()

(defn -main [& _args]
  (let [lang-tool (j-lang-tool "en-US")
        text (-> (AnnotatedTextBuilder.)
                 (.addMarkup "#")
                 (.addText " This is a header \n")
                 (.addText "Here is") ;; 25th position
                 (.addMarkup "***")
                 (.addText "some text")
                 (.addMarkup "***")
                 (.addText ".")
                 (.build))]
    (check-string lang-tool text)))


"[This is a goog link](http://www.google.com)"

(comment

  (check-grammar/-main)

  )
