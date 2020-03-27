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
    <link rel="stylesheet" href="../css/style.css">
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
                <th style="cursor:default">
                    <input type="checkbox" value="<x:out select="$id"/>" class="checkbox"
                           onchange="setGeneralCheckbox()">
                </th>
                <td onclick="return goToJournal(<x:out select="$id"/>)">
                    <x:out select="$journal/name"/>
                </td>
                <td onclick="goToJournal(<x:out select="$id"/>)">
                    <x:out select="$journal/description"/>
                </td>
                <td onclick="goToJournal(<x:out select="$id"/>)">
                    <x:out select="$journal/creationDate"/>
                </td>
            </tr>
            <div class="window" id ="editWindow<x:out select="$id"/>">
                <form action="${pageContext.request.contextPath}/editJournal" method="POST">
                    <span class = "close" id = "close<x:out select="$id"/>">X</span>
                    Name: <input type="text" name="name" id="editName" value="<x:out select="$journal/name"/>" required>
                    Description: <input type="text" name="description" id="editDescription"
                                        value="<x:out select="$journal/description"/>" required>
                    <input type="hidden" name="id" id = "editId<x:out select="$id"/>" value="">
                    <br>
                    <button type="submit">Edit</button>
                </form>
            </div>
            </x:forEach>
            <%}%>
        </tbody>
    </table>

    <div class="actions">
        <input type="button" id="addButt" class="button" value="Add">
        <input type="button" id="editButt" class="button" value="Edit" disabled>
        <input type="button" id="deleteButt" class="button" value="Delete" disabled>
    </div>

    <div class="window" id="addWindow">
        <form action="${pageContext.request.contextPath}/addJournal" method="POST" onsubmit="return checkParameter()">
            <span class="close" id = "addClose">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <br><br><br><br>
            Access Modifier: <input type="text" name="accessModifier" id = "accessId" required>
            <button type="submit">Add</button>
        </form>
    </div>


    <script>
        let addWindow = document.getElementById("addWindow");
        let addButton = document.getElementById("addButt");
        let editButton = document.getElementById("editButt");
        let deleteButton = document.getElementById("deleteButt");

        editButton.onclick = function () {
            let id = getCheckJournal()[0].value;
            document.getElementById("editId" + id).value = id;
            document.getElementById("editWindow" + id).style.display = "block";
            let closeButton = document.getElementById("close" + id);
            closeButton.onclick = function () {
                document.getElementById("editWindow" + id).style.display = "none";
            }
        }

        addButton.onclick = function () {
            addWindow.style.display = "block";
        }

        deleteButton.onclick = function () {
            if (confirm("Are you really want to delete?")) {
                let form = document.createElement('form');
                form.action = '/deleteJournal';
                form.method = 'POST';

                form.innerHTML = '<input type = "hidden" name="ids" id = "deleteIds" value="">';
                let strIds = "";
                let ids = getCheckJournal();
                for (let i = 0; i < ids.length; ++i) {
                    strIds += ids[i].value + " ";
                }
                document.body.append(form);
                document.getElementById("deleteIds").value = strIds;
                form.submit();
            }
        }

        let addCloseButton = document.getElementById("addClose");
        addCloseButton.onclick = function () {
            addWindow.style.display = "none";
        }


        function checkParameter() {
            let accessModifier = document.getElementById("accessId").value;
            if (accessModifier !== "private" && accessModifier !== "public") {
                alert("Value of access modifier is only 'public' or 'private'");
                return false;
            }
            else return true;
        }

        function goToJournal(id) {
            if (confirm("Are you really want to open this journal?")) {
                let form = document.createElement('form');
                form.action = '/tasks';
                form.method = 'POST';
                form.innerHTML = '<input type = "hidden" name="journalId" id = "journalId" value="">';
                document.body.append(form);
                document.getElementById("journalId").value = id;
                form.submit();
            }
        }


        function setCheck() {
            let generalCheckbox = document.getElementById("generalCheckbox");
            let checkboxes = document.getElementsByClassName("checkbox");
            let countChecked;
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
            let checkedCheckboxes = [];
            for (let i = 0; i < checkboxes.length; ++i) {
                if (checkboxes[i].checked) {
                    checkedCheckboxes.push(checkboxes[i]);
                }
            }
            return checkedCheckboxes;
        }

    </script>

</div>
</body>
</html>
