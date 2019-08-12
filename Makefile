BASEDIR=$(PWD)

deps:
	rm -rf $(PWD)/node_modules; npm i

compile:
	npx shadow-cljs compile app web

release:
	npx shadow-cljs release app web

deploy: deps release
	gcloud app deploy
