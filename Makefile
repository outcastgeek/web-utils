BASEDIR=$(PWD)

deps:
	rm -rf $(PWD)/node_modules; npm i

node-repl:
	npx shadow-cljs node-repl app

compile:
	npx shadow-cljs compile app web

release:
	npx shadow-cljs release app web

deploy: deps release
	gcloud app deploy -v 1

