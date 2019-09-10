BASEDIR=$(PWD)
APPNAME=web-utils
PROJECTID=web-utils-249217

deps:
	rm -rf $(PWD)/node_modules; npm i

node-repl:
	npx shadow-cljs node-repl app

compile:
	npx shadow-cljs compile app web

release:
	npx shadow-cljs release app web

# deploy:
# 	gcloud app deploy -v dev

container:
	gcloud builds submit --tag gcr.io/$(PROJECTID)/$(APPNAME)

deploy: container
	gcloud beta run deploy --image gcr.io/$(PROJECTID)/$(APPNAME) --platform managed

