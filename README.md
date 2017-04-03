## Motivation

This is a project which gives an example of how to write a simple unit test of a REST API.  The main code contains an
example of a REST API using Spring Boot.  This is not the important part of this project and it really is not a good example.
It always returns:

[
  {
    "bookId": "1",
    "name": "Harry Potter and the Sorcerer's Stone",
    "author": "J.K. Rowling"
  },
  {
    "bookId": "2",
    "name": "Eragon",
    "author": "Christopher Paolini"
  },
  {
    "bookId": "3",
    "name": "The Fellowship of the Ring",
    "author": "J.R.R. Tolkien"
  }
]

The project was done to show how junit tests can be independent of spring or what ever technology was used to create
the REST API.  These test could be used for consumer driven contract tests where the idea is that a consumer of your
service might run tests and put them in your project as contract verification.

## Tests

As stated above the tests are the important part of this project.  They just use the javax.json object model API to
process JSON.  The test just has helper functions to peform a get using just HttpURLConnection.

Really there is only one real test which is the method testBooks in the class BookServicTest.  It makes sure that all
objects in the json array returned by the rest call have a bookdId.  A consumer of the service could write this test
to ensure that a bookId is always present.

To run the tests first start the spring boot application in a command line window with the following command:

./gradlew bootRun

Then I was just running the BookServiceTest from and ide (intellij).




