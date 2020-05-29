<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
<body onload="showError()">
<div class="modal" style="width: 70%;
    margin-left: 15%">
    <div class="filter" style="margin: 0 30%">
        <form action="${pageContext.request.contextPath}/filterTasks" id="filterForm" method="POST">
            Column: <select name="column" required>
            <option value="name">Name</option>
            <option value="description">Description</option>
            <option value="status">Status</option>
        </select>
            Sort by <select name="sort" required>
            <option value="ASC">Ascending</option>
            <option value="DESC">Descending</option>
        </select>
            <br><br>
            By: <input type="text" name="pattern" required>
            Equal <input type="checkbox" name="equal">
            <br><br>
            <input type="submit" class="button" style="margin-left: 40%" value="Submit">
        </form>
    </div>
    <table class="table_sort">
        <caption>TABLE OF TASKS</caption>
        <thead>
        <tr>
            <th style="cursor:default"><input type="checkbox" id="generalCheckbox" onchange="setCheck()"></th>
            <th style="width:150px" title="Click this button to sort by this column">Name</th>
            <th style="width:200px" title="Click this button to sort by this column">Description</th>
            <th style="width:250px" title="Click this button to sort by this column">Planned date</th>
            <th style="width:250px" title="Click this button to sort by this column">Date of done</th>
            <th style="width:150px" title="Click this button to sort by this column">Status</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <% if (session.getAttribute("tasks") != null) { %>
        <x:parse xml="${sessionScope.tasks}" var="output"/>
        <x:forEach select="$output/tasks/task" var="task">
            <x:set var="id" select="$task/id"/>
            <tr>
                <td style="cursor:default; text-align:center">
                    <input type="checkbox" value="<x:out select = "$task/id"/>" class="checkbox"
                           status="<x:out select = "$task/status"/>" onchange="setGeneralCheckbox()">
                </td>
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
    <% if (session.getAttribute("tasks") != null) { %>
    <x:parse xml="${sessionScope.tasks}" var="output"/>
    <x:forEach select="$output/tasks/task" var="task">
        <div class="window" id="editWindow<x:out select="$task/id"/>">
            <form action="${pageContext.request.contextPath}/editTask" method="POST">
                <span class="close" id="close<x:out select="$task/id"/>">X</span>
                Name: <input type="text" name="name" value="<x:out select="$task/name"/>" required>
                Description: <input type="text" name="description"
                                    value="<x:out select="$task/description"/>" required>
                <input type="hidden" name="id" id="editId<x:out select="$task/id"/>" value="">
                <input type="hidden" name="journalId" value=<%=request.getAttribute("journalId")%>>
                <br><br><br><br>
                Planned date: <input type="datetime-local" min="<%=formatDateTimeNow%>"
                                     name="plannedDate"
                                     value="<x:out select = "$task/formattedPlannedDate"/>" required>
                <button type="submit">Edit</button>
            </form>
        </div>
    </x:forEach>
    <%}%>

    <div class="actions" style="margin-left:20%">
        <input type="button" id="addButt" class="button" value="Add">
        <input type="button" id="editButt" class="button" value="Edit" title="You can edit only one task" disabled>
        <input type="button" id="deleteButt" class="button" value="Delete" disabled>
        <input type="button" id="exportButt" class="button" value="Export" disabled>
        <input type="button" id="finishButt" class="button" value="Finish"
               title="You can finish tasks only with status PLANNED" disabled>
        <input type="button" id="showButt" class="button" value="Show all tasks">
        <input type="button" class="button" onclick="direct()" value="Return to journals page"
               style="display: block; margin-left: 30%">
    </div>


    <div class="window" id="addWindow">
        <form action="${pageContext.request.contextPath}/addTask" method="POST">
            <span class="close" id="addClose">X</span>
            Name: <input type="text" name="name" required>
            Description: <input type="text" name="description" required>
            <input type="hidden" name="journalId" value=<%=session.getAttribute("journalId")%>>
            <br><br><br><br>
            Planned date: <input type="datetime-local" name="plannedDate" min=<%=formatDateTimeNow%> required>
            <button type="submit">Add</button>
        </form>
    </div>


    <div class="window" id="exportWindow">
        <form action="${pageContext.request.contextPath}/exportTask" method="POST" id="exportForm">
            <span class="close">X</span>
            Save as: <input name="fileName" id="fileName" required>
            <input type="hidden" name="ids" id="exportIds" value="">
            <br>
            <br>
            <input type="submit" class="button" id="submitExportButt" value="Download" disabled/>
        </form>
    </div>
</div>
</body>
<script>
    let showButt = document.getElementById("showButt");
    showButt.onclick = function () {
        window.location.href = "/tasks";
    };


    function showError() {
        <%String error = (String) request.getAttribute("error");
        if (error != null) {%>
        let msgError = "<%=error%>";
        alert(msgError);
        <%}%>
    }

    function direct() {
        window.location.href = "journals";
    }

    document.addEventListener('DOMContentLoaded', () => {

        const getSort = ({target}) => {
            const order = (target.dataset.order = -(target.dataset.order || -1));
            const index = [...target.parentNode.cells].indexOf(target);
            const collator = new Intl.Collator(['en', 'ru'], {numeric: true});
            const comparator = (index, order) => (a, b) => order * collator.compare(
                a.children[index].innerHTML,
                b.children[index].innerHTML
            );

            for (const tBody of target.closest('table').tBodies)
                tBody.append(...[...tBody.rows].sort(comparator(index, order)));

            for (const cell of target.parentNode.cells)
                cell.classList.toggle('sorted', cell === target);
        };

        let ths = document.querySelector('.table_sort thead').getElementsByTagName('TH');
        for (let i = 1; i < ths.length; ++i) {
            ths[i].addEventListener('click', () => getSort(event));
        }

    });

    let addWindow = document.getElementById("addWindow");
    let editWindow = document.getElementById("editWindow");
    let addButton = document.getElementById("addButt");
    let editButton = document.getElementById("editButt");
    let deleteButton = document.getElementById("deleteButt");
    let finishButton = document.getElementById("finishButt");
    let exportButton = document.getElementById("exportButt");
    let submitExportButt = document.getElementById("submitExportButt");

    editButton.onclick = function () {
        let id = getCheckTask()[0].value;
        document.getElementById("editId" + id).value = id;
        document.getElementById("editWindow" + id).style.display = "block";
        let closeButton = document.getElementById("close" + id);
        closeButton.onclick = function () {
            document.getElementById("editWindow" + id).style.display = "none";
        }
    };

    let closeButtonAdd = document.getElementById("addClose");
    closeButtonAdd.onclick = function () {
        addWindow.style.display = "none";
    };

    addButton.onclick = function () {
        addWindow.style.display = "block";
    };

    finishButton.onclick = function () {
        if (confirm("Are you really want to finish this tasks?")) {
            let form = document.createElement('form');
            form.action = '/finishTask';
            form.method = 'POST';

            form.innerHTML = '<input type = "hidden" name="ids" id = "finishIds" value="">';
            let strIds = "";
            let ids = getCheckTaskFinish();
            for (let i = 0; i < ids.length; ++i) {
                strIds += ids[i].value + " ";
            }
            document.body.append(form);
            document.getElementById("finishIds").value = strIds;
            form.submit();
        }
    };

    deleteButton.onclick = function () {
        if (confirm("Are you really want to delete?")) {
            let form = document.createElement('form');
            form.action = '/deleteTask';
            form.method = 'POST';

            form.innerHTML = '<input type = "hidden" name="ids" id = "deleteIds" value="">';
            let strIds = "";
            let ids = getCheckTask();
            for (let i = 0; i < ids.length; ++i) {
                strIds += ids[i].value + " ";
            }
            document.body.append(form);
            document.getElementById("deleteIds").value = strIds;
            form.submit();
        }
    };

    exportButton.onclick = function () {
        document.getElementById('exportWindow').style.display = 'block';
    }
    submitExportButt.onclick = function () {
        let strIds = "";
        let ids = getCheckTask();
        for (let i = 0; i < ids.length; ++i) {
            strIds += ids[i].value + " ";
        }
        document.getElementById('exportIds').value = strIds;

        document.getElementById('exportForm').submit();
        document.getElementById('exportWindow').style.display = 'none';
    }

    let fileName = document.getElementById('fileName');

    fileName.addEventListener('input', () => submitExportButt.disabled = fileName.value === "");

    function setCheck() {
        let generalCheckbox = document.getElementById("generalCheckbox");
        let checkboxes = document.getElementsByClassName("checkbox");
        let countChecked = 0, countCheckedFinish = 0;
        let notPlannedIsChecked = false;
        if (generalCheckbox.checked) {
            for (let i = 0; i < checkboxes.length; ++i) {
                checkboxes[i].checked = true;
                if (checkboxes[i].getAttribute("status") === "PLANNED" ||
                    [i].getAttribute("status") === "DEFERRED") {
                    countCheckedFinish++;
                } else {
                    notPlannedIsChecked = true;
                }
            }
            countChecked = checkboxes.length;
        } else {
            for (let i = 0; i < checkboxes.length; ++i) {
                checkboxes[i].checked = false;
            }
            countChecked = 0;
        }
        setDisabledAttribute(countChecked, countCheckedFinish, notPlannedIsChecked);
    }

    function setDisabledAttribute(countChecked, countCheckedFinish, notPlannedIsChecked) {
        if (countChecked === 0) {
            editButton.disabled = true;
            deleteButton.disabled = true;
            finishButton.disabled = true;
            exportButton.disabled = true;
        } else if (countChecked === 1) {
            editButton.disabled = false;
            deleteButton.disabled = false;
            exportButton.disabled = false;
        } else if (countChecked > 1) {
            editButton.disabled = true;
            deleteButton.disabled = false;
            exportButton.disabled = false;
        }
        if (countCheckedFinish >= 1 && !notPlannedIsChecked) {
            finishButton.disabled = false;
        } else if (notPlannedIsChecked) {
            finishButton.disabled = true;
        }
    }

    function setGeneralCheckbox() {
        let generalCheckbox = document.getElementById("generalCheckbox");
        let checkboxes = document.getElementsByClassName("checkbox");
        let countChecked = 0, countCheckedFinish = 0;
        let notPlannedIsChecked = false;
        for (let i = 0; i < checkboxes.length; ++i) {
            if (!checkboxes[i].checked) {
                generalCheckbox.checked = false;
            } else {
                ++countChecked;
                if (checkboxes[i].getAttribute("status") === "PLANNED" ||
                    checkboxes[i].getAttribute("status") === "DEFERRED") {
                    countCheckedFinish++;
                } else {
                    notPlannedIsChecked = true;
                }
            }
        }
        setDisabledAttribute(countChecked, countCheckedFinish, notPlannedIsChecked);
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

    function getCheckTaskFinish() {
        let checkboxes = document.getElementsByClassName("checkbox");
        let checkedCheckboxes = [];
        for (let i = 0; i < checkboxes.length; ++i) {
            if (checkboxes[i].checked && (checkboxes[i].getAttribute("status") === "PLANNED"
                || checkboxes[i].getAttribute("status") === "DEFERRED")) {
                checkedCheckboxes.push(checkboxes[i]);
            }
        }
        return checkedCheckboxes;
    }

</script>
</html>
