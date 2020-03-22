<%@ page import="org.netcracker.students.model.Task" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDateTime" %><%--
Created by IntelliJ IDEA.
User: user
Date: 28.02.2020
Time: 9:16
To change this template use File | Settings | File Templates.
--%>
<%
ArrayList<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 3; ++i) {
    tasks.add(new Task(i, i, "#" + i, "#" + i, LocalDateTime.now(), null, "Planned"));
    }
    int j = 0;
%>

    <html>
    <head>
        <meta charset="UTF-8"/>
        <title>Journals page</title>
        <style>
            .modal {
                padding: 50px;
                position: fixed;
                top: 50%;
                left: 50%;
                -webkit-transform: translate(-50%, -50%);
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
            }

            table {
                font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
                text-align: left;
                border-collapse: separate;
                border-spacing: 5px;
                background: #ECE9E0;
                color: #656665;
                border: 16px solid #ECE9E0;
                border-radius: 20px;
            }

            th {
                font-size: 18px;
                padding: 10px;
            }

            td {
                background: #F5D7BF;
                padding: 10px;
            }
            .button {
                padding: 8px 28px;
                font-size: 15px;
                font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
                background-color: #e7e7e7;
                color: #656665;
            }
        </style>
    </head>
    <body>
    <div class="modal" >
        <table>
            <caption>Table of tasks</caption>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Description</th>
                <th>Planned date</th>
                <th>Date of done</th>
                <th>Status</th>
            <tr>
                <td><%= tasks.get(j).getId()%></td>
                <td><%= tasks.get(j).getName()%></td>
                <td><%= tasks.get(j).getDescription()%></td>
                <td><%= tasks.get(j).getPlannedDate()%></td>
                <td><%= tasks.get(j).getDateOfDone()%></td>
                <td><%= tasks.get(j++).getStatus()%></td>
            </tr>
            <tr>
                <td><%= tasks.get(j).getId()%></td>
                <td><%= tasks.get(j).getName()%></td>
                <td><%= tasks.get(j).getDescription()%></td>
                <td><%= tasks.get(j).getPlannedDate()%></td>
                <td><%= tasks.get(j).getDateOfDone()%></td>
                <td><%= tasks.get(j++).getStatus()%></td>
            </tr>
            <tr>
                <td><%= tasks.get(j).getId()%></td>
                <td><%= tasks.get(j).getName()%></td>
                <td><%= tasks.get(j).getDescription()%></td>
                <td><%= tasks.get(j).getPlannedDate()%></td>
                <td><%= tasks.get(j).getDateOfDone()%></td>
                <td><%= tasks.get(j).getStatus()%></td>
            </tr>
            <tr>
                <td></td>
                <td><input class = "button" type = "button" value = "Change"></td>
                <td><input class = "button" type = "button" value = "Delete"></td>
                <td><input class = "button" type = "button" value = "Add"></td>
                <td></td>
            </tr>
        </table>

    </div>
    </body>
    </html>
