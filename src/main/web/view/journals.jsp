<%--
Created by IntelliJ IDEA.
User: user
Date: 28.02.2020
Time: 9:16
To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<%@ page import ="org.netcracker.students.model.*" %>
<%@ page import ="org.apache.xpath.*" %>


<html>
<head>
    <link rel="stylesheet" href="../css/journals.css">
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
        <c:import url="example.xml" var ="journalsInfo"/>
        <x:parse xml = "${requestScope.journals}" var = "output"/>
        <tr>
            <td><x:out select="$output/journals/journal[1]/name" /> </td>
        </tr>
        </tbody>
    </table>


    <div class="actions">
        <input class="button" type="button" value="Change">
        <input class="button" type="button" value="Delete">
        <input class="button" type="button" value="Add" onclick="openAddWindow()">
    </div>


    <div id="addWindow">
        <form action="/add" method="POST">
            <a href="#close" title="Закрыть" onclick="closeWindow()" class="close">X</a>
            <p2>Name: <input type="text" name="name" id="name"></p2>
            <p2>Description: <input type="text" name="description" id="description"></p2>
            <br>
            <button type="submit" id="submitAdd">Add</button>
        </form>
    </div>

    <script>
        let actionAdd = document.getElementById("addWindow");

        function openAddWindow() {
            actionAdd.style.display = "block";
        }

        function closeWindow() {
            actionAdd.style.display = "none";
        }


    </script>
</div>
</body>
</html>
