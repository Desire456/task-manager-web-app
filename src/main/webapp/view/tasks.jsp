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
<body onload="showError(<%=request.getAttribute("error")%>)">
<div class="container">
    <div class="modal">
        <div class="container">
            <div class="filter">
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
        </div>
        <div class="table-wrapper">
        <table class="fl-table table_sort">
            <caption>TABLE OF TASKS</caption>
            <thead>
            <tr>
                <th style="cursor:default"><input type="checkbox" id="generalCheckbox" onchange="setTasksCheck()"></th>
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
                               status="<x:out select = "$task/status"/>" onchange="setTasksGeneralCheckbox()">
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
        </div>


        <% if (session.getAttribute("tasks") != null) { %>
        <x:parse xml="${sessionScope.tasks}" var="output"/>
        <x:forEach select="$output/tasks/task" var="task">
            <x:set var="id" select="$task/id"/>
            <div class="window" id="editWindow<x:out select="$id"/>">
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

        <div class="actions">
            <input type="button" id="addButt" class="button" value="Add">
            <input type="button" id="editButt" class="button" value="Edit" title="You can edit only one task" disabled>
            <input type="button" id="deleteButt" class="button" value="Delete" onclick="deleteItems('Task')" disabled>
            <input type="button" id="exportButt" class="button" value="Export" disabled>
            <input type="button" id="importButt" class="button" value="Import">
            <input type="button" id="finishButt" class="button" value="Finish"
                   title="You can finish tasks only with status PLANNED" disabled>
            <input type="button" id="showButt" class="button" onclick="showAll('tasks')" value="Show all tasks">
        </div>
        <div class="return-btn">
            <input type="button" class="button" onclick="direct('tasks')" value="Return to journals page">
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

        <div class="window" id="importWindow">
            <div>
                <span class="close">X</span>
            </div>
            <div id="importDiv">
                <div id="btn">
                    <label for="fileSelect">Browse...</label>
                </div>
                <div id="fileDrag">
                    Or drag them here
                    <input type="file" id="fileSelect" style="visibility: hidden" accept=".txt,.xml" name="importFile"
                           lang="en"/>
                </div>
            </div>
            <p id="messages"></p>
            <div id="submitButton">
                <input type="submit" class="button" id="submitImportButt" onclick="sendFile('Task')" value="Upload file"
                       disabled/>
            </div>
        </div>

        <div class="window" id="exportWindow">
            <form action="${pageContext.request.contextPath}/exportTask" method="POST" id="exportForm">
                <span class="close" id="exportClose">X</span>
                Save as: <input name="fileName" id="fileName" required>
                <input type="hidden" name="ids" id="exportIds" value="">
                <br>
                <br>
                <input type="submit" class="button" id="submitExportButt" value="Download" disabled/>
            </form>
        </div>
    </div>
</div>
</body>
<script src="../js/script.js" defer></script>
</html>
