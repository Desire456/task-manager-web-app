function $id(id) {
    return document.getElementById(id);
}

function showAll(pageName) {
    window.location.href = '/' + pageName;
}

function direct(pageName) {
    if (pageName === 'journals') {
        window.location.href = '/start';
    } else {
        window.location.href = '/journals';
    }
}

function showError(errorMsg) {
    if (errorMsg !== null) {
        alert(errorMsg);
    }
}

for (let close of document.getElementsByClassName('close')) {
    close.addEventListener('click', () => close.parentElement.parentElement.style.display = 'none');
}

$id('importButt').addEventListener('click', () => {
    $id('importWindow').style.display = 'block';
    $id('submitImportButt').disabled = true;
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

let addWindow = $id('addWindow');
let addButton = $id('addButt');
let editButton = $id('editButt');
let exportButton = $id('exportButt');
let finishButton = $id("finishButt");
let submitExportButt = $id('submitExportButt');

editButton.onclick = function () {
    let id = getCheckedItems()[0].value;
    $id("editId" + id).value = id;
    $id("editWindow" + id).style.display = "block";
};

addButton.onclick = function () {
    addWindow.style.display = "block";
};

if (finishButton != null) {
    finishButton.onclick = function () {
        if (confirm("Are you really want to finish these tasks?")) {
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
}

function deleteItems(entityName) {
    if (confirm("Are you really want to delete?")) {
        let form = document.createElement('form');
        form.action = '/delete' + entityName;
        form.method = 'POST';

        form.innerHTML = '<input type = "hidden" name="ids" id = "deleteIds" value="">';
        let strIds = "";
        let ids = getCheckedItems();
        for (let i = 0; i < ids.length; ++i) {
            strIds += ids[i].value + " ";
        }
        strIds = strIds.trimEnd();
        document.body.append(form);
        $id("deleteIds").value = strIds;
        form.submit();
    }
}

exportButton.onclick = function () {
    $id('exportWindow').style.display = 'block';
}
submitExportButt.onclick = function () {
    let strIds = "";
    let ids = getCheckedItems();
    for (let i = 0; i < ids.length; ++i) {
        strIds += ids[i].value + " ";
    }
    $id('exportIds').value = strIds;

    $id('exportForm').submit();
    $id('exportWindow').style.display = 'none';
}

let fileName = $id('fileName');

fileName.addEventListener('input', () => submitExportButt.disabled = fileName.value === "");

function goToJournal(id) {
    if (confirm("Are you really want to open this journal?")) {
        let form = document.createElement('form');
        form.action = '/tasks';
        form.method = 'POST';
        form.innerHTML = '<input type = "hidden" name="journalId" id = "journalId" value="">';
        document.body.append(form);
        $id("journalId").value = id;
        form.submit();
    }
}


function setJournalsCheck() {
    let generalCheckbox = $id("generalCheckbox");
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
    setDisabledJournalButtons(countChecked);
}

function setTasksCheck() {
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
    setDisabledTaskButtons(countChecked, countCheckedFinish, notPlannedIsChecked);
}

function setDisabledJournalButtons(countChecked) {
    let editButton = $id("editButt");
    let deleteButton = $id("deleteButt");
    let exportButton = $id("exportButt");
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

function setDisabledTaskButtons(countChecked, countCheckedFinish, notPlannedIsChecked) {
    let editButton = $id("editButt");
    let deleteButton = $id("deleteButt");
    let exportButton = $id("exportButt");
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

function setJournalsGeneralCheckbox() {
    let generalCheckbox = $id("generalCheckbox");
    let checkboxes = document.getElementsByClassName("checkbox");
    let countChecked = 0;
    for (let i = 0; i < checkboxes.length; ++i) {
        if (!checkboxes[i].checked) {
            generalCheckbox.checked = false;
        } else {
            ++countChecked;
        }
    }
    setDisabledJournalButtons(countChecked);
}

function setTasksGeneralCheckbox() {
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
    setDisabledTaskButtons(countChecked, countCheckedFinish, notPlannedIsChecked);
}

function getCheckedItems() {
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

async function sendFile(entityName) {
    try {
        let url = "/import" + entityName;
        let formData = new FormData();
        formData.append("file", file);

        let response = await fetch(url, {
            method: 'POST',
            body: formData
        });
        let result = await response;
        if (result.ok) {
            let answer = result.headers.get('error');
            let locationHref = '/' + entityName.toString().toLowerCase() + 's';
            if (answer === "0") window.location.href = locationHref;
            else {
                alert(answer)
                $id("messages").innerHTML = "";
                window.location.href = locationHref;
            }
        }
    } catch (error) {
        console.error(error);
    }
}