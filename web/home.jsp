<%-- 
    Document   : adminHome
    Created on : Jan 23, 2021, 4:59:49 PM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Home</title>
        <link href="framework/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="framework/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>
            body {
                background-image : url('images/bg.png');
            }
            div.main {
                margin-top:5%;
                margin-left: 10%;
                margin-right: 10%;
            }
            div#quiz {
                margin-top:2%;
            }
        </style>
        <script>
            var index = 1;
            var size = 0;

            function makeSearch()
            {
                var input = $("<input>")
                        .attr("type", "hidden")
                        .attr("name", "txtIndex").val(index);
                $('#form-quiz').append(input);
                var input = $("<input>")
                        .attr("type", "hidden")
                        .attr("name", "txtTimeQuiz").val(time / 60.0);
                $('#form-quiz').append(input);
                $('#form-quiz').submit();
            }
            function focusTag()
            {
                let a = document.getElementById(index);
                if (a !== null)
                    a.style.backgroundColor = "rgb(243, 217, 104)";
            }
            function clickTag(param)
            {
                index = param;
                size = document.getElementsByName("paging").length;
                if (index > size)
                {
                    index = 1;
                }
                focusTag();
            }
            function changeIndex(param)
            {
                if (index === param)
                {
                    return;
                }
                if (param === -1)
                {
                    index = index - 1;
                    if (index < 1)
                    {
                        index++;
                        return;
                    }
                } else if (param === -2)
                {
                    index = index + 1;
                    if (index > size)
                    {
                        index--;
                        return;
                    }
                } else
                    index = param;
                makeSearch();
            }
        </script>
    </head>
    <c:if test="${empty sessionScope.QUIZ}">
        <body>
        </c:if>
        <c:if test="${ not empty sessionScope.QUIZ}">
        <body onLoad="setTimer();
                clickTag(${param.txtIndex});">
        </c:if>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <a class="navbar-brand" style="color:blue"><font style="font-size:20px;color:black">Welcome</font> ${sessionScope.ACCOUNT.name} 
                <label class="label label-success" style="font-size:10px;vertical-align:10px;">student</label>
            </a>
            <ul class="nav navbar-nav" style="float:right;">
                <li class="active">
                    <a>Home</a>
                </li>
                <c:if test="${not empty sessionScope.QUIZ}">
                    <li>
                        <a>History</a>
                    </li>
                </c:if>
                <c:if test="${empty sessionScope.QUIZ}">
                    <li>
                        <a href="history.jsp">History</a>
                    </li>
                </c:if>

                <li>
                    <a href="LogOutServlet" >Log Out</a>
                </li>
            </ul>
        </nav>
        <div class="main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title" style="text-align:center">Quiz Online</h3>
                </div>
                <div class="panel-body" style="text-align: center;">
                    <div style="float:left;width:15%;height:inherit">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Subject Option</h3>
                            </div>
                            <div class="panel-body">
                                <c:if test="${not empty applicationScope.SUBJECT}">
                                    <c:forEach var="subject" items="${applicationScope.SUBJECT}" varStatus="counter" >
                                        <c:if test="${param.takeQuiz != null}">
                                            <form action="home.jsp" style="width:100%;${counter.last ? '' : 'margin-bottom:10px'}" method="POST">
                                                <input type="hidden" name="txtSubjectId" value="${subject.subjectId}" />
                                                <input type="hidden" name="txtNumber" value="${subject.numOfQuestion}"/>
                                                <input type="hidden" name="txtTime" value="${subject.timeForQuiz}"/>
                                                <input type="hidden" name="isCheck" value="${subject.name}"/>
                                                <button 
                                                    class="btn btn-default" 
                                                    type="submit" 
                                                    disabled="true"
                                                    value="${subject.name}"
                                                    ${subject.name.equals(param.isCheck) ? 'style=\'width:100%;background-color:black;color:white\'' : 'style=\'width:100%;\''}
                                                    >
                                                    ${subject.name}
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${param.takeQuiz == null}">
                                            <form action="home.jsp" style="width:100%;${counter.last ? '' : 'margin-bottom:10px'}" method="POST">
                                                <input type="hidden" name="txtSubjectId" value="${subject.subjectId}" />
                                                <input type="hidden" name="txtNumber" value="${subject.numOfQuestion}"/>
                                                <input type="hidden" name="txtTime" value="${subject.timeForQuiz}"/>
                                                <input type="hidden" name="isCheck" value="${subject.name}"/>
                                                <button 
                                                    class="btn btn-default" 
                                                    type="submit" 
                                                    value="${subject.name}"
                                                    ${subject.name.equals(param.isCheck) ? 'disabled=\'true\' style=\'width:100%;background-color:black;color:white\'' : 'style=\'width:100%;\''}
                                                    >
                                                    ${subject.name}
                                                </button>
                                            </form>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>

                    </div>
                    <div style="float:right;width:80%;height:inherit;position:relative">
                        <div class="panel panel-warning">

                            <div class="panel-heading" id="timer" style="text-center: right;padding:0;font-size:30px;height:38.7px">Quiz Infor</div>
                            <div class="panel-body" style="padding: 0 10% 0 10%;min-height:153px">
                                <c:if test="${not empty sessionScope.QUIZ}">
                                    <c:set var="quiz" value="${sessionScope.QUIZ}"></c:set>
                                        <form action="SaveInforQuizServlet" id="form-quiz" method="POST">
                                            <input type="hidden" name="txtPage" value="${param.txtIndex}"/>
                                        <input type="hidden" name="isCheck" value="${param.isCheck}" />
                                        <input type="hidden" name="takeQuiz" value="" />
                                        <input type="hidden" name="txtSubjectId" value="${param.txtSubjectId}" />
                                        <c:if test="${not empty quiz[2*(param.txtIndex-1)+1]}" >
                                            <c:forEach begin="1" end="2" varStatus="counter" >

                                                <div class="panel panel-warning" id="quiz">
                                                    <div class="panel-heading" style="padding:0;text-align:left;">Question ${2*(param.txtIndex-1) + counter.count}</div>
                                                    <div class="panel-body" style="height: 200px; overflow-y:auto;padding-top:0">
                                                        <div class="table-responsive">
                                                            <table class="table table-hover">
                                                                <tbody>
                                                                    <tr>
                                                                        <td style="text-align: left">
                                                                            <input type="hidden" name="questionId${counter.count}" value="${quiz[2*(param.txtIndex-1) + counter.count-1].getKey().questionId}"/>
                                                                            ${quiz[2*(param.txtIndex-1) + counter.count-1].getKey().content}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td style="border:0">
                                                                            <div class="radio" style="margin:0;text-align:left" >
                                                                                <label style="width:100%">
                                                                                    <input 
                                                                                        type="radio" 
                                                                                        name="rdOption${counter.count}" 
                                                                                        value="1"
                                                                                        ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[0].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                        >
                                                                                    ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[0].content}
                                                                                </label>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td style="border:0">
                                                                            <div class="radio" style="margin:0;text-align:left" >
                                                                                <label style="width:100%">
                                                                                    <input 
                                                                                        type="radio" 
                                                                                        name="rdOption${counter.count}" 
                                                                                        value="2"
                                                                                        ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[1].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                        >
                                                                                    ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[1].content}
                                                                                </label>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td style="border:0">
                                                                            <div class="radio" style="margin:0;text-align:left" >
                                                                                <label style="width:100%">
                                                                                    <input 
                                                                                        type="radio" 
                                                                                        name="rdOption${counter.count}" 
                                                                                        value="3"
                                                                                        ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[2].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                        >
                                                                                    ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[2].content}
                                                                                </label>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td style="border:0">
                                                                            <div class="radio" style="margin:0;text-align:left" >
                                                                                <label style="width:100%">
                                                                                    <input 
                                                                                        type="radio" 
                                                                                        name="rdOption${counter.count}" 
                                                                                        value="4"
                                                                                        ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[3].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                        >
                                                                                    ${quiz[2*(param.txtIndex-1) + counter.count-1].getValue()[3].content}
                                                                                </label>
                                                                            </div>
                                                                        </td>
                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </div>

                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty quiz[2*(param.txtIndex-1)+1]}">
                                            <div class="panel panel-warning" id="quiz">
                                                <div class="panel-heading" style="padding:0;text-align:left;">Question ${2*(param.txtIndex-1) + 1}</div>
                                                <div class="panel-body" style="height: 200px; overflow-y:auto;padding-top:0">
                                                    <div class="table-responsive">
                                                        <table class="table table-hover">
                                                            <tbody>
                                                                <tr>
                                                                    <td style="text-align: left">
                                                                        <input type="hidden" name="questionId1" value="${quiz[2*(param.txtIndex-1)].getKey().questionId}"/>
                                                                        ${quiz[2*(param.txtIndex-1)].getKey().content}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="border:0">
                                                                        <div class="radio" style="margin:0;text-align:left" >
                                                                            <label style="width:100%">
                                                                                <input 
                                                                                    type="radio" 
                                                                                    name="rdOption1" 
                                                                                    value="1"
                                                                                    ${quiz[2*(param.txtIndex-1)].getValue()[0].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                    >
                                                                                ${quiz[2*(param.txtIndex-1)].getValue()[0].content}
                                                                            </label>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="border:0">
                                                                        <div class="radio" style="margin:0;text-align:left" >
                                                                            <label style="width:100%">
                                                                                <input 
                                                                                    type="radio" 
                                                                                    name="rdOption1" 
                                                                                    value="2"
                                                                                    ${quiz[2*(param.txtIndex-1)].getValue()[1].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                    >
                                                                                ${quiz[2*(param.txtIndex-1)].getValue()[1].content}
                                                                            </label>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="border:0">
                                                                        <div class="radio" style="margin:0;text-align:left" >
                                                                            <label style="width:100%">
                                                                                <input 
                                                                                    type="radio" 
                                                                                    name="rdOption1" 
                                                                                    value="3"
                                                                                    ${quiz[2*(param.txtIndex-1)].getValue()[2].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                    >
                                                                                ${quiz[2*(param.txtIndex-1)].getValue()[2].content}
                                                                            </label>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="border:0">
                                                                        <div class="radio" style="margin:0;text-align:left" >
                                                                            <label style="width:100%">
                                                                                <input 
                                                                                    type="radio" 
                                                                                    name="rdOption1" 
                                                                                    value="4"
                                                                                    ${quiz[2*(param.txtIndex-1)].getValue()[3].isIsChoose() ? 'checked=\'checked\'': ''}
                                                                                    >
                                                                                ${quiz[2*(param.txtIndex-1)].getValue()[3].content}
                                                                            </label>
                                                                        </div>
                                                                    </td>
                                                                </tr>

                                                            </tbody>
                                                        </table>
                                                    </div>

                                                </div>
                                            </div>
                                            <div id="quiz" style="max-height: 223px;height: 223px"></div>
                                        </c:if>
                                    </form>

                                    <button class="btn btn-success" onClick="submitQuiz()" style="position:absolute;bottom:22px;right:1px">Submit</button>
                                    <button class="btn btn-default" onClick="window.location.replace('ExitQuizServlet')" style="position:absolute;bottom:22px;left:1px">Exit</button>
                                    <c:if test="${not empty sessionScope.PAGE && sessionScope.PAGE > 1}">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;padding:0;margin:0">

                                            <ul class="pagination" style="">
                                                <li><a onClick="changeIndex(-1)" >&laquo;</a></li>
                                                    <c:forEach begin="1" end="${sessionScope.PAGE}" step="1" varStatus="counter">
                                                    <li><a onClick="changeIndex(${counter.count})" id="${counter.count}" name="paging" >${counter.count}</a></li>
                                                    </c:forEach>
                                                <li><a onClick="changeIndex(-2)" >&raquo;</a></li>
                                            </ul>
                                        </div>
                                    </c:if>

                                </c:if>
                                <c:if test="${param.isCheck != null && empty sessionScope.QUIZ && param.isResult == null}" >
                                    <div class="row" style="height: 153px;position:relative">

                                        <label style="position:absolute;bottom:70%;left:39%">Number of question: <span class="label label-success" style="vertical-align: 1px">${param.txtNumber}</span></label>
                                        <label style="position:absolute;bottom:50%;left:40%">Time to take quiz: <span class="label label-success" style="vertical-align: 1px">${param.txtTime}</span></label>
                                        <form action="TakeQuizServlet" method="POST">
                                            <input type="hidden" name="isCheck" value="${param.isCheck}" />
                                            <input type="hidden" name="takeQuiz" value="" />
                                            <input type="hidden" name="txtSubjectId" value="${param.txtSubjectId}" />
                                            <input type="hidden" name="txtIndex" value="1"/>
                                            <button  type="submit" class="btn btn-primary" style="position:absolute;bottom:10%;left:43%">Take Quiz!!!</button>
                                             <c:if test="${not empty param.txtMessage}">
                                                <font color="red" >${param.txtMessage}</font>
                                            </c:if>
                                        </form>
                                    </div>

                                </c:if>
                                <c:if test="${param.isCheck == null && empty sessionScope.QUIZ}"> 
                                    <c:if test="${not empty sessionScope.RESULT}">
                                        <label style="position:absolute;bottom:65%;left:25%">Email: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.email}</span></label>
                                        <label style="position:absolute;bottom:65%;left:60%">Subject ID: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.subjectId}</span></label>
                                        <label style="position:absolute;bottom:45%;left:25%">Start Time: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.passStartTime()}</span></label>
                                        <label style="position:absolute;bottom:45%;left:60%">End Time: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.passEndTime()}</span></label>
                                        <label style="position:absolute;bottom:20%;left:25%">Correct: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.numOfCorrect}</span></label>
                                        <label style="position:absolute;bottom:20%;left:60%">InCorrect: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.numOfInCorrect}</span></label>
                                        <label style="position:absolute;bottom:10%;left:44%">Total Point: <span class="label label-success" style="vertical-align: 1px">${sessionScope.RESULT.total}</span></label>
                                    </c:if>
                                </c:if>

                            </div>
                        </div>
                    </div>
                    <div style="float:left;height: available;width: 20%">
                        <img src="images/pencil.jpg" style="width:100%;max-height:300px"/>
                    </div>
                </div>
            </div>
        </div>

        <script src="framework/js/jquery-3.2.1.min.js"></script>
        <script src="framework/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <script>

                                                    const beginMinutes = ${empty sessionScope.TIME ? 0 : sessionScope.TIME};
                                                    let time = beginMinutes * 60;
                                                    function setTimer()
                                                    {
                                                        const minutes = Math.floor(time / 60);
                                                        let seconds = time % 60;
                                                        document.getElementById("timer").innerHTML = '' + minutes + ' : ' + seconds.toFixed(0) + '';
                                                        time--;
                                                        if (time === 0)
                                                        {
                                                            var input1 = $("<input>")
                                                                    .attr("type", "hidden")
                                                                    .attr("name", "txtTimeQuiz").val(time / 60.0);
                                                            var input2 = $("<input>")
                                                                    .attr("type", "hidden")
                                                                    .attr("name", "isSubmit").val('true');
                                                            $('#form-quiz').append(input1);
                                                            $('#form-quiz').append(input2);
                                                            $('#form-quiz').submit();
                                                            return;
                                                        }
                                                        setTimeout(setTimer, 1000);
                                                    }
                                                    function submitQuiz()
                                                    {
                                                        $.confirm({
                                                            title: 'Submit quiz?',
                                                            content: 'Auto close if not response after 3s',
                                                            autoClose: 'cancel|3000',
                                                            buttons: {
                                                                delete: {
                                                                    text: 'Submit',
                                                                    action: function () {
                                                                        var input1 = $("<input>")
                                                                                .attr("type", "hidden")
                                                                                .attr("name", "txtTimeQuiz").val(time / 60.0);
                                                                        var input2 = $("<input>")
                                                                                .attr("type", "hidden")
                                                                                .attr("name", "isSubmit").val('true');
                                                                        $('#form-quiz').append(input1);
                                                                        $('#form-quiz').append(input2);
                                                                        $('#form-quiz').submit();
                                                                    }
                                                                },
                                                                cancel: function () {
                                                                }
                                                            }
                                                        });
                                                    }
        </script>
    </body>
</html>

