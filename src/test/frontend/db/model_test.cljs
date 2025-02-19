(ns frontend.db.model-test
  (:require [cljs.test :refer [use-fixtures deftest is]]
            [frontend.db.model :as model]
            [frontend.db.config :as config]
            [frontend.handler.repo :as repo-handler]))

(defn- load-test-files [files]
  (repo-handler/parse-files-and-load-to-db! config/test-db files {:re-render? false}))

(deftest get-page-namespace-routes
  (load-test-files [{:file/path "pages/a.b.c.md"
                     :file/content "foo"}
                    {:file/path "pages/b.c.md"
                     :file/content "bar"}
                    {:file/path "pages/b.d.md"
                     :file/content "baz"}])

  (is (= '()
         (map :block/name (model/get-page-namespace-routes config/test-db "b/c")))
      "Empty if page exists"))

;; (deftest test-page-alias-with-multiple-alias
;;   []
;;   (p/let [files [{:file/path "a.md"
;;                   :file/content "---\ntitle: a\nalias: b, c\n---"}
;;                  {:file/path "b.md"
;;                   :file/content "---\ntitle: b\nalias: a, d\n---"}
;;                  {:file/path "e.md"
;;                   :file/content "---\ntitle: e\n---\n## ref to [[b]]"}]
;;           _ (-> (repo-handler/parse-files-and-load-to-db! test-db files {:re-render? false})
;;                 (p/catch (fn [] "ignore indexedDB error")))
;;           a-aliases (model/page-alias-set test-db "a")
;;           b-aliases (model/page-alias-set test-db "b")
;;           alias-names (model/get-page-alias-names test-db "a")
;;           b-ref-blocks (model/get-page-referenced-blocks test-db "b")
;;           a-ref-blocks (model/get-page-referenced-blocks test-db "a")]
;;     (are [x y] (= x y)
;;       4 (count a-aliases)
;;       4 (count b-aliases)
;;       1 (count b-ref-blocks)
;;       1 (count a-ref-blocks)
;;       (set ["b" "c" "d"]) (set alias-names))))

;; (deftest test-page-alias-set
;;   []
;;   (p/let [files [{:file/path "a.md"
;;                   :file/content "---\ntitle: a\nalias: [[b]]\n---"}
;;                  {:file/path "b.md"
;;                   :file/content "---\ntitle: b\nalias: [[c]]\n---"}
;;                  {:file/path "d.md"
;;                   :file/content "---\ntitle: d\n---\n## ref to [[b]]"}]
;;           _ (-> (repo-handler/parse-files-and-load-to-db! test-db files {:re-render? false})
;;                 (p/catch (fn [] "ignore indexedDB error")))
;;           a-aliases (model/page-alias-set test-db "a")
;;           b-aliases (model/page-alias-set test-db "b")
;;           alias-names (model/get-page-alias-names test-db "a")
;;           b-ref-blocks (model/get-page-referenced-blocks test-db "b")
;;           a-ref-blocks (model/get-page-referenced-blocks test-db "a")]
;;     (are [x y] (= x y)
;;       3 (count a-aliases)
;;       1 (count b-ref-blocks)
;;       1 (count a-ref-blocks)
;;       (set ["b" "c"]) (set alias-names))))

;; (deftest test-page-alias-without-brackets
;;   []
;;   (p/let [files [{:file/path "a.md"
;;                   :file/content "---\ntitle: a\nalias: b\n---"}
;;                  {:file/path "b.md"
;;                   :file/content "---\ntitle: b\nalias: c\n---"}
;;                  {:file/path "d.md"
;;                   :file/content "---\ntitle: d\n---\n## ref to [[b]]"}]
;;           _ (-> (repo-handler/parse-files-and-load-to-db! test-db files {:re-render? false})
;;                 (p/catch (fn [] "ignore indexedDB error")))
;;           a-aliases (model/page-alias-set test-db "a")
;;           b-aliases (model/page-alias-set test-db "b")
;;           alias-names (model/get-page-alias-names test-db "a")
;;           b-ref-blocks (model/get-page-referenced-blocks test-db "b")
;;           a-ref-blocks (model/get-page-referenced-blocks test-db "a")]
;;     (are [x y] (= x y)
;;       3 (count a-aliases)
;;       1 (count b-ref-blocks)
;;       1 (count a-ref-blocks)
;;       (set ["b" "c"]) (set alias-names))))

(use-fixtures :each
  {:before config/start-test-db!
   :after config/destroy-test-db!})

#_(cljs.test/test-ns 'frontend.db.model-test)
