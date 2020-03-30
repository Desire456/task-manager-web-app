<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
Created by IntelliJ IDEA.
User: user
Date: 28.02.2020
Time: 9:16
To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
    String dateTimeFormat = "yyyy-MM-dd HH:mm";
    LocalDateTime dateTimeNow = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    String formatDateTimeNow = dateTimeNow.format(formatter);
    formatDateTimeNow = formatDateTimeNow.replace(" ", "T");
%>


<html lang="en">
<head>
    <link rel="stylesheet" href="../css/style.css">
    <meta charset="UTF-8"/>
    <title>Tasks page</title>
</head>
<body>
<div class="modal">
    <table>
        <caption>Table of tasks</caption>
        <thead>
        <tr>
            <th data-type = "checkbox"><input type="checkbox" id="generalCheckbox" onchange="setCheck()"></th>
            <th data-type = "text">Name</th>
            <th data-type = "text">Description</th>
            <th data-type = "date">Planned date</th>
            <th data-type = "date">Date of done</th>
            <th data-type = "text">Status</th>
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
            <div class="window" id = "editWindow<x:out select="$id"/>">
                <form action="${pageContext.request.contextPath}/editTask" method="POST">
                    <span class="close" id = "close<x:out select="$id"/>">X</span>
                    Name: <input type="text" name="name" id="editName" value="<x:out select="$task/name"/>" required>
                    Description: <input type="text" name="description" id="editDescription"
                                        value="<x:out select="$task/description"/>" required>
                    <input type="hidden" name="id" id = "editId<x:out select="$id"/>" value="">
                    <input type = "hidden" name = "journalId" value=<%=request.getAttribute("journalId")%>>
                    <br><br><br><br>
                    Planned date: <input type="datetime-local" min="<%=formatDateTimeNow%>"
                                         name="plannedDate" id="editPlannedDate"
                                         value="<x:out select = "$task/formattedPlannedDate"/>" required>
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
        <form action="${pageContext.request.contextPath}/addTask" method="POST">
            <span class="close" id = "addClose">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <input type = "hidden" name = "journalId" value=<%=request.getAttribute("journalId")%>>
            <br><br><br><br>
            Planned date: <input type="datetime-local" name="plannedDate" min=<%=formatDateTimeNow%> required>
            <button type="submit">Add</button>
        </form>
    </div>
</div>
</body>
<script>
    const table = document.querySelector("table");
    let colIndex = -1;


    const sortTable = function (index, type, isSorted) {
        if (type === 'checkbox') return;
        const tbody = table.querySelector('tbody');
        const parseDate = function(rowData) {
            let mas = rowData.split(' ');
            return mas[20].split('-').reverse().join('-').concat(' ' + mas[21]);
        };
        const compare = function (rowA, rowB) {
            const rowDataA = rowA.cells[index].innerHTML;
            const rowDataB = rowB.cells[index].innerHTML;

            switch (type) {
                case 'text':
                    const dataA = rowDataA.toString();
                    const dataB = rowDataB.toString();
                    if (dataA < dataB) return -1;
                    else if (dataA > dataB) return 1;
                    return 0;
                    break;
                case 'date':
                    const dateA = parseDate(rowDataA);
                    const dateB = parseDate(rowDataB);
                    return new Date(dateA).getTime() - new Date(dateB).getTime();
                    break;
            }
        };

        let rows = [].slice.call(tbody.rows);
        rows.sort(compare);

        if (isSorted) rows.reverse();

        table.removeChild(tbody);
        for (let i = 0; i < rows.length; ++i) {
            tbody.appendChild(rows[i]);
        }

        table.appendChild(tbody);
    };

    table.addEventListener('click', (e) => {
        const el = e.target;
        if (el.nodeName !== 'TH') return;

        const index = el.cellIndex;
        const type = el.getAttribute('data-type');

        sortTable(index, type, colIndex === index);
        colIndex = (colIndex === index) ? -1 : index;
    });

    let addWindow = document.getElementById("addWindow");
    let editWindow = document.getElementById("editWindow");
    let addButton = document.getElementById("addButt");
    let editButton = document.getElementById("editButt");
    let deleteButton = document.getElementById("deleteButt");

    editButton.onclick = function () {
        let id = getCheckTask()[0].value;
        document.getElementById("editId" + id).value = id;
        document.getElementById("editWindow" + id).style.display = "block";
        let closeButton = document.getElementById("close" + id);
        closeButton.onclick = function () {
            document.getElementById("editWindow" + id).style.display = "none";
        }
    }

    let closeButtonAdd = document.getElementById("addClose");
    closeButtonAdd.onclick = function () {
        addWindow.style.display = "none";
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
            form.innerHTML += '<input type = "hidden" name = "journalId" value=<%=request.getAttribute("journalId")%>>';
            let strIds = "";
            let ids = getCheckTask();
            for (let i = 0; i < ids.length; ++i) {
                strIds += ids[i].value + " ";
            }
            document.body.append(form);
            document.getElementById("deleteIds").value = strIds;
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

    function getCheckTask() {
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
