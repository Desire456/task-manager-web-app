<%--
Created by IntelliJ IDEA.
User: user
Date: 28.02.2020
Time: 9:16
To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>



<html>
<head>
    <link rel="stylesheet" href = "../css/journals.css">
    <meta charset="UTF-8"/>
    <title>Journals page</title>
</head>
<body>
<div class="modal">
    <table>
        <caption>Table of Journals</caption>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Creation date</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <% if(request.getAttribute("journals") != "null") {%>
        <x:parse xml = "${requestScope.journals}" var = "output"/>
        <x:forEach select="$output/journals/journal" var ="journal">
        <tr>
            <td>
                <x:out select="$journal/name" />
            </td>
            <td>
                <x:out select="$journal/description" />
            </td>
            <td>
                <x:out select="$journal/creationDate" />
            </td>
        </tr>
        </x:forEach>
        <%}%>
        </tbody>
    </table>


    <div class="actions">
        <input class="button" type="button" value="Add" id = "add">
        <input class="button" type="button" value="Edit">
        <input class="button" type="button" value="Delete">
    </div>


    <div id="addWindow">
        <form action="/add" method="POST">
            <span class ="close">X</span>
            Name: <input type="text" name="name" id="name" required>
            Description: <input type="text" name="description" id="description" required>
            <br>
            <button type="submit" id="submitAdd">Add</button>
        </form>
    </div>

    <script>
        let addWindow = document.getElementById("addWindow");
        let addButton = document.getElementById("add");
        let closeButton = document.getElementsByClassName("close")[0];

        function close(event) {
            if (event.target === addWindow) {
                addWindow.style.display = "none";
            }
        }

        addButton.onclick = function () {
            addWindow.style.display = "block";
        }

        closeButton.onclick = function () {
            addWindow.style.display = "none";
        }

        window.onclick = close;
        let actions = document.getElementsByClassName("actions")[0];
        let modal = document.getElementsByClassName("modal")[0];
        actions.onclick = close;
        modal.onclick = close;


    </script>

</div>
</body>
</html>
