<%--
Created by IntelliJ IDEA.
User: user
Date: 28.02.2020
Time: 9:16
To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


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
            <th><input type="checkbox" id="generalCheckbox" onchange="setCheck()"></th>
            <th>Name</th>
            <th>Description</th>
            <th>Creation date</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <% if (request.getAttribute("journals") != null) { %>
        <x:parse xml="${requestScope.journals}" var="output"/>
        <x:forEach select="$output/journals/journal" var="journal">
            <x:set var="id" select="$journal/id"/>
            <tr>
                <th>
                    <input type="checkbox" value="<x:out select = "$id"/>" class="checkbox"
                           onchange="setGeneralCheckbox()">
                </th>
                <td>
                    <x:out select="$journal/name"/>
                </td>
                <td>
                    <x:out select="$journal/description"/>
                </td>
                <td>
                    <x:out select="$journal/creationDate"/>
                </td>
            </tr>
        </x:forEach>
        <%}%>
        </tbody>
    </table>


    <div class="actions">
        <input type="button" id="addButt" class="button" value="Add">
        <input type="button" id="editButt" class="button" value="Edit" disabled>
        <input type="button" id="deleteButt" class="button" value="Delete" disabled>
    </div>

    <div class="window" id="editWindow">
        <form action="/edit" id="editForm" method="POST">
            <span class="close">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <input type="hidden" name="id" id = "editId" value = "">
            <br>
            <button type="submit">Edit</button>
        </form>
    </div>

    <div class="window" id="addWindow">
        <form action="/add" method="POST">
            <span class="close">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <br>
            <button type="submit">Add</button>
        </form>
    </div>

    <script>
        let addWindow = document.getElementById("addWindow");
        let editWindow = document.getElementById("editWindow");
        let addButton = document.getElementById("addButt");
        let editButton = document.getElementById("editButt");
        let closeButton = document.getElementsByClassName("close");

        editButton.onclick = function () {
            editWindow.style.display = "block";
            document.getElementById("editId").value = getCheckJournal();
        }

        addButton.onclick = function () {
            addWindow.style.display = "block";
        }

        closeButton[0].onclick = function () {
            editWindow.style.display = "none";
        }


        closeButton[1].onclick = function () {
            addWindow.style.display = "none";
        }


        function setCheck() {
            let generalCheckbox = document.getElementById("generalCheckbox");
            let checkboxes = document.getElementsByClassName("checkbox");
            let countChecked = 0;
            if (generalCheckbox.checked) {
                for (let i = 0; i < checkboxes.length; ++i) {
                    checkboxes[i].checked = true;
                }
                countChecked = checkboxes.length;
            } else {
                for (let i = 0; i < checkboxes.length; ++i) {
                    checkboxes[i].checked = false;
                }
                countChecked = 0;
            }
            setDisabledAttribute(countChecked);
        }

        function setDisabledAttribute(countChecked) {
            let editButton = document.getElementById("editButt");
            let deleteButton = document.getElementById("deleteButt");
            if (countChecked === 0) {
                editButton.disabled = true;
                deleteButton.disabled = true;
            } else if (countChecked === 1) {
                editButton.disabled = false;
                deleteButton.disabled = false;
            } else if (countChecked > 1) {
                editButton.disabled = true;
                deleteButton.disabled = false;
            }
        }

        function setGeneralCheckbox() {
            let generalCheckbox = document.getElementById("generalCheckbox");
            let checkboxes = document.getElementsByClassName("checkbox");
            let countChecked = 0;
            for (let i = 0; i < checkboxes.length; ++i) {
                if (!checkboxes[i].checked) {
                    generalCheckbox.checked = false;
                } else {
                    ++countChecked;
                }
            }
            setDisabledAttribute(countChecked);
        }

        function getCheckJournal() {
            let checkboxes = document.getElementsByClassName("checkbox");
            for (let i = 0; i < checkboxes.length; ++i) {
                if (checkboxes[i].checked) {
                    return checkboxes[i].getAttribute("value");
                }
            }
        }

    </script>

</div>
</body>
</html>
