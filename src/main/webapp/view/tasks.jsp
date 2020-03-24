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
    <title>Tasks page</title>
</head>
<body>
<div class="modal">
    <table>
        <caption>Table of tasks</caption>
        <thead>
        <tr>
            <th><input type="checkbox" id="generalCheckbox" onchange="setCheck()"></th>
            <th>Name</th>
            <th>Description</th>
            <th>Planned date</th>
            <th>Date of done</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <% if (request.getAttribute("tasks") != null) { %>
        <x:parse xml="${requestScope.tasks}" var="output"/>
        <x:forEach select="$output/tasks/task" var="task">
            <x:set var="id" select="$task/id"/>
            <tr>
                <th style="cursor:default">
                    <input type="checkbox" value="<x:out select = "$id"/>" class="checkbox"
                           onchange="setGeneralCheckbox()">
                </th>
                <td>
                    <x:out select="$task/name"/>
                </td>
                <td>
                    <x:out select="$task/description"/>
                </td>
                <td>
                    <x:out select="$task/plannedDate"/>
                </td>
                <td>
                    <x:out select="$task/dateOfDone"/>
                </td>
                <td>
                    <x:out select="$task/status"/>
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
        <form action="${pageContext.request.contextPath}/editTask" method="POST">
            <span class="close">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <input type="hidden" name="id" id="editId" value="">
            <br>
            <button type="submit">Edit</button>
        </form>
    </div>

    <div class="window" id="addWindow">
        <form action="${pageContext.request.contextPath}/addTask" method="POST">
            <span class="close">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <br>
            <button type="submit">Add</button>
        </form>
    </div>
</div>
</body>
<script>

    let addWindow = document.getElementById("addWindow");
    let editWindow = document.getElementById("editWindow");
    let addButton = document.getElementById("addButt");
    let editButton = document.getElementById("editButt");
    let deleteButton = document.getElementById("deleteButt");
    let closeButton = document.getElementsByClassName("close");

    editButton.onclick = function () {
        editWindow.style.display = "block";
        document.getElementById("editId").value = getCheckJournal()[0].value;
    }

    addButton.onclick = function () {
        addWindow.style.display = "block";
    }

    deleteButton.onclick = function () {
        if (confirm("Are you really want to delete?")) {
            let form = document.createElement('form');
            form.action = '/deleteTask';
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

    closeButton[0].onclick = function () {
        editWindow.style.display = "none";
    }


    closeButton[1].onclick = function () {
        addWindow.style.display = "none";
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
</html>
