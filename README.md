# Spring Boot - Download Example

A simple Spring Boot application showing how to make CSV and PDF files available for download.

Several different request handler methods exist, each one returning a different object.  This shows that
different HttpMessageConverters are used depending on the return type.

Two custom HttpMessageConverters have been provided to handle conversion of a PersonList to CSV and
PDF output.  For the other returned entities, one of the out-of-the-box HttpMessageConvertes described
[here](http://www.baeldung.com/spring-httpmessageconverter-rest) will be chosen by Spring.

Additionally, another endpoint writes the payload directly to the HttpServletResponse.
 
For each media type, whichever endpoint is chosen, the result should be the same.

Build and run the project using:

* mvn clean install && java -jar spring-boot-download-example-0.0.1-SNAPSHOT.jar *

Then visit the webpage at http://localhost:8080.

Note: PDF downloads not currently delivered correctly through the web page.  Instead, curl can be used like so:

* curl -v http://localhost:8080/download/servletresponse -H "Accept: application/pdf" -o ~/Downloads/servletresponse.pdf *

