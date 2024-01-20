
.PHONY: lint
lint:
	clojure -M:lint --lint src/ test/ dev/

.PHONY: tests
tests:
	clj -M:tests

.PHONY: rebel
rebel:
	clj -M:tests:rebel

.PHONY: outdated
outdated:
	clj -M:tests:lint:outdated
