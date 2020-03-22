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
            <tr>
                <th>
                    <input type="checkbox" class="checkbox" onchange="setGeneralCheckbox()">
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
        <input type="button" class="button" value="Add" id="add">
        <input type="button" id="editButt" class="button" value="Edit" disabled>
        <input type="button" id="deleteButt" class="button" value="Delete" disabled>
    </div>


    <div id="addWindow">
        <form action="/add" method="POST">
            <div id="addWindowContent">
                <span class="close">X</span>
                Name: <input type="text" name="name" id="name" required>
                Description: <input type="text" name="description" id="description" required>
                <br>
                <button type="submit" id="submitAdd">Add</button>
            </div>
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

        window.onclick = function (event) {
            if (event.target == addWindow) {
                addWindow.style.display = "none";
            }
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

    </script>

</div>
</body>
</html>
