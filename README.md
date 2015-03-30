# Shortest Path #

This project is designed to provide the shortest path between nodes in a Logistic Network, based on Dijkstra algorithm. It expose two rest services , one for creating the Logistic Network with nodes along with their relationships and other for getting the shortest path between two giving nodes in the previous created Logistic Network.

## How it works? ##

Here is an example of how to execute those rest services.

### Create a Logistic Network ###

```http

POST /network/create
Accept: application/json
Content-Type: application/json

Creates the Logistic Network passing the content through json body.

Returns a HTTP 201 - Created
```

The Json body should have something similar like this:

```json
{
  "map":"SP",
  "routes": [
    {
      "origin":"A",
      "destination":"B",
      "distance":10
    },
    {
      "origin":"B",
      "destination":"D",
      "distance":15
    },
    {
      "origin":"A",
      "destination":"C",
      "distance":20
    },
    {
      "origin":"C",
      "destination":"D",
      "distance":30
    },
    {
      "origin":"B",
      "destination":"E",
      "distance":50
    },
    {
      "origin":"D",
      "destination":"E",
      "distance":30
    }
  ]
}
```

### Get the Shortest Path using Dijkstra algorithm ###

```http

POST /network/shortest
Accept: application/json
Content-Type: application/json

Post through json body the required information to calculate the shortest path based on Dijkstra algorithm

```

The Json body should have something similar like this:

``` json
{
  "map":"SP",
  "origin":"A",
  "destination":"D",
  "autonomy":10,
  "gasValue":2.5
}
```

Returns

```json
{
  "route":["A","B","D"],
  "cost":6.25
}
```

# The Solution #

This project makes use of Scala, Play Framework and Neo4J.
The use of these technologies is to provide the maximum scalability in a simple way based on Scala.

Play Framework is built in using Akka, a highly concurrent, distributed, and resilient message-driven applications on the JVM. Using this approach the system use as much as possible async methods, to prevent blocking threads.

Unfortunately, I couldn't found a better way to work asynchronously with neo4j, since it uses a transaction approach.

### Dependency Injection? ###

This project is not using DI, instead it makes use of the Cake Pattern design.
For more information, check out the [Cake Pattern in depth](http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth)

### Know Issues ###

When running all tests at once through SBT, the PathFinderTest will throw

```scala
Caused by org.neo4j.kernel.StoreLockException: Unable to obtain lock on store lock file
```

I still coudn't find a solution for this problem when running tests through SBT. A possible reason is since SBT tries to run all tests at onde using multiple threads, perhaps the system is trying to create more than one instance. I have already created a issue for it in this repository.

# Ok, I want to run this project on my local machine. Is there any configuration? #

Yes. To run this project you must meet the following criteria.

## Tools and Technologies ##

This project uses Scala, Sbt, Play! Framework and Neo4j.

To run this application, you must have a valid installation of Scala and SBT.
Below are all the necessary steps.

### Scala ###

Download and install Scala from [http://www.scala-lang.org](http://www.scala-lang.org) and follow their instructions. The minimum required version of Scala is 2.11

### SBT ###

Download and install the latest version of SBT (Scala Build Tool) from [http://www.scala-sbt.org/download.html](http://www.scala-sbt.org/download.html)

### Neo4j ###

The project is using an embedded neo4j database, so there is no need to have a running instance, but of course, with just a few lines of code, you can change the embedded for a single or cluster installation.

## Tests ##

This project is using the [scoverage plugin](https://github.com/scoverage/scalac-scoverage-plugin), to check code coverage.

In the SBT console, run the following commands:

```bash

clean
coverage
test
```

## Deployment Instructions ##

According to Play documentation:

```
The simplest way to deploy a Play 2.X application is to retrieve the source (typically via a git workflow) on the server and to use either sbt start or sbt stage to start it in place.

However, you sometimes need to build a binary version of your application and deploy it to the server without any dependencies on Play itself. You can do this with the dist task.

sbt dist

This produces a ZIP file containing all JAR files needed to run your application in the target folder of your application.

You can use the generated start script to run your application.
```

# Contribution guidelines #

Contribute to this repository is simple by following these simple steps:

* Writing tests - Write a test for whatever you do
* Pull Request - Fork the project and submit a pull request.
