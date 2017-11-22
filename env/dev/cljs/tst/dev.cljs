(ns ^:figwheel-no-load tst.dev
  (:require
    [tst.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
