<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<html lang="en">
<head>
    <link rel="stylesheet" href="../css/style.css">
    <meta charset="UTF-8"/>
    <title>Journals page</title>
</head>
<body onload="showError()">
<div class="modal">
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
    <table class="table_sort">
        <caption>TABLE OF JOURNALS</caption>
        <thead>
        <tr>
            <th style="cursor:default">
                <input type="checkbox" id="generalCheckbox" onchange="setCheck()">
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
                           onchange="setGeneralCheckbox()">
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
        <input type="button" id="deleteButt" class="button" value="Delete" disabled>
        <input type="button" id="exportButt" class="button" value="Export" disabled>
        <input type="button" id="importButt" class="button" value="Import">
        <input type="button" id="showButt" class="button" value="Show all journals">
        <input type="button" class="button" onclick="direct()" value="Return to sign in page"
               style="display: block; margin-left: 30% ">
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
                <input type="file" id="fileSelect" style="visibility: hidden" name="importFile" lang="en"/>
            </div>
        </div>
        <p id="messages"></p>
        <div id="submitButton">
            <input type="submit" class="button" id="submitImportButt" onclick="sendFile()" value="Upload file"
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
</body>
<script>
    let showButton = document.getElementById("showButt");

    showButton.onclick = function () {
        window.location.href = "/journals";
    };

    function direct() {
        window.location.href = "/start";
    }

    function showError() {
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        let msgError = "<%=error%>";
        alert(msgError);
        <%}%>
    }

    for (let close of document.getElementsByClassName('close')) {
        close.addEventListener('click', () => close.parentElement.parentElement.style.display = 'none');
    }

    document.getElementById('importButt').addEventListener('click', () => {
        document.getElementById('importWindow').style.display = 'block';
        document.getElementById('submitImportButt').disabled = true;
    });

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
    let addButton = document.getElementById("addButt");
    let editButton = document.getElementById("editButt");
    let deleteButton = document.getElementById("deleteButt");
    let exportButton = document.getElementById("exportButt");
    let submitExportButt = document.getElementById("submitExportButt");

    editButton.onclick = function () {
        let id = getCheckJournal()[0].value;
        document.getElementById("editId" + id).value = id;
        document.getElementById("editWindow" + id).style.display = "block";
    };

    addButton.onclick = function () {
        addWindow.style.display = "block";
    };

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
    };
    exportButton.onclick = function () {
        document.getElementById('exportWindow').style.display = 'block';
    }
    submitExportButt.onclick = function () {
        let strIds = "";
        let ids = getCheckJournal();
        for (let i = 0; i < ids.length; ++i) {
            strIds += ids[i].value + " ";
        }
        document.getElementById('exportIds').value = strIds;

        document.getElementById('exportForm').submit();
        document.getElementById('exportWindow').style.display = 'none';
    }

    let fileName = document.getElementById('fileName');

    fileName.addEventListener('input', () => submitExportButt.disabled = fileName.value === "");


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
        let exportButton = document.getElementById("exportButt");
        if (countChecked === 0) {
            editButton.disabled = true;
            deleteButton.disabled = true;
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

    function $id(id) {
        return document.getElementById(id);
    }

    if (window.File && window.FileList && window.FileReader) {
        Init();
    }


    function Init() {

        var fileSelect = $id("fileSelect");
        var fileDrag = $id("fileDrag");

        fileSelect.addEventListener("change", FileSelectHandler, false);

        var xhr = new XMLHttpRequest();
        if (xhr.upload) {
            fileDrag.addEventListener("dragover", FileDragHover, false);
            fileDrag.addEventListener("dragleave", FileDragHover, false);
            fileDrag.addEventListener("drop", FileSelectHandler, false);
            fileDrag.style.display = "block";
        }

    }


    function FileDragHover(e) {
        e.stopPropagation();
        e.preventDefault();
        e.target.className = (e.type === "dragover" ? "hover" : "");
    }

    var file;

    function FileSelectHandler(e) {
        FileDragHover(e);
        var files = e.target.files || e.dataTransfer.files;
        file = files[0];
        if (file.type !== "text/plain" && file.type !== "text/xml") {
            alert("Incorrect type");
            return;
        }

        $id("messages").innerHTML = "File information <br> name: " + file.name + ", type:" + file.type + ", size: " + file.size
            + " bytes";

        let submitButton = $id("submitImportButt");
        submitButton.disabled = false;
    }

    async function sendFile() {
        try {
            let url = "/importJournal";
            let formData = new FormData();
            formData.append("file", file);

            let response = await fetch(url, {
                method: 'POST',
                body: formData
            });
            let result = await response;
            if (result.ok) {
                let answer = result.headers.get('error');
                if (answer === "0") window.location.href = '/journals';
                else {
                    alert(answer)
                    $id("messages").innerHTML = "";
                    window.location.href = '/journals';
                }
            }
        } catch (error) {
            console.error(error);
        }
    }
</script>
</html>
