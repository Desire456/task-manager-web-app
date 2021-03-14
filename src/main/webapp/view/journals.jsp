<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<html lang="en">
<head>
    <link rel="stylesheet" href="../css/style.css">
    <meta charset="UTF-8"/>
    <title>Journals page</title>
</head>
<body onload="showError(<%=request.getAttribute("error")%>)">
<div class="container">
    <div class="modal">
        <div class="container">
            <div class="filter">
                <form action="${pageContext.request.contextPath}/filterJournals" id="filterForm" method="POST">
                    Column: <select name="column" required>
                    <option value="name">Name</option>
                    <option value="description">Description</option>
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
                <caption>TABLE OF JOURNALS</caption>
                <thead>
                <tr>
                    <th style="cursor:default">
                        <input type="checkbox" id="generalCheckbox" onchange="setJournalsCheck()">
                    <th style="width: 150px" title="Click this button to sort by this column">Name</th>
                    <th title="Click this button to sort by this column">Description</th>
                    <th title="Click this button to sort by this column">Creation date</th>
                </tr>
                </thead>
                <tbody>
                <% if (session.getAttribute("journals") != null) { %>
                <x:parse xml="${sessionScope.journals}" var="output"/>
                <x:forEach select="$output/journals/journal" var="journal">
                    <x:set var="id" select="$journal/id"/>
                    <tr>
                        <td style="cursor:default; text-align:center">
                            <input type="checkbox" value="<x:out select="$id"/>" class="checkbox"
                                   onchange="setJournalsGeneralCheckbox()">
                        </td>
                        <td onclick="goToJournal(<x:out select="$id"/>)">
                            <x:out select="$journal/name"/>
                        </td>
                        <td onclick="goToJournal(<x:out select="$id"/>)">
                            <x:out select="$journal/description"/>
                        </td>
                        <td onclick="goToJournal(<x:out select="$id"/>)">
                            <x:out select="$journal/creationDate"/>
                        </td>
                    </tr>
                </x:forEach>
                <%}%>
                </tbody>
            </table>
        </div>


        <% if (session.getAttribute("journals") != null) { %>
        <x:parse xml="${sessionScope.journals}" var="output"/>
        <x:forEach select="$output/journals/journal" var="journal">
            <x:set var="id" select="$journal/id"/>
            <div class="window" id="editWindow<x:out select="$id"/>">
                <form action="${pageContext.request.contextPath}/editJournal" method="POST">
                    <span class="close">X</span>
                    Name: <input type="text" name="name" value="<x:out select="$journal/name"/>" required>
                    Description: <input type="text" name="description"
                                        value="<x:out select="$journal/description"/>" required>
                    <input type="hidden" name="id" id="editId<x:out select="$id"/>" value="">
                    <br>
                    <button type="submit">Edit</button>
                </form>
            </div>
        </x:forEach>
        <%}%>

        <div class="actions">
            <input type="button" id="addButt" class="button" value="Add">
            <input type="button" id="editButt" class="button" value="Edit" title="You can edit only one journal"
                   disabled>
            <input type="button" id="deleteButt" class="button" value="Delete" onclick="deleteItems('Journal')" disabled>
            <input type="button" id="exportButt" class="button" value="Export" disabled>
            <input type="button" id="importButt" class="button" value="Import">
            <input type="button" id="showButt" class="button" value="Show all journals" onclick="showAll('journals')">
        </div>
        <div class="return-btn">
            <input type="button" class="button" onclick="direct('journals')" value="Return to sign in page">
        </div>

        <div class="window" id="addWindow">
            <form action="${pageContext.request.contextPath}/addJournal" method="POST">
                <span class="close">X</span>
                Name: <input type="text" name="name" required>
                Description: <input type="text" name="description" required>
                <br><br><br><br>
                Do you want to make it public? <input type="checkbox" name="accessModifier" id="accessId">
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
                <input type="submit" class="button" id="submitImportButt" onclick="sendFile('Journal')" value="Upload file"
                       disabled/>
            </div>
        </div>

        <div class="window" id="exportWindow">
            <form action="${pageContext.request.contextPath}/exportJournal" method="POST" id="exportForm">
                <span class="close">X</span>
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
