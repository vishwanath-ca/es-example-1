## es-example-1
Sample spring boot, elasticsearch project to crawl through image search from serpapi and then index the url/thumbnails into elasticsearch.

##### pre-requisites
1. Elasticsearch 8.1.2 (ES)
2. JRE >= 8
3. Gradle >= 6.0.1
4. SerpAPI Key - For now, hardcoded in the code.

#### Build
Option 1: Executable.
Option 2: Deployable in web container.

#### Setup
1. Install and Run Elasticsearch - Depends on the Operating System. Do follow instructions from https://www.elastic.co/guide/en/elasticsearch/reference/8.1/install-elasticsearch.html
2. For now, this app version does not support ES Security, so let the ES just run on 9200 port with http protocal - Defaults. Ensure port is reachable.

#### Operations/Usage
1. DELETE ES Index if needed - DELETE http://localhost:9200/resource
2. Check number of records - http://localhost:8080/server/doc/counters
3. Prepare for crawling - http://localhost:8080/server/crawl/prepare this would give you a key to continue further processing. This step just ensures no accidental redundant calls. Copy the unique code generated.
4. Initiate crawling - http://localhost:8080/server/crawl/init?key=ec25ab38-ba31-430a-8beb-5aed6eb77796&maxPages=4
5. Change value of the key, maxPages as required. Per page we would recieve 100 (Configurable later) records to index.
6. Check number of records - http://localhost:8080/server/doc/counters

Author: vishwanathp@cloudangle.in
