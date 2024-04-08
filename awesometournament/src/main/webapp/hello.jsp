<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
      <title>Hello page</title>
    </head>

    <body>
      <h1>Hello, 
        <c:out value="${group}" />
      </h1>

      <p>It's currently
        <c:out value="${date}" />.
      </p>
      <div>
        Components:
        <ul>
          <c:forEach items="${names}" var="name">
            <li>
              <c:out value="${name}" />
            </li>
          </c:forEach>
        </ul>
      </div>

    </body>

    </html>