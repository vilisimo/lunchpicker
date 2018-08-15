# Lunch Picker
Lunch picker is an app to help you and your friends pick a next restaurant at
which to have a lunch.

Technologies:
- Java 8 is used for the main code
- Groovy 2.5.1 is used for tests
- Gradle 4.9 is used as a build tool

## Short Overview
`Lunchpicker` allows one to create restaurants and vote on them. At the start of
a new day, the app picks a restaurant that won the day before.

Anyone can create a restaurant. However, to vote on them, a user should
provide a `username` in request's header. If such user does not yet exist, it
will be automatically created.

At any point in time user can query restaurants. The result will be a sorted
array (on vote + unique votes) of restaurants. Therefore, the first restaurant
in the array is the one that is currently leading in votes.

Additionally, winners can be queried, too. These will be sorted on a date.
A specific date slice can be chosen, e.g.:
* `GET /winners?from=2018-08-08&to=2018-08-18`

## Assumptions & Shortcuts
1. **Everyone (even anonymous users) can add/remove/update restaurants.**
2. **No intricate user system is needed**. That is, any request that is issued
by a non-existing user simply creates that user in a user table. Additionally,
no authentication, authorization, etc. is needed.
3. **There can be no more than 999.99 votes.** No particular reason for this,
it just did not seem that a higher limit for a toy project would be needed.
4. **There can be no more than 65535 unique votes.** Again, no particular
reason, it could very well be `BIGINT`, but that seems a bit too much for this
project.
5. **By voting users are picking the next day's winner.** That is, if users
vote on restaurants on Tuesday, they will be picking Wednesday's winner.

## Launching Lunchpicker
### Prerequisites

The app uses MySQL as a database. Hence, you need to have a running MySQL
instance. The easiest way to do so is to clone the project, navigate to its
directory and run MySQL via `docker-compose`:

~~~
docker-compose up -d mysql
~~~

### Launching the App
There are several ways to launch the project. 

Run it via `docker-compose` (using an image on Docker Hub):

~~~
docker-compose up -d remote-lunchpicker
~~~

Run it via gradle:

~~~
./gradlew bootRun
~~~

Build the image and run it via `docker-compose`:

~~~
gradle jibDockerBuild   # will produce 'lunchpicker:1.0.0' image
docker-compose up -d lunchpicker
~~~

## API
Once an app is launched, REST API can be explored via Swagger console:

* http://localhost:8080/swagger-ui.html#/

In short, it exposes these endpoints:

* `/restaurants -- POST, GET`. Allows to create and retrieve a restaurant.
* `/restaurants/{id} -- DELETE, PATCH`. Allows to delete/update a restaurant.
* `/restaurants/{id}:vote -- POST`. Allows to vote on a restaurant.
* `/winners -- GET`. Allows to retrieve past winners.

Currently, user can only provide restaurant's name (either in POST or PATCH
request).

Example Postman requests are provided in 
[`lunchpicker.postman_collection.json`](../../blob/master/lunchpicker.postman_collection.json) file.
